package webSocketMessages.userCommands;

public class LeaveCommand extends UserGameCommand{
    CommandType commandType = CommandType.LEAVE;
    public LeaveCommand(String authToken) {
        super(authToken);
    }
}
