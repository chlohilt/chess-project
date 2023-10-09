package dataAccess;

import models.AuthToken;

/**
 * this class stores data for the authorization tokens and their corresponding users
 */
public class AuthDAO {

  /**
   * this function creates a new auth token for a specific user
   * @param username - username to create an auth token for
   */
  public void createAuthToken(String username) {}

  /**
   * this function gets an auth token for a specific user
   * @param username - username of the user
   * @return auth token for a specific user
   */
  public AuthToken getAuthToken(String username) {
    return null;
  }

  /**
   * this function deletes an auth token for a specific user
   * @param username - username of the user
   */
  public void deleteAuthToken(String username) {}
}
