package passoffTests.myServerTests;

import models.Game;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import requests.CreateGameRequest;
import responses.CreateGameResponse;
import services.ClearService;
import services.CreateGameService;

import java.util.Map;
import java.util.Objects;

public class CreateGameTests {
  static CreateGameService createGameService = new CreateGameService();
  static ClearService clearService = new ClearService();

  @BeforeAll
  public static void init() {
    clearService.clear();
  }
  @Test
  public void createGameSuccess() {
    CreateGameRequest createGameRequest = new CreateGameRequest();
    createGameRequest.setGameName("testGame");
    createGameService.createGame(createGameRequest);

    boolean found = false;
    for (Map.Entry<Integer, Game> gameEntry: createGameService.getGameDataAccess().getGameMap().entrySet()) {
      if (Objects.equals(gameEntry.getValue().getGameName(), "testGame")) {
        found = true;
      }
    }

    Assertions.assertTrue(found, "New game should be found");
  }

  @Test
  public void createGameFailure() {
    CreateGameRequest createGameRequest = new CreateGameRequest();
    createGameRequest.setGameName(null);
    CreateGameResponse createGameResponse = createGameService.createGame(createGameRequest);

    Assertions.assertEquals("Error: bad request", createGameResponse.getMessage());
  }
}
