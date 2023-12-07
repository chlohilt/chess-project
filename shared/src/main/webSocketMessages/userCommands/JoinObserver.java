package webSocketMessages.userCommands;

public class JoinObserver extends UserGameCommand{
  CommandType commandType = CommandType.JOIN_OBSERVER;
  public JoinObserver(String authToken) {
    super(authToken);
  }
}
