package com.example.iskander2.clicker;

import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


public class ClickerActivity extends AppCompatActivity {


    public static final String APP_PREFERENCES = "Settings";
    public static final String APP_PREFERENCES_CLICKS = "Clicks";
    public final String TAG = "MyTest";

    int clicks =0;
    public ProgressBar progressBar;
    public SharedPreferences mClicks;
    public TextView textView;
    public volatile ImageView coinView;

    public volatile boolean isWin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clicker);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        textView = (TextView) findViewById(R.id.score);
        coinView = (ImageView) findViewById(R.id.imageView);
        
        coinView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                        clicks = Integer.parseInt(textView.getText().toString());
                        textView.setText(Integer.toString(++clicks));
                        coinView.setVisibility(View.INVISIBLE);
                        coinView.setX((float) Math.random() * 1000);
                        coinView.setY((float) Math.random() * 1000);
                        Log.d(TAG, "Current cointView X " + coinView.getX());
                        Log.d(TAG, "Current cointView Y " + coinView.getY());
                        new Handler().postDelayed(new Runnable() {
                         @Override
                         public void run() {
                            coinView.setVisibility(View.VISIBLE);
                         }
                               }, 1000);
                if (clicks == 20) {
                    isWin = true;
                }
                    }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                int progress = 0;
                while (!isWin) {
                    try{
                    Thread.sleep(1000);
                    progressBar.setProgress(progress++);}
                    catch (Exception e){}
                }
            }
        }).start();

        MediaPlayer backgroundMusic = MediaPlayer.create(this,R.raw.background);
        backgroundMusic.start();
    }

  /*  @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor editor = mClicks.edit();
        editor.putInt(APP_PREFERENCES_CLICKS,clicks);
        editor.apply();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mClicks.contains(APP_PREFERENCES_CLICKS)) {
            textView.setText(Integer.toString(mClicks.getInt(APP_PREFERENCES_CLICKS, 0)));
            clicks = mClicks.getInt(APP_PREFERENCES_CLICKS, 0);
        }
    }
            */
}

