import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Obstacle {

    List<TerminalPosition> obstacles = new ArrayList<>();

    private int length;
    private int column;
    private int row;

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }



    public Obstacle(int length, int column, int row) throws IOException {
        setLength(length);
        setColumn(column);
        setRow(row);
    }



    public void createObstacle() throws IOException {
        // Create obstacles

        for (int i = 0; i < getLength(); i++) {
            obstacles.add(new TerminalPosition(getColumn() + i, getRow()));
        }


    }



    public void printObstacles(){


    }

        /*  // Create random obstacles
        for (int i = 0; i < 10; i++) {
            obstacles.add(new TerminalPosition(((int)(Math.random() * (58 - 10 + 1) + 10)) + i, ((int)(Math.random() * (14 - 8 + 1) + 8))));
        }
        for (int i = 0; i < (int)(Math.random() * (12 - 1 + 1) + 1); i++) {
            obstacles.add(new TerminalPosition(((int)(Math.random() * (58 - 10 + 1) + 10)), ((int)(Math.random() * (14 - 8 + 1) + 8)) + i));
        }
        // Create ceiling and floor
        // ceiling
        for (int i = 0; i < terminal.getTerminalSize().getColumns(); i++) {
            obstacles.add(new TerminalPosition(0 + i, 0));
        }
        // floor
        for (int i = 0; i < terminal.getTerminalSize().getColumns(); i++) {
            obstacles.add(new TerminalPosition(0 + i, terminal.getTerminalSize().getRows()-1));
        } */

}
