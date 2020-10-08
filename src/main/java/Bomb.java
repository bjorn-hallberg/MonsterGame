public class Bomb {
    protected int x;
    protected int y;
    protected String symbol;

    public Bomb(int x, int y) {
        this.x = x;
        this.y = y;
        symbol = String.valueOf('\u2622');
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getSymbol() {
        return symbol;
    }
}
