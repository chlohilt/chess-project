package database;

import models.User;

import java.sql.Connection;
import java.sql.SQLException;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

/**
 * this class holds user data and functions to create, update, read, and delete users
 */
public class UserDAO {
  Database database = new Database();
  Connection connection;

  public UserDAO() throws DataAccessException {
    Connection conn = this.database.getDatabaseInstance().getConnection();
    this.connection = conn;
  }

  /**
   * this function creates a user
   * @param u - user to be created
   * @throws database.DataAccessException - throws if it cannot create a user
   */
  public void createUser (User u) throws DataAccessException {
    try (var preparedStatement = connection.prepareStatement("INSERT INTO user_data (username, password, email) VALUES(?, ?, ?)", RETURN_GENERATED_KEYS)) {
      preparedStatement.setString(1, u.getUsername());
      preparedStatement.setString(2, u.getPassword());
      preparedStatement.setString(3, u.getEmail());

      preparedStatement.executeUpdate();

    } catch (SQLException e) {
      throw new DataAccessException(e.toString());
    }
  }

  /**
   * this function returns an existing user
   * @param username - user to be found
   * @return User
   * @throws database.DataAccessException when the user isn't found
   */
  public User returnUser(String username) throws DataAccessException {
    try (var preparedStatement = connection.prepareStatement("SELECT username, password, email FROM user_data WHERE username=?")) {
      preparedStatement.setString(1, username);
      try (var rs = preparedStatement.executeQuery()) {
        while (rs.next()) {
          var password=rs.getString("password");
          var email=rs.getString("email");
          return new User(username, password, email);
        }
      }
    }  catch (SQLException e) {
      throw new DataAccessException(e.toString());
    }
    throw new DataAccessException("User does not exist");
  }

  /**
   * this function deletes a user from the database
   * @param u - user to be deleted
   * @throws database.DataAccessException when the user cannot be found
   */
  public void deleteUser(User u) throws DataAccessException {
    try (var preparedStatement = connection.prepareStatement("DELETE FROM user_data WHERE username=?")) {
      preparedStatement.setString(1, u.getUsername());
      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      throw new DataAccessException(e.toString());
    }
    throw new DataAccessException("User does not exist");
  }

  public void clearUsers() throws DataAccessException {
    try (var preparedStatement = connection.prepareStatement("TRUNCATE user_data")) {
      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      throw new DataAccessException(e.toString());
    }
  }

  public Integer getUserSize() throws DataAccessException {
    try (var preparedStatement = connection.prepareStatement("SELECT count(*) FROM user_data")) {
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
