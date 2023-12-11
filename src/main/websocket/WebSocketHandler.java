package websocket;

import chess.ChessGame;
import com.google.gson.Gson;
import database.CommonDataAccess;
import database.DataAccessException;
import models.Game;
import models.User;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import webSocketMessages.serverMessages.LoadGameMessage;
import webSocketMessages.serverMessages.NotificationMessage;
import webSocketMessages.serverMessages.ServerMessage;
import webSocketMessages.userCommands.JoinPlayerCommand;
import webSocketMessages.userCommands.MakeMoveCommand;
import webSocketMessages.userCommands.ObserverLeaveResignMessage;
import webSocketMessages.userCommands.UserGameCommand;

import java.io.IOException;
import java.util.Objects;

@WebSocket
public class WebSocketHandler {
  CommonDataAccess commonDataAccess = new CommonDataAccess();
  Gson gson = new Gson();

  private final ConnectionManager connections = new ConnectionManager();

  @OnWebSocketMessage
  public void onMessage(Session session, String message) throws IOException, DataAccessException {
    UserGameCommand userGameCommand = gson.fromJson(message, UserGameCommand.class);
    String userName = commonDataAccess.getCommonAuthDAO().returnUsername(userGameCommand.getAuthString());
    if (userName == null) {
      return;
    }
    switch (userGameCommand.getCommandType()) {
      case JOIN_PLAYER -> joinPlayer(userName, session, message);
      case JOIN_OBSERVER -> joinObserver(userName, message);
      case MAKE_MOVE -> makeMove(userName, message);
      case LEAVE -> leaveGame(userName, message);
      case RESIGN -> resignGame(userName, message);
    }
  }

  private void joinPlayer(String userName, Session session, String clientCommand) throws IOException, DataAccessException {
    JoinPlayerCommand joinPlayerCommand = gson.fromJson(clientCommand, JoinPlayerCommand.class);
    boolean valid = false;
    Game game = commonDataAccess.getCommonGameDAO().findGame(joinPlayerCommand.getGameID());
    if (game != null && ((Objects.equals(game.getWhiteUsername(), userName) && joinPlayerCommand.getPlayerColor() == ChessGame.TeamColor.WHITE) ||
                (Objects.equals(game.getBlackUsername(), userName) && joinPlayerCommand.getPlayerColor() == ChessGame.TeamColor.BLACK))) {
            valid = true;

    }

    if (valid) {
      connections.add(game.getGameID(), new Connection(game.getGameID(), userName, session));
      LoadGameMessage loadGameMessage = new LoadGameMessage(ServerMessage.ServerMessageType.LOAD_GAME, game);
      session.getRemote().sendString(gson.fromJson(loadGameMessage.toString(), String.class)); // send message to root client
      connections.broadcast(userName, new NotificationMessage(joinPlayerCommand.getPlayerColor(), userName)); // notification for all other players
    }

  }

  private void joinObserver(String userName, String clientCommand) throws IOException {
    ObserverLeaveResignMessage observerLeaveResignMessage = gson.fromJson(clientCommand, ObserverLeaveResignMessage.class);
    // connections.broadcast(userName, new NotificationMessage(ServerMessage.ServerMessageType.NOTIFICATION, observerLeaveResignMessage.getPlayerColor(), userName));
  }

  private void makeMove(String userName, String clientCommand) throws IOException {
    MakeMoveCommand makeMoveCommand = gson.fromJson(clientCommand, MakeMoveCommand.class);
  }

  private void leaveGame(String userName, String clientCommand) throws IOException {
    ObserverLeaveResignMessage observerLeaveResignMessage = gson.fromJson(clientCommand, ObserverLeaveResignMessage.class);
  }

  private void resignGame(String userName, String clientCommand) {

  }
}
