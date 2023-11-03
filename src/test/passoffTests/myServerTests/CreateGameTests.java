package passoffTests.myServerTests;

import database.DataAccessException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import requests.CreateGameRequest;
import responses.CreateGameResponse;
import services.ClearService;
import services.CreateGameService;

public class CreateGameTests {
  static CreateGameService createGameService = new CreateGameService();
  static ClearService clearService = new ClearService();

  @BeforeAll
  public static void init() {
    clearService.clear();
  }
  @Test
  void createGameSuccess() throws DataAccessException {
    CreateGameRequest createGameRequest = new CreateGameRequest();
    createGameRequest.setGameName("testGame");
    createGameService.createGame(createGameRequest);

    Assertions.assertNotNull(clearService.getGameDataAccess().findGameString("testGame"), "New game should be found");
  }

  @Test
  void createGameFailure() {
    CreateGameRequest createGameRequest = new CreateGameRequest();
    createGameRequest.setGameName(null);
    CreateGameResponse createGameResponse = createGameService.createGame(createGameRequest);

    Assertions.assertEquals("Error: bad request", createGameResponse.getMessage());
  }
}
