package classics.piece;

import static classics.boardRepresentation.BoardUtils.*;
import static classics.move.Move.*;
import classics.boardRepresentation.Board;
import classics.move.Move;

import java.util.ArrayList;

public class Rook extends Piece{
    private final int[] directions = {-8, -1, 1, 8};
    public Rook(final int coordinate, final Alliance alliance) {
        super(coordinate, alliance, PieceType.ROOK);
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
                        allPossibleLegalMoves.add(new PrimaryMove(board, this, destinationCoordinate));
                    if (board.getChessBoard()[destinationCoordinate].isTileOccupied()) {
                        if (board.getChessBoard()[destinationCoordinate].getPiece().alliance !=
                                board.getChessBoard()[currentCoordinate].getPiece().alliance) {
                            allPossibleLegalMoves.add(new AttackMove(board, this, destinationCoordinate,
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
        return (!FIRST_COLUMN[currentPosition] || direction != -1);
    }
    private boolean isNotSeventhColumnExclusive(int currentPosition, int direction) {
        return (!SEVENTH_COLUMN[currentPosition] || direction != 1);
    }
}
