package player;

import classics.board.Board;
import classics.piece.King;
import classics.piece.Piece;
import classics.piece.PieceType;

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
        return transitionMove.isKingInCheck(estimateKingLocation(board));
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
