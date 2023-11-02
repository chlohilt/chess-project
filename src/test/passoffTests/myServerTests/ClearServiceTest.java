package passoffTests.myServerTests;

import dataAccess.DataAccessException;
import dataAccess.Database;
import models.Game;
import models.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import services.ClearService;
import org.junit.jupiter.api.*;

public class ClearServiceTest {
  private static ClearService clearService = new ClearService();
  @BeforeAll
  public static void init() {
    try {
      clearService.clear();
      clearService.getAuthDataAccess().createAuthToken("fake");
      clearService.getUserDataAccess().createUser(new User("user", "pass", "email@byu.edu"));
      clearService.getGameDataAccess().insertGame(new Game());
    } catch (Exception ignored) {}

  }

  @Test
  public void clearData() throws DataAccessException {
    Assertions.assertNotEquals(0, clearService.getAuthDataAccess().getAuthTokenSize(), "DAO addition didn't work for auth");
    Assertions.assertNotEquals(0, clearService.getUserDataAccess().getUserSize(), "DAO addition didn't work for users");
    Assertions.assertFalse(clearService.getGameDataAccess().getGameMap().isEmpty(), "DAO addition didn't work for games");

    clearService.clear();

    Assertions.assertEquals(0, clearService.getAuthDataAccess().getAuthTokenSize(), "Clear service didn't work for auth");
    Assertions.assertEquals(0, clearService.getUserDataAccess().getUserSize(), "Clear service didn't work for users");
    Assertions.assertTrue(clearService.getGameDataAccess().getGameMap().isEmpty(), "Clear service didn't work for games");
  }
}
