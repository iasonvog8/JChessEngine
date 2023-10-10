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
 * https://github.com/iasonvog8/JChessEngine/tree/master/src/main/java/player/Player.java
 *
 * About this class:
 *
 * Player: Create a class to represent a player, including their name, skill level, and other relevant information.
 */

package player;

import classics.board.Board;
import classics.piece.King;

public abstract class Player {


    public Player() {

    }

    public abstract boolean isWhitePlayer();
    public abstract King estimateKingLocation(final Board board);
    public abstract boolean isPlayerOnCheckMate(final Board board);
    public abstract boolean isPlayerInCheck(final Board board);
    public abstract boolean isPlayerOnDrawnStatement();

}
