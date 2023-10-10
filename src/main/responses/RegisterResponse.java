package responses;

import models.AuthToken;

/**
 * this class holds the information for a register response
 */
public class RegisterResponse {
  /**
   * this field holds the message that is given to the user after attempting to register a user
   */
  private String message;

  /**
   * this field holds the username that was passed in as part of a request
   */
  private String username;

  /**
   * this field holds the new authToken given to the user
   */
  private AuthToken authToken;

  /**
   * this is the constructor for a register response
   */
  public RegisterResponse() {}

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message=message;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username=username;
  }

  public AuthToken getAuthToken() {
    return authToken;
  }

  public void setAuthToken(AuthToken authToken) {
    this.authToken=authToken;
  }
}
