package graphics;

import game.SnakeGame;
import javax.swing.*;

public class SnakeWindow extends JFrame {

    private SnakeCanvas snakeCanvas;


    public SnakeWindow(SnakeGame snakeGame, int mapWidth, int mapHeight){

        snakeCanvas = new SnakeCanvas(snakeGame, mapWidth,mapHeight);

        this.add(getSnakeCanvas());

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setResizable(false);
        this.setVisible(true);

    }

    public SnakeCanvas getSnakeCanvas() {
        return snakeCanvas;
    }

}
