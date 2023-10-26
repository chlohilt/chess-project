package handlers;

import com.google.gson.Gson;
import models.BaseClass;
import requests.JoinGameRequest;
import responses.ResponseClass;
import services.JoinGameService;
import spark.Request;
import spark.Response;

import java.util.Objects;

public class JoinGameHandler extends BaseClass {
  Gson gson = new Gson();
  JoinGameService joinGameService = new JoinGameService();
  public String handleRequest(Request req, Response res) {
    ResponseClass joinGameResponse = new ResponseClass();
    boolean authenticated =authorizationCheck(req).getMessage() == null;
    if (!authenticated) {
      res.status(401);
      joinGameResponse.setMessage("Error: unauthorized");
      return gson.toJson(joinGameResponse);
    }

    JoinGameRequest joinGameRequest = gson.fromJson(req.body(), JoinGameRequest.class);
    joinGameRequest.setUsername(returnUsernameFromAuthToken(req));
    joinGameResponse = joinGameService.joinGame(joinGameRequest);
    String jsonResult = gson.toJson(joinGameResponse);

    if (Objects.equals(joinGameResponse.getMessage(), "Error: bad request")) {
      res.status(400);
    } else if (Objects.equals(joinGameResponse.getMessage(), "Error: already taken")) {
      res.status(403);
    }
    else if (Objects.equals(joinGameResponse.getMessage(), "Error: database error")) {
      res.status(500);
    } else {
      res.status(200);
    }

    res.body(jsonResult);
    return jsonResult;
  }
}
