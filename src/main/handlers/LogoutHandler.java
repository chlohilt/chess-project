package handlers;

import com.google.gson.Gson;
import requests.LogoutRequest;
import responses.LogoutResponse;
import services.LogoutService;
import spark.Request;
import spark.Response;

import java.util.Objects;

public class LogoutHandler {
  Gson gson = new Gson();
  LogoutService logoutService = new LogoutService();
  public String handleRequest(Request logoutRequest, Response logoutResponse){
    LogoutRequest request = gson.fromJson(logoutRequest.body(), LogoutRequest.class);
    LogoutResponse result = logoutService.logout(request);
    String jsonResult = gson.toJson(result);
    if (Objects.equals(result.getMessage(), "Error: database error")) {
      logoutResponse.status(500);
    } else {
      logoutResponse.status(200);
    }

    logoutResponse.body(jsonResult);
    return jsonResult;
  }
}
