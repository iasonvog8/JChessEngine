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
 * https://github.com/iasonvog8/JChessEngine/tree/master/src/main/java/classics/move/MoveGenerator.java
 *
 * About this class:
 *
 * A class responsible for generating all legal moves for the current position
 * on the board. It should consider piece movements, captures, and special moves.
 */

package classics.move;

import classics.board.Board;
import classics.piece.Piece;

import java.util.ArrayList;

public class MoveGenerator {

    public static ArrayList<Move> generateAllPossibleMoves(final Board board) {
        ArrayList<Move> generateMoves = new ArrayList<>();

        for (Piece piece : board.getAllPieces())
            generateMoves.addAll(piece.calculateLegalSquares(board));

        return generateMoves;
    }
    public static ArrayList<Move> generateAllBlackPossibleMoves(final Board board) {
        ArrayList<Move> generateMoves = new ArrayList<>();

        for (Piece piece : board.getAllBlackPieces())
            generateMoves.addAll(piece.calculateLegalSquares(board));

        return generateMoves;
    }
    public static ArrayList<Move> generateAllWhitePossibleMoves(final Board board) {
        ArrayList<Move> generateMoves = new ArrayList<>();

        for (Piece piece : board.getAllWhitePieces())
            generateMoves.addAll(piece.calculateLegalSquares(board));

        return generateMoves;
    }
}
