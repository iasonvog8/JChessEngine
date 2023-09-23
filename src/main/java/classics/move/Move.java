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
import classics.piece.PieceType;

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
        public Piece promotedPiece;

        public PromotionMove(final Board board,
                             final Piece movedPiece,
                             final int destinationCoordinate,
                             final Piece promotedPiece) {
            super(board, movedPiece, destinationCoordinate);
            this.promotedPiece = promotedPiece;
        }

        public void setPromotedPiece(Piece promotedPieceType) {
            this.promotedPiece =  promotedPieceType;
        }

        @Override
        public String toString() {
            return "Moved piece: " + movedPiece.getPieceType() + " Current coordinate: " + movedPiece.getPieceCoordinate() +
                    " Destination coordinate: " + destinationCoordinate + " Promoted Piece: " + promotedPiece;
        }
    }
    public static class Castling extends Move {
        public final PrimaryMove rookMove;

        public Castling(final Board board,
                        final Piece movedPiece,
                        final int destinationCoordinate,
                        final PrimaryMove rookMove) {
            super(board, movedPiece, destinationCoordinate);
            this.rookMove = rookMove;
        }

        @Override
        public String toString() {
            return "Moved piece: " + movedPiece.getPieceType() + " Current coordinate: " + movedPiece.getPieceCoordinate() +
                    " Destination coordinate: " + destinationCoordinate + " Rook Piece: " + rookMove.movedPiece +
                    " Rook current coordinate: " + rookMove.movedPiece.getPieceCoordinate() + " Rook destination coordinate: " +
                    rookMove.destinationCoordinate;
        }
    }
    public static class KingSideCastling extends Castling {
        public KingSideCastling(final Board board,
                                final Piece movedPiece,
                                final int destinationCoordinate,
                                final PrimaryMove rookMove) {
            super(board, movedPiece, destinationCoordinate, rookMove);
        }

        public static Piece getKingSideRook(final Board board, final Alliance alliance) {
            if (alliance == Alliance.WHITE && board.getTile(63).isTileOccupied())
                if (board.getTile(63).getPiece().getAlliance() == alliance)
                    return board.getTile(63).getPiece();
            if (alliance == Alliance.BLACK && board.getTile(7).isTileOccupied())
                if (board.getTile(7).getPiece().getAlliance() == alliance)
                    return board.getTile(7).getPiece();

            return null;
        }


        public static boolean isThereKingSideRook(final Board board, final Alliance alliance) {
            if (alliance == Alliance.WHITE && board.getTile(63).isTileOccupied())
                return board.getTile(63).getPiece().getAlliance() == alliance;
            if (alliance == Alliance.BLACK && board.getTile(7).isTileOccupied())
                return board.getTile(7).getPiece().getAlliance() == alliance;

            return false;
        }

        public static boolean isAvailableKingCorridor(final Board board, final Alliance alliance) {
            if (alliance == Alliance.WHITE)
                return !board.getTile(62).isTileOccupied() && !board.getTile(61).isTileOccupied();
            if (alliance == Alliance.BLACK)
                return !board.getTile(6).isTileOccupied() && !board.getTile(5).isTileOccupied();

            return false;
        }
    }
    public static class QueenSideCastling extends Castling {
        public QueenSideCastling(final Board board,
                                final Piece movedPiece,
                                final int destinationCoordinate,
                                final PrimaryMove rookMove) {
            super(board, movedPiece, destinationCoordinate, rookMove);
        }

        public static Piece getQueenSideRook(final Board board, final Alliance alliance) {
            if (alliance == Alliance.WHITE && board.getTile(56).isTileOccupied())
                if (board.getTile(56).getPiece().getAlliance() == alliance)
                    return board.getTile(56).getPiece();
            if (alliance == Alliance.BLACK && board.getTile(0).isTileOccupied())
                if (board.getTile(0).getPiece().getAlliance() == alliance)
                    return board.getTile(0).getPiece();

            return null;
        }


        public static boolean isThereQueenSideRook(final Board board, final Alliance alliance) {
            if (alliance == Alliance.WHITE && board.getTile(56).isTileOccupied())
                return board.getTile(56).getPiece().getAlliance() == alliance;
            if (alliance == Alliance.BLACK && board.getTile(0).isTileOccupied())
                return board.getTile(0).getPiece().getAlliance() == alliance;

            return false;
        }

        public static boolean isAvailableQueenCorridor(final Board board, final Alliance alliance) {
            if (alliance == Alliance.WHITE)
                return !board.getTile(57).isTileOccupied() && !board.getTile(58).isTileOccupied() && !board.getTile(59).isTileOccupied();
            if (alliance == Alliance.BLACK)
                return !board.getTile(1).isTileOccupied() && !board.getTile(2).isTileOccupied() && !board.getTile(3).isTileOccupied();

            return false;
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

        public void revokeMove(final Board revokeBoard) {
            try {
                transitionBoard = revokeBoard.clone();
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public boolean isOnCheck(final Move kingMove) {
            try {
                final Board copyBoard = kingMove.board.clone();
                boolean isFirstMove = kingMove.movedPiece.isFirstMove();
                createMove(kingMove);

                ArrayList<Move> allPossibleOpponentMoves = kingMove.movedPiece.getAlliance() == Alliance.WHITE ?
                        generateAllBlackPossibleMoves(transitionBoard) : generateAllWhitePossibleMoves(transitionBoard);

                for (Move attackerMove : allPossibleOpponentMoves) {
                    if (attackerMove instanceof AttackMove) {
                        if (((AttackMove) attackerMove).attackedPiece.getPieceType() == PieceType.KING) {
                            revokeMove(copyBoard);
                            kingMove.movedPiece.setFirstMove(isFirstMove);
                            return true;
                        }
                    }
                }
                kingMove.movedPiece.setFirstMove(isFirstMove);
                revokeMove(copyBoard);
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }
            return false;
        }

        @Override
        public ArrayList<Move> getBlockers(final Board board, final Piece king) {
            ArrayList<Move> allBlockersPossibleMoves = new ArrayList<>();
            try {
                Board copyBoard = board.clone();
                ArrayList<Move> allPlayerLegalMoves = king.getAlliance() == Alliance.WHITE ?
                        generateAllWhitePossibleMoves(copyBoard) : generateAllBlackPossibleMoves(copyBoard);
                ArrayList<Move> allOpponentPossibleMoves;

                boolean isKingSafe;
                boolean isFirsMove;

                for (Move blocker : allPlayerLegalMoves) {
                    isFirsMove = blocker.movedPiece.isFirstMove();

                    createMove(blocker);
                    allOpponentPossibleMoves = king.getAlliance() == Alliance.BLACK ?
                            generateAllWhitePossibleMoves(transitionBoard) : generateAllBlackPossibleMoves(transitionBoard);

                    isKingSafe = true;
                    for (Move attackerMove : allOpponentPossibleMoves) {
                        if (attackerMove instanceof AttackMove) {
                            System.out.println(attackerMove);
                            if (((AttackMove) attackerMove).attackedPiece.getPieceType() == king.getPieceType()) {
                                isKingSafe = false;
                                break;
                            }
                        }
                    }
                    if (isKingSafe)
                        allBlockersPossibleMoves.add(blocker);
                    revokeMove(copyBoard);
                    blocker.movedPiece.setFirstMove(isFirsMove);
                }
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }
            return allBlockersPossibleMoves;
        }

        @Override
        public boolean hasEscapeMoves(final Piece king) {
            ArrayList<Move> allKingPossibleMoves = king.calculateLegalSquares(transitionBoard);

            for (Move kingTransitionMove : allKingPossibleMoves)
                if (!isOnCheck(kingTransitionMove)) return true;

            return false;
        }

        @Override
        public boolean isDone(final Board board, final Piece king) {
            return hasEscapeMoves(king) && !getBlockers(board, king).isEmpty() && !isOnCheck(new PrimaryMove(board, king, king.getPieceCoordinate()));
        }
    }
}
