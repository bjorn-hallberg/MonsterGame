import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MonsterGame {

    static private Terminal terminal;
    static private TextGraphics tg;

    static private Player player;
    static private ArrayList<Monster> monsters =new ArrayList<>();
//    static private List<Obstacle> obstacles;
//    static private List<Bomb> bombs;


    public static void main(String[] args) {
        try {
            startGame();
            endGame();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void startGame() throws IOException {
        // Create terminal
        terminal = new DefaultTerminalFactory().createTerminal();
        terminal.setCursorVisible(false);
        tg = terminal.newTextGraphics();
        tg.putString(0, 0, "╔" + "═".repeat(78) + "╗");
        tg.putString(0, 1, "║" + " ".repeat(33) + "Monster Game" + " ".repeat(33) + "║");
        tg.putString(0, 2, "╚" + "═".repeat(78) + "╝");

        initGame();
    }

    private static void initGame() throws IOException {
        // Create player
        final char playerSymbol = '\u263B';
        Player player = new Player(5, 5, playerSymbol);

        // Create monsters
        Monster monster1 = new Monster(8,8, Monster.getMonsterCharacter());
        monsters.add(monster1);
        Monster monster2 = new Monster(12,12, Monster.getMonsterCharacter());
        monsters.add(monster2);

        // Create and draw obstacles

        // Create and draw bombs

        // Draw player and monsters
        drawCharacters(player, terminal, monsters);
    }

    private static void movePlayer() {

    }

    private static void moveMonsters() {

    }

    private static void drawCharacters(Player player, Terminal terminal, ArrayList<Monster> monsters) throws IOException {
        tg.setForegroundColor(TextColor.ANSI.CYAN);
        for (Monster monster : monsters){
            tg.putString(monster.getMonsterX(), monster.getMonsterY(), String.valueOf(monster.getMonsterCharacter()));
        }
        terminal.flush();



    }

    private static void endGame() throws IOException, InterruptedException {
        Thread.sleep(10000);
        terminal.close();
    }
}
