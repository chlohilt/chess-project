package dataAccess;

import models.Game;

import java.util.Collection;

/**
 * this class holds game data in the database
 */
public class GameDAO {
  /**
   * this function inserts a game into the database
   * @param game - game to insert into database
   */
  public void insertGame(Game game) {}

  /**
   * this function finds a specific game
   * @param gameID - ID used to find a specific game
   * @return the game that was found
   */
  public Game findGame(int gameID) {
    return null;
  }

  /**
   * this function gives all the games in the database
   * @return all games in the database
   */
  public Collection<Game> findAll() {
    return null;
  }

  /**
   * this game claims a spot in a game for a specific user
   * @param username - username of user who wants to claim a spot in a game
   */
  public  void claimSpot(String username) {}

  /**
   * this function updates a specific game
   * @param gameID - ID used to update a specific game
   */
  public void updateGame(int gameID){}

  /**
   * this function deletes a specific game
   * @param gameID - ID used to delete a specific game
   */
  public void removeGame(int gameID){}

  /**
   * this function clears the database
   */
  public void clear(){}
}
