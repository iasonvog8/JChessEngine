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
 * https://github.com/iasonvog8/JChessEngine/tree/master/src/main/java/classics/boardRepresentation/Board.java
 *
 * About this class:
 *
 * Represents the chessboard itself, including the current placement of
 * pieces and other board-related information. This class manages the state
 * of the game.
 */

package classics.boardRepresentation;

import java.util.ArrayList;

import static classics.boardRepresentation.BoardUtils.*;
import static classics.boardRepresentation.Tile.*;

public class Board {
    private Tile[] chessBoard;
    public Board() {
        chessBoard = new Tile[BOARD_LENGTH];
    }

    private void initBoard() {
        for (int t = 0; t < BOARD_LENGTH; t++)
            chessBoard[t] = new EmptyTile(t);
    }
    public void setBoard(ArrayList<ChessBitSet> bitSet) {
        initBoard();
        for (ChessBitSet bit : bitSet)
            chessBoard[bit.getBit().currentCoordinate] = new OccupiedTile(bit.getBit().currentCoordinate, bit.getBit());
    }

    public Tile[] getChessBoard() {
        return chessBoard;
    }

    public void setChessBoard(Tile[] chessBoard) {
        this.chessBoard = chessBoard;
    }
}
