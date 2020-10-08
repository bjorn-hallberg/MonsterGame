import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    public static void main(String[] args) throws Exception {
        DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory();
        Terminal terminal = terminalFactory.createTerminal();
        TextGraphics tg = terminal.newTextGraphics();

        terminal.setCursorVisible(false);

        int x = 4;
        int y = 4;
        final char player = '\u263B';
        final char block = '\u2588';
        final char bomb = '\u2622';
        final char monster = '\u263B';
        int monsterX = 40;
        int monsterY = 20;

        // Print player
        tg.setForegroundColor(TextColor.ANSI.GREEN);
        tg.putString(x, y, String.valueOf(player));

        // Print monster
        tg.setForegroundColor(TextColor.ANSI.YELLOW);
        tg.putString(monsterX, monsterY, String.valueOf(monster));


        // Create and print bomb
        Random rnd = new Random();
        TerminalPosition bombPosition = new TerminalPosition(rnd.nextInt(40) * 2, rnd.nextInt(12) * 2);
        tg.setForegroundColor(TextColor.ANSI.RED);
        tg.putString(bombPosition, String.valueOf(bomb));

        terminal.flush();

        boolean continueReadingInput = true;
        do {
            KeyStroke keyStroke = null;
            do {
                Thread.sleep(5); // might throw InterruptedException
                keyStroke = terminal.pollInput();
            } while (keyStroke == null);
            KeyType type = keyStroke.getKeyType();
            Character c = keyStroke.getCharacter(); // used Character instead of char because it might be null

//            System.out.println("keyStroke.getKeyType(): " + type
//                    + " keyStroke.getCharacter(): " + c);

            // Check if user wants to quit
            if (c == Character.valueOf('q') || type == KeyType.Escape) {
                continueReadingInput = false;
                System.out.println("quit");
            }

            // Save player's old position
            int oldX = x; // save old position x
            int oldY = y; // save old position y

            // Calculate player's new position
            switch (keyStroke.getKeyType()) {
                case ArrowDown -> y += 2;
                case ArrowUp -> y -= 2;
                case ArrowRight -> x += 2;
                case ArrowLeft -> x -= 2;
            }

            // Save monster's old position
            int oldMonsterX = monsterX; // save old position x
            int oldMonsterY = monsterY; // save old position y

            // Calculate monster's new position
            if (x < monsterX) {
                monsterX--;
            } else if (x > monsterX) {
                monsterX++;
            }
            if (y < monsterY) {
                monsterY--;
            } else if (y > monsterY) {
                monsterY++;
            }

            // Detect if player moved out of screen
            if (x < 0 || y < 0 || x > terminal.getTerminalSize().getColumns() - 1 || y > terminal.getTerminalSize().getRows() - 1) {
                x = oldX;
                y = oldY;
            }

            // Detect if player tries to run into obstacle
            boolean playerMovedIntoObstacle = false;
            for (TerminalPosition p : obstacles) {
                if (p.getColumn() == x && p.getRow() == y) {
                    playerMovedIntoObstacle = true;
                }
            }

            // Detect if monster tries to run into obstacle
            boolean monsterMovedIntoObstacle = false;
            for (TerminalPosition p : obstacles) {
                if (p.getColumn() == monsterX && p.getRow() == monsterY) {
                    monsterMovedIntoObstacle = true;
                }
            }

            if (playerMovedIntoObstacle) {
                // Restore player's position
                x = oldX;
                y = oldY;
            } else {
                // Move player
                tg.setForegroundColor(TextColor.ANSI.DEFAULT);
                tg.putString(oldX, oldY, " ");
                tg.setForegroundColor(TextColor.ANSI.GREEN);
                tg.putString(x, y, String.valueOf(monster));
            }

            // Detect if monster caught player
            boolean monsterCaughtPlayer = false;
            if (monsterX == x && monsterY == y) {
                monsterCaughtPlayer = true;
            }

            if (monsterMovedIntoObstacle) {
                // Restore monster's position
                monsterX = oldMonsterX;
                monsterY = oldMonsterY;
            } else {
                // Move monster
                tg.setForegroundColor(TextColor.ANSI.DEFAULT);
                tg.putString(oldMonsterX, oldMonsterY, " ");
                tg.setForegroundColor(TextColor.ANSI.YELLOW);
                tg.putString(monsterX, monsterY, String.valueOf(monster));
            }

            // Check if monster caught player
            if (monsterCaughtPlayer) {
                tg.setForegroundColor(TextColor.ANSI.DEFAULT);
                tg.putString(x, y + 2, "Game over!");
                terminal.flush();
                Thread.sleep(5000); // might throw InterruptedException
                continueReadingInput = false;
            }

            // Check if player runs into the bomb
            if (bombPosition.getColumn() == x && bombPosition.getRow() == y) {
                tg.setForegroundColor(TextColor.ANSI.RED);
                tg.putString(x, y, String.valueOf(player));
                tg.setForegroundColor(TextColor.ANSI.DEFAULT);
                tg.putString(x, y + 2, "Boom! You stepped on a bomb.");
                terminal.flush();
                Thread.sleep(5000); // might throw InterruptedException
                continueReadingInput = false;
            }

            // Check if monster runs into the bomb
            if (bombPosition.getColumn() == monsterX && bombPosition.getRow() == monsterY) {
                tg.setForegroundColor(TextColor.ANSI.RED);
                tg.putString(monsterX, monsterY, String.valueOf(monster));
                tg.setForegroundColor(TextColor.ANSI.DEFAULT);
                tg.putString(monsterX, monsterY + 2, "You won!");
                terminal.flush();
                Thread.sleep(5000); // might throw InterruptedException
                continueReadingInput = false;
            }

            terminal.flush();
        } while (continueReadingInput);

        terminal.close();
    }
}
