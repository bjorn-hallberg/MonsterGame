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
        int oldMonsterX = this.x; // save old position x
        int oldMonsterY = this.y; // save old position y

        // Calculate monster's new position
        if (player.getY() < this.x) {
                this.x--;
            } else if (player.getX() > this.x) {
                this.x++;
            }
            if (player.getX() < this.y) {
                this.y--;
            } else if (player.getY() > this.y) {
                this.y++;
            }

    }


}
