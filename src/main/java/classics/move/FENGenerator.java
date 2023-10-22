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
 * https://github.com/iasonvog8/JChessEngine/tree/master/src/main/java/classics/move/FENGenerator.java
 *
 * About this class:
 *
 * A class to generate FEN strings from the current board position, allowing you to represent and store games.
 */

package classics.move;

import classics.board.Board;
import classics.board.Tile;

import java.util.concurrent.atomic.AtomicBoolean;

import static classics.move.Move.*;

public class FENGenerator {
    private final StringBuilder FEN;

    public FENGenerator() {
        FEN = new StringBuilder();
    }

    public void translateBoard(final Board board) {
        FEN.setLength(0);
        int distance = 0;
        int rank = 0;
        AtomicBoolean isThereCastlingSide = new AtomicBoolean(false);

        for (Tile tile : board.getChessBoard()) {
            if (tile.isTileOccupied()) {
                if (distance > 0)
                    FEN.append(distance);
                distance = 0;
                FEN.append(tile.getPiece());
            } else distance++;
            rank++;
            if (rank == 8) {
                if (distance > 0)
                    FEN.append(distance);
                FEN.append("/");
                distance = 0;
                rank = 0;
            }
        }
        FEN.setLength(FEN.length() - 1);
        FEN.append(" ");

        if (board.position.isWhiteTurn())
            FEN.append("w");
        else FEN.append("b");

        FEN.append(" ");
        MoveGenerator.generateAllWhitePossibleMoves(board).forEach(move -> {
            if ((move instanceof QueenSideCastling) || (move instanceof KingSideCastling)) {
                final int initialPosition = move.movedPiece.getPieceCoordinate();
                final boolean firstMove = move.movedPiece.isFirstMove();

                TransitionMove transitionMove = new TransitionMove(board);
                transitionMove.createMove(move);

                if (!transitionMove.isDone(board, board.whitePlayer.estimateKingLocation(board))
                    && !transitionMove.isKingInCheck(board.whitePlayer.estimateKingLocation(board))) {
                    if ((move instanceof KingSideCastling)) {
                        isThereCastlingSide.set(true);
                        FEN.append("K");
                    } else {
                        isThereCastlingSide.set(true);
                        FEN.append("Q");
                    }
                }
                transitionMove.revokeMove(move, board, initialPosition, firstMove);
            }
        });

        MoveGenerator.generateAllBlackPossibleMoves(board).forEach(move -> {
            if ((move instanceof QueenSideCastling) || (move instanceof KingSideCastling)) {
                final int initialPosition = move.movedPiece.getPieceCoordinate();
                final boolean firstMove = move.movedPiece.isFirstMove();

                TransitionMove transitionMove = new TransitionMove(board);
                transitionMove.createMove(move);

                if (!transitionMove.isDone(board, board.blackPlayer.estimateKingLocation(board))
                        && !transitionMove.isKingInCheck(board.blackPlayer.estimateKingLocation(board))) {
                    if ((move instanceof KingSideCastling)) {
                        isThereCastlingSide.set(true);
                        FEN.append("k");
                    } else {
                        isThereCastlingSide.set(true);
                        FEN.append("q");
                    }
                }
                transitionMove.revokeMove(move, board, initialPosition, firstMove);
            }
        });

        if (!isThereCastlingSide.get())
            FEN.append("-");

        FEN.append(" ");
        if (board.getEnPassantTarget() == -1)
            FEN.append("-");
        else FEN.append(MoveNotationGenerator.getSquareAlgebraic(board.getEnPassantTarget()));

        FEN.append(" ").append(board.position.getHalfMove()).append(" ").append(board.position.getFullMove());
    }

    public StringBuilder getFEN() {
        return FEN;
    }
}
