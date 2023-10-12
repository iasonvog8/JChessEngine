package player;

import classics.board.Board;
import classics.move.Move;
import classics.move.MoveGenerator;
import classics.move.MoveValidator;
import classics.piece.King;
import classics.piece.Piece;
import classics.piece.PieceType;

import java.util.ArrayList;

import static classics.move.Move.*;

public class BlackPlayer extends Player{
    public BlackPlayer() {
    }

    @Override
    public boolean isWhitePlayer() {
        return false;
    }

    @Override
    public King estimateKingLocation(Board board) {
        for (Piece blackPiece : board.getAllBlackPieces())
            if (blackPiece.getPieceType() == PieceType.KING) return (King) blackPiece;
        return null;
    }

    @Override
    public boolean isPlayerOnCheckMate(final Board board) {
        TransitionMove transitionMove = new TransitionMove(board);
        return transitionMove.isDone(board, estimateKingLocation(board));
    }


    @Override
    public boolean isPlayerInCheck(Board board) {
        TransitionMove transitionMove = new TransitionMove(board);
        return transitionMove.isKingInCheck(estimateKingLocation(board));
    }

    @Override
    public boolean isPlayerOnDrawnStatement(final Board board) {
        ArrayList<Move> legalMoves = MoveGenerator.generateAllBlackPossibleMoves(board);
        for (Move drawnMove : legalMoves)
            if (MoveValidator.isValidMove(drawnMove, board.blackPlayer, board)) return false;

        TransitionMove transitionMove = new TransitionMove(board);
        return !transitionMove.hasEscapeMoves(estimateKingLocation(board));
    }
}
