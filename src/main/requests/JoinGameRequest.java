package requests;

import chess.ChessGame;

public class JoinGameRequest {
  private ChessGame.TeamColor teamColor;
  private String gameID;

  private String message;

  public JoinGameRequest() {}

  public ChessGame.TeamColor getTeamColor() {
    return teamColor;
  }

  public void setTeamColor(ChessGame.TeamColor teamColor) {
    this.teamColor=teamColor;
  }

  public String getGameID() {
    return gameID;
  }

  public void setGameID(String gameID) {
    this.gameID=gameID;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message=message;
  }
}
