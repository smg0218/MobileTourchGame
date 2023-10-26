package com.example.touch_game;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class gameOverActivity extends AppCompatActivity {
    private ScoreDBHelper dbHelper;
    //private ScoreRequest request;
    private TextView score;
    private String nowTime;
    private int day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_over);

        score = (TextView) findViewById(R.id.final_score);
        dbHelper = new ScoreDBHelper(this);
        Intent intent = getIntent();
        int final_score = intent.getIntExtra("SCORE",0);
        score.setText("당신의 점수 : " + final_score);

        if(final_score != 0) {
            nowTime = getTime();
            dbHelper.insertRecord(final_score, nowTime, day);
        } else
            Toast.makeText(getApplicationContext(), "0점은 기록되지 않습니다.", Toast.LENGTH_LONG).show();
    }

    private String getTime() {
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String getTime = dateFormat.format(date);

        dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        String S_day = dateFormat.format(date);
        S_day = S_day.replaceAll("/", "");
        day = Integer.parseInt(S_day);

        return getTime;
    }

    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }

    public void end_Game(View v) {
        setResult(RESULT_CANCELED);
        finish();
    }

}