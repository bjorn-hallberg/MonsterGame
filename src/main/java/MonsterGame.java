import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MonsterGame {
    static private Terminal terminal;
    static private TextGraphics tg;

    static private Player player;
    static private List<Monster> monsters = new ArrayList<>();
    //    static private List<Obstacle> obstacles = new ArrayList<>();
    static private List<Bomb> bombs = new ArrayList<>();
//    static private List<Fruit> fruits = new ArrayList<>();

    static int score = 0;

    public static void main(String[] args) {
        try {
            startGame();
            endGame();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void startGame() throws IOException, InterruptedException {
        // Create terminal
        terminal = new DefaultTerminalFactory().createTerminal();
        terminal.setCursorVisible(false);
        tg = terminal.newTextGraphics();
        tg.putString(0, 0, "╔═══════════════════════════════════════════════════════════════╤══════════════╗");
        tg.putString(0, 1, "║                                 MONSTER GAME                  │  Score:      ║");
        tg.putString(0, 2, "╚═══════════════════════════════════════════════════════════════╧══════════════╝");

        initGame();

        boolean continueReadingInput = true;
        int iter = 0;
        KeyStroke keyStroke;
        while (continueReadingInput) {
            do {
                iter = (iter + 1) % 100;
                if (iter == 0) {
                    // Move monsters
                    moveMonsters();
                    drawCharacters();
                }

                Thread.sleep(5);
                keyStroke = terminal.pollInput();
            } while (keyStroke == null);

            // Check if user wants to quit
            if (keyStroke.getCharacter() == Character.valueOf('q') || keyStroke.getKeyType() == KeyType.Escape) {
                continueReadingInput = false;
                break;
            }

            // Calculate player's new position
            switch (keyStroke.getKeyType()) {
                case ArrowDown -> player.moveDown();
                case ArrowUp -> player.moveUp();
                case ArrowRight -> player.moveRight();
                case ArrowLeft -> player.moveLeft();
            }

            // Check if valid position
            if (!validPlayerPosition(player)) {
//                player.moveToPreviousPosition();
            }

            drawCharacters();
        }
    }

    private static void initGame() throws IOException {
        // Create player
        player = new Player(10, 10, String.valueOf('\u263B'));

        printPlayer();

        // Create monsters
        Monster monster1 = new Monster(20, 15, '\u263B');
        monsters.add(monster1);
        Monster monster2 = new Monster(30, 20, '\u263B');
        monsters.add(monster2);

        // Create and draw obstacles

        // Create and draw bombs
        bombs.add(new Bomb(62, 16));
        tg.setForegroundColor(TextColor.ANSI.RED);
        for (Bomb bomb : bombs) {
            tg.putString(bomb.getX(), bomb.getY(), bomb.getSymbol());
        }

        // Create and draw fruits

        // Draw player and monsters
        drawCharacters();

        // Draw score
        drawScore();
    }

    private static void printPlayer() throws IOException {
        tg.setForegroundColor(TextColor.ANSI.GREEN);
        tg.putString(player.getX(), player.getY(), player.getSymbol());

        terminal.flush();
    }

    private static void moveMonsters() {
        for (Monster monster : monsters) {
            monster.moveMonster(player);
            // Check if valid position
            // monster.moveToPreviousPosition();
        }
    }

    private static boolean validPlayerPosition(Player player) throws IOException {
        // Check if tried to move outside screen
        if (player.getX() < 0 || player.getY() < 3 || player.getX() > terminal.getTerminalSize().getColumns() - 1 || player.getY() > terminal.getTerminalSize().getRows() - 1) {
            return false;
        }

        // Check if tried to move into an obstacle
//        for (Obstacle obstacle : obstacles) {
//            if (player.getX() == obstacle.getX() && player.getY() == obstacle.getY()) {
//                return false;
//            }
//        }

        return true;
    }

    private static boolean validMonsterPosition(Monster monster) throws IOException {
        // Check if tried to move outside screen
//        if (monster.getX() < 0 || monster.getY() < 3 || monster.getX() > terminal.getTerminalSize().getColumns() - 1 || monster.getY() > terminal.getTerminalSize().getRows() - 1) {
//            return false;
//        }

        // Check if tried to move into an obstacle
//        for (Obstacle obstacle : obstacles) {
//            if (player.getX() == obstacle.getX() && player.getY() == obstacle.getY()) {
//                return false;
//            }
//        }

        return true;
    }

    private static void drawCharacters() throws IOException {
        tg.setForegroundColor(TextColor.ANSI.CYAN);
        for (Monster monster : monsters) {
            tg.putString(monster.getMonsterX(), monster.getMonsterY(), String.valueOf(monster.getMonsterCharacter()));
        }
        tg.setForegroundColor(TextColor.ANSI.GREEN);
        tg.putString(player.getX(), player.getY(), player.getSymbol());

        terminal.flush();
    }

    private static void drawScore() throws IOException {
        String str = String.valueOf(score);
        str += " ".repeat(3 - str.length());

        tg.setForegroundColor(TextColor.ANSI.DEFAULT);
        tg.putString(74, 1, str);

        terminal.flush();
    }

    private static void drawMessage(String message) throws IOException {
        int len = message.length();
        tg.setForegroundColor(TextColor.ANSI.DEFAULT);
        tg.putString((74 - len) / 2, 11, "╔══" + "═".repeat(len) + "══╗");
        tg.putString((74 - len) / 2, 12, "║  " + message + "  ║");
        tg.putString((74 - len) / 2, 13, "╚══" + "═".repeat(len) + "══╝");
        terminal.flush();
    }

    private static void endGame() throws IOException, InterruptedException {
        drawMessage("GAME OVER");
        Thread.sleep(10000);
        terminal.close();
    }
}
