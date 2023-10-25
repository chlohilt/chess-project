package dataAccess;

import models.AuthToken;

import java.util.*;

/**
 * this class stores data for the authorization tokens and their corresponding users
 */
public class AuthDAO {
  private Set<AuthToken> authTokenSet = new HashSet<AuthToken>();

  /**
   * this function creates a new auth token for a specific user
   * @param username - username to create an auth token for
   */
  public String createAuthToken(String username) throws DataAccessException {
    try {
      AuthToken newAuthToken = new AuthToken();
      newAuthToken.setAuthToken(UUID.randomUUID().toString());
      newAuthToken.setUsername(username);
      authTokenSet.add(newAuthToken);
      return newAuthToken.getAuthToken();
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
      for (AuthToken checkAuthToken : authTokenSet) {
        if (checkAuthToken.getUsername().equals(username)) {
          return checkAuthToken.getAuthToken();
        }
      }
      return null;
    } catch (Exception e) {
      throw new DataAccessException("Failed to get auth token for that user.");
    }
  }

  public String returnUsername(String authToken) throws DataAccessException {
    try {
      for (AuthToken checkAuthToken : authTokenSet) {
        if (checkAuthToken.getAuthToken().equals(authToken)) {
          return checkAuthToken.getUsername();
        }
      }
      return null;
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
      authTokenSet.remove(returnAuthToken(username));
    } catch (Exception e) {
      throw new DataAccessException("Failed to delete that auth token.");
    }
  }

  public void clearAuthTokens() {
    authTokenSet.clear();
  }

  public Set<AuthToken> getAuthTokenSet() {
    return authTokenSet;
  }
}
