package responses;

import java.util.Map;

public class ListGamesResponse extends ResponseClass {
  public ListGamesResponse(String message) {
    super(message);
  }
  private Map gameList;
  public Map getGameList() {
    return gameList;
  }

  public void setGameList(Map gameList) {
    this.gameList=gameList;
  }
}
