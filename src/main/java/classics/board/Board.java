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

package classics.board;

import classics.move.Move;
import classics.piece.Piece;
import player.BlackPlayer;
import player.WhitePlayer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import static classics.board.BoardUtils.*;
import static classics.board.Tile.*;
import static classics.move.Move.*;
import static classics.piece.PieceType.*;

public class Board implements Cloneable{
    private Tile[] chessBoard;
    private HashMap<Integer, Piece> chessBoardPieces = new HashMap<>();
    public WhitePlayer whitePlayer;
    public BlackPlayer blackPlayer;
    public Position position;
    private int enPassantTarget = 0;
    public Board() {
        chessBoard = new Tile[BOARD_LENGTH];
        whitePlayer = new WhitePlayer();
        blackPlayer = new BlackPlayer();
        position = new Position(this);
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
        Piece commutedPiece = move.movedPiece;
        setEnPassantTarget(-1);

        if (move instanceof PrimaryMove || move instanceof AttackMove) {
            final int locationCoordinate = commutedPiece.getPieceCoordinate();
            final boolean[] enPassantRow = commutedPiece.getAlliance().isWhite() ? FIFTH_ROW : FOURTH_ROW;

            if (commutedPiece.getPieceType() == PAWN &&
                    commutedPiece.isFirstMove() && enPassantRow[move.destinationCoordinate])
                setEnPassantTarget(move.destinationCoordinate + (commutedPiece.getAlliance().isWhite() ? 8 : -8));

            if (commutedPiece.isFirstMove())
                commutedPiece.setFirstMove(false);

            setTile(locationCoordinate, new EmptyTile(locationCoordinate));
            setTile(move.destinationCoordinate, new OccupiedTile(move.destinationCoordinate, commutedPiece));

            commutedPiece.setPieceCoordinate(move.destinationCoordinate);

            chessBoardPieces.remove(locationCoordinate);
            chessBoardPieces.put(commutedPiece.getPieceCoordinate(), commutedPiece);
        }
        else if (move instanceof  PromotionMove) {
            final int promotedPieceCoordinate = move.destinationCoordinate;
            final int promotedPawnCoordinate = commutedPiece.getPieceCoordinate();

            setTile(promotedPawnCoordinate, new EmptyTile(promotedPawnCoordinate));
            setTile(promotedPieceCoordinate, new OccupiedTile(promotedPieceCoordinate, ((PromotionMove) move).promotedPiece));
            chessBoardPieces.remove(promotedPawnCoordinate);
            chessBoardPieces.put(promotedPieceCoordinate, ((PromotionMove) move).promotedPiece);

        }
        else if (move instanceof KingSideCastling || move instanceof QueenSideCastling) {
            int locationCoordinate = commutedPiece.getPieceCoordinate();
            commutedPiece.setFirstMove(false);
            ((Castling) move).rookMove.movedPiece.setFirstMove(false);

            setMove(((Castling) move).rookMove);
            commutedPiece.setPieceCoordinate(move.destinationCoordinate);

            setTile(move.destinationCoordinate, new OccupiedTile(move.destinationCoordinate, commutedPiece));
            setTile(locationCoordinate, new EmptyTile(locationCoordinate));

            chessBoardPieces.remove(locationCoordinate);
            chessBoardPieces.put(commutedPiece.getPieceCoordinate(), commutedPiece);
        }
        else if (move instanceof EnPassantMove) {
            int attackedPawnCoordinate = ((EnPassantMove) move).attackedPawn.getPieceCoordinate();

            setTile(commutedPiece.getPieceCoordinate(), new EmptyTile(commutedPiece.getPieceCoordinate()));

            commutedPiece.setPieceCoordinate(move.destinationCoordinate);

            setTile(commutedPiece.getPieceCoordinate(), new OccupiedTile(move.destinationCoordinate, commutedPiece));
            setTile(attackedPawnCoordinate, new EmptyTile(attackedPawnCoordinate));

            chessBoardPieces.remove(commutedPiece.getPieceCoordinate());
            chessBoardPieces.remove(attackedPawnCoordinate);
            chessBoardPieces.put(move.destinationCoordinate, commutedPiece);
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
            if (whitePiece.getAlliance().isWhite()) boardPieces.add(whitePiece);

        return boardPieces;
    }

    public ArrayList<Piece> getAllBlackPieces() {
        ArrayList<Piece> boardPieces = new ArrayList<>();

        for (Piece blackPiece : chessBoardPieces.values())
            if (!blackPiece.getAlliance().isWhite()) boardPieces.add(blackPiece);

        return boardPieces;
    }

    public int getEnPassantTarget() {
        return enPassantTarget;
    }

    public void setEnPassantTarget(int enPassantTarget) {
        this.enPassantTarget = enPassantTarget;
    }

    public void displayHashMap() {
        for (Piece c : chessBoardPieces.values())
            System.out.println(c);
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
    public Board clone() {
        try {

            final Board clonedBoard = (Board) super.clone();

            clonedBoard.chessBoard = this.chessBoard.clone();
            clonedBoard.chessBoardPieces = new HashMap<>(this.chessBoardPieces);
            clonedBoard.enPassantTarget = this.enPassantTarget;
            return clonedBoard;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}