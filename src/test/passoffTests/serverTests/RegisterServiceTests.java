package passoffTests.serverTests;

import dataAccess.DataAccessException;
import models.User;
import org.junit.jupiter.api.*;
import requests.RegisterRequest;
import services.RegisterService;

public class RegisterServiceTests {
  private static RegisterService registerService = new RegisterService();

  @Test
  public void registerSuccess() throws DataAccessException {
    try {
      User u = new User("user", "pass", "email@byu.edu");
      RegisterRequest request = new RegisterRequest(u.getUsername(), u.getPassword(), u.getEmail());

      registerService.register(request);

      Assertions.assertEquals(registerService.getUserDataAccess().returnUser(u.getUsername()).getUsername(), u.getUsername());
      Assertions.assertEquals(registerService.getUserDataAccess().returnUser(u.getUsername()).getPassword(), u.getPassword());
      Assertions.assertEquals(registerService.getUserDataAccess().returnUser(u.getUsername()).getEmail(), u.getEmail());
    } catch (Exception e) {
      throw new DataAccessException("Register service failed to access the database");
    }
  }

  @Test
  public void registerFailure() {
    Integer originalUserDataSize = registerService.getUserDataAccess().getUserMap().size();
    RegisterRequest request = new RegisterRequest();

    registerService.register(request);

    Assertions.assertNotEquals(registerService.getUserDataAccess().getUserMap().size(), originalUserDataSize+1);
  }
}
