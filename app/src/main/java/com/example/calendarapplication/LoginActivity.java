package com.example.calendarapplication;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
//import android.widget.Button;
import com.beardedhen.androidbootstrap.AwesomeTextView;
import com.beardedhen.androidbootstrap.BootstrapButton;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText password;
    private BootstrapButton button;
//    private Button button;
//    private Button buttonForgot;
    private DataBaseLogin helper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        // Viewの取得
        password = findViewById(R.id.password);
        button = findViewById(R.id.button);

        // ボタンにクリックリスナーをセット
        button.setOnClickListener(this);

        // スクショを無効化
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        // 文字制限
        password.setFilters(new InputFilter[]{new InputFilter.LengthFilter(5)});
        // 数字入力キーボード、パスワードを隠す
        password.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
        // データベース
        helper = new DataBaseLogin(getApplicationContext());
    }

    // エラー回数
    int numError = 0;

    // 送信ボタン押下
    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            // パスワード入力
            case R.id.button:
                // 入力欄が空白かチェック
                if (password.length() != 0) {
                    // ログイン処理
                    // dbデータ取得(パスワード)
                    // readData(); // データベースを読み込む
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
                    String pswd = password.getText().toString();

                    // 照合
                    Log.d("debug", "判定処理:" + dswd + "," + pswd);
                    if (pswd.equals(dswd)) {

                        Log.d("debug", "成功:" + dswd + "," + pswd);

                        // 画像認証に遷移
                        Intent intent = new Intent(getApplication(), MainActivity.class);
                        startActivity(intent);

                    } else {
                        password.setError("パスワードが違います");

                        // エラー回数を足す
                        numError += 1;
                        Log.d("debug", "失敗" + numError);

                        // 画像認証に遷移
                        if (numError > 5) {
                            Intent intent = new Intent(getApplication(), ImageActivity.class);
                            startActivity(intent);
                        }
                    }
                } else {
                    password.setError("入力してください");
                }
                break;
        }
    }

    // データベース
//    public void readData() {
//        SQLiteDatabase db = helper.getReadableDatabase();
//        Cursor cursor = db.query(
//                "pswddb",
//                new String[]{"password"},
//                null,
//                null,
//                null,
//                null,
//                null
//        );
//        cursor.moveToFirst();
//
//        StringBuilder sbuilder = new StringBuilder();
//        for (int i = 0; i < cursor.getCount(); i++) {
//            sbuilder.append(cursor.getString(0));
//            cursor.moveToNext();
//        }
//        Log.d("debug", "" + sbuilder);
//        cursor.close();
//        db.close();
//    }
//
//    public void saveData(View view) {
//        SQLiteDatabase db = helper.getWritableDatabase();
//        ContentValues values = new ContentValues();
//
//        EditText DbPswd = findViewById(R.id.password);
//        String password = DbPswd.getText().toString();
//        values.put("password", password);
//
//        db.insert("pswddb", null, values);
//    }

    // 待機画面に遷移
//    Button button = findViewById(R.id.button);
//        button.setOnClickListener(v ->
//
//    {
//        Intent intent = new Intent(getApplication(), StopActivity.class);
//        startActivity(intent);
//    });
//}

}
