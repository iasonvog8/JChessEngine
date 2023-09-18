package classics.piece;

import classics.boardRepresentation.Board;
import classics.move.Move;

import java.util.ArrayList;

public interface KingSafety {

     boolean isOnCheck(Board board, int coordinate);

     boolean isThereBlockers(int checkCoordinate);
     ArrayList<Move> calculateEscapeMoves(Board board, int kingLocation);
}
