package handlers;

import com.google.gson.Gson;
import requests.RegisterRequest;
import responses.RegisterResponse;
import services.RegisterService;
import spark.Request;
import spark.Response;

import java.util.Objects;

public class RegisterHandler {
  Gson gson = new Gson();
  RegisterService registerService= new RegisterService();

  public RegisterHandler() {}

  public String handleRequest(Request registerRequest, Response registerResponse){
    // deserialize JSON to Request obj
    RegisterRequest request =gson.fromJson(registerRequest.body(), RegisterRequest.class);
    // call service
    RegisterResponse result = registerService.register(request);
    String jsonResult = gson.toJson(result);
    if (Objects.equals(result.getMessage(), "Error: bad request")) {
      registerResponse.status(400);
    } else if (Objects.equals(result.getMessage(), "Error: already taken")) {
      registerResponse.status(403);
    } else if (Objects.equals(result.getMessage(), "Error: database error")) {
      registerResponse.status(500);
    } else {
      registerResponse.status(200);
    }

    registerResponse.body(jsonResult);
    return jsonResult;
  }

}
