public class Player {
    private int x;
    private int y;
    private String symbol;
    private int previousPlayerX;
    private int previousPlayerY;

    public Player(int x, int y, String symbol) {
        this.x = x;
        this.y = y;
        this.symbol = symbol;
        this.previousPlayerX = x;
        this.previousPlayerY = y;
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

    public int getPreviousX() {
        return previousPlayerX;
    }

    public int getPreviousY() {
        return previousPlayerY;
    }

    public void moveUp(){
        previousPlayerX = x;
        previousPlayerY = y;
        y -= 2;
    }

    public void moveDown(){
        previousPlayerX = x;
        previousPlayerY = y;
        y += 2;
    }

    public void moveLeft(){
        previousPlayerX = x;
        previousPlayerY = y;
        x -= 2;
    }

    public void moveRight(){
        previousPlayerX = x;
        previousPlayerY = y;
        x += 2;
    }

    @Override
    public String toString() {
        return "Player{" +
                "x=" + x +
                ", y=" + y +
                ", symbol=" + symbol +
                ", previousX=" + previousPlayerX +
                ", previousY=" + previousPlayerY +
                '}';
    }
}