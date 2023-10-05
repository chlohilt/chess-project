package chessImpl;

import chess.*;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

public class ChessGameImpl implements ChessGame {
  TeamColor teamTurn = TeamColor.WHITE;
  ChessBoardImpl chessBoard;
  @Override
  public TeamColor getTeamTurn() {
    return teamTurn;
  }

  @Override
  public void setTeamTurn(TeamColor team) {
    teamTurn = team;
  }

  @Override
  public Collection<ChessMove> validMoves(ChessPosition startPosition) {
    if (chessBoard.getPiece(startPosition) == null) {
      return null;
    } else {
      return chessBoard.getPiece(startPosition).pieceMoves(chessBoard, startPosition);
    }
  }

  @Override
  public void makeMove(ChessMove move) throws InvalidMoveException {
    try {
      if (chessBoard.getPiece(move.getStartPosition()) == null) {
        if (teamTurn == TeamColor.WHITE) {
          teamTurn = TeamColor.BLACK;
        } else {
          teamTurn = TeamColor.WHITE;
        }
        return;
      }
      TeamColor currColor = chessBoard.getPiece(move.getStartPosition()).getTeamColor();
      if (currColor != teamTurn) {
        throw new InvalidMoveException();
      }
      Collection<ChessMove> possibleMoves = this.validMoves(move.getStartPosition());
      boolean found = false;

      for (ChessMove moveCheck : possibleMoves) {
        if (moveCheck.getEndPosition().equals(move.getEndPosition())) {
          found = true;
          break;
        }
      }

      if (found) {
        chessBoard.makeMove(move);
      } else {
        throw new InvalidMoveException();
      }

      if (isInCheck(currColor)) {
        throw new InvalidMoveException();
      }
      if (currColor == TeamColor.WHITE) {
        teamTurn = TeamColor.BLACK;
      } else {
        teamTurn = TeamColor.WHITE;
      }

    } catch (InvalidMoveException invalidMoveException) {
      throw new InvalidMoveException();
    }

  }

  @Override
  public boolean isInCheck(TeamColor teamColor) {
    Collection<ChessMove> movesThatCouldCheck= new HashSet<>();;
    ChessPositionImpl kingPos=null;
    TeamColor opposingColor=null;
    if (teamColor == TeamColor.WHITE) {
      opposingColor = TeamColor.BLACK;
    } else if (teamColor == TeamColor.BLACK) {
      opposingColor = TeamColor.WHITE;
    }

    for (int i = 1; i <= chessBoard.board.length; ++i) {
      for (int j = 1; j <= chessBoard.board.length; ++j) {
        ChessPositionImpl checkPos = new ChessPositionImpl(i, j);
        if (chessBoard.getPiece(checkPos) != null && chessBoard.getPiece(checkPos).getTeamColor() == opposingColor) {
          movesThatCouldCheck.addAll(validMoves(checkPos));
        } else if (chessBoard.getPiece(checkPos) != null && chessBoard.getPiece(checkPos).getPieceType() == ChessPiece.PieceType.KING && chessBoard.getPiece(checkPos).getTeamColor() == teamColor) {
          kingPos = checkPos;
        }
      }
    }

    for (ChessMove checkMove: movesThatCouldCheck) {
      if (kingPos.equals(checkMove.getEndPosition())) {
        return true;
      }
    }
    return false;
  }

  @Override
  public boolean isInCheckmate(TeamColor teamColor) {
    Collection<ChessMove> movesThatCouldCheckMate= new HashSet<>();;
    ChessPositionImpl kingPos=null;
    TeamColor opposingColor=null;
    if (teamColor == TeamColor.WHITE) {
      opposingColor = TeamColor.BLACK;
    } else if (teamColor == TeamColor.BLACK) {
      opposingColor = TeamColor.WHITE;
    }

    for (int i = 1; i <= chessBoard.board.length; ++i) {
      for (int j = 1; j <= chessBoard.board.length; ++j) {
        ChessPositionImpl checkPos = new ChessPositionImpl(i, j);
        if (chessBoard.getPiece(checkPos) != null && chessBoard.getPiece(checkPos).getPieceType() == ChessPiece.PieceType.KING && chessBoard.getPiece(checkPos).getTeamColor() == teamColor) {
          kingPos = checkPos;
        }
      }
    }
    Collection<ChessMove> kingMoves = validMoves(kingPos);

    chessBoard.removePiece(kingPos);
    for (ChessMove kingMove: kingMoves) {
      ChessPieceImpl kingPiece=new ChessPieceImpl(teamColor, ChessPiece.PieceType.KING);
      chessBoard.addPiece(kingMove.getEndPosition(), kingPiece);
      for (int i=1; i <= chessBoard.board.length; ++i) {
        for (int j=1; j <= chessBoard.board.length; ++j) {
          ChessPositionImpl checkPos=new ChessPositionImpl(i, j);
          if (chessBoard.getPiece(checkPos) != null && chessBoard.getPiece(checkPos).getTeamColor() == opposingColor) {
            movesThatCouldCheckMate.addAll(validMoves(checkPos));
          }
        }
      }
      chessBoard.removePiece(kingMove.getEndPosition());
    }

    boolean result = kingMoves.stream()
            .allMatch(kingMove -> movesThatCouldCheckMate.stream()
                    .anyMatch(checkMateMove -> kingMove.getEndPosition().equals(checkMateMove.getEndPosition())));

    return result;
  }

  @Override
  public boolean isInStalemate(TeamColor teamColor) {
    return false;
  }

  @Override
  public void setBoard(ChessBoard board) {
    this.chessBoard =(ChessBoardImpl) board;
  }

  @Override
  public ChessBoard getBoard() {
    return this.chessBoard;
  }
}
