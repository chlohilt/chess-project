package services;

import com.google.gson.Gson;
import requests.CreateGameRequest;
import responses.CreateGameResponse;
import responses.ResponseClass;
import spark.Request;

import java.util.Random;

/**
 * this class holds a service to create a game
 */
public class CreateGameService extends BaseService {
  Gson gson = new Gson();
  /**
   * this is the constructor for a service to create a game
   */
  public CreateGameService(){}
  /**
   * this function creates a game
   * @param c - create game request
   * @return a create game response
   */
  public CreateGameResponse createGame(CreateGameRequest c) {
    try {
      if (c.getGameName() == null) {
        return new CreateGameResponse("Error: bad request");
      }
      Random random = new Random();
      int randomNumber = random.nextInt(9000 + 1) + 1000;

      getGameDataAccess().getGameMap().put(randomNumber, c.getGameName());
      return new CreateGameResponse();
    } catch (Exception e) {
      return new CreateGameResponse("Error: database error");
    }
  }
}
