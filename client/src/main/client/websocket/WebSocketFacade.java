package client.websocket;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessMove;
import chess.ChessPiece;
import chessImpl.ChessPositionImpl;
import com.google.gson.Gson;
import exception.ResponseException;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketError;
import webSocketMessages.serverMessages.LoadGameMessage;
import webSocketMessages.serverMessages.NotificationMessage;
import webSocketMessages.serverMessages.ServerMessage;
import webSocketMessages.userCommands.JoinPlayerCommand;
import webSocketMessages.userCommands.MakeMoveCommand;
import webSocketMessages.userCommands.ObserverLeaveResignMessage;
import webSocketMessages.userCommands.UserGameCommand;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import static ui.EscapeSequences.*;


public class WebSocketFacade extends Endpoint {
  Session session;
  NotificationHandler notificationHandler;
  Gson gson = new Gson();
  private String headerFooter = "  a  b  c  d  e  f  g  h ";
  @Override
  public void onOpen(Session session, EndpointConfig endpointConfig) {
  }

  public WebSocketFacade(String url, NotificationHandler notificationHandler) throws ResponseException {
    try {
      url = url.replace("http", "ws");
      URI socketURI = new URI(url + "/connect");
      this.notificationHandler = notificationHandler;

      WebSocketContainer container = ContainerProvider.getWebSocketContainer();
      this.session = container.connectToServer(this, socketURI);

      //set message handler
      this.session.addMessageHandler(new MessageHandler.Whole<String>() {
        @Override
        public void onMessage(String message) {
          ServerMessage serverMessage = gson.fromJson(message, ServerMessage.class);
          switch (serverMessage.getServerMessageType()) {
            case NOTIFICATION -> handleNotification(message);
            case LOAD_GAME -> loadGame(message);
            case ERROR -> error(message);
          }

        }
      });
    } catch (DeploymentException | IOException | URISyntaxException ex) {
      throw new ResponseException(500, ex.getMessage());
    }
  }

  @OnWebSocketError
    public void onError(Session session, Throwable throwable) {
        System.out.println("Error: " + throwable.getMessage());
    }

  public void joinGame(String currentAuthToken, Integer gameID, ChessGame.TeamColor teamColor) throws ResponseException {
    try {
      var action = new JoinPlayerCommand(currentAuthToken, gameID, teamColor);
      this.session.getBasicRemote().sendText(gson.toJson(action));
    } catch (IOException ex) {
      throw new ResponseException(500, ex.getMessage());
    }
  }

  public void leaveGame(String currentAuthToken, Integer gameID) throws ResponseException {
    try {
      var action = new ObserverLeaveResignMessage(currentAuthToken, UserGameCommand.CommandType.LEAVE, gameID);
      this.session.getBasicRemote().sendText(gson.toJson(action));
    } catch (IOException ex) {
      throw new ResponseException(500, ex.getMessage());
    }
  }

  public void resignGame(String currentAuthToken, Integer gameID) throws ResponseException {
    try {
      var action = new ObserverLeaveResignMessage(currentAuthToken, UserGameCommand.CommandType.RESIGN, gameID);
      this.session.getBasicRemote().sendText(gson.toJson(action));
    } catch (IOException ex) {
      throw new ResponseException(500, ex.getMessage());
    }
  }

  public void makeMove(String currentAuthToken, Integer gameID, ChessMove chessMove) throws ResponseException {
    try {
      var action = new MakeMoveCommand(currentAuthToken, gameID, chessMove);
      this.session.getBasicRemote().sendText(gson.toJson(action));
    } catch (IOException ex) {
      throw new ResponseException(500, ex.getMessage());
    }
  }

  public void showValidMoves(String currentAuthToken, Integer gameID) {
  }

  private void handleNotification(String serverMessage) {
    NotificationMessage notification = gson.fromJson(serverMessage, NotificationMessage.class);
    notificationHandler.notify(notification);
  }

  private void loadGame(String serverMessage) {
    LoadGameMessage loadGameMessage = gson.fromJson(serverMessage, LoadGameMessage.class);
    // draw board
    if (loadGameMessage.getGame().getChessGame() != null) {
      ChessBoard chessBoard = loadGameMessage.getGame().getChessGame().getBoard();
      String whiteColor = SET_TEXT_COLOR_RED;
      String blackColor = SET_TEXT_COLOR_BLUE;
      if (loadGameMessage.getTeamColor() != null && loadGameMessage.getTeamColor() == ChessGame.TeamColor.BLACK) {
        printBoard(chessBoard, blackColor, whiteColor);
      } else {
        printBoard(chessBoard, whiteColor, blackColor);
      }
    }
  }

  public String printBoard(ChessBoard chessBoard, String whiteColor, String blackColor) {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append(RESET_TEXT_COLOR + SET_BG_COLOR_DARK_GREY + headerFooter + "\n");
    for (int i = 0; i < 8; i++) {
      stringBuilder.append(SET_BG_COLOR_DARK_GREY + (i + 1) + RESET_BG_COLOR);
      for (int j = 0; j < 8; j++) {
        if ((i + j) % 2 == 0) {
          boardHelper(stringBuilder, SET_BG_COLOR_LIGHT_GREY, whiteColor, blackColor, chessBoard.getPiece(new ChessPositionImpl(i + 1,j +1)));
        } else {
          boardHelper(stringBuilder, SET_BG_COLOR_DARK_GREY, whiteColor, blackColor, chessBoard.getPiece(new ChessPositionImpl(i + 1,j + 1)));
        }
      }
      stringBuilder.append(SET_BG_COLOR_DARK_GREY + (i + 1) + "\n");
    }
    stringBuilder.append(SET_BG_COLOR_DARK_GREY + headerFooter + SET_TEXT_COLOR_WHITE + "\n\n");
    return stringBuilder.toString();
  }

  private void boardHelper(StringBuilder stringBuilder, String backgroundColor, String whiteColor, String blackColor, chess.ChessPiece chessPiece) {
    if (chessPiece == null) {
      stringBuilder.append(backgroundColor + "   " + RESET_TEXT_COLOR);
      return;
    }
    ChessPiece.PieceType pieceType = chessPiece.getPieceType();
    if (pieceType == ChessPiece.PieceType.PAWN && chessPiece.getTeamColor() == ChessGame.TeamColor.WHITE) {
      stringBuilder.append(backgroundColor + whiteColor + " P " + RESET_TEXT_COLOR);
    } else if (pieceType == ChessPiece.PieceType.PAWN && chessPiece.getTeamColor() == ChessGame.TeamColor.BLACK) {
      stringBuilder.append(backgroundColor + blackColor + " P " + RESET_TEXT_COLOR);
    } else if (pieceType == ChessPiece.PieceType.ROOK && chessPiece.getTeamColor() == ChessGame.TeamColor.WHITE) {
      stringBuilder.append(backgroundColor + whiteColor + " R " + RESET_TEXT_COLOR);
    } else if (pieceType == ChessPiece.PieceType.ROOK && chessPiece.getTeamColor() == ChessGame.TeamColor.BLACK) {
      stringBuilder.append(backgroundColor + blackColor + " R " + RESET_TEXT_COLOR);
    } else if (pieceType == ChessPiece.PieceType.KNIGHT && chessPiece.getTeamColor() == ChessGame.TeamColor.WHITE) {
      stringBuilder.append(backgroundColor + whiteColor + " N " + RESET_TEXT_COLOR);
    } else if (pieceType == ChessPiece.PieceType.KNIGHT && chessPiece.getTeamColor() == ChessGame.TeamColor.BLACK) {
      stringBuilder.append(backgroundColor + blackColor + " N " + RESET_TEXT_COLOR);
    } else if (pieceType == ChessPiece.PieceType.BISHOP && chessPiece.getTeamColor() == ChessGame.TeamColor.WHITE) {
      stringBuilder.append(backgroundColor + whiteColor + " B " + RESET_TEXT_COLOR);
    } else if (pieceType == ChessPiece.PieceType.BISHOP && chessPiece.getTeamColor() == ChessGame.TeamColor.BLACK) {
      stringBuilder.append(backgroundColor + blackColor + " B " + RESET_TEXT_COLOR);
    } else if (pieceType == ChessPiece.PieceType.QUEEN && chessPiece.getTeamColor() == ChessGame.TeamColor.WHITE) {
      stringBuilder.append(backgroundColor + whiteColor + " Q " + RESET_TEXT_COLOR);
    } else if (pieceType == ChessPiece.PieceType.QUEEN && chessPiece.getTeamColor() == ChessGame.TeamColor.BLACK) {
      stringBuilder.append(backgroundColor + blackColor + " Q " + RESET_TEXT_COLOR);
    } else if (pieceType == ChessPiece.PieceType.KING && chessPiece.getTeamColor() == ChessGame.TeamColor.WHITE) {
      stringBuilder.append(backgroundColor + whiteColor + " K " + RESET_TEXT_COLOR);
    } else if (pieceType == ChessPiece.PieceType.KING && chessPiece.getTeamColor() == ChessGame.TeamColor.BLACK) {
      stringBuilder.append(backgroundColor + blackColor + " K " + RESET_TEXT_COLOR);
    }
  }

  private void error(String serverMessage) {
    System.out.println(serverMessage);
  }
}
