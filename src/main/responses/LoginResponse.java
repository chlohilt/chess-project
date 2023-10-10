package responses;

/**
 * this class holds the information for a login response
 */
public class LoginResponse {
  /**
   * this field holds the message outputted based on the success/failure
   */
  private String message;
  /**
   * this field holds the authToken used to log in
   */
  private String authToken;
  /**
   * this field holds the username that was used to login
   */
  private String username;

  /**
   * this is the login response constructor
   */
  public LoginResponse() {}

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message=message;
  }

  public String getAuthToken() {
    return authToken;
  }

  public void setAuthToken(String authToken) {
    this.authToken=authToken;
  }

  public void setUsername(String username) {
    this.username=username;
  }

  public String getUsername() {
    return username;
  }
}
