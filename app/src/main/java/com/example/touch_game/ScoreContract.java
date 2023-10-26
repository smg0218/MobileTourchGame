package com.example.touch_game;

import android.provider.BaseColumns;

public final class ScoreContract {
    private ScoreContract() {
    }

    public static class ScoreEntry implements BaseColumns {
        public static final String TABLE_NAME = "tgScore";
        public static final String COLUMN_SCORE = "score";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_DAY = "day";
        public static final String SQL_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" + _ID + " INTEGER PRIMARY KEY," + COLUMN_SCORE + " INTEGER," + COLUMN_DATE + " TEXT," + COLUMN_DAY + " INTEGER)";
        public static final String SQL_DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
        public static final String SQL_DELETE_ALL = "DELETE FROM " + TABLE_NAME;
    }
}