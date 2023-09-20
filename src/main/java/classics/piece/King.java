package classics.piece;

import classics.boardRepresentation.Board;
import classics.move.Move;

import java.util.ArrayList;

import static classics.boardRepresentation.BoardUtils.*;
import static classics.move.Move.*;

public class King extends Piece {
    private final int[] CANDIDATE_MOVE_DIRECTIONS = {-9, -8, -7, -1, 1, 7, 8, 9};
    public King(final int coordinate, final Alliance alliance) {
        super(coordinate, alliance, PieceType.KING);
    }

    @Override
    public ArrayList<Move> calculateLegalSquares(Board board) {
        ArrayList<Move> allPossibleLegalMoves = new ArrayList<>();
        int destinationCoordinate;

        for (int dir : CANDIDATE_MOVE_DIRECTIONS) {
            destinationCoordinate = pieceCoordinate + dir;

            if (isNotFirstColumnExclusive(pieceCoordinate, dir)  &&
                isNotSeventhColumnExclusive(pieceCoordinate, dir)&&
                isValidTile(destinationCoordinate)) {

                if (!board.getTile(destinationCoordinate).isTileOccupied())
                    allPossibleLegalMoves.add(new PrimaryMove(board, this, destinationCoordinate));
                if (board.getTile(destinationCoordinate).isTileOccupied()) {
                    if (board.getTile(destinationCoordinate).getPiece().getAlliance() !=
                            board.getTile(pieceCoordinate).getPiece().getAlliance())
                        allPossibleLegalMoves.add(new AttackMove(board, this, destinationCoordinate,
                                board.getTile(destinationCoordinate).getPiece()));
                }
            }
        }
        return allPossibleLegalMoves;
    }

    private boolean isNotFirstColumnExclusive(int currentPosition, int direction) {
        return (!FIRST_COLUMN[currentPosition] || direction != -9 && direction != 7 && direction != -1);
    }
    private boolean isNotSeventhColumnExclusive(int currentPosition, int direction) {
        return (!SEVENTH_COLUMN[currentPosition] || direction != 9 && direction != -7 && direction != 1);
    }
}
