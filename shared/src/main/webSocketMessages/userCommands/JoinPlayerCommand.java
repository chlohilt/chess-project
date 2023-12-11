package webSocketMessages.userCommands;

import chess.ChessGame;

public class JoinPlayerCommand extends UserGameCommand {
  public JoinPlayerCommand(String authToken, Integer gameID, ChessGame.TeamColor playerColor) {
    super(authToken);
    this.gameID = gameID;
    this.playerColor = playerColor;
  }

  private Integer gameID;

  private ChessGame.TeamColor playerColor;

  public Integer getGameID() {
    return gameID;
  }

  public void setGameID(Integer gameID) {
    this.gameID=gameID;
  }

  public ChessGame.TeamColor getPlayerColor() {
    return playerColor;
  }

  public void setPlayerColor(ChessGame.TeamColor playerColor) {
    this.playerColor=playerColor;
  }
}
