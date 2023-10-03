package chessImpl;

import chess.*;

import java.lang.reflect.Array;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import static java.lang.Math.abs;

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
    PieceType myPieceType;
    if (board.getPiece(myPosition) != null) {
      myPieceType = board.getPiece(myPosition).getPieceType();
      switch(myPieceType) {
        case PAWN:
          return pawnPieceMoves(board, myPosition);
        case ROOK:
          return rookPieceMoves(board, myPosition);
        case KNIGHT:
          return knightPieceMoves(board, myPosition);
        case BISHOP:
          return bishopPieceMoves(board, myPosition);
        case KING:
          return kingPieceMoves(board, myPosition);
        case QUEEN:
          Collection<ChessMove> queenMoves = new HashSet<>();
          queenMoves.addAll(rookPieceMoves(board, myPosition));
          queenMoves.addAll(bishopPieceMoves(board, myPosition));
          return queenMoves;
      }
    }
    return null;
  }

  public Collection<ChessMove> knightPieceMoves(ChessBoard board, ChessPosition myPosition) {
    Collection<ChessMove> possibleKnightMoves = new HashSet<>();

    Integer[] possibleRows = {
            myPosition.getRow() + 2,
            myPosition.getRow() - 2,
            myPosition.getRow() + 1,
            myPosition.getRow() - 1
    };
    Integer[] possibleCols = {
            myPosition.getColumn() + 2,
            myPosition.getColumn() - 2,
            myPosition.getColumn() + 1,
            myPosition.getColumn() - 1
    };

    return pieceMovesHelper(board, myPosition, possibleRows, possibleCols);
  }
  public Collection<ChessMove> rookPieceMoves(ChessBoard board, ChessPosition myPosition) {
    Collection<ChessMove> possibleRookMoves = new HashSet<>();
    // FIXME: add some functions for less repeated code

    // in same row
    int row = myPosition.getRow() - 1;
    while (row > 0) {
      ChessPositionImpl checkPos = new ChessPositionImpl(row, myPosition.getColumn());
      if (board.getPiece(checkPos) == null) {
        possibleRookMoves.add(new ChessMoveImpl(myPosition, checkPos, null));
      } else if (board.getPiece(checkPos).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
        possibleRookMoves.add(new ChessMoveImpl(myPosition, checkPos, null));
        break;
      } else if (board.getPiece(checkPos).getTeamColor() == board.getPiece(myPosition).getTeamColor()) {
        break;
      }
      row--;
    }

    row = myPosition.getRow() + 1;
    while (row <= 8) {
      ChessPositionImpl checkPos = new ChessPositionImpl(row, myPosition.getColumn());
      if (board.getPiece(checkPos) == null) {
        possibleRookMoves.add(new ChessMoveImpl(myPosition, checkPos, null));
      } else if (board.getPiece(checkPos).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
        possibleRookMoves.add(new ChessMoveImpl(myPosition, checkPos, null));
        break;
      } else if (board.getPiece(checkPos).getTeamColor() == board.getPiece(myPosition).getTeamColor()) {
        break;
      }
      row++;
    }

    // in same column
    int col = myPosition.getColumn() - 1;
    while (col > 0) {
      ChessPositionImpl checkPos = new ChessPositionImpl(myPosition.getRow(), col);
      if (board.getPiece(checkPos) == null) {
        possibleRookMoves.add(new ChessMoveImpl(myPosition, checkPos, null));
      } else if (board.getPiece(checkPos).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
        possibleRookMoves.add(new ChessMoveImpl(myPosition, checkPos, null));
        break;
      } else if (board.getPiece(checkPos).getTeamColor() == board.getPiece(myPosition).getTeamColor()) {
        break;
      }
      col--;
    }

    col = myPosition.getColumn() + 1;
    while (col <= 8) {
      ChessPositionImpl checkPos = new ChessPositionImpl(myPosition.getRow(), col);
      if (board.getPiece(checkPos) == null) {
        possibleRookMoves.add(new ChessMoveImpl(myPosition, checkPos, null));
      } else if (board.getPiece(checkPos).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
        possibleRookMoves.add(new ChessMoveImpl(myPosition, checkPos, null));
        break;
      } else if (board.getPiece(checkPos).getTeamColor() == board.getPiece(myPosition).getTeamColor()) {
        break;
      }
      col++;
    }

    return possibleRookMoves;
  }

  /*
  private rookRookHelper(ChessBoardImplCollection<ChessMove> possibleRookMoves, int row, int col, ChessPositionImpl myPosition) {
    ChessPositionImpl checkPos = new ChessPositionImpl(row, col);
    if (board.getPiece(checkPos) == null) {
      possibleRookMoves.add(new ChessMoveImpl(myPosition, checkPos, null));
    } else if (board.getPiece(checkPos).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
      possibleRookMoves.add(new ChessMoveImpl(myPosition, checkPos, null));
      break;
    }
  }

   */
  public Collection<ChessMove> bishopPieceMoves(ChessBoard board, ChessPosition myPosition) {
    Collection<ChessMove> possibleBishopMoves = new HashSet<>();
    int row =myPosition.getRow() + 1;
    int col =myPosition.getColumn() + 1;

    while (row <= 8 && col <= 8) {
      ChessPositionImpl checkPos = new ChessPositionImpl(row, col);
      if (board.getPiece(checkPos) == null) {
        possibleBishopMoves.add(new ChessMoveImpl(myPosition, checkPos, null));
      } else if (board.getPiece(checkPos).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
        possibleBishopMoves.add(new ChessMoveImpl(myPosition, checkPos, null));
        break;
      } else if (board.getPiece(checkPos).getTeamColor() == board.getPiece(myPosition).getTeamColor()) {
        break;
      }
      row++;
      col++;
    }

    row =myPosition.getRow() - 1;
    col =myPosition.getColumn() - 1;

    while (row > 0 && col > 0) {
      ChessPositionImpl checkPos = new ChessPositionImpl(row, col);
      if (board.getPiece(checkPos) == null) {
        possibleBishopMoves.add(new ChessMoveImpl(myPosition, checkPos, null));
      } else if (board.getPiece(checkPos).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
        possibleBishopMoves.add(new ChessMoveImpl(myPosition, checkPos, null));
        break;
      } else if (board.getPiece(checkPos).getTeamColor() == board.getPiece(myPosition).getTeamColor()) {
        break;
      }
      row--;
      col--;
    }

    row =myPosition.getRow() + 1;
    col =myPosition.getColumn() - 1;

    while (row <= 8 && col > 0) {
      ChessPositionImpl checkPos = new ChessPositionImpl(row, col);
      if (board.getPiece(checkPos) == null) {
        possibleBishopMoves.add(new ChessMoveImpl(myPosition, checkPos, null));
      } else if (board.getPiece(checkPos).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
        possibleBishopMoves.add(new ChessMoveImpl(myPosition, checkPos, null));
        break;
      } else if (board.getPiece(checkPos).getTeamColor() == board.getPiece(myPosition).getTeamColor()) {
        break;
      }
      row++;
      col--;
    }

    row =myPosition.getRow() - 1;
    col =myPosition.getColumn() + 1;

    while (row > 0 && col <= 8) {
      ChessPositionImpl checkPos = new ChessPositionImpl(row, col);
      if (board.getPiece(checkPos) == null) {
        possibleBishopMoves.add(new ChessMoveImpl(myPosition, checkPos, null));
      } else if (board.getPiece(checkPos).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
        possibleBishopMoves.add(new ChessMoveImpl(myPosition, checkPos, null));
        break;
      } else if (board.getPiece(checkPos).getTeamColor() == board.getPiece(myPosition).getTeamColor()) {
        break;
      }
      row--;
      col++;
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
              myPosition.getColumn() - 1
      };
    } else if (checkForEnemy2 != null && myColor == ChessGame.TeamColor.WHITE) {
      possibleCols = new Integer[] {
              myPosition.getColumn(),
              myPosition.getColumn() + 1
      };
    } else if (checkForEnemy2 != null && myColor == ChessGame.TeamColor.BLACK) {
      possibleCols = new Integer[] {
              myPosition.getColumn(),
              myPosition.getColumn() - 1
      };
    }else if (checkForEnemy1 != null && myColor == ChessGame.TeamColor.BLACK) {
      possibleCols = new Integer[] {
              myPosition.getColumn(),
              myPosition.getColumn() + 1
      };
    } else {
      possibleCols = new Integer[] {
              myPosition.getColumn()
      };
    }

    if ((myPosition.getRow() == 2 &&  myColor == ChessGame.TeamColor.WHITE) || (myPosition.getRow() == 7 && myColor == ChessGame.TeamColor.BLACK)) { // first turn for the pawn
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

        if (possibleCol <= 8 && possibleRow <= 8 && possibleCol > 0 && possibleRow > 0) {
        ChessPositionImpl checkPos=new ChessPositionImpl(possibleRow, possibleCol);

        if (board.getPiece(myPosition).getPieceType() == PieceType.PAWN) {
          Boolean inBetweenCheck = true;
          ChessPositionImpl checkWhiteCapture = new ChessPositionImpl(myPosition.getRow() + 1, myPosition.getColumn() - 1);
          ChessPositionImpl checkWhiteCapture2 = new ChessPositionImpl(myPosition.getRow() + 1, myPosition.getColumn() + 1);
          ChessPositionImpl checkBlackCapture = new ChessPositionImpl(myPosition.getRow() - 1, myPosition.getColumn() - 1);
          ChessPositionImpl checkBlackCapture2 = new ChessPositionImpl(myPosition.getRow() - 1, myPosition.getColumn() + 1);

          if (board.getPiece(checkPos) == null ||
                  (board.getPiece(checkPos).getTeamColor() != board.getPiece(myPosition).getTeamColor() &&
                          (checkPos.equals(checkWhiteCapture) ||
                                  checkPos.equals(checkWhiteCapture2) ||
                                  checkPos.equals(checkBlackCapture) ||
                                  checkPos.equals(checkBlackCapture2)))) {
            if (abs(checkPos.getRow() - myPosition.getRow()) > 1 && checkPos.getColumn() == myPosition.getColumn()) {

              if (board.getPiece(myPosition).getTeamColor() == ChessGame.TeamColor.WHITE) {
                ChessPositionImpl checkInBetween = new ChessPositionImpl(myPosition.getRow() + 1, myPosition.getColumn());

                if (board.getPiece(checkInBetween) != null) {
                  inBetweenCheck = false;
                }
              } else if (board.getPiece(myPosition).getTeamColor() == ChessGame.TeamColor.BLACK) {
                ChessPositionImpl checkInBetween = new ChessPositionImpl(myPosition.getRow() - 1, myPosition.getColumn());

                if (board.getPiece(checkInBetween) != null) {
                  inBetweenCheck = false;
                }
              }
            }

            if (inBetweenCheck) {
              if (checkPos.getRow() == 8 && board.getPiece(myPosition).getTeamColor() == ChessGame.TeamColor.WHITE) {
                possibleMoves.add(new ChessMoveImpl(myPosition, checkPos, PieceType.ROOK));
                possibleMoves.add(new ChessMoveImpl(myPosition, checkPos, PieceType.KNIGHT));
                possibleMoves.add(new ChessMoveImpl(myPosition, checkPos, PieceType.BISHOP));
                possibleMoves.add(new ChessMoveImpl(myPosition, checkPos, PieceType.QUEEN));
              } else if (checkPos.getRow() == 1 && board.getPiece(myPosition).getTeamColor() == ChessGame.TeamColor.BLACK) {
                possibleMoves.add(new ChessMoveImpl(myPosition, checkPos, PieceType.ROOK));
                possibleMoves.add(new ChessMoveImpl(myPosition, checkPos, PieceType.KNIGHT));
                possibleMoves.add(new ChessMoveImpl(myPosition, checkPos, PieceType.BISHOP));
                possibleMoves.add(new ChessMoveImpl(myPosition, checkPos, PieceType.QUEEN));
              } else {
                ChessMoveImpl newPossibleMove=new ChessMoveImpl(myPosition, checkPos, null);
                possibleMoves.add(newPossibleMove);
              }
            }
          }
        } else if (board.getPiece(myPosition).getPieceType() == PieceType.KING){ // KING
          if (board.getPiece(checkPos) == null || board.getPiece(checkPos).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
            ChessMoveImpl newPossibleMove=new ChessMoveImpl(myPosition, checkPos, null);
            possibleMoves.add(newPossibleMove);
          }
        } else if (board.getPiece(myPosition).getPieceType() == PieceType.KNIGHT){ // KNIGHT
          // check that it is two vs one
          if ((abs(checkPos.getRow() - myPosition.getRow()) > 1 && abs(checkPos.getColumn() - myPosition.getColumn()) == 1) ||
                  (abs(checkPos.getRow() - myPosition.getRow()) == 1 && abs(checkPos.getColumn() - myPosition.getColumn()) > 1)) {
            if (board.getPiece(checkPos) == null || board.getPiece(checkPos).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
              ChessMoveImpl newPossibleMove=new ChessMoveImpl(myPosition, checkPos, null);
              possibleMoves.add(newPossibleMove);
            }
          }
        }

      }
    }
    }

    return possibleMoves;
  }
}
