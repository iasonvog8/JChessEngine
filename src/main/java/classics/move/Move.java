/*
 * Copyright 2023 iasonvog8 (https://github.com/iasonvog8)
 *
 * Licensed under the Intellij Idea License, Version 2023.2.2 (the "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 *
 * https://www.jetbrains.com/idea/download/?section=windows
 *
 * Class under the Intellij Idea JChessEngine project, Version 2023.2.2 (the "File"); you may edit this file and you may cannot return to
 * the current code. You may obtain a copy of the file at
 *
 * https://github.com/iasonvog8/JChessEngine/tree/master/src/main/java/classics/move/Move.java
 *
 * About this class:
 *
 * Represents a move in chess. It includes information about the source
 * and destination squares, the piece moved, and additional information
 * for special moves like castling or en passant.
 */

package classics.move;

import static classics.move.MoveGenerator.*;
import classics.boardRepresentation.Board;
import classics.piece.Alliance;
import classics.piece.KingSafety;
import classics.piece.Piece;

import java.util.ArrayList;

public abstract class Move {
    public final Board board;
    public final Piece movedPiece;
    public final int destinationCoordinate;

    protected Move(final Board board,
                   final Piece movedPiece,
                   final int destinationCoordinate) {
        this.board = board;
        this.movedPiece = movedPiece;
        this.destinationCoordinate = destinationCoordinate;
    }
    public static class PrimaryMove extends Move { // or MajorMove
        public PrimaryMove(final Board board,
                           final Piece movedPiece,
                           final int destinationCoordinate) {
            super(board, movedPiece, destinationCoordinate);
        }

        @Override
        public String toString() {
            return "Moved piece: " + movedPiece.getPieceType() + " Current coordinate: " + movedPiece.getPieceCoordinate() +
                    " Destination coordinate: " + destinationCoordinate;
        }
    }
    public static class AttackMove extends Move {
        public final Piece attackedPiece;

        public AttackMove(final Board board,
                          final Piece movedPiece,
                          final int destinationCoordinate,
                          final Piece attackedPiece) {
            super(board, movedPiece, destinationCoordinate);
            this.attackedPiece = attackedPiece;
        }

        @Override
        public String toString() {
            return "Moved piece: " + movedPiece.getPieceType() + " Current coordinate: " + movedPiece.getPieceCoordinate() +
                    " Destination coordinate: " + destinationCoordinate + " Attacked Piece: " + attackedPiece;
        }
    }
    public static class PromotionMove extends Move {
        private final Piece promotedPiece;

        public PromotionMove(final Board board,
                             final Piece movedPiece,
                             final int destinationCoordinate,
                             final Piece promotedPiece) {
            super(board, movedPiece, destinationCoordinate);
            this.promotedPiece = promotedPiece;
        }

        public Piece getPromotedPiece() {
            return promotedPiece;
        }
    }
    public static class RevokeMove extends Move {
        public final Piece revokedPiece;
        public RevokeMove(final Move revokeMove,
                          final Piece revokedPiece) {
            super(revokeMove.board, revokeMove.movedPiece, revokeMove.destinationCoordinate);
            this.revokedPiece = revokedPiece;
        }

        @Override
        public String toString() {
            return "Moved Piece: " + movedPiece.getPieceType() + " Destination coordinate: " + destinationCoordinate +
                    " Undo attacked piece: " + revokedPiece;
        }
    }
    public static class TransitionMove implements KingSafety {
        protected Board transitionBoard;

        public TransitionMove(final Board transitionBoard) {
            this.transitionBoard = transitionBoard;
        }
        public void createMove(final Move createMove) {
            transitionBoard.setMove(createMove);
        }

        public void revokeMove(final Move undoMove) {
            Move reverseMove;

            if (undoMove instanceof PrimaryMove)
                reverseMove = new RevokeMove(undoMove, null);
            else if (undoMove instanceof AttackMove)
                reverseMove = new RevokeMove(undoMove, ((AttackMove) undoMove).attackedPiece);
            else reverseMove = null;

            transitionBoard.setMove(reverseMove);
        }

        @Override
        public boolean isOnCheck(final Move kingMove) {
            createMove(kingMove);
            ArrayList<Move> allPossibleOpponentMoves = kingMove.movedPiece.getAlliance() == Alliance.WHITE ?
                    generateAllBlackPossibleMoves(transitionBoard) : generateAllWhitePossibleMoves(transitionBoard);

            for (Move attackerMove : allPossibleOpponentMoves) {
                if (attackerMove.destinationCoordinate == kingMove.destinationCoordinate) {
                    revokeMove(kingMove);
                    return true;
                }
            }
            revokeMove(kingMove);
            return false;
        }

        @Override
        public boolean isThereBlockers(final int checkCoordinate) {
            return false;
        }

        @Override
        public boolean hasEscapeMoves(final Move kingMove) {
            ArrayList<Move> allKingPossibleMoves = kingMove.movedPiece.calculateLegalSquares(transitionBoard);

            for (Move kingTransitionMove : allKingPossibleMoves)
                if (!isOnCheck(kingTransitionMove)) return true;

            return false;
        }

        @Override
        public boolean isDone(final Board board) {
            return false;
        }
    }
}
