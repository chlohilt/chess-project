package dataAccess;

import models.Game;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * this class holds game data in the database
 */
public class GameDAO {
  private Map<Integer, Game> gameMap = new HashMap<>();
  /**
   * this function inserts a game into the database
   * @param game - game to insert into database
   */
  public void insertGame(Game game) throws DataAccessException {
    try {
      gameMap.put(game.getGameID(), game);
    } catch (Exception e) {
      throw new DataAccessException("Failure adding game to database.");
    }
  }

  /**
   * this function finds a specific game
   * @param gameID - ID used to find a specific game
   * @return the game that was found
   */
  public Game findGame(int gameID) throws DataAccessException {
    try {
      return gameMap.get(gameID);
    } catch (Exception e) {
      throw new DataAccessException("Failure adding game to database.");
    }
  }

  /**
   * this function gives all the games in the database
   *
   * @return all games in the database
   */
  public Set<Integer> findAll() throws DataAccessException {
    try {
      return gameMap.keySet();
    } catch (Exception e) {
      throw new DataAccessException("Error returning all games. Please try again.");
    }
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
  public void updateGame(int gameID, String gameString){
  }

  /**
   * this function deletes a specific game
   * @param gameID - ID used to delete a specific game
   */
  public void removeGame(int gameID) throws DataAccessException{
    try  {
      gameMap.remove(gameID);
    } catch (Exception e) {
      throw new DataAccessException("Failure removing game to database. Make sure that the game exists.");
    }
  }

  /**
   * this function clears the database
   */
  public void clearGames(){
    gameMap.clear();
  }

  public Map<Integer, Game> getGameMap() {
    return gameMap;
  }
}
