import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;

public class MonsterGame {

    static private Terminal terminal;
    static private TextGraphics tg;

//    static private Player player;
//    static private List<Monster> monsters;
//    static private List<Obstacles> obstacles;
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
        tg = terminal.newTextGraphics();
        terminal.setCursorVisible(false);

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

    private static void endGame() throws IOException, InterruptedException {
        Thread.sleep(5000);
        terminal.close();
    }
}
