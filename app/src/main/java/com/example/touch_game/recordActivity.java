package com.example.touch_game;

import android.content.Context;
import android.content.ContextParams;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class recordActivity extends AppCompatActivity {
    private ScoreDBHelper dbHelper;
    private TextView recordView, resultView;
    private String recordList, resultList;
    private boolean db_chk = false;
    private int db_count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.record);

        Button reset_btn = (Button) findViewById(R.id.record_reset);
        recordView = (TextView) findViewById(R.id.recordView);
        resultView = (TextView) findViewById(R.id.resultView);

        dbHelper = new ScoreDBHelper(this);
        recordList = printTable();
        recordView.setText(recordList);
        resultList = printDay();
        resultView.setText(resultList);

        reset_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHelper.onDeleteAll();
                Toast.makeText(getApplicationContext(), "기록이 전부 삭제되었습니다.", Toast.LENGTH_LONG).show();
                recordList = printTable();
                recordView.setText(recordList);
                recordList = printTable();
                recordView.setText(recordList);
            }
        });
    }

    private String printTable() {
        Cursor cursor = dbHelper.readRecordOrderByScore();
        String result = "";

        result += " 번호    점수                      시간\n";
        while (cursor.moveToNext()) {
            db_count++;
            if(db_count > 10)
                break;
            db_chk = true;
            int itemId = cursor.getInt(cursor.getColumnIndexOrThrow(ScoreContract.ScoreEntry._ID));
            int Score = cursor.getInt(cursor.getColumnIndexOrThrow(ScoreContract.ScoreEntry.COLUMN_SCORE));
            String Date = cursor.getString(cursor.getColumnIndexOrThrow(ScoreContract.ScoreEntry.COLUMN_DATE));

            result += String.format("%5s", itemId);
            result += String.format("%10s", Score);
            if(itemId < 10) {
                if(Score < 10)
                    result += " ";
            } else if (Score < 10) {
                result = result.substring(0, result.length() - 2);
                result += Score + " ";
            } else if (Score >= 100) {
                result = result.substring(0, result.length() - 4);
                result += Score;
            } else {
                result = result.substring(0, result.length() - 3);
                result += Score;
            }

            if (Score > 100 && itemId < 10)
                result += String.format("%29s", Date);
            else if(Score > 100 && itemId >= 10)
                result += String.format("%29s", Date);
            else
                result += String.format("%30s", Date);
            result += "\n";
        }

        if(db_chk == false)
            result = "기록이 없습니다.";

        db_count = 0;
        cursor.close();
        return result;
    }

    private String printDay() {
        Cursor cursor = dbHelper.readRecordOrderByDay();
        int before_Day = 0;
        int S_count = 0;
        int cursor_Last = cursor.getCount(), cursor_count = 0;
        double avg;
        String result = "";

        while (cursor.moveToNext()) {
            cursor_count++;
            int Score = cursor.getInt(cursor.getColumnIndexOrThrow(ScoreContract.ScoreEntry.COLUMN_SCORE));
            int Day = cursor.getInt(cursor.getColumnIndexOrThrow(ScoreContract.ScoreEntry.COLUMN_DAY));
            if (before_Day == 0) {
                before_Day = Day;
                S_count = Score;
                db_count++;
            }
            else if (before_Day == Day) {
                db_count++;
                S_count += Score;
            } else {
                avg = (double) S_count / db_count;
                db_count = 1;
                result += (int)before_Day/10000 + "년 " + (int)Math.floorMod(before_Day,10000)/100 + "월 " + (int)(before_Day*10%1000/10) + "일 평균 : " + avg + "점\n";
                before_Day = Day;
                S_count = Score;
            }
            if (cursor_Last == cursor_count) {
                avg = (double) S_count / db_count;
                result += (int) Day / 10000 + "년 " + (int) Math.floorMod(Day, 10000) / 100 + "월 " + (int) Day % 100 + "일 평균 : " + avg + "점";
            }
        }

        cursor.close();
        return result;
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