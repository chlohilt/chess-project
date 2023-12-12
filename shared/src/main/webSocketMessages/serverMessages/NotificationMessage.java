package webSocketMessages.serverMessages;

import chess.ChessGame;
import chess.ChessMove;

public class NotificationMessage extends ServerMessage{
  public NotificationMessage(ChessGame.TeamColor teamColor, String username) {
    super(ServerMessageType.NOTIFICATION);
    this.serverMessageType = ServerMessageType.NOTIFICATION;
    this.message = "Player " + username + " has joined the game as " + teamColor.toString();
  }

  public NotificationMessage(String username, NotificationType notificationType) {
    super(ServerMessageType.NOTIFICATION);
    this.serverMessageType = ServerMessageType.NOTIFICATION;
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

  public NotificationMessage(String username, ChessMove move) {
    super(ServerMessageType.NOTIFICATION);
    this.serverMessageType = ServerMessageType.NOTIFICATION;
    this.message = "Player " + username + " made a move: " + move.toString();
  }

  private String message;

  public enum NotificationType {
    RESIGN,
    CHECKMATE,
    LEAVE,
    CHECK,
    OBSERVE;
  }

    public Object getMessage() {
      return message;
    }

    public void setMessage(String message) {
      this.message=message;
    }

}
