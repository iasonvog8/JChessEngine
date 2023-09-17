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
 * https://github.com/iasonvog8/JChessEngine/tree/master/src/main/java/player/ai/MoveOrdering.java
 *
 * About this class:
 *
 * A class responsible for ordering the moves to be explored during the search.
 * Good move ordering can significantly improve the efficiency of search algorithms.
 *
 * Notes:
 *
 * 1.Capture Moves First: In chess, capturing an opponent's piece is often a high-value move. Therefore, move ordering typically starts by considering all capturing moves first. These moves are likely to have a significant impact on the evaluation of the position.
 *
 * 2.Killer Moves: Move ordering also considers "killer moves." These are moves that were previously found to cause a cutoff in a similar position in previous searches. Killer moves are often given a high priority because they tend to be good moves in many positions.
 *
 * 3.Quiet Moves: After capturing and killer moves, quiet moves (non-capturing moves) are considered. Quiet moves include pawn moves, piece development, and other non-capturing actions. These moves are ordered based on their potential to improve the position.
 *
 * 4.Checks and Threats: Moves that put the opponent's king in check or create threats against the opponent's pieces are usually considered early in the move ordering process, as they often lead to tactical opportunities and force the opponent to respond.
 *
 * 5.History Heuristic: Some move ordering algorithms use a history heuristic to prioritize moves that have led to cutoffs in previous searches. Moves that have historically been successful are given higher priority.
 *
 * 6.Promotion Moves: If a pawn is close to promotion, moves that lead to pawn promotion are considered early since promoting a pawn to a higher-value piece (usually a queen) can have a significant impact on the game.
 */

package player.ai;

public class MoveOrdering {
}
