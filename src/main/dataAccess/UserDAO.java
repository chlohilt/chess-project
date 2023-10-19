package dataAccess;

import models.User;

import java.util.Map;

/**
 * this class holds user data and functions to create, update, read, and delete users
 */
public class UserDAO {
  private Map<String, User> userMap;

  /**
   * this function creates a user
   * @param u - user to be created
   * @throws DataAccessException - throws if it cannot create a user
   */
  public void createUser(User u) throws DataAccessException {
    try {
      userMap.put(u.getUsername(), u);
    } catch (Exception e) {
      throw new DataAccessException("Failure adding user to database.");
    }
  }

  /**
   * this function returns an existing user
   * @param username - user to be found
   * @return User
   * @throws DataAccessException when the user isn't found
   */
  public User returnUser(String username) throws DataAccessException {
    try {
      return userMap.get(username);
    }  catch (Exception e) {
      throw new DataAccessException("That user does not exist in the database. Please try again with an existing user.");
    }

  }

  /**
   * this function updates a given user with a new object
   * @param username - username to be changed
   * @param u - new user object that it is updated to
   * @throws DataAccessException when the user object is invalid or the username cannot find a user
   */
  public void updateUser(String username, User u) throws DataAccessException {
    try {
      userMap.put(username, u);
    }  catch (Exception e) {
      throw new DataAccessException("That user does not exist in the database. Please try again with an existing user.");
    }
  }

  /**
   * this function deletes a user from the database
   * @param u - user to be deleted
   * @throws DataAccessException when the user cannot be found
   */
  public void deleteUser(User u) throws DataAccessException {
    try {
      userMap.remove(u.getUsername());
    } catch (Exception e) {
      throw new DataAccessException("That user does not exist in the database. Please try again with an existing user.");
    }
  }
}
