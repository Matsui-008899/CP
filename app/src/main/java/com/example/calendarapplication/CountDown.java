package com.example.calendarapplication;

import android.os.CountDownTimer;
import android.util.Log;
import android.webkit.WebView;

import java.util.Random;

public class CountDown extends CountDownTimer {


    public CountDown(long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
    }
    @Override
    public void onTick(long l) {
        Log.d("debug","カウントまで"+l/1000);
    }

    @Override
    public void onFinish() {
        Log.d("debug","カウント終了");
        Random random = new Random();
        int randomValue = random.nextInt(6);
        new CountDown(10000,100);
    }
}
