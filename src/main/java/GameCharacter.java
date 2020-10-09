public abstract class GameCharacter extends GameObject {
    protected int previousX;
    protected int previousY;

    public GameCharacter(int x, int y) {
        super(x, y, "☻");
        previousX = x;
        previousY = y;
    }

    public GameCharacter(int x, int y, String symbol) {
        super(x, y, symbol);
        previousX = x;
        previousY = y;
    }

    public int getPreviousX() {
        return previousX;
    }

    public int getPreviousY() {
        return previousY;
    }

    public void moveToPreviousPosition() {
        x = previousX;
        y = previousY;
    }
}
