package classics.piece;

import classics.boardRepresentation.Board;
import classics.move.Move;

public interface KingSafety {

     boolean isOnCheck(final Move kingMove);

     boolean isThereBlockers(final int checkCoordinate);
     boolean hasEscapeMoves(final Move kingMove);
     boolean isDone(final Board board);
}
