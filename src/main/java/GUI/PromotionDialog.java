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
 * https://github.com/iasonvog8/JChessEngine/tree/master/src/main/java/GUI/PromotionDialog.java
 *
 */

package GUI;

import classics.piece.*;
import javafx.event.Event;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;

public class PromotionDialog {
    private static final ImageView[] allPiecesImage = PieceImageLoader.loadClassicBitSet();
    private static Piece promotedPiece;
    public static void runPromotionDialog(final boolean isWhite, final int promotionCoordinate) {
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Chess Promotion Dialog");

        VBox dialogVBox = new VBox(10);
        dialogVBox.setSpacing(10);

        // Create a ComboBox with customized rendering
        ComboBox<String> promotionComboBox = new ComboBox<>();
        promotionComboBox.getItems().addAll("Queen", "Rook", "Bishop", "Knight");

        // Set a custom cell factory to render items with images
        promotionComboBox.setCellFactory(new Callback<>() {
            @Override
            public ListCell<String> call(ListView<String> stringListView) {
                return new ListCell<>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        final int pieceColor = isWhite ? 0 : 6;
                        if (item != null && !empty) {
                            setText(item);
                            switch (item) {
                                case "Queen" -> setGraphic(allPiecesImage[1 + pieceColor]);
                                case "Rook" -> setGraphic(allPiecesImage[4 + pieceColor]);
                                case "Bishop" -> setGraphic(allPiecesImage[2 + pieceColor]);
                                case "Knight" -> setGraphic(allPiecesImage[3 + pieceColor]);
                            }
                        } else {
                            setText(null);
                            setGraphic(null);
                        }
                    }
                };
            }
        });

        // Add a listener to the ComboBox to print the selected choice
        promotionComboBox.setOnAction(event -> {
            Alliance alliance = isWhite ? Alliance.WHITE : Alliance.BLACK;
            String selectedChoice = promotionComboBox.getSelectionModel().getSelectedItem();
            if (selectedChoice != null) {
                switch (selectedChoice) {
                    case "Queen" -> promotedPiece = new Queen(promotionCoordinate, alliance);
                    case "Rook" -> promotedPiece = new Rook(promotionCoordinate, alliance);
                    case "Knight" -> promotedPiece = new Knight(promotionCoordinate, alliance);
                    case "Bishop" -> promotedPiece = new Bishop(promotionCoordinate, alliance);
                }
            }
        });

        // Make a confirm button
        Button confirmButton = new Button("Confirm");
        confirmButton.setOnAction(e -> {
            if (promotedPiece != null)
                primaryStage.close();
        });

        dialogVBox.getChildren().addAll(promotionComboBox, confirmButton);

        Scene dialogScene = new Scene(dialogVBox, 400, 200);

        primaryStage.setScene(dialogScene);
        primaryStage.setOnCloseRequest(Event::consume);
        primaryStage.show();
    }

    public static Piece getPromotedPiece() {
        return promotedPiece;
    }
}
