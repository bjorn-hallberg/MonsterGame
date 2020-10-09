public class Monster extends GameCharacter {

    public Monster(int x, int y) {
        super(x, y, "⚉");
    }

    public void moveMonster(Player player) {
        // The monster moves towards the player based on players move
        // Save monster's old position
        previousX = x; // save old position x
        previousY = y; // save old position y

        // Calculate monster's new position
        if (player.getX() < x) {
            x--;
        } else if (player.getX() > x) {
            x++;
        }
        if (player.getY() < y) {
            y--;
        } else if (player.getY() > y) {
            y++;
        }

    }

}
