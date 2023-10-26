package passoffTests.serverTests;

import chess.ChessGame;
import models.Game;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import requests.JoinGameRequest;
import responses.ResponseClass;
import services.ClearService;
import services.CreateGameService;
import services.JoinGameService;

public class JoinGameTests {
  static JoinGameService joinGameService = new JoinGameService();
  static ClearService clearService = new ClearService();

  @BeforeAll
  public static void init() {
    try {
      clearService.clear();
      Game game = new Game();
      game.setGameID(1234);
      game.setWhiteUsername("Tester");
      joinGameService.getGameDataAccess().insertGame(game);
    } catch (Exception ignored) {}
  }

  @Test
  public void joinGameSuccess() {
    JoinGameRequest joinGameRequest = new JoinGameRequest();
    joinGameRequest.setUsername("testPerson");
    joinGameRequest.setPlayerColor(ChessGame.TeamColor.BLACK);
    joinGameRequest.setGameID(1234);
    ResponseClass response = joinGameService.joinGame(joinGameRequest);

    Assertions.assertEquals("", response.getMessage());
  }

  @Test
  public void joinGameFailure() {
    JoinGameRequest joinGameRequest = new JoinGameRequest();
    joinGameRequest.setUsername("testPerson");
    joinGameRequest.setPlayerColor(ChessGame.TeamColor.WHITE);
    joinGameRequest.setGameID(1234);
    ResponseClass response = joinGameService.joinGame(joinGameRequest);

    Assertions.assertEquals("Error: already taken", response.getMessage());
  }
}
