package com.example.calendarapplication.AchieveView;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.calendarapplication.R;

public class AchieveViewHolder extends RecyclerView.ViewHolder {

    public TextView achieveName;
    public TextView achieveInfo;
    public TextView achieveDate;

    public AchieveViewHolder(View itemView){
        super(itemView);

        achieveName=(TextView) itemView.findViewById(R.id.achieveName);
        achieveInfo=(TextView) itemView.findViewById(R.id.achieveinfo);
        achieveDate=(TextView) itemView.findViewById(R.id.achieveDate);


    }
}
