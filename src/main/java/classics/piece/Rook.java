package classics.piece;

import static classics.boardRepresentation.BoardUtils.*;
import static classics.move.Move.*;
import classics.boardRepresentation.Board;
import classics.move.Move;

import java.util.ArrayList;

public class Rook extends Piece{
    private final int[] CANDIDATE_MOVE_DIRECTIONS = {-8, -1, 1, 8};
    public Rook(final int coordinate, final Alliance alliance) {
        super(coordinate, alliance, PieceType.ROOK);
    }

    @Override
    public ArrayList<Move> calculateLegalSquares(Board board) {
        ArrayList<Move> allPossibleLegalMoves = new ArrayList<>();
        int destinationCoordinate;

        for (int dir : CANDIDATE_MOVE_DIRECTIONS) {
            destinationCoordinate = pieceCoordinate + dir;

            if (isNotFirstColumnExclusive(pieceCoordinate, dir) &&
                    isNotSeventhColumnExclusive(pieceCoordinate, dir) &&
                isValidTile(destinationCoordinate)) {
                do {
                    if (!board.getTile(destinationCoordinate).isTileOccupied())
                        allPossibleLegalMoves.add(new PrimaryMove(board, this, destinationCoordinate));
                    if (board.getTile(destinationCoordinate).isTileOccupied()) {
                        if (board.getTile(destinationCoordinate).getPiece().getAlliance() !=
                                board.getTile(pieceCoordinate).getPiece().getAlliance())
                            allPossibleLegalMoves.add(new AttackMove(board, this, destinationCoordinate,
                                    board.getTile(destinationCoordinate).getPiece()));
                        break;
                    }
                } while (isNotFirstColumnExclusive(destinationCoordinate, dir) &&
                        isNotSeventhColumnExclusive(destinationCoordinate, dir) &&
                        isValidTile(destinationCoordinate));
            }
        }

        return allPossibleLegalMoves;
    }

    private boolean isNotFirstColumnExclusive(final int currentPosition, final int direction) {
        return (!FIRST_COLUMN[currentPosition] || direction != -1);
    }
    private boolean isNotSeventhColumnExclusive(final int currentPosition, final int direction) {
        return (!SEVENTH_COLUMN[currentPosition] || direction != 1);
    }
}
