package com.example.stefan.snake2d.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.stefan.snake2d.engine.GameEngine;
import com.example.stefan.snake2d.enums.TileType;

/**
 * Created by stefan on 24.5.17..
 */

public class SnakeView extends View{

    private Paint paint = new Paint();
    private Paint paint1 = new Paint();
    private TileType level[][];

    public SnakeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void setLevel(TileType[][] level){this.level = level;}

    @Override
    public void onDraw(Canvas canvas){
        super.onDraw(canvas);

        if(level != null){
            float tileWidth = canvas.getWidth() / GameEngine.levelWidth;
            float tileHeight = canvas.getHeight() / GameEngine.levelHeight;

            for(int i = 0; i<GameEngine.levelWidth; i++){
                for(int j = 0; j<GameEngine.levelHeight; j++){
                    switch(level[i][j]){
                        case EMPTY:
                            paint.setColor(Color.BLACK);
                            paint1.setColor(Color.GREEN);
                            break;
                        case WALL:
                            if(!GameEngine.transparentWalls)
                                paint.setColor(Color.BLUE);
                            else
                                paint.setColor(Color.BLACK);
                            paint1.setColor(Color.BLUE);
                            break;
                        case SNAKE_HEAD:
                            paint.setColor(Color.BLUE);
                            paint1.setColor(Color.BLUE);
                            break;
                        case SNAKE_TAIL:
                            paint.setColor(Color.GREEN);
                            paint1.setColor(Color.GREEN);
                            break;
                        case FRUIT:
                            paint.setColor(Color.RED);
                            paint1.setColor(Color.RED);
                            break;
                    }
                    float cx = i * tileWidth;// + radius;
                    float cy = j * tileHeight; // + radius;

                    canvas.drawRect(cx, cy, cx+tileWidth, cy+tileHeight, paint1);
                    canvas.drawRect(cx + 1, cy + 1, cx+tileWidth - 1, cy+tileHeight - 1, paint);
                }
            }
        }
    }
}
