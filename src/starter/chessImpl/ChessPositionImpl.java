package chessImpl;

import chess.ChessPosition;

public class ChessPositionImpl implements ChessPosition {
  private int row;
  private int col;
  @Override
  public int getRow() {
    return row;
  }

  @Override
  public int getColumn() {
    return col;
  }
}
