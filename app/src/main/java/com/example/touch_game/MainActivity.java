package com.example.touch_game;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    static final int GET_RESULT = 1;
    int score_temp = 0;
    private ImageView soundImage;
    private ImageView mainImage;
    private int imageCount = 0;
    boolean flag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        soundImage = (ImageView) findViewById(R.id.soundImage);
        mainImage = (ImageView) findViewById(R.id.main_img);
        Button gameStart_btn = (Button) findViewById(R.id.btn_gameStart);
        Button record_btn = (Button) findViewById(R.id.btn_gameScore);

        startService(new Intent(getApplicationContext(), MusicService.class));

        gameStart_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(MainActivity.this, gameViewActivity.class);
                in.addFlags(Intent.FLAG_ACTIVITY_NO_USER_ACTION);
                startActivityForResult(in, GET_RESULT);
            }
        });

        record_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(MainActivity.this, recordActivity.class);
                in.addFlags(Intent.FLAG_ACTIVITY_NO_USER_ACTION);
                startActivity(in);
            }
        });

        mainImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(imageCount == 0) {
                    mainImage.setImageResource(R.drawable.main_img2);
                    imageCount++;
                } else if(imageCount == 1) {
                    mainImage.setImageResource(R.drawable.main_img3);
                    imageCount++;
                } else if(imageCount == 2) {
                    mainImage.setImageResource(R.drawable.main_img4);
                    imageCount++;
                } else if (imageCount == 3) {
                    mainImage.setImageResource(R.drawable.main_img1);
                    imageCount = 0;
                }
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GET_RESULT) {
            if (resultCode == RESULT_OK) {
                score_temp = data.getIntExtra("SCORE",0);
                Intent in = new Intent(MainActivity.this, gameOverActivity.class);
                in.addFlags(Intent.FLAG_ACTIVITY_NO_USER_ACTION);
                in.putExtra("SCORE",score_temp);
                startActivity(in);
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        stopService(new Intent(getApplicationContext(), MusicService.class));
        super.onDestroy();
    }

    @Override
    protected void onUserLeaveHint() {
        super.onUserLeaveHint();

        stopService(new Intent(getApplicationContext(), MusicService.class));
    }

    public void bgm_OnOff(View v) {
        if(flag) {
            stopService(new Intent(this, MusicService.class));
            soundImage.setImageResource(R.drawable.audio_icon_x);
            flag = false;
        } else {
            startService(new Intent(this, MusicService.class));
            soundImage.setImageResource(R.drawable.audio_icon);
            flag = true;
        }
    }
    public void Exit_game(View v) {
        ActivityCompat.finishAffinity(this);
        System.exit(0);
    }
}