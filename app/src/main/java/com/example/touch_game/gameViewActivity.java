package com.example.touch_game;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.util.Property;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.util.Random;

public class gameViewActivity extends AppCompatActivity {
    View myView;
    LinearLayout layoutTop;
    TextView time;
    public float x, y, radius;
    private CountDownTimer countDownTimer;
    private TextView score;
    private long time_set = 63000;
    float x_l, x_r, y_t, y_b;
    int score_count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_view);

        score = (TextView) findViewById(R.id.Score_text);
        time = (TextView) findViewById(R.id.Time_text);
        myView = (View) findViewById(R.id.myView);
        layoutTop = (LinearLayout) findViewById(R.id.linearLayout);
        Toast.makeText(getApplicationContext(), "3초 뒤 게임이 시작됩니다.", Toast.LENGTH_LONG).show();
        startTimer();
    }

    public boolean onTouchEvent(MotionEvent event) {
        float curX = event.getX();
        float curY = event.getY() - layoutTop.getHeight();
        x = DrawCircle.x; y = DrawCircle.y; radius = DrawCircle.radius;
        x_l = x-radius; x_r = x+radius;
        y_t = y-radius; y_b = y+radius;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if(curX > x_l && curX < x_r) {
                    if(curY > y_t && curY < y_b) {
                        reDraw(myView);
                        score_count++;
                        score.setText("SCORE : " + score_count);
                    }
                }
                break;
            default:
                break;
        }
        return true;
    }

    public void reDraw(View view) {
        myView.invalidate();
    }

    private void startTimer() {

        countDownTimer = new CountDownTimer(time_set, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if(millisUntilFinished<=60000) {
                    myView.setVisibility(View.VISIBLE);
                    time.setText("Time : " + millisUntilFinished / 1000);
                }
            }

            @Override
            public void onFinish() {
                //textView.setText("Time : 0");
                Intent intent = new Intent();
                intent.putExtra("SCORE", score_count);
                setResult(RESULT_OK, intent);
                finish();
            }
        }.start();
    }

    public void end_Game(View v) {
        setResult(RESULT_CANCELED);
        finish();
    }
}