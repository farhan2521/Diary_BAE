package com.example.diary_bae.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.diary_bae.database.DetailsContract.DetailsEntry;

public class DbHelper extends SQLiteOpenHelper {
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + DetailsEntry.TABLE_NAME + " (" +
                    DetailsEntry._ID + " INTEGER PRIMARY KEY," +
                    DetailsEntry.COLUMN_NAME_TITLE + " TEXT," +
                    DetailsEntry.COLUMN_NAME_DATE + " DATE," +
                    DetailsEntry.COLUMN_NAME_TIME +" TEXT)";

    public DbHelper(@Nullable Context context) {
        super(context, DetailsContract.DetailsEntry.DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
