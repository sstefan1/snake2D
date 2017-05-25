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
            float radius = Math.min(tileHeight, tileWidth) / 2;

            for(int i = 0; i<GameEngine.levelWidth; i++){
                for(int j = 0; j<GameEngine.levelHeight; j++){
                    switch(level[i][j]){
                        case EMPTY:
                            paint.setColor(Color.LTGRAY);
                            break;
                        case WALL:
                            paint.setColor(Color.BLUE);
                            break;
                        case SNAKE_HEAD:
                            paint.setColor(Color.GREEN);
                            break;
                        case SNAKE_TAIL:
                            paint.setColor(Color.BLACK);
                            break;
                        case FRUIT:
                            paint.setColor(Color.RED);
                            break;
                    }
                    float cx = i * tileWidth + radius;
                    float cy = j * tileHeight + radius;

                    canvas.drawCircle(cx, cy, radius, paint);
                }
            }
        }
    }
}
