package dataAccess;

import models.User;

/**
 * this class holds user data and functions to create, update, read, and delete users
 */
public class UserDAO {
  /**
   * this function creates a user
   * @param u - user to be created
   * @throws DataAccessException - throws if it cannot create a user
   */
  public void createUser(User u) throws DataAccessException {}

  /**
   * this function returns an existing user
   * @param username - user to be found
   * @return User
   * @throws DataAccessException when the user isn't found
   */
  public User returnUser(String username) throws DataAccessException {
    return null;
  }

  /**
   * this function updates a given user with a new object
   * @param username - username to be changed
   * @param u - new user object that it is updated to
   * @throws DataAccessException when the user object is invalid or the username cannot find a user
   */
  public void updateUser(String username, User u) throws DataAccessException {}

  /**
   * this function deletes a user from the database
   * @param u - user to be deleted
   * @throws DataAccessException when the user cannot be found
   */
  public void deleteUser(User u) throws DataAccessException {}
}
