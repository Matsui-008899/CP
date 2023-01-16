package com.example.calendarapplication.Game;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.util.Log;
import android.widget.Toast;

import com.example.calendarapplication.AchieveView.AchieveViewRowData;
import com.example.calendarapplication.MyApplication;

import java.util.Calendar;
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
        Log.d("eheheh","レベル挿入"+levelSta);
        values.put("level", levelSta);
        //DBにレベルを反映
        dbInsert.update("charadb", values, "_id = " + cursor.getString(0), null);

        if (levelSta % 5 == 0){
            checkingAchieve(2, levelSta);
        }


        //Toast表示
        String message = cursor.getString(1) + "がレベルアップ：現在レベル" + levelSta;
        Toast.makeText(MyApplication.getAppContext(), message, Toast.LENGTH_LONG).show();


        //進化：塁化キャラチェック
        boolean visibleChara = checkEvolve(cursor.getString(0), cursor.getString(1), levelSta, Integer.parseInt(cursor.getString(3)));
        Log.d("debug", "現在レベル" + levelSta + "名前：" + cursor.getString(1) + "進化段階：" + Integer.parseInt(cursor.getString(3)));
        cursor.close();
        if (visibleChara) {
            return true;
        }
        return false;
    }

    /**
     * キャラ進化＆キャラ追加判定
     */
    private boolean checkEvolve(String id, String charaName, int level, int evolveLevel) {
        SQLiteDatabase db = dbGame.getWritableDatabase();
        //追加キャラチェック
        if ((level == 5)) {
            if (charaName.equals("chara1")) {
                //２体目追加
                Log.d("debug", "２体目キャラ出現：");
                addChara(db, "2");
                return true;
            }
            if (charaName.equals("chara2")) {
                //２体目追加
                Log.d("debug", "３体目キャラ出現：");
                addChara(db, "3");
                return true;
            }
        }


        //進化チェック
//        if ((level >= 10) && (evolveLevel == 0)) {
//            evolve(db, id, evolveLevel);
//            if (charaName.equals("chara1")) {
//                //２体目追加
//                Log.d("debug", "２体目キャラ出現：");
//                addChara(db, "2");
//            }
//            return true;
//        } else if ((level >= 20) && (evolveLevel == 1)) {
//            evolve(db, id, evolveLevel);
//            return true;
//        } else if ((level >= 30) && (evolveLevel == 2)) {
//            return evolve(db, id, evolveLevel);
//        }
        return false;
    }

    /**
     * 追加キャラ処理
     *
     * @param db
     * @param id
     * @return
     */
    private boolean addChara(SQLiteDatabase db, String id) {
        ContentValues values = new ContentValues();
        values.put("level", 1);
        db.update("charadb", values, "_id = " + id, null);
        charaMany = checkCharaMany();
        checkingAchieve(1,0);
        return true;
    }


    /**
     * 進化処理
     *
     * @param db
     * @param id
     * @param evolveLevel
     * @return
     */
    private boolean evolve(SQLiteDatabase db, String id, int evolveLevel) {
        ContentValues values = new ContentValues();
        //db更新エボルヴテーブル
        int i = evolveLevel + 1;
        values.put("evolution", i);
        db.update("charadb", values, "_id = " + id, null);
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
        Log.d("debug", "表示キャラ数" + many);
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
     *
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
        if (cursor.getCount() == 1) {
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
        } else {
            Log.d("debug", charaName + "：抽出失敗");
            return null;
        }

    }

    /**
     * 実績のリスト作成
     *
     * @param dataset
     * @return
     */
    public List<AchieveViewRowData> listAchieve(List<AchieveViewRowData> dataset) {
        SQLiteDatabase db = dbGame.getReadableDatabase();

        Cursor cursor = db.query(
                "achievedb",
                new String[]{"achieveName, achieveContent, achieveDate, comple"},
                null,
                null,
                null,
                null,
                null
        );

        cursor.moveToFirst();
        Log.d("debug", "総合実績件数" + cursor.getCount());

        for (int i = 0; i < cursor.getCount(); i++) {
            AchieveViewRowData data = new AchieveViewRowData();

            data.setAchieveName(cursor.getString(0));
            data.setAchieveInfo(cursor.getString(1));
            if (Integer.parseInt(cursor.getString(3)) == 0) {
                data.setAchieveInfoColor(Color.LTGRAY);
                data.setAchieveNameColor(Color.LTGRAY);
            } else {
                data.setAchieveInfoColor(Color.BLACK);
                data.setAchieveNameColor(Color.BLACK);
                data.setAchieveDate(cursor.getString(2));
            }

            dataset.add(data);
            cursor.moveToNext();
            Log.d("debug", "実績処理実行");
        }

        cursor.close();

        return dataset;
    }

    public void resetAchieve() {
        SQLiteDatabase db = dbGame.getWritableDatabase();
        db.delete("achievedb", null, null);
        dbGame.reCreate3(db);
    }

    /**
     * 吹き出しのレベル表示用出力
     *
     * @return
     */
    public int[] levelBalloon() {
        SQLiteDatabase dbRead = dbGame.getReadableDatabase();
        Cursor cursor = dbRead.query(
                "charadb",
                new String[]{"_id, level"},
                null,
                null,
                null,
                null,
                null
        );
        cursor.moveToFirst();


        int[] level = new int[3];
        for (int i = 0; i < cursor.getCount(); i++) {
            level[i] = Integer.parseInt(cursor.getString(1));

            cursor.moveToNext();
        }
        cursor.close();

        return level;
    }

    /**
     * 吹き出しのキャラ名表示用出力
     *
     * @return
     */
    public String[] nameBalloon() {
        SQLiteDatabase dbRead = dbGame.getReadableDatabase();
        Cursor cursor = dbRead.query(
                "charadb",
                new String[]{"_id, charaName"},
                null,
                null,
                null,
                null,
                null
        );
        cursor.moveToFirst();

        Cursor cursor2 = dbRead.query(
                "evolvedb",
                new String[]{"evolveName"},
                "originName = ?",
                new String[]{cursor.getString(1)},
                null,
                null,
                null
        );
        cursor2.moveToFirst();

        String[] name = new String[3];
        for (int i = 0; i < cursor.getCount(); i++) {
            name[i] = cursor.getString(1);

            cursor.moveToNext();
        }
        cursor.close();

        return name;
    }

    public String[] charaNameBalloon() {

        String[] chara = new String[3];
        SQLiteDatabase db = dbGame.getReadableDatabase();
        Cursor cursor = db.query(
                "charadb",
                new String[]{"charaName, evolution"},
                null,
                null,
                null,
                null,
                null
        );
        cursor.moveToFirst();

        for (int i = 0; i < cursor.getCount(); i++) {
            Cursor cursor2 = db.query(
                    "evolvedb",
                    new String[]{"evolveName"},
                    "evolution = ? and originName = ?",
                    new String[]{cursor.getString(1), cursor.getString(0)},
                    null,
                    null,
                    null
            );
            cursor2.moveToFirst();
            chara[i] = cursor2.getString(0);
            cursor.moveToNext();
            cursor2.close();
        }

        cursor.close();
        return chara;
    }

    public void checkingAchieve(int kind, int level) {
        SQLiteDatabase db = dbGame.getReadableDatabase();
        SQLiteDatabase dbw = dbGame.getWritableDatabase();
        //キャラ獲得時
        if (kind == 1) {
            Cursor cursor = db.query(
                    "charadb",
                    new String[]{"_id"},
                    "level != 0",
                    null,
                    null,
                    null,
                    null
            );
            int count = cursor.getCount();


            int setId = 0;
            if (count == 1) {
                setId = 1;
            } else if (count == 2) {
                setId = 2;
            } else if (count == 3) {
                setId = 3;
            }
            if (setId != 0){
                setAchieve(setId);
            }
            cursor.close();
        }
        //レベルアップ時
        if (kind == 2) {
            int search = 0;
            if (level == 5){
                search = 5;
            }
            if (level == 10){
                search = 10;
            }
            if (level == 30){
                search = 30;
            }
            if (search != 0){
                Cursor cursor = db.query(
                        "charadb",
                        new String[]{"_id, level"},
                        "level >= ?",
                        new String[]{String.valueOf(search)},
                        null,
                        null,
                        null
                );
                int count = cursor.getCount();

                int setId = 0;
                if (count == 1){
                    if (search == 5){
                        setId = 9;
                    }
                    if (search == 10){
                        setId = 10;
                    }
                    if (search == 30){
                        setId = 11;
                    }
                }
                if(count == 2){
                    if (search == 5){
                        setId = 12;
                    }
                    if (search == 10){
                        setId = 13;
                    }
                    if (search == 30){
                        setId = 14;
                    }
                }
                if(count == 3){
                    if (search == 5){
                        setId = 15;
                    }
                    if (search == 10){
                        setId = 16;
                    }
                    if (search == 30){
                        setId = 17;
                    }
                }
                if(setId != 0 ){
                    setAchieve(setId);
                }

                cursor.close();
            }
        }
        //進化時
        if (kind != 0) {

        }
        if(kind == 3){
            int setId = 0;
            if (level == 1){
                setId = 4;
            }
            if (level == 5){
                setId = 5;
            }
            if (level == 15){
                setId = 6;
            }if (level == 20){
                setId = 7;
            }if (level == 30){
                setId = 8;
            }

            if (setId != 0){
                setAchieve(setId);
            }


        }

    }

    private void setAchieve(int setId){
        SQLiteDatabase dbw = dbGame.getWritableDatabase();
        Calendar c = Calendar.getInstance();
        String date = String.format("%d/%02d/%02d", c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1, c.get(Calendar.DATE));
        ContentValues values = new ContentValues();
        values.put("achieveDate", date);
        values.put("comple", 1);
        dbw.update("achievedb", values, "_id = " + setId, null);
        popUpAC(setId);
    }

    private void popUpAC(int setId) {
        Log.d("dd","うに"+setId);
        SQLiteDatabase db = dbGame.getReadableDatabase();
        Cursor cursor = db.query(
                "achievedb",
                new String[]{"_id, achieveName"},
                "_id = ?",
                new String[]{String.valueOf(setId)},
                null,
                null,
                null
        );
        cursor.moveToFirst();
        Log.d("dd","ddddd"+cursor.getCount());
        Log.d("dd","ddddd"+cursor.getString(1));
        String message = "実績："+cursor.getString(1) + "を達成";
        Toast.makeText(MyApplication.getAppContext(), message, Toast.LENGTH_LONG).show();
    }
}
