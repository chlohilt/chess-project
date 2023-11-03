package passoffTests.myServerTests;

import database.DataAccessException;
import models.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import requests.LoginRequest;
import requests.LogoutRequest;
import requests.RegisterRequest;
import responses.ResponseClass;
import services.LoginService;
import services.LogoutService;
import services.RegisterService;

public class LogoutServiceTests {
  static LogoutService logoutService = new LogoutService();
  static RegisterService registerService = new RegisterService();
  static LoginService loginService = new LoginService();
  static User u = new User("user", "pass", "email@byu.edu");

  @BeforeAll
  public static void init() {

    RegisterRequest request = new RegisterRequest(u.getUsername(), u.getPassword(), u.getEmail());
    registerService.register(request);
    LoginRequest loginRequest = new LoginRequest("user", "pass");
    loginService.login(loginRequest);
  }

  @Test
  void logoutSuccess() throws DataAccessException {
    try {
      String authToken = logoutService.getAuthDataAccess().returnAuthTokenString(u.getUsername());
      logoutService.logout(new LogoutRequest(u.getUsername()));

      Assertions.assertNull(logoutService.getAuthDataAccess().returnUsername(authToken), "Auth token should no longer be in auth data");
    } catch (Exception e) {
      throw new DataAccessException("Failure to access database");
    }
  }

  @Test
  void logoutFailure() throws DataAccessException {
    try {
      ResponseClass response = logoutService.logout(new LogoutRequest("random"));

      Assertions.assertEquals("Error: database error", response.getMessage());

    } catch (Exception e) {
      throw new DataAccessException("Failure to access database");
    }
  }
}
