package chessImpl;

import chess.ChessMove;
import chess.ChessPiece;
import chess.ChessPosition;

public class ChessMoveImpl implements ChessMove {
  ChessPosition startPosition;
  ChessPosition endPosition;

  ChessMoveImpl(ChessPosition startPosition, ChessPosition endPosition) {
    this.startPosition = startPosition;
    this.endPosition = endPosition;
  }
  @Override
  public ChessPosition getStartPosition() {
    return startPosition;
  }

  @Override
  public ChessPosition getEndPosition() {
    return endPosition;
  }

  @Override
  public ChessPiece.PieceType getPromotionPiece() {
    return null;
  }
}
