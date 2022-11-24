package com.example.calendarapplication;

import android.content.ClipData;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CasarealRecycleViewAdapter extends RecyclerView.Adapter<CasarealViewHolder> {

    private MainActivity.onRecyclerClickListener listener;

    private List<RowData> list;


    public CasarealRecycleViewAdapter(List<RowData> list){
        this.list = list;
    }


    @Override
    public CasarealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.row,parent,false);
        CasarealViewHolder vh = new CasarealViewHolder(inflate);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull CasarealViewHolder holder, int position) {
        holder.startDay.setText(list.get(position).getStartDay());
        holder.startTime.setText(list.get(position).getStartTime());
        holder.endTime.setText(list.get(position).getEndTime());
        holder.taskName.setText(list.get(position).getTaskName());
        holder.setId.setId(list.get(position).getId());
        holder.upSDay1.setId(list.get(position).getIdSDay1());
        holder.upSDay1.setText(list.get(position).getSDay1());
        holder.upSDay2.setText(list.get(position).getSDay2());
        holder.upSDay2.setId(list.get(position).getIdSDay2());
        holder.upSTime1.setText(list.get(position).getSTime1());
        holder.upSTime1.setId(list.get(position).getIdSTime1());
        holder.upSTime2.setText(list.get(position).getSTime2());
        holder.upSTime2.setId(list.get(position).getIdSTime2());
        holder.upETime1.setText(list.get(position).getETime1());
        holder.upETime1.setId(list.get(position).getIdETime1());
        holder.upETime2.setText(list.get(position).getETime2());
        holder.upETime2.setId(list.get(position).getIdETime2());
        holder.upYear.setText(list.get(position).getYear());
        holder.upYear.setId(list.get(position).getIdYear());
        holder.upTask.setText(list.get(position).getTaskName());
        holder.upTask.setId(list.get(position).getIdTask());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}
