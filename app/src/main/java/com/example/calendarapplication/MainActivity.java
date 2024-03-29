package com.example.calendarapplication;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.calendarapplication.AchieveView.AchieveRecyclerViewAdapter;
import com.example.calendarapplication.AchieveView.AchieveViewRowData;
import com.example.calendarapplication.CalendarTaskView.CTaskRecyclerViewAdapter;
import com.example.calendarapplication.CalendarTaskView.CTaskViewRowData;
import com.example.calendarapplication.Casareal.CasarealRecycleViewAdapter;
import com.example.calendarapplication.Casareal.CasarealRowData;
import com.example.calendarapplication.Food.FoodRecycleAdapter;
import com.example.calendarapplication.Food.FoodRowData;
import com.example.calendarapplication.Game.GameActivity;
import com.example.calendarapplication.TimeFragment.DatePickerFragment;
import com.example.calendarapplication.TimeFragment.TimePickerFragment;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {
    private EditText taskName;
    private TextView dateS;
    private TextView timeS;
    private TextView dateE;
    private TextView timeE;
    private String flagTime;
    private String flagDate;
    private DataBase selectDB;
    private WebView gameView;
    private int taskSetting;

    InputMethodManager inputMethodManager;
    private LinearLayout layerTask;

    private  final  String[] spinnerYearItems = {
            "2020年","2021年","2022年","2023年"
    };

    private final String[] spinnerItems = {"01月", "02月", "03月", "04月", "05月", "06月",
            "07月", "08月", "09月", "10月", "11月", "12月"};

    private Integer[] idList;
    private boolean[] checkLoad;

    private SharedPreferences preference;
    private SharedPreferences.Editor editor;

    LinearLayout CalendarLayout;
    LinearLayout TaskLayout;
    LinearLayout TaskListLayout;
    LinearLayout AchievementLayout;
    LinearLayout GameLayout;

    private final String tableName = "taskdb";
    private final String idName = "_id";
    private final String startDayName = "startDay";
    private final String startTimeName = "startTime";
    private final String endDayName = "endDay";
    private final String endTimeName = "endTime";
    private final String taskNameDb = "task";
    private final String levelCheck = "level";

    private boolean charaCheck1;
    private boolean charaCheck2;
    private boolean charaCheck3;

    private GameActivity gameActivity;

    public TextView setYear;
    public TextView setMonth;
    public TextView setDay;
    public TextView setHour;
    public TextView setMin;

    private boolean char1 = false;
    private boolean char2 = false;
    private boolean char3 = false;

    private String nowDay;


    @SuppressLint({"DefaultLocale", "SetJavaScriptEnabled", "ClickableViewAccessibility", "CutPasteId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初回起動時の処理
        preference = getSharedPreferences("Preference Name", MODE_PRIVATE);
        editor = preference.edit();

        //true=初回起動の処理
        if (!preference.getBoolean("Launched", false)) {
            //ホーム画面の日別予定の出力
            findViewById(R.id.CalendarRecycleView).setVisibility(View.GONE);

            editor.putBoolean("Launched", true);
            editor.commit();
        }

        //レイアウト取得
        CalendarLayout = findViewById(R.id.layerCalendar);
        TaskLayout = findViewById(R.id.layerTask);
        TaskListLayout = findViewById(R.id.layerTaskList);
        AchievementLayout = findViewById(R.id.layerAchievement);
        GameLayout = findViewById(R.id.layerGame);

        //ダイアログ設定用フラグ
        flagTime = null;
        flagDate = null;
        taskName = findViewById(R.id.taskName);
        dateS = findViewById(R.id.dateS);
        timeS = findViewById(R.id.timeS);
        dateE = findViewById(R.id.dateE);
        timeE = findViewById(R.id.timeE);

        //DB起動
        selectDB = new DataBase(getApplicationContext());
        //DB起動ゲームver
        gameActivity = new GameActivity();
        gameActivity.createDataBase();

        inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        layerTask = findViewById(R.id.layerTask);

        //月日選択プルダウン設定
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                spinnerItems
        );
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        Spinner spinner = findViewById(R.id.spinner);
        spinner.setAdapter(adapter);

        //年選択プルダウン設定
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                spinnerYearItems
        );
        adapter2.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        Spinner spinner2 = findViewById(R.id.spinner2);
        spinner2.setAdapter(adapter2);


        //月日ごとに呼び出し済みかどうかのチェック
        checkLoad = new boolean[13];




        //予定一覧画面

        /*
        テスト：カレンダーの標識確認：削除予定
         */
        CalendarView calendar = findViewById(R.id.calendar);
        calendar.setOnDateChangeListener(
                (calendarView, year, month, date) -> {
                    String message = (String.format("%d年%02d月%02d日", year, month + 1, date));
//                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                    //日別の予定一覧表示
                    taskDaySelect(message);
                    nowDay = message;

                }
        );


        //ゲーム画面表示処理
        gameView = findViewById(R.id.gameWeb);
        gameView.setWebViewClient(new WebViewClient());
        gameView.setClickable(true);
        gameView.setEnabled(true);
        gameView.getSettings().setJavaScriptEnabled(true);
        gameView.loadUrl("file:///android_asset/testCout.html");

        StartGameCharaView();

        gameView.setOnTouchListener((view, motionEvent) -> (motionEvent.getAction() == MotionEvent.ACTION_MOVE));

        maxIdSetting();

        Calendar cal = Calendar.getInstance();
        String sYear = cal.get(Calendar.YEAR) + "年";
        setSelection(spinner2,sYear);
        String sMonth = (String.format("%02d月", cal.get(Calendar.MONTH) + 1));
        setSelection(spinner,sMonth);

        String mes = (String.format("%d年%02d月%02d日", cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH)));
        nowDay = mes;
    }

    private void setSelection(Spinner spinner, String item) {
        SpinnerAdapter adapter = spinner.getAdapter();
        int index = 0;
        for (int i = 0; i < adapter.getCount(); i++) {
            Log.d("deb",""+adapter.getItem(i));
            Log.d("deb",""+item);

            if (adapter.getItem(i).equals(item)) {
                index = i; break;
            }
        }
        spinner.setSelection(index);
    }


    /**
     * 初回起動処理
     */
    private void StartGameCharaView() {

        gameView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                AllCharaView();
            }
        });
    }

    /**
     * キャラクターの表示チェック
     */
    private void AllCharaView() {
        charaCheck1 = GameCharaView("chara1");
        charaCheck2 = GameCharaView("chara2");
        charaCheck3 = GameCharaView("chara3");
    }

    /**
     * キャラクター一件分の表示チェック
     */
    private boolean GameCharaView(String chara) {
        Log.d("debug","チェック"+chara);


        //ＮＵＬＬであれば渡されたキャラクターのレベルは０であり表示することはできない
        String charaName = gameActivity.callCharaName(chara);

        Log.d("debug", "通行確認" + charaName+"ssssssssssssssssssssss");
        //NULLでなければキャラクターを表示する
        if (charaName != null) {
            gameView.loadUrl("javascript:" + charaName + "()");
            gameView.loadUrl("javascript:setVisible('" + chara + "')");
            if (!char1&&chara=="chara1"){
                gameView.loadUrl("javascript:kurukuru('" + chara + "')");
                char1 = true;
            }
            if (!char2&&chara=="chara2"){
                gameView.loadUrl("javascript:kurukuru('" + chara + "')");
                char2 = true;
            }
            if (!char3&&chara=="chara3"){
                gameView.loadUrl("javascript:kurukuru('" + chara + "')");
                char3 = true;
            }
            Log.d("debug", "表示キャラ名" + charaName);
            Log.d("debug", "表示キャラID" + chara);
            return true;
        }

        //起動済みチェック
        if (Objects.equals(chara, "chara1") && charaCheck1) {
            return true;
        }
        if (Objects.equals(chara, "chara2") && charaCheck2) {
            return true;
        }
        if (Objects.equals(chara, "chara3") && charaCheck3) {
            return true;
        }
        return false;
    }

    /**
     * IDの最大値に更新
     */
    private void maxIdSetting() {
        //DB->ID格納
        SQLiteDatabase db = selectDB.getReadableDatabase();
        Cursor cursor = db.query(
                tableName,
                new String[]{"max(_id)"},
                null,
                null,
                null,
                null,
                null
        );
        cursor.moveToFirst();
        Log.d("debug", "月true=" + cursor.getString(0));
        if (cursor.getString(0) != null) {
            //idリストを現在最大の件数に更新
            idList = new Integer[Integer.parseInt(cursor.getString(0) + 1)];
            Log.d("dubug", "配列作成" + cursor.getString(0));
        }

        cursor.close();
    }

    /**
     * キーボード非表示（フォーカス外タッチ時）
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        inputMethodManager.hideSoftInputFromWindow(layerTask.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        layerTask.requestFocus();

        return true;
    }

    /**
     * ホーム画面表示
     */
    public void openHome(View view) {

        changeGameViewStandard();


        layerVisible(View.VISIBLE, View.GONE, View.GONE, View.GONE, View.GONE);

        //背景変更
        gameView.loadUrl("javascript:smallBack()");
        stayMotion();
        Log.d("dsdda","s："+charaCheck1);
    }


    /**
     * 予定追加画面表示
     */
    @SuppressLint("DefaultLocale")
    public void openTaskAdd(View view) {

        changeGameViewStandard();
        Calendar c = Calendar.getInstance();
        timeS.setText(String.format("%02d時%02d分", c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE)));
        timeS.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        dateS.setText(nowDay);
        dateS.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);

        timeE.setText(String.format("%02d時%02d分", c.get(Calendar.HOUR_OF_DAY) + 1, c.get(Calendar.MINUTE)));
        timeE.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        dateE.setText(nowDay);
        dateE.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);

        layerVisible(View.GONE, View.VISIBLE, View.GONE, View.GONE, View.GONE);

        gameView.loadUrl("javascript:huhouShinnyuu()");
        stayMotion();
    }

    /**
     * 予定一覧画面表示
     */
    public void openTaskView(View view) {

        changeGameViewStandard();

        layerVisible(View.GONE, View.GONE, View.VISIBLE, View.GONE, View.GONE);

        /*
         * JavaScriptにゲームキャラクターのモーション操作を指示する
         */
        gameView.loadUrl("javascript:huhouShinnyuu()");
        stayMotion();

        ListTask(view);
    }

    /**
     * 実績画面表示
     */
    public void openAchievement(View view) {

        changeGameViewStandard();

        layerVisible(View.GONE, View.GONE, View.GONE, View.VISIBLE, View.GONE);

        gameView.loadUrl("javascript:ryokan()");
        //キャラクター待機モーション
        stayMotion();

        //実績一覧表示処理
        onAchieveView(view);
    }



    /**
     * キャラ画面表示
     */
    public void openChara(View view) {
        //ゲーム画面サイズ変更
        changeGameViewSetting();

        layerVisible(View.GONE, View.GONE, View.GONE, View.GONE, View.VISIBLE);
        gameView.loadUrl("javascript:hugeBack()");

        stayMotion();
        onFoodTask(view);
    }

    /**
     * レイヤ表示管理
     */
    private void layerVisible(int home, int taskAdd, int taskList, int achievement, int game) {
        CalendarLayout.setVisibility(home);
        TaskLayout.setVisibility(taskAdd);
        TaskListLayout.setVisibility(taskList);
        AchievementLayout.setVisibility(achievement);
        GameLayout.setVisibility(game);
    }

    /**
     * 画面遷移時キャラモーション変更(モーションタイプ：待機)
     */
    private void stayMotion() {
        if(charaCheck1){
//            gameView.loadUrl("javascript:puyon('" + "chara1" + "')");
        }
        if (charaCheck2) {
//            gameView.loadUrl("javascript:puyon('" + "chara2" + "')");
        }
        if (charaCheck3) {
//            gameView.loadUrl("javascript:puyon('" + "chara3" + "')");
        }
    }


    /**
     * ゲーム画面調整
     */
    private void changeGameViewSetting() {
        LinearLayout gameLayout = findViewById(R.id.gameLayout);
        ViewGroup.LayoutParams layoutParams = gameLayout.getLayoutParams();
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200, getResources().getDisplayMetrics());
        gameLayout.setLayoutParams(layoutParams);

        popCharaInfo();
    }

    private void popCharaInfo(){
        int level[] = gameActivity.levelBalloon();
        String name[] = gameActivity.nameBalloon();
        String charaName[] = gameActivity.charaNameBalloon();


        for (int i = 0 ; i < 3 ; i++){
            Log.d("dddd","れべ："+level[i]+"れべ："+name[i]);
            if (level[i] == 0){
                return;
            }
            gameView.loadUrl("javascript:visibleBalloon('" + name[i] + "','" +level[i] + "','" +charaName[i]+"')");
        }
    }

    /**
     * ゲーム画面調整
     */
    private void changeGameViewStandard() {
        LinearLayout gameLayout = findViewById(R.id.gameLayout);
        ViewGroup.LayoutParams layoutParams = gameLayout.getLayoutParams();
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 90, getResources().getDisplayMetrics());
        gameLayout.setLayoutParams(layoutParams);
        gameView.loadUrl("javascript:invisibleBalloon()");

    }

    public void openSetting(View view) {
        Intent intent = new Intent(getApplication(), SettingActivity.class);
        startActivity(intent);
    }


    /**
     * 　予定追加開始日時選択ボタン表示
     */
    public void selectDateStartBtn(View view) {
        TextView origin = findViewById(R.id.dateS);

        int year = Integer.parseInt(origin.getText().toString().substring(
                0,origin.getText().toString().indexOf("年")
        ));

        int month = Integer.parseInt(origin.getText().toString().substring(
                origin.getText().toString().indexOf("年")+1,origin.getText().toString().indexOf("月")
        ));

        int day = Integer.parseInt(origin.getText().toString().substring(
                origin.getText().toString().indexOf("月")+1,origin.getText().toString().indexOf("日")
        ));

        ShowDialogDateView(year,month,day);
        flagDate = "timeS";
    }

    /**
     * 　予定追加開始日時選択ボタン表示
     */
    public void selectTimeStartBtn(View view) {
        TextView origin = findViewById(R.id.timeS);
        int hour = Integer.parseInt(origin.getText().toString().substring(0,origin.getText().toString().indexOf("時")));

        int min = Integer.parseInt(origin.getText().toString().substring(origin.getText().toString().indexOf("時")+1,origin.getText().toString().indexOf("分")));

        ShowDialogTimeView(hour,min);
        flagTime = "timeS";
    }


    /**
     * 予定追加終了日時選択ボタン表示
     */
    public void selectDateEndBtn(View view) {
        TextView origin = findViewById(R.id.dateE);

        int year = Integer.parseInt(origin.getText().toString().substring(
                0,origin.getText().toString().indexOf("年")
        ));

        int month = Integer.parseInt(origin.getText().toString().substring(
                origin.getText().toString().indexOf("年")+1,origin.getText().toString().indexOf("月")
        ));

        int day = Integer.parseInt(origin.getText().toString().substring(
                origin.getText().toString().indexOf("月")+1,origin.getText().toString().indexOf("日")
        ));
        ShowDialogDateView(year, month, day);
        flagDate = "timeE";

    }

    /**
     * 予定追加終了日時選択ボタン表示
     */
    public void selectTimeEndBtn(View view) {
        TextView origin = findViewById(R.id.timeE);
        int hour = Integer.parseInt(origin.getText().toString().substring(0,origin.getText().toString().indexOf("時")));

        int min = Integer.parseInt(origin.getText().toString().substring(origin.getText().toString().indexOf("時")+1,origin.getText().toString().indexOf("分")));

        ShowDialogTimeView(hour, min);
        flagTime = "timeE";

    }

    /**
     * 　開始日時選択ボタン表示（タスク編集モード）
     */
    public void selectTimeStartTaskBtn(View view) {
        //確定ボタンの親レイアウト
        LinearLayout viewPare = (LinearLayout) view.getParent();
        LinearLayout viewPaPare = (LinearLayout) viewPare.getParent();
        //全体の親レイアウト
        LinearLayout viewPPare = (LinearLayout) viewPaPare.getParent();

        taskSetting = Arrays.asList(idList).indexOf(viewPPare.getId());


        Log.d("pepe", "" + taskSetting);

        LinearLayout lv = (LinearLayout) view;
        TextView hou = (TextView) lv.getChildAt(0);
        TextView mi = (TextView) lv.getChildAt(2);

        setHour = hou;
        setMin = mi;

        int hour = Integer.parseInt(hou.getText().toString());

        int min = Integer.parseInt(mi.getText().toString());

        ShowDialogTimeView(hour, min);
        flagTime = "timeSS";
    }

    /**
     * 　開始日時選択ボタン表示（タスク編集モード）
     */
    public void selectDateStartTaskBtn(View view) {
        //確定ボタンの親レイアウト
        LinearLayout viewPare = (LinearLayout) view.getParent();
        LinearLayout viewPaPare = (LinearLayout) viewPare.getParent();
        //全体の親レイアウト
        LinearLayout viewPPare = (LinearLayout) viewPaPare.getParent();

        taskSetting = Arrays.asList(idList).indexOf(viewPPare.getId());

        LinearLayout lv = (LinearLayout)view;
        TextView yea = (TextView) lv.getChildAt(0);
        TextView mont = (TextView) lv.getChildAt(2);
        TextView da = (TextView) lv.getChildAt(4);

        setYear = yea;
        setMonth = mont;
        setDay = da;

        int year = Integer.parseInt(yea.getText().toString());
        int month = Integer.parseInt(mont.getText().toString());
        int day = Integer.parseInt(da.getText().toString());

        ShowDialogDateView(year, month, day);
        flagDate = "timeSS";
    }

    /**
     * 終了日時選択ボタン表示（タスク編集モード）
     */
    public void selectDateEndTaskBtn(View view) {
        //確定ボタンの親レイアウト
        LinearLayout viewPare = (LinearLayout) view.getParent();
        LinearLayout viewPaPare = (LinearLayout) viewPare.getParent();
        //全体の親レイアウト
        LinearLayout viewPPare = (LinearLayout) viewPaPare.getParent();

        taskSetting = Arrays.asList(idList).indexOf(viewPPare.getId());

        LinearLayout lv = (LinearLayout)view;
        TextView yea = (TextView) lv.getChildAt(0);
        TextView mont = (TextView) lv.getChildAt(2);
        TextView da = (TextView) lv.getChildAt(4);

        setYear = yea;
        setMonth = mont;
        setDay = da;

        int year = Integer.parseInt(yea.getText().toString());
        int month = Integer.parseInt(mont.getText().toString());
        int day = Integer.parseInt(da.getText().toString());

        ShowDialogDateView(year, month, day);

        flagDate = "timeEE";

    }

    /**
     * 終了日時選択ボタン表示（タスク編集モード）
     */
    public void selectTimeEndTaskBtn(View view) {
        //確定ボタンの親レイアウト
        LinearLayout viewPare = (LinearLayout) view.getParent();
        LinearLayout viewPaPare = (LinearLayout) viewPare.getParent();
        //全体の親レイアウト
        LinearLayout viewPPare = (LinearLayout) viewPaPare.getParent();

        taskSetting = Arrays.asList(idList).indexOf(viewPPare.getId());

        LinearLayout lv = (LinearLayout) view;

        TextView hou = (TextView) lv.getChildAt(0);
        TextView mi = (TextView) lv.getChildAt(2);

        setHour = hou;
        setMin = mi;


        int hour = Integer.parseInt(hou.getText().toString());

        int min = Integer.parseInt(mi.getText().toString());

        ShowDialogTimeView(hour, min);
        flagTime = "timeEE";

    }

    /**
     * 予定追加ボタン押下時
     */
    public void selectTaskSetBtn(View view) {
        //データ挿入
        InsertTask(view);
    }

    /**
     * DBインサート文
     */
    @SuppressLint("DefaultLocale")
    private void InsertTask(View view) {
        LinearLayout CalendarLayout = findViewById(R.id.layerCalendar);
        LinearLayout TaskLayout = findViewById(R.id.layerTask);

        SQLiteDatabase db = selectDB.getWritableDatabase();
        ContentValues values = new ContentValues();
        taskName = findViewById(R.id.taskName);
        dateS = findViewById(R.id.dateS);
        timeS = findViewById(R.id.timeS);
        dateE = findViewById(R.id.dateE);
        timeE = findViewById(R.id.timeE);
        String taskNameS = taskName.getText().toString();
        String dateSS = dateS.getText().toString();
        String timeSS = timeS.getText().toString();
        String dateES = dateE.getText().toString();
        String timeES = timeE.getText().toString();

        TextInputLayout emailField = findViewById(R.id.emailField);


        int test = dateSS.compareTo(dateES);
        int testSub = timeSS.compareTo(timeES);
        TextView error = findViewById(R.id.setError);
        error.setVisibility(View.GONE);
        /*
          日付判定
          時刻が開始＞終了かつ日付が開始＜終了または
         */
        if ((testSub < 0 && test <= 0) || (testSub >= 0 && test < 0) || TextUtils.isEmpty(taskNameS)) {
            Log.d("debug", "正常に処理");



            if (TextUtils.isEmpty(taskNameS)) {
                emailField.setError("予定を入力してください");

                Log.d("debug", "null値判定,いずれかの値が空欄です");
            } else {
                values.put("startday", dateSS);
                values.put("starttime", timeSS);
                values.put("endday", dateES);
                values.put("endtime", timeES);
                values.put("task", taskNameS);
                values.put("level", 0);
                Log.d("debug", "**********" + values);
                db.insert("taskdb", null, values);

                CalendarLayout.setVisibility(View.VISIBLE);
                TaskLayout.setVisibility(View.INVISIBLE);

                Snackbar.make(view, "予定："+taskNameS + "が追加されました", Snackbar.LENGTH_LONG).show();

                taskName.setText("");
                Calendar c = Calendar.getInstance();
                timeS.setText(String.format("%02d時%02d分", c.get(Calendar.HOUR), c.get(Calendar.MINUTE)));
                dateS.setText(String.format("%d年%02d月%02d日", c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1, c.get(Calendar.DAY_OF_MONTH)));

                timeE.setText(String.format("%02d時%02d分", c.get(Calendar.HOUR) + 1, c.get(Calendar.MINUTE)));
                dateE.setText(String.format("%d年%02d月%02d日", c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1, c.get(Calendar.DAY_OF_MONTH)));

                int i = Integer.parseInt(dateSS.substring(dateSS.indexOf("年") + 1, dateSS.indexOf("月")));
                checkLoad[i] = false;

                Log.d("debug", "旧idlist=" + idList.length);
                int b = idList.length;
                idList = Arrays.copyOf(idList, b + 1);
                Log.d("debug", "新idlist=" + idList.length);
            }
        } else {
            Log.d("debug", "日付に矛盾が発生");
            error.setVisibility(View.VISIBLE);
            error.setText("＊日付に矛盾が生じています");
        }


        Log.d("debug", "日付判定日にち" + test + "\n日付判定時刻" + testSub);

    }

    /**
     * カレンダーの日にちを選択時、予定しているタスクを表示
     */
    private void taskDaySelect(String message) {
        findViewById(R.id.CalendarRecycleView).setVisibility(View.VISIBLE);
        RecyclerView rv = findViewById(R.id.CalendarRecycleView);
        CTaskRecyclerViewAdapter adapter = new CTaskRecyclerViewAdapter(this.CalendarTaskCreateDataset(message));

        LinearLayoutManager lm = new LinearLayoutManager(this);

        rv.setHasFixedSize(true);
        rv.setLayoutManager(lm);
        rv.setAdapter(adapter);
    }

    /**
     * カレンダー日にち選択時、予定表示
     */
    private List<CTaskViewRowData> CalendarTaskCreateDataset(String message) {
        List<CTaskViewRowData> dataset = new ArrayList<>();

        SQLiteDatabase db = selectDB.getReadableDatabase();
        Cursor cursor = db.query(
                tableName,
                new String[]{startDayName, startTimeName, endDayName, endTimeName, taskNameDb},
                "startDay=?",
                new String[]{message},
                null,
                null,
                null
        );
        cursor.moveToFirst();

        TextView mes = findViewById(R.id.taskNone);

        if(cursor.getCount() == 0){
            mes.setVisibility(View.VISIBLE);
        }else {
            mes.setVisibility(View.GONE);
        }


        for (int i = 0; i < cursor.getCount(); i++) {
            CTaskViewRowData data = new CTaskViewRowData();

            data.setStartDayView(cursor.getString(0));
            data.setStartTimeView(cursor.getString(1));
            data.setEndDayView(cursor.getString(2));
            data.setEndTimeView(cursor.getString(3));
            data.setTaskView(cursor.getString(4));

            dataset.add(data);
            cursor.moveToNext();
        }

        cursor.close();
        return dataset;
    }

    /**
     * 月別予定一覧表示
     * createDatasetよりDBから指定した月の予定をForで出力
     * 出力した予定を日にちごとの予定のプリセット（row.xml）に当てはめて呼びだす。
     */
    public void ListTask(View view) {

        RecyclerView rv = findViewById(R.id.RecycleView);
        CasarealRecycleViewAdapter adapter = new CasarealRecycleViewAdapter(this.createDataset());

        LinearLayoutManager llm = new LinearLayoutManager(this);

        rv.setHasFixedSize(true);
        rv.setLayoutManager(llm);
        rv.setAdapter(adapter);
    }

    /**
     * DBより月別の予定を取得
     */
    private List<CasarealRowData> createDataset() {
        /*
        プリセット（Row.xml）に入れる予定のgetter,setter呼びだし
         */
        List<CasarealRowData> dataset = new ArrayList<>();

        //DB呼びだし
        SQLiteDatabase db = selectDB.getReadableDatabase();
        //月
        Spinner spinner = findViewById(R.id.spinner);
        String item = (String) spinner.getSelectedItem();

        //年
        Spinner spinner2 = findViewById(R.id.spinner2);
        String item2 = (String) spinner2.getSelectedItem();

        int id;

        //指定した月を条件にDBからその月の予定を一件ずつ呼び出す
        Cursor cursor = db.query(
                tableName,
                new String[]{idName, startDayName, startTimeName, endDayName, endTimeName, taskNameDb},
                "startDay LIKE ?",
                new String[]{item2 + item + "%"},
                null,
                null,
                "startDay"
        );
        cursor.moveToFirst();

        TextView mes = findViewById(R.id.notTask);

        if(cursor.getCount() == 0){
            mes.setVisibility(View.VISIBLE);
        }else {
            mes.setVisibility(View.GONE);
        }

        int month = Integer.parseInt(item.substring(0, item.indexOf("月")));
        Log.d("debug", "月true=" + checkLoad[month]);

        //DBから取り出した件数分回す
        for (int i = 0; i < cursor.getCount(); i++) {
            //リサイクル用データの入れ子を作成
            CasarealRowData data = new CasarealRowData();
            id = Integer.parseInt(cursor.getString(0));
            //配列にidの番号で格納
            // true 選択した月が未表示の場合or予定追加時
            // false 選択した月がすでに表示済みの場合
            idList[id] = ViewCompat.generateViewId();


            //data(setter,getter)にプリセット(Row.xml)に入れる
            // データ(開始月日、開始時刻、終了時刻etc...)をDBから抽出し挿入
            data.setStartDay(cursor.getString(1));
            data.setStartTime(cursor.getString(2));
            data.setEndDay(cursor.getString(3));
            data.setEndTime(cursor.getString(4));
            data.setTaskName(cursor.getString(5));

            //開始月日
            //例 ２０２２年｜１０｜月１１日
            data.setSDay1(cursor.getString(1).substring(
                    cursor.getString(1).indexOf("年") + 1, cursor.getString(1).indexOf("月")));
            //例 ２０２２年１０月｜１１｜日
            data.setSDay2(cursor.getString(1).substring(
                    cursor.getString(1).indexOf("月") + 1, cursor.getString(1).indexOf("日")));

            //開始時刻
            //例　｜１０｜時１１分
            data.setSTime1(cursor.getString(2).substring(0, cursor.getString(2).indexOf("時")));
            //例　１０時｜１１｜分
            data.setSTime2(cursor.getString(2).substring(cursor.getString(2).indexOf("時") + 1, cursor.getString(2).indexOf("分")));

            //終了月日
            //例 ２０２２年｜１０｜月１１日
            data.setEDay1(cursor.getString(3).substring(
                    cursor.getString(3).indexOf("年") + 1, cursor.getString(3).indexOf("月")));
            //例 ２０２２年１０月｜１１｜日
            data.setEDay2(cursor.getString(3).substring(
                    cursor.getString(3).indexOf("月") + 1, cursor.getString(3).indexOf("日")));

            //終了時刻
            //例　｜１０｜時１１分
            data.setETime1(cursor.getString(4).substring(0, cursor.getString(4).indexOf("時")));
            //例　１０時｜１１｜分
            data.setETime2(cursor.getString(4).substring(cursor.getString(4).indexOf("時") + 1, cursor.getString(4).indexOf("分")));

            //例 ｜２０２２｜年１０月１１日
            data.setYear(cursor.getString(1).substring(0, cursor.getString(1).indexOf("年")));

            //例 ｜２０２２｜年１０月１１日
            data.setEndYear(cursor.getString(3).substring(0, cursor.getString(3).indexOf("年")));

            //予定内容
            data.setTaskName(cursor.getString(5));

            //EdittextビューにIDを格納及びIDを配列で保存



            //プリセットの１件のデータを一意に識別できるように
            // DBの"_id"からID付与
            data.setId(idList[id]);

            dataset.add(data);
            cursor.moveToNext();
        }

        checkLoad[month] = true;

        //DBから呼びだしたデータの件数文Forを回す
        cursor.close();
        return dataset;
    }

    /**
     * 予定編集モード移行
     */
    public void selectUpdate(View view) {
        LinearLayout pare = (LinearLayout) view.getParent();
        LinearLayout motherLayout = (LinearLayout) pare.getParent();
        LinearLayout listLayout = (LinearLayout) motherLayout.getChildAt(0);
        LinearLayout updateLayout = (LinearLayout) motherLayout.getChildAt(1);

        listLayout.setVisibility(View.GONE);
        updateLayout.setVisibility(View.VISIBLE);
    }

    /**
     * 予定編集モード終了
     */
    public void onExit(View view) {
        LinearLayout pare = (LinearLayout) view.getParent();
        LinearLayout pareLayout = (LinearLayout) pare.getParent();
        LinearLayout motherLayout = (LinearLayout) pareLayout.getParent();
        LinearLayout listLayout = (LinearLayout) motherLayout.getChildAt(0);
        LinearLayout updateLayout = (LinearLayout) motherLayout.getChildAt(1);

        listLayout.setVisibility(View.VISIBLE);
        updateLayout.setVisibility(View.GONE);

    }

    /**
     * 編集内容確定
     * 確定ボタン押下時のEditTextから編集した内容を取り出す。
     * DBに入れるためにデータの型を整形してDBに入れる。
     */
    public void onUpdate(View view) {
        //確定ボタンの親レイアウト
        LinearLayout viewPare = (LinearLayout) view.getParent();
        //編集親レイアウト
        LinearLayout viewPaPare = (LinearLayout) viewPare.getParent();
        //全体の親レイアウト
        LinearLayout viewPPare = (LinearLayout) viewPaPare.getParent();

        //編集するIDを親レイアウトのIDから取得しidListから検索
        int id = Arrays.asList(idList).indexOf(viewPPare.getId());

        LinearLayout start1Layout = (LinearLayout) viewPaPare.getChildAt(0);
        LinearLayout layout1 = (LinearLayout) start1Layout.getChildAt(1);

        TextView year = (TextView) layout1.getChildAt(0);
        TextView startMonth = (TextView) layout1.getChildAt(2);
        TextView startDay = (TextView) layout1.getChildAt(4);
        //開始月日取得
        String startMD = year.getText() + "年" + startMonth.getText() + "月" + startDay.getText() + "日";


        LinearLayout start2Layout = (LinearLayout) viewPaPare.getChildAt(1);
        LinearLayout layout2 = (LinearLayout) start2Layout.getChildAt(1);

        TextView startHours = (TextView) layout2.getChildAt(0);
        TextView startMin = (TextView) layout2.getChildAt(2);
        //開始時刻取得
        String startHM = startHours.getText() + "時" + startMin.getText() + "分";

        LinearLayout start3Layout = (LinearLayout) viewPaPare.getChildAt(2);
        LinearLayout layout3 = (LinearLayout) start3Layout.getChildAt(1);

        TextView endYear = (TextView) layout3.getChildAt(0);
        TextView endMonth = (TextView) layout3.getChildAt(2);
        TextView endDay = (TextView) layout3.getChildAt(4);
        //終了月日取得
        String endMD = endYear.getText() + "年" + endMonth.getText() + "月" + endDay.getText() + "日";

        LinearLayout start4Layout = (LinearLayout) viewPaPare.getChildAt(3);
        LinearLayout layout4 = (LinearLayout) start4Layout.getChildAt(1);

        TextView endHours = (TextView) layout4.getChildAt(0);
        TextView endMin = (TextView) layout4.getChildAt(2);
        //終了時刻取得
        String endHM = endHours.getText() + "時" + endMin.getText() + "分";

        LinearLayout layout5 = (LinearLayout) viewPaPare.getChildAt(4);

        EditText taskText = (EditText) layout5.getChildAt(1);
        //タスク内容取得
        String task = String.valueOf(taskText.getText());

        int test = startMD.compareTo(endMD);
        int testSub = startHM.compareTo(endHM);
        /*
          日付判定
          if true　いずれかのエディットに入力されていない
              false 正常
         */
        if ((testSub < 0 && test <= 0) || (testSub >= 0 && test < 0)) {
            Log.d("debug", "正常に処理");
            /*
                空白判定
              if true　いずれかのエディットに入力されていない
              false 正常
             */
            if (TextUtils.isEmpty(task) ||
                    TextUtils.isEmpty(startMD) ||
                    TextUtils.isEmpty(startHM) ||
                    TextUtils.isEmpty(endMD) ||
                    TextUtils.isEmpty(endHM)) {
                Log.d("debug", "null値判定,いずれかの値が空欄です");
            } else {
                ContentValues values = new ContentValues();
                values.put("startday", startMD);
                values.put("starttime", startHM);
                values.put("endday", endMD);
                values.put("endtime", endHM);
                values.put("task", task);


                SQLiteDatabase db = selectDB.getWritableDatabase();
                db.update("taskdb", values, "_id = " + id, null);

                View pan = findViewById(R.id.listMonthSelectBtn);
                ListTask(pan);
            }
        } else {
            Log.d("debug", "日付に矛盾が発生");
        }
    }

    /**
     * 予定一件を削除
     */
    public void onDelete(View view) {
        //確定ボタンの親レイアウト
        LinearLayout viewPare = (LinearLayout) view.getParent();

        LinearLayout viewPPPare = (LinearLayout) viewPare.getParent();
        //全体の親レイアウト
        LinearLayout viewPPare = (LinearLayout) viewPPPare.getParent();

        int viewId = viewPPare.getId();

        SQLiteDatabase db = selectDB.getWritableDatabase();

        int i = Arrays.asList(idList).indexOf(viewId);
        int aa = db.delete("taskdb", "_id = " + i, null);

        String message = "予定を削除しました";
        Toast.makeText(MyApplication.getAppContext(), message, Toast.LENGTH_SHORT).show();

        Log.d("dubug","削除成功判定ID ￥＝"+aa);
        View pan = findViewById(R.id.listMonthSelectBtn);
        ListTask(pan);
    }

    public void onAllDelete(){

    }

    /**
     * 月日ダイアログ表示
     * @param year
     * @param month
     * @param day
     */
    public void ShowDialogDateView(int year, int month, int day) {
        /*
          newFragment.show(getSupportFragmentManager(), "timePicker");
         */
        DatePickerFragment datePicker = new DatePickerFragment();
        datePicker.selected(year,month,day);
        datePicker.show(getSupportFragmentManager(), "datePicker");

    }

    /**
     * 時刻ダイアログ表示
     * @param hour
     * @param min
     */
    public void ShowDialogTimeView(int hour, int min) {
        /*
          newFragment.show(getSupportFragmentManager(), "timePicker");
         */
        TimePickerFragment timePicker = new TimePickerFragment();
        timePicker.selected(hour,min);
        timePicker.show(getSupportFragmentManager(), "timePicker");

    }

    /**
     * 時刻セット
     */
    @SuppressLint("DefaultLocale")
    @Override
    public void onTimeSet(TimePicker timePicker, int hour, int minu) {
        if (Objects.equals(flagTime, "timeE")) {
            timeE.setText(String.format("%02d時%02d分", hour, minu));
        } else if (Objects.equals(flagTime, "timeS")) {
            timeS.setText(String.format("%02d時%02d分", hour, minu));
        } else if (Objects.equals(flagTime, "timeSS")) {
            TextView startHours = setHour;
            TextView startMin = setMin;

            startHours.setText(String.format("%02d", hour));
            startMin.setText(String.format("%02d", minu));

            setHour = null;
            setMin = null;
        } else if (Objects.equals(flagTime, "timeEE")) {
            TextView endHours = setHour;
            TextView endMin = setMin;

            endHours.setText(String.format("%02d", hour));
            endMin.setText(String.format("%02d", minu));

            setHour = null;
            setMin = null;
        }


        flagTime = null;
    }

    /**
     * 月日セット
     */
    @SuppressLint("DefaultLocale")
    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        if (Objects.equals(flagDate, "timeE")) {
            dateE.setText(String.format("%d年%02d月%02d日", year, month + 1, dayOfMonth));
        } else if (Objects.equals(flagDate, "timeS")) {
            dateS.setText(String.format("%d年%02d月%02d日", year, month + 1, dayOfMonth));
        } else if (Objects.equals(flagDate, "timeSS")) {
            TextView yearH = setYear;
            TextView startMonth = setMonth;
            TextView startDay = setDay;

            yearH.setText(String.format("%d", year));
            startMonth.setText(String.format("%02d", month + 1));
            startDay.setText(String.format("%02d", dayOfMonth));
            setYear = null;
            setMonth = null;
            setDay = null;
        } else if (Objects.equals(flagDate, "timeEE")) {
            TextView yearH = setYear;
            TextView endMonth = setMonth;
            TextView endDay = setDay;

            yearH.setText(String.format("%d", year));
            endMonth.setText(String.format("%02d", month + 1));
            endDay.setText(String.format("%02d", dayOfMonth));

            setYear = null;
            setDay = null;
            setMonth = null;
        }

        flagDate = null;
    }


    /**
     * キャラクター画面（以下キャラ画面）
     * RecyclerViewより餌情報の要求
     */
    public void onFoodTask(View view) {
        RecyclerView rv = findViewById(R.id.foodRecycleView);
        FoodRecycleAdapter adapter = new FoodRecycleAdapter(this.createFoodList());

        LinearLayoutManager llm = new LinearLayoutManager(this);

        rv.setHasFixedSize(true);
        rv.setLayoutManager(llm);
        rv.setAdapter(adapter);
    }

    /**
     * キャラの餌（完了済み予定）の出力
     */
    private List<FoodRowData> createFoodList() {
         /*
        プリセット（Row.xml）に入れる予定のgetter,setter呼びだし
         */
        List<FoodRowData> dataset = new ArrayList<>();

        //DB呼びだし
        SQLiteDatabase db = selectDB.getReadableDatabase();
        Calendar c = Calendar.getInstance();

        String item;
        String item2;
        item = String.format("%d年%02d月%02d日", c.get(Calendar.YEAR),c.get(Calendar.MONTH) + 1, c.get(Calendar.DATE));
        if(c.get(Calendar.MONTH)==0){
            item2 = String.format("%d年%02d月%02d日", c.get(Calendar.YEAR)-1,12, c.get(Calendar.DATE));
        }else {
            item2 = String.format("%d年%02d月%02d日", c.get(Calendar.YEAR),c.get(Calendar.MONTH), c.get(Calendar.DATE));
        }


        //指定した月を条件にDBからその月の予定を一件ずつ呼び出す
        Cursor cursor = db.query(
                tableName,
                new String[]{idName, startDayName, startTimeName, endDayName, endTimeName, taskNameDb, levelCheck},
                "endDay <= ? and endDay > ? and level = 0",
                new String[]{ item, item2},
                null,
                null,
                "startDay"
        );
        cursor.moveToFirst();
        //DBから取り出した件数分回す
        for (int i = 0; i < cursor.getCount(); i++) {
            //リサイクル用データの入れ子を作成
            FoodRowData data = new FoodRowData();

            data.setStartView(cursor.getString(1) + "：" + cursor.getString(2));
            data.setEndView(cursor.getString(3) + "：" + cursor.getString(4));
            data.setTaskView(cursor.getString(5));
            data.setTag(Integer.parseInt(cursor.getString(0)));

            dataset.add(data);
            cursor.moveToNext();
        }

        //DBから呼びだしたデータの件数文Forを回す
        cursor.close();

        return dataset;
    }

    /**
     * キャラ画面で
     * 予定一件をタップ時
     */
    public void checkTask(View view) {
        int id = (int) view.getTag();

        //gameActivityでレベルアップの処理を行う
//        trueを返すのであればキャラクターに変化（追加キャラor進化）があり
        boolean visibleChara = gameActivity.levelUp();
        //
        if (visibleChara) {
            AllCharaView();
            gameView.loadUrl("javascript:hugeBack()");
        }

        //予定のチェック確認
        ContentValues values = new ContentValues();
        values.put("level", 1);


        SQLiteDatabase db = selectDB.getWritableDatabase();
        db.update("taskdb", values, "_id = " + id, null);
        SQLiteDatabase dbR = selectDB.getReadableDatabase();

        Cursor cursor = dbR.query(
                "taskdb",
                new String[]{"_id"},
                "level = 1",
                null,
                null,
                null,
                null
        );
        gameActivity.checkingAchieve(3,cursor.getCount());
        cursor.close();

        popCharaInfo();
        //リスト更新
        onFoodTask(view);
    }

    /**
     * RecyclerViewより要求された実績情報を制作する
     * @param view
     */
    private void onAchieveView(View view) {
        RecyclerView rv = findViewById(R.id.achieveRecycleView);
        AchieveRecyclerViewAdapter adapter = new AchieveRecyclerViewAdapter(this.createAchieveList());

        LinearLayoutManager llm = new LinearLayoutManager(this);

        rv.setHasFixedSize(true);
        rv.setLayoutManager(llm);
        rv.setAdapter(adapter);
    }

    /**
     * 実績情報を画面に出力
     * @return
     */
    private List<AchieveViewRowData> createAchieveList() {
        List<AchieveViewRowData> dataset = new ArrayList<>();

        dataset = gameActivity.listAchieve(dataset);

        return dataset;
    }



}