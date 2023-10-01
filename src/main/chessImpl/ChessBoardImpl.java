package chessImpl;

import chess.ChessBoard;
import chess.ChessPiece;
import chess.ChessPosition;

public class ChessBoardImpl implements ChessBoard {
  ChessPositionImpl[][] board = new ChessPositionImpl[8][8];
  public ChessBoardImpl() {
    resetBoard();
  }
  @Override
  public void addPiece(ChessPosition position, ChessPiece piece) {
    board[position.getRow()][position.getColumn()].setChessPieceAtPosition((ChessPieceImpl) piece);
  }

  @Override
  public void resetBoard() {
    for (int i = 0; i < board.length; ++i) {
      for (int j = 0; j < board.length; ++j) {
        board[i][j] = new ChessPositionImpl(i, j);
      }
    }
  }

  @Override
  public ChessPieceImpl getPiece(ChessPosition position) {
    return board[position.getRow()][position.getColumn()].getChessPieceAtPosition();
  }
}
