package services;

import com.google.gson.Gson;
import models.BaseClass;
import responses.ResponseClass;
import spark.Request;

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
   * @param listGamesRequest - list game request
   * @return list game response
   */
  public ResponseClass listGames(Request listGamesRequest) {
    return authorizationCheck(listGamesRequest);
  }
}
