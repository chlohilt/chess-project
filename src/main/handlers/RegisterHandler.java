package handlers;

import com.google.gson.Gson;
import requests.RegisterRequest;
import responses.RegisterResponse;
import services.RegisterService;
import spark.Request;
import spark.Response;

public class RegisterHandler {
  Gson gson = new Gson();
  RegisterService service = new RegisterService();

  public RegisterHandler() {}

  public String handleRequest(Request registerRequest, Response registerResponse){
    // deserialize JSON to Request obj
    RegisterRequest request = (RegisterRequest) gson.fromJson(registerRequest.body(), RegisterRequest.class);
    // call service
    RegisterResponse result = service.register(request);
    String jsonResult = gson.toJson(result);
    if (result.getMessage() == "Error: bad request") {
      registerResponse.status(400);
    } else if (result.getMessage() == "Error: already taken") {
      registerResponse.status(403);
    } else if (result.getMessage() == "Error: database error") {
      registerResponse.status(500);
    } else {
      registerResponse.status(200);
    }

    registerResponse.body(jsonResult);
    return jsonResult;
  }

}
