package com.example.touch_game;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class ScoreDBHelper  extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "tourchgame";
    public static final int DATABASE_VERSION = 1;

    public ScoreDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(ScoreContract.ScoreEntry.SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL(ScoreContract.ScoreEntry.SQL_DELETE_TABLE);
        onCreate(sqLiteDatabase);
    }

    public void onDeleteAll() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(ScoreContract.ScoreEntry.SQL_DELETE_ALL);
    }

    void insertRecord(int score, String date, int day) {
        SQLiteDatabase db = getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(ScoreContract.ScoreEntry.COLUMN_SCORE, score);
        values.put(ScoreContract.ScoreEntry.COLUMN_DATE, date);
        values.put(ScoreContract.ScoreEntry.COLUMN_DAY, day);

        db.insert(ScoreContract.ScoreEntry.TABLE_NAME, null, values);
    }

    public Cursor readRecordOrderByScore() {
        SQLiteDatabase db = getReadableDatabase();
        String[] projection = {
                BaseColumns._ID,
                ScoreContract.ScoreEntry.COLUMN_SCORE,
                ScoreContract.ScoreEntry.COLUMN_DATE,
                ScoreContract.ScoreEntry.COLUMN_DAY
        };

        String sortOrder = ScoreContract.ScoreEntry.COLUMN_SCORE + " DESC";

        Cursor cursor = db.query(
                ScoreContract.ScoreEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                sortOrder
        );

        return cursor;
    }

    public Cursor readRecordOrderByDay() {
        SQLiteDatabase db = getReadableDatabase();
        String[] projection = {
                BaseColumns._ID,
                ScoreContract.ScoreEntry.COLUMN_SCORE,
                ScoreContract.ScoreEntry.COLUMN_DATE,
                ScoreContract.ScoreEntry.COLUMN_DAY
        };

        String sortOrder = ScoreContract.ScoreEntry.COLUMN_DAY + " ASC";

        Cursor cursor = db.query(
                ScoreContract.ScoreEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                sortOrder
        );

        return cursor;
    }
}
