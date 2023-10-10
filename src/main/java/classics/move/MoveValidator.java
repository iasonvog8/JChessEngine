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
 * https://github.com/iasonvog8/JChessEngine/tree/master/src/main/java/classics/move/MoveValidator.java
 *
 * About this class:
 *
 * Ensures that moves generated are legal, i.e., they don't put
 * the player's own king in check. It can also check for other
 * legality conditions like en passant captures and castling.
 */

package classics.move;

import classics.board.Board;
import player.Player;

import java.util.ArrayList;

import static classics.move.Move.*;

public class MoveValidator {
    public static boolean isValidMove(final Move playerMove,
                                      final Player player,
                                      final Board board) {
        ArrayList<Move> allPossiblePlayerMove = player.isWhitePlayer() ? MoveGenerator.generateAllWhitePossibleMoves(board) :
                                                                MoveGenerator.generateAllBlackPossibleMoves(board);

        if (!player.isPlayerInCheck(board)) {
            if (player.isWhitePlayer() == board.position.isWhiteTurn()) {
                for (Move legalMove : allPossiblePlayerMove) {
                    if (legalMove.equals(playerMove)) return true;
                }
            }
        } else {
            TransitionMove transitionMove = new Move.TransitionMove(board);
            ArrayList<Move> safeMoves = transitionMove.getBlockers(board, player.estimateKingLocation(board));

            if (transitionMove.hasEscapeMoves(player.estimateKingLocation(board)))
                safeMoves.addAll(player.estimateKingLocation(board).calculateLegalSquares(board));

            if (player.isWhitePlayer() == board.position.isWhiteTurn()){
                for (Move safeMove : safeMoves) {
                    if (safeMove.equals(playerMove)) return true;
                }
            }
        }

        return false;
    }

}
