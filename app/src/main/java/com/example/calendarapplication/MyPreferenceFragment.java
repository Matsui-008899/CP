package com.example.calendarapplication;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.example.calendarapplication.Game.GameActivity;

public class MyPreferenceFragment extends PreferenceFragmentCompat {
    private GameActivity gameActivity;
    private DataBase seledb;
    private DataBaseLogin login;
    private DbImg image;

    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
        setPreferencesFromResource(R.xml.preferences,rootKey);
        seledb = new DataBase(MyApplication.getAppContext());

        gameActivity = new GameActivity();
        gameActivity.createDataBase();
        login = new DataBaseLogin(MyApplication.getAppContext());
        image = new DbImg(MyApplication.getAppContext());

        Preference button = findPreference("resetGameDataBase");
        button.setOnPreferenceClickListener(preference -> {
            gameActivity.resetDB();
            return true;
        });

        Preference buttonA = findPreference("setAchieve");
        buttonA.setOnPreferenceClickListener(preference -> {
            gameActivity.resetAchieve();
            return true;
        });

        Preference button2 = findPreference("showGameDataBase");
        button2.setOnPreferenceClickListener(preference -> {
            gameActivity.gameSetting();
            return true;
        });

        Preference button3 = findPreference("showEvolveDataBase");
        button3.setOnPreferenceClickListener(preference -> {
            gameActivity.evolveSetting();
            return true;
        });
        Preference button4 = findPreference("setTask10DataBase");
        button4.setOnPreferenceClickListener(preference -> {
            SQLiteDatabase db = seledb.getWritableDatabase();
            seledb.debugCreate(db);
            return true;
        });

        Preference button5 = findPreference("deleteAllTask");
        button5.setOnPreferenceClickListener(preference -> {
            Log.d("debug", "日程データベース初期化開始");
            SQLiteDatabase db = seledb.getWritableDatabase();
            db.delete("taskdb", null, null);
            seledb.debugCreate(db);
            Log.d("debug", "日程データベース初期化完了");
            return true;
        });


        Preference button6 = findPreference("deletePass");
        button6.setOnPreferenceClickListener(preference -> {
            Log.d("debug", "パスワード＆画像認証初期化開始");
            SQLiteDatabase dbLogin = login.getWritableDatabase();
            dbLogin.delete("pswddb", null, null);
            SQLiteDatabase dbImage = image.getWritableDatabase();
            dbImage.delete("imgdb",null,null);
            Log.d("debug", "パスワード＆画像認証初期化終了");
            Intent intent = new Intent(MyApplication.getAppContext(), FirstActivity.class);
            startActivity(intent);
            return true;

        });
    }
}
