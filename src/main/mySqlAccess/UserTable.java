package mySqlAccess;

import dataAccess.DataAccessException;

import java.sql.Connection;
import java.sql.SQLException;
import models.User;
import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class UserTable {
  Connection connection;

  UserTable(Connection connection) {
    this.connection =connection;
  }

  /**
   * this function creates a user
   * @param u - user to be created
   * @throws DataAccessException - throws if it cannot create a user
   */
  public void createUser2(Connection conn, User u) throws DataAccessException {
    try (var preparedStatement = conn.prepareStatement("INSERT INTO user_data (username, password, email) VALUES(?, ?, ?)", RETURN_GENERATED_KEYS)) {
      preparedStatement.setString(1, u.getUsername());
      preparedStatement.setString(2, u.getPassword());
      preparedStatement.setString(3, u.getEmail());

      preparedStatement.executeUpdate();

    } catch (SQLException e) {
      throw new DataAccessException(e.toString());
    }
  }

  /**
   * this function deletes a user from the database
   * @param u - user to be deleted
   * @throws DataAccessException when the user cannot be found
   */
  public void deleteUser2(Connection conn, User u) throws DataAccessException {
    try (var preparedStatement = conn.prepareStatement("DELETE FROM user_data WHERE username=?")) {
      preparedStatement.setString(1, u.getUsername());
      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      throw new DataAccessException(e.toString());
    }
  }

  public void clearUsers2(Connection conn) throws DataAccessException {
    try (var preparedStatement = conn.prepareStatement("TRUNCATE user_data")) {

      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      throw new DataAccessException(e.toString());
    }
  }

  /**
   * this function returns an existing user
   * @param username - user to be found
   * @return User
   * @throws DataAccessException when the user isn't found
   */

  public User returnUser2(Connection conn, String username) throws DataAccessException {
    try (var preparedStatement = conn.prepareStatement("SELECT username, password, email FROM user_data WHERE username=?")) {
      preparedStatement.setString(1, username);
      try (var rs = preparedStatement.executeQuery()) {
        var password = rs.getString("password");
        var email = rs.getString("email");
        return new User(username, password, email);
      }
    }  catch (SQLException e) {
      throw new DataAccessException(e.toString());
    }
  }

  public Integer getUserDataSize(Connection conn) throws DataAccessException {
    try (var preparedStatement = conn.prepareStatement("SELECT count(*) FROM user_data")) {
      try (var rs = preparedStatement.executeQuery()) {
         return rs.getInt(1);
      }
    } catch (SQLException e) {
      throw new DataAccessException(e.toString());
    }
  }
}
