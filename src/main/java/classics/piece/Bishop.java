package classics.piece;

import classics.boardRepresentation.Board;
import classics.move.Move;

import java.util.ArrayList;

import static classics.boardRepresentation.BoardUtils.*;

public class Bishop extends Piece{
    private final int[] CANDIDATE_DIRECTIONS = {-9, -7, 7, 9};
    public Bishop(final int coordinate, final Alliance alliance) {
        super(coordinate, alliance, PieceType.BISHOP);
    }

    @Override
    public ArrayList<Move> calculateLegalSquares(final Board board) {
        ArrayList<Move> allPossibleLegalMoves = new ArrayList<>();
        int destinationCoordinate;

        for (int dir : CANDIDATE_DIRECTIONS) {
            destinationCoordinate = pieceCoordinate + dir;

            if (isNotFirstColumnExclusive(pieceCoordinate, dir) &&
                    isNotSeventhColumnExclusive(pieceCoordinate, dir) &&
                    isValidTile(pieceCoordinate)) {
                do {
                    if (!board.getTile(destinationCoordinate).isTileOccupied())
                        allPossibleLegalMoves.add(new Move.PrimaryMove(board, this, destinationCoordinate));
                    if (board.getTile(destinationCoordinate).isTileOccupied()) {
                        if (board.getTile(destinationCoordinate).getPiece().getAlliance() !=
                                board.getTile(pieceCoordinate).getPiece().getAlliance()) {
                            allPossibleLegalMoves.add(new Move.AttackMove(board, this, destinationCoordinate,
                                    board.getTile(destinationCoordinate).getPiece()));
                            break;
                        }
                    }
                } while (isNotFirstColumnExclusive(destinationCoordinate, dir) &&
                        isNotSeventhColumnExclusive(destinationCoordinate, dir) &&
                        isValidTile(destinationCoordinate));
            }
        }

        return allPossibleLegalMoves;
    }

    private boolean isNotFirstColumnExclusive(final int currentPosition, final int direction) {
        return (!FIRST_COLUMN[currentPosition] || direction != -9 && direction != 7);
    }
    private boolean isNotSeventhColumnExclusive(final int currentPosition, final int direction) {
        return (!SEVENTH_COLUMN[currentPosition] || direction != 9 && direction != -7);
    }
}
