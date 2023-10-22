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
 * https://github.com/iasonvog8/JChessEngine/tree/master/src/main/java/classics/move/FENParser.java
 *
 * About this class:
 *
 * A class to parse FEN (Forsyth-Edwards Notation) strings and initialize the board to a given position.
 */

package classics.move;

import classics.board.Board;
import classics.board.Tile;
import classics.piece.*;

public class FENParser {
    public static void setBoard(final StringBuilder FEN, final Board board) {
        int tileCoordinate = 0;
        int charIndex = 0;
        board.initBoard();

        for (int fenChar = 0; fenChar < FEN.length(); fenChar++) {
            if (FEN.charAt(fenChar) == ' ') {
                charIndex = fenChar + 1;
                break;
            }
            if ('/' != FEN.charAt(fenChar)) {
                if (!Character.isDigit(FEN.charAt(fenChar))) {
                    switch (FEN.charAt(fenChar)) {
                        case 'P' -> board.setTile(new Tile.OccupiedTile(tileCoordinate, new Pawn(tileCoordinate, Alliance.WHITE)));
                        case 'R' -> board.setTile(new Tile.OccupiedTile(tileCoordinate, new Rook(tileCoordinate, Alliance.WHITE)));
                        case 'N' -> board.setTile(new Tile.OccupiedTile(tileCoordinate, new Knight(tileCoordinate, Alliance.WHITE)));
                        case 'B' -> board.setTile(new Tile.OccupiedTile(tileCoordinate, new Bishop(tileCoordinate, Alliance.WHITE)));
                        case 'Q' -> board.setTile(new Tile.OccupiedTile(tileCoordinate, new Queen(tileCoordinate, Alliance.WHITE)));
                        case 'K' -> board.setTile(new Tile.OccupiedTile(tileCoordinate, new King(tileCoordinate, Alliance.WHITE)));
                        case 'p' -> board.setTile(new Tile.OccupiedTile(tileCoordinate, new Pawn(tileCoordinate, Alliance.BLACK)));
                        case 'r' -> board.setTile(new Tile.OccupiedTile(tileCoordinate, new Rook(tileCoordinate, Alliance.BLACK)));
                        case 'n' -> board.setTile(new Tile.OccupiedTile(tileCoordinate, new Knight(tileCoordinate, Alliance.BLACK)));
                        case 'b' -> board.setTile(new Tile.OccupiedTile(tileCoordinate, new Bishop(tileCoordinate, Alliance.BLACK)));
                        case 'q' -> board.setTile(new Tile.OccupiedTile(tileCoordinate, new Queen(tileCoordinate, Alliance.BLACK)));
                        case 'k' -> board.setTile(new Tile.OccupiedTile(tileCoordinate, new King(tileCoordinate, Alliance.BLACK)));
                    }
                    tileCoordinate++;
                }else tileCoordinate += FEN.charAt(fenChar) - '0';
            }
        }
        board.position.setWhiteTurn(FEN.charAt(charIndex) == 'w');
        //TODO en passant target
    }
}
