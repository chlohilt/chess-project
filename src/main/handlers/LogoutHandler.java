package handlers;

import com.google.gson.Gson;
import database.DataAccessException;
import models.BaseClass;
import requests.LogoutRequest;
import responses.ResponseClass;
import services.LogoutService;
import spark.Request;
import spark.Response;

import java.util.Objects;

public class LogoutHandler extends BaseClass {
  Gson gson = new Gson();
  LogoutService logoutService = new LogoutService();
  public String handleRequest(Request logoutRequest, Response logoutResponse){
    String authToken = logoutRequest.headers("Authorization");
    ResponseClass responseClass=new ResponseClass();
    try {
      String username=getAuthDataAccess().returnUsername(authToken);
      if (authorizationCheck(logoutRequest).getMessage() == null) {
        if (username != null) {
          responseClass = logoutService.logout(new LogoutRequest(username));
        }
      } else {
        responseClass=new ResponseClass("Error: unauthorized");
      }
    }  catch (DataAccessException e) {
      responseClass = new ResponseClass("Error: database error");
    }

    String jsonResult = gson.toJson(responseClass);
    if (Objects.equals(responseClass.getMessage(), "Error: database error")) {
      logoutResponse.status(500);
    } else if (Objects.equals(responseClass.getMessage(), "Error: unauthorized")) {
      logoutResponse.status(401);
    } else {
      logoutResponse.status(200);
    }

    logoutResponse.body(jsonResult);
    return jsonResult;
  }
}
