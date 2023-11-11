package server;

import com.google.gson.Gson;
import database.DataAccessException;
import models.ModelSerializer;
import requests.CreateGameRequest;
import requests.JoinGameRequest;
import requests.LogoutRequest;
import requests.RegisterRequest;
import responses.CreateGameResponse;
import responses.LoginResponse;
import responses.RegisterResponse;
import responses.ResponseClass;
import spark.Request;

import java.io.*;
import java.net.*;

public class ServerFacade {

  private final String serverUrl;

  public ServerFacade(String url) {
    serverUrl=url;
  }

  String gamePath = "/game";

  public void clear() throws DataAccessException {
    var path = "/db";
    this.makeRequest("DELETE", path, null, null);
  }

  public LoginResponse login(Request request) throws DataAccessException {
    var path = "/session";
    return this.makeRequest("POST", path, request, LoginResponse.class);
  }

  public RegisterResponse register(RegisterRequest request) throws DataAccessException {
    var path = "/user";
    return this.makeRequest("POST", path, request, RegisterResponse.class);
  }

  public ResponseClass logout(LogoutRequest request) throws DataAccessException {
    var path = "/session";
    return this.makeRequest("DELETE", path, request, ResponseClass.class);
  }

  public String listGames(Request request) throws DataAccessException {
      return this.makeRequest("GET", gamePath, request, String.class);
  }

  public CreateGameResponse createGame(CreateGameRequest request) throws DataAccessException {
      return this.makeRequest("POST", gamePath, request, null);
  }

  public ResponseClass joinGame(JoinGameRequest request) throws DataAccessException {
      return this.makeRequest("PUT", gamePath, request, null);
  }

  private <T> T makeRequest(String method, String path, Object request, Class<T> responseClass) throws DataAccessException {
    try {
      URL url = (new URI(serverUrl + path)).toURL();
      HttpURLConnection http = (HttpURLConnection) url.openConnection();
      http.setRequestMethod(method);
      http.setDoOutput(true);

      writeBody(request, http);
      http.connect();
      throwIfNotSuccessful(http);
      return (T) readBody(http, responseClass);
    } catch (Exception ex) {
      throw new DataAccessException(ex.getMessage());
    }
  }
  private static void writeBody(Object request, HttpURLConnection http) throws IOException {
    if (request != null) {
      http.addRequestProperty("Content-Type", "application/json");
      String reqData = new Gson().toJson(request);
      try (OutputStream reqBody = http.getOutputStream()) {
        reqBody.write(reqData.getBytes());
      }
    }
  }

  private void throwIfNotSuccessful(HttpURLConnection http) throws IOException, DataAccessException {
    var status = http.getResponseCode();
    if (!isSuccessful(status)) {
      throw new DataAccessException("failure: " + status);
    }
  }

  private static <T> T readBody(HttpURLConnection http, Class<T> responseClass) throws IOException {
    T response = null;
    if (http.getContentLength() < 0) {
      try (InputStream respBody = http.getInputStream()) {
        InputStreamReader reader = new InputStreamReader(respBody);
        if (responseClass != null) {
          response = ModelSerializer.deserialize(reader, responseClass);
        }
      }
    }
    return response;
  }


  private boolean isSuccessful(int status) {
    return status / 100 == 2;
  }
}
