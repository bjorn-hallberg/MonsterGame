import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;
import java.util.List;

public class MonsterGame {

    static private Terminal terminal;
    static private TextGraphics tg;

//    static private Player player;
    static private List<Monster> monsters;
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
        tg.putString(0, 0, "*".repeat(80));
        tg.putString(33, 1, "Monster Game");
        tg.putString(0, 2, "*".repeat(80));

        initGame();
    }

    private static void initGame() {
        // Create player

        // Create monsters
        
        // Create obstacles

        // Create bombs

    }

    private static void movePlayer() {

    }

    private static void moveMonsters() {

    }

    private static void drawCharacters() {

    }

    private static void endGame() throws IOException, InterruptedException {
        Thread.sleep(5000);
        terminal.close();
    }
}
