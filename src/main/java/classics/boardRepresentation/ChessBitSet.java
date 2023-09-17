package classics.boardRepresentation;

import classics.piece.Alliance;
import classics.piece.Knight;
import classics.piece.Pawn;
import classics.piece.Piece;

import java.util.ArrayList;

public class ChessBitSet {
    private final Piece bit;

    public ChessBitSet(Piece bit) {
        this.bit = bit;
    }

    public Piece getBit() {
        return bit;
    }

    public static ArrayList<ChessBitSet> classicBitSet() {
        ArrayList<ChessBitSet> bitSets = new ArrayList<>();

        bitSets.add(new ChessBitSet(new Pawn(8, Alliance.BLACK)));
        bitSets.add(new ChessBitSet(new Pawn(9, Alliance.BLACK)));
        bitSets.add(new ChessBitSet(new Pawn(10, Alliance.BLACK)));
        bitSets.add(new ChessBitSet(new Pawn(11, Alliance.BLACK)));
        bitSets.add(new ChessBitSet(new Pawn(12, Alliance.BLACK)));
        bitSets.add(new ChessBitSet(new Pawn(13, Alliance.BLACK)));
        bitSets.add(new ChessBitSet(new Pawn(14, Alliance.BLACK)));
        bitSets.add(new ChessBitSet(new Pawn(15, Alliance.BLACK)));
        bitSets.add(new ChessBitSet(new Knight(1, Alliance.BLACK)));
        bitSets.add(new ChessBitSet(new Knight(6, Alliance.BLACK)));

        bitSets.add(new ChessBitSet(new Pawn(48, Alliance.WHITE)));
        bitSets.add(new ChessBitSet(new Pawn(49, Alliance.WHITE)));
        bitSets.add(new ChessBitSet(new Pawn(50, Alliance.WHITE)));
        bitSets.add(new ChessBitSet(new Pawn(51, Alliance.WHITE)));
        bitSets.add(new ChessBitSet(new Pawn(52, Alliance.WHITE)));
        bitSets.add(new ChessBitSet(new Pawn(53, Alliance.WHITE)));
        bitSets.add(new ChessBitSet(new Pawn(54, Alliance.WHITE)));
        bitSets.add(new ChessBitSet(new Pawn(55, Alliance.WHITE)));
        bitSets.add(new ChessBitSet(new Knight(57, Alliance.WHITE)));
        bitSets.add(new ChessBitSet(new Knight(62, Alliance.WHITE)));

        return bitSets;
    }

}