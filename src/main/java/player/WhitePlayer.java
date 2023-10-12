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

public class WhitePlayer extends Player{
    public WhitePlayer() {

    }

    @Override
    public boolean isWhitePlayer() {
        return true;
    }

    @Override
    public King estimateKingLocation(final Board board) {
        for (Piece whitePiece : board.getAllWhitePieces())
            if (whitePiece.getPieceType() == PieceType.KING) return (King) whitePiece;
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
        ArrayList<Move> legalMoves = MoveGenerator.generateAllWhitePossibleMoves(board);
        for (Move drawnMove : legalMoves)
            if (MoveValidator.isValidMove(drawnMove, board.whitePlayer, board)) return false;

        TransitionMove transitionMove = new TransitionMove(board);
        return !transitionMove.hasEscapeMoves(estimateKingLocation(board));
    }
}
