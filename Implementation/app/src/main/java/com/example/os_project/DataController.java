package com.example.os_project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DataController {
    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;

    public DataController(Context context) {
        dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public boolean insertQuest(Quest quest) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.KEY_QUEST_TITLE, quest.getTitle());
        values.put(DatabaseHelper.KEY_QUEST_DUE, quest.getDueDate().getTime());
        values.put(DatabaseHelper.KEY_QUEST_DIFF, quest.getDifficulty());
        values.put(DatabaseHelper.KEY_QUEST_STATUS, quest.getStatus());

        long result = db.insert(DatabaseHelper.TABLE_QUEST, null, values);
        return result != -1;
    }

    public boolean updateQuestStatus(int questId, String status) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.KEY_QUEST_STATUS, status);

        int rows = db.update(DatabaseHelper.TABLE_QUEST, values,
                DatabaseHelper.KEY_QUEST_ID + " = ?", new String[]{String.valueOf(questId)});
        return rows > 0;
    }

    public List<Quest> fetchPendingQuests() {
        List<Quest> questList = new ArrayList<>();

        Cursor cursor = db.query(DatabaseHelper.TABLE_QUEST, null,
                DatabaseHelper.KEY_QUEST_STATUS + " = ?", new String[]{"PENDING"},
                null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.KEY_QUEST_ID));
                String title = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.KEY_QUEST_TITLE));
                long dueMillis = cursor.getLong(cursor.getColumnIndexOrThrow(DatabaseHelper.KEY_QUEST_DUE));
                int difficulty = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.KEY_QUEST_DIFF));
                String status = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.KEY_QUEST_STATUS));

                Quest quest = new Quest(id, title, new Date(dueMillis), difficulty, status);
                questList.add(quest);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return questList;
    }

    public boolean updatePlayerStat(int hp, int exp, int gold) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.KEY_PLAYER_HP, hp);
        values.put(DatabaseHelper.KEY_PLAYER_EXP, exp);
        values.put(DatabaseHelper.KEY_PLAYER_GOLD, gold);

        int rows = db.update(DatabaseHelper.TABLE_PLAYER, values, null, null);
        return rows > 0;
    }

    public Player getPlayer() {
        Cursor cursor = db.query(DatabaseHelper.TABLE_PLAYER, null, null, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            int level = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.KEY_PLAYER_LEVEL));
            int hp = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.KEY_PLAYER_HP));
            int exp = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.KEY_PLAYER_EXP));
            int gold = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.KEY_PLAYER_GOLD));

            Player player = new Player(level, hp, exp, gold);
            cursor.close();
            return player;
        }
        return null;
    }
}