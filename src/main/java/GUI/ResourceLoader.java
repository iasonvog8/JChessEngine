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
 * https://github.com/iasonvog8/JChessEngine/tree/master/src/main/java/GUI/ResourceLoader.java
 *
 */

package GUI;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.*;
import javafx.scene.image.Image;


public class ResourceLoader {
    private static final Image[] chessBitSets = {new Image("C:\\Users\\iason\\IdeaProjects\\JChessEngine\\src\\main\\resources\\images\\pieces.png")};

    public static ImageView[] loadClassicBitSet() {
        ImageView[] set = new ImageView[12];
        final double cellWidth = chessBitSets[0].getWidth();
        final double cellHeight = chessBitSets[0].getHeight();

        int bit = 0;

        for (int y = 0; y < cellHeight; y += 200) {
            for (int x = 0; x < cellWidth; x += 200) {
                set[bit] = new ImageView();
                set[bit].setImage(chessBitSets[0]);
                set[bit].setViewport(new Rectangle2D(x, y, 200, 200));
                int finalBit = bit;
                set[bit].setOnMousePressed(mouseEvent -> {
                    set[finalBit].setX(mouseEvent.getX());
                    set[finalBit].setY(mouseEvent.getY());
                });
                bit++;
            }
        }

        return set;
    }

}
