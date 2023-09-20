package player;

public class BlackPlayer extends Player{
    public BlackPlayer(String name, int level) {
        super(name, level);
    }

    @Override
    public boolean isWhitePlayer() {
        return false;
    }
}
