package responses;

import chess.ChessGame;

public class JoinGameResponse {
  private String message;

  public JoinGameResponse() {}

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message=message;
  }
}
