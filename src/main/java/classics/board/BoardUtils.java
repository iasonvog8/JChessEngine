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
 * https://github.com/iasonvog8/JChessEngine/tree/master/src/main/java/classics/boardRepresentation/BoardUtils.java
 *
 * About this class:
 *
 * A class responsible for board's requirements
 * e.g. board partition, exclusive rows & columns, etc.
  */

package classics.board;

public class BoardUtils {
    public static final int BOARD_LENGTH = 64;
    public static final int COLUMN = 8;
    public static final int ROW = 8;

    public static final boolean[] FIRST_COLUMN = initCol(0);
    public static final boolean[] SECOND_COLUMN = initCol(1);
    public static final boolean[] SEVENTH = initCol(6);
    public static final boolean[] EIGHTH = initCol(7);
    public static final boolean[] FIRST_ROW = initRow(0);
    public static final boolean[] FOURTH_ROW = initRow(3);
    public static final boolean[] FIFTH_ROW = initRow(4);
    public static final boolean[] EIGHTH_ROW = initRow(7);

    private static boolean[] initCol(int colNum) {
        boolean[] column = new boolean[BOARD_LENGTH];

        for (int c = 0; c < COLUMN; c++)
            column[(c * COLUMN) + colNum] = true;

        return column;
    }

    private static boolean[] initRow(int rowNum) {
        boolean[] raw = new boolean[BOARD_LENGTH];

        int pointer = 0;
        for (int r = ROW * rowNum; pointer < ROW; pointer++) {

            raw[r + pointer] = true;
        }

        return raw;
    }

    public static boolean isValidTile(int tileCoordinate) {
        return (tileCoordinate >= 0 && tileCoordinate < BOARD_LENGTH);
    }
}
