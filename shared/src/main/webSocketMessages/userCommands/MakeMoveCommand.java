package webSocketMessages.userCommands;

public class MakeMoveCommand extends UserGameCommand{
    CommandType commandType = CommandType.MAKE_MOVE;
    private int x;
    private int y;

    public MakeMoveCommand(String authToken, int x, int y) {
        super(authToken);
        this.x = x;
        this.y = y;
    }

    public int getX() { return x; }
    public int getY() { return y; }
}
