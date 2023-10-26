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
 * https://github.com/iasonvog8/JChessEngine/tree/master/src/main/java/classics/game/GameHistory.java
 *
 * About this class:
 *
 * A class to store a history of moves made during the game. This can
 * be used for move undo, draw by repetition checks, and generating a PGN
 * (Portable Game Notation) representation of the game.
 */

package classics.game;

import classics.board.Position;
import classics.move.Move;
import classics.move.MoveNotationGenerator;

import java.util.*;

public class GameHistory {
    private static final LinkedHashMap<Move, String> gameHistory = new LinkedHashMap<>();
    private static final LinkedHashMap<Integer, Boolean> undoMoves = new LinkedHashMap<>();

    public static void addMoveToHistory(final Move move) {
        gameHistory.put(move, MoveNotationGenerator.translateMoveToAlgebraic(move));
        undoMoves.put(move.getMovedPiece().getPieceCoordinate(), move.getMovedPiece().isFirstMove());
    }

    public static void undoMove() {
        if (!gameHistory.isEmpty()) {
            Move undoMove = null;
            int initialPosition = 0;
            boolean isFirstMove = false;

            List<Map.Entry<Move, String>> entryList = gameHistory.entrySet().stream().toList();

            if (!entryList.isEmpty()) {
                Map.Entry<Move, String> lastEntry = entryList.get(entryList.size() - 1);
                undoMove = lastEntry.getKey();
            }

            List<Map.Entry<Integer, Boolean>> undoEntryList = undoMoves.entrySet().stream().toList();

            if (!undoEntryList.isEmpty()) {
                Map.Entry<Integer, Boolean> lastEntry = undoEntryList.get(undoEntryList.size() - 1);
                initialPosition = lastEntry.getKey();
                isFirstMove = lastEntry.getValue();
            }

            if (undoMove != null) {
                Move.TransitionMove undoTransistor = new Move.TransitionMove(undoMove.getBoard());
                undoTransistor.revokeMove(undoMove, undoMove.getBoard(), initialPosition, isFirstMove);

                final Position gameState = undoMove.getBoard().position;
                gameState.setWhiteTurn(!gameState.isWhiteTurn());

                if (gameState.getHalfMove() != 0) {
                    gameState.setHalfMove(gameState.getHalfMove() - 1);

                    if (gameState.getHalfMove() % 2 != 0 && gameState.getFullMove() != 0)
                        gameState.setHalfMove(gameState.getHalfMove() - 1);
                }

                List<Map.Entry<Move, String>> removalEntryList = new ArrayList<>(gameHistory.entrySet());

                if (!removalEntryList.isEmpty())
                    removalEntryList.remove(removalEntryList.size() - 1);
            }
        }
    }

    public static HashMap<Move, String> getGameHistory() {
        return gameHistory;
    }
}
