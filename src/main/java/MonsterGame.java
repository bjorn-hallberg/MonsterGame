import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;
import java.util.List;

public class MonsterGame {

    static private Terminal terminal;
    static private TextGraphics tg;

    static private Player player;
    static private List<Monster> monsters;
//    static private List<Obstacle> obstacles;
//    static private List<Bomb> bombs;
//    static private List<Fruit> fruits;


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
        tg.putString(0, 0, "╔════════════════════════════════════════════════════════════════╤═════════════╗");
        tg.putString(0, 1, "║                                 MONSTER GAME                   │  Score:     ║");
        tg.putString(0, 2, "╚════════════════════════════════════════════════════════════════╧═════════════╝");

        initGame();
    }

    private static void initGame() {
        // Create player
        final char playerSymbol = '\u263B';
        Player player = new Player(5, 5, playerSymbol);

        // Create monsters
        Monster monster1 = new Monster(20,30,'\u263B');
        monsters.add(monster1);
        Monster monster2 = new Monster(30,20,'\u263B');
        monsters.add(monster2);
        
        // Create and draw obstacles

        // Create and draw bombs

        // Create and draw fruits

        // Draw player and monsters
        drawCharacters();
    }

    private static void movePlayer() {

    }

    private static void moveMonsters() {

    }

    private static void drawCharacters() {

    }

    private static void endGame() throws IOException, InterruptedException {
        Thread.sleep(10000);
        terminal.close();
    }
}
