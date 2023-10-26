package passoffTests.serverTests;

import models.Game;
import models.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import services.ClearService;
import services.RegisterService;
import org.junit.jupiter.api.*;

public class ClearServiceTest {
  private static ClearService clearService = new ClearService();
  @BeforeAll
  public static void init() {
    try {
      clearService.getAuthDataAccess().createAuthToken("fake");
      clearService.getUserDataAccess().createUser(new User("user", "pass", "email@byu.edu"));
      clearService.getGameDataAccess().insertGame(new Game());
    } catch (Exception ignored) {}

  }

  @Test
  public void clearData() {
    Assertions.assertFalse(clearService.getAuthDataAccess().getAuthTokenSet().isEmpty(), "DAO addition didn't work for auth");
    Assertions.assertFalse(clearService.getUserDataAccess().getUserMap().isEmpty(), "DAO addition didn't work for users");
    Assertions.assertFalse(clearService.getGameDataAccess().getGameMap().isEmpty(), "DAO addition didn't work for games");

    clearService.clear();

    Assertions.assertTrue(clearService.getAuthDataAccess().getAuthTokenSet().isEmpty(), "Clear service didn't work for auth");
    Assertions.assertTrue(clearService.getUserDataAccess().getUserMap().isEmpty(), "Clear service didn't work for users");
    Assertions.assertTrue(clearService.getGameDataAccess().getGameMap().isEmpty(), "Clear service didn't work for games");
  }
}
