package graphics;

import agent.SnakeAgent;
import game.*;

import javax.swing.*;
import java.awt.*;
import java.lang.*;

public class SnakeCanvas extends JPanel {

    private int tileSize = 32;
    private int width;
    private int height;

    private SnakeGame game;

    public SnakeCanvas(SnakeGame snakeGame, int width, int height){

        this.game = snakeGame;
        this.width = width;
        this.height = height;
        this.setPreferredSize(new Dimension(tileSize * (width * 3 + 2), tileSize * (height + 2)));

    }

    public void renderMap(Graphics g){

        MapTile[][] map = game.getMap();
        for (int i = 0; i < map.length; i++){
            for (int j = 0; j < map[0].length; j++){

                Color c = null;
                switch (map[i][j].getType()){
                    case Snake1Body: c = new Color(65, 128, 65); break;
                    case Snake1Head: c = new Color(65, 255, 65); break;
                    case Snake2Body: c = new Color(128, 65, 65); break;
                    case Snake2Head: c = new Color(255, 65, 65); break;
                    case Apple: c = Color.yellow; break;
                    case Empty: c = Color.gray; break;
                }

                g.setColor(c);
                g.fillRect(i * tileSize + 1 ,j * tileSize + 1,tileSize - 2,tileSize - 2);
            }
        }
    }

    public void renderControlMap(Graphics g){

        MapTile[][] map = game.getMap();
        for (int i = 0; i < map.length; i++){
            for (int j = 0; j < map[0].length; j++){

                Color c = Color.gray;
                int result = map[i][j].getControlledBy();
                if (result < 0) {
                    c = new Color(65, 128, 65);
                } else if (result > 0) {
                    c = new Color(128, 65, 65);
                }

                if (!map[i][j].isWalkable()){
                    c = new Color(65,65,65);
                }
                g.setColor(c);

                g.fillRect((i + width + 1) * tileSize + 1 ,j * tileSize + 1,tileSize - 2,tileSize - 2);

                g.setFont(new Font("Arial", Font.PLAIN, 16));
                g.setColor(Color.black);
                if (map[i][j].isWalkable()) {
                    g.drawString("" + map[i][j].getControlledBy(), (i + width + 1) * tileSize + 5, j * tileSize + 20);
                }
            }
        }
    }

    public void renderTurnsUntilWalkableInfo(Graphics g){

        MapTile[][] map = game.getMap();
        for (int i = 0; i < map.length; i++){
            for (int j = 0; j < map[0].length; j++){

               g.setFont(new Font("Arial", Font.PLAIN, 16));
               g.setColor(Color.black);
               g.drawString("" + map[i][j].getTurnsUntilWalkable(), i * tileSize + 5, j * tileSize + 20);
            }
        }
    }

    public void renderInfo(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        Font font = new Font("Serif", Font.BOLD, 24);
        g2d.setFont(font);

        int snake1Area = Math.round(game.getSnake1().getControllingArea() * 100 / (width * height));
        g2d.setColor(new Color(65,128,65));
        g2d.drawString("Snake 1 area: " + snake1Area + "%", tileSize * 2, (height + 1) * tileSize);

        int snake2Area = Math.round(game.getSnake2().getControllingArea() * 100 / (width * height));
        g2d.setColor(new Color(128,65,65));
        g2d.drawString("Snake 2 area: " + snake2Area + "%", tileSize * (width + 2) / 2, (height + 1) * tileSize);
    }

    public void renderDistance(Graphics g, SnakeAgent snake){

        MapTile[][] map = game.getMap();
        for (int i = 0; i < map.length; i++){
            for (int j = 0; j < map[0].length; j++){

                Color c = null;
                switch (map[i][j].getType()){
                    case Snake1Body: c = new Color(65, 128, 65); break;
                    case Snake1Head: c = new Color(65, 255, 65); break;
                    case Snake2Body: c = new Color(128, 65, 65); break;
                    case Snake2Head: c = new Color(255, 65, 65); break;
                    case Apple: c = Color.yellow; break;
                    case Empty: c = Color.gray; break;
                }

                g.setColor(c);
                g.fillRect((i + 2 * width + 2) * tileSize + 1 ,j * tileSize + 1,tileSize - 2,tileSize - 2);

                g.setFont(new Font("Arial", Font.PLAIN, 16));
                g.setColor(Color.black);
                g.drawString("" + snake.getDistances()[i][j], (i + 2 * width + 2) * tileSize + 5, j * tileSize + 20);
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        renderMap(g);
        renderControlMap(g);
        renderDistance(g, game.getSnake1());

        renderTurnsUntilWalkableInfo(g);
        renderInfo(g);
    }
}