package webSocketMessages.serverMessages;

import chess.ChessGame;
import chess.ChessMove;

public class NotificationMessage extends ServerMessage{
  public NotificationMessage(ServerMessageType type, ChessGame.TeamColor teamColor, String username) {
    super(type);
    this.message = "Player " + username + " has joined the game as " + teamColor.toString();
  }

  public NotificationMessage(ServerMessageType type, String username, NotificationType notificationType) {
    super(type);
    switch (notificationType) {
      case RESIGN:
        this.message = "Player " + username + " has resigned";
        break;
      case CHECKMATE:
        this.message = "Player " + username + " has been checkmated";
        break;
      case LEAVE:
        this.message = "Player " + username + " has left the game";
        break;
      case CHECK:
        this.message = "Player " + username + " has been checked";
        break;
      case OBSERVE:
        this.message = "Player " + username + " has joined the game as an observer of the game";
        break;
    }
  }

  public NotificationMessage(ServerMessageType type, String username, ChessMove move) {
    super(type);
    this.message = "Player " + username + " made a move: " + move.toString();
  }

  public String message;
  public ServerMessageType type = ServerMessageType.NOTIFICATION;
  public enum NotificationType {
    RESIGN,
    CHECKMATE,
    LEAVE,
    CHECK,
    OBSERVE
  }
}
