package com.example.calendarapplication;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CTaskViewHolder extends RecyclerView.ViewHolder {
    public TextView startDayView;
    public TextView startTimeView;
    public TextView endDayView;
    public TextView endTimeView;
    public TextView taskView;
    public CTaskViewHolder(@NonNull View itemView) {
        super(itemView);

        startDayView =(TextView) itemView.findViewById(R.id.startDayView);
        startTimeView=(TextView) itemView.findViewById(R.id.startTimeView);
        endDayView=(TextView) itemView.findViewById(R.id.endDayView);
        endTimeView=(TextView) itemView.findViewById(R.id.endTimeView);
        taskView=(TextView) itemView.findViewById(R.id.taskView);
    }
}
