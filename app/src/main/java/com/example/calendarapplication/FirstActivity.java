package com.example.calendarapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.beardedhen.androidbootstrap.BootstrapButton;

public class FirstActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText firstPassword;
    private BootstrapButton firstButton;
    private DataBaseLogin helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        // Viewの取得
        firstPassword = findViewById(R.id.firstPassword);
        firstButton = findViewById(R.id.firstButton);

        // ボタンにクリックリスナーをセット
        firstButton.setOnClickListener(this);

        // スクショを無効化
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        // 文字制限
        firstPassword.setFilters(new InputFilter[]{new InputFilter.LengthFilter(5)});
        // 数字入力キーボード、パスワードを隠す
        firstPassword.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
        // データベース
        helper = new DataBaseLogin(getApplicationContext());
    }

    // 送信ボタン押下
    @Override
    public void onClick(View view) {
        //パスワード入力
        if (view.getId() == R.id.firstButton) {
            // 入力欄が空白かチェック
            if (firstPassword.length() != 0) {
                if (firstPassword.length() == 5) {
                    // パスワード登録
                    // DBにパスワードを登録
                    saveData();
                    Intent intent = new Intent(getApplication(), SecondActivity.class);
                    startActivity(intent);
                } else {
                    firstPassword.setError("5文字入力してください");
                }
            } else {
                firstPassword.setError("入力してください");
            }
        }
    }

    public void saveData() {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();

        EditText dbPswd = findViewById(R.id.firstPassword);
        String fstDbPassword = dbPswd.getText().toString();
        values.put("password", fstDbPassword);

        db.insert("pswddb", null, values);
    }
}