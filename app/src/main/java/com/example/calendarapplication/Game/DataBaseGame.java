package com.example.calendarapplication.Game;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseGame extends  SQLiteOpenHelper{
    private static final int DATABASE_VERSION = 3;

    private  static final String DATABASE_NAME = "GAME.db";
    private  static final String TABLE_NAME = "charadb";
    private  static final String _ID = "_id";
    private  static final String CHARANAME = "charaName";
    private  static final String LEVEL = "level";
    private  static final String EVOLUTION = "evolution";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    _ID + " INTEGER PRIMARY KEY," +
                    CHARANAME + " TEXT," +
                    EVOLUTION + " INTEGER," +
                    LEVEL + " INTEGER)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    DataBaseGame(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                SQL_CREATE_ENTRIES
        );
        saveData(db,"mitarashi",2,2);
    }


    public void saveData(SQLiteDatabase db, String charaName,int evolution, int level) {
        ContentValues values = new ContentValues();
        values.put("charaName",charaName);
        values.put("evolution",evolution);
        values.put("level",level);

        db.insert("charadb",null,values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
        db.execSQL(
                SQL_DELETE_ENTRIES
        );
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

}
