import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;

import java.util.ArrayList;
import java.util.List;

public class Obstacle {
    public List<TerminalPosition> getObstacles() {
        return obstacles;
    }

    List<TerminalPosition> obstacles = new ArrayList<>();

    public Obstacle() {
    }

    public void createObstacles(){
        // Create obstacles

        for (int i = 0; i < 10; i++) {
            obstacles.add(new TerminalPosition(10 + i, 10));
        }
        for (int i = 0; i < 6; i++) {
            obstacles.add(new TerminalPosition(30, 8 + i));
        }
        for (int i = 0; i < 12; i++) {
            obstacles.add(new TerminalPosition(58 + i, 14));
        }
        for (int i = 0; i < 8; i++) {
            obstacles.add(new TerminalPosition(46, 12 + i));
        }


    }

    public void printObstacles(){

        // Print obstacles
        for (TerminalPosition p : obstacles) {
            //tg.setForegroundColor(TextColor.ANSI.CYAN);
            //tg.putString(p, String.valueOf(block));
        }
    }

}
