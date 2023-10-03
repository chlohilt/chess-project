package chessImpl;

import chess.*;

import java.lang.reflect.Array;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

public class ChessPieceImpl implements ChessPiece {
  ChessGameImpl.TeamColor teamColor;
  PieceType pieceType;
  Collection<ChessMove> possiblePieceMoves;
  public ChessPieceImpl(ChessGame.TeamColor teamColor, PieceType pieceType) {
    this.teamColor = teamColor;
    this.pieceType = pieceType;

  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj == null || obj.getClass() != this.getClass()) {
      return false;
    }
    ChessPieceImpl c = (ChessPieceImpl) obj;
    return c.getPieceType().equals(this.getPieceType()) && c.getTeamColor().equals(this.getTeamColor());
  }

  @Override
  public int hashCode() {
    int result = 7;
    result = 31 * result + this.getTeamColor().hashCode();
    result = 31 * result + this.getPieceType().hashCode();
    return result;
  }

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

    for (int i = myPosition.getRow(); i > 0; --i) {
      if (0 < row && row < 8 && 0 < col && col < 8) {
        ChessPositionImpl checkPos=new ChessPositionImpl(i, col);
        if (board.getPiece(checkPos).getPieceType() == null) {
          possibleBishopMoves.add(new ChessMoveImpl(myPosition, checkPos));
        }
      }
      col--;
    }
    for (int i = 0; i < myPosition.getColumn(); ++i) {
      if (0 < row && row < 8 && 0 < col && col < 8) {
        ChessPositionImpl checkPos = new ChessPositionImpl(i, row);
        if (board.getPiece(checkPos) == null) {
          possibleBishopMoves.add(new ChessMoveImpl(myPosition, checkPos));
        }
      }
      row++;
    }

    return possibleBishopMoves;
  }
  public Collection<ChessMove> kingPieceMoves(ChessBoard board, ChessPosition myPosition) {
    Integer[] possibleRows = {
            myPosition.getRow() + 1,
            myPosition.getRow() - 1,
            myPosition.getRow()
    };
    Integer[] possibleCols = {
            myPosition.getColumn() + 1,
            myPosition.getColumn() - 1,
            myPosition.getColumn()
    };

    return pieceMovesHelper(board, myPosition, possibleRows, possibleCols);
  }

  public Collection<ChessMove> pawnPieceMoves(ChessBoard board, ChessPosition myPosition) {
    Integer[] possibleRows;
    Integer[] possibleCols;
    ChessGame.TeamColor myColor = board.getPiece(myPosition).getTeamColor();

    // check for enemies left diagonal and right diagonal
    ChessPiece checkForEnemy1;
    ChessPiece checkForEnemy2;
    if (myColor == ChessGame.TeamColor.BLACK) {
      checkForEnemy1 = board.getPiece(new ChessPositionImpl(myPosition.getRow() - 1, myPosition.getColumn() + 1));
      checkForEnemy2 = board.getPiece(new ChessPositionImpl(myPosition.getRow() - 1, myPosition.getColumn() - 1));
    }
    else {
      checkForEnemy1 = board.getPiece(new ChessPositionImpl(myPosition.getRow() + 1, myPosition.getColumn() + 1));
      checkForEnemy2 = board.getPiece(new ChessPositionImpl(myPosition.getRow() + 1, myPosition.getColumn() - 1));
    }

    if (checkForEnemy1 != null && myColor == ChessGame.TeamColor.WHITE) {
      possibleCols = new Integer[] {
              myPosition.getColumn(),
              myPosition.getColumn() + 1
      };
    } else if (checkForEnemy2 != null && myColor == ChessGame.TeamColor.WHITE) {
      possibleCols = new Integer[] {
              myPosition.getColumn(),
              myPosition.getColumn() - 1
      };
    } else if (checkForEnemy2 != null && myColor == ChessGame.TeamColor.BLACK) {
      possibleCols = new Integer[] {
              myPosition.getColumn(),
              myPosition.getColumn() + 1
      };
    }else if (checkForEnemy1 != null && myColor == ChessGame.TeamColor.BLACK) {
      possibleCols = new Integer[] {
              myPosition.getColumn(),
              myPosition.getColumn() - 1
      };
    } else {
      possibleCols = new Integer[] {
              myPosition.getColumn()
      };
    }

    if (myPosition.getRow() == 2 || myPosition.getRow() == 7) { // first turn for the pawn
      if (myColor == ChessGame.TeamColor.BLACK) {
        possibleRows=new Integer[]{
                myPosition.getRow() - 1,
                myPosition.getRow() - 2
        };
      } else {
        possibleRows=new Integer[]{
                myPosition.getRow() + 1,
                myPosition.getRow() + 2
        };
      }
    } else if () {

    } else {
      if (myColor == ChessGame.TeamColor.BLACK) {
        possibleRows=new Integer[]{
                myPosition.getRow() - 1
        };
      } else {
        possibleRows=new Integer[]{
                myPosition.getRow() + 1
        };
      }
    }

    return pieceMovesHelper(board, myPosition, possibleRows, possibleCols);
  }

  public Collection<ChessMove> pieceMovesHelper(ChessBoard board, ChessPosition myPosition, Integer[] possibleRows, Integer[] possibleCols) {
    Collection<ChessMove> possibleMoves = new HashSet<>();

    for (Integer possibleRow : possibleRows) {
      for (Integer possibleCol : possibleCols) {
        ChessPositionImpl checkPos=new ChessPositionImpl(possibleRow, possibleCol);
        if (board.getPiece(checkPos) == null) {
            ChessMoveImpl newPossibleMove=new ChessMoveImpl(myPosition, checkPos, null);
            possibleMoves.add(newPossibleMove);
          }
      }
    }
    return possibleMoves;
  }
}
