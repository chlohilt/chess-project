package pregameTests;

import chess.ChessGame;
import database.DataAccessException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import requests.*;
import responses.CreateGameResponse;
import responses.LoginResponse;
import responses.RegisterResponse;
import responses.ResponseClass;
import server.ServerFacade;

public class ServerFacadeTests {
  static ServerFacade serverFacade = new ServerFacade("http://localhost:8080");
  @BeforeEach
  public void init() throws DataAccessException {
    serverFacade.clear();
    serverFacade.register(new RegisterRequest("user", "pass", "user@email.com"));
  }

  @Test
  public void testLoginSuccess() throws DataAccessException {
    LoginResponse loginResponse = serverFacade.login(new LoginRequest("user", "pass"));

    Assertions.assertNotNull(loginResponse.getAuthToken(), "Login Response came back with created auth token");

    serverFacade.logout(new LogoutRequest("user"), loginResponse.getAuthToken());
  }

  @Test
  public void testLoginFailure() {
    Assertions.assertThrows(DataAccessException.class, () -> serverFacade.login(new LoginRequest("user", "wrongpass")), "Login failed with wrong password");
  }

  @Test
  public void registerSuccess() throws DataAccessException {
    RegisterResponse registerResponse = serverFacade.register(new RegisterRequest("user2", "pass", "newemail@email.com"));
    Assertions.assertNotNull(registerResponse.getAuthToken(), "Register Response came back with auth token after creating user");
  }

  @Test
  public void registerFailure() {
    Assertions.assertThrows(DataAccessException.class, () -> serverFacade.register(new RegisterRequest()), "Register failed with no username, password, or email");
  }

  @Test
  public void logoutSuccess() throws DataAccessException {
    LoginResponse loginResponse = serverFacade.login(new LoginRequest("user", "pass"));
    ResponseClass logoutResponse = serverFacade.logout(new LogoutRequest("user"), loginResponse.getAuthToken());

    Assertions.assertNotNull(logoutResponse, "Logout Response came back with no errors");
  }

  @Test
  public void logoutFailure() throws DataAccessException {
    LoginResponse loginResponse = serverFacade.login(new LoginRequest("user", "pass"));

    Assertions.assertThrows(DataAccessException.class, () -> serverFacade.logout(new LogoutRequest("user"), "fakeAuth"), "Logout failed with wrong auth token");

    serverFacade.logout(new LogoutRequest("user"), loginResponse.getAuthToken());
  }

  @Test
  public void listGamesSuccess() throws DataAccessException {
    LoginResponse loginResponse = serverFacade.login(new LoginRequest("user", "pass"));
    serverFacade.createGame(new CreateGameRequest("game1"), loginResponse.getAuthToken());

    Assertions.assertNotNull(serverFacade.listGames(loginResponse.getAuthToken()), "List Games Response came back with no errors");

    serverFacade.logout(new LogoutRequest("user"), loginResponse.getAuthToken());
  }

  @Test
  public void listGamesFailure() throws DataAccessException {
    LoginResponse loginResponse = serverFacade.login(new LoginRequest("user", "pass"));
    serverFacade.createGame(new CreateGameRequest("game1"), loginResponse.getAuthToken());

    Assertions.assertThrows(DataAccessException.class, () -> serverFacade.listGames("fakeAuth"), "List games failed with incorrect auth token");
  }

  @Test
  public void createGameSuccess() throws DataAccessException {
    LoginResponse loginResponse = serverFacade.login(new LoginRequest("user", "pass"));
    CreateGameResponse createGameResponse = serverFacade.createGame(new CreateGameRequest("game1"), loginResponse.getAuthToken());

    Assertions.assertNotNull(createGameResponse.getGameId(), "Create Game Response came back with game ID");
  }

  @Test
  public void createGameFailure() throws DataAccessException {
    serverFacade.login(new LoginRequest("user", "pass"));

    Assertions.assertThrows(DataAccessException.class, () -> serverFacade.createGame(new CreateGameRequest("game1"), "fakeAuth"), "Create game failed with incorrect auth token");
  }

  @Test
  public void joinGameSuccess() throws DataAccessException {
    LoginResponse loginResponse = serverFacade.login(new LoginRequest("user", "pass"));
    CreateGameResponse createGameResponse = serverFacade.createGame(new CreateGameRequest("game1"), loginResponse.getAuthToken());

    Assertions.assertNotNull(serverFacade.joinGame(new JoinGameRequest("user", createGameResponse.getGameId(), ChessGame.TeamColor.BLACK), loginResponse.getAuthToken()), "Join Game Response came back with no errors");
  }

  @Test
  public void joinGameFailure() throws DataAccessException {
    LoginResponse loginResponse = serverFacade.login(new LoginRequest("user", "pass"));
    CreateGameResponse createGameResponse = serverFacade.createGame(new CreateGameRequest("game1"), loginResponse.getAuthToken());

    Assertions.assertThrows(DataAccessException.class, () -> serverFacade.joinGame(new JoinGameRequest("user", createGameResponse.getGameId(), ChessGame.TeamColor.BLACK), "fakeAuth"), "Join game failed with incorrect auth token");
  }

  @Test
  public void clearTest() throws DataAccessException {
    serverFacade.clear();
    Assertions.assertThrows(DataAccessException.class, () -> serverFacade.login(new LoginRequest("user", "pass")), "Login failed with empty database");
  }

}
