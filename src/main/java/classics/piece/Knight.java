package classics.piece;

import classics.boardRepresentation.Board;
import classics.move.Move;

import java.util.ArrayList;

import static classics.boardRepresentation.BoardUtils.*;
import static classics.boardRepresentation.Tile.*;
import static classics.move.Move.*;

public class Knight extends Piece{
    private final int[] directions = {-17, -15, -10, -6, 6, 10, 15, 17};
    public Knight(final int coordinate, final Alliance alliance) {
        super(coordinate, alliance, PieceType.KNIGHT);
    }

    @Override
    ArrayList<Move> calculateLegalSquares(final Board board) {
        ArrayList<Move> allPossibleLegalMoves = new ArrayList<>();
        int destinationCoordinate;

        for (int dir : directions) {
            destinationCoordinate = currentCoordinate + dir;
            if (!isFirstColumnExclusive(this.currentCoordinate, dir)  &&
                !isSecondColumnExclusive(this.currentCoordinate, dir) &&
                !isSixthColumnExclusive(this.currentCoordinate, dir)  &&
                !isSeventhColumnExclusive(this.currentCoordinate, dir)&&
                isValidTile(destinationCoordinate)) {

                if (board.getChessBoard()[destinationCoordinate] instanceof EmptyTile)
                    allPossibleLegalMoves.add(new MajorMove(board, this, destinationCoordinate));
                if (board.getChessBoard()[destinationCoordinate] instanceof OccupiedTile) {
                    if (board.getChessBoard()[destinationCoordinate].getPiece().alliance !=
                            board.getChessBoard()[this.currentCoordinate].getPiece().alliance)
                        allPossibleLegalMoves.add(new AttackMove(board, this, destinationCoordinate,
                                board.getChessBoard()[destinationCoordinate].getPiece()));
                }
            }
        }
        return allPossibleLegalMoves;
    }

    private boolean isFirstColumnExclusive(int currentPosition, int direction) {
        return (FIRST_COLUMN[currentPosition] && direction == -17 || direction == -10 ||
                direction == 6 || direction == 15);
    }
    private boolean isSecondColumnExclusive(int currentPosition, int direction) {
        return (SECOND_COLUMN[currentPosition] && direction == -10 || direction == 6);
    }
    private boolean isSixthColumnExclusive(int currentPosition, int direction) {
        return (SIXTH_COLUMN[currentPosition] && direction == 10 ||direction == -6);
    }
    private boolean isSeventhColumnExclusive(int currentPosition, int direction) {
        return (SEVENTH_COLUMN[currentPosition] && direction == 10 ||direction == -6 ||
                direction == 17 || direction == -15);
    }
}
