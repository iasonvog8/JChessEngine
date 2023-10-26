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
 * https://github.com/iasonvog8/JChessEngine/tree/master/src/main/java/GUI/ChessGUI.java
 *
 */

package GUI;

import classics.board.Board;
import classics.board.ChessBitSet;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ChessGUI extends Application {
    @Override
    public void start(Stage stage)  {
        Board board = new Board();
        //board.buildBoard(ChessBitSet.classicBitSet());
        ChessBitSet.setFENBitSet(new StringBuilder("r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R3K2R w KQkq - 0 0"), board);

        HBox applicationPane = new HBox();
        applicationPane.setPrefSize(1100, 900);

        GridPane chessBoard = ChessBoardPanel.createChessBoard(board);
        chessBoard.setMaxHeight(800);
        chessBoard.setMaxWidth(800);
        chessBoard.setMinHeight(800);
        chessBoard.setMinHeight(800);

        StackPane chessBoardWood = new StackPane();
        chessBoardWood.getChildren().addAll(CoordinateLabelPanel.algebraicLabelCoordinate(), CoordinateLabelPanel.numericLabelCoordinate(), chessBoard);
        chessBoardWood.setPrefSize(900, 900);

        final Color woodColor = Color.rgb(121, 58, 58, 0.80);
        final BackgroundFill backgroundFill = new BackgroundFill(woodColor, CornerRadii.EMPTY, Insets.EMPTY);
        final Background background = new Background(backgroundFill);
        chessBoardWood.setBackground(background);

        VBox gameInformationPanel = new VBox();

        applicationPane.getChildren().addAll(chessBoardWood, MoveHistoryPanel.gameHistory());

        Scene scene = new Scene(applicationPane);

        stage.setMinHeight(900);
        stage.setMinWidth(900);
        stage.setScene(scene);
        stage.setTitle("JChess Engine");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
