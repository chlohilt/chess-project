package services;

import chessImpl.ChessBoardImpl;
import chessImpl.ChessGameImpl;
import models.BaseClass;
import models.Game;
import requests.CreateGameRequest;
import responses.CreateGameResponse;

import java.util.Random;

/**
 * this class holds a service to create a game
 */
public class CreateGameService extends BaseClass {
  /**
   * this is the constructor for a service to create a game
   */
  public CreateGameService(){}
  /**
   * this function creates a game
   * @param c - create game request
   * @return a create game response
   */
  public CreateGameResponse createGame(CreateGameRequest c) {
    try {
      if (c.getGameName() == null) {
        return new CreateGameResponse("Error: bad request");
      }
      Random random = new Random();
      int randomNumber = random.nextInt(9000 + 1) + 1000;
      Game newGame = new Game(c.getGameName(), randomNumber);
      newGame.setChessGame(new ChessGameImpl());
      newGame.getChessGame().setBoard(new ChessBoardImpl());
      newGame.getChessGame().getBoard().resetBoard();
      getGameDataAccess().insertGame(newGame);
      return new CreateGameResponse(randomNumber);
    } catch (Exception e) {
      return new CreateGameResponse("Error: database error");
    }
  }
}
