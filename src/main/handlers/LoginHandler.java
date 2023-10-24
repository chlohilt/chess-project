package handlers;

import com.google.gson.Gson;
import requests.LoginRequest;
import responses.LoginResponse;
import services.LoginService;
import spark.Request;
import spark.Response;

import java.util.Objects;

public class LoginHandler {
  Gson gson = new Gson();
  LoginService loginService = new LoginService();
  public String handleRequest(Request loginRequest, Response loginResponse){
    LoginRequest request = gson.fromJson(loginRequest.body(), LoginRequest.class);
    LoginResponse result = loginService.login(request);
    String jsonResult = gson.toJson(result);
    if (Objects.equals(result.getMessage(), "Error: unauthorized")) {
      loginResponse.status(401);
    } else if (Objects.equals(result.getMessage(), "Error: database error")) {
      loginResponse.status(500);
    } else {
      loginResponse.status(200);
    }

    loginResponse.body(jsonResult);
    return jsonResult;
  }
}
