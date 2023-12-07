package websocket;

import com.google.gson.Gson;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import webSocketMessages.serverMessages.ServerMessage;
import webSocketMessages.userCommands.UserGameCommand;

import java.io.IOException;

@WebSocket
public class WebSocketHandler {

  private final ConnectionManager connections = new ConnectionManager();

  @OnWebSocketMessage
  public void onMessage(Session session, String message) throws IOException {
    UserGameCommand userGameCommand = new Gson().fromJson(message, UserGameCommand.class); // cast to specific command type?
    String userName = userGameCommand.getAuthString();
    switch (userGameCommand.getCommandType()) {
      case JOIN_PLAYER -> joinPlayer(userName, session);
      case JOIN_OBSERVER -> joinObserver(userName);
      case MAKE_MOVE -> makeMove(userName, userGameCommand.getMove());
      case LEAVE -> connections.remove(userName);
      case RESIGN -> connections.remove(userName);
    }
  }

  private void joinPlayer(String userName, Session session) throws IOException {
    connections.add(userName, session);
    connections.broadcast(userName, new ServerMessage(ServerMessage.ServerMessageType.LOAD_GAME)); // to root client
    connections.broadcast(userName, new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION)); // to all other players in the game
  }

  private void joinObserver(String userName) throws IOException {
    connections.broadcast(userName, new ServerMessage(ServerMessage.ServerMessageType.LOAD_GAME));
  }

  private void makeMove(String userName, String move) throws IOException {
    connections.broadcast(userName, new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION));
  }
}
