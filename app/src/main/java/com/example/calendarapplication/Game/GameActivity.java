package com.example.calendarapplication.Game;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.calendarapplication.AchieveView.AchieveViewRowData;
import com.example.calendarapplication.MyApplication;

import java.util.List;
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
     */
    public boolean levelUp() {
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
        //DBにレベルを反映
        dbInsert.update("charadb", values, "_id = " + cursor.getString(0), null);

        //進化：塁化キャラチェック
        boolean visibleChara = checkEvolve(cursor.getString(0), cursor.getString(1), levelSta, Integer.parseInt(cursor.getString(3)));
        Log.d("debug", "現在レベル"+levelSta+ "名前："+cursor.getString(1)+ "進化段階："+Integer.parseInt(cursor.getString(3)));
        cursor.close();
        if (visibleChara){
            return true;
        }
        return false;
    }

    /**
     * キャラ進化＆キャラ追加判定
     * @param id　キャラID
     * @param charaName　キャラネーム
     * @param level　レベル
     * @param evolveLevel　進化段階
     * @return　進化orキャラ追加
     */
    private boolean checkEvolve(String id, String charaName, int level, int evolveLevel) {
        SQLiteDatabase db = dbGame.getWritableDatabase();
        //追加キャラチェック
        if ((level >= 5) ) {
            if(charaName.equals("chara1") ){
                //２体目追加
                Log.d("debug", "２体目キャラ出現：" );
                addChara(db,"2");
                return true;
            }
            if(charaName.equals("chara2") ){
                //２体目追加
                Log.d("debug", "３体目キャラ出現：" );
                addChara(db,"3");
                return true;
            }
        }


        //進化チェック
        if ((level >= 10) && (evolveLevel == 0)) {
            evolve(db,id,evolveLevel);
            if(charaName.equals("chara1") ){
                //２体目追加
                Log.d("debug", "２体目キャラ出現：" );
                addChara(db,"2");
            }
            return true;
        } else if ((level >= 20) && (evolveLevel == 1)) {
            evolve(db,id,evolveLevel);
            return true;
        } else if ((level >= 30) && (evolveLevel == 2)) {
            return evolve(db,id,evolveLevel);
        }
        return false;
    }

    /**
     * 追加キャラ処理
     * @param db
     * @param id
     * @return
     */
    private boolean addChara(SQLiteDatabase db, String id) {
        ContentValues values = new ContentValues();
        values.put("level",1);
        db.update("charadb", values, "_id = " + id, null);
        charaMany = checkCharaMany();
        return true;
    }


    /**
     * 進化処理
     * @param db
     * @param id
     * @param evolveLevel
     * @return
     */
    private boolean evolve(SQLiteDatabase db, String id, int evolveLevel) {
        ContentValues values = new ContentValues();
        //db更新エボルヴテーブル
        int i = evolveLevel + 1;
        values.put("evolution",i);
        db.update("charadb",values,"_id = " + id,null);
        //webview更新
        return true;
    }

    /**
     * 表示できるキャラクター数確認
     */
    public int checkCharaMany() {
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
        Log.d("debug", "表示キャラ数"+many);
        cursor.close();
        return many;
    }

    /**
     * デバッグ用：キャラ情報リセット
     */
    public void resetDB() {
        Log.d("debug", "データベース初期化開始");
        SQLiteDatabase db = dbGame.getWritableDatabase();
        db.delete("charadb", null, null);
        db.delete("evolvedb", null, null);
        dbGame.reCreate(db);
        dbGame.reCreate2(db);
        Log.d("debug", "データベース初期化完了");

    }

    /**
     * DBからキャラクターが表示できるかのチェック
     * @param charaName
     * @return
     */
    public String callCharaName(String charaName) {
        SQLiteDatabase db = dbGame.getReadableDatabase();
        Cursor cursor = db.query(
                "charadb",
                new String[]{"charaName, evolution"},
                "charaName = ? and level >= 1",
                new String[]{charaName},
                null,
                null,
                null
        );
        cursor.moveToFirst();
        if (cursor.getCount()==1){
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
        }else {
            Log.d("debug", charaName+"：抽出失敗");
            return null;
        }

    }


    public List<AchieveViewRowData> listAchieve(List<AchieveViewRowData> dataset) {
        SQLiteDatabase db = dbGame.getReadableDatabase();

        Cursor cursor = db.query(
                "achievedb",
                new String[]{"achieveName, achieveContent"},
                "comple = 1",
                null,
                null,
                null,
                null
        );

        cursor.moveToFirst();
        Log.d("debug","総合実績件数"+cursor.getCount());

        for (int i = 0; i < cursor.getCount(); i++){
            AchieveViewRowData data = new AchieveViewRowData();

            data.setAchieveName(cursor.getString(0));
            data.setAchieveInfo(cursor.getString(1));

            dataset.add(data);
            cursor.moveToNext();
            Log.d("debug","実績処理実行");
        }

        cursor.close();

        return dataset;
    }

    public void resetAchieve() {
        SQLiteDatabase db = dbGame.getWritableDatabase();
        db.delete("achievedb", null, null);
        dbGame.reCreate3(db);
    }
}
