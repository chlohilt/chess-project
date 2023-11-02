package passoffTests.myServerTests;

import models.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import passoffTests.testClasses.TestModels;
import requests.LoginRequest;
import requests.RegisterRequest;
import services.LoginService;
import services.RegisterService;

public class LoginServiceTests {
  private static TestModels.TestCreateRequest createRequest;
  private static LoginService loginService = new LoginService();
  private static RegisterService registerService = new RegisterService();
  @BeforeAll
  public static void init() {
    try {
      User u = new User("user", "pass", "email@byu.edu");
      RegisterRequest request = new RegisterRequest(u.getUsername(), u.getPassword(), u.getEmail());

      registerService.register(request);
    } catch (Exception ignored) {}

  }

  @Test
  public void loginSuccess() {
    int originalAuthDataSize = loginService.getAuthDataAccess().getAuthTokenSet().size();
    LoginRequest loginRequest = new LoginRequest("user", "pass");
    loginService.login(loginRequest);

    Assertions.assertEquals(loginService.getAuthDataAccess().getAuthTokenSet().size(), originalAuthDataSize+1, "User data was added");
  }

  @Test
  public void loginFailure() {
    int originalAuthDataSize = loginService.getAuthDataAccess().getAuthTokenSet().size();
    LoginRequest loginRequest = new LoginRequest("user", "pass1");
    loginService.login(loginRequest);

    Assertions.assertEquals(loginService.getAuthDataAccess().getAuthTokenSet().size(), originalAuthDataSize, "User data was not added");
  }
}