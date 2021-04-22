package agent;

import game.*;
import java.awt.*;
import java.util.ArrayList;


public class SnakeAgent {

    private ArrayList<Point> snake = new ArrayList<Point>();
    private SnakeGame game;

    private int[][] distances;
    private int controllingArea = 0;

    public SnakeAgent(SnakeGame game, Point start){
        this.game = game;
        this.setDistances(new int[game.getMapWidth()][game.getMapHeight()]);
        snake.add(start);
    }

    // Selects next move for the snake
    public boolean nextAction(){


        ArrayList<Point> validMoves = getValidMoves();
        if (validMoves.isEmpty()){ //If the list is empty we lost the game
            this.eat(new Point(1,1)); //For testing purposes, instead teleport it to (1,1)...
            return false;
        }

        // Get a random move from the list of valid moves
        int index = game.random.nextInt(validMoves.size());
        int newX = validMoves.get(index).x;
        int newY = validMoves.get(index).y;

        // For testing purposes every action increases the snakes length, up to 15
        this.eat(new Point(newX, newY));
        if (snake.size() > 15){
            snake.remove(snake.size() - 1);
        }
        return true;
    }

    public ArrayList<Point> getValidMoves(){

        ArrayList<Point> validMoves = new ArrayList<>();
        int R[] = {0, -1, 0, 1};
        int C[] = {-1, 0, 1, 0};

        for (int i = 0; i < 4; i++){
            int newX = this.getHead().x + R[i];
            int newY = this.getHead().y + C[i];
            if (isInBounds(newX, newY) && game.getMap()[newX][newY].isWalkableIn(1)){
                validMoves.add(new Point(newX, newY));
            }
        }
        return validMoves;
    }


    public void move(Point newPoint){
        snake.add(0, newPoint);
        snake.remove(snake.size() - 1);
    }

    public void eat(Point newPoint){
        snake.add(0, newPoint);
    }



    public void increaseControllingArea(){
        this.controllingArea++;
    }

    public void calculateBFSDistances(){

        ArrayList<Point> frontier = new ArrayList<Point>();
        for (int i = 0; i < game.getMapWidth(); i++){
            for (int j = 0; j < game.getMapHeight(); j++){
                distances[i][j] = 999; //represents unreachable location
            }
        }
        frontier.add(0, getHead());
        distances[getHead().x][getHead().y] = 0;

        int R[] = {0, -1, 0, 1};
        int C[] = {-1, 0, 1, 0};

        while (!frontier.isEmpty()){

            int x = frontier.get(0).x;
            int y = frontier.get(0).y;
            frontier.remove(0);

            for (int i = 0; i < 4; i++){

                int newX = x + R[i];
                int newY = y + C[i];
                int newDistance = distances[x][y] + 1;

                if (isInBounds(newX, newY) && getDistances()[newX][newY] == 999) { // 999 represents uncreachable location

                    if (isWalkable(newX, newY) || newDistance >= game.getMap()[newX][newY].getTurnsUntilWalkable()) {
                        frontier.add(frontier.size(), new Point(newX, newY));
                        distances[newX][newY] = newDistance;
                    }
                }

            }
        }
    }

    public boolean isValid(int x, int y){

        if (!isInBounds(x,y)){
            return false;
        }

        if (!isWalkable(x,y)){
            return false;
        }

        if (getDistances()[x][y] != 999){ // 999 represents unreachable location
            return false;
        }
        return true;
    }

    public boolean isWalkable(int x, int y){
        return game.getMap()[x][y].isWalkable();
    }

    public boolean isInBounds(int x, int y){
        return x >= 0 && x < game.getMapWidth() && y >= 0 && y < game.getMapHeight();
    }

    public ArrayList<Point> getSnake(){ return snake; }

    public Point getHead(){
        return snake.get(0);
    }

    public int getLength(){
        return snake.size();
    }

    public int[][] getDistances() { return distances; }

    public void setDistances(int[][] distances) { this.distances = distances; }

    public int getControllingArea() { return controllingArea; }

    public void setControllingArea(int controllingArea) { this.controllingArea = controllingArea; }

}
