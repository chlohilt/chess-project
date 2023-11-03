package dataAccess;

import com.google.gson.Gson;
import models.Game;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

/**
 * this class holds game data in the database
 */
public class GameDAO {
  Database database = new Database();
  Gson gson = new Gson();
  Connection connection;
  GameDAO() throws DataAccessException {
    Connection conn = this.database.getDatabaseInstance().getConnection();
    this.connection = conn;
  }
  private Map<Integer, Game> gameMap = new HashMap<>();
  /**
   * this function inserts a game into the database
   * @param game - game to insert into database
   */
  public void insertGame(Game game) throws DataAccessException {
    try (var preparedStatement = connection.prepareStatement("INSERT INTO game_data (gameID, gameInfo) VALUES(?, ?)", RETURN_GENERATED_KEYS)) {
      preparedStatement.setInt(1, game.getGameID());
      var json = new Gson().toJson(game);
      preparedStatement.setString(2, json);

      preparedStatement.executeUpdate();

    } catch (SQLException e) {
      throw new DataAccessException(e.toString());
    }
  }

  /**
   * this function finds a specific game
   * @param gameID - ID used to find a specific game
   * @return the game that was found
   */
  public Game findGame(int gameID) throws DataAccessException {
    try (var preparedStatement = connection.prepareStatement("SELECT gameInfo FROM game_data WHERE gameID = ?")) {
      preparedStatement.setInt(1, gameID);
      try (var rs = preparedStatement.executeQuery()) {
        while (rs.next()) {
          var json = rs.getString("gameInfo");
          return new Gson().fromJson(json, Game.class);
        }
      }
    } catch (SQLException e) {
      throw new DataAccessException(e.toString());
    }
    return null;
  }

  /**
   * this function sets the white username
   * @param game - gameInfo
   */
  public void setWhiteUsername(Game game) throws DataAccessException {
    try (var preparedStatement = connection.prepareStatement("UPDATE game_data SET gameInfo = ?, whiteUsername = ? WHERE gameID = ?")) {
      var gameJson = new Gson().toJson(game);
      preparedStatement.setString(1, gameJson);
      preparedStatement.setString(2, game.getWhiteUsername());
      preparedStatement.setInt(3, game.getGameID());
      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      throw new DataAccessException(e.toString());
    }
  }

  public void setBlackUsername(Game game) throws DataAccessException {
    try (var preparedStatement = connection.prepareStatement("UPDATE game_data SET gameInfo = ?, blackUsername = ? WHERE gameID = ?")) {
      var gameJson = new Gson().toJson(game);
      preparedStatement.setString(1, gameJson);
      preparedStatement.setString(2, game.getBlackUsername());
      preparedStatement.setInt(3, game.getGameID());
      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      throw new DataAccessException(e.toString());
    }
  }

  /**
   * this function finds a specific game
   * @param gameName - Game name used to find a specific game
   * @return the game that was found
   */
  public Game findGameString(String gameName) throws DataAccessException {
    try (var preparedStatement = connection.prepareStatement("SELECT gameInfo FROM game_data WHERE gameName = ?")) {
      preparedStatement.setString(1, gameName);
      try (var rs = preparedStatement.executeQuery()) {
        while (rs.next()) {
          var json = rs.getString("gameInfo");
          return new Gson().fromJson(json, Game.class);
        }
      }
    } catch (SQLException e) {
      throw new DataAccessException(e.toString());
    }
    return null;
  }

  /**
   * this function clears the database
   */
  public void clearGames() throws DataAccessException {
    try (var preparedStatement = connection.prepareStatement("TRUNCATE game_data")) {
      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      throw new DataAccessException(e.toString());
    }
  }

  public Integer getGameSize() throws DataAccessException {
    try (var preparedStatement = connection.prepareStatement("SELECT count(*) FROM game_data")) {
      try (var rs = preparedStatement.executeQuery()) {
        while (rs.next()) {
          return rs.getInt(1);
        }
      }
    } catch (SQLException e) {
      throw new DataAccessException(e.toString());
    }
    return null;
  }

  public List<Object> toList() {
    List<Object> gameList = new ArrayList<>();
    try (var preparedStatement = connection.prepareStatement("SELECT gameID, gameName, whiteUsername, blackUsername FROM game_data")) {
      try (var rs = preparedStatement.executeQuery()) {
        while (rs.next()) {
          Map<String, Object> obj = new HashMap<>();

          obj.put("gameID", rs.getInt("gameID"));
          if (rs.getString("whiteUsername") != null) {
            obj.put("whiteUsername", rs.getString("whiteUsername"));
          }
          if (rs.getString("blackUsername") != null) {
            obj.put("blackUsername", rs.getString("blackUsername"));
          }
          obj.put("gameName", rs.getString("gameName"));

          gameList.add(obj);
        }
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return gameList;
  }
}
