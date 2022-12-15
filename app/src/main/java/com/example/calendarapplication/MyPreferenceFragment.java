package com.example.calendarapplication;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.example.calendarapplication.Game.GameActivity;

public class MyPreferenceFragment extends PreferenceFragmentCompat {
    private GameActivity gameActivity;
    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
        setPreferencesFromResource(R.xml.preferences,rootKey);


        gameActivity = new GameActivity();
        gameActivity.createDataBase();

        Preference button = findPreference("resetGameDataBase");
        button.setOnPreferenceClickListener(preference -> {
            gameActivity.resetDB();
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

    }
}
