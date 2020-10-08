public class Monster {
    private int MonsterX;
    private int MonsterY;
    private char monster;
    private int oldMonsterX;
    private int oldMonsterY;

    public Monster(int MonsterX, int MonsterY, char monster) {
        this.MonsterX = MonsterX;
        this.MonsterY = MonsterY;
        this.monster = monster;
        this.oldMonsterX = MonsterX;
        this.oldMonsterY = MonsterY;

    }

    public int getMonsterX() {
        return MonsterX;
    }

    public int getMonsterY() {
        return MonsterY;
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
/*
    public void moveMonster(Player player){
        // The monster moves towards the player based on players move

        // Save monster's old position
        int oldMonsterX = monsterX; // save old position x
        int oldMonsterY = monsterY; // save old position y

        // Calculate monster's new position
        if (player.getX < this.MonsterX) {
                this.MonsterX--;
            } else if (player.getX > this.MonsterX) {
                this.MonsterX++;
            }
            if (player.getY < this.MonsterY) {
                this.MonsterY--;
            } else if (player.getY > this.MonsterY) {
                this.MonsterY++;
            }


    }
*/
}
