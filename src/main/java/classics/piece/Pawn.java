package classics.piece;

import static classics.boardRepresentation.BoardUtils.*;
import static classics.move.Move.*;
import classics.boardRepresentation.Board;
import classics.move.Move;

import java.util.ArrayList;

public class Pawn extends Piece{
    private final int[] CANDIDATE_MOVE_DIRECTIONS = {16, 9, 8, 7};
    public Pawn(final int currentCoordinate, final Alliance alliance) {
        super(currentCoordinate, alliance, PieceType.PAWN);
    }

    @Override
    public ArrayList<Move> calculateLegalSquares(final Board board) {
        ArrayList<Move> allPossibleLegalMoves = new ArrayList<>();
        int destinationCoordinate;
        int verticalDirectionOffSet = getAlliance().getVerticalDirection().getDirectionOffSet();
        int behindTile = -(8 * verticalDirectionOffSet);

        for (int directionOffSet : CANDIDATE_MOVE_DIRECTIONS) {
            destinationCoordinate = pieceCoordinate + (directionOffSet * verticalDirectionOffSet);

            if (directionOffSet == 8 && isValidTile(destinationCoordinate) && !board.getTile(destinationCoordinate).isTileOccupied()) {
                //TODO more for promotions
                allPossibleLegalMoves.add(new PrimaryMove(board, this, destinationCoordinate));

            }else if (directionOffSet == 16 && isValidTile(destinationCoordinate) &&
                    !board.getTile(destinationCoordinate).isTileOccupied() &&
                    !board.getTile(destinationCoordinate + behindTile).isTileOccupied())
                allPossibleLegalMoves.add(new PrimaryMove(board, this, destinationCoordinate));

            else if ((directionOffSet * verticalDirectionOffSet == 7 || directionOffSet * verticalDirectionOffSet == -9) &&
                    isValidTile(destinationCoordinate) && !FIRST_COLUMN[pieceCoordinate]) {
                if (board.getTile(destinationCoordinate).isTileOccupied() && board.getTile(destinationCoordinate).getPiece().getAlliance() !=
                    board.getTile(pieceCoordinate).getPiece().getAlliance())
                    allPossibleLegalMoves.add(new AttackMove(board, this, destinationCoordinate,
                            board.getTile(destinationCoordinate).getPiece()));
            }

            else if ((directionOffSet * verticalDirectionOffSet == -7 || directionOffSet * verticalDirectionOffSet == 9) &&
                    isValidTile(destinationCoordinate) && !SEVENTH_COLUMN[pieceCoordinate]) {
                if (board.getTile(destinationCoordinate).isTileOccupied() && board.getTile(destinationCoordinate).getPiece().getAlliance() !=
                        board.getTile(pieceCoordinate).getPiece().getAlliance())
                    allPossibleLegalMoves.add(new AttackMove(board, this, destinationCoordinate,
                            board.getTile(destinationCoordinate).getPiece()));
            }
        }

        return allPossibleLegalMoves;
    }

}
