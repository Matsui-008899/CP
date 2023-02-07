package com.example.calendarapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.Calendar;

/**
 * https://qiita.com/kengo_kuwahara/items/a8ef858a9810cad42ca6
 */
public class DataBase extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 3;

    private  static final String DATABASE_NAME = "TASK.db";
    private  static final String TABLE_NAME = "taskdb";
    private  static final String _ID = "_id";
    private  static final String STARTDAY = "startDay";
    private  static final String STARTTIME = "startTime";
    private  static final String ENDDAY = "endDay";
    private  static final String ENDTIME = "endTime";
    private  static final String TASK = "task";
    private  static final String LEVELCHECK = "level";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    _ID + " INTEGER PRIMARY KEY," +
                    STARTDAY + " TEXT," +
                    STARTTIME + " TEXT," +
                    ENDDAY + " TEXT," +
                    ENDTIME + " TEXT," +
                    TASK + " TEXT," +
                    LEVELCHECK + " TEXT)";

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

        debugCreate(db);
    }


    public void saveData(SQLiteDatabase db, String startD,String startT, String endD,String endT,String task,String level) {
        ContentValues values = new ContentValues();
        values.put("startDay",startD);
        values.put("startTime",startT);
        values.put("endDay",endD);
        values.put("endTime",endT);
        values.put("task",task);
        values.put("level",level);

        db.insert("taskdb",null,values);
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

    public void debugCreate(SQLiteDatabase db){
        Calendar c = Calendar.getInstance();
        String endY = String.format("%d年%02d月%02d日", c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1, c.get(Calendar.DAY_OF_MONTH)-1);

        saveData(db, DateCon(-1), "18時00分", DateCon(-1),"23時30分", "アルバイト","0");
        saveData(db, DateCon(-2), "18時00分", DateCon(-2),"22時00分", "アルバイト","0");
        saveData(db, DateCon(-3), "18時00分", DateCon(-3),"22時00分", "アルバイト","0");
        saveData(db, DateCon(-4), "18時00分", DateCon(-4),"23時00分", "アルバイト","0");
        saveData(db, DateCon(-14), "18時00分",DateCon(-14), "22時00分", "アルバイト","0");
        saveData(db, DateCon(-2), "10時00分", DateCon(-2),"16時00分", "コミケ","0");
        saveData(db, DateCon(-1), "14時00分", DateCon(-1),"22時00分", "デート","0");
        saveData(db, DateCon(-3), "11時00分", DateCon(-3),"18時00分", "デート","0");
        saveData(db, DateCon(-4), "11時00分", DateCon(-4),"17時00分", "デート","0");
        saveData(db, DateCon(-5), "18時00分", DateCon(-5),"22時00分", "デート","0");
        saveData(db, DateCon(-6), "18時00分", DateCon(-6),"23時30分", "アルバイト","0");
        saveData(db, DateCon(-7), "18時00分", DateCon(-7),"22時00分", "アルバイト","0");
        saveData(db, DateCon(-8), "18時00分", DateCon(-8),"22時00分", "アルバイト","0");
        saveData(db, DateCon(-9), "18時00分", DateCon(-9),"23時00分", "アルバイト","0");
        saveData(db, DateCon(-10), "18時00分",DateCon(-10), "22時00分", "アルバイト","0");
        saveData(db, DateCon(-11), "18時00分",DateCon(-11), "23時30分", "アルバイト","0");
        saveData(db, DateCon(-12), "18時00分",DateCon(-12), "22時00分", "アルバイト","0");
        saveData(db, DateCon(-13), "18時00分",DateCon(-13), "22時00分", "アルバイト","0");
    }

    private String DateCon(int i) {
        Calendar c = Calendar.getInstance();
        int a = c.get(Calendar.DAY_OF_MONTH)+i;
        String startY = String.format("%d年%02d月%02d日", c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1, c.get(Calendar.DAY_OF_MONTH)+i);
        Log.d("deb","加工前"+startY);
        if (a <= 0){
            c.set(c.get(Calendar.YEAR),c.get(Calendar.MONTH)-1,c.get(Calendar.DAY_OF_MONTH));
            int b = c.getActualMaximum(Calendar.DAY_OF_MONTH);
            Log.d("deb","加工後b"+b);
            Log.d("deb","加工後a"+a);
            i = b + a;
            Log.d("deb","加工後i"+i);
            startY = String.format("%d年%02d月%02d日", c.get(Calendar.YEAR), c.get(Calendar.MONTH)+1, i);
        }

        Log.d("deb","加工後"+startY);
        return startY;
    }


}
