package dataAccess;

import models.User;

public class UserDAO {
  public void createUser(User u) throws DataAccessException {}

  public User returnUser(String username) throws DataAccessException {
    return null;
  }

  public void updateUser(String username, User u) throws DataAccessException {}

  public void deleteUser(User u) throws DataAccessException {}
}
