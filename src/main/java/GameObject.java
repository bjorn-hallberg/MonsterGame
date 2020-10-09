public abstract class GameObject {

    protected int x;
    protected int y;
    protected String symbol;

    public GameObject(int x, int y, String symbol) {
        this.x = x;
        this.y = y;
        this.symbol = symbol;
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

    public boolean hasSamePosition(GameObject object) {
        return x == object.x && y == object.y;
    }

}
