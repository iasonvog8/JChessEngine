package classics.piece;

import classics.boardRepresentation.Board;
import classics.move.Move;

import java.util.ArrayList;

import static classics.boardRepresentation.BoardUtils.*;
import static classics.move.Move.*;

public class Knight extends Piece{
    private final int[] CANDIDATE_MOVE_DIRECTIONS = {-17, -15, -10, -6, 6, 10, 15, 17};
    public Knight(final int coordinate, final Alliance alliance) {
        super(coordinate, alliance, PieceType.KNIGHT);
    }

    @Override
    public ArrayList<Move> calculateLegalSquares(final Board board) {
        ArrayList<Move> allPossibleLegalMoves = new ArrayList<>();
        int destinationCoordinate;

        for (int dir : CANDIDATE_MOVE_DIRECTIONS) {
            destinationCoordinate = pieceCoordinate + dir;
            System.out.println(destinationCoordinate + " " + dir + " " + pieceCoordinate);
            System.out.println(!isFirstColumnExclusive(pieceCoordinate, dir));

            if (!isFirstColumnExclusive(pieceCoordinate, dir)  &&
                !isSecondColumnExclusive(pieceCoordinate, dir) &&
                !isSixthColumnExclusive(pieceCoordinate, dir)  &&
                !isSeventhColumnExclusive(pieceCoordinate, dir)&&
                isValidTile(destinationCoordinate)) {
                System.out.println(destinationCoordinate);
                if (!board.getTile(destinationCoordinate).isTileOccupied()) {// or ... instanceof EmptyTile
                    allPossibleLegalMoves.add(new PrimaryMove(board, this, destinationCoordinate));
                    System.out.println("AKDJAGD");
                    //TODO 2
                }if (board.getTile(destinationCoordinate).isTileOccupied()) { // or ... instanceof OccupiedTile
                    if (board.getTile(destinationCoordinate).getPiece().getAlliance() !=
                            board.getTile(pieceCoordinate).getPiece().getAlliance())
                        allPossibleLegalMoves.add(new AttackMove(board, this, destinationCoordinate,
                                board.getTile(destinationCoordinate).getPiece()));
                }
            }
        }
        for (Move m : allPossibleLegalMoves)
            System.out.println(m.toString());
        return allPossibleLegalMoves;
    }

    private boolean isFirstColumnExclusive(final int currentPosition, final int direction) {
        System.out.println(FIRST_COLUMN[currentPosition] && direction == -17 || direction == -10 ||
                direction == 6 || direction == 15);
        return (FIRST_COLUMN[currentPosition] && direction == -17 || direction == -10 ||
                direction == 6 || direction == 15);
    }
    private boolean isSecondColumnExclusive(final int currentPosition, final int direction) {
        return (SECOND_COLUMN[currentPosition] && direction == -10 || direction == 6);
    }
    private boolean isSixthColumnExclusive(final int currentPosition, final int direction) {
        return (SIXTH_COLUMN[currentPosition] && direction == 10 ||direction == -6);
    }
    private boolean isSeventhColumnExclusive(final int currentPosition, final int direction) {
        return (SEVENTH_COLUMN[currentPosition] && direction == 10 ||direction == -6 ||
                direction == 17 || direction == -15);
    }
}
