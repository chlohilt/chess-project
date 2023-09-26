package chessImpl;

import chess.ChessPosition;

public class ChessPositionImpl implements ChessPosition {
  private int row;
  private int col;
  private ChessPieceImpl chessPieceAtPosition;
  @Override
  public int getRow() { return row; }

  @Override
  public int getColumn() {
    return col;
  }

  public ChessPieceImpl getChessPieceAtPosition() { return chessPieceAtPosition; }

  public void setChessPieceAtPosition(ChessPositionImpl chessPosition, ChessPieceImpl chessPiece) {
    chessPosition.chessPieceAtPosition = chessPiece;
  }
}
