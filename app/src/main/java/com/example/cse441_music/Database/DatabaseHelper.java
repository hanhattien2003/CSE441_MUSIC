package com.example.cse441_music.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "MusicDB";
    private static final String TABLE_FAVORITES = "Favorites";
    private static final String COLUMN_ID = "id";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_FAVORITES + "("
                + COLUMN_ID + " TEXT PRIMARY KEY)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVORITES);
        onCreate(db);
    }

    public void addFavorite(String songId) {
        // Check if the song is already a favorite
        if (!isFavorite(songId)) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(COLUMN_ID, songId);
            db.insert(TABLE_FAVORITES, null, values);
            db.close();
        }
    }

    public void removeFavorite(String songId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_FAVORITES, COLUMN_ID + " = ?", new String[]{songId});
        db.close();
    }

    public boolean isFavorite(String songId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_FAVORITES, new String[]{COLUMN_ID}, COLUMN_ID + " = ?",
                new String[]{songId}, null, null, null);
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        db.close();
        return exists;
    }

    public List<String> getFavorites() {
        List<String> favoriteIds = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_FAVORITES, new String[]{COLUMN_ID}, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                favoriteIds.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return favoriteIds;
    }
}
