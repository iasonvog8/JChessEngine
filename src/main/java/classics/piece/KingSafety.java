package classics.piece;

import classics.boardRepresentation.Board;
import classics.move.Move;

import java.util.ArrayList;

public interface KingSafety {

     boolean isOnCheck(final Move kingMove);

     ArrayList<Move> getBlockers(final Board board, final Piece king);
     boolean hasEscapeMoves(final Piece king);
     boolean isDone(final Board board, final Piece king);
}
