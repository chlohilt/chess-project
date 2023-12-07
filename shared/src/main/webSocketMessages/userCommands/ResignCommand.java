package webSocketMessages.userCommands;

public class ResignCommand extends UserGameCommand{
  CommandType commandType = CommandType.RESIGN;
    public ResignCommand(String authToken) {
        super(authToken);
    }
}
