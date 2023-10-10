package requests;

/**
 * this class holds the information needed for a login request
 */
public class LoginRequest {
  /**
   * this is the password used to log in
   */
  private String password;
  /**
   * this field holds the username that wants to log in
   */
  private String username;

  /**
   * this is the login request constructor
   */
  public LoginRequest() {}

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password=password;
  }

  public void setUsername(String username) {
    this.username=username;
  }

  public String getUsername() {
    return username;
  }
}
