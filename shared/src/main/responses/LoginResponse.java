package responses;

/**
 * this class holds the information for a login response
 */
public class LoginResponse extends ResponseClass {
  /**
   * this field holds the authToken used to log in
   */
  private String authToken;
  /**
   * this field holds the username that was used to log in
   */
  private String username;

  /**
   * this is the login response constructor
   */
  public LoginResponse(String username, String authToken) {
    this.username = username;
    this.authToken = authToken;
    this.setMessage(null);
  }

  public LoginResponse(String message) {
    this.setMessage(message);
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
