package helper;

import responses.ListGamesResponse;

public class GamesWrapper {
  private ListGamesResponse games;

  public GamesWrapper(ListGamesResponse games) {
    this.games = games;
  }

  public ListGamesResponse getGames() {
    return games;
  }

  public void setGames(ListGamesResponse games) {
    this.games = games;
  }
}