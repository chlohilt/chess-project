package chessImpl;

import chess.*;

import java.util.Collection;
import java.util.Iterator;

public class ChessGameImpl implements ChessGame {
  TeamColor teamTurn;
  ChessBoardImpl chessBoard;
  @Override
  public TeamColor getTeamTurn() {
    return teamTurn;
  }

  @Override
  public void setTeamTurn(TeamColor team) {
    teamTurn = team;
  }

  @Override
  public Collection<ChessMove> validMoves(ChessPosition startPosition) {
    if (chessBoard.getPiece(startPosition) == null) {
      return null;
    } else {
      return chessBoard.getPiece(startPosition).pieceMoves(chessBoard, startPosition);
    }
  }

  @Override
  public void makeMove(ChessMove move) throws InvalidMoveException {
    try {
      if (chessBoard.getPiece(move.getStartPosition()).getTeamColor() != teamTurn) {
        throw new InvalidMoveException();
      }
      Collection<ChessMove> possibleMoves = this.validMoves(move.getStartPosition());
      boolean found = false;

      for (ChessMove moveCheck : possibleMoves) {
        if (moveCheck.getEndPosition().equals(move.getEndPosition())) {
          found = true;
          break;
        }
      }

      if (found) {
        chessBoard.makeMove(move);
      } else {
        throw new InvalidMoveException();
      }
    } catch (InvalidMoveException invalidMoveException) {
      throw new InvalidMoveException();
    }

  }

  @Override
  public boolean isInCheck(TeamColor teamColor) {
    return false;
  }

  @Override
  public boolean isInCheckmate(TeamColor teamColor) {
    return false;
  }

  @Override
  public boolean isInStalemate(TeamColor teamColor) {
    return false;
  }

  @Override
  public void setBoard(ChessBoard board) {
    this.chessBoard =(ChessBoardImpl) board;
  }

  @Override
  public ChessBoard getBoard() {
    return this.chessBoard;
  }
}
