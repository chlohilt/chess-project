package client;

import chess.ChessGame;
import chess.ChessMove;
import chessImpl.ChessMoveImpl;
import chessImpl.ChessPositionImpl;
import client.websocket.NotificationHandler;
import client.websocket.WebSocketFacade;
import com.google.gson.Gson;
import database.CommonDataAccess;
import exception.ResponseException;
import models.Game;
import requests.*;
import database.DataAccessException;
import responses.LoginResponse;
import responses.RegisterResponse;
import server.ServerFacade;

import static ui.EscapeSequences.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

public class ChessClient {
  Gson gson = new Gson();
  private String visitorName = null;
  private String currentAuthToken = null;
  private final ServerFacade server;
  private State state = State.SIGNEDOUT;
  private WebSocketFacade ws;
  private final String headerFooter = "  a  b  c  d  e  f  g  h ";
  private String serverUrl;
  private Integer currentGameID;
  private final NotificationHandler notificationHandler;
  private CommonDataAccess commonDataAccess = new CommonDataAccess();

  public ChessClient(String serverUrl, NotificationHandler notificationHandler) {
      server = new ServerFacade(serverUrl);
      this.serverUrl = serverUrl;
      this.notificationHandler = notificationHandler;
  }

  public void setCurrentAuthToken(String currentAuthToken) {
    this.currentAuthToken=currentAuthToken;
  }

  public String eval(String input) {
    try {
      var tokens = input.toLowerCase().split(" ");
      var cmd = (tokens.length > 0) ? tokens[0] : "help";
      var params = Arrays.copyOfRange(tokens, 1, tokens.length);
      return switch (cmd) {
        case "login" -> logIn(params);
        case "register" -> register(params);
        case "logout" -> logOut();
        case "create" -> createGame(params);
        case "join", "observe" -> joinGame(params);
        case "move" -> move(params);
        case "moves" -> showValidMoves(params);
        case "draw" -> drawBoard();
        case "leave" -> leaveGame();
        case "resign" -> resignGame();
        case "list" -> listGames();
        case "quit" -> "quit";
        default -> help();
      };
    } catch (DataAccessException ex) {
      return ex.getMessage();
    }
  }

  public String joinGame(String... params) throws DataAccessException {
    ChessGame.TeamColor teamColor=null;
    if (params.length > 1) {
      if (Objects.equals(params[1].toUpperCase(), "WHITE")) {
        teamColor=ChessGame.TeamColor.WHITE;
      } else if (Objects.equals(params[1].toUpperCase(), "BLACK")) {
        teamColor=ChessGame.TeamColor.BLACK;
      }
    }
      try {
        server.joinGame(new JoinGameRequest(visitorName, Integer.valueOf(params[0]), teamColor), currentAuthToken);
        if (params.length > 1) {
          if (Objects.equals(params[1].toUpperCase(), "WHITE")) {
            System.out.print(drawStartingWhiteBoard());
          } else if (Objects.equals(params[1].toUpperCase(), "BLACK")) {
            System.out.print(drawStartingBlackBoard());
          }
          ws = new WebSocketFacade(serverUrl, notificationHandler);
          ws.joinGame(currentAuthToken, Integer.valueOf(params[0]), teamColor);
          state = State.GAMEPLAY;
          currentGameID = Integer.valueOf(params[0]);
          return visitorName + " joined game " + params[0] + " as: " + params[1];
        }
        else {
          System.out.print(drawStartingWhiteBoard());
          state = State.GAMEPLAY;
          currentGameID = Integer.valueOf(params[0]);
          return visitorName + " joined game " + params[0];
        }
      } catch (DataAccessException | ResponseException e) {
        return "Invalid game join. Please try again.";
      }

  }

  public String drawBoard() {
    try {
      String whiteColor = SET_TEXT_COLOR_RED;
      String blackColor = SET_TEXT_COLOR_BLUE;
      Game game = commonDataAccess.getCommonGameDAO().findGame(currentGameID);
      if (Objects.equals(game.getWhiteUsername(), commonDataAccess.getCommonAuthDAO().returnUsername(currentAuthToken))) {
        return ws.printBoard(game.getChessGame().getBoard(), whiteColor, blackColor, null);
      } else {
        return ws.printBoard(game.getChessGame().getBoard(), blackColor, whiteColor, null);
      }
    } catch (DataAccessException e) {
      return "Error: Database error";
    }

  }

  public String resignGame() {
    try {
      ws.resignGame(currentAuthToken, currentGameID);
      currentGameID = null;
      return "You have resigned the game.";
    } catch (ResponseException e) {
      return "Error";
    }
  }

  public String leaveGame() {
    try {
      ws.leaveGame(currentAuthToken, currentGameID);
      currentGameID = null;
      return "You have left the game.";
    } catch (ResponseException e) {
      return "Error";
    }
  }

  public String move(String... params) {
    String invalidMove = "Invalid move. Please try again.";
    try {
      if (params.length < 2) {
        return invalidMove;
      }
      ChessPositionImpl startChessPosition = new ChessPositionImpl(Integer.valueOf(params[0].substring(1)), params[0].charAt(0) - 'a' + 1);
      ChessPositionImpl endChessPosition = new ChessPositionImpl(Integer.valueOf(params[1].substring(1)), params[1].charAt(0) - 'a' + 1);
      ws.makeMove(currentAuthToken,
              currentGameID,
              new ChessMoveImpl(startChessPosition, endChessPosition, null));
      return "Moved from " + params[0] + " to " + params[1];
    } catch (ResponseException e) {
      System.out.println(invalidMove);
    }
    return invalidMove;
  }

  public String showValidMoves(String... params) throws DataAccessException {
    ChessPositionImpl startChessPosition = new ChessPositionImpl(Integer.valueOf(params[0].substring(1)), params[0].charAt(0) - 'a' + 1);
    Game currentGame = commonDataAccess.getCommonGameDAO().findGame(currentGameID);
    Collection<ChessMove> validMoves = ws.showValidMoves(currentGameID, startChessPosition);
    String whiteColor = SET_TEXT_COLOR_RED;
    String blackColor = SET_TEXT_COLOR_BLUE;
    String userName = commonDataAccess.getCommonAuthDAO().returnUsername(currentAuthToken);

    if (Objects.equals(currentGame.getWhiteUsername(), userName)) {
      return ws.printBoard(currentGame.getChessGame().getBoard(), whiteColor, blackColor, validMoves);
    } else {
      return ws.printBoard(currentGame.getChessGame().getBoard(), blackColor, whiteColor, validMoves);
    }
  }

  public String drawStartingWhiteBoard() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(RESET_TEXT_COLOR + SET_BG_COLOR_DARK_GREY + headerFooter + "\n");
    for (int i = 0; i < 8; i++) {
      stringBuilder.append(SET_BG_COLOR_DARK_GREY + (i + 1) + RESET_BG_COLOR);
      for (int j = 0; j < 8; j++) {
        if ((i + j) % 2 == 0) {
          boardHelper(stringBuilder, i, j, SET_BG_COLOR_LIGHT_GREY, SET_TEXT_COLOR_RED, SET_TEXT_COLOR_BLUE);
        } else {
          boardHelper(stringBuilder, i, j, SET_BG_COLOR_DARK_GREY, SET_TEXT_COLOR_RED, SET_TEXT_COLOR_BLUE);
        }
      }
      stringBuilder.append(SET_BG_COLOR_DARK_GREY + (i + 1) + "\n");
    }
    stringBuilder.append(SET_BG_COLOR_DARK_GREY + headerFooter + SET_TEXT_COLOR_WHITE + "\n\n");
    return stringBuilder.toString();
  }

  public String drawStartingBlackBoard() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(RESET_TEXT_COLOR + SET_BG_COLOR_DARK_GREY + headerFooter + "\n");
    for (int i = 0; i < 8; i++) {
      stringBuilder.append(SET_BG_COLOR_DARK_GREY + (i + 1) + RESET_BG_COLOR);
      for (int j = 0; j < 8; j++) {
        if ((i + j) % 2 == 0) {
          boardHelper(stringBuilder, i, j, SET_BG_COLOR_LIGHT_GREY, SET_TEXT_COLOR_BLUE, SET_TEXT_COLOR_RED);
        } else {
          boardHelper(stringBuilder, i, j, SET_BG_COLOR_DARK_GREY, SET_TEXT_COLOR_BLUE, SET_TEXT_COLOR_RED);
        }
      }
      stringBuilder.append(SET_BG_COLOR_DARK_GREY + (i + 1) + "\n");
    }
    stringBuilder.append(SET_BG_COLOR_DARK_GREY + headerFooter + SET_TEXT_COLOR_WHITE + "\n\n");
    return stringBuilder.toString();
  }

  public void boardHelper(StringBuilder stringBuilder, int i, int j, String backgroundColor, String firstPlayerColor, String secondPlayerColor) {
    if (i == 0 && (j == 0 || j == 7)) {
      stringBuilder.append(backgroundColor + firstPlayerColor + " R " + RESET_BG_COLOR + RESET_TEXT_COLOR);
    } else if (i == 0 && (j == 1 || j == 6)) {
      stringBuilder.append(backgroundColor + firstPlayerColor + " N " + RESET_BG_COLOR + RESET_TEXT_COLOR);
    } else if (i == 0 && (j == 2 || j == 5)) {
      stringBuilder.append(backgroundColor + firstPlayerColor + " B " + RESET_BG_COLOR + RESET_TEXT_COLOR);
    } else if (i == 0 && j == 3) {
      stringBuilder.append(backgroundColor + firstPlayerColor + " K " + RESET_BG_COLOR + RESET_TEXT_COLOR);
    } else if (i == 0 && j == 4) {
      stringBuilder.append(backgroundColor + firstPlayerColor + " Q " + RESET_BG_COLOR + RESET_TEXT_COLOR);
    } else if (i == 1) {
      stringBuilder.append(backgroundColor + firstPlayerColor + " P " + RESET_BG_COLOR + RESET_TEXT_COLOR);
    } else if (i == 7 && (j == 0 || j == 7)) { /* black pieces */
      stringBuilder.append(backgroundColor + secondPlayerColor + " R " + RESET_BG_COLOR + RESET_TEXT_COLOR);
    } else if (i == 7 && (j == 1 || j == 6)) {
      stringBuilder.append(backgroundColor + secondPlayerColor + " N " + RESET_BG_COLOR + RESET_TEXT_COLOR);
    } else if (i == 7 && (j == 2 || j == 5)) {
      stringBuilder.append(backgroundColor + secondPlayerColor + " B " + RESET_BG_COLOR + RESET_TEXT_COLOR);
    } else if (i == 7 && j == 3) {
      stringBuilder.append(backgroundColor + secondPlayerColor + " K " + RESET_BG_COLOR + RESET_TEXT_COLOR);
    } else if (i == 7 && j == 4) {
      stringBuilder.append(backgroundColor + secondPlayerColor + " Q " + RESET_BG_COLOR + RESET_TEXT_COLOR);
    } else if (i == 6) {
      stringBuilder.append(backgroundColor + secondPlayerColor + " P " + RESET_BG_COLOR + RESET_TEXT_COLOR);
    } else {
      stringBuilder.append(backgroundColor + "   " + RESET_BG_COLOR);
    }

  }

  public String createGame(String... params) throws DataAccessException {
    try {
      server.createGame(new CreateGameRequest(params[0]), currentAuthToken);
      return visitorName + " created game " + params[0];
    } catch (DataAccessException e) {
      return "Invalid game creation. Please try again.";
    }
  }

  public String logOut() throws DataAccessException {
    try {
      server.logout(new LogoutRequest(visitorName), currentAuthToken);
      return visitorName + " has logged out";
    } catch (DataAccessException e) {
      return "Invalid logout. Please try again.";
    }
  }

  public String register(String... params) throws DataAccessException {
    try {
      if (params.length < 3) {
        return "Invalid registration. Please try again.";
      }
      RegisterResponse registerResponse = server.register(new RegisterRequest(params[0], params[1], params[2]));
      visitorName = params[0];
      setCurrentAuthToken(registerResponse.getAuthToken());
      return visitorName + " has registered";
    } catch (DataAccessException e) {
      return "Invalid registration. Please try again.";
    }
  }

  public String logIn(String... params) throws DataAccessException {
    try {
      if (params.length >= 2) {
        LoginResponse loginResponse = server.login(new LoginRequest(params[0], params[1]));
        state = State.SIGNEDIN;
        visitorName = params[0];
        setCurrentAuthToken(loginResponse.getAuthToken());
        return visitorName + " has logged in";
      }
    } catch (DataAccessException e) {
      return "Invalid login. Please try again.";
    }
    return "Invalid login. Please try again.";
  }

  public String listGames() throws DataAccessException {
    assertSignedIn();
    return String.valueOf(gson.toJsonTree(server.listGames(currentAuthToken).getGames()));
  }

  public String help() {
    if (state == State.SIGNEDOUT) {
      return """
                    - login <yourusername> <yourpassword>
                    - register <yourusername> <yourpassword> <youremail>
                    - help
                    - quit
                    """;
    }
    else if (state == State.GAMEPLAY) {
        return """
                        - move <from> <to>
                        - moves <from> (to see all legal moves highlighted for that piece)
                        - draw (redraws board)
                        - resign
                        - leave
                        - help
                        - quit
                        """;
    }
    return """
                - list
                - create <gamename>
                - join <gameID> <WHITE | BLACK>
                - observe <gameID>
                - logout
                - help
                - quit
                """;
  }

  private void assertSignedIn() throws DataAccessException {
    if (state == State.SIGNEDOUT) {
      throw new DataAccessException("You must sign in first!");
    }
  }
}
