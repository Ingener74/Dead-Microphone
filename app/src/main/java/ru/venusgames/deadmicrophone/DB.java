package ru.venusgames.deadmicrophone;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DB extends SQLiteOpenHelper {

    public static final String TABLE_MESSAGES = "messages";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_MESSAGE = "message";
    public static final String CREATE = "create table " + TABLE_MESSAGES + " ( " +
            COLUMN_ID + " integer primary key autoincrement," +
            COLUMN_NAME + " text," +
            COLUMN_MESSAGE + " text" +
            ");";

    public DB(Context context, int version) {
        super(context, "DeadMic.db", null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(getClass().getName(), "on create database");
        db.execSQL(CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(getClass().getName(), "on upgrade database");
        db.execSQL("drop table if exists " + TABLE_MESSAGES);
        onCreate(db);
    }

    public void addContact(Contact contact) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, contact.name);
        values.put(COLUMN_MESSAGE, contact.message);
        database.insert(TABLE_MESSAGES, null, values);
    }

    public Cursor getAllData() {
        SQLiteDatabase database = getReadableDatabase();
        return database.query(TABLE_MESSAGES, null, null, null, null, null, null);
    }

    public void clearMessages(){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL("delete from " + TABLE_MESSAGES);
    }
}
