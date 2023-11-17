package server;

import com.google.gson.Gson;
import database.DataAccessException;
import models.ModelSerializer;
import requests.*;
import responses.*;

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
    this.makeRequest("DELETE", path, null, null, null);
  }

  public LoginResponse login(LoginRequest request) throws DataAccessException {
    var path = "/session";
    return this.makeRequest("POST", path, request, LoginResponse.class, null);
  }

  public RegisterResponse register(RegisterRequest request) throws DataAccessException {
    var path = "/user";
    return this.makeRequest("POST", path, request, RegisterResponse.class, null);
  }

  public ResponseClass logout(LogoutRequest request, String currentAuthToken) throws DataAccessException {
    var path = "/session";
    return this.makeRequest("DELETE", path, request, ResponseClass.class, currentAuthToken);
  }

  public ListGamesResponse listGames(String currentAuthToken) throws DataAccessException {
      return this.makeRequest("GET", gamePath, null, ListGamesResponse.class, currentAuthToken);
  }

  public CreateGameResponse createGame(CreateGameRequest request, String currentAuthToken) throws DataAccessException {
      return this.makeRequest("POST", gamePath, request, null, currentAuthToken);
  }

  public ResponseClass joinGame(JoinGameRequest request, String currentAuthToken) throws DataAccessException {
      return this.makeRequest("PUT", gamePath, request, null, currentAuthToken);
  }

  private <T> T makeRequest(String method, String path, Object request, Class<T> responseClass, String authToken) throws DataAccessException {
    try {
      URL url = (new URI(serverUrl + path)).toURL();
      HttpURLConnection http = (HttpURLConnection) url.openConnection();
      http.setRequestMethod(method);
      http.setDoOutput(true);

      writeBody(request, http, authToken);

      http.connect();
      throwIfNotSuccessful(http);
      return readBody(http, responseClass);
    } catch (Exception ex) {
      throw new DataAccessException(ex.getMessage());
    }
  }
  private static void writeBody(Object request, HttpURLConnection http, String authToken) throws IOException {
    if (authToken != null) {
      http.addRequestProperty("Authorization", authToken);
    }
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
