package handlers;

import com.google.gson.Gson;
import requests.CreateGameRequest;
import responses.CreateGameResponse;
import models.BaseClass;
import services.CreateGameService;
import spark.Request;
import spark.Response;

import java.util.Objects;

public class CreateGameHandler extends BaseClass {
  Gson gson = new Gson();
  CreateGameService createGameService = new CreateGameService();
  public String handleRequest(Request req, Response res) {
    CreateGameResponse createGameResponse = new CreateGameResponse();
    boolean authenticated =authorizationCheck(req).getMessage() == null;
    if (!authenticated) {
      res.status(401);
      createGameResponse.setMessage("Error: unauthorized");
      return gson.toJson(createGameResponse);
    }
    CreateGameRequest createGameRequest = gson.fromJson(req.body(), CreateGameRequest.class);
    createGameResponse = createGameService.createGame(createGameRequest);
    String jsonResult = gson.toJson(createGameResponse);
    if (Objects.equals(createGameResponse.getMessage(), "Error: bad request")) {
      res.status(400);
    } else if (Objects.equals(createGameResponse.getMessage(), "Error: database error")) {
      res.status(500);
    } else {
      res.status(200);
    }

    res.body(jsonResult);
    return jsonResult;
  }

}
