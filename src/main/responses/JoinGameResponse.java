package responses;

/**
 * this class holds the information for a join game response
 */
public class JoinGameResponse implements ResponseInterface {
  /**
   * this field holds the message given after success/failure
   */
  private String message;

  /**
   * this is the constructor for a join game response
   */
  public JoinGameResponse() {}

  @Override
  public String getMessage() {
    return null;
  }

  @Override
  public void setMessage(String message) {

  }
}
