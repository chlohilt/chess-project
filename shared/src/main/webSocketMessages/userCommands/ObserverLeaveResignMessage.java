package webSocketMessages.userCommands;

public class ObserverLeaveResignMessage extends UserGameCommand{
  public ObserverLeaveResignMessage(String authToken, CommandType commandType, Integer gameID) {
    super(authToken);
  }

  private Integer gameID;
}
