package handlers;

import com.google.gson.Gson;
import requests.RegisterRequest;
import responses.RegisterResponse;
import spark.Request;
import spark.Response;
import spark.Spark;

import java.util.Map;

public class RegisterHandler {
  RegisterHandler() {}

  private void run() {
    Spark.port(8080);
    Spark.post("/user", this::echoBody);
  }

  private Object echoBody(Request req, Response res) {
    var bodyObj = getBody(req, Map.class);

    res.type("application/json");
    return new Gson().toJson(bodyObj);
  }

  private static <T> T getBody(Request request, Class<T> clazz) {
    var body = new Gson().fromJson(request.body(), clazz);
    if (body == null) {
      throw new RuntimeException("missing required body");
    }
    return body;
  }
  public void newHandler() {
    // Must be done before mapping routes
    Spark.staticFiles.location("/public");
    RegisterRequest registerRequest = (RegisterRequest)gson.fromJson(reqData, RegisterRequest.class);
  }

  public void handleRequest(RegisterRequest registerRequest, RegisterResponse registerResponse){

  }

  public RegisterHandler getInstance() {
    return this;
  }
}
