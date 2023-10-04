package chessImpl;

import chess.*;

public class ChessBoardImpl implements ChessBoard {
  ChessPiece[][] board = new ChessPiece[8][8];
  @Override
  public void addPiece(ChessPosition position, ChessPiece piece) {
    board[position.getRow() - 1][position.getColumn() - 1] = piece;
  }

  public void removePiece(ChessPosition position) {
    board[position.getRow() - 1][position.getColumn() - 1] = null;
  }

  @Override
  public void resetBoard() {
    for (int i = 0; i < board.length; ++i) {
      for (int j = 0; j < board.length; ++j) {
        if (i == 1) { board[i][j] = new ChessPieceImpl(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN); } //PAWNS
        else if (i == 6) { board[i][j] = new ChessPieceImpl(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN); }
        else if (i == 0 && (j == 0 || j == 7)) { board[i][j] = new ChessPieceImpl(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.ROOK); } // ROOKS
        else if (i == 7 && (j == 0 || j == 7)) { board[i][j] = new ChessPieceImpl(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK); }
        else if (i == 0 && (j == 1 || j == 6)) { board[i][j] = new ChessPieceImpl(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KNIGHT); } // KNIGHTS
        else if (i == 7 && (j == 1 || j == 6)) { board[i][j] = new ChessPieceImpl(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KNIGHT); }
        else if (i == 0 && (j == 2 || j == 5)) { board[i][j] = new ChessPieceImpl(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.BISHOP); } // BISHOPS
        else if (i == 7 && (j == 2 || j == 5)) { board[i][j] = new ChessPieceImpl(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.BISHOP); }
        else if (i == 0 && j == 3) { board[i][j] = new ChessPieceImpl(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.QUEEN); } // QUEENS
        else if (i == 7 && j == 3) { board[i][j] = new ChessPieceImpl(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.QUEEN); }
        else if (i == 0 && j == 4) { board[i][j] = new ChessPieceImpl(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KING); } // KINGS
        else if (i == 7 && j == 4) { board[i][j] = new ChessPieceImpl(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KING); }
        else { board[i][j] = null; }
      }
    }
  }

  @Override
  public ChessPiece getPiece(ChessPosition position) {
    return board[position.getRow() - 1][position.getColumn() - 1];
  }

  public void makeMove(ChessMove move) {
    ChessPiece piece = this.getPiece(move.getStartPosition());
    this.removePiece(move.getStartPosition());
    this.addPiece(move.getEndPosition(), piece);
  }

}
