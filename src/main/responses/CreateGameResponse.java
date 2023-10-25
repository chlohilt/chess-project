package responses;

public class CreateGameResponse extends ResponseClass {

  private String gameId;
  /**
   * this is the constructor for a create game response
   */
  public CreateGameResponse(Integer gameID) {
    this.setGameId(String.valueOf(gameID));
  }

  public CreateGameResponse(String message) {
    this.setMessage(message);
  }

  public CreateGameResponse() {}

  public String getGameId() {
    return gameId;
  }

  public void setGameId(String gameId) {
    this.gameId=gameId;
  }
}
