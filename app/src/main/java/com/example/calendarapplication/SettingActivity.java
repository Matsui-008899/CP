package com.example.calendarapplication;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

/**
 * 設定画面
 */
public class SettingActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);

        this.initializeToolBar();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.setting_container,new MyPreferenceFragment())
                .commit();
    }

    private void initializeToolBar() {
        Toolbar toolbar = findViewById(R.id.settingsToolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        ActionBar actionBar = this.getSupportActionBar();
        if (actionBar == null){
            return;
        }
        actionBar.setDisplayHomeAsUpEnabled(true);
    }
}
