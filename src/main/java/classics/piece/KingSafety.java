package classics.piece;

import classics.boardRepresentation.Board;
import classics.move.Move;

import java.util.ArrayList;

public interface KingSafety {

     boolean isOnCheck(final Move kingMoveTest);

     boolean isThereBlockers(final int checkCoordinate);
     boolean hasEscapeMoves();
     boolean isDone(final Board board);
}
