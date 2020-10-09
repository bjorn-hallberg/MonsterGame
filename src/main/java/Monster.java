public class Monster {
    private int x;
    private int y;
    static final char monsterCharacter= '\u2689';
    private int oldX;
    private int oldY;

    public Monster(int x, int y) {
        this.x = x;
        this.y = y;
        this.oldX = x;
        this.oldY = y;

    }
    public Monster(int x, int monsterY, char monsterCharacter) {
        this.x = x;
        this.y = y;
        this.oldX = x;
        this.oldY = y;

    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    static public char getMonsterCharacter() {
        return monsterCharacter;
    }

    public int getOldX() {
        return oldX;
    }

    public int getOldY() {
        return oldY;
    }

    public void moveMonster(Player player){
        // The monster moves towards the player based on players move
        // Save monster's old position
        oldX = x; // save old position x
        oldY = y; // save old position y

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
