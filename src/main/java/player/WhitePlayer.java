package player;

import classics.board.Board;
import classics.piece.Piece;
import classics.piece.PieceType;

public class WhitePlayer extends Player{
    public WhitePlayer() {

    }

    @Override
    public boolean isWhitePlayer() {
        return true;
    }

    @Override
    public Piece estimateKingLocation(final Board board) {
        for (Piece whitePiece : board.getAllWhitePieces())
            if (whitePiece.getPieceType() == PieceType.KING) return whitePiece;
        return null;
    }
}
