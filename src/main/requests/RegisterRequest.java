package requests;

/**
 * this class holds the information for a register request
 */
public class RegisterRequest {
  /**
   * this field gives a username for a new user
   */
  private String username;
  /**
   * this field gives a password for a new user
   */
  private String password;
  /**
   * this field gives an email for a new user
   */
  private String email;

  /**
   * this is the constructor for a register request
   */
  public RegisterRequest() {}

  public RegisterRequest(String username, String password, String email) {
    this.username = username;
    this.password = password;
    this.email = email;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username=username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password=password;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email=email;
  }
}
