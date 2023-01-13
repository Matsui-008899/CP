package com.example.calendarapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;

public class SecondActivity extends AppCompatActivity implements View.OnClickListener {

    View scdZero, scdOne, scdTwo, scdThree, scdFour, scdFive,
            scdSix, scdSeven, scdEight, scdNine;
    View scdAno, scdCoe, scdNau, scdMam, scdSmi;
    TextView scdCount;
    BootstrapButton scdButton2, scdButton3;

    // 配列
    String[] Varray = new String[5];
    int n = 0;

    // true, false
    private Boolean check = false;

    private DbImg helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        // Viewを取得
        // カウント
        scdCount = findViewById(R.id.secondTv2);

        // 数字
        scdZero = findViewById(R.id.imageZero);
        scdOne = findViewById(R.id.imageOne);
        scdTwo = findViewById(R.id.imageTwo);
        scdThree = findViewById(R.id.imageThree);
        scdFour = findViewById(R.id.imageFour);
        scdFive = findViewById(R.id.imageFive);
        scdSix = findViewById(R.id.imageSix);
        scdSeven = findViewById(R.id.imageSeven);
        scdEight = findViewById(R.id.imageEight);
        scdNine = findViewById(R.id.imageNine);
        // 古代生物
        scdAno = findViewById(R.id.anomalocaris);
        scdCoe = findViewById(R.id.coelacanth);
        scdNau = findViewById(R.id.nautilus);
        scdMam = findViewById(R.id.mammoth);
        scdSmi = findViewById(R.id.smilodon);

        // ボタン
        scdButton2 = findViewById(R.id.secondBtn2);
        scdButton3 = findViewById(R.id.secondBtn3);

        // リスナーをセット
        scdZero.setOnClickListener(this);
        scdOne.setOnClickListener(this);
        scdTwo.setOnClickListener(this);
        scdThree.setOnClickListener(this);
        scdFour.setOnClickListener(this);
        scdFive.setOnClickListener(this);
        scdSix.setOnClickListener(this);
        scdSeven.setOnClickListener(this);
        scdEight.setOnClickListener(this);
        scdNine.setOnClickListener(this);
        scdAno.setOnClickListener(this);
        scdCoe.setOnClickListener(this);
        scdNau.setOnClickListener(this);
        scdMam.setOnClickListener(this);
        scdSmi.setOnClickListener(this);
        scdButton2.setOnClickListener(this);
        scdButton3.setOnClickListener(this);

        Log.d("debug", "通過");

        // データベース
        helper = new DbImg(getApplicationContext());
    }

    @Override
    public void onClick(View view) {
        // カウント
        String scdCountView = (scdCount.getText()).toString();
        int smCount = Integer.parseInt(scdCountView);

        // 画像が5つセットされた場合の送信ボタン、リセットボタンの動き
        if (n == 5) {
            switch (view.getId()) {
                // 設定ボタン押下(登録処理)
                case R.id.secondBtn2:
                    check = true;
                    Log.d("debug", "ボタン押下:");
                    savaData();
                    Intent intent = new Intent(getApplication(), MainActivity.class);
                    startActivity(intent);
                    break;

                // リセットボタン押下
                case R.id.secondBtn3:
                    // リセット
                    Log.d("debug", "リセットボタン押下:");
                    Log.d("debug", "リセットします➡" + Varray[0] + "," + Varray[1] + "," + Varray[2] + "," + Varray[3] + "," + Varray[4]);
                    n = 0;
                    Varray[0] = null;
                    Varray[1] = null;
                    Varray[2] = null;
                    Varray[3] = null;
                    Varray[4] = null;

                    Log.d("debug", "リセット後:" + Varray[0] + "," + Varray[1] + "," + Varray[2] + "," + Varray[3] + "," + Varray[4]);

                    smCount = 0;
                    scdCount.setText(String.valueOf(smCount));
                    Log.d("debug", "カウントリセット:" + "," + smCount);

                    // 画像を表示
                    scdZero.setVisibility(scdZero.VISIBLE);
                    scdOne.setVisibility(scdOne.VISIBLE);
                    scdTwo.setVisibility(scdTwo.VISIBLE);
                    scdThree.setVisibility(scdThree.VISIBLE);
                    scdFour.setVisibility(scdFour.VISIBLE);
                    scdFive.setVisibility(scdFive.VISIBLE);
                    scdSix.setVisibility(scdSix.VISIBLE);
                    scdSeven.setVisibility(scdSeven.VISIBLE);
                    scdEight.setVisibility(scdEight.VISIBLE);
                    scdNine.setVisibility(scdNine.VISIBLE);
                    scdAno.setVisibility(scdAno.VISIBLE);
                    scdCoe.setVisibility(scdCoe.VISIBLE);
                    scdNau.setVisibility(scdNau.VISIBLE);
                    scdMam.setVisibility(scdMam.VISIBLE);
                    scdSmi.setVisibility(scdSmi.VISIBLE);
                    break;
            } // switch
        } // if (n == 5)


        // 画像が足りない場合の送信ボタン、リセットボタンの動き
        switch (view.getId()) {
            // 送信ボタン押下
            case R.id.button2:
                // 画像が足りない場合
                if (n != 5) {
                    if (n < 5 && !check) {
                        Log.d("debug", "画像が足りません:" + n);
                        scdButton2.setError("画像が足りません");
                        Toast.makeText(getApplicationContext(), "画像が足りません", Toast.LENGTH_LONG).show();
                    } // n < 5 && !check
                } // n != 5
                check = false;
                break;

            // リセットボタン押下
            case R.id.secondBtn3:
                // リセット
                Log.d("debug", "リセットボタン押下:");
                Log.d("debug", "リセットします➡" + Varray[0] + "," + Varray[1] + "," + Varray[2] + "," + Varray[3] + "," + Varray[4]);
                n = 0;
                Varray[0] = null;
                Varray[1] = null;
                Varray[2] = null;
                Varray[3] = null;
                Varray[4] = null;

                Log.d("debug", "リセット後:" + Varray[0] + "," + Varray[1] + "," + Varray[2] + "," + Varray[3] + "," + Varray[4]);

                smCount = 0;
                scdCount.setText(String.valueOf(smCount));
                Log.d("debug", "カウントリセット:" + "," + smCount);

                // 画像を表示
                scdZero.setVisibility(scdZero.VISIBLE);
                scdOne.setVisibility(scdOne.VISIBLE);
                scdTwo.setVisibility(scdTwo.VISIBLE);
                scdThree.setVisibility(scdThree.VISIBLE);
                scdFour.setVisibility(scdFour.VISIBLE);
                scdFive.setVisibility(scdFive.VISIBLE);
                scdSix.setVisibility(scdSix.VISIBLE);
                scdSeven.setVisibility(scdSeven.VISIBLE);
                scdEight.setVisibility(scdEight.VISIBLE);
                scdNine.setVisibility(scdNine.VISIBLE);
                scdAno.setVisibility(scdAno.VISIBLE);
                scdCoe.setVisibility(scdCoe.VISIBLE);
                scdNau.setVisibility(scdNau.VISIBLE);
                scdMam.setVisibility(scdMam.VISIBLE);
                scdSmi.setVisibility(scdSmi.VISIBLE);
                break;
        } // if (view.getId() == R.id.button2, R.id.button3)

        // 画像をクリックした場合の動き
        if (n < 5) {
            switch (view.getId()) {
                default:
                    break;

                case R.id.imageZero:
                    // 画像を非表示
                    view.setVisibility(view.INVISIBLE);

                    String scdZeroId
                            = getApplicationContext().getResources().
                            getResourceEntryName(scdZero.getId());
                    Varray[n] = scdZeroId;
                    n++;
                    // カウント
                    smCount++;
                    scdCount.setText(String.valueOf(smCount));
                    break;

                case R.id.imageOne:
                    // 画像を非表示
                    view.setVisibility(view.INVISIBLE);

                    String scdOneId
                            = getApplicationContext().getResources().
                            getResourceEntryName(scdOne.getId());
                    Varray[n] = scdOneId;
                    n++;
                    // カウント
                    smCount++;
                    scdCount.setText(String.valueOf(smCount));
                    break;

                case R.id.imageTwo:
                    // 画像を非表示
                    view.setVisibility(view.INVISIBLE);

                    String scdTwoId
                            = getApplicationContext().getResources().
                            getResourceEntryName(scdTwo.getId());
                    Varray[n] = scdTwoId;
                    n++;
                    // カウント
                    smCount++;
                    scdCount.setText(String.valueOf(smCount));
                    break;

                case R.id.imageThree:
                    // 画像を非表示
                    view.setVisibility(view.INVISIBLE);

                    String scdThreeId
                            = getApplicationContext().getResources().
                            getResourceEntryName(scdThree.getId());
                    Varray[n] = scdThreeId;
                    n++;
                    // カウント

                    smCount++;
                    scdCount.setText(String.valueOf(smCount));
                    break;

                case R.id.imageFour:
                    // 画像を非表示
                    view.setVisibility(view.INVISIBLE);

                    String scdFourId
                            = getApplicationContext().getResources().
                            getResourceEntryName(scdFour.getId());
                    Varray[n] = scdFourId;
                    n++;
                    // カウント
                    smCount++;
                    scdCount.setText(String.valueOf(smCount));
                    break;

                case R.id.imageFive:
                    // 画像を非表示
                    view.setVisibility(view.INVISIBLE);

                    String scdFiveId
                            = getApplicationContext().getResources().
                            getResourceEntryName(scdFive.getId());
                    Varray[n] = scdFiveId;
                    n++;
                    // カウント
                    smCount++;
                    scdCount.setText(String.valueOf(smCount));
                    break;

                case R.id.imageSix:
                    // 画像を非表示
                    view.setVisibility(view.INVISIBLE);

                    String scdSixId
                            = getApplicationContext().getResources().
                            getResourceEntryName(scdSix.getId());
                    Varray[n] = scdSixId;
                    n++;
                    // カウント
                    smCount++;
                    scdCount.setText(String.valueOf(smCount));
                    break;

                case R.id.imageSeven:
                    // 画像を非表示
                    view.setVisibility(view.INVISIBLE);

                    String scdSevenId
                            = getApplicationContext().getResources().
                            getResourceEntryName(scdSeven.getId());
                    Varray[n] = scdSevenId;
                    n++;
                    // カウント
                    smCount++;
                    scdCount.setText(String.valueOf(smCount));
                    break;

                case R.id.imageEight:
                    // 画像を非表示
                    view.setVisibility(view.INVISIBLE);

                    String scdEightId
                            = getApplicationContext().getResources().
                            getResourceEntryName(scdEight.getId());
                    Varray[n] = scdEightId;
                    n++;
                    // カウント
                    smCount++;
                    scdCount.setText(String.valueOf(smCount));
                    break;

                case R.id.imageNine:
                    // 画像を非表示
                    view.setVisibility(view.INVISIBLE);

                    String scdNineId
                            = getApplicationContext().getResources().
                            getResourceEntryName(scdNine.getId());
                    Varray[n] = scdNineId;
                    n++;
                    // カウント
                    smCount++;
                    scdCount.setText(String.valueOf(smCount));
                    break;

                case R.id.anomalocaris:
                    // 画像を非表示
                    view.setVisibility(view.INVISIBLE);

                    String scdAnoId
                            = getApplicationContext().getResources().
                            getResourceEntryName(scdAno.getId());
                    Varray[n] = scdAnoId;
                    n++;
                    // カウント
                    smCount++;
                    scdCount.setText(String.valueOf(smCount));
                    break;

                case R.id.coelacanth:
                    // 画像を非表示
                    view.setVisibility(view.INVISIBLE);

                    String scdCoeId
                            = getApplicationContext().getResources().
                            getResourceEntryName(scdCoe.getId());
                    Varray[n] = scdCoeId;
                    n++;
                    // カウント
                    smCount++;
                    scdCount.setText(String.valueOf(smCount));
                    break;

                case R.id.nautilus:
                    // 画像を非表示
                    view.setVisibility(view.INVISIBLE);

                    String scdNauId
                            = getApplicationContext().getResources().
                            getResourceEntryName(scdNau.getId());
                    Varray[n] = scdNauId;
                    n++;
                    // カウント
                    smCount++;
                    scdCount.setText(String.valueOf(smCount));
                    break;

                case R.id.mammoth:
                    // 画像を非表示
                    view.setVisibility(view.INVISIBLE);

                    String scdMamId
                            = getApplicationContext().getResources().
                            getResourceEntryName(scdMam.getId());
                    Varray[n] = scdMamId;
                    n++;
                    // カウント
                    smCount++;
                    scdCount.setText(String.valueOf(smCount));
                    break;

                case R.id.smilodon:
                    // 画像を非表示
                    view.setVisibility(view.INVISIBLE);

                    String scdSmiId
                            = getApplicationContext().getResources().
                            getResourceEntryName(scdSmi.getId());
                    Varray[n] = scdSmiId;
                    n++;
                    // カウント
                    smCount++;
                    scdCount.setText(String.valueOf(smCount));
                    break;
            }

            Log.d("debug", "現在の配列:" + Varray[0] + ","+ Varray[1] + "," + Varray[2] + "," + Varray[3] + "," + Varray[4]);
            Log.d("debug", "カウント:" + "," + smCount);

        } else if (!check){ // ホーム画面に遷移した場合に　↓　のトーストが表示されてしますため

            Log.d("debug", "n:" + "," + n);

            // 上限を超えた場合の動き
            Toast.makeText(getApplicationContext(), "選択上限を超えたため、リセット", Toast.LENGTH_LONG).show();
            Log.d("debug", "選択上限を超過した:");
            Log.d("debug", "リセットします➡" + Varray[0] + ","+ Varray[1] + "," + Varray[2] + "," + Varray[3] + "," + Varray[4]);
            // 画像を６個以上選択した場合、初期化
            n = 0;
            Varray[0] = null;
            Varray[1] = null;
            Varray[2] = null;
            Varray[3] = null;
            Varray[4] = null;

            Log.d("debug", "リセット後:" + Varray[0] + "," + Varray[1] + "," + Varray[2] + "," + Varray[3] + "," + Varray[4]);

            smCount = 0;
            scdCount.setText(String.valueOf(smCount));
            Log.d("debug", "カウントリセット:" + smCount);

            // 画像を表示
            scdZero.setVisibility(scdZero.VISIBLE);
            scdOne.setVisibility(scdOne.VISIBLE);
            scdTwo.setVisibility(scdTwo.VISIBLE);
            scdThree.setVisibility(scdThree.VISIBLE);
            scdFour.setVisibility(scdFour.VISIBLE);
            scdFive.setVisibility(scdFive.VISIBLE);
            scdSix.setVisibility(scdSix.VISIBLE);
            scdSeven.setVisibility(scdSeven.VISIBLE);
            scdEight.setVisibility(scdEight.VISIBLE);
            scdNine.setVisibility(scdNine.VISIBLE);
            scdAno.setVisibility(scdAno.VISIBLE);
            scdCoe.setVisibility(scdCoe.VISIBLE);
            scdNau.setVisibility(scdNau.VISIBLE);
            scdMam.setVisibility(scdMam.VISIBLE);
            scdSmi.setVisibility(scdSmi.VISIBLE);
        } // if (n < 5)
    }

    private void savaData() {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();

        String scdImg0 = Varray[0];
        String scdImg1 = Varray[1];
        String scdImg2 = Varray[2];
        String scdImg3 = Varray[3];
        String scdImg4 = Varray[4];

        values.put("img", scdImg0);
        db.insert("imgdb", null, values);
        values.put("img", scdImg1);
        db.insert("imgdb", null, values);
        values.put("img", scdImg2);
        db.insert("imgdb", null, values);
        values.put("img", scdImg3);
        db.insert("imgdb", null, values);
        values.put("img", scdImg4);
        db.insert("imgdb", null, values);
    }
}