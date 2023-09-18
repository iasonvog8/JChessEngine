package classics.piece;

import classics.boardRepresentation.Board;
import classics.move.Move;

import java.util.ArrayList;

public class Pawn extends Piece{
    public Pawn(int coordinate, Alliance alliance) {
        super(coordinate, alliance, PieceType.PAWN);
    }

    @Override
    public ArrayList<Move> calculateLegalSquares(Board board) {
        return null;
    }


}
