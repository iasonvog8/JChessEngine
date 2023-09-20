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

package classics.boardRepresentation;

import classics.move.Move;
import classics.move.MoveValidator;
import player.BlackPlayer;
import player.WhitePlayer;

public class Position {
    boolean isWhiteTurn;
    final WhitePlayer whitePlayer;
    final BlackPlayer blackPlayer;
    final Board board;

    public Position(final WhitePlayer whitePlayer, final BlackPlayer blackPlayer, final Board board) {
        this.isWhiteTurn = true;
        this.whitePlayer = whitePlayer;
        this.blackPlayer = blackPlayer;
        this.board = board;
    }

    public void changeTurn() {
        isWhiteTurn = !isWhiteTurn;
    }

    public void execute(Move move) {
        if (isWhiteTurn && MoveValidator.isValidMove(move, whitePlayer.isWhitePlayer(), board))
            board.setMove(move);
        else if (!isWhiteTurn && MoveValidator.isValidMove(move, blackPlayer.isWhitePlayer(), board))
                board.setMove(move);
    }
}
