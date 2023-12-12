package client.websocket;

import chess.ChessBoard;
import chess.ChessGame;
import chessImpl.ChessPieceImpl;
import chessImpl.ChessPositionImpl;
import com.google.gson.Gson;
import exception.ResponseException;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketError;
import webSocketMessages.serverMessages.LoadGameMessage;
import webSocketMessages.serverMessages.NotificationMessage;
import webSocketMessages.serverMessages.ServerMessage;
import webSocketMessages.userCommands.JoinPlayerCommand;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import static ui.EscapeSequences.*;


public class WebSocketFacade extends Endpoint {
  Session session;
  NotificationHandler notificationHandler;
  Gson gson = new Gson();
  private String headerFooter = "  h  g  f  e  d  c  b  a ";
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

  private void handleNotification(String serverMessage) {
    NotificationMessage notification = gson.fromJson(serverMessage, NotificationMessage.class);
    notificationHandler.notify(notification);
  }

  private void loadGame(String serverMessage) {
    LoadGameMessage loadGameMessage = gson.fromJson(serverMessage, LoadGameMessage.class);
    // draw board
    ChessBoard chessBoard = loadGameMessage.getGame().getChessGame().getBoard();
    String whiteColor = SET_TEXT_COLOR_RED;
    String blackColor = SET_TEXT_COLOR_BLUE;
    if (loadGameMessage.getTeamColor() != null && loadGameMessage.getTeamColor() == ChessGame.TeamColor.BLACK) {
      printBoard(chessBoard, blackColor, whiteColor);
    } else {
      printBoard(chessBoard, whiteColor, blackColor);
    }
  }

  private String printBoard(ChessBoard chessBoard, String firstColor, String secondColor) {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append(RESET_TEXT_COLOR + SET_BG_COLOR_DARK_GREY + headerFooter + "\n");
    for (int i = 0; i < 8; i++) {
      stringBuilder.append(SET_BG_COLOR_DARK_GREY + (i + 1) + RESET_BG_COLOR);
      for (int j = 0; j < 8; j++) {
        if ((i + j) % 2 == 0) {
          boardHelper(stringBuilder, SET_BG_COLOR_LIGHT_GREY, SET_TEXT_COLOR_BLUE, SET_TEXT_COLOR_RED, chessBoard.getPiece(new ChessPositionImpl(i,j)));
        } else {
          boardHelper(stringBuilder, SET_BG_COLOR_DARK_GREY, SET_TEXT_COLOR_BLUE, SET_TEXT_COLOR_RED, chessBoard.getPiece(new ChessPositionImpl(i,j)));
        }
      }
      stringBuilder.append(SET_BG_COLOR_DARK_GREY + (i + 1) + "\n");
    }
    stringBuilder.append(SET_BG_COLOR_DARK_GREY + headerFooter + SET_TEXT_COLOR_WHITE + "\n\n");
    return stringBuilder.toString();
  }

  private void boardHelper(StringBuilder stringBuilder, String backgroundColor, String firstColor, String secondColor, chess.ChessPiece chessPiece) {
    switch (chessPiece.getPieceType()) {
        case PAWN -> stringBuilder.append(backgroundColor + firstColor + " P " + RESET_TEXT_COLOR);
        case ROOK -> stringBuilder.append(backgroundColor + firstColor + " R " + RESET_TEXT_COLOR);
        case KNIGHT -> stringBuilder.append(backgroundColor + firstColor + " N " + RESET_TEXT_COLOR);
        case BISHOP -> stringBuilder.append(backgroundColor + firstColor + " B " + RESET_TEXT_COLOR);
        case QUEEN -> stringBuilder.append(backgroundColor + firstColor + " Q " + RESET_TEXT_COLOR);
        case KING -> stringBuilder.append(backgroundColor + firstColor + " K " + RESET_TEXT_COLOR);
        default -> stringBuilder.append(backgroundColor + "   " + RESET_TEXT_COLOR);
    }
  }

  private void error(String serverMessage) {
    System.out.println(serverMessage);
  }
}
