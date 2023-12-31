package classics.piece;

import classics.board.Board;
import classics.move.Move;

import java.util.ArrayList;

import static classics.board.BoardUtils.*;

public class Queen extends Piece {
    private final int[] CANDIDATE_MOVE_DIRECTIONS = {-9, -8, -7, -1, 1, 7, 8, 9};
    public Queen(final int coordinate, final Alliance alliance) {
        super(coordinate, alliance, PieceType.QUEEN);
    }

    @Override
    public ArrayList<Move> calculateLegalSquares(final Board board) {
        ArrayList<Move> allPossibleLegalMoves = new ArrayList<>();
        int destinationCoordinate;

        for (int dir : CANDIDATE_MOVE_DIRECTIONS) {
            destinationCoordinate = pieceCoordinate;

            while (!isFirstColumnExclusive(destinationCoordinate, dir) &&
                    !isEighthColumnExclusive(destinationCoordinate, dir)) {

                destinationCoordinate += dir;
                if (!isValidTile(destinationCoordinate))
                    break;
                if (!board.getTile(destinationCoordinate).isTileOccupied())
                    allPossibleLegalMoves.add(new Move.MajorMove(board, this, destinationCoordinate));
                if (board.getTile(destinationCoordinate).isTileOccupied()) {
                    if (board.getTile(destinationCoordinate).getPiece().getAlliance() !=
                            board.getTile(pieceCoordinate).getPiece().getAlliance())
                        allPossibleLegalMoves.add(new Move.AttackMove(board, this, destinationCoordinate,
                                board.getTile(destinationCoordinate).getPiece()));
                    break;
                }
            }
        }
        return allPossibleLegalMoves;
    }

    private boolean isFirstColumnExclusive(final int currentPosition, final int direction) {
        return (FIRST_COLUMN[currentPosition] && (direction == -9 || direction == 7 || direction == -1));
    }
    private boolean isEighthColumnExclusive(final int currentPosition, final int direction) {
        return (EIGHTH[currentPosition] && (direction == 9 || direction == -7 || direction == 1));
    }

    @Override
    public String toString() {
        if (getAlliance().isWhite())
            return "Q";
        return "q";
    }
}
