package models;

/**
 * this class holds the information of a user
 */
public class User {
  /**
   * this field holds the user's username
   */
  private String username;

  /**
   * this field holds the user's username
   */
  private String password;

  /**
   * this field holds the user's email
   */
  private String email;

  /**
   * this is the constructor for a user
   */
  public User(String username, String password, String email) {
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
