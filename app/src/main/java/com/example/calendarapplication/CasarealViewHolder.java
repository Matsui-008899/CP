package com.example.calendarapplication;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CasarealViewHolder extends RecyclerView.ViewHolder {
    public TextView startDay;
    public TextView startTime;
    public TextView endTime;
    public  TextView taskName;

    public CasarealViewHolder(@NonNull View itemView) {
        super(itemView);
        startDay = (TextView) itemView.findViewById(R.id.startDayRecycle);
        startTime = (TextView) itemView.findViewById(R.id.startTimeRecycle);
        endTime = (TextView) itemView.findViewById(R.id.endTimeRecycle);
        taskName = (TextView) itemView.findViewById(R.id.taskNameRecycle);
    }
}
