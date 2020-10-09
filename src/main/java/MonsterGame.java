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
                    // Print fruits again if monster moved over them
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
                drawMessage("Game ended");
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
            if (invalidCharacterPosition(player)) {
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

            // Check if played ran into a monster
            if (hasMonsterCaughtPlayer()) {
                drawMessage("GAME OVER");
                break;
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
        player = new Player(40, 10);

        // Create monsters
        monsters.add(new Monster(55, 13));
        monsters.add(new Monster(39, 7));
        monsters.add(new Monster(5, 8));
        monsters.add(new Monster(25, 22));
        monsters.add(new Monster(71, 18));



        // Create and draw obstacles
//        for (int i = 0; i < terminal.getTerminalSize().getColumns(); i++) {
//            obstacles.add(new Obstacle(i,3)); // "Roof"
//            obstacles.add(new Obstacle(i,23)); // "Ceiling"
//        }
        for (int i = 0; i < (terminal.getTerminalSize().getColumns()/3); i++) {
            obstacles.add(new Obstacle(i+26,6));

            obstacles.add(new Obstacle(i+26,18));
        }


        for (int i = 0; i < (terminal.getTerminalSize().getColumns()/6); i++) {
            obstacles.add(new Obstacle(i + 5, 9));
            obstacles.add(new Obstacle(i + 58, 9));
            obstacles.add(new Obstacle(i + 5, 15));
            obstacles.add(new Obstacle(i + 58, 15));
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
        fruits.add(new Fruit(38, 7));
        fruits.add(new Fruit(4, 8));
        fruits.add(new Fruit(24, 22));
        fruits.add(new Fruit(70, 18));
        drawGameObjects(fruits, TextColor.ANSI.YELLOW);

        // Draw player and monsters
        drawCharacters();

        // Draw score
        drawScore();
    }

    private static void moveMonsters() throws IOException {
        for (Monster monster : monsters) {
            monster.moveMonster(player);
            // Check if valid position
            if (invalidCharacterPosition(monster)) {
                monster.moveToPreviousPosition();
            }
        }
    }

    private static boolean invalidCharacterPosition(GameCharacter character) throws IOException {
        // Check if player tried to move outside screen
        if (character.getX() < 0 ||
                character.getY() < 3 ||
                character.getX() > terminal.getTerminalSize().getColumns() - 1 ||
                character.getY() > terminal.getTerminalSize().getRows() - 1) {
            return true;
        }

        // Check if player tried to move into an obstacle
        for (Obstacle obstacle : obstacles) {
            if (character.hasSamePosition(obstacle)) {
                return true;
            }
        }

        return false;
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
                if (monster.hasSamePosition(bomb)) {
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
