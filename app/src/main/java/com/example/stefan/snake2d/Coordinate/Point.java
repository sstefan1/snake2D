package com.example.stefan.snake2d.Coordinate;

/**
 * Created by stefan on 24.5.17..
 */

public class Point {
    private int x;
    private int y;

    public Point(int x, int y){
        this.x = x;
        this.y = y;
    }

    public void setX(int x){this.x = x;}
    public void setY(int y){this.y = y;}

    public int getX(){return this.x;}
    public int getY(){return this.y;}

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Point point = (Point) o;

        return (y == point.y && x == point.x);
    }
}
