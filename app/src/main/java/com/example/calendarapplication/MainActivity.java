package com.example.calendarapplication;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity implements  TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {
    private EditText taskName;
    private EditText dateS;
    private EditText timeS;
    private EditText dateE;
    private EditText timeE;
    private boolean flag;
    private TextView taskView;
    private DataBase selectDB;
    private WebView gameView;

    InputMethodManager inputMethodManager;
    private LinearLayout layerTask;

    private final String[] spinnerItems = {"01月", "02月", "03月", "04月", "05月", "06月",
            "07月", "08月", "09月", "10月", "11月", "12月"};




    @SuppressLint({"DefaultLocale", "SetJavaScriptEnabled", "ClickableViewAccessibility"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gameView = (WebView)findViewById(R.id.gameWeb);
        gameView.setWebViewClient(new WebViewClient());
        gameView.setClickable(true);
        gameView.setEnabled(true);
        gameView.getSettings().setJavaScriptEnabled(true);
        gameView.loadUrl("file:///android_asset/testCout.html");

        gameView.setOnTouchListener((view, motionEvent) -> (motionEvent.getAction() == MotionEvent.ACTION_MOVE));

        flag = false;
        taskName = findViewById(R.id.taskName);
        dateS = findViewById(R.id.dateS);
        timeS = findViewById(R.id.timeS);
        dateE = findViewById(R.id.dateE);
        timeE = findViewById(R.id.timeE);
        taskView = findViewById(R.id.TaskView);

        selectDB = new DataBase(getApplicationContext());

        inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        layerTask = findViewById(R.id.layerTask);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                spinnerItems
        );

        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        Spinner spinner = findViewById(R.id.spinner);
        spinner.setAdapter(adapter);

        //予定一覧画面

        /*
        テスト：カレンダーの標識確認
         */
        CalendarView calendar = findViewById(R.id.calendar);
        calendar.setOnDateChangeListener(
                (calendarView, year, month, date) -> {
                    String message = (String.format("%d年%02d月%02d日",year,month+1,date));
                    Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
                    taskDaySelect(message,taskView);
                }

        );

        Calendar c = Calendar.getInstance();
        timeS.setText(String.format("%02d時%02d分",c.get(Calendar.HOUR),c.get(Calendar.MINUTE)));
        dateS.setText(String.format("%d年%02d月%02d日", c.get(Calendar.YEAR), c.get(Calendar.MONTH)+1, c.get(Calendar.DAY_OF_MONTH)));

        timeE.setText(String.format("%02d時%02d分",c.get(Calendar.HOUR)+1,c.get(Calendar.MINUTE)));
        dateE.setText(String.format("%d年%02d月%02d日", c.get(Calendar.YEAR), c.get(Calendar.MONTH)+1, c.get(Calendar.DAY_OF_MONTH)));
    }

    /**
     * キーボード非表示（フォーカス外タッチ時）
     */
    @Override
    public boolean onTouchEvent(MotionEvent event){
        inputMethodManager.hideSoftInputFromWindow(layerTask.getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
        layerTask.requestFocus();

        return true;
    }


    /**
     * ホーム画面表示
     */
    public void openHome(View view){
        LinearLayout CalendarLayout = (LinearLayout) findViewById(R.id.layerCalendar);
        LinearLayout TaskLayout = (LinearLayout) findViewById(R.id.layerTask);
        LinearLayout TaskListLayout = (LinearLayout) findViewById(R.id.layerTaskList);

        CalendarLayout.setVisibility(View.VISIBLE);
        TaskLayout.setVisibility(View.GONE);
        TaskListLayout.setVisibility(View.GONE);

        /*
         * JavaScriptにゲームキャラクターのモーション操作を指示する
         */
        gameView.loadUrl("javascript:tara()");
    }

    /**
     * 予定追加画面表示
     */
    public void openTaskAdd(View view){
        LinearLayout CalendarLayout = (LinearLayout) findViewById(R.id.layerCalendar);
        LinearLayout TaskLayout = (LinearLayout) findViewById(R.id.layerTask);
        LinearLayout TaskListLayout = (LinearLayout) findViewById(R.id.layerTaskList);

        CalendarLayout.setVisibility(View.INVISIBLE);
        TaskLayout.setVisibility(View.VISIBLE);
        TaskListLayout.setVisibility(View.INVISIBLE);
    }

    /**
     * 予定一覧画面表示
     */
    public void openTaskView(View view){

        LinearLayout CalendarLayout = (LinearLayout) findViewById(R.id.layerCalendar);
        LinearLayout TaskLayout = (LinearLayout) findViewById(R.id.layerTask);
        LinearLayout TaskListLayout = (LinearLayout) findViewById(R.id.layerTaskList);

        CalendarLayout.setVisibility(View.GONE);
        TaskLayout.setVisibility(View.GONE);
        TaskListLayout.setVisibility(View.VISIBLE);

        /*
         * JavaScriptにゲームキャラクターのモーション操作を指示する
         */
        gameView.loadUrl("javascript:tako()");

        ListTask(view);
    }

    /**
     *　開始日時選択ボタン表示
     */
    public void selectTimeStartBtn(View view){
        ShowDialogView(timeS);
        flag = true;
    }

    /**
     * 終了日時選択ボタン表示
     */
    public void selectTimeEndBtn(View view){
        ShowDialogView(timeE);
        flag = false;
    }

    /**
     * 予定追加ボタン押下時
     */
    public void selectTaskSetBtn(View view){
        //データ挿入
        InsertTask();
    }

    /**
     * DBインサート文
     */
    private void InsertTask() {
        LinearLayout CalendarLayout = (LinearLayout) findViewById(R.id.layerCalendar);
        LinearLayout TaskLayout = (LinearLayout) findViewById(R.id.layerTask);

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
        if((testSub<0&&test<=0)||(testSub>=0&&test<0)){
            Log.d("debug","正常に処理");
            /*
              if true　いずれかのエディットに入力されていない
              false 正常
             */
            if (TextUtils.isEmpty(taskNameS) ||
                    TextUtils.isEmpty(dateSS) ||
                    TextUtils.isEmpty(timeSS) ||
                    TextUtils.isEmpty(dateES) ||
                    TextUtils.isEmpty(timeES)){
                Log.d("debug","null値判定");
            }else {
                values.put("startday",dateSS);
                values.put("starttime",timeSS);
                values.put("endday",dateES);
                values.put("endtime",timeES);
                values.put("task",taskNameS);
                Log.d("debug","**********"+values);
                db.insert("tastdb",null,values);
                CalendarLayout.setVisibility(View.VISIBLE);
                TaskLayout.setVisibility(View.INVISIBLE);

                taskName.setText("");
                Calendar c = Calendar.getInstance();
                timeS.setText(String.format("%02d時%02d分",c.get(Calendar.HOUR),c.get(Calendar.MINUTE)));
                dateS.setText(String.format("%d年%02d月%02d日", c.get(Calendar.YEAR), c.get(Calendar.MONTH)+1, c.get(Calendar.DAY_OF_MONTH)));

                timeE.setText(String.format("%02d時%02d分",c.get(Calendar.HOUR)+1,c.get(Calendar.MINUTE)));
                dateE.setText(String.format("%d年%02d月%02d日", c.get(Calendar.YEAR), c.get(Calendar.MONTH)+1, c.get(Calendar.DAY_OF_MONTH)));

            }
        }else{
            Log.d("debug","日付に矛盾が発生");
        }



        Log.d("debug","日付判定日にち"+test);
        Log.d("debug","日付判定時刻"+testSub);

    }

    /**
     * カレンダーの日にちを選択時、予定しているタスクを表示
     */
    private void taskDaySelect(String message, TextView taskView) {
        SQLiteDatabase db = selectDB.getReadableDatabase();
        Cursor cursor = db.query(
                "tastdb",
                new String[]{"startday","starttime","endday","endtime","task"},
                "startday=?",
                new String[]{message},
                null,
                null,
                null
        );
        cursor.moveToFirst();

        StringBuilder subuilder = new StringBuilder();

        for (int i = 0; i < cursor.getCount(); i++){
            subuilder.append(cursor.getString(0));
            subuilder.append(cursor.getString(1));
            subuilder.append("～");
            subuilder.append(cursor.getString(2));
            subuilder.append(cursor.getString(3));
            subuilder.append("\n\n");
            subuilder.append(cursor.getString(4));
            subuilder.append("\n\n");
            cursor.moveToNext();
        }
        Log.d("debug","**********"+subuilder);
        cursor.close();
        taskView.setText(subuilder.toString());
    }

    /**
     * 月別予定一覧表示
     */
    public void ListTask(View view) {

        RecyclerView rv = (RecyclerView)findViewById(R.id.RecycleView);
        CasarealRecycleViewAdapter adapter = new CasarealRecycleViewAdapter(this.createDataset());

        LinearLayoutManager llm = new LinearLayoutManager(this);

        rv.setHasFixedSize(true);
        rv.setLayoutManager(llm);
        rv.setAdapter(adapter);

//        LinearLayout insertLayout = (LinearLayout) findViewById(R.id.taskInsert);
//        insertLayout.removeAllViews();
//        SQLiteDatabase db = selectDB.getReadableDatabase();
//        Spinner spinner = findViewById(R.id.spinner);
//        String item = (String)spinner.getSelectedItem();
//
//        //幅調整（未完）
////        insertLayout.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
//        Cursor cursor = db.query(
//                "tastdb",
//                new String[]{"_id","startday","starttime","task"},
//                "startday LIKE ?",
//                new String[]{"2022年"+item+"%"},
//                null,
//                null,
//                "startday"
//        );
//        cursor.moveToFirst();
//        /*
//        データ抽出語のデータを一意に識別するよう配列で格納
//         */
//        int[] array;
//
//        int id = 10;
//        for (int i = 0; i < cursor.getCount(); i++){
//            final TableRow tr = new TableRow(this);
//            tr.setLayoutParams(new TableRow.LayoutParams(
//                    TableRow.LayoutParams.MATCH_PARENT,
//                    TableRow.LayoutParams.MATCH_PARENT));
//            //開始時刻
//            TextView TView1 = new TextView(this);
//            TextView TView2 = new TextView(this);
//            TextView TView3 = new TextView(this);
//            TextView TView4 = new TextView(this);
//            Button TaskEdit = new Button(this);
//
//            TableRow.LayoutParams textLayoutParams = new TableRow.LayoutParams(
//                    TableRow.LayoutParams.WRAP_CONTENT,
//                    TableRow.LayoutParams.WRAP_CONTENT
//            );
//
//            TView1.setText(cursor.getString(1));
//            tr.addView(TView1,textLayoutParams);
//            TView2.setText(cursor.getString(2));
//            tr.addView(TView2,textLayoutParams);
//            TView4.setText(cursor.getString(3));
//            tr.addView(TView4,textLayoutParams);
////            TView2.setLayoutParams(new ViewGroup.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,TableRow.LayoutParams.WRAP_CONTENT));
//            //Gravity(右詰め予定)
////            startTimeView.setGravity(Gravity.RIGHT);
//
////            編集用ボタン生成
////            TaskEdit.setOnClickListener(updateDB);
////            tr.addView(TaskEdit,new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));
//

//            //予定クリック時に遷移
//            tr.setOnClickListener(testDebug);
//            insertLayout.addView(tr);
//
//            cursor.moveToNext();
//        }
//        cursor.close();
    }

    /**
     * DBより月別の予定を取得
     * @return
     */
    private List<RowData> createDataset() {
        List<RowData> dataset = new ArrayList<>();

        SQLiteDatabase db = selectDB.getReadableDatabase();
        Spinner spinner = findViewById(R.id.spinner);
        String item = (String)spinner.getSelectedItem();
        int id =0;


        Cursor cursor = db.query(
                "tastdb",
                new String[]{"_id","startday","starttime","endtime","task"},
                "startday LIKE ?",
                new String[]{"2022年"+item+"%"},
                null,
                null,
                "startday"
        );
        cursor.moveToFirst();


        for (int i = 0; i < cursor.getCount(); i++) {
            RowData data = new RowData();
            data.setStartDay(cursor.getString(1));
            data.setStartTime(cursor.getString(2));
            data.setEndTime(cursor.getString(3));
            data.setTaskName(cursor.getString(4));

            data.setSDay1(cursor.getString(1).substring(cursor.getString(1).indexOf("年")+1,cursor.getString(1).indexOf("月")));
            data.setSDay2(cursor.getString(1).substring(cursor.getString(1).indexOf("月")+1,cursor.getString(1).indexOf("日")));

            data.setSTime1(cursor.getString(2).substring(0,cursor.getString(2).indexOf("時")));
            data.setSTime2(cursor.getString(2).substring(cursor.getString(2).indexOf("時")+1,cursor.getString(3).indexOf("分")));
            data.setETime1(cursor.getString(3).substring(0,cursor.getString(3).indexOf("時")));
            data.setETime2(cursor.getString(3).substring(cursor.getString(3).indexOf("時")+1,cursor.getString(3).indexOf("分")));


            //DBの"_id"からID付与
            id = Integer.parseInt(cursor.getString(0));
            data.setId(id);

            dataset.add(data);
            cursor.moveToNext();
        }
        cursor.close();
        return dataset;
    }

    //予定編集モード移行
    public void selectUpdate(View view){
        LinearLayout pare =(LinearLayout)view.getParent();
        LinearLayout childLO = (LinearLayout)pare.getParent();

        childLO.getChildAt(0).setVisibility(View.GONE);
        childLO.getChildAt(1).setVisibility(View.VISIBLE);

        childLO.getChildAt(2).setVisibility(View.GONE);
        childLO.getChildAt(3).setVisibility(View.VISIBLE);

        childLO.getChildAt(4).setVisibility(View.GONE);
        childLO.getChildAt(5).setVisibility(View.VISIBLE);

        LinearLayout child1 = (LinearLayout)childLO.getChildAt(7);
        child1.getChildAt(2).setVisibility(View.VISIBLE);

    }

    //編集内容確定
    public  void onUpdate(View view){
        //確定ボタンの親レイアウト
        LinearLayout viewPare = (LinearLayout)view.getParent();
        //全体の親レイアウト
        LinearLayout viewPPare = (LinearLayout)viewPare.getParent();
        //開始月日のレイアウト
        LinearLayout startDayLayout = (LinearLayout)viewPPare.getChildAt(1);
        //開始・終了時刻レイアウト
        LinearLayout timeLayout = (LinearLayout)viewPPare.getChildAt(3);
        //タスクレイアウト
        LinearLayout taskLayout = (LinearLayout)viewPPare.getChildAt(5);

        EditText startMonth = (EditText) startDayLayout.getChildAt(0);
        EditText startDay = (EditText) startDayLayout.getChildAt(2);

        //開始時刻取得
        String startMD = String.format(startMonth.getText()+"月"+startDay.getText()+"日");

        EditText startHours = (EditText) timeLayout.getChildAt(0);
        EditText startMin = (EditText) timeLayout.getChildAt(2);

        String startHM = String.format(startHours.getText()+"時"+startMin.getText()+"分");



        SQLiteDatabase db = selectDB.getReadableDatabase();


        Log.d("debug","　"+viewPPare.getId()+"開始月日"+startMD+"開始時刻"+startHM);
    }

    //デバッグ用（メッセージ表示）
    private View.OnClickListener testDebug = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            int id = view.getId();
            Log.d("debug","testMessage"+id);
        }
    };

    /**
     * 時刻・月日ダイアログ表示
     */
    public void ShowDialogView(View timeS) {
        /*
          newFragment.show(getSupportFragmentManager(), "timePicker");
         */
        TimePickerFragment timePicker = new TimePickerFragment();
        timePicker.show(getSupportFragmentManager(),"timePicker");
        DatePickerFragment datePicker = new DatePickerFragment();
        datePicker.show(getSupportFragmentManager(),"datePicker");

    }

    /**
     * 時刻セット
     */
    @SuppressLint("DefaultLocale")
    @Override
    public void onTimeSet(TimePicker timePicker, int hour, int minu) {
        if (!flag){
            timeE.setText(String.format("%02d時%02d分",hour,minu));
        }else{
            timeS.setText(String.format("%02d時%02d分",hour,minu));
        }

    }

    /**
     * 月日セット
     */
    @SuppressLint("DefaultLocale")
    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        if(!flag){
            dateE.setText(String.format("%d年%02d月%02d日", year, month + 1, dayOfMonth));
        }else{
            dateS.setText(String.format("%d年%02d月%02d日", year, month + 1, dayOfMonth));
        }
    }


}