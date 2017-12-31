package com.example.iskander2.clicker;

import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class PopActivity extends AppCompatActivity {
    protected final String TAG = "MyTest";
    Button button;
    TextView result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_window);
        result = findViewById(R.id.result);
        button = findViewById(R.id.restart_button) ;

        final MediaPlayer loseSound = MediaPlayer.create(PopActivity.this, R.raw.losesound);
        final MediaPlayer winSound = MediaPlayer.create(PopActivity.this, R.raw.winsound);

       if (ClickerActivity.Win == true){
           Log.d(TAG, "Starting win scenario");
           winSound.start();
           runOnUiThread(new Runnable() {
               @Override
               public void run() {
                   button.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View view) {
                           restart();
                           ClickerActivity.isWin = false;
                           winSound.stop();
                           Log.d(TAG, "Trying to start background music");
                           ClickerActivity.backgroundMusic.start();
                           finish();
                       }
                   });
               }
           });
       }
       if(ClickerActivity.Win == false) {
            Log.d(TAG, "Starting lose scenario");
            result.setText("Oh no! You Lost!");
            loseSound.start();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            restart();
                            loseSound.stop();
                            ClickerActivity.backgroundMusic.start();
                            finish();
                        }
                    });
                }
            });
        }
    }
    protected void restart(){
        ClickerActivity.clicks = 0;
        ClickerActivity.score.setText("0");
        ClickerActivity.progress = 0;
        ClickerActivity.backgroundMusic.start();
        ClickerActivity.mClicks.edit().clear().apply();
    }
    @Override
    protected void onResume() {
        super.onResume();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        getWindow().setLayout((int)(width*.5),(int)(height*.5));
    }
}
