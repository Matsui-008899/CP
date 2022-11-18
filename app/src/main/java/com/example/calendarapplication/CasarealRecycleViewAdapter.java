package com.example.calendarapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CasarealRecycleViewAdapter extends RecyclerView.Adapter<CasarealViewHolder> {

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
        holder.upSDay1.setText(list.get(position).getSDay1());
        holder.upSDay2.setText(list.get(position).getSDay2());
        holder.upSTime1.setText(list.get(position).getSTime1());
        holder.upSTime2.setText(list.get(position).getSTime2());
        holder.upETime1.setText(list.get(position).getETime1());
        holder.upETime2.setText(list.get(position).getETime2());
        holder.upYear.setText(list.get(position).getYear());
        holder.upTask.setText(list.get(position).getTaskName());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
