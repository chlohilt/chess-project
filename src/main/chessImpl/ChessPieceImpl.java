package chessImpl;

import chess.*;

import java.lang.reflect.Array;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;

public class ChessPieceImpl implements ChessPiece {
  ChessGameImpl.TeamColor teamColor;
  PieceType pieceType;
  Collection<ChessMove> possiblePieceMoves;
  @Override
  public ChessGame.TeamColor getTeamColor() {
    return teamColor;
  }

  @Override
  public PieceType getPieceType() {
    return pieceType;
  }

@Override
  public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
    PieceType myPieceType = null;
    if (board.getPiece(myPosition) != null) {
      myPieceType = board.getPiece(myPosition).getPieceType();
      switch(myPieceType) {
        case PAWN:
          return pawnPieceMoves(board, myPosition);
        case ROOK:

        case KNIGHT:
          return null;
        case BISHOP:
          return bishopPieceMoves(board, myPosition);
        case KING:
          return kingPieceMoves(board, myPosition);
        case QUEEN:
          return null;
      }
    }
    return null;
  }

  public Collection<ChessMove> bishopPieceMoves(ChessBoard board, ChessPosition myPosition) {
    Collection<ChessMove> possibleBishopMoves = new ArrayList<>();
    Integer col = myPosition.getColumn();
    Integer row =myPosition.getRow();

    //TODO: make this more efficient, needs to be diagonal both ways
    for (int i = myPosition.getRow(); i > 0; --i) {
        ChessPositionImpl checkPos = new ChessPositionImpl(i, col);
        if (board.getPiece(checkPos) == null) {
          possibleBishopMoves.add(new ChessMoveImpl(myPosition, checkPos));
        }
        col--;
    }
    for (int i = 0; i < myPosition.getColumn(); ++i) {
      ChessPositionImpl checkPos = new ChessPositionImpl(i, col);
      if (board.getPiece(checkPos) == null) {
        possibleBishopMoves.add(new ChessMoveImpl(myPosition, checkPos));
      }
      row++;
    }

    return possibleBishopMoves;
  }
  public Collection<ChessMove> kingPieceMoves(ChessBoard board, ChessPosition myPosition) {
    Integer[] possibleRows = {
            myPosition.getRow() + 1,
            myPosition.getRow() - 1
    };
    Integer[] possibleCols = {
            myPosition.getColumn() + 1,
            myPosition.getColumn() - 1
    };

    return pieceMovesHelper(board, myPosition, possibleRows, possibleCols);
  }

  public Collection<ChessMove> pawnPieceMoves(ChessBoard board, ChessPosition myPosition) {
    Integer[] possibleRows;
    Integer[] possibleCols;
    if (myPosition.getRow() == 1) { // first turn for the pawn
      possibleRows = new Integer[]{
              myPosition.getRow() + 1,
              myPosition.getRow() + 2
      };
    } else {
      possibleRows = new Integer[]{
              myPosition.getRow() + 1
      };
    }

    // check for enemies left diagonal and right diagonal
    ChessPositionImpl checkForEnemy1 = new ChessPositionImpl(myPosition.getRow() + 1, myPosition.getColumn() + 1);
    ChessPositionImpl checkForEnemy2 = new ChessPositionImpl(myPosition.getRow() + 1, myPosition.getColumn() - 1);
    if (board.getPiece(checkForEnemy1) != null) {
      possibleCols = new Integer[] {
              myPosition.getColumn(),
              myPosition.getColumn() + 1
      };
    } else if (board.getPiece(checkForEnemy2) != null) {
      possibleCols = new Integer[] {
              myPosition.getColumn(),
              myPosition.getColumn() - 1
      };
    } else {
      possibleCols = new Integer[] {
              myPosition.getColumn()
      };
    }

    return pieceMovesHelper(board, myPosition, possibleRows, possibleCols);
  }

  public Collection<ChessMove> pieceMovesHelper(ChessBoard board, ChessPosition myPosition, Integer[] possibleRows, Integer[] possibleCols) {
    Collection<ChessMove> possibleMoves = new ArrayList<>();
    for (int j = 0; j < possibleCols.length; ++j) {
      for (int i = 0; i < possibleRows.length; ++i){
        ChessPositionImpl checkPos = new ChessPositionImpl(possibleRows[i], possibleCols[j]);
        if (board.getPiece(checkPos) == null) {
          ChessMoveImpl newPossibleMove = new ChessMoveImpl(myPosition, checkPos);
          possibleMoves.add(newPossibleMove);
        }
      }
    }
    return possibleMoves;
  }
}
