package game;

import agent.SnakeAgent;
import graphics.SnakeWindow;

import java.awt.*;
import java.util.Random;

//TODO: (bugfix) If the snakes die at location (1,1) the env crashes - this is due to the respawn point set to that exact location
//      will not matter in the real env where you lose the game when you die :)


public class SnakeGame {

    private int mapWidth;
    private int mapHeight;

    private SnakeAgent snake1;
    private SnakeAgent snake2;

    private Point apple;

    private MapTile[][] map;

    private SnakeWindow snakeWindow;

    public Random random = new Random();

    public SnakeGame(int width, int height){

        this.mapWidth = width;
        this.mapHeight = height;

        snakeWindow = new SnakeWindow(this, this.getMapWidth(), this.getMapHeight());

        //Initializes an empty map
        map = new MapTile[this.mapWidth][this.mapHeight];
        for (int i = 0; i < this.mapWidth; i++){
            for (int j = 0; j < this.mapHeight; j++){
                getMap()[i][j] = new MapTile(TileType.Empty, true, -1);
            }
        }

        //Initializes both snakes and the apple
        setSnake1(new SnakeAgent(this, getRandomCoordinate()));
        setSnake2(new SnakeAgent(this, getRandomCoordinate()));
        apple = getRandomCoordinate();
    }

    //Main loop
    public static void main(String[] args){

        SnakeGame game = new SnakeGame(16,16);
        int seconds = 1;

        try {
            while (true) {
                game.update();
                game.render();
                Thread.sleep((long) (seconds * 100));
            }
        }catch(InterruptedException ie){
            ie.printStackTrace();
        }

    }

    public void update(){

        getSnake1().nextAction();
        getSnake2().nextAction();
        updateMap();

    }

    //Updating map to reflect current game state and calculates which snake controls which location
    public void updateMap(){

        //Set empty
        for(int i = 0; i < this.mapWidth; i++){
            for(int j = 0; j < this.mapHeight; j++) {
                map[i][j] = new MapTile(TileType.Empty, true, -1);
            }
        }

        //Set apple
        map[apple.x][apple.y] = new MapTile(TileType.Apple, true, -1);

        //Set body1
        for(int i = 1; i < snake1.getLength(); i++){
            Point p = snake1.getSnake().get(i);
            map[p.x][p.y] = new MapTile(TileType.Snake1Body, false, getSnake1().getLength() - i);
        }

        //Set body2
        for(int i = 1; i < snake2.getLength(); i++){
            Point p = snake2.getSnake().get(i);
            map[p.x][p.y] = new MapTile(TileType.Snake2Body, false, getSnake2().getLength() - i);
        }

        //Set head
        Point snake1head = this.getSnake1().getHead();
        Point snake2head = this.getSnake2().getHead();
        map[snake1head.x][snake1head.y] = new MapTile(TileType.Snake1Head, false, getSnake1().getLength());
        map[snake2head.x][snake2head.y] = new MapTile(TileType.Snake2Head, false, getSnake1().getLength());


        // Sets distances from snakes heads to each location
        snake1.calculateBFSDistances();
        snake2.calculateBFSDistances();

        // Sets map control based on calculated distances
        snake1.setControllingArea(0);
        snake2.setControllingArea(0);
        for (int i = 0; i < mapWidth; i++){
            for(int j = 0; j < mapHeight; j++){

                int result = compareSnakeDistancesBFS(getSnake1(), getSnake2(), i, j);
                if (result < 0){
                    getSnake1().increaseControllingArea();
                } else if (result > 0){
                    getSnake2().increaseControllingArea();
                }
               map[i][j].setControlledBy(result);
            }
        }
    }

    public void render(){
        this.snakeWindow.repaint();
    }

    public Point getRandomCoordinate(){
        return new Point(random.nextInt(getMapWidth()), random.nextInt(getMapHeight()));
    }

    public static int getManhattanDistance(Point first, Point second){
        return (int) (Math.abs(first.getX() - second.getX()) + Math.abs(first.getY() - second.getY()));
    }

    public static int compareSnakeDistancesBFS(SnakeAgent snake1, SnakeAgent snake2, int x, int y){
        return snake1.getDistances()[x][y] - snake2.getDistances()[x][y];
    }

    public int getMapWidth() {
        return mapWidth;
    }

    public int getMapHeight() {
        return mapHeight;
    }

    public MapTile[][] getMap() {
        return map;
    }

    public SnakeAgent getSnake1() {
        return snake1;
    }

    public void setSnake1(SnakeAgent snake1) {
        this.snake1 = snake1;
    }

    public SnakeAgent getSnake2() {
        return snake2;
    }

    public void setSnake2(SnakeAgent snake2) {
        this.snake2 = snake2;
    }
}
