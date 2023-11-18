package services;

import com.google.gson.Gson;
import database.DataAccessException;
import helper.GamesWrapper;
import models.BaseClass;
import models.Game;
import responses.ListGamesResponse;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.List;
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
  public ListGamesResponse listGames() throws DataAccessException {
    List<Object> gamesList = getGameDataAccess().toList(); // Assuming you have a method to get a list of games

    // Create a ListGamesResponse object with the list of games
    ListGamesResponse response = new ListGamesResponse(gamesList);

    // Use Gson library to convert the response object to a JSON string with the "games" field
    String jsonBody = new Gson().toJson(new GamesWrapper(response));

    return response;
  }
}
