package player;

public class WhitePlayer extends Player{
    public WhitePlayer(String name, int level) {
        super(name, level);
    }

    @Override
    public boolean isWhitePlayer() {
        return true;
    }
}
