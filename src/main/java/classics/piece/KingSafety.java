package classics.piece;

import classics.board.Board;
import classics.move.Move;

import java.util.ArrayList;

public interface KingSafety {

     boolean isKingInCheck(final King king);
     ArrayList<Move> getBlockers(final Board board, final King king);
     boolean hasEscapeMoves(final King king);
     boolean isDone(final Board board, final King king);
}
