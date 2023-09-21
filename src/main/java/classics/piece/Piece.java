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
     protected int pieceCoordinate;
     private Alliance alliance;
     private PieceType pieceType;
     private boolean isFirstMove;

     public Piece(final int pieceCoordinate, final Alliance alliance, final PieceType pieceType) {
          this.pieceCoordinate = pieceCoordinate;
          this.alliance = alliance;
          this.pieceType = pieceType;
          this.isFirstMove = true;
     }

     public abstract ArrayList<Move> calculateLegalSquares(final Board board);
     public int getPieceCoordinate() {
          return pieceCoordinate;
     }
     public Alliance getAlliance() {
          return alliance;
     }

     public void setAlliance(Alliance alliance) {
          this.alliance = alliance;
     }

     public PieceType getPieceType() {
          return pieceType;
     }

     public void setPieceType(PieceType pieceType) {
          this.pieceType = pieceType;
     }

     public boolean isFirstMove() {
          return isFirstMove;
     }

     public void setFirstMove(boolean firstMove) {
          isFirstMove = firstMove;
     }

     @Override
     public String toString() {
          return "Piece Location: " + pieceCoordinate + " Alliance: " + alliance +
                  " PieceType: " + pieceType;
     }
}
