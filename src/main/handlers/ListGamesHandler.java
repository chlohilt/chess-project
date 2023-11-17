package handlers;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import database.DataAccessException;
import models.BaseClass;
import requests.JoinGameRequest;
import responses.ResponseClass;
import services.ListGamesService;
import spark.Request;
import spark.Response;

import java.util.Objects;

public class ListGamesHandler extends BaseClass {
  ListGamesService listGamesService = new ListGamesService();
  Gson gson = new Gson();
  public ListGamesHandler() {}
  public String handleRequest(Request req, Response res) {
    ResponseClass result = authorizationCheck(req);
    String objectJson ="";
    if (Objects.equals(result.getMessage(), "Error: database error")) {
      res.status(500);
    } else if (Objects.equals(result.getMessage(), "Error: unauthorized")) {
      res.status(401);
      objectJson =String.valueOf(new JsonObject());
      res.body(objectJson);
      return objectJson;
    } else {
      try {
        ResponseClass listGamesResponse = listGamesService.listGames();

        if (Objects.equals(listGamesResponse.getMessage(), "Error: bad request")) {
          res.status(400);
        } else if (Objects.equals(listGamesResponse.getMessage(), "Error: already taken")) {
          res.status(403);
        } else if (Objects.equals(listGamesResponse.getMessage(), "Error: database error")) {
          res.status(500);
        } else {
          res.status(200);
          objectJson = listGamesResponse.getGameList();
        }
      } catch (DataAccessException e) {
        res.status(500);
      }
    }

    res.body(objectJson);
    return objectJson;
  }
}
