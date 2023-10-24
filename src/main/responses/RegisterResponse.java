package responses;

import models.AuthToken;

/**
 * this class holds the information for a register response
 */
public class RegisterResponse implements ResponseInterface {
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
  public RegisterResponse(String username, AuthToken authToken) {
    this.username = username;
    this.authToken = authToken;
  }

  public RegisterResponse() {

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

  @Override
  public String getMessage() {
    return null;
  }

  @Override
  public void setMessage(String message) {

  }
}
