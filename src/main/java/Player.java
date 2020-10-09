public class Player extends GameCharacter {

    public Player(int x, int y) {
        super(x, y, "☻");
    }

    public void moveUp() {
        previousX = x;
        previousY = y;
        y--;
    }

    public void moveDown() {
        previousX = x;
        previousY = y;
        y++;
    }

    public void moveLeft() {
        previousX = x;
        previousY = y;
        x--;
    }

    public void moveRight() {
        previousX = x;
        previousY = y;
        x++;
    }

}