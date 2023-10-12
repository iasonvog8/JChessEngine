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
 * https://github.com/iasonvog8/JChessEngine/tree/master/src/main/java/GUI/MoveHighlighter.java
 *
 */

package GUI;

import classics.board.Board;
import classics.move.Move;
import classics.move.MoveValidator;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import player.Player;

import java.util.ArrayList;

public class MoveHighlighter {
    public static void markAllLegalSquares(final int piecePosition,
                                           final ArrayList<Move> allPiecePossibleLegalMoves,
                                           final GridPane chessBoardPanel,
                                           final Player player,
                                           final Board board) {
        final Color markedPosition = Color.BLUE;
        final Color markedEmptySquare =Color.rgb(255, 242, 0, 0.5);
        final Color markedAttackedPiece = Color.rgb(94, 13, 227, 0.3);
        final Color kingInCheck = Color.rgb(231, 8, 8, 0.5);

        Rectangle rectangle;
        ArrayList<Integer> markedTiles = new ArrayList<>();
        ArrayList<Integer> markedAttackedTiles = new ArrayList<>();

        boolean isWhiteCell = true;
        int kingCoordinate = -1;
        int tileCoordinate;

        for (Move markedMove : allPiecePossibleLegalMoves) {
            if (MoveValidator.isValidMove(markedMove, player, board)) {
                if (!(markedMove instanceof Move.AttackMove) && !(markedMove instanceof Move.EnPassantMove))
                    markedTiles.add(markedMove.getDestinationCoordinate());
                else markedAttackedTiles.add(markedMove.getDestinationCoordinate());
            }
            if (player.isPlayerOnCheckMate(board) || player.isPlayerInCheck(board))
                kingCoordinate = player.estimateKingLocation(board).getPieceCoordinate();
        }


        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                rectangle = new Rectangle(100, 100);
                rectangle.setFill(isWhiteCell ? Color.BEIGE : Color.BROWN);
                tileCoordinate = r * 8 + c;

                if (tileCoordinate == piecePosition)
                    rectangle.setFill(markedPosition);
                else if (markedTiles.contains(tileCoordinate))
                    rectangle.setFill(markedEmptySquare);
                else if (markedAttackedTiles.contains(tileCoordinate))
                    rectangle.setFill(markedAttackedPiece);
                else if (tileCoordinate == kingCoordinate) {
                    rectangle.setFill(kingInCheck);
                }


                Pane tile = new Pane();
                tile.getChildren().add(rectangle);

                chessBoardPanel.add(rectangle, c, r);

                isWhiteCell = !isWhiteCell;
            }
            isWhiteCell = !isWhiteCell;
        }
    }
}
