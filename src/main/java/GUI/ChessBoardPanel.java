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
import classics.piece.Piece;
import javafx.animation.KeyFrame;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import static classics.piece.Alliance.BLACK;

public class ChessBoardPanel {
    public static GridPane createChessBoard() {
        GridPane chessBoardPanel = new GridPane();
        chessBoardPanel.setGridLinesVisible(true);

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

        return chessBoardPanel;
    }

    public static void setPieces(GridPane graphicBoard, Board board) {
        ImageView[] allPiecesImage = ResourceLoader.loadClassicBitSet();
        int piecePointer;

        for (Piece piece : board.getAllPieces()) {
            piecePointer = 0;
            switch (piece.getPieceType()) {
                case KING -> {}
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
}
