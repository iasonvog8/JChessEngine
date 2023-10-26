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
 * https://github.com/iasonvog8/JChessEngine/tree/master/src/main/java/classics/move/MoveNotationGenerator.java
 *
 * About this class:
 *
 * MoveNotationGenerator: Implement a class to generate notations for moves made in the game.
 */

package classics.move;

import classics.piece.King;
import classics.piece.Piece;

import static classics.move.Move.*;

public class MoveNotationGenerator {
    private static final String[] algebraicArray = {
            "a1", "b1", "c1", "d1", "e1", "f1", "g1", "h1",
            "a2", "b2", "c2", "d2", "e2", "f2", "g2", "h2",
            "a3", "b3", "c3", "d3", "e3", "f3", "g3", "h3",
            "a4", "b4", "c4", "d4", "e4", "f4", "g4", "h4",
            "a5", "b5", "c5", "d5", "e5", "f5", "g5", "h5",
            "a6", "b6", "c6", "d6", "e6", "f6", "g6", "h6",
            "a7", "b7", "c7", "d7", "e7", "f7", "g7", "h7",
            "a8", "b8", "c8", "d8", "e8", "f8", "g8", "h8"
    };

    public static String translateMoveToAlgebraic(Move algebraicMove) {
        final StringBuilder algebraicNotation = new StringBuilder();

        if ((algebraicMove instanceof QueenSideCastling) ||
                (algebraicMove instanceof KingSideCastling))
            algebraicNotation.append(algebraicMove);
        else if (!(algebraicMove instanceof PromotionMove)) {
            algebraicNotation.append(pieceAbbreviations(algebraicMove.getMovedPiece()));
            if (algebraicMove instanceof AttackMove || algebraicMove instanceof EnPassantMove)
                algebraicNotation.append("x");
            algebraicNotation.append(algebraicArray[algebraicMove.getDestinationCoordinate()]);
        }else
            algebraicNotation.append(algebraicArray[algebraicMove.destinationCoordinate]).
                    append(pieceAbbreviations(((PromotionMove) algebraicMove).promotedPiece));

        TransitionMove transitionMove = new TransitionMove(algebraicMove.board);
        final King king = algebraicMove.movedPiece.getAlliance().isWhite() ?
                algebraicMove.board.blackPlayer.estimateKingLocation(algebraicMove.board) :
                algebraicMove.board.whitePlayer.estimateKingLocation(algebraicMove.board) ;
        final int initialPosition = algebraicMove.movedPiece.getPieceCoordinate();
        final boolean firstMove = algebraicMove.movedPiece.isFirstMove();

        transitionMove.createMove(algebraicMove);
        if (transitionMove.isDone(algebraicMove.board, king)){
            transitionMove.revokeMove(algebraicMove, algebraicMove.board, initialPosition, firstMove);
            return algebraicNotation.append("#").toString();
        }
        if (transitionMove.isKingInCheck(king)) {
            transitionMove.revokeMove(algebraicMove, algebraicMove.board, initialPosition, firstMove);
            return algebraicNotation.append("+").toString();
        }
        transitionMove.revokeMove(algebraicMove, algebraicMove.board, initialPosition, firstMove);
        return algebraicNotation.toString();
    }

    public static String getSquareAlgebraic(final int square) {
        return algebraicArray[square];
    }

    private static String pieceAbbreviations(final Piece piece) {
        switch (piece.getPieceType()) {
            case KING -> {return "K";}
            case QUEEN -> {return "Q";}
            case BISHOP -> {return "B";}
            case KNIGHT -> {return "N";}
            case ROOK -> {return "R";}
        }
        return "";
    }
}
