package services;

import com.google.gson.Gson;
import responses.ResponseClass;
import spark.Request;

import java.io.Reader;
import java.util.List;
import java.util.Map;

/**
 * this class gives a list of all the games
 */
public class ListGamesService extends BaseService {
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
    ResponseClass response = authorizationCheck(listGamesRequest);
    return response;
  }
}
