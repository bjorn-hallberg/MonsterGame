import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;

public class Monster {
    private int monsterX;
    private int monsterY;
    static final char monsterCharacter= '\u2689';
    private int oldMonsterX;
    private int oldMonsterY;

    public Monster(int monsterX, int monsterY, char monsterCharacter) {
        this.monsterX = monsterX;
        this.monsterY = monsterY;
        this.oldMonsterX = monsterX;
        this.oldMonsterY = monsterY;

    }

    public int getMonsterX() {
        return monsterX;
    }

    public int getMonsterY() {
        return monsterY;
    }

    static public char getMonsterCharacter() {
        return monsterCharacter;
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

/*    public void drawMonster(TextGraphics tg){
        tg.setForegroundColor(TextColor.ANSI.CYAN);
        tg.putString(monsterX, monsterX, String.valueOf(monsterCharacter));
    }*/

}
