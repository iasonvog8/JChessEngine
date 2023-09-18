package classics.piece;

import classics.boardRepresentation.Board;
import classics.move.Move;

import java.util.ArrayList;

import static classics.boardRepresentation.BoardUtils.*;
import static classics.boardRepresentation.BoardUtils.isValidTile;
import static classics.move.MoveGenerator.*;
import static classics.piece.Alliance.*;

public class King extends Piece implements KingSafety{
    private final int[] directions = {-9, -8, -7, -1, 1, 7, 8, 9};
    public King(final int coordinate, final Alliance alliance) {
        super(coordinate, alliance, PieceType.KING);
    }

    @Override
    public ArrayList<Move> calculateLegalSquares(Board board) {
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

    @Override
    public boolean isOnCheck(Board board, int coordinate) {
        ArrayList<Move> opponentMoves = alliance == WHITE ? generateAllBlackPossibleMoves(board) : generateAllWhitePossibleMoves(board);

        for (Move move : opponentMoves)
            if (move.destinationCoordinate == coordinate) return true;

        return false;
    }

    @Override
    public boolean isThereBlockers(int checkCoordinate) {
        return false;
    }

    @Override
    public ArrayList<Move> calculateEscapeMoves(Board board, int kingLocation) {
        return null;
    }

    private boolean isNotFirstColumnExclusive(int currentPosition, int direction) {
        return (!FIRST_COLUMN[currentPosition] || direction != -9 && direction != 7 && direction != -1);
    }
    private boolean isNotSeventhColumnExclusive(int currentPosition, int direction) {
        return (!SEVENTH_COLUMN[currentPosition] || direction != 9 && direction != -7 && direction != 1);
    }
}
