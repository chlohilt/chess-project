package chessImpl;

import chess.ChessMove;
import chess.ChessPiece;
import chess.ChessPosition;

public class ChessMoveImpl implements ChessMove {
  ChessPosition startPosition;
  ChessPosition endPosition;
  ChessPiece.PieceType pieceType;
  public ChessMoveImpl() {}

  public ChessMoveImpl(ChessPosition startPosition, ChessPosition endPosition, ChessPiece.PieceType pieceType) {
    this.startPosition = startPosition;
    this.endPosition = endPosition;
    this.pieceType = pieceType;
  }
  public ChessMoveImpl(ChessPosition startPosition, ChessPosition endPosition) {
    this.startPosition = startPosition;
    this.endPosition = endPosition;
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o == null || o.getClass() != this.getClass()) {
      return false;
    }
    ChessMoveImpl m = (ChessMoveImpl) o;
    /* && m.getPromotionPiece().equals(this.getPromotionPiece())*/
    return m.getStartPosition().equals(this.getStartPosition()) && m.getEndPosition().equals(this.getEndPosition());
  }
  @Override
  public int hashCode() {
    int result = 7;
    result = 31 * result + startPosition.hashCode();
    result = 31 * result + endPosition.hashCode();

    return result;
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
