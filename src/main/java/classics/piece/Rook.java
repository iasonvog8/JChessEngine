package classics.piece;

import static classics.board.BoardUtils.*;
import static classics.move.Move.*;
import classics.board.Board;
import classics.move.Move;

import java.util.ArrayList;

public class Rook extends Piece {
    private final int[] CANDIDATE_MOVE_DIRECTIONS = {-8, -1, 1, 8};
    public Rook(final int coordinate, final Alliance alliance) {
        super(coordinate, alliance, PieceType.ROOK);
    }

    @Override
    public ArrayList<Move> calculateLegalSquares(Board board) {
        ArrayList<Move> allPossibleLegalMoves = new ArrayList<>();
        int destinationCoordinate;

        for (int dir : CANDIDATE_MOVE_DIRECTIONS) {
            destinationCoordinate = pieceCoordinate;

            while (isNotFirstColumnExclusive(destinationCoordinate, dir) &&
                    isNotEighthColumnExclusive(destinationCoordinate, dir)) {

                destinationCoordinate += dir;
                if (!isValidTile(destinationCoordinate))
                    break;
                if (!board.getTile(destinationCoordinate).isTileOccupied())
                    allPossibleLegalMoves.add(new MajorMove(board, this, destinationCoordinate));
                if (board.getTile(destinationCoordinate).isTileOccupied()) {
                    if (board.getTile(destinationCoordinate).getPiece().getAlliance() !=
                            board.getTile(pieceCoordinate).getPiece().getAlliance())
                        allPossibleLegalMoves.add(new AttackMove(board, this, destinationCoordinate,
                                board.getTile(destinationCoordinate).getPiece()));
                    break;
                }
            }
        }
        return allPossibleLegalMoves;
    }

    private boolean isNotFirstColumnExclusive(final int currentPosition, final int direction) {
        return (!FIRST_COLUMN[currentPosition] || direction != -1);
    }
    private boolean isNotEighthColumnExclusive(final int currentPosition, final int direction) {
        return (!EIGHTH[currentPosition] || direction != 1);
    }

    @Override
    public String toString() {
        if (getAlliance().isWhite())
            return "R";
        return "r";
    }
}
