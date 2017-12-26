package com.example.iskander2.clicker;

import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ClickerActivity extends AppCompatActivity {


    public static final String APP_PREFERENCES = "Settings";
    public static final String APP_PREFERENCES_CLICKS = "Clicks";
    public final String TAG = "MyTest";

    int clicks =0;

    SharedPreferences mClicks;
    TextView textView;
    ImageView imageView;

    volatile boolean isWin;
    volatile boolean pressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clicker);
        textView = (TextView) findViewById(R.id.score);
        imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setX((float) Math.random() * 1000);
        imageView.setY((float) Math.random() * 1000);
        Log.d(TAG, "Vse ok");
        pressed = false;
        isWin = false;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            clicks = Integer.parseInt(textView.getText().toString());
                            textView.setText(Integer.toString(++clicks));
                            imageView.setVisibility(View.INVISIBLE);
                            imageView.setX((float) Math.random() * 100);
                            imageView.setY((float) Math.random() * 100);
                            Toast.makeText(ClickerActivity.this, "coin is invisible", Toast.LENGTH_LONG).show();
                            try{
                            Thread.sleep(2000);
                            imageView.setVisibility(View.VISIBLE);
                                }
                            catch (Exception e){}
                            if (clicks == 20) {
                                isWin = true;
                            }
                        }
                    });
            }
            }
        }).start();
       /* imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clicks = Integer.parseInt(textView.getText().toString());
                textView.setText(Integer.toString(++clicks));
                pressed = true;
                imageView.setVisibility(View.INVISIBLE);
                if(clicks==20){
                    isWin = true;
                }
            }
        }); */
       /*if (pressed) {
                        try {
                            Thread.sleep(2000);
                            imageView.setVisibility(View.VISIBLE);
                            imageView.setX((float) Math.random() * 1000);
                            imageView.setY((float) Math.random() * 1000);
                            pressed = false;
                        } catch (Exception e) {
                        }
                    }*/
        MediaPlayer backgroundMusic = MediaPlayer.create(this,R.raw.background);
        backgroundMusic.start();
    }

/*    @Override
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
    } */

}
