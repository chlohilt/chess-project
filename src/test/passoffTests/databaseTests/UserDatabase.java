package passoffTests.databaseTests;

import database.DataAccessException;
import database.UserDAO;
import models.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class UserDatabase {
  static UserDAO userDAO;

  static {
    try {
      userDAO=new UserDAO();
    } catch (DataAccessException e) {
      throw new RuntimeException(e);
    }
  }

  String testUsername = "testUser";
  User newUser = new User(testUsername, "testPass", "test@gmail.com");

  @BeforeAll
  static void init() throws DataAccessException {
    userDAO.clearUsers();
  }

  @Test
  void createUserTestSuccess() throws DataAccessException {
    userDAO.clearUsers();
    userDAO.createUser(newUser);

    Assertions.assertEquals(newUser.getUsername(), userDAO.returnUser(testUsername).getUsername(), "Successful user creation");
  }

  @Test
  void createUserTestFailure() throws DataAccessException {
    User u = new User("u", "uPass", "uPass@gmail.com");
    userDAO.createUser(u);

    Assertions.assertThrows(DataAccessException.class, () -> userDAO.createUser(u));
  }

  @Test
  void returnUserTestSuccess() throws DataAccessException {
    User anotherUser = new User("user", "userPass", "userPass@gmail.com");
    userDAO.createUser(anotherUser);
    User returnUser = userDAO.returnUser(anotherUser.getUsername());

    Assertions.assertEquals(anotherUser.getUsername(), returnUser.getUsername());
    Assertions.assertEquals(anotherUser.getPassword(), returnUser.getPassword());
    Assertions.assertEquals(anotherUser.getEmail(), returnUser.getEmail());
  }

  @Test
  void returnUserTestFailure() throws DataAccessException {

    Assertions.assertNull(userDAO.returnUser(null));
  }

  @Test
  void deleteUserTestSuccess() throws DataAccessException {
    userDAO.deleteUser(newUser);

    Assertions.assertNull(userDAO.returnUser(newUser.getUsername()));
  }

  @Test
  void clearUsersTestSuccess() throws DataAccessException {
    userDAO.clearUsers();

    Assertions.assertEquals(0, userDAO.getUserSize());
  }

  @Test
  void userSizeTestSuccess() throws DataAccessException {
    userDAO.clearUsers();

    Assertions.assertEquals(0, userDAO.getUserSize());

    User testUser = new User("testUserr", "moreTest", "test@gamil.com");

    userDAO.createUser(newUser);
    userDAO.createUser(testUser);

    Assertions.assertEquals(2, userDAO.getUserSize());
  }
}
