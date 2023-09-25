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
 * https://github.com/iasonvog8/JChessEngine/tree/master/src/main/java/classics/boardRepresentation/Position.java
 *
 * About this class:
 *
 * Stores the current position on the board, including information about
 * which player's turn it is, the castling rights, en passant target square,
 * and half-move and full-move counters for the 50-move rule and move number.
 */

package classics.board;

public class Position {
    protected boolean isWhiteTurn;
    protected boolean attack;
    protected boolean kingSideCastling;
    protected boolean queenSideCastling;
    protected int enPassantTarget;
    protected int halfMove;
    protected int fullMove;

    public Position(final boolean isWhiteTurn,
                    final boolean attack,
                    final boolean kingSideCastling,
                    final boolean queenSideCastling,
                    final int enPassantTarget,
                    final int halfMove, final int fullMove) {
        this.isWhiteTurn = isWhiteTurn;
        this.attack = attack;
        this.kingSideCastling = kingSideCastling;
        this.queenSideCastling = queenSideCastling;
        this.enPassantTarget = enPassantTarget;
        this.halfMove = halfMove;
        this.fullMove = fullMove;
    }

}
