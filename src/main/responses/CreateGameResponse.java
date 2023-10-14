package responses;

public class CreateGameResponse {

  private String gameId;
  /**
   * this is the constructor for a create game response
   */
  public CreateGameResponse() {}

  public String getGameId() {
    return gameId;
  }

  public void setGameId(String gameId) {
    this.gameId=gameId;
  }
}
