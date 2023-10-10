package responses;

/**
 * this class holds the information for a join game response
 */
public class JoinGameResponse {
  /**
   * this field holds the message given after success/failure
   */
  private String message;

  /**
   * this is the constructor for a join game response
   */
  public JoinGameResponse() {}

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message=message;
  }
}
