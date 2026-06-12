package com.example.os_project;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "SurviveTheSemester.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_QUEST = "quests";
    public static final String KEY_QUEST_ID = "id";
    public static final String KEY_QUEST_TITLE = "title";
    public static final String KEY_QUEST_DUE = "due_date";
    public static final String KEY_QUEST_DIFF = "difficulty";
    public static final String KEY_QUEST_STATUS = "status";

    public static final String TABLE_PLAYER = "player";
    public static final String KEY_PLAYER_LEVEL = "level";
    public static final String KEY_PLAYER_HP = "hp";
    public static final String KEY_PLAYER_EXP = "exp";
    public static final String KEY_PLAYER_GOLD = "gold";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_QUEST_TABLE = "CREATE TABLE " + TABLE_QUEST + "("
                + KEY_QUEST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_QUEST_TITLE + " TEXT,"
                + KEY_QUEST_DUE + " INTEGER,"
                + KEY_QUEST_DIFF + " INTEGER,"
                + KEY_QUEST_STATUS + " TEXT" + ")";
        db.execSQL(CREATE_QUEST_TABLE);

        String CREATE_PLAYER_TABLE = "CREATE TABLE " + TABLE_PLAYER + "("
                + KEY_PLAYER_LEVEL + " INTEGER,"
                + KEY_PLAYER_HP + " INTEGER,"
                + KEY_PLAYER_EXP + " INTEGER,"
                + KEY_PLAYER_GOLD + " INTEGER" + ")";
        db.execSQL(CREATE_PLAYER_TABLE);

        ContentValues values = new ContentValues();
        values.put(KEY_PLAYER_LEVEL, 1);
        values.put(KEY_PLAYER_HP, 100);
        values.put(KEY_PLAYER_EXP, 0);
        values.put(KEY_PLAYER_GOLD, 0);
        db.insert(TABLE_PLAYER, null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUEST);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLAYER);
        onCreate(db);
    }
}