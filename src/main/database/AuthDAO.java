package database;

import com.google.gson.Gson;
import models.AuthToken;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

/**
 * this class stores data for the authorization tokens and their corresponding users
 */
public class AuthDAO {
  Database database = new Database();
  Connection connection;
  AuthDAO() throws DataAccessException {
    Connection conn = this.database.getDatabaseInstance().getConnection();
    this.connection = conn;
  }

  /**
   * this function creates a new auth token for a specific user
   * @param username - username to create an auth token for
   */
  public String createAuthToken(String username) throws DataAccessException {
    AuthToken newAuthToken = new AuthToken();
    newAuthToken.setAuthToken(UUID.randomUUID().toString());
    newAuthToken.setUsername(username);
    try (var preparedStatement = connection.prepareStatement("INSERT INTO auth_data (authToken, authTokenString, username) VALUES(?, ?, ?)", RETURN_GENERATED_KEYS)) {
      var json = new Gson().toJson(newAuthToken);
      preparedStatement.setString(1, json);
      preparedStatement.setString(2, newAuthToken.getAuthToken());
      preparedStatement.setString(3, username);
      preparedStatement.executeUpdate();
      return newAuthToken.getAuthToken();

    } catch (SQLException e) {
      throw new DataAccessException(e.toString());
    }
  }

  /**
   * this function gets an auth token for a specific user
   * @param username - username of the user
   * @return auth token for a specific user
   */
  public String returnAuthTokenString(String username) throws DataAccessException {
    try (var preparedStatement = connection.prepareStatement("SELECT authTokenString FROM auth_data WHERE username=?")) {
      preparedStatement.setString(1, username);
      try (var rs = preparedStatement.executeQuery()) {
        while (rs.next()) {
          return rs.getString("authTokenString");
        }
      }
    }  catch (SQLException e) {
      throw new DataAccessException(e.toString());
    }
    return null;
  }

  public AuthToken returnAuthToken(String username) throws DataAccessException {
    try (var preparedStatement = connection.prepareStatement("SELECT authToken FROM auth_data WHERE username=?")) {
      preparedStatement.setString(1, username);
      try (var rs = preparedStatement.executeQuery()) {
        while (rs.next()) {
          var json = rs.getString("authToken");
          return new Gson().fromJson(json, AuthToken.class);
        }
      }
    }  catch (SQLException e) {
      throw new DataAccessException(e.toString());
    }
    return null;
  }

  public String returnUsername(String authTokenString) throws DataAccessException {
    try (var preparedStatement = connection.prepareStatement("SELECT username FROM auth_data WHERE authTokenString=?")) {
      preparedStatement.setString(1, authTokenString);
      try (var rs = preparedStatement.executeQuery()) {
        while (rs.next()) {
          return rs.getString("username");
        }
      }
    }  catch (SQLException e) {
      throw new DataAccessException(e.toString());
    }
    return null;
  }

  /**
   * this function deletes an auth token for a specific user
   * @param username - username of the user
   */
  public void deleteAuthToken(String username) throws DataAccessException {
    try (var preparedStatement = connection.prepareStatement("DELETE FROM auth_data WHERE username=?")) {
      preparedStatement.setString(1, username);
      preparedStatement.executeUpdate();
    }  catch (SQLException e) {
      throw new DataAccessException(e.toString());
    }
  }

  public void clearAuthTokens() throws DataAccessException {
    try (var preparedStatement = connection.prepareStatement("TRUNCATE auth_data")) {
      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      throw new DataAccessException(e.toString());
    }
  }

  public Integer getAuthTokenSize() throws DataAccessException {
    try (var preparedStatement = connection.prepareStatement("SELECT count(*) FROM auth_data")) {
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
}
