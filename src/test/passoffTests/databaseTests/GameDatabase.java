package passoffTests.databaseTests;

import database.DataAccessException;
import database.GameDAO;
import models.Game;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameDatabase {
  static GameDAO gameDAO;

  static {
    try {
      gameDAO=new GameDAO();
    } catch (DataAccessException e) {
      throw new RuntimeException(e);
    }
  }

  Game game = new Game("gameName", 1239);

  @BeforeEach
  void init() throws DataAccessException {
    gameDAO.clearGames();
  }

  @Test
  void insertGameTestSuccess() throws DataAccessException {
    gameDAO.insertGame(game);

    Assertions.assertEquals(game.getGameID(), gameDAO.findGame(game.getGameID()).getGameID());
  }

  @Test
  void insertGameTestFailure() throws DataAccessException {
    gameDAO.insertGame(game);

    Assertions.assertThrows(DataAccessException.class, () -> gameDAO.insertGame(game));
  }

  @Test
  void findGameTestSuccess() throws DataAccessException {
    gameDAO.insertGame(game);

    Assertions.assertEquals(game.getGameID(), gameDAO.findGame(game.getGameID()).getGameID());
  }

  @Test
  void findGameTestFailure() throws DataAccessException {
  Assertions.assertNull(gameDAO.findGame(game.getGameID()));
  }

  @Test
  void setWhiteUserTestSuccess() throws DataAccessException {
    gameDAO.insertGame(game);
    game.setWhiteUsername("whiteUsername");

    Assertions.assertEquals("whiteUsername", gameDAO.findGame(game.getGameID()).getWhiteUsername());
  }

  @Test
  void setBlackUserTestSuccess() throws DataAccessException {
    gameDAO.insertGame(game);
    game.setBlackUsername("blackUsername");

    Assertions.assertEquals("blackUsername", gameDAO.findGame(game.getGameID()).getBlackUsername());
  }

  @Test
  void gameSizeTestSuccess() throws DataAccessException {
    gameDAO.clearGames();

    Assertions.assertEquals(0, gameDAO.getGameSize());

    gameDAO.insertGame(new Game(12345));
    gameDAO.insertGame(new Game(90934));

    Assertions.assertEquals(2, gameDAO.getGameSize());

  }

  @Test
  void clearGamesTestSuccess() throws DataAccessException {
    gameDAO.clearGames();

    Assertions.assertEquals(0, gameDAO.getGameSize());
  }

  @Test
  void findGameStringTestSuccess() throws DataAccessException {
    gameDAO.insertGame(game);

    Assertions.assertNotNull(gameDAO.findGameString(game.getGameName()));
  }

  @Test
  void findGameStringTestFailure() throws DataAccessException {
    Assertions.assertNull(gameDAO.findGameString(""));
  }
  
  @Test
  void gameListSuccess() throws DataAccessException {
    gameDAO.insertGame(game);
    Object newObj = new Object();
    List<Object> gameList = new ArrayList<>();
    Map<String, Object> obj = new HashMap<>();

    obj.put("gameID", game.getGameID());
    obj.put("gameName", game.getGameName());
    gameList.add(obj);
    
    Assertions.assertEquals(gameList, gameDAO.toList());
  }

}
