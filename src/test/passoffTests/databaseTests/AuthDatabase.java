package passoffTests.databaseTests;

import database.AuthDAO;
import database.DataAccessException;
import models.AuthToken;
import models.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AuthDatabase {
  User testUser = new User("testUser", "testUserPass", "testUser@gmail.com");
  static AuthDAO authDAO;

  static {
    try {
      authDAO=new AuthDAO();
    } catch (DataAccessException e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  void createAuthTestSuccess() throws DataAccessException {
    String authTokenString = authDAO.createAuthToken(testUser.getUsername());

    Assertions.assertNotNull(authTokenString);
  }

  @Test
  void createAuthTestFailure() throws DataAccessException {
    Assertions.assertThrows(DataAccessException.class, () -> authDAO.createAuthToken(null));
  }

  @Test
  void returnAuthStringTestSuccess() throws DataAccessException {
    authDAO.createAuthToken(testUser.getUsername());

    Assertions.assertNotNull(authDAO.returnAuthTokenString(testUser.getUsername()));
  }

  @Test
  void returnAuthStringTestFailure() throws DataAccessException {
    Assertions.assertThrows(DataAccessException.class, () -> authDAO.returnAuthTokenString(""));
  }

  @Test
  void returnAuthTokenTestSuccess() throws DataAccessException {
    authDAO.createAuthToken(testUser.getUsername());

    Assertions.assertNotNull(authDAO.returnAuthToken(testUser.getUsername()));
  }

  @Test
  void returnAuthTokenTestFailure() throws DataAccessException {
    Assertions.assertThrows(DataAccessException.class, () -> authDAO.returnAuthToken(""));
  }

  @Test
  void returnUsernameTestSuccess() throws DataAccessException {
    authDAO.createAuthToken(testUser.getUsername());
    String authTokenString = authDAO.returnAuthTokenString(testUser.getUsername());
    Assertions.assertEquals(testUser.getUsername(), authDAO.returnUsername(authTokenString));
  }

  @Test
  void returnUsernameTestFailure() throws DataAccessException {
    Assertions.assertNull(authDAO.returnUsername(null));
  }

  @Test
  void deleteAuthTokenTestSuccess() throws DataAccessException {
    authDAO.createAuthToken(testUser.getUsername());
    authDAO.deleteAuthToken(testUser.getUsername());

    Assertions.assertThrows(DataAccessException.class, () -> authDAO.returnAuthToken(testUser.getUsername()));
  }

  @Test
  void clearAuthToken() throws DataAccessException {
    authDAO.clearAuthTokens();

    Assertions.assertEquals(0, authDAO.getAuthTokenSize());
  }

  @Test
  void authDAOTestSize() throws DataAccessException {
    authDAO.clearAuthTokens();

    Assertions.assertEquals(0, authDAO.getAuthTokenSize());

    authDAO.createAuthToken("newUsername");
    authDAO.createAuthToken("username");

    Assertions.assertEquals(2, authDAO.getAuthTokenSize());
  }
}
