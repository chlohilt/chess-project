package responses;

/**
 * This interface is to have the message in every response
 */
public class ResponseClass {
  private String gameList;
  public ResponseClass(String message) {
    this.message = message;
  }

  public ResponseClass() {
    this.message = null;
  }
  /**
   * this field holds the message given after success/failure
   */
  private String message;
  public String getMessage() {
    return this.message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
  public String getGameList() {
    return gameList;
  }

  public void setGameList(String gameList) {
    this.gameList=gameList;
  }

}
