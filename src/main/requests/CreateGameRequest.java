package requests;

/**
 * this class holds the create game request
 */
public class CreateGameRequest {
  private String gameName;
  /**
   * this is the constructor for a create game request
   */
  public CreateGameRequest(){}

  public String getGameName() {
    return gameName;
  }

  public void setGameName(String gameName) {
    this.gameName=gameName;
  }

}
