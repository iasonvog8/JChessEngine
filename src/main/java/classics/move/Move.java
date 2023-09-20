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
    }

    public static class AttackMove extends Move {
        final Piece attackedPiece;

        public AttackMove(final Board board,
                          final Piece movedPiece,
                          final int destinationCoordinate,
                          final Piece attackedPiece) {
            super(board, movedPiece, destinationCoordinate);
            this.attackedPiece = attackedPiece;
        }
    }
    private static class TransitionMove implements KingSafety {
        protected Board currentBoard;
        protected Board transitionBoard;
        protected Move kingMove;

        public TransitionMove(final Move kingMove) {
            this.currentBoard = kingMove.board;
            this.kingMove = kingMove;
            this.transitionBoard = currentBoard.clone();
        }
        public void createMove(final Move transitionMove) {
            transitionBoard.setMove(transitionMove);
        }

        @Override
        public boolean isOnCheck(final Move transitionMove) {
            ArrayList<Move> allPossibleOpponentMoves = transitionMove.movedPiece.getAlliance() == Alliance.WHITE ?
                generateAllBlackPossibleMoves(transitionBoard) : generateAllWhitePossibleMoves(transitionBoard);

            for (Move attackerMove : allPossibleOpponentMoves)
                if (attackerMove.destinationCoordinate == transitionMove.destinationCoordinate)
                    return true;
            return false;
        }

        @Override
        public boolean isThereBlockers(final int checkCoordinate) {
            return false;
        }

        @Override
        public boolean hasEscapeMoves() {
            Piece king = kingMove.movedPiece;
            ArrayList<Move> allKingPossibleMoves = king.calculateLegalSquares(currentBoard);

            for (Move kingMove : allKingPossibleMoves) {
                transitionBoard = currentBoard.clone();
                createMove(kingMove);
                if (!isOnCheck(kingMove)) return true;
            }

            return false;
        }

        @Override
        public boolean isDone(final Board board) {
            return false;
        }
    }
}
