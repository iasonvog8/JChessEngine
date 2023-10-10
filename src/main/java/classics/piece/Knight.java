package classics.piece;

import classics.board.Board;
import classics.move.Move;

import java.util.ArrayList;

import static classics.board.BoardUtils.*;
import static classics.move.Move.*;

public class Knight extends Piece {
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

            if (!isFirstColumnExclusive(pieceCoordinate, dir)  &&
                !isSecondColumnExclusive(pieceCoordinate, dir) &&
                !isSeventhColumnExclusive(pieceCoordinate, dir)  &&
                !isEighthColumnExclusive(pieceCoordinate, dir)&&
                isValidTile(destinationCoordinate)) {

                if (!board.getTile(destinationCoordinate).isTileOccupied()) // or ... instanceof EmptyTile
                    allPossibleLegalMoves.add(new PrimaryMove(board, this, destinationCoordinate));
                if (board.getTile(destinationCoordinate).isTileOccupied()) { // or ... instanceof OccupiedTile
                    if (board.getTile(destinationCoordinate).getPiece().getAlliance() !=
                            board.getTile(pieceCoordinate).getPiece().getAlliance())
                        allPossibleLegalMoves.add(new AttackMove(board, this, destinationCoordinate,
                                board.getTile(destinationCoordinate).getPiece()));
                }
            }
        }
        return allPossibleLegalMoves;
    }

    private boolean isFirstColumnExclusive(final int currentPosition, final int direction) {
        return (FIRST_COLUMN[currentPosition] && (direction == -17 || direction == -10 ||
                direction == 6 || direction == 15));
    }
    private boolean isSecondColumnExclusive(final int currentPosition, final int direction) {
        return (SECOND_COLUMN[currentPosition] && (direction == -10 || direction == 6));
    }
    private boolean isSeventhColumnExclusive(final int currentPosition, final int direction) {
        return (SEVENTH[currentPosition] && (direction == 10 ||direction == -6));
    }
    private boolean isEighthColumnExclusive(final int currentPosition, final int direction) {
        return (EIGHTH[currentPosition] && (direction == 10 ||direction == -6 ||
                direction == 17 || direction == -15));
    }
}
