import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class MonsterGame {
    static private Terminal terminal;
    static private TextGraphics tg;

    static private Player player;
    static private List<Monster> monsters = new ArrayList<>();
    static private List<Obstacle> obstacles = new ArrayList<>();
    static private List<GameObject> bombs = new ArrayList<>();
    static private List<GameObject> fruits = new ArrayList<>();

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
        // Initiate game
        initGame();

        // Run game
        boolean continueReadingInput = true;
        boolean monsterHasCaughtPlayer = false;
        int iter = 0;
        KeyStroke keyStroke = null;
        while (continueReadingInput) {
            // Loop while no input from user
            do {
                iter = (iter + 1) % 100;
                if (iter == 0) {
                    // Print game object again if monster moved over them
                    drawGameObjects(fruits, TextColor.ANSI.YELLOW);

                    // Move monsters
                    moveMonsters();
                    drawCharacters();

                    // Check if any monster has caught player
                    if (hasMonsterCaughtPlayer()) {
                        monsterHasCaughtPlayer = true;
                        continueReadingInput = false;
                        break;
                    }

                    // Check if monster stepped on a bomb
                    hasMonsterSteppedOnBomb();
                }

                Thread.sleep(5);
                keyStroke = terminal.pollInput();
            } while (keyStroke == null);

            // End game if monster has caught player
            if (monsterHasCaughtPlayer) {
                drawMessage("GAME OVER");
                break;
            }

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

            // Check if valid player position
            if (!validPlayerPosition(player)) {
                player.moveToPreviousPosition();
            }

            drawCharacters();

            // Check if player picked a fruit
            for (GameObject fruit : fruits) {
                if (player.getX() == fruit.getX() && player.getY() == fruit.getY()) {
                    score++;
                    drawScore();
                    fruits.remove(fruit);
                    break;
                }
            }

            // Check if player won
            if (fruits.size() == 0) {
                drawMessage("YOU WON!");
                continueReadingInput = false;
            }

            // Check if player stepped on a bomb
            for (GameObject bomb : bombs) {
                if (player.getX() == bomb.getX() && player.getY() == bomb.getY()) {
                    tg.setForegroundColor(TextColor.ANSI.RED);
                    tg.putString(player.getX(), player.getY(), player.getSymbol());
                    terminal.flush();
                    drawMessage("GAME OVER");
                    continueReadingInput = false;
                    break;
                }
            }
        }
    }

    private static void initGame() throws IOException {
        // Create terminal
        terminal = new DefaultTerminalFactory().createTerminal();
        terminal.setCursorVisible(false);
        tg = terminal.newTextGraphics();
        tg.putString(0, 0, "╔═══════════════════════════════════════════════════════════════╤══════════════╗");
        tg.putString(0, 1, "║                                 MONSTER GAME                  │  Score:      ║");
        tg.putString(0, 2, "╚═══════════════════════════════════════════════════════════════╧══════════════╝");

        // Create player
        player = new Player(40, 10, String.valueOf('\u263B'));

        printPlayer();
        // Create monsters
        for (int i=0;i<10;i++){
            monsters.add(new Monster(ThreadLocalRandom.current().nextInt(1,44), ThreadLocalRandom.current().nextInt(1,50)));

        // Create and draw obstacles
        Obstacle obstacle1 = new Obstacle(60, 14);
        obstacles.add(obstacle1);
        obstacles.add(new Obstacle(5, 5));
        // Add obstacles on a horizontal line at Y height
        for (int i = 0; i < terminal.getTerminalSize().getColumns(); i++) {
            obstacles.add(new Obstacle(i,6));
        }
        for (Obstacle obstacle : obstacles) {
            tg.setForegroundColor(TextColor.ANSI.MAGENTA);
            tg.putString(obstacle.getX(), obstacle.getY(), obstacle.getSymbol());
        }






        // Create and draw bombs
        bombs.add(new Bomb(62, 16));
        bombs.add(new Bomb(22, 8));
        drawGameObjects(bombs, TextColor.ANSI.RED);

        // Create and draw fruits
        fruits.add(new Fruit(54, 12));
        fruits.add(new Fruit(38, 6));
        fruits.add(new Fruit(4, 8));
        fruits.add(new Fruit(24, 22));
        fruits.add(new Fruit(70, 18));
        drawGameObjects(fruits, TextColor.ANSI.YELLOW);

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

    private static void moveMonsters() throws IOException {
        for (Monster monster : monsters) {
            monster.moveMonster(player);
            // Check if valid position
            if (!validMonsterPosition(monster)) {
                monster.moveToPreviousPosition();
            }
        }
    }

    private static boolean validPlayerPosition(Player player) throws IOException {
        // Check if player tried to move outside screen
        if (player.getX() < 0 || player.getY() < 3 || player.getX() > terminal.getTerminalSize().getColumns() - 1 || player.getY() > terminal.getTerminalSize().getRows() - 1) {
            return false;
        }

        // Check if player tried to move into an obstacle
        for (Obstacle obstacle : obstacles) {
            if (player.hasSamePosition(obstacle)) {
                return false;
            }
        }

        return true;
    }

    private static boolean validMonsterPosition(Monster monster) throws IOException {
        // Check if monster tried to move outside screen
        if (monster.getX() < 0 || monster.getY() < 3 || monster.getX() > terminal.getTerminalSize().getColumns() - 1 || monster.getY() > terminal.getTerminalSize().getRows() - 1) {
            return false;
        }

        // Check if monster tried to move into an obstacle
        for (Obstacle obstacle : obstacles) {
            if (monster.hasSamePosition(obstacle)) {
                return false;
            }
        }

        return true;
    }

    private static boolean hasMonsterCaughtPlayer() {
        for (Monster monster : monsters) {
            if (monster.hasSamePosition(player)) {
                return true;
            }
        }
        return false;
    }

    private static boolean hasMonsterSteppedOnBomb() {
        boolean anyMonsterHasSteppedOnBomb = false;
        for (Monster monster : monsters) {
            boolean monsterHasSteppedOnBomb = false;
            for (GameObject bomb : bombs) {
                if (monster.getX() == bomb.getX() && monster.getY() == bomb.getY()) { // monster.hasSamePosition(bomb)
                    bombs.remove(bomb);
                    monsterHasSteppedOnBomb = true;
                    break;
                }
            }
            if (monsterHasSteppedOnBomb) {
                tg.putString(monster.getX(), monster.getY(), " ");
                monsters.remove(monster);
                anyMonsterHasSteppedOnBomb = true;
                break;
            }
        }
        return anyMonsterHasSteppedOnBomb;
    }

    private static void drawCharacters() throws IOException {
        // Draw player
        tg.setForegroundColor(TextColor.ANSI.GREEN);
        tg.putString(player.getPreviousX(), player.getPreviousY(), " ");
        tg.putString(player.getX(), player.getY(), player.getSymbol());

        // Draw monsters
        tg.setForegroundColor(TextColor.ANSI.CYAN);
        for (Monster monster : monsters) {
            tg.putString(monster.getPreviousX(), monster.getPreviousY(), " ");
            tg.putString(monster.getX(), monster.getY(), String.valueOf(monster.getSymbol()));
        }

        terminal.flush();
    }

    private static void drawGameObjects(List<GameObject> objectList, TextColor color) throws IOException {
        // Draw game objects
        tg.setForegroundColor(color);
        for (GameObject object : objectList) {
            tg.putString(object.getX(), object.getY(), String.valueOf(object.getSymbol()));
        }

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
        Thread.sleep(10000);
        terminal.close();
    }
}
