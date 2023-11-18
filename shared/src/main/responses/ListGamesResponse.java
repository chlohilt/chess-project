package responses;

import models.Game;

import java.util.List;

public class ListGamesResponse extends ResponseClass {
  public ListGamesResponse(String message) {
    super(message);
  }

  public ListGamesResponse(List<Object> games) {
    this.games = games;
  }
  private List<Object> games;
  public List<Object> getGames() {
    return games;
  }

  public void setGames(List<Object> games) {
    this.games=games;
  }
}
