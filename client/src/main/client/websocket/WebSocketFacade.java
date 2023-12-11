package client.websocket;

import chess.ChessGame;
import com.google.gson.Gson;
import exception.ResponseException;
import webSocketMessages.serverMessages.LoadGameMessage;
import webSocketMessages.serverMessages.NotificationMessage;
import webSocketMessages.serverMessages.ServerMessage;
import webSocketMessages.userCommands.JoinPlayerCommand;
import webSocketMessages.userCommands.UserGameCommand;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import static webSocketMessages.serverMessages.ServerMessage.ServerMessageType.NOTIFICATION;

public class WebSocketFacade extends Endpoint {
  Session session;
  NotificationHandler notificationHandler;
  Gson gson = new Gson();
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
            case ERROR -> System.out.println(message);
          }

        }
      });
    } catch (DeploymentException | IOException | URISyntaxException ex) {
      throw new ResponseException(500, ex.getMessage());
    }
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

  }
}
