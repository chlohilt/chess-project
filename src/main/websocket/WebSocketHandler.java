package websocket;

import chess.*;
import chessImpl.ChessPieceImpl;
import chessImpl.ChessPositionImpl;
import com.google.gson.Gson;
import database.CommonDataAccess;
import database.DataAccessException;
import models.Game;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import webSocketMessages.serverMessages.ErrorMessage;
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
  public void onMessage(Session session, String message) throws IOException, DataAccessException, InvalidMoveException, chessImpl.InvalidMoveException {
    UserGameCommand userGameCommand = gson.fromJson(message, UserGameCommand.class);
    String userName = commonDataAccess.getCommonAuthDAO().returnUsername(userGameCommand.getAuthString());
    if (userName == null) {
      session.getRemote().sendString(gson.toJson(new ErrorMessage(ServerMessage.ServerMessageType.ERROR, "Invalid auth token")));
    } else {
      switch (userGameCommand.getCommandType()) {
        case JOIN_PLAYER -> joinPlayer(userName, session, message);
        case JOIN_OBSERVER -> joinObserver(userName, session, message);
        case MAKE_MOVE -> makeMove(userName, session, message);
        case LEAVE -> leaveGame(userName, session, message);
        case RESIGN -> resignGame(userName, session, message);
      }
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
      session.getRemote().sendString(gson.toJson(loadGameMessage)); // send message to root client
      connections.broadcast(userName, game.getGameID(), new NotificationMessage(joinPlayerCommand.getPlayerColor(), userName)); // notification for all other players
    } else {
      session.getRemote().sendString(gson.toJson(new ErrorMessage(ServerMessage.ServerMessageType.ERROR, "Invalid join command")));
    }

  }

  private void joinObserver(String userName, Session session, String clientCommand) throws IOException, DataAccessException {
    ObserverLeaveResignMessage observerLeaveResignMessage = gson.fromJson(clientCommand, ObserverLeaveResignMessage.class);
    boolean valid = false;
    Game game = commonDataAccess.getCommonGameDAO().findGame(observerLeaveResignMessage.getGameID());
    if (game != null) {
      valid = true;
    }

    if (valid) {
      connections.add(game.getGameID(), new Connection(game.getGameID(), userName, session));
      LoadGameMessage loadGameMessage = new LoadGameMessage(ServerMessage.ServerMessageType.LOAD_GAME, game);
      session.getRemote().sendString(gson.toJson(loadGameMessage)); // send message to root client
      connections.broadcast(userName, game.getGameID(), new NotificationMessage(userName, NotificationMessage.NotificationType.OBSERVE));
    } else {
      session.getRemote().sendString(gson.toJson(new ErrorMessage(ServerMessage.ServerMessageType.ERROR, "Invalid observe command")));
    }
  }

  private void makeMove(String userName, Session session, String clientCommand) throws IOException, DataAccessException, InvalidMoveException, chessImpl.InvalidMoveException {
    MakeMoveCommand makeMoveCommand = gson.fromJson(clientCommand, MakeMoveCommand.class);
    Game game = commonDataAccess.getCommonGameDAO().findGame(makeMoveCommand.getGameID());
    String whiteUsername=null;
    String blackUsername=null;
    if (game == null) {
      session.getRemote().sendString(gson.toJson(new ErrorMessage(ServerMessage.ServerMessageType.ERROR, "Game no longer exists")));
    } else {
      whiteUsername = game.getWhiteUsername();
      blackUsername = game.getBlackUsername();
      ChessPositionImpl startPosition =(ChessPositionImpl) makeMoveCommand.getMove().getStartPosition();
      if (makeMoveCommand.getGameID() != null && makeMoveCommand.getMove() != null
              && game.getChessGame().validMoves(startPosition).contains(makeMoveCommand.getMove())
              && (game.getChessGame().getTeamTurn() == ChessGame.TeamColor.BLACK && blackUsername.equals(userName)
              || game.getChessGame().getTeamTurn() == ChessGame.TeamColor.WHITE && whiteUsername.equals(userName))) {
        game.getChessGame().makeMove(makeMoveCommand.getMove());
        commonDataAccess.getCommonGameDAO().updateGame(game);
        LoadGameMessage loadGameMessage = new LoadGameMessage(ServerMessage.ServerMessageType.LOAD_GAME, game);
        connections.broadcast(null, game.getGameID(), loadGameMessage);
        connections.broadcast(userName, game.getGameID(), new NotificationMessage(userName, makeMoveCommand.getMove()));
      } else {
        session.getRemote().sendString(gson.toJson(new ErrorMessage(ServerMessage.ServerMessageType.ERROR, "Invalid move command")));
      }
    }

  }

  private void leaveGame(String userName, Session session, String clientCommand) throws IOException, DataAccessException {
    ObserverLeaveResignMessage observerLeaveResignMessage = gson.fromJson(clientCommand, ObserverLeaveResignMessage.class);
    Game game = commonDataAccess.getCommonGameDAO().findGame(observerLeaveResignMessage.getGameID());

    if (game != null) {
      connections.remove(game.getGameID());
      if (game.getWhiteUsername().equals(userName)) {
        game.setWhiteUsername(null);
      } else if (game.getBlackUsername().equals(userName)) {
        game.setBlackUsername(null);
      }
      commonDataAccess.getCommonGameDAO().updateGame(game);
      connections.broadcast(userName, game.getGameID(), new NotificationMessage(userName, NotificationMessage.NotificationType.LEAVE));
    } else {
      session.getRemote().sendString(gson.toJson(new ErrorMessage(ServerMessage.ServerMessageType.ERROR, "Invalid leave command")));
    }
  }

  private void resignGame(String userName, Session session, String clientCommand) throws DataAccessException, IOException {
    ObserverLeaveResignMessage observerLeaveResignMessage = gson.fromJson(clientCommand, ObserverLeaveResignMessage.class);
    Game game = commonDataAccess.getCommonGameDAO().findGame(observerLeaveResignMessage.getGameID());

    if (game != null && (Objects.equals(game.getWhiteUsername(), userName) || Objects.equals(game.getBlackUsername(), userName))) {
      Integer gameID = game.getGameID();
      commonDataAccess.getCommonGameDAO().deleteGame(gameID);
      connections.broadcast(null, gameID, new NotificationMessage(userName, NotificationMessage.NotificationType.RESIGN));
      connections.remove(game.getGameID());
    } else {
      session.getRemote().sendString(gson.toJson(new ErrorMessage(ServerMessage.ServerMessageType.ERROR, "Invalid resign command")));
    }
  }
}
