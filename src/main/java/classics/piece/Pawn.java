package classics.piece;

import java.util.ArrayList;

public class Pawn extends Piece{
    public Pawn(int coordinate, Alliance alliance) {
        super(coordinate, alliance, PieceType.PAWN);
    }

    @Override
    ArrayList<Integer> calculateLegalSquares() {
        return null;
    }


}
