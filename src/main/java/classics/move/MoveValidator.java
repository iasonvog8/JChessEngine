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
import classics.piece.Alliance;

import java.util.ArrayList;

public class MoveValidator {
    public static boolean isValidMove(final Move playerMove,
                                      final Alliance isWhitePlayer,
                                      final Board board) {
        ArrayList<Move> allPossiblePlayerMove = isWhitePlayer == Alliance.WHITE ? MoveGenerator.generateAllWhitePossibleMoves(board) :
                                                                MoveGenerator.generateAllBlackPossibleMoves(board);

        for (Move legalMove : allPossiblePlayerMove) {
            System.out.println(legalMove + " " + playerMove);
            if (legalMove.equals(playerMove)) return true;
        }
        return false;
    }
}
