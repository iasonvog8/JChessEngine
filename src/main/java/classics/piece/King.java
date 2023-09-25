package classics.piece;

import classics.board.Board;
import classics.move.Move;

import java.util.ArrayList;
import java.util.Objects;

import static classics.board.BoardUtils.*;
import static classics.move.Move.*;
import static classics.move.Move.KingSideCastling.*;
import static classics.move.Move.QueenSideCastling.*;

public class King extends Piece {
    private final int[] CANDIDATE_MOVE_DIRECTIONS = {-9, -8, -7, -1, 1, 7, 8, 9};
    public King(final int coordinate, final Alliance alliance) {
        super(coordinate, alliance, PieceType.KING);
    }

    @Override
    public ArrayList<Move> calculateLegalSquares(Board board) {
        ArrayList<Move> allPossibleLegalMoves = new ArrayList<>();
        TransitionMove transitionMove;
        int destinationCoordinate;
        int kingSideCastlingCoordinate = getAlliance() == Alliance.WHITE ? 63 : 7;
        int queenSideCoordinate = getAlliance() == Alliance.WHITE ? 56 : 0;

        for (int dir : CANDIDATE_MOVE_DIRECTIONS) {
            destinationCoordinate = pieceCoordinate + dir;

            if (isNotFirstColumnExclusive(pieceCoordinate, dir)  &&
                isNotEighthColumnExclusive(pieceCoordinate, dir)&&
                isValidTile(destinationCoordinate)) {

                if (!board.getTile(destinationCoordinate).isTileOccupied()) {
                    try {
                        transitionMove = new TransitionMove(board.clone());
                    } catch (CloneNotSupportedException e) {
                        throw new RuntimeException(e);
                    }

                    if (!transitionMove.isOnCheck(new PrimaryMove(board, this, destinationCoordinate)))
                        allPossibleLegalMoves.add(new PrimaryMove(board, this, destinationCoordinate));

                } if (board.getTile(destinationCoordinate).isTileOccupied()) {
                    if (board.getTile(destinationCoordinate).getPiece().getAlliance() !=
                            board.getTile(pieceCoordinate).getPiece().getAlliance()) {
                        try {
                            transitionMove = new TransitionMove(board.clone());
                        } catch (CloneNotSupportedException e) {
                            throw new RuntimeException(e);
                        }

                        if (!transitionMove.isOnCheck(new AttackMove(board, this, destinationCoordinate,
                                board.getTile(destinationCoordinate).getPiece())))
                            allPossibleLegalMoves.add(new AttackMove(board, this, destinationCoordinate,
                                board.getTile(destinationCoordinate).getPiece()));
                    }
                }
            }
        }
        if (isThereKingSideRook(board, getAlliance()) && isAvailableKingCorridor(board, getAlliance())) {
            if (Objects.requireNonNull(getKingSideRook(board, getAlliance())).isFirstMove() && this.isFirstMove()) {
                try {
                    transitionMove = new TransitionMove(board.clone());
                } catch (CloneNotSupportedException e) {
                    throw new RuntimeException(e);
                }
                if (!transitionMove.isOnCheck(new PrimaryMove(board, this, kingSideCastlingCoordinate - 1)))
                    allPossibleLegalMoves.add(new KingSideCastling(board, this, kingSideCastlingCoordinate - 1,
                            new PrimaryMove(board, getKingSideRook(board, getAlliance()), kingSideCastlingCoordinate - 2)));
            }

        }
        if (isThereQueenSideRook(board, getAlliance()) && isAvailableQueenCorridor(board, getAlliance())) {
            if (Objects.requireNonNull(getQueenSideRook(board, getAlliance())).isFirstMove() && this.isFirstMove()) {
                try {
                    transitionMove = new TransitionMove(board.clone());
                } catch (CloneNotSupportedException e) {
                    throw new RuntimeException(e);
                }
                if (!transitionMove.isOnCheck(new PrimaryMove(board, this, queenSideCoordinate + 2)))
                    allPossibleLegalMoves.add(new QueenSideCastling(board, this, queenSideCoordinate + 2,
                            new PrimaryMove(board, getQueenSideRook(board, getAlliance()), queenSideCoordinate + 3)));
            }
        }

        return allPossibleLegalMoves;
    }

    private boolean isNotFirstColumnExclusive(final int currentPosition, final int direction) {
        return (!FIRST_COLUMN[currentPosition] || direction != -9 && direction != 7 && direction != -1);
    }
    private boolean isNotEighthColumnExclusive(final int currentPosition, final int direction) {
        return (!EIGHTH[currentPosition] || direction != 9 && direction != -7 && direction != 1);
    }
}
