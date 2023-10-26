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
 * https://github.com/iasonvog8/JChessEngine/tree/master/src/main/java/GUI/MoveHistoryPanel.java
 *
 */

package GUI;

import classics.move.Move;
import javafx.collections.FXCollections;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MoveHistoryPanel {
    public static ListView<String> movesListView = new ListView<>(FXCollections.observableArrayList("White  Black"));
    public static ScrollPane gameHistory() {
        movesListView.setPrefHeight(300);

        ScrollPane rowsScrollPane = new ScrollPane(movesListView);
        rowsScrollPane.setFitToWidth(true);

        return rowsScrollPane;
    }

    public static void addMove(final HashMap<Move, String> gameHistory) {
        List<Map.Entry<Move, String>> entryList = gameHistory.entrySet().stream().toList();

        if (!entryList.isEmpty()) {
            String move = entryList.get(entryList.size() - 1).getValue();

            if (entryList.size() % 2 != 0)
                movesListView.getItems().add(move);
            else {
                String row = movesListView.getItems().get(movesListView.getItems().size() - 1);
                movesListView.getItems().remove(movesListView.getItems().size() - 1);

                movesListView.getItems().add(row + " ".repeat(7 - row.length()) + move);
            }
        }
    }
}
