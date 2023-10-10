package classics.piece;

import classics.board.Board;
import classics.move.Move;

import java.util.ArrayList;

import static classics.board.BoardUtils.*;
import static classics.move.Move.*;

public class Bishop extends Piece {
    private final int[] CANDIDATE_DIRECTIONS = {-9, -7, 7, 9};
    public Bishop(final int coordinate, final Alliance alliance) {
        super(coordinate, alliance, PieceType.BISHOP);
    }

    @Override
    public ArrayList<Move> calculateLegalSquares(final Board board) {
        ArrayList<Move> allPossibleLegalMoves = new ArrayList<>();
        int destinationCoordinate;

        for (int dir : CANDIDATE_DIRECTIONS) {
            destinationCoordinate = pieceCoordinate;

           while (isNotFirstColumnExclusive(destinationCoordinate, dir) &&
                    isNotEighthColumnExclusive(destinationCoordinate, dir)) {

               destinationCoordinate += dir;
               if (!isValidTile(destinationCoordinate))
                   break;
               if (!board.getTile(destinationCoordinate).isTileOccupied())
                    allPossibleLegalMoves.add(new PrimaryMove(board, this, destinationCoordinate));
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
        return (!FIRST_COLUMN[currentPosition] || direction != -9 && direction != 7);
    }
    private boolean isNotEighthColumnExclusive(final int currentPosition, final int direction) {
        return (!EIGHTH[currentPosition] || direction != 9 && direction != -7);
    }
}
