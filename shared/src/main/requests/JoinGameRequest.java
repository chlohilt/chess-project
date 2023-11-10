package requests;

import chess.ChessGame;

/**
 * this class holds information needed to join a chess game
 */
public class JoinGameRequest {
  /**
   * this field holds the color that the user prefers to be put as
   */
  private ChessGame.TeamColor playerColor;

  /**
   * this field holds the game ID that the user wants to join
   */
  private Integer gameID;
  private String username;

  public ChessGame.TeamColor getPlayerColor() {
    return playerColor;
  }

  public void setPlayerColor(ChessGame.TeamColor playerColor) {
    this.playerColor=playerColor;
  }

  /**
   * this is the constructor for a join game request
   */
  public JoinGameRequest() {}

  public JoinGameRequest(String username, Integer gameID, ChessGame.TeamColor playerColor) {
    this.playerColor=playerColor;
    this.gameID=gameID;
    this.username=username;
  }


  public Integer getGameID() {
    return gameID;
  }

  public void setGameID(Integer gameID) {
    this.gameID=gameID;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username=username;
  }

}
