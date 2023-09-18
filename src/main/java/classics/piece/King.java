package classics.piece;

import classics.boardRepresentation.Board;
import classics.move.Move;

import java.util.ArrayList;

import static classics.boardRepresentation.BoardUtils.*;
import static classics.boardRepresentation.BoardUtils.isValidTile;

public class King extends Piece{
    private final int[] directions = {-9, -8, -7, -1, 1, 7, 8, 9};
    public King(final int currentCoordinate, final Alliance alliance) {
        super(currentCoordinate, alliance, PieceType.KING);
    }

    @Override
    ArrayList<Move> calculateLegalSquares(Board board) {
        ArrayList<Move> allPossibleLegalMoves = new ArrayList<>();
        int destinationCoordinate;

        for (int dir : directions) {
            destinationCoordinate = currentCoordinate + dir;

            if (isNotFirstColumnExclusive(currentCoordinate, dir)  &&
                isNotSeventhColumnExclusive(currentCoordinate, dir)&&
                isValidTile(destinationCoordinate)) {

                if (!board.getChessBoard()[destinationCoordinate].isTileOccupied())
                    allPossibleLegalMoves.add(new Move.PrimaryMove(board, this, destinationCoordinate));
                if (board.getChessBoard()[destinationCoordinate].isTileOccupied()) {
                    if (board.getChessBoard()[destinationCoordinate].getPiece().alliance !=
                            board.getChessBoard()[currentCoordinate].getPiece().alliance)
                        allPossibleLegalMoves.add(new Move.AttackMove(board, this, destinationCoordinate,
                                board.getChessBoard()[destinationCoordinate].getPiece()));
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
