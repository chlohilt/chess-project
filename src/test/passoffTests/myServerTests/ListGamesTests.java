package passoffTests.myServerTests;

import database.DataAccessException;
import database.Database;
import models.Game;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import services.ClearService;
import services.ListGamesService;

import java.sql.SQLException;

public class ListGamesTests {
  static ListGamesService listGamesService = new ListGamesService();
  static ClearService clearService = new ClearService();
  static Database database = new Database();

  @BeforeAll
  public static void init() {
    try {
      clearService.clear();
      Game game = new Game("coolGame", 1234);
      listGamesService.getGameDataAccess().insertGame(game);
      game.setWhiteUsername("Tester");
    } catch (Exception ignored) {}
  }

  @Test
  void listGamesSuccess() throws DataAccessException {
    String expectedGameList = "{games=[{gameID=1234, whiteUsername=Tester, gameName=coolGame}]}";

    Assertions.assertEquals(expectedGameList, listGamesService.listGames().getGameList().toString());
  }

  @Test
  void listGamesFailure() throws DataAccessException, SQLException {
    try (var preparedStatement = database.getConnection().prepareStatement("DROP TABLE game_data")) {
      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      throw new DataAccessException(e.toString());
    }

    Assertions.assertThrows(DataAccessException.class, listGamesService::listGames);

    database.getConnection().setCatalog("chess");

    var createGameTable = """
            CREATE TABLE  IF NOT EXISTS game_data (
                gameID INT NOT NULL,
                gameName VARCHAR(255),
                whiteUsername VARCHAR(255),
                blackUsername VARCHAR(255),
                gameInfo longtext NOT NULL,
                PRIMARY KEY (gameID)
            )""";

    try (var createTableStatement = database.getConnection().prepareStatement(createGameTable)) {
      createTableStatement.executeUpdate();
    }
  }

}
