package webSocketMessages.userCommands;

public class ObserverLeaveResignMessage extends UserGameCommand{
  public ObserverLeaveResignMessage(String authToken, CommandType commandType, Integer gameID) {
    super(authToken);
    this.commandType = commandType;
  }

  private Integer gameID;

  public Integer getGameID() {
    return gameID;
  }

  public void setGameID(Integer gameID) {
    this.gameID=gameID;
  }
}
