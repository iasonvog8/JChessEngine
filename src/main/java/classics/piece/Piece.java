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
 * https://github.com/iasonvog8/JChessEngine/tree/master/src/main/java/classics/piece/Piece.java
 *
 * About this class:
 *
 * An abstract class or interface representing a chess piece.
 * You can create subclasses or implement different classes for each
 * type of piece (Pawn, Rook, Knight, Bishop, Queen, King). Each piece
 * class should define its move rules and characteristics.
 */

package classics.piece;

import classics.boardRepresentation.Board;
import classics.move.Move;

import java.util.ArrayList;

public abstract class Piece {
     public int currentCoordinate;
     public Alliance alliance;
     PieceType pieceType;
     boolean isFirstMove;

     public Piece(final int currentCoordinate, final Alliance alliance, final PieceType pieceType) {
          this.currentCoordinate = currentCoordinate;
          this.alliance = alliance;
          this.pieceType = pieceType;
     }

     public Piece(final int currentCoordinate, final Alliance alliance, final PieceType pieceType, boolean isFirstMove) {
          this.currentCoordinate = currentCoordinate;
          this.alliance = alliance;
          this.pieceType = pieceType;
          this.isFirstMove = isFirstMove;
     }
     public abstract ArrayList<Move> calculateLegalSquares(final Board board);
}
