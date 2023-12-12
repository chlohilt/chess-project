package webSocketMessages.userCommands;

import chess.ChessMove;
import chessImpl.ChessMoveImpl;

public class MakeMoveCommand extends UserGameCommand{

    public MakeMoveCommand(String authToken, Integer gameID, ChessMoveImpl move) {
        super(authToken);
        this.gameID = gameID;
        this.move = move;
        this.commandType = CommandType.MAKE_MOVE;
    }

    private Integer gameID;

    private ChessMoveImpl move;

    public ChessMoveImpl getMove() {
        return move;
    }

    public void setMove(ChessMoveImpl move) {
        this.move=move;
    }
    public Integer getGameID() {
        return gameID;
    }

    public void setGameID(Integer gameID) {
        this.gameID=gameID;
    }
}
