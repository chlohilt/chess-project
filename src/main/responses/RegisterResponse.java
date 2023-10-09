package responses;

import models.AuthTokenModel;

public class RegisterResponse {
  private String message;
  private String username;

  private AuthTokenModel authToken;

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

  public AuthTokenModel getAuthToken() {
    return authToken;
  }

  public void setAuthToken(AuthTokenModel authToken) {
    this.authToken=authToken;
  }
}
