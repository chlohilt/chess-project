package dataAccess;

import models.AuthToken;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * this class stores data for the authorization tokens and their corresponding users
 */
public class AuthDAO {
  private Map<String, AuthToken> authTokenMap = new HashMap<>();

  /**
   * this function creates a new auth token for a specific user
   * @param username - username to create an auth token for
   */
  public void createAuthToken(String username) throws DataAccessException {
    try {
      AuthToken newAuthToken = new AuthToken();
      newAuthToken.setAuthToken(UUID.randomUUID().toString());
      newAuthToken.setUsername(username);
      authTokenMap.put(username, newAuthToken);
    } catch (Exception e) {
      throw new DataAccessException("Failed to create auth token for that user.");
    }
  }

  /**
   * this function gets an auth token for a specific user
   * @param username - username of the user
   * @return auth token for a specific user
   */
  public String returnAuthToken(String username) throws DataAccessException {
    try {
      return authTokenMap.get(username).getAuthToken();
    } catch (Exception e) {
      throw new DataAccessException("Failed to get auth token for that user.");
    }
  }

  /**
   * this function deletes an auth token for a specific user
   * @param username - username of the user
   */
  public void deleteAuthToken(String username) throws DataAccessException {
    try {
      authTokenMap.remove(username);
    } catch (Exception e) {
      throw new DataAccessException("Failed to delete that auth token.");
    }
  }
}
