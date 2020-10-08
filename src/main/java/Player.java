public class Player {
    private int x;
    private int y;
    private String symbol;
    private int previousX; 
    private int previousY;

    public Player(int x, int y, String symbol) {
        this.x = x;
        this.y = y;
        this.symbol = symbol;
        this.previousX = x;
        this.previousY = y;
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
        return previousX;
    }

    public int getPreviousY() {
        return previousY;
    }

    public void moveUp(){
        previousX = x;
        previousY = y;
        y -= 2;
    }

    public void moveDown(){
        previousX = x;
        previousY = y;
        y += 2;
    }

    public void moveLeft(){
        previousX = x;
        previousY = y;
        x -= 2;
    }

    public void moveRight(){
        previousX = x;
        previousY = y;
        x += 2;
    }

    @Override
    public String toString() {
        return "Player{" +
                "x=" + x +
                ", y=" + y +
                ", symbol=" + symbol +
                ", previousX=" + previousX +
                ", previousY=" + previousY +
                '}';
    }
}