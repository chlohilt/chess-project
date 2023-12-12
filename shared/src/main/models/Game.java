package models;

import chess.ChessGame;
import chessImpl.ChessGameImpl;
import database.DataAccessException;

/**
 * this class holds data about each game
 */
public class Game extends BaseClass {
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
  private ChessGameImpl chessGame;

  /**
   * this is the constructor for a game
   */
  public Game() {}

  public Game(String gameName, int gameId) {
    this.gameName = gameName;
    this.gameID = gameId;
    this.chessGame = new ChessGameImpl();
  }

  public Game(int gameID) {
    this.gameID = gameID;
  }

  public ChessGame getChessGame() {
    return chessGame;
  }

  public void setChessGame(ChessGameImpl chessGame) {
    this.chessGame=chessGame;
  }

  public String getBlackUsername() {
    return blackUsername;
  }
  public String getGameName() { return gameName; }

  public void setBlackUsername(String blackUsername) throws DataAccessException {
    this.blackUsername=blackUsername;
    getGameDataAccess().setBlackUsername(this);
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

  public void setWhiteUsername(String whiteUsername) throws DataAccessException {
    this.whiteUsername=whiteUsername;
    getGameDataAccess().setWhiteUsername(this);
  }

}
