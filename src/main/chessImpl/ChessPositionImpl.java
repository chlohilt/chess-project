package chessImpl;

import chess.ChessPosition;

public class ChessPositionImpl implements ChessPosition {
  private int row;
  private int col;
  private ChessPieceImpl chessPieceAtPosition = new ChessPieceImpl();

  public ChessPositionImpl(Integer row, Integer col) {
    this.row = row;
    this.col = col;
  }

  @Override
  public int hashCode() {
    int result = 7;
    result = 31 * result + row;
    result = 31 * result + col;
    return result;
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o == null || o.getClass() != this.getClass()) {
      return false;
    }

    ChessPositionImpl c = (ChessPositionImpl) o;
    return c.getColumn() == this.getColumn() && c.getRow() == this.getRow() && c.getChessPieceAtPosition().equals(this.getChessPieceAtPosition());
  }
  @Override
  public int getRow() { return row; }

  @Override
  public int getColumn() {
    return col;
  }

  public ChessPieceImpl getChessPieceAtPosition() { return chessPieceAtPosition; }

  public void setChessPieceAtPosition(ChessPieceImpl chessPiece) {
    this.chessPieceAtPosition = chessPiece;
  }
}
