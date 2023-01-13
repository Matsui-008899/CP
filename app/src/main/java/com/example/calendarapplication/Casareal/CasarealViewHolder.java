package com.example.calendarapplication.Casareal;

import android.graphics.Paint;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.calendarapplication.R;

public class CasarealViewHolder extends RecyclerView.ViewHolder {
    public TextView startDay;
    public TextView startTime;
    public TextView endDay;
    public TextView endTime;
    public TextView taskName;
    public LinearLayout setId;
    public TextView upSDay1;
    public TextView upSDay2;
    public TextView upSTime1;
    public TextView upSTime2;
    public TextView upEDay1;
    public TextView upEDay2;
    public TextView upETime1;
    public TextView upETime2;
    public TextView upYear;
    public TextView upEndYear;
    public EditText upTask;

    public CasarealViewHolder(@NonNull View itemView) {
        super(itemView);

        startDay = (TextView) itemView.findViewById(R.id.startDayRecycle);
        startTime = (TextView) itemView.findViewById(R.id.startTimeRecycle);
        endDay = (TextView) itemView.findViewById(R.id.endDayRecycle);
        endTime = (TextView) itemView.findViewById(R.id.endTimeRecycle);
        taskName = (TextView) itemView.findViewById(R.id.taskNameRecycle);
        setId = (LinearLayout) itemView.findViewById(R.id.setId);
        upSDay1 = (TextView) itemView.findViewById(R.id.startDayRecycleUp1);
        upSDay2 = (TextView) itemView.findViewById(R.id.startDayRecycleUp2);
        upSTime1 = (TextView) itemView.findViewById(R.id.startTimeRecycleUp1);
        upSTime2 = (TextView) itemView.findViewById(R.id.startTimeRecycleUp2);
        upEDay1 = (TextView) itemView.findViewById(R.id.endDayRecycleUp1);
        upEDay2 = (TextView) itemView.findViewById(R.id.endDayRecycleUp2);
        upETime1 = (TextView) itemView.findViewById(R.id.endTimeRecycleUp1);
        upETime2 = (TextView) itemView.findViewById(R.id.endTimeRecycleUp2);
        upYear = (TextView) itemView.findViewById(R.id.yearRecycleUp);
        upEndYear = (TextView)itemView.findViewById(R.id.endYearRecycleUp);
        upTask = (EditText) itemView.findViewById(R.id.taskNameRecycleUp);

        upSDay1.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        upSDay2.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        upSTime1.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        upSTime2.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        upEDay1.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        upEDay2.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        upETime1.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        upETime2.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        upYear.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        upEndYear.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);

    }


}
