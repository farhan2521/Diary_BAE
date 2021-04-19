package com.example.diary_bae.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.diary_bae.database.DetailsContract.DetailsEntry;
import com.example.diary_bae.pojo.EntryDetails;

import java.util.ArrayList;

public class DbAccessObject {
    SQLiteDatabase entriesdb;
    DbHelper dbHelper;

    public DbAccessObject(Context context) {
        dbHelper = new DbHelper(context);
    }

    public void openDb() {
        entriesdb = dbHelper.getWritableDatabase();
    }

    public void closeDb(){
        entriesdb.close();
    }

    public void createRow(String title, String date, String time) {
        ContentValues values = new ContentValues();
        values.put(DetailsEntry.COLUMN_NAME_TITLE,title);
        values.put(DetailsEntry.COLUMN_NAME_DATE,date);
        values.put(DetailsEntry.COLUMN_NAME_TIME,time);
        entriesdb.insert(DetailsEntry.TABLE_NAME,null,values);
    }

    public String readRow() {
        Cursor cursor = entriesdb.query(DetailsEntry.TABLE_NAME,null,null,null,null,null,null);
        cursor.moveToFirst();
        int titleIndex = cursor.getColumnIndexOrThrow(DetailsEntry.COLUMN_NAME_TITLE); //index = 1
        int dateIndex = cursor.getColumnIndexOrThrow(DetailsEntry.COLUMN_NAME_DATE); //index = 2
        int timeIndex = cursor.getColumnIndexOrThrow(DetailsEntry.COLUMN_NAME_TIME); //index = 3
        return cursor.getString(titleIndex) + "\n"+cursor.getString(dateIndex) + "\n" +cursor.getString(timeIndex);
    }

    public void updateRow(String title, String date, String time, int id){
        ContentValues values = new ContentValues();
        if(!title.isEmpty())
            values.put(DetailsEntry.COLUMN_NAME_TITLE,title);
        if(!date.isEmpty())
            values.put(DetailsEntry.COLUMN_NAME_DATE,date);
        if(!time.isEmpty())
            values.put(DetailsEntry.COLUMN_NAME_TIME,time);
        entriesdb.update(DetailsEntry.TABLE_NAME,values,DetailsEntry._ID + " = ?",new String[]{String.valueOf(id)});
    }

    public void deleteRow(int id){
        entriesdb.delete(DetailsEntry.TABLE_NAME,DetailsEntry._ID + " = ?",new String[]{String.valueOf(id)});
    }

    public Cursor searchRow(String keyword){
        String sql = "select * from " + DetailsEntry.TABLE_NAME + " where " + DetailsEntry.COLUMN_NAME_TITLE + " = " + DetailsEntry.COLUMN_NAME_TITLE + " AND " + DetailsEntry.COLUMN_NAME_TITLE + " LIKE '" + keyword + "%'";
        Cursor cursor = entriesdb.rawQuery(sql, null);
        return cursor;
    }

    public Cursor getAllRows(){
        Cursor cursor = entriesdb.query(DetailsEntry.TABLE_NAME,null,null,null,null,null,null);
        return  cursor;
    }

    public ArrayList<EntryDetails> getEntryDetails(){
        String sql = "select * from " + DetailsEntry.TABLE_NAME;
        ArrayList<EntryDetails> entryDetails = new ArrayList<>();
        Cursor cursor = entriesdb.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                int id = Integer.parseInt(cursor.getString(0));
                String title = cursor.getString(1);
                String date = cursor.getString(2);
                String time = cursor.getString(3);
                entryDetails.add(new EntryDetails(id,title,date,time));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return entryDetails;
    }
}