package models;

import chess.ChessGame;

/**
 * this class holds data about each game
 */
public class Game {
  /**
   * this field holds the game's unique ID
   */
  private int gameID;

  /**
   * this field hold's the game's white username
   */
  private String whiteUsername;

  /**
   * this field holds the game's black username
   */
  private String blackUsername;

  /**
   * this field holds the game's name
   */
  private String gameName;

  /**
   * this field holds the actual game object used for game play
   */
  private ChessGame game;

  /**
   * this is the constructor for a game
   */
  public Game() {}

  public Game(String gameName, int gameId) {
    this.gameName = gameName;
    this.gameID = gameId;
  }

  public ChessGame getGame() {
    return game;
  }

  public void setGame(ChessGame game) {
    this.game=game;
  }

  public String getGameName() {
    return gameName;
  }

  public void setGameName(String gameName) {
    this.gameName=gameName;
  }
  public String getBlackUsername() {
    return blackUsername;
  }

  public void setBlackUsername(String blackUsername) {
    this.blackUsername=blackUsername;
  }

  public int getGameID() {
    return gameID;
  }

  public void setGameID(int gameID) {
    this.gameID=gameID;
  }

  public String getWhiteUsername() {
    return whiteUsername;
  }

  public void setWhiteUsername(String whiteUsername) {
    this.whiteUsername=whiteUsername;
  }

}
