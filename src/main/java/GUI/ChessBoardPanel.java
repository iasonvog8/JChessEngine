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
 * https://github.com/iasonvog8/JChessEngine/tree/master/src/main/java/GUI/ChessboardPanel.java
 *
 */

package GUI;

import classics.board.Board;
import classics.board.BoardUtils;
import classics.board.Tile;
import classics.game.GameHistory;
import classics.move.Move;
import classics.move.MoveValidator;
import classics.piece.Alliance;
import classics.piece.Piece;
import classics.piece.PieceType;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import player.Player;

import java.util.Objects;

import static classics.board.BoardUtils.FIFTH_ROW;
import static classics.board.BoardUtils.FOURTH_ROW;
import static classics.move.Move.*;
import static classics.move.Move.KingSideCastling.*;
import static classics.move.Move.QueenSideCastling.*;
import static classics.piece.Alliance.*;
import static GUI.MoveHighlighter.*;
import static classics.piece.PieceType.PAWN;

public class ChessBoardPanel {
    private static int selectedPiecePosition = -1;
    private static int selectedDestinationCoordinate = -1;
    public static GridPane createChessBoard(final Board board) {
        GridPane chessBoardPanel = new GridPane();
        chessBoardPanel.setGridLinesVisible(true);
        chessBoardPanel.setOnMouseClicked(e -> {

            int clickedColumn = -1;
            int clickedRow = -1;

            final double mouseX = e.getX();
            final double mouseY = e.getY();

            for (javafx.scene.Node node : chessBoardPanel.getChildren()) {
                if (node instanceof Rectangle rectangle) {
                    if (rectangle.getBoundsInParent().contains(mouseX, mouseY)) {
                        clickedColumn = GridPane.getColumnIndex(rectangle);
                        clickedRow = GridPane.getRowIndex(rectangle);
                        break;
                    }
                }
            }

            if (clickedColumn != -1 && clickedRow != -1) {
                Player player = board.position.isWhiteTurn() ? board.whitePlayer : board.blackPlayer;
                if (selectedPiecePosition == -1 && board.getTile(clickedRow * 8 + clickedColumn).isTileOccupied()) {
                    selectedPiecePosition = clickedRow * 8 + clickedColumn;
                    markAllLegalSquares(selectedPiecePosition, board.getTile(selectedPiecePosition).getPiece().calculateLegalSquares(board), chessBoardPanel, player, board);
                    setPieces(chessBoardPanel, board, true);
                } else if (selectedPiecePosition >= 0 && clickedRow * 8 + clickedColumn != selectedPiecePosition){
                    selectedDestinationCoordinate = clickedRow * 8 + clickedColumn;

                    if (board.getTile(selectedDestinationCoordinate).isTileOccupied() &&
                        board.getTile(selectedDestinationCoordinate).getPiece().getAlliance().isWhite() == player.isWhitePlayer()) {
                        selectedPiecePosition = selectedDestinationCoordinate;
                        selectedDestinationCoordinate = -1;

                        markAllLegalSquares(selectedPiecePosition, board.getTile(selectedPiecePosition).getPiece().calculateLegalSquares(board), chessBoardPanel, player, board);
                        setPieces(chessBoardPanel, board, true);
                    } else {
                        executeMove(board, chessBoardPanel, player);
                        resetSelectedTiles();
                    }
                }else {
                    resetSelectedTiles();
                    setPieces(chessBoardPanel, board, false);
                }
            }
        });

        createBoard(chessBoardPanel);
        setPieces(chessBoardPanel, board, false);
        return chessBoardPanel;
    }

    private static void createBoard(final GridPane chessBoardPanel) {
        Rectangle rectangle;
        boolean isWhiteCell = true;

        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                rectangle = new Rectangle(100, 100);
                rectangle.setFill(isWhiteCell ? Color.BEIGE : Color.BROWN);

                Pane tile = new Pane();
                tile.getChildren().add(rectangle);

                chessBoardPanel.add(rectangle, c, r);

                isWhiteCell = !isWhiteCell;
            }
            isWhiteCell = !isWhiteCell;
        }
    }

    private static void setPieces(final GridPane graphicBoard,
                                  final Board board,
                                  final boolean markTiles) {
        ImageView[] allPiecesImage = PieceImageLoader.loadClassicBitSet();
        int piecePointer;

        if (!markTiles)
            createBoard(graphicBoard);

        for (Piece piece : board.getAllPieces()) {
            piecePointer = -1;
            switch (piece.getPieceType()) {
                case KING -> piecePointer = 0;
                case QUEEN -> piecePointer = 1;
                case BISHOP -> piecePointer = 2;
                case KNIGHT -> piecePointer = 3;
                case ROOK -> piecePointer = 4;
                case PAWN -> piecePointer = 5;
            }
            if (piece.getAlliance() == BLACK) piecePointer += 6;

            ImageView originalImageView = allPiecesImage[piecePointer];
            ImageView pieceImage = new ImageView(originalImageView.getImage());
            pieceImage.setViewport(originalImageView.getViewport());

            pieceImage.setFitHeight(100);
            pieceImage.setFitWidth(100);

            graphicBoard.add(pieceImage, piece.getPieceCoordinate() % 8, piece.getPieceCoordinate() / 8);
        }
    }

    private static void executeMove(final Board board, final GridPane graphicBoard, final Player player) {
        if (selectedDestinationCoordinate != -1 && selectedPiecePosition != -1) {
            Move playerMove = calculateMoveType(board, selectedPiecePosition, selectedDestinationCoordinate);

            assert playerMove != null;
            final boolean[] enPassantRow = playerMove.getMovedPiece().getAlliance().isWhite() ? FIFTH_ROW : FOURTH_ROW;

            if (MoveValidator.isValidMove(playerMove, player, board)) {
                if (playerMove.getMovedPiece().getPieceType() == PAWN &&
                        playerMove.getMovedPiece().getAlliance().isWhite() == player.isWhitePlayer() &&
                        playerMove.getMovedPiece().isFirstMove() && enPassantRow[playerMove.getDestinationCoordinate()])
                    board.setEnPassantTarget(playerMove.getDestinationCoordinate() + (playerMove.getMovedPiece().getAlliance().isWhite() ? 8 : -8));
                else board.setEnPassantTarget(-1);

                if ((playerMove instanceof PromotionMove)
                   || (playerMove instanceof EnPassantMove)
                   || (playerMove instanceof AttackMove)) {
                    board.position.setFullMove(0);
                    board.position.setHalfMove(0);
                } else {
                    board.position.setHalfMove(board.position.getHalfMove() + 1);
                    if (board.position.getHalfMove() % 2 == 0) board.position.setFullMove(board.position.getFullMove() + 1);
                }
                GameHistory.addMoveToHistory(playerMove);

                board.execute(Objects.requireNonNull(playerMove));
                setPieces(graphicBoard, board, false);
                board.position.setWhiteTurn(!player.isWhitePlayer());

                MoveHistoryPanel.addMove(GameHistory.getGameHistory());

                board.fenGenerator.translateBoard(board);
                System.out.println(board.fenGenerator.getFEN());

                //board.displayBoard();
            }else setPieces(graphicBoard, board, false);
        }
    }

    private static Move calculateMoveType(final Board board,
                                          final int currentPosition,
                                          final int destinationPosition) {
        final Piece selectedPiece = board.getTile(currentPosition).getPiece();
        final Tile destinationTile = board.getTile(destinationPosition);
        final Player player = selectedPiece.getAlliance() == WHITE ? board.whitePlayer : board.blackPlayer;

        final boolean[] promotionRow = selectedPiece.getAlliance() == WHITE ? BoardUtils.FIRST_ROW : BoardUtils.EIGHTH_ROW;

        final int enPassantTarget = board.getEnPassantTarget();
        final int behindTile = selectedPiece.getAlliance() == BLACK ? enPassantTarget - 8 : enPassantTarget + 8;
        final int kingSideCastlingCoordinate = selectedPiece.getAlliance() == Alliance.WHITE ? 63 : 7;
        final int queenSideCastlingCoordinate = selectedPiece.getAlliance() == Alliance.WHITE ? 56 : 0;

        if (promotionRow[destinationPosition] && selectedPiece.getPieceType() == PieceType.PAWN) {
            PromotionMove promotionMove = new PromotionMove(board, selectedPiece, destinationPosition, null);
            PromotionDialog.runPromotionDialog(selectedPiece.getAlliance().isWhite(), destinationPosition);

            promotionMove.setPromotedPiece(PromotionDialog.getPromotedPiece());
            return promotionMove;
        }

        if (destinationTile.isTileOccupied())
            return new AttackMove(board, selectedPiece, destinationPosition, destinationTile.getPiece());

        if (!destinationTile.isTileOccupied()) {
            if (selectedPiece.getPieceType() == PieceType.PAWN && destinationPosition == board.getEnPassantTarget())
                return new EnPassantMove(board, selectedPiece, destinationPosition, board.getTile(behindTile).getPiece());

            if (selectedPiece.getPieceType() == PieceType.KING && selectedPiece.isFirstMove()) {
                if (isThereKingSideRook(board, selectedPiece.getAlliance()) && isAvailableKingCorridor(board, selectedPiece.getAlliance())) {
                    if (Objects.requireNonNull(getKingSideRook(board, selectedPiece.getAlliance())).isFirstMove() && destinationPosition == kingSideCastlingCoordinate - 1)
                        return new KingSideCastling(board, player.estimateKingLocation(board), kingSideCastlingCoordinate - 1,
                                new MajorMove(board, getKingSideRook(board, selectedPiece.getAlliance()), kingSideCastlingCoordinate - 2));
                }
                if (isThereQueenSideRook(board, selectedPiece.getAlliance()) && isAvailableQueenCorridor(board, selectedPiece.getAlliance())) {
                    if (Objects.requireNonNull(getQueenSideRook(board, selectedPiece.getAlliance())).isFirstMove() && destinationPosition == queenSideCastlingCoordinate + 2)
                        return new QueenSideCastling(board, player.estimateKingLocation(board), queenSideCastlingCoordinate + 2,
                                new MajorMove(board, getQueenSideRook(board, selectedPiece.getAlliance()), queenSideCastlingCoordinate + 3));
                }
            }
            return new MajorMove(board, selectedPiece, destinationPosition);
        }
        return null;
    }

    private static void resetSelectedTiles() {
        selectedPiecePosition = -1;
        selectedDestinationCoordinate = -1;
    }
}