package chessImpl;

import chess.*;

import java.util.Collection;
import java.util.HashSet;

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
      Collection<ChessMove> movesBeforeInCheck = chessBoard.getPiece(startPosition).pieceMoves(chessBoard, startPosition);
      Collection<ChessMove> validMoves = new HashSet<>();
      TeamColor teamColor = chessBoard.getPiece(startPosition).getTeamColor();
      ChessPiece.PieceType pieceType = chessBoard.getPiece(startPosition).getPieceType();
      ChessPiece originalPiece = chessBoard.getPiece(startPosition);
      TeamColor opposingColor=opposingTeamColor(teamColor);

      chessBoard.removePiece(startPosition);
      for (ChessMove checkMove: movesBeforeInCheck) {
        ChessPieceImpl piece=new ChessPieceImpl(teamColor, pieceType);
        ChessPiece enemyPiece = null;
        if (chessBoard.getPiece(checkMove.getEndPosition()) != null && chessBoard.getPiece(checkMove.getEndPosition()).getTeamColor() == opposingColor) {
          enemyPiece = chessBoard.getPiece(checkMove.getEndPosition());
        }
        chessBoard.addPiece(checkMove.getEndPosition(), piece);
        if (!isInCheck(teamColor)) {
          validMoves.add(checkMove);
        }
        chessBoard.removePiece(checkMove.getEndPosition());
        if (enemyPiece != null) {
          chessBoard.addPiece(checkMove.getEndPosition(), enemyPiece);
        }
      }
      chessBoard.addPiece(startPosition, originalPiece);
      return validMoves;
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
      Collection<ChessMove> possibleMoves = chessBoard.getPiece(move.getStartPosition()).pieceMoves(chessBoard, move.getStartPosition());
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

  public TeamColor opposingTeamColor(TeamColor currTeamColor) {
    if (currTeamColor == TeamColor.WHITE) {
      return TeamColor.BLACK;
    } else if (currTeamColor == TeamColor.BLACK) {
      return TeamColor.WHITE;
    }
    return null;
  }

  @Override
  public boolean isInCheck(TeamColor teamColor) {
    TeamColor opposingColor=opposingTeamColor(teamColor);

    Collection<ChessMove> movesThatCouldCheck= getCheckMoves(opposingColor);

    if (getKingPos(teamColor) != null) { // this line is for testing purposes
      for (ChessMove checkMove: movesThatCouldCheck) {
        if (getKingPos(teamColor).equals(checkMove.getEndPosition())) {
          return true;
        }
      }
      return false;
    }
    return false;
  }

  public Collection<ChessMove> getCheckMoves(TeamColor opposingColor) {
    Collection<ChessMove> movesThatCouldCheck= new HashSet<>();
    for (int i = 1; i <= chessBoard.board.length; ++i) {
      for (int j = 1; j <= chessBoard.board.length; ++j) {
        ChessPositionImpl checkPos = new ChessPositionImpl(i, j);
        if (chessBoard.getPiece(checkPos) != null && chessBoard.getPiece(checkPos).getTeamColor() == opposingColor) {
          movesThatCouldCheck.addAll(chessBoard.getPiece(checkPos).pieceMoves(chessBoard, checkPos));
        }
      }
    }
    return movesThatCouldCheck;
  }

  public ChessPosition getKingPos(TeamColor teamColor) {
    ChessPosition kingPos=null;
    for (int i = 1; i <= chessBoard.board.length; ++i) {
      for (int j = 1; j <= chessBoard.board.length; ++j) {
        ChessPositionImpl checkPos = new ChessPositionImpl(i, j);if (chessBoard.getPiece(checkPos) != null && chessBoard.getPiece(checkPos).getPieceType() == ChessPiece.PieceType.KING && chessBoard.getPiece(checkPos).getTeamColor() == teamColor) {
          kingPos = checkPos;
        }
      }
    }
    return kingPos;
  }
  @Override
  public boolean isInCheckmate(TeamColor teamColor) {
    Collection<ChessMove> movesThatCouldCheckMate= new HashSet<>();
    TeamColor opposingColor=opposingTeamColor(teamColor);

    ChessPosition kingPos = getKingPos(teamColor);
    Collection<ChessMove> kingMoves = chessBoard.getPiece(kingPos).pieceMoves(chessBoard, kingPos);

    if (kingMoves.isEmpty()) {
      return false;
    }
    chessBoard.removePiece(kingPos);
    for (ChessMove kingMove: kingMoves) {
      ChessPieceImpl kingPiece=new ChessPieceImpl(teamColor, ChessPiece.PieceType.KING);
      chessBoard.addPiece(kingMove.getEndPosition(), kingPiece);
      for (int i=1; i <= chessBoard.board.length; ++i) {
        for (int j=1; j <= chessBoard.board.length; ++j) {
          ChessPositionImpl checkPos=new ChessPositionImpl(i, j);
          if (chessBoard.getPiece(checkPos) != null && chessBoard.getPiece(checkPos).getTeamColor() == opposingColor) {
            movesThatCouldCheckMate.addAll(chessBoard.getPiece(checkPos).pieceMoves(chessBoard, checkPos));
          }
        }
      }
      chessBoard.removePiece(kingMove.getEndPosition());
    }

    return kingMoves.stream()
            .allMatch(kingMove -> movesThatCouldCheckMate.stream()
                    .anyMatch(checkMateMove -> kingMove.getEndPosition().equals(checkMateMove.getEndPosition())));
  }

  @Override
  public boolean isInStalemate(TeamColor teamColor) {
    return !isInCheck(teamColor) && isInCheckmate(teamColor);
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
