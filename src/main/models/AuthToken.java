package models;

/**
 * this class creates authorization tokens and holds which user they're connected to
 */
public class AuthToken {
  /**
   * this field holds the authorization token of a user
   */
  private String authToken;

  /**
   * this field holds the username associated to an auth token
   */
  private String username;

  /**
   * this is the constructor for an authorization token
   */
  public AuthToken() {

  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username=username;
  }

  public String getAuthToken() {
    return authToken;
  }

  public void setAuthToken(String authToken) {
    this.authToken=authToken;
  }
}
