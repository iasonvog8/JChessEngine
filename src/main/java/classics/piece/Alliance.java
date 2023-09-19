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
 * https://github.com/iasonvog8/JChessEngine/tree/master/src/main/java/classics/piece/Color.java
 *
 * About this class:
 *
 * An enum class representing the colors of chess pieces (e.g., White and Black).
 */

package classics.piece;

public enum Alliance {
    BLACK{
        @Override
        public verticalDirection getVerticalDirection() {
            return verticalDirection.DOWN;
        }
    },
    WHITE {
        @Override
        public verticalDirection getVerticalDirection() {
            return verticalDirection.UP;
        }
    };

    public abstract verticalDirection getVerticalDirection();
    protected enum verticalDirection {
        DOWN {
            @Override
            public int getDirectionOffSet() {
                return 1;
            }
        },
        UP {
            @Override
            public int getDirectionOffSet() {
                return -1;
            }
        };

        public abstract int getDirectionOffSet();
    }
}

