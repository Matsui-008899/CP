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
    private  static final String CNAME = "name";

    private  static final String TABLE_NAME2 = "evolvedb";
    private  static final String _ID2 = "_id";
    private  static final String NAME = "evolveName";
    private  static final String ORIGIN = "originName";

    private  static final String TABLE_NAME3 = "achievedb";
    private  static final String _ID3 = "_id";
    private  static final String ACHIEVENAME = "achieveName";
    private  static final String ACHIEVECONTENT = "achieveContent";
    private  static final String ACHIEVEDATE = "achieveDate";
    private  static final String CHECKING = "comple";



    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    _ID + " INTEGER PRIMARY KEY," +
                    CHARANAME + " TEXT," +
                    EVOLUTION + " INTEGER," +
                    LEVEL + " INTEGER,"+
                    CNAME + " TEXT)";

    private static final String SQL_CREATE_ENTRIES_EVOLVE =
            "CREATE TABLE " + TABLE_NAME2 + " (" +
                    _ID2 + " INTEGER PRIMARY KEY," +
                    NAME + " TEXT," +
                    EVOLUTION + " INTEGER," +
                    ORIGIN + " TEXT)";

    private static  final String SQL_CREATE_ENTRIES_ACHIEVE =
            "CREATE TABLE " + TABLE_NAME3 + " (" +
                    _ID3 + " INTEGER PRIMARY KEY," +
                    ACHIEVENAME + " TEXT," +
                    ACHIEVECONTENT + " TEXT," +
                    CHECKING + " TEXT," +
                    ACHIEVEDATE + " TEXT)";


    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    private static final String SQL_DELETE_ENTRIES_EVOLVE =
            "DROP TABLE IF EXISTS " + TABLE_NAME2;

    private static final String SQL_DELETE_ENTRIES_ACHIEVE =
            "DROP TABLE IF EXISTS " + TABLE_NAME3;


    DataBaseGame(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(
                SQL_CREATE_ENTRIES
        );

        db.execSQL(
                SQL_CREATE_ENTRIES_EVOLVE
        );

        db.execSQL(
                SQL_CREATE_ENTRIES_ACHIEVE
        );

        reCreate(db);
        reCreate2(db);
        reCreate3(db);
    }


    public void reCreate(SQLiteDatabase db) {
        saveData(db,"chara1",0,0,"みたらし");
        saveData(db,"chara2",0,0,"ねこ");
        saveData(db,"chara3",0,0,"うーぱー");
    }

    public void reCreate2(SQLiteDatabase db) {
        saveData(db,"mitarashi",0,"chara1");
        saveData(db,"donatsu",1,"chara1");
        saveData(db,"donatsu",2,"chara1");
        saveData(db,"neko",0,"chara2");
        saveData(db,"neko2",1,"chara2");
        saveData(db,"neko3",2,"chara2");
        saveData(db,"u_pa_",0,"chara3");
        saveData(db,"u_pa_2",1,"chara3");
        saveData(db,"u_pa_",2,"chara3");
    }

    public void reCreate3(SQLiteDatabase db) {
        saveData(db,"丸い生き物","１体目のキャラを獲得","",0);
        saveData(db,"ネコ","２体目のキャラを獲得","",0);
        saveData(db,"ウーパー","３体目のキャラを獲得","",0);
        saveData(db,"始まりの一歩","予定を１件達成","",0);
        saveData(db,"一休み","予定を５件達成","",0);
        saveData(db,"慣れたかな？","予定を１５件達成","",0);
        saveData(db,"順調順調～","予定を２０件達成","",0);
        saveData(db,"時には休息も","予定を３０件達成","",0);
        saveData(db,"くる","１体のキャラクターがレベル５に到達","",0);
        saveData(db,"くるくる","１体のキャラクターがレベル１０に到達","",0);
        saveData(db,"くるくるくる","１体のキャラクターがレベル３０に到達","",0);
        saveData(db,"くるるん","２体のキャラクターがレベル５に到達","",0);
        saveData(db,"くるるるるる","２体のキャラクターがレベル１０に到達","",0);
        saveData(db,"くるくるくる","２体のキャラクターがレベル３０に到達","",0);
        saveData(db,"くるぅーーーん！","３体のキャラクターがレベル５に到達","",0);
        saveData(db,"ぐるぐるぐる","３体のキャラクターがレベル１０に到達","",0);
        saveData(db,"ぐるるるるる","３体のキャラクターがレベル３０に到達","",0);



    }

    private void saveData(SQLiteDatabase db, String achiName, String achiContent, String date, int i) {
        ContentValues values = new ContentValues();
        values.put("achieveName",achiName);
        values.put("achieveContent",achiContent);
        values.put("achieveDate",date);
        values.put("comple",i);

        db.insert("achievedb",null,values);
    }

    public void saveData(SQLiteDatabase db, String name,int evolution, String origin) {
        ContentValues values = new ContentValues();
        values.put("evolveName",name);
        values.put("evolution",evolution);
        values.put("originName",origin);

        db.insert("evolvedb",null,values);
    }



    public void saveData(SQLiteDatabase db, String charaName,int evolution, int level,String name) {
        ContentValues values = new ContentValues();
        values.put("charaName",charaName);
        values.put("evolution",evolution);
        values.put("level",level);
        values.put("name",name);

        db.insert("charadb",null,values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
        db.execSQL(
                SQL_DELETE_ENTRIES
        );
        db.execSQL(
                SQL_DELETE_ENTRIES_EVOLVE
        );
        db.execSQL(
                SQL_DELETE_ENTRIES_ACHIEVE
        );
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

}
