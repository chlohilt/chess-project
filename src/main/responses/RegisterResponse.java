package responses;

/**
 * this class holds the information for a register response
 */
public class RegisterResponse extends ResponseClass {
  /**
   * this field holds the username that was passed in as part of a request
   */
  private String username;

  /**
   * this field holds the new authToken given to the user
   */
  private String authToken;

  /**
   * this is the constructor for a register response
   */
  public RegisterResponse(String username, String authToken) {
    this.username = username;
    this.authToken = authToken;
    this.setMessage(null);
  }

  public RegisterResponse(String message) {
    this.setMessage(message);
  }

  public RegisterResponse() {}

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
