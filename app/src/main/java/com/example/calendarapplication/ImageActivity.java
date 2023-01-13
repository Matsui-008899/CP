package com.example.calendarapplication;

import androidx.appcompat.app.AppCompatActivity;


import android.animation.ObjectAnimator;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;

public class ImageActivity extends AppCompatActivity implements View.OnClickListener{

    View zero, one, two, three, four, five, six, seven, eight, nine;
    View ano, coe, nau, mam, smi;
    TextView count;
    BootstrapButton button2, button3;

    // 配列
    String[] Varray = new String[5];
    int n = 0;

    // 成功回数
    int correct = 0;
    // 失敗回数
    int bnumError = 0;

    // true, false
    private Boolean check = false;

    private DbImg helper;

    // CREATE
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        // Viewを取得
        //カウント
        count = findViewById(R.id.ImgTextView2);

        // 数字
        zero = findViewById(R.id.imageZero);
        one = findViewById(R.id.imageOne);
        two = findViewById(R.id.imageTwo);
        three = findViewById(R.id.imageThree);
        four = findViewById(R.id.imageFour);
        five = findViewById(R.id.imageFive);
        six = findViewById(R.id.imageSix);
        seven = findViewById(R.id.imageSeven);
        eight = findViewById(R.id.imageEight);
        nine = findViewById(R.id.imageNine);
        // 古代生物
        ano = findViewById(R.id.anomalocaris);
        coe = findViewById(R.id.coelacanth);
        nau = findViewById(R.id.nautilus);
        mam = findViewById(R.id.mammoth);
        smi = findViewById(R.id.smilodon);

        // ボタン
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);

        // リスナーをセット
        zero.setOnClickListener(this);
        one.setOnClickListener(this);
        two.setOnClickListener(this);
        three.setOnClickListener(this);
        four.setOnClickListener(this);
        five.setOnClickListener(this);
        six.setOnClickListener(this);
        seven.setOnClickListener(this);
        eight.setOnClickListener(this);
        nine.setOnClickListener(this);
        ano.setOnClickListener(this);
        coe.setOnClickListener(this);
        nau.setOnClickListener(this);
        mam.setOnClickListener(this);
        smi.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);

        Log.d("debug", "通過");

        // データベース
        helper = new DbImg(getApplicationContext());
    }



    @Override
    public void onClick(View view) {
        // Viewを移動(失敗)
        // #1
//        Log.d("debug", "座標" + view.getX() + "空白" + view.getY());

//        TranslateAnimation translate = null;
//
//        if (n == 0) {
//            translate = new TranslateAnimation(view.getX(), 40, view.getY(), -406);
//            n++;
//        } else if (n == 1) {
//            translate = new TranslateAnimation(view.getX(), 240, view.getY(), -403);
//            n++;
//        } else if (n == 2) {
//            translate = new TranslateAnimation(view.getX(), 440, view.getY(), -403);
//            n++;
//        } else if (n == 3) {
//            translate = new TranslateAnimation(view.getX(), 640, view.getY(), -406);
//            n++;
//        } else if (n == 4) {
//            translate = new TranslateAnimation(view.getX(), 840, view.getY(), -406);
//            n++;
//        } else if (n > 4) {
//            // 初期化
//            n = 0;
//            Varray[0] = null;
//            Varray[1] = null;
//            Varray[2] = null;
//            Varray[3] = null;
//            Varray[4] = null;
//        }
//
//        translate.setFillAfter(true);
//
//        view.startAnimation(translate);
//

        // カウント
        String countView = (count.getText()).toString();
        int mCount = Integer.parseInt(countView);

        // 画像が5つセットされた場合の送信ボタン、リセットボタンの動き
        if (n == 5) {
            switch (view.getId()) {
                // 送信ボタン押下(判定処理)
                case R.id.button2:
                    check = true;
                    Log.d("debug", "ボタン押下:");
                    // データベース照合
                    SQLiteDatabase db = helper.getReadableDatabase();
                    Cursor cursor = db.query(
                            "imgdb",
                            new String[]{"img"},
                            null,
                            null,
                            null,
                            null,
                            null
                    );
                    cursor.moveToFirst();

                    // 配列を照合するために n を初期化
                    n = 0;

                    for (int i = 0; i < cursor.getCount(); i++) {
                        // dbデータ取得
                        String dbimg = cursor.getString(0);
                        Log.d("debug", "判定処理:" + "DBデータ=" + dbimg + "," + "配列=" + Varray[i] + ",");
                        // 照合, 選択した画像データ取得
                        if (dbimg.equals(Varray[i])) {
                            Log.d("debug", "成功:" + dbimg + "," + Varray[i]);
                            Log.d("debug", "配列の順番:" + i);
                            n++;
                            // 成功回数を足す
                            correct = correct + 1;
                            Log.d("debug", "成功回数:" + correct);
                        }
                        cursor.moveToNext();
                    }
                    cursor.close();
                    db.close();

                    // ホーム画面に遷移
                    if (correct == 5) {
                        Log.d("debug", "すべて成功:");
                        Toast.makeText(getApplicationContext(), "成功", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplication(), MainActivity.class);
                        startActivity(intent);

                    } else {
                        // db登録した画像と違う場合
                        Log.d("debug", "画像 or 並び が違います:");
                        button2.setError("画像 or 並び が違います");
                        Toast.makeText(getApplicationContext(), "画像 or 並び が違います", Toast.LENGTH_LONG).show();

                        n = 0;
                        Varray[0] = null;
                        Varray[1] = null;
                        Varray[2] = null;
                        Varray[3] = null;
                        Varray[4] = null;

                        Log.d("debug", "リセット後:" + Varray[0] + "," + Varray[1] + "," + Varray[2] + "," + Varray[3] + "," + Varray[4]);

                        mCount = 0;
                        count.setText(String.valueOf(mCount));
                        Log.d("debug", "カウントリセット:" + "," + mCount);

                        // 成功回数をリセット
                        correct = 0;
                        Log.d("debug", "成功回数リセット:" + "," + correct);

                        // 画像を表示
                        zero.setVisibility(zero.VISIBLE);
                        one.setVisibility(one.VISIBLE);
                        two.setVisibility(two.VISIBLE);
                        three.setVisibility(three.VISIBLE);
                        four.setVisibility(four.VISIBLE);
                        five.setVisibility(five.VISIBLE);
                        six.setVisibility(six.VISIBLE);
                        seven.setVisibility(seven.VISIBLE);
                        eight.setVisibility(eight.VISIBLE);
                        nine.setVisibility(nine.VISIBLE);
                        ano.setVisibility(ano.VISIBLE);
                        coe.setVisibility(coe.VISIBLE);
                        nau.setVisibility(nau.VISIBLE);
                        mam.setVisibility(mam.VISIBLE);
                        smi.setVisibility(smi.VISIBLE);

                        // エラー回数を足す
                        bnumError += 1;
                        Log.d("debug", "失敗" + bnumError);

                        // 5回失敗で画像認証に遷移
                        if (bnumError > 5) {
                            Intent intent = new Intent(getApplication(), StopActivity.class);
                            startActivity(intent);
                        }
                    }
                    break;

                // リセットボタン押下
                case R.id.button3:
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

                    mCount = 0;
                    count.setText(String.valueOf(mCount));
                    Log.d("debug", "カウントリセット:" + "," + mCount);

                    // 成功回数をリセット
                    correct = 0;
                    Log.d("debug", "成功回数リセット:" + "," + correct);

                    // 画像を表示
                    zero.setVisibility(zero.VISIBLE);
                    one.setVisibility(one.VISIBLE);
                    two.setVisibility(two.VISIBLE);
                    three.setVisibility(three.VISIBLE);
                    four.setVisibility(four.VISIBLE);
                    five.setVisibility(five.VISIBLE);
                    six.setVisibility(six.VISIBLE);
                    seven.setVisibility(seven.VISIBLE);
                    eight.setVisibility(eight.VISIBLE);
                    nine.setVisibility(nine.VISIBLE);
                    ano.setVisibility(ano.VISIBLE);
                    coe.setVisibility(coe.VISIBLE);
                    nau.setVisibility(nau.VISIBLE);
                    mam.setVisibility(mam.VISIBLE);
                    smi.setVisibility(smi.VISIBLE);
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
                        button2.setError("画像が足りません");
                        Toast.makeText(getApplicationContext(), "画像が足りません", Toast.LENGTH_LONG).show();
                    } // n < 5 && !check
                } // n != 5
                check = false;
                break;

            // リセットボタン押下
            case R.id.button3:
                // リセット
                Log.d("debug", "リセットボタン押下:");
                Log.d("debug", "リセットします➡" + Varray[0] + "," + Varray[1] + "," + Varray[2] + "," + Varray[3] + "," + Varray[4]);

//                Toast.makeText(getApplicationContext(), "リセットしました", Toast.LENGTH_LONG).show();

                n = 0;
                Varray[0] = null;
                Varray[1] = null;
                Varray[2] = null;
                Varray[3] = null;
                Varray[4] = null;

                Log.d("debug", "リセット後:" + Varray[0] + "," + Varray[1] + "," + Varray[2] + "," + Varray[3] + "," + Varray[4]);

                mCount = 0;
                count.setText(String.valueOf(mCount));
                Log.d("debug", "カウントリセット:" + "," + mCount);

                // 成功回数をリセット
                correct = 0;
                Log.d("debug", "成功回数リセット:" + "," + correct);

                // 画像を表示
                zero.setVisibility(zero.VISIBLE);
                one.setVisibility(one.VISIBLE);
                two.setVisibility(two.VISIBLE);
                three.setVisibility(three.VISIBLE);
                four.setVisibility(four.VISIBLE);
                five.setVisibility(five.VISIBLE);
                six.setVisibility(six.VISIBLE);
                seven.setVisibility(seven.VISIBLE);
                eight.setVisibility(eight.VISIBLE);
                nine.setVisibility(nine.VISIBLE);
                ano.setVisibility(ano.VISIBLE);
                coe.setVisibility(coe.VISIBLE);
                nau.setVisibility(nau.VISIBLE);
                mam.setVisibility(mam.VISIBLE);
                smi.setVisibility(smi.VISIBLE);
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

                    String zeroId
                            = getApplicationContext().getResources().
                            getResourceEntryName(zero.getId());
                    Varray[n] = zeroId;
                    n++;
                    // カウント
                    mCount++;
                    count.setText(String.valueOf(mCount));
                    break;

                case R.id.imageOne:
                    // 画像を非表示
                    view.setVisibility(view.INVISIBLE);

                    String oneId
                            = getApplicationContext().getResources().
                            getResourceEntryName(one.getId());
                    Varray[n] = oneId;
                    n++;
                    // カウント
                    mCount++;
                    count.setText(String.valueOf(mCount));
                    break;

                case R.id.imageTwo:
                    // 画像を非表示
                    view.setVisibility(view.INVISIBLE);

                    String twoId
                            = getApplicationContext().getResources().
                            getResourceEntryName(two.getId());
                    Varray[n] = twoId;
                    n++;
                    // カウント
                    mCount++;
                    count.setText(String.valueOf(mCount));
                    break;

                case R.id.imageThree:
                    // 画像を非表示
                    view.setVisibility(view.INVISIBLE);

                    String threeId
                            = getApplicationContext().getResources().
                            getResourceEntryName(three.getId());
                    Varray[n] = threeId;
                    n++;
                    // カウント

                    mCount++;
                    count.setText(String.valueOf(mCount));
                    break;

                case R.id.imageFour:
                    // 画像を非表示
                    view.setVisibility(view.INVISIBLE);

                    String fourId
                            = getApplicationContext().getResources().
                            getResourceEntryName(four.getId());
                    Varray[n] = fourId;
                    n++;
                    // カウント
                    mCount++;
                    count.setText(String.valueOf(mCount));
                    break;

                case R.id.imageFive:
                    // 画像を非表示
                    view.setVisibility(view.INVISIBLE);

                    String fiveId
                            = getApplicationContext().getResources().
                            getResourceEntryName(five.getId());
                    Varray[n] = fiveId;
                    n++;
                    // カウント
                    mCount++;
                    count.setText(String.valueOf(mCount));
                    break;

                case R.id.imageSix:
                    // 画像を非表示
                    view.setVisibility(view.INVISIBLE);

                    String sixId
                            = getApplicationContext().getResources().
                            getResourceEntryName(six.getId());
                    Varray[n] = sixId;
                    n++;
                    // カウント
                    mCount++;
                    count.setText(String.valueOf(mCount));
                    break;

                case R.id.imageSeven:
                    // 画像を非表示
                    view.setVisibility(view.INVISIBLE);

                    String sevenId
                            = getApplicationContext().getResources().
                            getResourceEntryName(seven.getId());
                    Varray[n] = sevenId;
                    n++;
                    // カウント
                    mCount++;
                    count.setText(String.valueOf(mCount));
                    break;

                case R.id.imageEight:
                    // 画像を非表示
                    view.setVisibility(view.INVISIBLE);

                    String eightId
                            = getApplicationContext().getResources().
                            getResourceEntryName(eight.getId());
                    Varray[n] = eightId;
                    n++;
                    // カウント
                    mCount++;
                    count.setText(String.valueOf(mCount));
                    break;

                case R.id.imageNine:
                    // 画像を非表示
                    view.setVisibility(view.INVISIBLE);

                    String nineId
                            = getApplicationContext().getResources().
                            getResourceEntryName(nine.getId());
                    Varray[n] = nineId;
                    n++;
                    // カウント
                    mCount++;
                    count.setText(String.valueOf(mCount));
                    break;

                case R.id.anomalocaris:
                    // 画像を非表示
                    view.setVisibility(view.INVISIBLE);

                    String anoId
                            = getApplicationContext().getResources().
                            getResourceEntryName(ano.getId());
                    Varray[n] = anoId;
                    n++;
                    // カウント
                    mCount++;
                    count.setText(String.valueOf(mCount));
                    break;

                case R.id.coelacanth:
                    // 画像を非表示
                    view.setVisibility(view.INVISIBLE);

                    String coeId
                            = getApplicationContext().getResources().
                            getResourceEntryName(coe.getId());
                    Varray[n] = coeId;
                    n++;
                    // カウント
                    mCount++;
                    count.setText(String.valueOf(mCount));
                    break;

                case R.id.nautilus:
                    // 画像を非表示
                    view.setVisibility(view.INVISIBLE);

                    String nauId
                            = getApplicationContext().getResources().
                            getResourceEntryName(nau.getId());
                    Varray[n] = nauId;
                    n++;
                    // カウント
                    mCount++;
                    count.setText(String.valueOf(mCount));
                    break;

                case R.id.mammoth:
                    // 画像を非表示
                    view.setVisibility(view.INVISIBLE);

                    String mamId
                            = getApplicationContext().getResources().
                            getResourceEntryName(mam.getId());
                    Varray[n] = mamId;
                    n++;
                    // カウント
                    mCount++;
                    count.setText(String.valueOf(mCount));
                    break;

                case R.id.smilodon:
                    // 画像を非表示
                    view.setVisibility(view.INVISIBLE);

                    String smiId
                            = getApplicationContext().getResources().
                            getResourceEntryName(smi.getId());
                    Varray[n] = smiId;
                    n++;
                    // カウント
                    mCount++;
                    count.setText(String.valueOf(mCount));
                    break;
            }

            Log.d("debug", "現在の配列:" + Varray[0] + ","+ Varray[1] + "," + Varray[2] + "," + Varray[3] + "," + Varray[4]);
            Log.d("debug", "カウント:" + "," + mCount);

        } else if ( correct != 5){ // ホーム画面に遷移した場合に　↓　のトーストが表示されてしますため

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

            mCount = 0;
            count.setText(String.valueOf(mCount));
            Log.d("debug", "カウントリセット:" + mCount);

            // 成功回数をリセット
            correct = 0;
            Log.d("debug", "成功回数リセット:" + "," + correct);

            // 画像を表示
            zero.setVisibility(zero.VISIBLE);
            one.setVisibility(one.VISIBLE);
            two.setVisibility(two.VISIBLE);
            three.setVisibility(three.VISIBLE);
            four.setVisibility(four.VISIBLE);
            five.setVisibility(five.VISIBLE);
            six.setVisibility(six.VISIBLE);
            seven.setVisibility(seven.VISIBLE);
            eight.setVisibility(eight.VISIBLE);
            nine.setVisibility(nine.VISIBLE);
            ano.setVisibility(ano.VISIBLE);
            coe.setVisibility(coe.VISIBLE);
            nau.setVisibility(nau.VISIBLE);
            mam.setVisibility(mam.VISIBLE);
            smi.setVisibility(smi.VISIBLE);
        } // if (n < 5)
    } // onClick

//    private void saveData() {
//        SQLiteDatabase db = helper.getWritableDatabase();
//        ContentValues values = new ContentValues();
//
//        values.put("", password);
//
//        db.insert("pswddb", null, values);
//    }

}