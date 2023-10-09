package responses;

import models.AuthToken;

public class RegisterResponse {
  private String message;
  private String username;

  private AuthToken authToken;

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
