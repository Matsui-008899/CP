package com.example.calendarapplication;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements  TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {
    private EditText taskName;
    private EditText dateS;
    private EditText timeS;
    private EditText dateE;
    private EditText timeE;
    private boolean flag;
    private TextView taskView;
    private DataBase selectDB;
    private TextView startDayView;
    private TextView taskNameView;
    private TextView startTimeView;

    private String[] spinnerItems = {"01月", "02月", "03月", "04月", "05月", "06月",
            "07月", "08月", "09月", "10月", "11月", "12月"};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        flag = false;
        taskName = findViewById(R.id.taskName);
        dateS = findViewById(R.id.dateS);
        timeS = findViewById(R.id.timeS);
        dateE = findViewById(R.id.dateE);
        timeE = findViewById(R.id.timeE);
        taskView = findViewById(R.id.TaskView);

        selectDB = new DataBase(getApplicationContext());

        ArrayAdapter<String> addapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                spinnerItems
        );

        addapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        Spinner spinner = findViewById(R.id.spinner);
        spinner.setAdapter(addapter);

        CalendarView calendar = findViewById(R.id.calendar);
        calendar.setOnDateChangeListener(
                (calendarView, year, month, date) -> {
                    String message = (String.format("%d年%02d月%02d日",year,month+1,date));
                    Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
                    taskDaySelect(message,taskView);
                }

        );

        /**
         * 開始時刻→現在時刻
         */
        Calendar c = Calendar.getInstance();
        timeS.setText(String.format("%02d時%02d分",c.get(Calendar.HOUR),c.get(Calendar.MINUTE)));
        dateS.setText(String.format("%d年%02d月%02d日", c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)));
    }

    /**
     * ホーム画面表示
     * @param view
     */
    public void openHome(View view){
        LinearLayout CalendarLayout = (LinearLayout) findViewById(R.id.layerCalendar);
        LinearLayout TaskLayout = (LinearLayout) findViewById(R.id.layerTask);
        LinearLayout TaskListLayout = (LinearLayout) findViewById(R.id.layerTaskList);

        CalendarLayout.setVisibility(View.VISIBLE);
        TaskLayout.setVisibility(View.INVISIBLE);
        TaskListLayout.setVisibility(View.INVISIBLE);
    }

    /**
     * 予定追加画面表示
     * @param view
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
     * @param view
     */
    public void openTaskView(View view){

        LinearLayout CalendarLayout = (LinearLayout) findViewById(R.id.layerCalendar);
        LinearLayout TaskLayout = (LinearLayout) findViewById(R.id.layerTask);
        LinearLayout TaskListLayout = (LinearLayout) findViewById(R.id.layerTaskList);

        CalendarLayout.setVisibility(View.INVISIBLE);
        TaskLayout.setVisibility(View.INVISIBLE);
        TaskListLayout.setVisibility(View.VISIBLE);

        ListTask(view);
    }

    /**
     *　開始日時選択ボタン表示
     */
    public void selectTimeStartBtn(View view){
        ShowDialogView(dateS,timeS);
        flag = true;
    }

    /**
     * 終了日時選択ボタン表示
     */
    public void selectTimeEndBtn(View view){
        ShowDialogView(dateE,timeE);
        flag = false;
    }

    /**
     * 予定追加ボタン押下時
     */
    public void selectTaskSetBtn(View view){
        //データ挿入
        InsertTask(view);
    }

    /**
     * DBインサート文
     * @param view
     */
    private void InsertTask(View view) {
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
        int testsub = timeSS.compareTo(timeES);
        /**
         * 日付判定
         * 時刻が開始＞終了かつ日付が開始＜終了または
         */
        if((testsub<0&&test<=0)||(testsub>=0&&test<0)){
            Log.d("debug","正常に処理");
            /**
             * if true　いずれかのエディットに入力されていない
             * false 正常
             */
            if (TextUtils.isEmpty(taskNameS) ==true||
                    TextUtils.isEmpty(dateSS) ==true||
                    TextUtils.isEmpty(timeSS) ==true||
                    TextUtils.isEmpty(dateES) ==true||
                    TextUtils.isEmpty(timeES) ==true){
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
            }
            taskName.setText("");
            dateS.setText("");
            timeS.setText("");
            dateE.setText("");
            timeE.setText("");
        }else{
            Log.d("debug","日付に矛盾が発生");
        }


        Log.d("debug","日付判定日にち"+test);
        Log.d("debug","日付判定時刻"+testsub);

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
                new String[]{message.toString()},
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
        LinearLayout insertLayout = (TableLayout) findViewById(R.id.taskInsert);
        insertLayout.removeAllViews();
        SQLiteDatabase db = selectDB.getReadableDatabase();
        Spinner spinner = findViewById(R.id.spinner);
        String item = (String)spinner.getSelectedItem();

        Cursor cursor = db.query(
                "tastdb",
                new String[]{"startday","starttime","task"},
                "startday LIKE ?",
                new String[]{"2022年"+item+"%"},
                null,
                null,
                "startday"
        );
        cursor.moveToFirst();


        for (int i = 0; i < cursor.getCount(); i++){
            final TableRow tr = new TableRow(this);
            final TableRow tr1 = new TableRow(this);
            //開始時刻
            startDayView = new TextView(this);
            startTimeView = new TextView(this);
            taskNameView = new TextView(this);

            startDayView.setText(cursor.getString(0));
            tr.addView(startDayView);
            insertLayout.addView(tr);

            taskNameView.setText(cursor.getString(2));
            tr1.addView(taskNameView);
            startTimeView.setText(cursor.getString(1));
            tr1.addView(startTimeView);
            insertLayout.addView(tr1);

            cursor.moveToNext();
        }
        cursor.close();
    }

    /**
     * 時刻・月日ダイアログ表示
     * @param dateS
     * @param timeS
     */
    public void ShowDialogView(View dateS, View timeS) {
        /**
         * newFragment.show(getSupportFragmentManager(), "timePicker");
         */
        TimePickerFragment timePicker = new TimePickerFragment();
        timePicker.show(getSupportFragmentManager(),"timePicker");
        DatePickerFragment datePicker = new DatePickerFragment();
        datePicker.show(getSupportFragmentManager(),"datePicker");

    }

    /**
     * 時刻セット
     * @param timePicker
     * @param hour
     * @param minu
     */
    @Override
    public void onTimeSet(TimePicker timePicker, int hour, int minu) {
        if (flag == false){
            timeE.setText(String.format("%02d時%02d分",hour,minu));
        }else{
            timeS.setText(String.format("%02d時%02d分",hour,minu));
        }

    }

    /**
     * 月日セット
     * @param datePicker
     * @param year
     * @param month
     * @param dayOfMonth
     */
    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        if(flag == false){
            dateE.setText(String.format("%d年%02d月%02d日", year, month + 1, dayOfMonth));
        }else{
            dateS.setText(String.format("%d年%02d月%02d日", year, month + 1, dayOfMonth));
        }
    }
}