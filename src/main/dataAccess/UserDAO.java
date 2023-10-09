package dataAccess;

import models.UserModel;

public class UserDAO {
  void createUser(UserModel u) throws DataAccessException {}

  UserModel returnUser(String username) throws DataAccessException {
    return null;
  }

  void updateUser(String username, UserModel u) throws DataAccessException {}

  void deleteUser(UserModel u) throws DataAccessException {}
}
