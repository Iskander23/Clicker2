package com.example.iskander2.clicker;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Map;
import java.util.Set;


public class ClickerActivity extends AppCompatActivity {


    int clicks =0;
    int i=1;
    static final String APP_PREFERENCES_CLICKS = "Clicks";
    static final String APP_PREFERENCES = "Settings";
    private SharedPreferences mClicks ;

    public final String TAG = "MyTest";
    private ProgressBar progressBar;
    volatile int progress = 0;
    private TextView score;
    private volatile ImageView coinView;
    TextView result1;

    public boolean isWin = false;
    volatile boolean isPaused;
    volatile boolean isRestarted;
    PopupWindow popupWindow;
    MediaPlayer backgroundMusic;

    LinearLayout mainLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mClicks = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clicker);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        score = (TextView) findViewById(R.id.score);
        coinView = (ImageView) findViewById(R.id.imageView);

        final LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_window, null);
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true;
        popupWindow = new PopupWindow(popupView,width,height,focusable);


        //Spawning the Coin
        new Thread(new Runnable() {
            @Override
            public void run() {
                coinView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        clicks = Integer.parseInt(score.getText().toString());
                        score.setText(Integer.toString(++clicks));
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
                    }
                });
            }
        }).start();

        //Updating ProgressBar
        Thread progressBarThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!isWin) {
                    try{
                    Thread.sleep(1000);
                    progressBar.setProgress(progress++);}
                    catch (Exception e){}
                }
            }
        });
        progressBarThread.start();

        //Checking for Win or Lose
        new Thread(new Runnable() {
            @Override
            public void run() {
                final View view = inflater.inflate(R.layout.popup_window, null);
                result1 = (TextView) view.findViewById(R.id.result);
                mainLayout = (LinearLayout) findViewById(R.id.linearLayout);
                runOnUiThread(new Runnable() {
                    public void run() {
                        while (!isWin) {
                            if (progress == progressBar.getMax()) {
                                isWin = true;
                                String string = new String();
                                string = "Oh no! You Lost!";
                                result1.setText(string);
                                popupWindow.showAtLocation(mainLayout, Gravity.CENTER, 0, 0);
                                MediaPlayer loseSound = MediaPlayer.create(ClickerActivity.this, R.raw.losesound);
                                loseSound.start();
                            }
                            else {
                                if (clicks == 5) {
                                    isWin = true;
                                    Button button;
                                    button = view.findViewById(R.id.restart_button);
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            popupWindow.showAtLocation(mainLayout, Gravity.CENTER, 0, 0);
                                        }
                                    }, 100);
                                    MediaPlayer winSound = MediaPlayer.create(ClickerActivity.this, R.raw.winsound);
                                    winSound.start();
                                    button.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            popupWindow.dismiss();
                                            clicks = 0;
                                            progressBar.setProgress(0);
                                        }
                                    });
                                }
                            }
                        }
                    }
                });
            }
        }).start();

    }

    @Override
    protected void onStart() {
        super.onStart();
        backgroundMusic = MediaPlayer.create(this,R.raw.background);
        backgroundMusic.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        backgroundMusic.stop();
        popupWindow.dismiss();
        SharedPreferences.Editor editor = mClicks.edit();
        editor.putInt(APP_PREFERENCES_CLICKS,clicks);
        editor.apply();
    }

    @Override
    protected void onResume() {
        super.onResume();
        backgroundMusic.start();

        final LinearLayout mainLayout = (LinearLayout) findViewById(R.id.linearLayout);

        Log.d(TAG, "isPaused is "+ isPaused);
                if(i!=1) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            popupWindow.showAtLocation(mainLayout,Gravity.CENTER, 0, 0);
                            isPaused = false;
                        }
                    },100);
                }

        Log.d(TAG, "mClicks is"+ mClicks);
            if (mClicks.contains(APP_PREFERENCES_CLICKS)) {
                score.setText(Integer.toString(mClicks.getInt(APP_PREFERENCES_CLICKS, clicks)));
                clicks = mClicks.getInt(APP_PREFERENCES_CLICKS, 0);
            }
        i++;
    }

}

