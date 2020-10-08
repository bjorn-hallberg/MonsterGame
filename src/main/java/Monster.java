public class Monster {
    private int monsterX;
    private int monsterY;
    private char monster;
    private int oldMonsterX;
    private int oldMonsterY;

    public Monster(int monsterX, int monsterY, char monster) {
        this.monsterX = monsterX;
        this.monsterY = monsterY;
        this.monster = monster;
        this.oldMonsterX = monsterX;
        this.oldMonsterY = monsterY;

    }

    public int getMonsterX() {
        return monsterX;
    }

    public int getMonsterY() {
        return monsterY;
    }

    public char getMonster() {
        return monster;
    }

    public int getOldMonsterX() {
        return oldMonsterX;
    }

    public int getOldMonsterY() {
        return oldMonsterY;
    }

    public void moveMonster(Player player){
        // The monster moves towards the player based on players move

        // Save monster's old position
        int oldMonsterX = this.monsterX; // save old position x
        int oldMonsterY = this.monsterY; // save old position y

        // Calculate monster's new position
        if (player.getY() < this.monsterX) {
                this.monsterX--;
            } else if (player.getX() > this.monsterX) {
                this.monsterX++;
            }
            if (player.getX() < this.monsterY) {
                this.monsterY--;
            } else if (player.getY() > this.monsterY) {
                this.monsterY++;
            }


    }

}
