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
 * https://github.com/iasonvog8/JChessEngine/tree/master/src/main/java/GUI/CoordinateLabelPanel.java
 *
 */

package GUI;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

public class CoordinateLabelPanel {
    public static HBox algebraicLabelCoordinate() {
        final String[] algebraicLetters = {"a", "b", "c", "d", "e", "f", "g", "h"};
        final HBox algebraicLabel = new HBox();

        algebraicLabel.setSpacing(85);
        algebraicLabel.setPadding(new Insets(0, 0, 0, 85));

        for (String letter : algebraicLetters) {
            Label letterCoordinateLabel = new Label(letter);
            letterCoordinateLabel.setFont(new Font("bold", 30));

            algebraicLabel.getChildren().add(letterCoordinateLabel);
        }
        return algebraicLabel;
    }

    public static VBox numericLabelCoordinate() {
        final String[] algebraicNumbers = {"1", "2", "3", "4", "5", "6", "7", "8"};
        final VBox algebraicLabel = new VBox();

        algebraicLabel.setSpacing(55);
        algebraicLabel.setPadding(new Insets(70, 0, 0, 10));

        for (String letter : algebraicNumbers) {
            Label letterCoordinateLabel = new Label(letter);
            letterCoordinateLabel.setFont(new Font("bold", 30));

            algebraicLabel.getChildren().add(letterCoordinateLabel);
        }
        return algebraicLabel;
    }
}
