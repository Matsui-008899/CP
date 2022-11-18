package com.example.calendarapplication;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class ImageActivity extends AppCompatActivity
        implements View.OnTouchListener, View.OnDragListener {

    View zero;
    View one;
    View two;
    View three;
    View four;
    View five;
    View six;
    View seven;
    View eight;
    View nine;
    LinearLayout out;

    int [] num = new int[9];

    // CREATE
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        // Viewを取得
        out = findViewById(R.id.img_output);

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



        // リスナーをセット
        zero.setOnTouchListener(this::onTouch);
        zero.setOnDragListener(this::onDrag);

//        // 待機画面に遷移
//        Button button2 = findViewById(R.id.button2);
//        button2.setOnClickListener(v -> {
//        Intent intent = new Intent(getApplication(), StopActivity.class);
//        startActivity(intent); });
    }


    // ドラッグ・ドロップ処理
    @Override
    public boolean onDrag(View view, DragEvent dragEvent) {
        switch (dragEvent.getAction()) {

            // end
            case DragEvent.ACTION_DRAG_ENDED:
                Log.d("end", "endDrag");
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
//                    getMainExecutor().execute(() -> mDragView.setAlpha(1));
//                }
                return true;

            // start
            case
                    DragEvent.ACTION_DRAG_STARTED:
                View dragView = (View) dragEvent.getLocalState();
                Log.d("start", "startDrag");
                return true;

            // drop
            case DragEvent.ACTION_DROP:
                Log.d("drop", "drop");
                View addedView = new View(this);
                addedView.setX(dragEvent.getX());
                addedView.setY(dragEvent.getY());
                out.addView(addedView);

                return true;
        }
        return true;
    }

    // タッチ処理
//    private View mDragView;
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
//        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
//            mDragView = view;
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                view.startDragAndDrop(null, new View.DragShadowBuilder(view), view, 0);
//            }
//            view.setAlpha(0);
//        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            view.startDragAndDrop(null, new View.DragShadowBuilder(view), view, 0);
            return true;
        }
        return true;
    }

}
