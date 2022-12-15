package com.example.calendarapplication.Game;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.calendarapplication.MyApplication;

import java.util.Random;

public class GameActivity {
    private DataBaseGame dbGame;
    //    private DataBaseEvolve dbEvolve;
    public int charaMany;


    public void createDataBase() {
        Log.d("debug", "くぎゅう");

        dbGame = new DataBaseGame(MyApplication.getAppContext());
        Log.d("debug", "通貨判定");

        charaMany = checkCharaMany();
    }

    /**
     * 設定用キャラクター挿入確認
     *
     * @return
     */
    public void gameSetting() {
        SQLiteDatabase db = dbGame.getReadableDatabase();
        Cursor cursor = db.query(
                "charadb",
                new String[]{"charaName, level, evolution"},
                null,
                null,
                null,
                null,
                null
        );
        cursor.moveToFirst();
        for (int i = 0; i < cursor.getCount(); i++) {
            Log.d("debug", "キャラ名" + cursor.getString(0));
            Log.d("debug", "レベル" + cursor.getString(1));
            Log.d("debug", "進化段階" + cursor.getString(2));
            cursor.moveToNext();
        }
        cursor.close();
    }

    public void evolveSetting() {
        SQLiteDatabase db = dbGame.getReadableDatabase();
        Cursor cursor = db.query(
                "evolvedb",
                new String[]{"evolveName, evolution, originName"},
                null,
                null,
                null,
                null,
                null
        );
        cursor.moveToFirst();

        for (int i = 0; i < cursor.getCount(); i++) {
            Log.d("debug", "進化名：" + cursor.getString(0));
            Log.d("debug", "進化段階：" + cursor.getString(1));
            Log.d("debug", "元キャラ名；" + cursor.getString(2));
            cursor.moveToNext();
        }
        cursor.close();
    }

    /**
     * 現在表示しているキャラクターの中からランダムでレベルをあげる
     *
     * @return
     */
    public int levelUp() {
        //レベルアップするキャラをランダムに決定
        Random random = new Random();
        int aiai = random.nextInt(charaMany) + 1;

        SQLiteDatabase dbRead = dbGame.getReadableDatabase();
        Cursor cursor = dbRead.query(
                "charadb",
                new String[]{"_id, charaName, level, evolution"},
                "_id = ?",
                new String[]{String.valueOf(aiai)},
                null,
                null,
                null
        );
        cursor.moveToFirst();
        //指定キャラのレベルを保持
        int levelSta = Integer.parseInt(cursor.getString(2));


        SQLiteDatabase dbInsert = dbGame.getWritableDatabase();
        ContentValues values = new ContentValues();
        levelSta++;
        values.put("level", levelSta);
        dbInsert.update("charadb", values, "_id = " + cursor.getString(0), null);

        //進化チェック
        checkEvolve(cursor.getString(0), cursor.getString(1), levelSta, Integer.parseInt(cursor.getString(3)));

        cursor.close();
        return levelSta;
    }

    private void checkEvolve(String id, String charaName, int level, int evolveLevel) {
        //進化チェック
        if ((level >= 10) && (evolveLevel == 0)) {

        } else if ((level >= 20) && (evolveLevel == 1)) {

        } else if ((level >= 30) && (evolveLevel == 2)) {

        } else {
            return;
        }
    }

    /**
     * 進化判定
     */

    /**
     * 表示できるキャラクター数確認
     */
    public int checkCharaMany() {
        dbGame = new DataBaseGame(MyApplication.getAppContext());
        SQLiteDatabase db = dbGame.getReadableDatabase();
        Cursor cursor = db.query(
                "charadb",
                new String[]{"charaName, level"},
                "level > 0",
                null,
                null,
                null,
                null
        );
        cursor.moveToFirst();
        int many = cursor.getCount();
        cursor.close();
        return many;
    }

    public void resetDB() {
        Log.d("debug", "データベース初期化開始");
        SQLiteDatabase db = dbGame.getWritableDatabase();
        db.delete("charadb", null, null);
        db.delete("evolvedb", null, null);
        dbGame.reCreate(db);
        dbGame.reCreate2(db);
        Log.d("debug", "データベース初期化完了");

    }

    public String callCharaName(String charaName) {
        SQLiteDatabase db = dbGame.getReadableDatabase();
        Cursor cursor = db.query(
                "charadb",
                new String[]{"charaName, evolution"},
                "charaName = ?",
                new String[]{charaName},
                null,
                null,
                null
        );
        cursor.moveToFirst();

        Log.d("debug", "抽出数" + cursor.getCount());
        Cursor cursor2 = db.query(
                "evolvedb",
                new String[]{"evolveName"},
                "evolution = ? and originName = ?",
                new String[]{cursor.getString(1), charaName},
                null,
                null,
                null
        );
        cursor2.moveToFirst();
        Log.d("debug", "抽出数" + cursor2.getCount());
        String chara = cursor2.getString(0);
        return chara;
    }
}
