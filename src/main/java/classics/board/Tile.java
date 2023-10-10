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
 * https://github.com/iasonvog8/JChessEngine/tree/master/src/main/java/classics/boardRepresentation/Tile.java
 *
 * About this class:
 *
 * Represents a square on the chessboard. It can hold information about the
 * piece occupying the square (if any) and its position.
 */

package classics.board;

import classics.piece.Piece;

public abstract class Tile {
    public final int coordinate;
    Tile (int coordinate) {
        this.coordinate = coordinate;
    }
    abstract public boolean isTileOccupied();
    abstract public Piece getPiece();

    public static class EmptyTile extends Tile {
        public EmptyTile(int coordinate) {
            super(coordinate);
        }

        @Override
        public boolean isTileOccupied() {
            return false;
        }

        @Override
        public Piece getPiece() {
            return null;
        }

        @Override
        public String toString() {
            return "E";
        }
    }

    public static class OccupiedTile extends Tile {
        Piece tilePiece;
        public OccupiedTile(int coordinate, Piece tilePiece) {
            super(coordinate);
            this.tilePiece = tilePiece;
        }

        @Override
        public boolean isTileOccupied() {
            return true;
        }

        @Override
        public Piece getPiece() {
            return tilePiece;
        }

        @Override
        public String toString() {
            return "O";
        }
    }

    @Override
    public String toString() {
        return "Tile piece: " + getPiece();
    }
}
