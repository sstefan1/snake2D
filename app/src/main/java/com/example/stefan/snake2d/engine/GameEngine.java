package com.example.stefan.snake2d.engine;

import com.example.stefan.snake2d.Coordinate.Point;
import com.example.stefan.snake2d.enums.Directions;
import com.example.stefan.snake2d.enums.GameState;
import com.example.stefan.snake2d.enums.TileType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by stefan on 24.5.17..
 */

public class GameEngine {
    public static final int levelWidth = 28;
    public static final int levelHeight = 42;

    private List<Point> wallPoints = new ArrayList<>();
    private List<Point> snake = new ArrayList<>();
    private Point fruit;

    private Directions currentDirection = Directions.RIGHT;
    private GameState currentState = GameState.IN_PROGRESS;

    private boolean increaseTail = false; // If fruit has been eaten, snake should grow!


    public GameEngine(){}

    public void init() {
        addWallPoints();
        addSnake();
        addFruit();
    }

    public TileType[][] getLevel(){
        TileType[][] level = new TileType[levelWidth][levelHeight];

        for(int i = 0; i < GameEngine.levelWidth; i++){
            for(int j = 0; j<GameEngine.levelHeight; j++){
                level[i][j] = TileType.EMPTY;
            }
        }

        for(Point p : wallPoints){
            level[p.getX()][p.getY()] = TileType.WALL;
        }

        for(Point p : snake)
            level[p.getX()][p.getY()] = TileType.SNAKE_TAIL;

        level[snake.get(0).getX()][snake.get(0).getY()] = TileType.SNAKE_HEAD;

        level[fruit.getX()][fruit.getY()] = TileType.FRUIT;

        return level;
    }

    private void addWallPoints(){
        for(int i = 0; i<levelHeight; i++){
            if(i<levelWidth) {
                wallPoints.add(new Point(i, 0));
                wallPoints.add(new Point(i, levelHeight - 1));
            }

            wallPoints.add(new Point(0, i));
            wallPoints.add(new Point(levelWidth-1, i));
        }
    }

    private void addSnake(){
        int x = (int) (Math.random() * (GameEngine.levelWidth - 2) + 1);
        int y = (int) (Math.random() * (GameEngine.levelHeight - 2) + 1);
        snake.add(new Point(x, y));

        /*
            snake.add(new Point(x+1, y));
            snake.add(new Point(x+2, y));
            snake.add(new Point(x+3, y));
        */
    }

    public void addFruit(){
        boolean added = false;

        while(!added) {
            int x = (int) (Math.random() * (GameEngine.levelWidth - 2) + 1);
            int y = (int) (Math.random() * (GameEngine.levelHeight - 2) + 1);

            Point tryFruit = new Point(x, y);

            boolean match = false;

            // Check for availability
            for (Point p : snake)
                if(p.equals(tryFruit))
                    match = true;

            if(match != true) {
                fruit = tryFruit;
                added = true;
            }
        }
    }

    public void update(){
        switch(currentDirection){
            case UP:
                updateSnake(0, -1);
                break;
            case DOWN:
                updateSnake(0, 1);
                break;
            case LEFT:
                updateSnake(-1, 0);
                break;
            case RIGHT:
                updateSnake(1, 0);
                break;
        }

        // Check for wall collision
        for (Point p : wallPoints){
            if(snake.get(0).equals(p))
                currentState = GameState.FINISHED;
        }

        // Check for snake parts collision
        for(int i = 1; i < snake.size(); i++)
            if(snake.get(i).equals(getHead()))
                currentState = GameState.FINISHED;


        if(getHead().equals(fruit)){
            //fruit = null;
            addFruit();
            increaseTail = true;
        }
    }

    private void updateSnake(int x, int y){

        int tailX = snake.get(snake.size() - 1).getX();         // When snake is updated, new(old) tail should be added (if needed)
        int tailY = snake.get(snake.size() - 1).getY();

        for(int i = snake.size() - 1; i>0; i--){
            snake.get(i).setX(snake.get(i-1).getX());       // Move each part of the tail to the place of its predecessor
            snake.get(i).setY(snake.get(i-1).getY());
        }

        snake.get(0).setX(snake.get(0).getX() + x);         // Move HEAD to appropriate point
        snake.get(0).setY(snake.get(0).getY() + y);

        if(increaseTail) {
            snake.add(new Point(tailX, tailY));
            increaseTail = false;
        }
    }

    public GameState getCurrentState(){return this.currentState;}

    public void updateDirection(Directions newDirection){
        if(Math.abs(newDirection.ordinal() - currentDirection.ordinal()) % 2 == 1)
            currentDirection = newDirection;
    }

    private Point getHead(){return snake.get(0);}
}