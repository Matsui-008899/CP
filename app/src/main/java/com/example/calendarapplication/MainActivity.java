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
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
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

    private int[][] taskLayoutNum;
    private int[] idList;
    private boolean[] checkLoad;




    @SuppressLint({"DefaultLocale", "SetJavaScriptEnabled", "ClickableViewAccessibility"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gameView = findViewById(R.id.gameWeb);
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

        checkLoad = new boolean[13];

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


        //DB->ID格納
        SQLiteDatabase db = selectDB.getReadableDatabase();
        Cursor cursor = db.query(
                "tastdb",
                new String[]{"_id"},
                null,
                null,
                null,
                null,
                null
        );
        cursor.moveToFirst();
        idList = new int[cursor.getCount()+1];
        taskLayoutNum = new int[cursor.getCount()+1][9];
        cursor.close();

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
        LinearLayout CalendarLayout = findViewById(R.id.layerCalendar);
        LinearLayout TaskLayout = findViewById(R.id.layerTask);
        LinearLayout TaskListLayout = findViewById(R.id.layerTaskList);

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
        LinearLayout CalendarLayout = findViewById(R.id.layerCalendar);
        LinearLayout TaskLayout = findViewById(R.id.layerTask);
        LinearLayout TaskListLayout = findViewById(R.id.layerTaskList);

        CalendarLayout.setVisibility(View.INVISIBLE);
        TaskLayout.setVisibility(View.VISIBLE);
        TaskListLayout.setVisibility(View.INVISIBLE);
    }

    /**
     * 予定一覧画面表示
     */
    public void openTaskView(View view){

        LinearLayout CalendarLayout = findViewById(R.id.layerCalendar);
        LinearLayout TaskLayout = findViewById(R.id.layerTask);
        LinearLayout TaskListLayout = findViewById(R.id.layerTaskList);

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
                Log.d("debug","null値判定,いずれかの値が空欄です");
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

                int i = Integer.parseInt(dateSS.substring(dateSS.indexOf("年")+1,dateSS.indexOf("月")));
                checkLoad[i]  =false;

                Log.d("debug","旧配列数1="+taskLayoutNum[0].length);
                Log.d("debug","旧配列数2="+taskLayoutNum.length);
                //空の配列opferを作成し既存の配列をコピーする
                int[][] opfer = new int[taskLayoutNum.length+1][9];
                for (int d=0;d<taskLayoutNum.length;d++){
                    for (int h=0;h<taskLayoutNum[d].length;h++){
                        opfer[d][h] = taskLayoutNum[d][h];
                    }
                }
                //コピー＆要素追加したものを新規作成する。
                taskLayoutNum = new int[opfer.length][];
                for (int d=0;d<opfer.length;d++){
                    taskLayoutNum[d] = new int[opfer[d].length];
                    for (int h=0;h<opfer[d].length;h++){
                        taskLayoutNum[d][h] = opfer[d][h];
                    }
                }


                Log.d("debug","新配列数1="+taskLayoutNum[0].length);
                Log.d("debug","新配列数2="+taskLayoutNum.length);

                int b = idList.length;
                idList = Arrays.copyOf(idList,b+1);
            }
        }else{
            Log.d("debug","日付に矛盾が発生");
        }



        Log.d("debug","日付判定日にち"+test+"\n日付判定時刻"+testSub);

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
            subuilder.append("開始月日："+cursor.getString(0));
            subuilder.append("\n                                ");
            subuilder.append("開始時刻："+cursor.getString(1));
            subuilder.append("\n終了月日："+cursor.getString(2));
            subuilder.append("\n                                ");
            subuilder.append("終了時刻："+cursor.getString(3));
            subuilder.append("\n");
            subuilder.append("予定内容："+cursor.getString(4));
            subuilder.append("\n\n");
            cursor.moveToNext();
        }
        //Log.d("debug","**********"+subuilder);
        cursor.close();
        taskView.setText(subuilder.toString());
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
    private List<RowData> createDataset() {
        /*
        プリセット（Row.xml）に入れる予定のgetter,setter呼びだし
         */
        List<RowData> dataset = new ArrayList<>();

        //DB呼びだし
        SQLiteDatabase db = selectDB.getReadableDatabase();
        Spinner spinner = findViewById(R.id.spinner);
        String item = (String)spinner.getSelectedItem();
        int id;

        //指定した月を条件にDBからその月の予定を一件ずつ呼び出す
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


        int month = Integer.parseInt(item.substring(0,item.indexOf("月")));
        Log.d("debug","月true="+ checkLoad[month]);

            for (int i = 0; i < cursor.getCount(); i++) {
                int count = 0;
                RowData data = new RowData();
                id = Integer.parseInt(cursor.getString(0));
                //配列にidの番号で格納
                // true 選択した月が未表示の場合or予定追加時
                // false 選択した月がすでに表示済みの場合
                if (checkLoad[month] == false){
                    //RowData一件分のデータの一意識別ようIDをジェネレート
                    idList[id] = ViewCompat.generateViewId();
                    //ジェネレートしたIDを格納
                    taskLayoutNum[id][count] = idList[id];
                }
                count = count++;

                //data(setter,getter)にプリセット(Row.xml)に入れる
                // データ(開始月日、開始時刻、終了時刻etc...)をDBから抽出し挿入
                data.setStartDay(cursor.getString(1));
                data.setStartTime(cursor.getString(2));
                data.setEndTime(cursor.getString(3));
                data.setTaskName(cursor.getString(4));

                //例 ２０２２年｜１０｜月１１日
                data.setSDay1(cursor.getString(1).substring(
                        cursor.getString(1).indexOf("年")+1,cursor.getString(1).indexOf("月")));
                //例 ２０２２年１０月｜１１｜日
                data.setSDay2(cursor.getString(1).substring(
                        cursor.getString(1).indexOf("月")+1,cursor.getString(1).indexOf("日")));

                //例　｜１０｜時１１分
                data.setSTime1(cursor.getString(2).substring(0,cursor.getString(2).indexOf("時")));
                //例　１０時｜１１｜分
                data.setSTime2(cursor.getString(2).substring(cursor.getString(2).indexOf("時")+1,cursor.getString(3).indexOf("分")));
                data.setETime1(cursor.getString(3).substring(0,cursor.getString(3).indexOf("時")));
                data.setETime2(cursor.getString(3).substring(cursor.getString(3).indexOf("時")+1,cursor.getString(3).indexOf("分")));

                //例 ｜２０２２｜年１０月１１日
                data.setYear(cursor.getString(1).substring(0,cursor.getString(1).indexOf("年")));

                //予定内容
                data.setTaskName(cursor.getString(4));

                // true 選択した月が未表示の場合or予定追加時
                // false 選択した月がすでに表示済みの場合
                if (checkLoad[month] == false){
                    for (count = 1;count <9;count++){
                        taskLayoutNum[id][count] = ViewCompat.generateViewId();
                        Log.d("debug","testId挿入="+ taskLayoutNum[id][count]);
                    }
                }else{
                    Log.d("debug","生成済み459");
                }
                //EdittextビューにIDを格納及びIDを配列で保存

                data.setIdSDay1(taskLayoutNum[id][1]);
                data.setIdSDay2(taskLayoutNum[id][2]);
                data.setIdSTime1(taskLayoutNum[id][3]);
                data.setIdSTime2(taskLayoutNum[id][4]);
                data.setIdETime1(taskLayoutNum[id][5]);
                data.setIdETime2(taskLayoutNum[id][6]);
                data.setIdYear(taskLayoutNum[id][7]);
                data.setIdTask(taskLayoutNum[id][8]);

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

    /**
     * 予定編集モード終了
     */
    public void onExit(View view){
        LinearLayout pare =(LinearLayout)view.getParent();
        LinearLayout childLO = (LinearLayout)pare.getParent();

        childLO.getChildAt(0).setVisibility(View.VISIBLE);
        childLO.getChildAt(1).setVisibility(View.GONE);

        childLO.getChildAt(2).setVisibility(View.VISIBLE);
        childLO.getChildAt(3).setVisibility(View.GONE);

        childLO.getChildAt(4).setVisibility(View.VISIBLE);
        childLO.getChildAt(5).setVisibility(View.GONE);

        LinearLayout child1 = (LinearLayout)childLO.getChildAt(7);
        child1.getChildAt(2).setVisibility(View.GONE);
    }

    /**
     * 編集内容確定
     * 確定ボタン押下時のEditTextから編集した内容を取り出す。
     * DBに入れるためにデータの型を整形してDBに入れる。
     */
    public  void onUpdate(View view){
        //確定ボタンの親レイアウト
        LinearLayout viewPare = (LinearLayout)view.getParent();
        //全体の親レイアウト
        LinearLayout viewPPare = (LinearLayout)viewPare.getParent();

        int id = viewPPare.getId();
        for (int i = 1;i<idList.length;i++){
            if(id == idList[i]){
                id = i;
                break;
            }
        }


        EditText year = findViewById(taskLayoutNum[id][7]);
        EditText startMonth = findViewById(taskLayoutNum[id][1]);
        EditText startDay = findViewById(taskLayoutNum[id][2]);


        //開始月日取得
        String startMD = year.getText() + "年" + startMonth.getText() + "月" + startDay.getText() + "日";

        EditText startHours = findViewById(taskLayoutNum[id][3]);
        EditText startMin = findViewById(taskLayoutNum[id][4]);


        //開始時刻取得
        String startHM = startHours.getText() + "時" + startMin.getText() + "分";

        EditText endHours = findViewById(taskLayoutNum[id][5]);
        EditText endMin = findViewById(taskLayoutNum[id][6]);


        //開始時刻取得
        String endHM = endHours.getText() + "時" + endMin.getText() + "分";

        EditText taskText = findViewById(taskLayoutNum[id][8]);

        //タスク内容取得
        String task = String.valueOf(taskText.getText());

        ContentValues values = new ContentValues();
        values.put("startday",startMD);
        values.put("starttime",startHM);
        values.put("endtime",endHM);
        values.put("task",task);

        SQLiteDatabase db = selectDB.getWritableDatabase();
        int ret = db.update("tastdb",values,"_id = "+viewPPare.getId(),null);
//        Log.d("debug","　"+viewPPare.getId()+"\n開始月日："+startMD+"\n開始時刻："+startHM+"\n終了時刻："+endHM+"\nタスク名："+task+"\n判定処理："+ret);

        onExit(view);
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

    public interface onRecyclerClickListener{
        void onClickListener(Object item);
    }





}