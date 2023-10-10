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
        Piece attackedPiece;
        Move move;
        int destinationCoordinate;
        int kingSideCastlingCoordinate = getAlliance() == Alliance.WHITE ? 63 : 7;
        int queenSideCastlingCoordinate = getAlliance() == Alliance.WHITE ? 56 : 0;

        for (int dir : CANDIDATE_MOVE_DIRECTIONS) {
            destinationCoordinate = pieceCoordinate + dir;

            if (isNotFirstColumnExclusive(pieceCoordinate, dir)  &&
                isNotEighthColumnExclusive(pieceCoordinate, dir)&&
                isValidTile(destinationCoordinate)) {

                if (!board.getTile(destinationCoordinate).isTileOccupied()) {
                    move = new PrimaryMove(board, this, destinationCoordinate);

                    if (isSafeKing(board, move, isFirstMove, pieceCoordinate))
                        allPossibleLegalMoves.add(move);

                } if (board.getTile(destinationCoordinate).isTileOccupied()) {
                    if (board.getTile(destinationCoordinate).getPiece().getAlliance() !=
                        board.getTile(pieceCoordinate).getPiece().getAlliance()) {
                        attackedPiece = board.getTile(destinationCoordinate).getPiece();
                        move = new AttackMove(board, this, destinationCoordinate, attackedPiece);

                        if (isSafeKing(board, move, isFirstMove, pieceCoordinate))
                            allPossibleLegalMoves.add(move);
                    }
                }
            }
        }

        TransitionMove transitionMove = new TransitionMove(board);
        if (isThereKingSideRook(board, getAlliance()) && isAvailableKingCorridor(board, getAlliance()) && !transitionMove.isKingInCheck(this)) {
            if (Objects.requireNonNull(getKingSideRook(board, getAlliance())).isFirstMove() && this.isFirstMove()) {
                if (isSafeKing(board, new PrimaryMove(board, this, kingSideCastlingCoordinate - 1), isFirstMove, pieceCoordinate))
                    allPossibleLegalMoves.add(new KingSideCastling(board, this, kingSideCastlingCoordinate - 1,
                        new PrimaryMove(board, getKingSideRook(board, getAlliance()), kingSideCastlingCoordinate - 2)));
            }

        }
        transitionMove = new TransitionMove(board);
        if (isThereQueenSideRook(board, getAlliance()) && isAvailableQueenCorridor(board, getAlliance()) && !transitionMove.isKingInCheck(this)) {
            if (Objects.requireNonNull(getQueenSideRook(board, getAlliance())).isFirstMove() && this.isFirstMove()) {
                if (isSafeKing(board, new PrimaryMove(board, this, queenSideCastlingCoordinate + 2), isFirstMove, pieceCoordinate))
                    allPossibleLegalMoves.add(new QueenSideCastling(board, this, queenSideCastlingCoordinate + 2,
                        new PrimaryMove(board, getQueenSideRook(board, getAlliance()), queenSideCastlingCoordinate + 3)));
            }
        }

        return allPossibleLegalMoves;
    }


    private boolean isSafeKing(final Board board, final Move move, final boolean isKingFirstMove, final int initialPosition) {
        TransitionMove transitionMove;
        transitionMove = new TransitionMove(board);
        transitionMove.createMove(move);

        boolean isSafe = !transitionMove.isKingInCheck(this);
        transitionMove.revokeMove(move, board, initialPosition, isKingFirstMove);

        return isSafe;
    }
    private boolean isNotFirstColumnExclusive(final int currentPosition, final int direction) {
        return (!FIRST_COLUMN[currentPosition] || direction != -9 && direction != 7 && direction != -1);
    }
    private boolean isNotEighthColumnExclusive(final int currentPosition, final int direction) {
        return (!EIGHTH[currentPosition] || direction != 9 && direction != -7 && direction != 1);
    }
}
