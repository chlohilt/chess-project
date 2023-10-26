package responses;

public class CreateGameResponse extends ResponseClass {

  private Integer gameID;
  /**
   * this is the constructor for a creation game response
   */
  public CreateGameResponse(Integer gameID) {
    this.gameID = gameID;
  }

  public CreateGameResponse(String message) {
    this.setMessage(message);
  }

  public CreateGameResponse() {}

  public Integer getGameId() {
    return gameID;
  }

  public void setGameId(Integer gameId) {
    this.gameID=gameId;
  }
}
