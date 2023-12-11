package webSocketMessages.serverMessages;

import models.Game;

public class LoadGameMessage extends ServerMessage{
  public LoadGameMessage(ServerMessageType type, Game game) {
    super(type);
    this.game = game;
  }

  private Game game;
}
