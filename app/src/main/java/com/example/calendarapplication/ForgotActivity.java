package com.example.calendarapplication;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.beardedhen.androidbootstrap.BootstrapButton;

public class ForgotActivity extends AppCompatActivity implements View.OnClickListener {

   private EditText current;
   private EditText nnew;
   private EditText re;
   private BootstrapButton fgtButton;
   private BootstrapButton fgtButton2;
   private DataBaseLogin helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);

        // Viewの取得
        current = findViewById(R.id.fgtCurrentPassword);
        nnew = findViewById(R.id.fgtNewPassword);
        re = findViewById(R.id.fgtRePassword);
        fgtButton = findViewById(R.id.fgtButton);
        fgtButton2 = findViewById(R.id.fgtButton2);

        // リスナーをセット
        fgtButton.setOnClickListener(this);
        fgtButton2.setOnClickListener(this);

        // スクショを無効化
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        // 文字制限
        current.setFilters(new InputFilter[]{new InputFilter.LengthFilter(5)});
        nnew.setFilters(new InputFilter[]{new InputFilter.LengthFilter(5)});
        re.setFilters(new InputFilter[]{new InputFilter.LengthFilter(5)});
        // 数字入力キーボード、パスワードを隠す
        current.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
        nnew.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
        re.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
        // データベース
        helper = new DataBaseLogin(getApplicationContext());
    }

    // エラー回数
    int numError = 0;

    // 変更ボタン押下
    @Override
    public void onClick(View view) {
        // 入力欄が空白かチェック、5文字かチェック
        // 現在のパスワード
        if (current.length() != 0 && current.length() == 5 ) {
            // 新しいパスワード
            if (nnew.length() != 0 && nnew.length() == 5) {
                // 再入力パスワード
                if (re.length() != 0 && re.length() == 5) {
                    // 現在のパスワードと新しいパスワードが同じじゃないか
                    Log.d("debug", "現新:" + current.getText() + "," + nnew.getText());
                    if (!current.getText().toString().equals(nnew.getText().toString())) {
                        // 新しいパスワードと再入力パスワードが同じか
                        Log.d("debug", "新再:" + nnew.getText() + "," + re.getText());
                        if (nnew.getText().toString().equals(re.getText().toString())) {
                            // dbデータ取得(現在のパスワード)
                            SQLiteDatabase db = helper.getReadableDatabase();
                            Cursor cursor = db.query(
                                    "pswddb",
                                    new String[]{"password"},
                                    null,
                                    null,
                                    null,
                                    null,
                                    null
                            );
                            cursor.moveToFirst();

                            StringBuilder sbuilder = new StringBuilder();
                            for (int i = 0; i < cursor.getCount(); i++) {
                                sbuilder.append(cursor.getString(0));
                                cursor.moveToNext();
                            }
                            Log.d("debug", "" + sbuilder);
                            cursor.close();
                            db.close();
                            String dswd = sbuilder.toString();

                            // 入力データ取得
                            String pswd = current.getText().toString();
                            // 照合
                            Log.d("debug", "判定処理:" + dswd + "," + pswd);

                            if (pswd.equals(dswd)) {
                                Log.d("debug", "成功:" + dswd + "," + pswd);

                                updateData(nnew);

                                Toast.makeText(getApplicationContext(), "再設定完了", Toast.LENGTH_LONG).show();

                                // 画像認証に遷移
                                Intent intent = new Intent(getApplication(), MainActivity.class);
                                startActivity(intent);

                            } else {
                                current.setError("パスワードが違います");

                                // エラー回数を足す
                                numError += 1;
                                Log.d("debug", "失敗" + numError);

                                // 5回失敗で１５秒待機
                                if (numError > 5) {
                                    Intent intent = new Intent(getApplication(), StopActivity.class);
                                    startActivity(intent);
                                }
                            }// pswd.equals(dswd)
                        } else {
                            nnew.setError("一致させてください");
                            re.setError("一致させてください");
                        }// nnew == re
                    } else {
                        nnew.setError("パスワードを変えてください");
                    }// current != nnew
                } else {
                    re.setError("入力してください");
                }// re.length() != 0 && re.length() == 5
            } else {
                nnew.setError("入力してください");
            }// nnew.length() != 0 && nnew.length() == 5
        } else {
            current.setError("入力してください");
        }// current.length() != 0 && current.length() == 5

        if (view.getId() == R.id.fgtButton2) {
            Intent intent = new Intent(getApplication(), SettingActivity.class);
            startActivity(intent);
        }
    } // Click

    private void updateData(EditText nnew) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();

        String password = nnew.getText().toString();
        values.put("password", password);

        db.update("pswddb", values, "_id = 1" ,null);
    }
}