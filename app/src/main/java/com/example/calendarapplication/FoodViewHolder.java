package com.example.calendarapplication;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FoodViewHolder extends RecyclerView.ViewHolder {
    public TextView startView;
    public TextView endView;
    public TextView taskView;
    public LinearLayout tag;

    public FoodViewHolder(@NonNull View itemView) {
        super(itemView);

        startView=(TextView) itemView.findViewById(R.id.startRecycle);
        endView=(TextView) itemView.findViewById(R.id.endRecycle);
        taskView=(TextView) itemView.findViewById(R.id.taskNameRecycle);
        tag = (LinearLayout) itemView.findViewById(R.id.foodLayout);
    }
}
