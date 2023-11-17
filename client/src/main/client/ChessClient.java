package client;

import chess.ChessGame;
import com.google.gson.Gson;
import requests.*;
import database.DataAccessException;
import responses.LoginResponse;
import responses.RegisterResponse;
import server.ServerFacade;

import java.util.Arrays;
import java.util.Objects;

public class ChessClient {
  Gson gson = new Gson();
  private String visitorName = null;
  private String currentAuthToken = null;
  private final ServerFacade server;
  private final String serverUrl;
  private State state = State.SIGNEDOUT;

  public ChessClient(String serverUrl) {
    server = new ServerFacade(serverUrl);
    this.serverUrl = serverUrl;
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
        case "login" -> signIn(params);
        case "register" -> register(params);
        case "logout" -> logOut();
        case "create" -> createGame(params);
        case "join", "observe" -> joinGame(params);
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
        if (params.length > 1) {return visitorName + " joined game " + params[0] + " as " + params[1] + ".";}
        else {return visitorName + " joined game " + params[0];}
      } catch (DataAccessException e) {
        throw new DataAccessException(e.getMessage());
      }

  }

  public String createGame(String... params) throws DataAccessException {
    server.createGame(new CreateGameRequest(params[0]), currentAuthToken);
    return visitorName + " created game " + params[0] + ".";
  }

  public String logOut() throws DataAccessException {
    server.logout(new LogoutRequest(visitorName), currentAuthToken);
    return visitorName + " logged out.";
  }

  public String register(String... params) throws DataAccessException {
    RegisterResponse registerResponse = server.register(new RegisterRequest(params[0], params[1], params[2]));
    visitorName = params[0];
    setCurrentAuthToken(registerResponse.getAuthToken());
    return visitorName + " registered.";
  }

  public String signIn(String... params) throws DataAccessException {
    if (params.length >= 1) {
      state = State.SIGNEDIN;
      LoginResponse loginResponse = server.login(new LoginRequest(params[0], params[1]));
      visitorName = params[0];
      setCurrentAuthToken(loginResponse.getAuthToken());
      return String.format("You signed in as %s.", visitorName);
    }
    throw new DataAccessException("Expected: <yourname>");
  }

  public String listGames() throws DataAccessException {
    assertSignedIn();
    return String.valueOf(gson.toJsonTree(server.listGames(currentAuthToken).getGameList()));
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
      throw new DataAccessException("You must sign in first.");
    }
  }
}
