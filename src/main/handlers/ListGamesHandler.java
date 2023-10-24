package handlers;

import com.google.gson.Gson;
import responses.ResponseClass;
import services.ListGamesService;
import spark.Request;
import spark.Response;

import java.util.Objects;

public class ListGamesHandler {
  ListGamesService listGamesService = new ListGamesService();
  Gson gson = new Gson();
  public String handleRequest(Request req, Response res) {
    ResponseClass result = listGamesService.listGames(req);
    String jsonResult = gson.toJson(result);
    if (Objects.equals(result.getMessage(), "Error: database error")) {
      res.status(500);
    } else if (Objects.equals(result.getMessage(), "Error: unauthorized")) {
      res.status(401);
    } else {
      res.status(200);
    }

    res.body(jsonResult);
    return jsonResult;
  }
}
