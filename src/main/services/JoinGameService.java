package services;

import chess.ChessGame;
import models.BaseClass;
import models.Game;
import requests.JoinGameRequest;
import responses.ResponseClass;

/**
 * this class holds the service to join a game
 */
public class JoinGameService extends BaseClass {
  /**
   * this is the constructor for a service to join a game
   */
  public JoinGameService() {}
  /**
   * this function joins a game given a request
   * @param r - a request to join a game
   * @return JoinGameResponse to see if the game was successfully joined
   */
  public ResponseClass joinGame(JoinGameRequest r) {
    try {
      if (r.getGameID() == null || getGameDataAccess().findGame(r.getGameID()) == null) {
        return new ResponseClass("Error: bad request");
      }
      ChessGame.TeamColor teamColor = r.getPlayerColor();
      Game gameToJoin = getGameDataAccess().findGame(r.getGameID());
      if ((gameToJoin.getBlackUsername() != null && teamColor == ChessGame.TeamColor.BLACK)
      || (gameToJoin.getWhiteUsername() != null && teamColor == ChessGame.TeamColor.WHITE)) {
        return new ResponseClass("Error: already taken");
      }

      if (teamColor == ChessGame.TeamColor.BLACK) {
        gameToJoin.setBlackUsername(r.getUsername());
      } else if (teamColor == ChessGame.TeamColor.WHITE) {
        gameToJoin.setWhiteUsername(r.getUsername());
      }
      // watcher logic would be here

      return new ResponseClass("");
    } catch (Exception e) {
      return new ResponseClass("Error: database error");
    }

  }
}
