package webSocketMessages.userCommands;

public class JoinPlayerCommand extends UserGameCommand {
  CommandType commandType = CommandType.JOIN_PLAYER;
  public JoinPlayerCommand(String authToken) {
    super(authToken);
  }
}
