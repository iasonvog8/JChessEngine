package player;

import classics.board.Board;
import classics.piece.Piece;
import classics.piece.PieceType;

public class BlackPlayer extends Player{
    public BlackPlayer() {
    }

    @Override
    public boolean isWhitePlayer() {
        return false;
    }

    @Override
    public Piece estimateKingLocation(Board board) {
        for (Piece blackPiece : board.getAllBlackPieces())
            if (blackPiece.getPieceType() == PieceType.KING) return blackPiece;
        return null;
    }
}
