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

import classics.move.Move;
import classics.piece.Alliance;
import classics.piece.Piece;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import static classics.boardRepresentation.BoardUtils.*;
import static classics.boardRepresentation.Tile.*;
import static classics.move.Move.*;

public class Board implements Cloneable{
    private Tile[] chessBoard;
    private HashMap<Integer, Piece> chessBoardPieces = new HashMap<>();
    public Board() {
        chessBoard = new Tile[BOARD_LENGTH];
    }

    private void initBoard() {
        for (int t = 0; t < BOARD_LENGTH; t++)
            setTile(t, new EmptyTile(t));
    }
    public void setBoard(final ArrayList<ChessBitSet> bitSet) {
        initBoard();
        for (ChessBitSet bit : bitSet) {
            chessBoard[bit.getBit().getPieceCoordinate()] = new OccupiedTile(bit.getBit().getPieceCoordinate(), bit.getBit());
            chessBoardPieces.put(bit.getBit().getPieceCoordinate(), bit.getBit());
        }
    }
    public void setMove(final Move move) {
        if (move instanceof PrimaryMove || move instanceof AttackMove) {
            int locationCoordinate = move.movedPiece.getPieceCoordinate();

            if (move.movedPiece.isFirstMove())
                move.movedPiece.setFirstMove(false);

            setTile(locationCoordinate, new EmptyTile(locationCoordinate));
            setTile(move.destinationCoordinate, new OccupiedTile(move.destinationCoordinate, move.movedPiece));
            chessBoardPieces.remove(locationCoordinate);
            chessBoardPieces.put(move.destinationCoordinate, move.movedPiece);

            if (move instanceof AttackMove)
                chessBoardPieces.remove(((AttackMove) move).attackedPiece.getPieceCoordinate());

        }
        else if (move instanceof  PromotionMove) {
            int promotedPieceCoordinate = move.destinationCoordinate;
            int promotedPawnCoordinate = move.movedPiece.getPieceCoordinate();

            setTile(promotedPawnCoordinate, new EmptyTile(promotedPawnCoordinate));
            setTile(promotedPieceCoordinate, new OccupiedTile(promotedPieceCoordinate, ((PromotionMove) move).promotedPiece));
            chessBoardPieces.remove(promotedPawnCoordinate);
            chessBoardPieces.put(promotedPieceCoordinate, ((PromotionMove) move).promotedPiece);

        }
        else if (move instanceof KingSideCastling || move instanceof QueenSideCastling) {
            move.movedPiece.setFirstMove(false);
            ((Castling) move).rookMove.movedPiece.setFirstMove(false);

            setMove(((Castling) move).rookMove);
            setTile(move.destinationCoordinate, new OccupiedTile(move.destinationCoordinate, move.movedPiece));

            chessBoardPieces.remove(move.movedPiece.getPieceCoordinate());
            chessBoardPieces.put(move.destinationCoordinate, move.movedPiece);
        }
    }
    public Tile getTile(final int tileCoordinate) {
        return chessBoard[tileCoordinate];
    }
    public void setTile(final int tileCoordinate, final Tile tile) {
        chessBoard[tileCoordinate] = tile;
    }
    public ArrayList<Piece> getAllPieces() {
        Collection<Piece> pieces = chessBoardPieces.values();
        return new ArrayList<>(pieces);
    }
    public ArrayList<Piece> getAllWhitePieces() {
        ArrayList<Piece> boardPieces = new ArrayList<>();

        for (Piece whitePiece : chessBoardPieces.values())
            if (whitePiece.getAlliance() == Alliance.WHITE) boardPieces.add(whitePiece);

        return boardPieces;
    }

    public ArrayList<Piece> getAllBlackPieces() {
        ArrayList<Piece> boardPieces = new ArrayList<>();

        for (Piece blackPiece : chessBoardPieces.values())
            if (blackPiece.getAlliance() == Alliance.BLACK) boardPieces.add(blackPiece);

        return boardPieces;
    }

    public Tile[] getChessBoard() {
        return chessBoard;
    }

    public void setChessBoard(final Tile[] chessBoard) {
        this.chessBoard = chessBoard;
        chessBoardPieces.clear();
        for (Tile square : chessBoard)
            if (square instanceof OccupiedTile)
                chessBoardPieces.put(square.coordinate, square.getPiece());
    }
    public void displayBoard() {
        for (int sq = 0; sq < BOARD_LENGTH; sq++) {
            if (sq % 8 == 0) {
                System.out.println("\n" + "+--".repeat(8) + "+");
                System.out.print("|");
            }
            if (chessBoard[sq] instanceof OccupiedTile) {
                switch (chessBoard[sq].getPiece().getPieceType()) {
                    case KING -> System.out.print("k");
                    case QUEEN -> System.out.print("q");
                    case KNIGHT -> System.out.print("n");
                    case BISHOP -> System.out.print("b");
                    case ROOK -> System.out.print("r");
                    case PAWN -> System.out.print("p");
                }
                switch (chessBoard[sq].getPiece().getAlliance()) {
                    case WHITE -> System.out.print("w|");
                    case BLACK -> System.out.print("b|");
                }
            }else System.out.print("  |");
        }
        System.out.println("\n" + "+--".repeat(8) + "+");
    }
    @Override
    public Board clone() throws CloneNotSupportedException {
        try {

            final Board clonedBoard = (Board) super.clone();

            // Now, handle deep copying of the internal state, if needed
            // For example, you may need to clone the chessBoard and chessBoardPieces objects

            clonedBoard.chessBoard = this.chessBoard.clone();
            clonedBoard.chessBoardPieces = new HashMap<>(this.chessBoardPieces);
            return clonedBoard;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }

    }
}