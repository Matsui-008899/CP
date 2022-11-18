package com.example.calendarapplication;

import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CasarealViewHolder extends RecyclerView.ViewHolder {
    public TextView startDay;
    public TextView startTime;
    public TextView endTime;
    public  TextView taskName;
    public LinearLayout setId;
    public EditText upSDay1;
    public EditText upSDay2;
    public EditText upSTime1;
    public EditText upSTime2;
    public EditText upETime1;
    public EditText upETime2;
    public EditText upYear;
    public EditText upTask;

    public CasarealViewHolder(@NonNull View itemView) {
        super(itemView);

        startDay = (TextView) itemView.findViewById(R.id.startDayRecycle);
        startTime = (TextView) itemView.findViewById(R.id.startTimeRecycle);
        endTime = (TextView) itemView.findViewById(R.id.endTimeRecycle);
        taskName = (TextView) itemView.findViewById(R.id.taskNameRecycle);
        setId = (LinearLayout) itemView.findViewById(R.id.setId);
        upSDay1 = (EditText) itemView.findViewById(R.id.startDayRecycleUp1);
        upSDay2 = (EditText) itemView.findViewById(R.id.startDayRecycleUp2);
        upSTime1 = (EditText) itemView.findViewById(R.id.startTimeRecycleUp1);
        upSTime2 = (EditText) itemView.findViewById(R.id.startTimeRecycleUp2);
        upETime1 = (EditText) itemView.findViewById(R.id.endTimeRecycleUp1);
        upETime2 = (EditText) itemView.findViewById(R.id.endTimeRecycleUp2);
        upYear = (EditText) itemView.findViewById(R.id.yearRecycleUp);
        upTask = (EditText) itemView.findViewById(R.id.taskNameRecycleUp);
    }
}
