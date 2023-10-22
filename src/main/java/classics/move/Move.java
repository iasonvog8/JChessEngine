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
import static classics.board.Tile.*;
import static classics.piece.PieceType.KING;

import classics.board.Board;
import classics.piece.*;

import java.util.ArrayList;

public abstract class Move {
    protected final Board board;
    protected final Piece movedPiece;
    protected final int destinationCoordinate;

    protected Move(final Board board,
                   final Piece movedPiece,
                   final int destinationCoordinate) {
        this.board = board;
        this.movedPiece = movedPiece;
        this.destinationCoordinate = destinationCoordinate;
    }

    public Board getBoard() {
        return board;
    }

    public Piece getMovedPiece() {
        return movedPiece;
    }

    public int getDestinationCoordinate() {
        return destinationCoordinate;
    }

    public static class MajorMove extends Move {
        public MajorMove(final Board board,
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
    public static class EnPassantMove extends Move {
        public final Piece attackedPawn;

        public EnPassantMove(final Board board,
                             final Piece movedPiece,
                             final int destinationCoordinate,
                             final Piece attackedPawn) {
            super(board, movedPiece, destinationCoordinate);
            this.attackedPawn = attackedPawn;
        }

        @Override
        public String toString() {
            return "EnPassantMove{" +
                    "attackedPawn=" + attackedPawn +
                    ", movedPiece=" + movedPiece +
                    ", destinationCoordinate=" + destinationCoordinate +
                    '}';
        }
    }
    public static class Castling extends Move {
        public final MajorMove rookMove;

        public Castling(final Board board,
                        final Piece movedPiece,
                        final int destinationCoordinate,
                        final MajorMove rookMove) {
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
                                final MajorMove rookMove) {
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

        @Override
        public String toString() {
            return "O-O";
        }
    }
    public static class QueenSideCastling extends Castling {
        public QueenSideCastling(final Board board,
                                final Piece movedPiece,
                                final int destinationCoordinate,
                                final MajorMove rookMove) {
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

        @Override
        public String toString() {
            return "O-O-O";
        }
    }
    public static class TransitionMove implements KingSafety {
        protected Board transitionBoard;

        public TransitionMove(final Board transitionBoard) {
            this.transitionBoard = transitionBoard;
        }
        public void createMove(final Move createMove) {
            transitionBoard.execute(createMove);
        }

        public void revokeMove(final Move revokeMove, final Board board, final int initialPosition, final boolean isFirstMove) {
            board.execute(new MajorMove(board, revokeMove.movedPiece, initialPosition));
            revokeMove.movedPiece.setFirstMove(isFirstMove);

            if (revokeMove instanceof AttackMove) {
                int revokedAttackedPieceCoordinate = ((AttackMove) revokeMove).attackedPiece.getPieceCoordinate();
                board.setTile(new OccupiedTile(revokedAttackedPieceCoordinate, ((AttackMove) revokeMove).attackedPiece));
            }else if (revokeMove instanceof EnPassantMove) {
                int revokedAttackedPieceCoordinate = ((EnPassantMove) revokeMove).attackedPawn.getPieceCoordinate();

                board.setTile(new OccupiedTile(revokedAttackedPieceCoordinate, ((EnPassantMove) revokeMove).attackedPawn));
                board.setEnPassantTarget(revokeMove.destinationCoordinate);
            }else if (revokeMove instanceof PromotionMove) {
                board.setTile(new EmptyTile(revokeMove.destinationCoordinate));
            }else if (revokeMove instanceof QueenSideCastling) {
                board.execute(new MajorMove(board, ((QueenSideCastling) revokeMove).rookMove.getMovedPiece(),
                        ((QueenSideCastling) revokeMove).rookMove.getMovedPiece().getAlliance().isWhite() ? 56 : 0));
                ((Castling) revokeMove).rookMove.getMovedPiece().setFirstMove(true);

            }else if (revokeMove instanceof KingSideCastling) {
                board.execute(new MajorMove(board, ((KingSideCastling) revokeMove).rookMove.getMovedPiece(),
                        ((KingSideCastling) revokeMove).rookMove.getMovedPiece().getAlliance().isWhite() ? 63 : 7));
                ((Castling) revokeMove).rookMove.getMovedPiece().setFirstMove(true);
            }
        }

        @Override
        public boolean isKingInCheck(final King king) {
            ArrayList<Move> allOpponentLegalMoves = !king.getAlliance().isWhite() ?
                    generateAllWhiteMovesExceptKing(transitionBoard) : generateAllBlackMovesExceptKing(transitionBoard);

            for (Move attackerMove : allOpponentLegalMoves)
                if (attackerMove.destinationCoordinate == king.getPieceCoordinate()) return true;

            return false;
        }

        @Override
        public ArrayList<Move> getBlockers(final Board board, final King king) {
            ArrayList<Move> allBlockersPossibleMoves = new ArrayList<>();
            ArrayList<Move> allPlayerLegalMoves = king.getAlliance().isWhite() ?
                    generateAllWhitePossibleMoves(transitionBoard) : generateAllBlackPossibleMoves(transitionBoard);

            int initialPosition;
            boolean isFirstMove;

            for (Move transitionMove : allPlayerLegalMoves) {
                if (transitionMove.movedPiece.getPieceType() != KING &&
                        !(transitionMove instanceof QueenSideCastling) &&
                        !(transitionMove instanceof KingSideCastling)) {

                    initialPosition = transitionMove.movedPiece.getPieceCoordinate();
                    isFirstMove = transitionMove.movedPiece.isFirstMove();

                    createMove(transitionMove);
                    if (!isKingInCheck(king)) allBlockersPossibleMoves.add(transitionMove);
                    revokeMove(transitionMove, transitionBoard, initialPosition, isFirstMove);
                }
            }
            return allBlockersPossibleMoves;
        }

        @Override
        public boolean hasEscapeMoves(final King king) {
            ArrayList<Move> allKingPossibleMoves = king.calculateLegalSquares(transitionBoard);
            int initialPosition;
            boolean isFirstMove;

            for (Move kingTransitionMove : allKingPossibleMoves) {
                initialPosition = kingTransitionMove.movedPiece.getPieceCoordinate();
                isFirstMove = kingTransitionMove.movedPiece.isFirstMove();

                createMove(kingTransitionMove);
                if (!isKingInCheck(king)) {
                    revokeMove(kingTransitionMove, transitionBoard, initialPosition, isFirstMove);
                    return true;
                }
                revokeMove(kingTransitionMove, transitionBoard, initialPosition, isFirstMove);
            }

            return false;
        }

        @Override
        public boolean isDone(final Board board, final King king) {
            return !hasEscapeMoves(king) && getBlockers(board, king).isEmpty() && isKingInCheck(king);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof PromotionMove other)) {
            Move other = (Move) obj;
            return this.destinationCoordinate == other.destinationCoordinate
                    && this.movedPiece == other.movedPiece
                    && this.board == other.board;
        }

        return this.destinationCoordinate == other.destinationCoordinate
                && this.movedPiece == other.movedPiece
                && this.board == other.board
                && null != other.promotedPiece;
    }
}
