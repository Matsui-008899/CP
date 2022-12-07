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
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {
    private EditText taskName;
    private EditText dateS;
    private EditText timeS;
    private EditText dateE;
    private EditText timeE;
    private String flagTime;
    private String flagDate;
    private DataBase selectDB;
    private CountDown countSta;
    private WebView gameView;
    private int taskSetting;
    private  boolean moveScene;

    InputMethodManager inputMethodManager;
    private LinearLayout layerTask;

    private final String[] spinnerItems = {"01月", "02月", "03月", "04月", "05月", "06月",
            "07月", "08月", "09月", "10月", "11月", "12月"};

    private int[][] taskLayoutNum;
    private Integer[] idList;
    private boolean[] checkLoad;

    private String[] charaMotion = {"purun","korokoro","pyon","poyoon","purupurun","pururun","puyon","papa"};

    private SharedPreferences preference;
    private SharedPreferences.Editor editor;

    @SuppressLint({"DefaultLocale", "SetJavaScriptEnabled", "ClickableViewAccessibility"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初回起動時の処理
        preference = getSharedPreferences("Preference Name",MODE_PRIVATE);
        editor = preference.edit();

        //true=初回起動の処理
        if (preference.getBoolean("Launched",false)==false){
            findViewById(R.id.CalendarRecycleView).setVisibility(View.GONE);

            editor.putBoolean("Launched",true);
            editor.commit();
        }

        //ゲーム画面表示処理
        gameView = findViewById(R.id.gameWeb);
        gameView.setWebViewClient(new WebViewClient());
        gameView.setClickable(true);
        gameView.setEnabled(true);
        gameView.getSettings().setJavaScriptEnabled(true);
        gameView.loadUrl("file:///android_asset/testCout.html");
        gameView.loadUrl("javascript:purun()");

        gameView.setOnTouchListener((view, motionEvent) -> (motionEvent.getAction() == MotionEvent.ACTION_MOVE));

        //ゲーム：待機画面モーション無限ループ
        timeCount();

        flagTime = null;
        flagDate = null;
        taskName = findViewById(R.id.taskName);
        dateS = findViewById(R.id.dateS);
        timeS = findViewById(R.id.timeS);
        dateE = findViewById(R.id.dateE);
        timeE = findViewById(R.id.timeE);

        //DB起動
        selectDB = new DataBase(getApplicationContext());

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

        //月日ごとに呼び出し済みかどうかのチェック
        checkLoad = new boolean[13];

        moveScene = false;

        //予定一覧画面

        /*
        テスト：カレンダーの標識確認
        削除予定
         */
        CalendarView calendar = findViewById(R.id.calendar);
        calendar.setOnDateChangeListener(
                (calendarView, year, month, date) -> {
                    String message = (String.format("%d年%02d月%02d日", year, month + 1, date));
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                    taskDaySelect(message);
                }

        );

        maxIdSetting();

    }

    /**
     * IDの最大値に更新
     */
    private void maxIdSetting() {
        //DB->ID格納
        SQLiteDatabase db = selectDB.getReadableDatabase();
        Cursor cursor = db.query(
                "tastdb",
                new String[]{"max(_id)"},
                null,
                null,
                null,
                null,
                null
        );
        cursor.moveToFirst();
        idList = new Integer[Integer.parseInt(cursor.getString(0) + 1)];
        taskLayoutNum = new int[Integer.parseInt(cursor.getString(0) + 1)][12];
        Log.d("dubug", "配列作成" + cursor.getString(0));
        cursor.close();
    }

    /**
     * 一定周期でキャラクターのアニメーションを変える
     */
    private void timeCount() {
        Random random = new Random();
        //レンジ：１５秒
        int tara = random.nextInt(5)*1000+10000;
        CountDownTimer cdt = new CountDownTimer(tara,1000) {
            @Override
            public void onTick(long l) {
                Log.d("debug","カウントまで"+l/1000);
                if (moveScene){
                    timeCount();
                    moveScene=false;
                    cancel();
                    return;
                }
            }

            @Override
            public void onFinish() {
                Log.d("debug","カウント終了");
                Random random = new Random();
                int randomValue = random.nextInt(7);
                gameView.loadUrl("javascript:"+charaMotion[randomValue]+"()");
                timeCount();
            }
        }.start();

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
        moveScene=true;
        LinearLayout CalendarLayout = findViewById(R.id.layerCalendar);
        LinearLayout TaskLayout = findViewById(R.id.layerTask);
        LinearLayout TaskListLayout = findViewById(R.id.layerTaskList);
        LinearLayout AchievementLayout = findViewById(R.id.layerAchievement);

        CalendarLayout.setVisibility(View.VISIBLE);
        TaskLayout.setVisibility(View.GONE);
        TaskListLayout.setVisibility(View.GONE);
        AchievementLayout.setVisibility(View.GONE);

        /*
         * JavaScriptにゲームキャラクターのモーション操作を指示する
         */
        gameView.loadUrl("javascript:purun()");
    }



    /**
     * 予定追加画面表示
     */
    public void openTaskAdd(View view) {
        moveScene=true;
        Calendar c = Calendar.getInstance();
        timeS.setText(String.format("%02d時%02d分", c.get(Calendar.HOUR), c.get(Calendar.MINUTE)));
        dateS.setText(String.format("%d年%02d月%02d日", c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1, c.get(Calendar.DAY_OF_MONTH)));

        timeE.setText(String.format("%02d時%02d分", c.get(Calendar.HOUR) + 1, c.get(Calendar.MINUTE)));
        dateE.setText(String.format("%d年%02d月%02d日", c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1, c.get(Calendar.DAY_OF_MONTH)));

        LinearLayout CalendarLayout = findViewById(R.id.layerCalendar);
        LinearLayout TaskLayout = findViewById(R.id.layerTask);
        LinearLayout TaskListLayout = findViewById(R.id.layerTaskList);
        LinearLayout AchievementLayout = findViewById(R.id.layerAchievement);

        CalendarLayout.setVisibility(View.INVISIBLE);
        TaskLayout.setVisibility(View.VISIBLE);
        TaskListLayout.setVisibility(View.INVISIBLE);
        AchievementLayout.setVisibility(View.GONE);

        gameView.loadUrl("javascript:pururun()");
    }

    /**
     * 予定一覧画面表示
     */
    public void openTaskView(View view) {
        moveScene=true;
        LinearLayout CalendarLayout = findViewById(R.id.layerCalendar);
        LinearLayout TaskLayout = findViewById(R.id.layerTask);
        LinearLayout TaskListLayout = findViewById(R.id.layerTaskList);
        LinearLayout AchievementLayout = findViewById(R.id.layerAchievement);

        CalendarLayout.setVisibility(View.GONE);
        TaskLayout.setVisibility(View.GONE);
        TaskListLayout.setVisibility(View.VISIBLE);
        AchievementLayout.setVisibility(View.GONE);

        /*
         * JavaScriptにゲームキャラクターのモーション操作を指示する
         */
        gameView.loadUrl("javascript:puyon()");

        ListTask(view);
    }

    /**
     * 実績画面表示
     */
    public void openAchievement(View view) {
        moveScene=true;
        LinearLayout CalendarLayout = findViewById(R.id.layerCalendar);
        LinearLayout TaskLayout = findViewById(R.id.layerTask);
        LinearLayout TaskListLayout = findViewById(R.id.layerTaskList);
        LinearLayout AchievementLayout = findViewById(R.id.layerAchievement);

        AchievementLayout.setVisibility(View.VISIBLE);
        CalendarLayout.setVisibility(View.GONE);
        TaskLayout.setVisibility(View.GONE);
        TaskListLayout.setVisibility(View.GONE);

        /*
         * JavaScriptにゲームキャラクターのモーション操作を指示する
         */
        gameView.loadUrl("javascript:tara()");
    }

    public void openSetting(View view) {
        Intent intent = new Intent(getApplication(), SettingActivity.class);
        startActivity(intent);
    }

    /**
     * 　開始日時選択ボタン表示
     */
    public void selectTimeStartBtn(View view) {
        ShowDialogView();
        flagTime = "timeS";
        flagDate = "timeS";
    }

    /**
     * 終了日時選択ボタン表示
     */
    public void selectTimeEndBtn(View view) {
        ShowDialogView();
        flagTime = "timeE";
        flagDate = "timeE";

    }

    /**
     * 　開始日時選択ボタン表示（タスク編集モード）
     */
    public void selectTimeStartTaskBtn(View view) {
        //確定ボタンの親レイアウト
        LinearLayout viewPare = (LinearLayout) view.getParent();
        //全体の親レイアウト
        LinearLayout viewPPare = (LinearLayout) viewPare.getParent();

        taskSetting = Arrays.asList(idList).indexOf(viewPPare.getId());



        Log.d("pepe",""+taskSetting);
        ShowDialogView();
        flagTime = "timeSS";
        flagDate = "timeSS";
    }

    /**
     * 終了日時選択ボタン表示（タスク編集モード）
     */
    public void selectTimeEndTaskBtn(View view) {
        //確定ボタンの親レイアウト
        LinearLayout viewPare = (LinearLayout) view.getParent();
        //全体の親レイアウト
        LinearLayout viewPPare = (LinearLayout) viewPare.getParent();

        taskSetting = Arrays.asList(idList).indexOf(viewPPare.getId());


        ShowDialogView();
        flagTime = "timeEE";
        flagDate = "timeEE";

    }

    /**
     * 予定追加ボタン押下時
     */
    public void selectTaskSetBtn(View view) {
        //データ挿入
        InsertTask();
    }

    /**
     * DBインサート文
     */
    @SuppressLint("DefaultLocale")
    private void InsertTask() {
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


        int test = dateSS.compareTo(dateES);
        int testSub = timeSS.compareTo(timeES);
        /*
          日付判定
          時刻が開始＞終了かつ日付が開始＜終了または
         */
        if ((testSub < 0 && test <= 0) || (testSub >= 0 && test < 0)) {
            Log.d("debug", "正常に処理");
            /*
              if true　いずれかのエディットに入力されていない
              false 正常
             */
            if (TextUtils.isEmpty(taskNameS) ||
                    TextUtils.isEmpty(dateSS) ||
                    TextUtils.isEmpty(timeSS) ||
                    TextUtils.isEmpty(dateES) ||
                    TextUtils.isEmpty(timeES)) {
                Log.d("debug", "null値判定,いずれかの値が空欄です");
            } else {
                values.put("startday", dateSS);
                values.put("starttime", timeSS);
                values.put("endday", dateES);
                values.put("endtime", timeES);
                values.put("task", taskNameS);
                Log.d("debug", "**********" + values);
                db.insert("tastdb", null, values);
                CalendarLayout.setVisibility(View.VISIBLE);
                TaskLayout.setVisibility(View.INVISIBLE);

                taskName.setText("");
                Calendar c = Calendar.getInstance();
                timeS.setText(String.format("%02d時%02d分", c.get(Calendar.HOUR), c.get(Calendar.MINUTE)));
                dateS.setText(String.format("%d年%02d月%02d日", c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1, c.get(Calendar.DAY_OF_MONTH)));

                timeE.setText(String.format("%02d時%02d分", c.get(Calendar.HOUR) + 1, c.get(Calendar.MINUTE)));
                dateE.setText(String.format("%d年%02d月%02d日", c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1, c.get(Calendar.DAY_OF_MONTH)));

                int i = Integer.parseInt(dateSS.substring(dateSS.indexOf("年") + 1, dateSS.indexOf("月")));
                checkLoad[i] = false;

                Log.d("debug", "旧配列数1=" + taskLayoutNum[0].length);
                Log.d("debug", "旧配列数2=" + taskLayoutNum.length);
                //空の配列opferを作成し既存の配列をコピーする
                int[][] opfer = new int[taskLayoutNum.length + 1][12];
                for (int d = 0; d < taskLayoutNum.length; d++) {
                    System.arraycopy(taskLayoutNum[d], 0, opfer[d], 0, taskLayoutNum[d].length);
                }
                //コピー＆要素追加したものを新規作成する。
                taskLayoutNum = new int[opfer.length][];
                for (int d = 0; d < opfer.length; d++) {
                    taskLayoutNum[d] = new int[opfer[d].length];
                    System.arraycopy(opfer[d], 0, taskLayoutNum[d], 0, opfer[d].length);
                }


                Log.d("debug", "新配列数1=" + taskLayoutNum[0].length);
                Log.d("debug", "新配列数2=" + taskLayoutNum.length);

                Log.d("debug", "旧idlist=" + idList.length);
                int b = idList.length;
                idList = Arrays.copyOf(idList, b + 1);
                Log.d("debug", "新idlist=" + idList.length);
            }
        } else {
            Log.d("debug", "日付に矛盾が発生");
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
                "tastdb",
                new String[]{"startday", "starttime", "endday", "endtime", "task"},
                "startday=?",
                new String[]{message},
                null,
                null,
                null
        );
        cursor.moveToFirst();

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
        Spinner spinner = findViewById(R.id.spinner);
        String item = (String) spinner.getSelectedItem();
        int id;

        //指定した月を条件にDBからその月の予定を一件ずつ呼び出す
        Cursor cursor = db.query(
                "tastdb",
                new String[]{"_id", "startday", "starttime", "endday", "endtime", "task"},
                "startday LIKE ?",
                new String[]{"2022年" + item + "%"},
                null,
                null,
                "startday"
        );
        cursor.moveToFirst();


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
            if (!checkLoad[month]) {
                //RowData一件分のデータの一意識別ようIDをジェネレート
                idList[id] = ViewCompat.generateViewId();
                //ジェネレートしたIDを格納
                taskLayoutNum[id][0] = idList[id];
            }

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

            //予定内容
            data.setTaskName(cursor.getString(5));

            // true 選択した月が未表示の場合or予定追加時
            // false 選択した月がすでに表示済みの場合
            if (!checkLoad[month]) {
                for (int count = 1; count < 11; count++) {
                    taskLayoutNum[id][count] = ViewCompat.generateViewId();
                    Log.d("debug", "testId挿入=" + taskLayoutNum[id][count]);
                }
            } else {
                Log.d("debug", "生成済み459");
            }
            //EdittextビューにIDを格納及びIDを配列で保存

            data.setIdSDay1(taskLayoutNum[id][1]);
            data.setIdSDay2(taskLayoutNum[id][2]);
            data.setIdSTime1(taskLayoutNum[id][3]);
            data.setIdSTime2(taskLayoutNum[id][4]);
            data.setIdEDay1(taskLayoutNum[id][5]);
            data.setIdEDay2(taskLayoutNum[id][6]);
            data.setIdETime1(taskLayoutNum[id][7]);
            data.setIdETime2(taskLayoutNum[id][8]);
            data.setIdYear(taskLayoutNum[id][9]);
            data.setIdTask(taskLayoutNum[id][10]);

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
        LinearLayout childLO = (LinearLayout) pare.getParent();

        //開始表示
        childLO.getChildAt(0).setVisibility(View.GONE);
        //終了表示
        childLO.getChildAt(1).setVisibility(View.GONE);
        //タスク表示
        childLO.getChildAt(2).setVisibility(View.GONE);

        //編集表示
        childLO.getChildAt(3).setVisibility(View.VISIBLE);
        childLO.getChildAt(4).setVisibility(View.VISIBLE);
        childLO.getChildAt(5).setVisibility(View.VISIBLE);
        childLO.getChildAt(6).setVisibility(View.VISIBLE);
        childLO.getChildAt(7).setVisibility(View.VISIBLE);

        LinearLayout child1 = (LinearLayout) childLO.getChildAt(9);
        child1.getChildAt(2).setVisibility(View.VISIBLE);
        child1.getChildAt(4).setVisibility(View.VISIBLE);

    }

    /**
     * 予定編集モード終了
     */
    public void onExit(View view) {
        LinearLayout pare = (LinearLayout) view.getParent();
        LinearLayout childLO = (LinearLayout) pare.getParent();

        //開始表示
        childLO.getChildAt(0).setVisibility(View.VISIBLE);
        //終了表示
        childLO.getChildAt(1).setVisibility(View.VISIBLE);
        //タスク表示
        childLO.getChildAt(2).setVisibility(View.VISIBLE);

        //編集表示
        childLO.getChildAt(3).setVisibility(View.GONE);
        childLO.getChildAt(4).setVisibility(View.GONE);
        childLO.getChildAt(5).setVisibility(View.GONE);
        childLO.getChildAt(6).setVisibility(View.GONE);
        childLO.getChildAt(7).setVisibility(View.GONE);

        LinearLayout child1 = (LinearLayout) childLO.getChildAt(9);
        child1.getChildAt(2).setVisibility(View.INVISIBLE);
        child1.getChildAt(4).setVisibility(View.INVISIBLE);
    }

    /**
     * 編集内容確定
     * 確定ボタン押下時のEditTextから編集した内容を取り出す。
     * DBに入れるためにデータの型を整形してDBに入れる。
     */
    public void onUpdate(View view) {
        //確定ボタンの親レイアウト
        LinearLayout viewPare = (LinearLayout) view.getParent();
        //全体の親レイアウト
        LinearLayout viewPPare = (LinearLayout) viewPare.getParent();

        //編集するIDを親レイアウトのIDから取得しidListから検索
        int id = Arrays.asList(idList).indexOf(viewPPare.getId());

        EditText year = findViewById(taskLayoutNum[id][9]);
        EditText startMonth = findViewById(taskLayoutNum[id][1]);
        EditText startDay = findViewById(taskLayoutNum[id][2]);
        //開始月日取得
        String startMD = year.getText() + "年" + startMonth.getText() + "月" + startDay.getText() + "日";


        EditText startHours = findViewById(taskLayoutNum[id][3]);
        EditText startMin = findViewById(taskLayoutNum[id][4]);
        //開始時刻取得
        String startHM = startHours.getText() + "時" + startMin.getText() + "分";


        EditText endMonth = findViewById(taskLayoutNum[id][5]);
        EditText endDay = findViewById(taskLayoutNum[id][6]);
        //終了月日取得
        String endMD = year.getText() + "年" + endMonth.getText() + "月" + endDay.getText() + "日";


        EditText endHours = findViewById(taskLayoutNum[id][7]);
        EditText endMin = findViewById(taskLayoutNum[id][8]);
        //終了時刻取得
        String endHM = endHours.getText() + "時" + endMin.getText() + "分";


        EditText taskText = findViewById(taskLayoutNum[id][10]);
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
                values.put("endday",endMD);
                values.put("endtime", endHM);
                values.put("task", task);


                SQLiteDatabase db = selectDB.getWritableDatabase();
                db.update("tastdb", values, "_id = " + id, null);

                View pan = findViewById(R.id.listMonthSelectBtn);
                ListTask(pan);
            }
        }else {
            Log.d("debug", "日付に矛盾が発生");
        }
    }

    /**
     * 予定一件を削除
     */
    public void onDelete(View view) {
        //確定ボタンの親レイアウト
        LinearLayout viewPare = (LinearLayout) view.getParent();
        //全体の親レイアウト
        LinearLayout viewPPare = (LinearLayout) viewPare.getParent();

        int viewId = viewPPare.getId();

        SQLiteDatabase db = selectDB.getWritableDatabase();

        int i = Arrays.asList(idList).indexOf(viewId);
//        for (int a = 0;a < idList.length;a++){
//            Log.d("dubug","リスト出力ID ￥＝"+idList[a]);
//        }
//        Log.d("dubug","idlistの格納インデックス番号 ￥＝"+i);
//        Log.d("dubug","おやID ￥＝"+viewId);
        db.delete("tastdb", "_id = " + i, null);

//        Log.d("dubug","削除成功判定ID ￥＝"+ewt);
        View pan = findViewById(R.id.listMonthSelectBtn);
        ListTask(pan);
    }

    /**
     * 時刻・月日ダイアログ表示
     */
    public void ShowDialogView() {
        /*
          newFragment.show(getSupportFragmentManager(), "timePicker");
         */
        TimePickerFragment timePicker = new TimePickerFragment();
        timePicker.show(getSupportFragmentManager(), "timePicker");
        DatePickerFragment datePicker = new DatePickerFragment();
        datePicker.show(getSupportFragmentManager(), "datePicker");

    }

    /**
     * 時刻セット
     */
    @SuppressLint("DefaultLocale")
    @Override
    public void onTimeSet(TimePicker timePicker, int hour, int minu) {
        if (flagTime =="timeE") {
            timeE.setText(String.format("%02d時%02d分", hour, minu));
        } else if (flagTime =="timeS"){
            timeS.setText(String.format("%02d時%02d分", hour, minu));
        }else if (flagTime == "timeSS"){
            EditText startHours = findViewById(taskLayoutNum[taskSetting][3]);
            EditText startMin = findViewById(taskLayoutNum[taskSetting][4]);


            startHours.setText(String.format("%02d",hour));
            startMin.setText(String.format("%02d",minu));
        }else if (flagTime == "timeEE"){
            EditText endHours = findViewById(taskLayoutNum[taskSetting][7]);
            EditText endMin = findViewById(taskLayoutNum[taskSetting][8]);

            endHours.setText(String.format("%02d",hour));
            endMin.setText(String.format("%02d",minu));
        }


        flagTime=null;
    }

    /**
     * 月日セット
     */
    @SuppressLint("DefaultLocale")
    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        if (flagDate =="timeE") {
            dateE.setText(String.format("%d年%02d月%02d日", year, month + 1, dayOfMonth));
        } else if (flagDate =="timeS"){
            dateS.setText(String.format("%d年%02d月%02d日", year, month + 1, dayOfMonth));
        }else if (flagTime == "timeSS"){
            EditText yearH = findViewById(taskLayoutNum[taskSetting][9]);
            EditText startMonth = findViewById(taskLayoutNum[taskSetting][1]);
            EditText startDay = findViewById(taskLayoutNum[taskSetting][2]);

            yearH.setText(String.format("%d",year));
            startMonth.setText(String.format("%02d",month+1));
            startDay.setText(String.format("%02d",dayOfMonth));
        }else if (flagTime == "timeEE"){
            EditText endMonth = findViewById(taskLayoutNum[taskSetting][5]);
            EditText endDay = findViewById(taskLayoutNum[taskSetting][6]);

            endMonth.setText(String.format("%02d",month+1));
            endDay.setText(String.format("%02d",dayOfMonth));
        }

        flagDate=null;
    }

    /**
     * デバッグ用　DBデータセット（日にち）
     */
    public void dataSet(View view) {

        SQLiteDatabase db = selectDB.getWritableDatabase();

        selectDB.saveData(db, "2022年01月12日", "12時30分", "2022年01月12日", "13時40分", "肉まん1");
        selectDB.saveData(db, "2022年01月12日", "12時30分", "2022年01月12日", "13時40分", "肉まん12");
        selectDB.saveData(db, "2022年01月12日", "12時30分", "2022年01月12日", "13時40分", "肉まん1");
        selectDB.saveData(db, "2022年01月12日", "12時30分", "2022年01月12日", "13時40分", "肉まん12");
        selectDB.saveData(db, "2022年01月12日", "12時30分", "2022年01月12日", "13時40分", "肉まん1");
        selectDB.saveData(db, "2022年01月12日", "12時30分", "2022年01月12日", "13時40分", "肉まん12");
        selectDB.saveData(db, "2022年02月12日", "12時30分", "2022年02月12日", "13時40分", "肉まん123");
        selectDB.saveData(db, "2022年02月12日", "12時30分", "2022年02月12日", "13時40分", "肉まん1234");
        selectDB.saveData(db, "2022年02月12日", "12時30分", "2022年02月12日", "13時40分", "肉まん123");
        selectDB.saveData(db, "2022年02月12日", "12時30分", "2022年02月12日", "13時40分", "肉まん1234");
        selectDB.saveData(db, "2022年02月12日", "12時30分", "2022年02月12日", "13時40分", "肉まん123");
        selectDB.saveData(db, "2022年02月12日", "12時30分", "2022年02月12日", "13時40分", "肉まん1234");
        selectDB.saveData(db, "2022年03月12日", "12時30分", "2022年03月12日", "13時40分", "肉まん1234");
        selectDB.saveData(db, "2022年03月12日", "12時30分", "2022年03月12日", "13時40分", "肉まん1234");
        selectDB.saveData(db, "2022年04月12日", "12時30分", "2022年04月12日", "13時40分", "肉まん1234");
        selectDB.saveData(db, "2022年04月12日", "12時30分", "2022年04月12日", "13時40分", "肉まん1234");
        selectDB.saveData(db, "2022年05月12日", "12時30分", "2022年05月12日", "13時40分", "肉まん1234");
        selectDB.saveData(db, "2022年05月12日", "12時30分", "2022年05月12日", "13時40分", "肉まん1234");
    }

}