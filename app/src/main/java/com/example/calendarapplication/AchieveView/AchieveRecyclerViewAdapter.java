package com.example.calendarapplication.AchieveView;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.calendarapplication.R;

import java.util.List;

public class AchieveRecyclerViewAdapter extends RecyclerView.Adapter<AchieveViewHolder> {

    private List<AchieveViewRowData> list;

    public  AchieveRecyclerViewAdapter(List<AchieveViewRowData> achieveList){
        this.list = achieveList;
    }
    @NonNull
    @Override
    public AchieveViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.achieve_view_row,parent,false);
        AchieveViewHolder vh = new AchieveViewHolder(inflate);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull AchieveViewHolder holder, int position) {
        holder.achieveName.setText(list.get(position).getAchieveName());
        holder.achieveInfo.setText(list.get(position).getAchieveInfo());
        holder.achieveInfo.setTextColor(list.get(position).getAchieveInfoColor());
        holder.achieveName.setTextColor(list.get(position).getAchieveNameColor());
        holder.achieveDate.setText(list.get(position).getAchieveDate());
        if (list.get(position).getAchieveDate()!=null){
            holder.achieveDate.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
