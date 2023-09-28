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
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class ChessGUI extends Application {
    @Override
    public void start(Stage stage)  {
        Board board = new Board();
        board.setBoard(ChessBitSet.classicBitSet());

        GridPane chessBoard = ChessBoardPanel.createChessBoard(board);
        chessBoard.setMaxHeight(800);
        chessBoard.setMaxWidth(800);
        chessBoard.setMinHeight(800);
        chessBoard.setMinHeight(800);

        Scene scene = new Scene(chessBoard);

        stage.setResizable(false);
        stage.setScene(scene);
        stage.setTitle("JChess Engine");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
