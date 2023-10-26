package handlers;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dataAccess.CommonDataAccess;
import responses.ResponseClass;
import services.ListGamesService;
import spark.Request;
import spark.Response;

import java.io.Reader;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ListGamesHandler {
  CommonDataAccess commonDataAccess = new CommonDataAccess();
  ListGamesService listGamesService = new ListGamesService();
  public ListGamesHandler() {}
  Gson gson = new Gson();
  public String handleRequest(Request req, Response res) {
    ResponseClass result = listGamesService.listGames(req);
    String objectJson ="";
    if (Objects.equals(result.getMessage(), "Error: database error")) {
      res.status(500);
    } else if (Objects.equals(result.getMessage(), "Error: unauthorized")) {
      res.status(401);
    } else {
      if (commonDataAccess.getCommonGameDAO().getGameMap().size() != 0) {
        var jsonBody = Map.of (
                "games", commonDataAccess.getCommonGameDAO().toList()
        );
        objectJson =String.valueOf(gson.toJsonTree(jsonBody));
      } else {
        objectJson =String.valueOf(new JsonObject());
      }
      res.status(200);
    }

    res.body(objectJson);
    return objectJson;
  }
}
