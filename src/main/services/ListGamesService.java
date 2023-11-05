package services;

import com.google.gson.Gson;
import database.DataAccessException;
import models.BaseClass;

import java.util.Map;

/**
 * this class gives a list of all the games
 */
public class ListGamesService extends BaseClass {
  Gson gson = new Gson();
  /**
   * this is the constructor for a service to list all games
   */
  public ListGamesService() {}

  /**
   * this function lists all the games
   * @return list game response
   */
  public String listGames() throws DataAccessException {
    var jsonBody = Map.of (
            "games", getGameDataAccess().toList()
    );
    return String.valueOf(gson.toJsonTree(jsonBody));
  }
}
