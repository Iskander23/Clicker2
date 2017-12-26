package com.example.iskander2.clicker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
ImageButton button1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startNewActivity();
        TextView textView1 = (TextView) findViewById(R.id.text_view);
        TranslateAnimation animation = new TranslateAnimation(-1500.0f, 0.0f, 0.0f, 0.0f);
        animation.setDuration(800);
        animation.setFillAfter(true);
        textView1.startAnimation(animation);
    }

 /*   @Override
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
    }*/
    public void startNewActivity() {
        button1 = (ImageButton) findViewById(R.id.button_start);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent start = new Intent(MainActivity.this, ClickerActivity.class);
                startActivity(start);
            }
        });
    }
 /*  public void OnClick(View view){
        int clicks = Integer.parseInt(textView.getText().toString());
        textView.setText(Integer.toString(++clicks))
    } */
}
