package chessImpl;

import chess.ChessPosition;

public class ChessPositionImpl implements ChessPosition {
  private int row;
  private int col;
  private ChessPieceImpl chessPieceAtPosition = null;

  public ChessPositionImpl(Integer row, Integer col) {
    this.row = row;
    this.col = col;
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
