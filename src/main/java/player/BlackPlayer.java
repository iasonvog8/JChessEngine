package player;

import classics.board.Board;
import classics.piece.King;
import classics.piece.Piece;
import classics.piece.PieceType;

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
    public boolean isPlayerOnDrawnStatement() {
        return false;
    }
}
