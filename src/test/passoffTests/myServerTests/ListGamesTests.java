package passoffTests.myServerTests;

import dataAccess.DataAccessException;
import models.Game;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import services.ClearService;
import services.ListGamesService;

import java.util.HashMap;
import java.util.Map;

public class ListGamesTests {
  static ListGamesService listGamesService = new ListGamesService();
  static ClearService clearService = new ClearService();

  @BeforeAll
  public static void init() {
    try {
      clearService.clear();
      Game game = new Game(1234);
      game.setWhiteUsername("Tester");
      listGamesService.getGameDataAccess().insertGame(game);
      listGamesService.getGameDataAccess().insertGame(game);
    } catch (Exception ignored) {}
  }

  @Test
  public void listGamesSuccess() {
    String expectedGameList = "{\"games\":[{\"gameID\":1234,\"whiteUsername\":\"Tester\"}]}";

    Assertions.assertEquals(expectedGameList, listGamesService.listGames());
  }

  @Test
  public void listGamesFailure() throws DataAccessException {
    listGamesService.getGameDataAccess().clearGames();

    Assertions.assertThrows(NullPointerException.class, listGamesService::listGames);
  }

}
