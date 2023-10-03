package chessImpl;

import chess.ChessMove;
import chess.ChessPiece;
import chess.ChessPosition;

import java.util.Objects;

public class ChessMoveImpl implements ChessMove {
  ChessPosition startPosition;
  ChessPosition endPosition;
  ChessPiece.PieceType pieceType;

  public ChessMoveImpl(ChessPosition startPosition, ChessPosition endPosition, ChessPiece.PieceType pieceType) {
    this.startPosition = startPosition;
    this.endPosition = endPosition;
    this.pieceType = pieceType;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ChessMoveImpl chessMove=(ChessMoveImpl) o;
    return Objects.equals(startPosition, chessMove.startPosition) && Objects.equals(endPosition, chessMove.endPosition) && pieceType == chessMove.pieceType;
  }

  @Override
  public int hashCode() {
    return Objects.hash(startPosition, endPosition, pieceType);
  }

  public ChessMoveImpl(ChessPosition startPosition, ChessPosition endPosition) {
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
