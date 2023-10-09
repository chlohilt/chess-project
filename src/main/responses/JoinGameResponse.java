package responses;

import chess.ChessGame;

public class JoinGameResponse {

  private ChessGame.TeamColor teamColor;
  private String gameID;

  public JoinGameResponse() {}

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
}
