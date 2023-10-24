package handlers;

import com.google.gson.Gson;
import responses.LogoutResponse;
import responses.ResponseClass;
import services.LogoutService;
import spark.Request;
import spark.Response;

import java.util.Objects;

public class LogoutHandler {
  Gson gson = new Gson();
  LogoutService logoutService = new LogoutService();
  public String handleRequest(Request logoutRequest, Response logoutResponse){
    ResponseClass result = logoutService.logout(logoutRequest);
    String jsonResult = gson.toJson(result);
    if (Objects.equals(result.getMessage(), "Error: database error")) {
      logoutResponse.status(500);
    } else if (Objects.equals(result.getMessage(), "Error: unauthorized")) {
      logoutResponse.status(401);
    } else {
      logoutResponse.status(200);
    }

    logoutResponse.body(jsonResult);
    return jsonResult;
  }
}
