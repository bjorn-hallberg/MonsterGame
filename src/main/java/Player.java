public class Player extends GameCharacter{

    public Player(int x, int y, String s) {
        super(x,y,"\u263B");
    }

    public void moveUp(){
        previousX = x;
        previousY = y;
        y --;
    }

    public void moveDown(){
        previousX = x;
        previousY = y;
        y ++;
    }

    public void moveLeft(){
        previousX = x;
        previousY = y;
        x --;
    }

    public void moveRight(){
        previousX = x;
        previousY = y;
        x ++;
    }

    public void movePreviousPosition(){
        x = previousX;
        y = previousY;
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