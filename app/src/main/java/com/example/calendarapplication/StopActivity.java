package com.example.calendarapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;

import androidx.appcompat.app.AppCompatActivity;

public class StopActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stop);

        // 15秒のカウントダウン
        CountDownTimer countDownTimer = new CountDownTimer(15000,100) {
            @Override
            public void onTick(long t) {
                int time = (int) t/1000;

            }

            @Override
            public void onFinish() {
                Intent intent = new Intent(getApplication(), LoginActivity.class);
                startActivity(intent);
            }
        }.start();
    }
}