package webSocketMessages.serverMessages;

import chess.ChessGame;
import models.Game;

public class LoadGameMessage extends ServerMessage{
  public LoadGameMessage(ServerMessageType type, Game game) {
    super(type);
    this.game = game;
  }

  public LoadGameMessage(ServerMessageType type, Game game, ChessGame.TeamColor teamColor) {
    super(type);
    this.game = game;
    this.teamColor = teamColor;
  }

  private Game game;

  private ChessGame.TeamColor teamColor;

  public ChessGame.TeamColor getTeamColor() {
    return teamColor;
  }

  public void setTeamColor(ChessGame.TeamColor teamColor) {
    this.teamColor=teamColor;
  }

  public Game getGame() {
    return game;
  }

  public void setGame(Game game) {
    this.game=game;
  }
}
