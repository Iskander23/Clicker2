package com.example.iskander2.clicker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.util.Random;


public class ClickerActivity extends AppCompatActivity {

    protected static int clicks =0;
    int i=1;
    static final String APP_PREFERENCES_CLICKS = "Clicks";
    static final String APP_PREFERENCES = "Settings";
    static protected SharedPreferences mClicks ;

    protected final String TAG = "MyTest";
    protected static ProgressBar progressBar;
    protected static int progress = 0;
    protected static TextView score;
    private ImageView coinView;
    TextView result1;

    public static boolean isWin;
    protected static boolean Win;
    PopupWindow popupWindow;
    protected static MediaPlayer backgroundMusic;

    LinearLayout mainLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clicker);
        mClicks = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        score = (TextView) findViewById(R.id.score);

        //Spawning the Coin

         Thread spawnThread = new Thread(new Runnable() {
            @Override
            public void run() {
                coinView = (ImageView) findViewById(R.id.imageView);
                spawnCoin();
                coinView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        clicks = Integer.parseInt(score.getText().toString());
                        score.setText(Integer.toString(++clicks));
                        coinView.setVisibility(View.INVISIBLE);
                        spawnCoin();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                coinView.setVisibility(View.VISIBLE);
                            }
                        },500);
                    }
                });
            }
        });
        spawnThread.start();

        //Updating ProgressBar
        Thread progressBarThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(1000);
                        progressBar.setProgress(progress++);
                    } catch (Exception e) {
                    }
                }
            }
        });
        progressBarThread.start();
        //Checking for Win or Lose
        new Thread(new Runnable() {
            @Override
            public void run() {
                final Intent intent = new Intent(ClickerActivity.this, PopActivity.class);
                 View view = getLayoutInflater().inflate(R.layout.popup_window, null);
                result1 = (TextView) view.findViewById(R.id.result);
                while(true) {
                    while (!isWin) {
                        if (progress == progressBar.getMax()) {
                            isWin = true;
                            Win = false;
                            startActivity(intent);
                            break;
                        } else {
                            if (clicks == 5) {
                                isWin = true;
                                Win = true;
                                startActivity(intent);
                                break;
                            }
                        }
                    }
                }
            }
        }).start();
    }
    protected void spawnCoin(){
        Random r = new Random();
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int X = r.nextInt(displayMetrics.widthPixels);
        int Y = r.nextInt(displayMetrics.heightPixels);
                if(X>100){
                    coinView.setX(X-80);
                    coinView.setY(Y);
                }
                if(Y>100){
                    coinView.setX(X);
                    coinView.setY(Y-80);
                }
        if (X<100 && Y<100){
            coinView.setX(X);
            coinView.setY(Y);
        }
        Log.d(TAG, "Current cointView X " + coinView.getX());
        Log.d(TAG, "Current cointView Y " + coinView.getY());
    }

    @Override
    protected void onStart() {
        mClicks.edit().clear().apply();
        super.onStart();
        backgroundMusic = MediaPlayer.create(this,R.raw.background);
        backgroundMusic.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        backgroundMusic.stop();

        SharedPreferences.Editor editor = mClicks.edit();
        editor.putInt(APP_PREFERENCES_CLICKS,clicks);
        editor.apply();
    }

    @Override
    protected void onResume() {
        super.onResume();

        backgroundMusic.start();
        final LinearLayout mainLayout = (LinearLayout) findViewById(R.id.linearLayout);
            if (mClicks.contains(APP_PREFERENCES_CLICKS)) {
                score.setText(Integer.toString(mClicks.getInt(APP_PREFERENCES_CLICKS, clicks)));
                clicks = mClicks.getInt(APP_PREFERENCES_CLICKS, 0);
            }
        i++;
    }

}

