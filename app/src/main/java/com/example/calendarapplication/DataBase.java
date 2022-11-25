package com.example.calendarapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * https://qiita.com/kengo_kuwahara/items/a8ef858a9810cad42ca6
 */
public class DataBase extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 3;

    private  static final String DATABASE_NAME = "MMDB.db";
    private  static final String TABLE_NAME = "tastdb";
    private  static final String _ID = "_id";
    private  static final String STARTDAY = "startday";
    private  static final String STARTTIME = "starttime";
    private  static final String ENDDAY = "endday";
    private  static final String ENDTIME = "endtime";
    private  static final String TASK = "task";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    _ID + " INTEGER PRIMARY KEY," +
                    STARTDAY + " TEXT," +
                    STARTTIME + " TEXT," +
                    ENDDAY + " TEXT," +
                    ENDTIME + " TEXT," +
                    TASK + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;


    DataBase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                SQL_CREATE_ENTRIES
        );

    }


    public void saveData(SQLiteDatabase db, String startD,String startT, String endD,String endT,String task) {
        ContentValues values = new ContentValues();
        values.put("startday",startD);
        values.put("starttime",startT);
        values.put("endday",endD);
        values.put("endtime",endT);
        values.put("task",task);

        db.insert("tastdb",null,values);
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
