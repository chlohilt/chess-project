package handlers;

import com.google.gson.Gson;
import requests.CreateGameRequest;
import responses.CreateGameResponse;
import services.BaseService;
import services.CreateGameService;
import spark.Request;
import spark.Response;

import java.util.Objects;

public class CreateGameHandler extends BaseService {
  Gson gson = new Gson();
  CreateGameService createGameService = new CreateGameService();
  public String handleRequest(Request req, Response res) {
    boolean authenticated = false;
    if (authorizationCheck(req).getMessage() == null) {
      authenticated = true;
    }
    if (!authenticated) {

    }
    CreateGameRequest createGameRequest = gson.fromJson(req.body(), CreateGameRequest.class);
    CreateGameResponse createGameResponse = createGameService.createGame(createGameRequest);
    String jsonResult = gson.toJson(createGameResponse);
    if (Objects.equals(createGameResponse.getMessage(), "Error: bad request")) {
      res.status(400);
    } else if (Objects.equals(createGameResponse.getMessage(), "Error: unauthorized")) {
      res.status(401);
    } else if (Objects.equals(createGameResponse.getMessage(), "Error: database error")) {
      res.status(500);
    } else {
      res.status(200);
    }

    res.body(jsonResult);
    return jsonResult;
  }

}
