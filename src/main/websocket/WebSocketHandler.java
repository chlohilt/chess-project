package websocket;

import chess.ChessGame;
import com.google.gson.Gson;
import database.CommonDataAccess;
import database.DataAccessException;
import models.User;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import webSocketMessages.serverMessages.LoadGameMessage;
import webSocketMessages.serverMessages.NotificationMessage;
import webSocketMessages.serverMessages.ServerMessage;
import webSocketMessages.userCommands.JoinPlayerCommand;
import webSocketMessages.userCommands.UserGameCommand;

import java.io.IOException;

@WebSocket
public class WebSocketHandler {
  CommonDataAccess commonDataAccess = new CommonDataAccess();
  Gson gson = new Gson();

  private final ConnectionManager connections = new ConnectionManager();

  @OnWebSocketMessage
  public void onMessage(Session session, String message) throws IOException, DataAccessException {
    UserGameCommand userGameCommand = gson.fromJson(message, UserGameCommand.class); // cast to specific command type?
    String userName = commonDataAccess.getCommonAuthDAO().returnUsername(userGameCommand.getAuthString());
    switch (userGameCommand.getCommandType()) {
      case JOIN_PLAYER -> joinPlayer(userName, session, message);
      case JOIN_OBSERVER -> joinObserver(userName);
      case MAKE_MOVE -> makeMove();
      case LEAVE -> connections.remove(userName);
      case RESIGN -> connections.remove(userName);
    }
  }

  private void joinPlayer(String userName, Session session, String clientCommand) throws IOException {
    connections.add(userName, session);
    JoinPlayerCommand joinPlayerCommand = gson.fromJson(clientCommand, JoinPlayerCommand.class); // cast to specific command type? & create load game message for root client
    // TODO: send load game message back to root client
    LoadGameMessage loadGameMessage = new LoadGameMessage();
    connections.broadcast(userName, new NotificationMessage(ServerMessage.ServerMessageType.NOTIFICATION, joinPlayerCommand.getPlayerColor(), userName)); // notification for all other players
  }

  private void joinObserver(String userName) throws IOException {
    connections.broadcast(userName, new ServerMessage(ServerMessage.ServerMessageType.LOAD_GAME));
  }

  private void makeMove(String userName, String move) throws IOException {
    connections.broadcast(userName, new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION));
  }
}
