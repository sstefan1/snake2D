package com.example.stefan.snake2d.activities;

import android.content.Intent;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.example.stefan.snake2d.R;
import com.example.stefan.snake2d.engine.GameEngine;
import com.example.stefan.snake2d.enums.Directions;
import com.example.stefan.snake2d.enums.GameState;
import com.example.stefan.snake2d.views.SnakeView;

import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener{

    private GameEngine engine;
    private SnakeView snakeView;
    private final Handler handler = new Handler();
    private float prevX, prevY;
    public static int delay = 150;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        engine = new GameEngine();
        engine.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        engine.init();

        snakeView = (SnakeView)findViewById(R.id.snakeView);
        snakeView.setOnTouchListener(this);

        startUpdateHandler();
    }

    private void startUpdateHandler(){
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                engine.update();

                if(engine.getCurrentState() == GameState.IN_PROGRESS)
                    handler.postDelayed(this, delay);
                else if(engine.getCurrentState() == GameState.FINISHED)
                    youLostToast();
                snakeView.setLevel(engine.getLevel());
                snakeView.invalidate();
            }
        }, delay);
    }

    private void youLostToast(){
        Toast.makeText(this, "You Lost!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        if(event.getAction()==MotionEvent.ACTION_DOWN){
            if(engine.getCurrentState() == GameState.FINISHED){
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
                return true;
            }
        }

        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                prevX = event.getX();
                prevY = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                float newX = event.getX();
                float newY = event.getY();

                // Swipe direction
                if(Math.abs(prevX - newX) > Math.abs(prevY - newY)){
                    // it's LEFT/RIGHT
                    if(newX > prevX)
                        engine.updateDirection(Directions.RIGHT);
                    else
                        engine.updateDirection(Directions.LEFT);
                }else{
                    // it's UP/DOWN
                    if(newY > prevY)
                        engine.updateDirection(Directions.DOWN);
                    else
                        engine.updateDirection(Directions.UP);
                }
                break;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void openPreferenceSettings(){
        Intent settings = new Intent(MainActivity.this,SettingsActivity.class);
        startActivity(settings);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.action_settings:
                openPreferenceSettings();
        }

        return super.onOptionsItemSelected(item);
    }
}



