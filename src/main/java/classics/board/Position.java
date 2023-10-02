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
    private Board gamePosition;
    private boolean isWhiteTurn;
    private int halfMove;
    private int fullMove;
    public Position(final Board board) {
        this.gamePosition = board;
        this.isWhiteTurn = true;
        this.halfMove = 0;
        this.fullMove = 0;
    }

    public Board getGamePosition() {
        return gamePosition;
    }

    public void setGamePosition(Board gamePosition) {
        this.gamePosition = gamePosition;
    }

    public boolean isWhiteTurn() {
        return isWhiteTurn;
    }

    public void setWhiteTurn(boolean whiteTurn) {
        isWhiteTurn = whiteTurn;
    }

    public int getHalfMove() {
        return halfMove;
    }

    public void setHalfMove(int halfMove) {
        this.halfMove = halfMove;
    }

    public int getFullMove() {
        return fullMove;
    }

    public void setFullMove(int fullMove) {
        this.fullMove = fullMove;
    }
}
