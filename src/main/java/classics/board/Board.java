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

import classics.move.FENGenerator;
import classics.move.Move;
import classics.piece.*;
import player.BlackPlayer;
import player.WhitePlayer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import static classics.board.BoardUtils.*;
import static classics.board.Tile.*;
import static classics.move.Move.*;

public class Board {
    private final Tile[] chessBoard;
    private final HashMap<Integer, Piece> chessBoardPieces = new HashMap<>();
    public WhitePlayer whitePlayer;
    public BlackPlayer blackPlayer;
    public FENGenerator fenGenerator;
    public Position position;
    private int enPassantTarget = 0;
    public Board() {
        chessBoard = new Tile[BOARD_LENGTH];
        whitePlayer = new WhitePlayer();
        blackPlayer = new BlackPlayer();
        fenGenerator = new FENGenerator();
        position = new Position();
    }

    public void initBoard() {
        for (int t = 0; t < BOARD_LENGTH; t++)
            setTile(new EmptyTile(t));
    }

    public void buildBoard(final ArrayList<ChessBitSet> bitSet) {
        initBoard();
        for (ChessBitSet bit : bitSet) {
            chessBoard[bit.bit().getPieceCoordinate()] = new OccupiedTile(bit.bit().getPieceCoordinate(), bit.bit());
            chessBoardPieces.put(bit.bit().getPieceCoordinate(), bit.bit());
        }
        fenGenerator.translateBoard(this);
    }

    public void execute(final Move move) {
        Piece commutedPiece = move.getMovedPiece();

        if (move instanceof MajorMove || move instanceof AttackMove) {
            final int locationCoordinate = commutedPiece.getPieceCoordinate();

            if (commutedPiece.isFirstMove())
                commutedPiece.setFirstMove(false);

            setTile(new EmptyTile(locationCoordinate));
            setTile(new OccupiedTile(move.getDestinationCoordinate(), commutedPiece));

            commutedPiece.setPieceCoordinate(move.getDestinationCoordinate());
        }
        else if (move instanceof  PromotionMove) {
            final int promotedPieceCoordinate = move.getDestinationCoordinate();
            final int promotedPawnCoordinate = commutedPiece.getPieceCoordinate();

            setTile(new EmptyTile(promotedPawnCoordinate));
            setTile(new OccupiedTile(promotedPieceCoordinate, ((PromotionMove) move).promotedPiece));
        }
        else if (move instanceof KingSideCastling || move instanceof QueenSideCastling) {
            int locationCoordinate = commutedPiece.getPieceCoordinate();
            commutedPiece.setFirstMove(false);
            ((Castling) move).rookMove.getMovedPiece().setFirstMove(false);

            execute(((Castling) move).rookMove);
            commutedPiece.setPieceCoordinate(move.getDestinationCoordinate());

            setTile(new OccupiedTile(move.getDestinationCoordinate(), commutedPiece));
            setTile(new EmptyTile(locationCoordinate));
        }
        else if (move instanceof EnPassantMove) {
            int attackedPawnCoordinate = ((EnPassantMove) move).attackedPawn.getPieceCoordinate();

            setTile(new EmptyTile(commutedPiece.getPieceCoordinate()));

            commutedPiece.setPieceCoordinate(move.getDestinationCoordinate());

            setTile(new OccupiedTile(move.getDestinationCoordinate(), commutedPiece));
            setTile(new EmptyTile(attackedPawnCoordinate));

            setEnPassantTarget(-1);
        }
    }

    public void setTile(final Tile tile) {
        chessBoard[tile.coordinate] = tile;

        if (tile instanceof EmptyTile)
            chessBoardPieces.remove(tile.coordinate);
        else if (tile instanceof OccupiedTile)
            chessBoardPieces.put(tile.coordinate, tile.getPiece());
    }

    public void setEnPassantTarget(int enPassantTarget) {
        this.enPassantTarget = enPassantTarget;
    }

    public Tile getTile(final int tileCoordinate) {
        return chessBoard[tileCoordinate];
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

    public Tile[] getChessBoard() {
        return chessBoard;
    }

    public int getEnPassantTarget() {
        return enPassantTarget;
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
}