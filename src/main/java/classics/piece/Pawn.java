package classics.piece;

import static classics.board.BoardUtils.*;
import static classics.move.Move.*;
import classics.board.Board;
import classics.move.Move;

import java.util.ArrayList;

public class Pawn extends Piece{
    private final int[] CANDIDATE_MOVE_DIRECTIONS = {16, 9, 8, 7};
    public Pawn(final int coordinate, final Alliance alliance) {
        super(coordinate, alliance, PieceType.PAWN);
    }

    @Override
    public ArrayList<Move> calculateLegalSquares(final Board board) {
        ArrayList<Move> allPossibleLegalMoves = new ArrayList<>();
        boolean[] promotionRow = getAlliance() == Alliance.WHITE ? FIRST_ROW : EIGHTH_ROW;
        boolean[] enPassantRow = getAlliance() == Alliance.WHITE ? FIFTH_ROW : FOURTH_ROW;
        int destinationCoordinate;
        int verticalDirectionOffSet = getAlliance().getVerticalDirection().getDirectionOffSet();
        int behindTile = -(8 * verticalDirectionOffSet);

        for (int directionOffSet : CANDIDATE_MOVE_DIRECTIONS) {
            destinationCoordinate = pieceCoordinate + (directionOffSet * verticalDirectionOffSet);

            if (directionOffSet == 8 && isValidTile(destinationCoordinate) && !board.getTile(destinationCoordinate).isTileOccupied()) {
                if (promotionRow[destinationCoordinate])
                    allPossibleLegalMoves.add(new PromotionMove(board, this, destinationCoordinate, null));
                else allPossibleLegalMoves.add(new PrimaryMove(board, this, destinationCoordinate));

            }else if (directionOffSet == 16 && isValidTile(destinationCoordinate) &&
                    !board.getTile(destinationCoordinate).isTileOccupied() &&
                    !board.getTile(destinationCoordinate + behindTile).isTileOccupied())
                allPossibleLegalMoves.add(new PrimaryMove(board, this, destinationCoordinate));
//left
            else if ((directionOffSet * verticalDirectionOffSet == 7 || directionOffSet * verticalDirectionOffSet == -9) &&
                    isValidTile(destinationCoordinate) && !FIRST_COLUMN[pieceCoordinate]) {
                if (board.getTile(destinationCoordinate).isTileOccupied() && board.getTile(destinationCoordinate).getPiece().getAlliance() !=
                        board.getTile(pieceCoordinate).getPiece().getAlliance())
                    allPossibleLegalMoves.add(new AttackMove(board, this, destinationCoordinate,
                            board.getTile(destinationCoordinate).getPiece()));
                //en passant
                else if (enPassantRow[pieceCoordinate] && destinationCoordinate == board.getEnPassantTarget())
                    allPossibleLegalMoves.add(new EnPassantMove(board, this, destinationCoordinate,
                            board.getTile(pieceCoordinate - 1).getPiece()));
            }
//right
            else if ((directionOffSet * verticalDirectionOffSet == -7 || directionOffSet * verticalDirectionOffSet == 9) &&
                    isValidTile(destinationCoordinate) && !EIGHTH[pieceCoordinate]) {
                if (board.getTile(destinationCoordinate).isTileOccupied() && board.getTile(destinationCoordinate).getPiece().getAlliance() !=
                        board.getTile(pieceCoordinate).getPiece().getAlliance())
                    allPossibleLegalMoves.add(new AttackMove(board, this, destinationCoordinate,
                            board.getTile(destinationCoordinate).getPiece()));
                //en passant
                else if (enPassantRow[pieceCoordinate] && destinationCoordinate == board.getEnPassantTarget())
                    allPossibleLegalMoves.add(new EnPassantMove(board, this, destinationCoordinate,
                            board.getTile(pieceCoordinate + 1).getPiece()));
            }
        }

        return allPossibleLegalMoves;
    }

}
