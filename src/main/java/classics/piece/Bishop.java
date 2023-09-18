package classics.piece;

import classics.boardRepresentation.Board;
import classics.move.Move;

import java.util.ArrayList;

import static classics.boardRepresentation.BoardUtils.*;

public class Bishop extends Piece{
    private final int[] directions = {-9, -7, 7, 9};
    public Bishop(final int coordinate, final Alliance alliance) {
        super(coordinate, alliance, PieceType.BISHOP);
    }

    @Override
    public ArrayList<Move> calculateLegalSquares(Board board) {
        ArrayList<Move> allPossibleLegalMoves = new ArrayList<>();
        int destinationCoordinate;

        for (int dir : directions) {
            destinationCoordinate = currentCoordinate + dir;

            if (isNotFirstColumnExclusive(currentCoordinate, dir) &&
                    isNotSeventhColumnExclusive(currentCoordinate, dir) &&
                    isValidTile(currentCoordinate)) {
                do {
                    if (!board.getChessBoard()[destinationCoordinate].isTileOccupied())
                        allPossibleLegalMoves.add(new Move.PrimaryMove(board, this, destinationCoordinate));
                    if (board.getChessBoard()[destinationCoordinate].isTileOccupied()) {
                        if (board.getChessBoard()[destinationCoordinate].getPiece().alliance !=
                                board.getChessBoard()[currentCoordinate].getPiece().alliance) {
                            allPossibleLegalMoves.add(new Move.AttackMove(board, this, destinationCoordinate,
                                    board.getChessBoard()[destinationCoordinate].getPiece()));
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

    private boolean isNotFirstColumnExclusive(int currentPosition, int direction) {
        return (!FIRST_COLUMN[currentPosition] || direction != -9 && direction != 7);
    }
    private boolean isNotSeventhColumnExclusive(int currentPosition, int direction) {
        return (!SEVENTH_COLUMN[currentPosition] || direction != 9 && direction != -7);
    }
}
