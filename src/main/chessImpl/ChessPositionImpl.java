package chessImpl;

import chess.ChessPosition;

import java.util.Objects;

public class ChessPositionImpl implements ChessPosition {
  private int row;
  private int col;

  public ChessPositionImpl(Integer row, Integer col) {
    this.row = row;
    this.col = col;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ChessPositionImpl that=(ChessPositionImpl) o;
    return row == that.row && col == that.col;
  }

  @Override
  public int hashCode() {
    return Objects.hash(row, col);
  }

  @Override
  public int getRow() { return row; }

  @Override
  public int getColumn() {
    return col;
  }

}
