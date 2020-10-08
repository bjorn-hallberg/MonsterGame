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
}
