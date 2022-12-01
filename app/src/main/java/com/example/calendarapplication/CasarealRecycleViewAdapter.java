package com.example.calendarapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CasarealRecycleViewAdapter extends RecyclerView.Adapter<CasarealViewHolder> {

    private List<CasarealRowData> list;


    public CasarealRecycleViewAdapter(List<CasarealRowData> list){
        this.list = list;
    }


    @Override
    public CasarealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_task_view_row,parent,false);
        CasarealViewHolder vh = new CasarealViewHolder(inflate);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull CasarealViewHolder holder, int position) {
        holder.startDay.setText(list.get(position).getStartDay());
        holder.startTime.setText(list.get(position).getStartTime());
        holder.endDay.setText(list.get(position).getEndDay());
        holder.endTime.setText(list.get(position).getEndTime());
        holder.taskName.setText(list.get(position).getTaskName());
        holder.setId.setId(list.get(position).getId());
        holder.upSDay1.setId(list.get(position).getIdSDay1());
        holder.upSDay2.setId(list.get(position).getIdSDay2());
        holder.upSTime1.setId(list.get(position).getIdSTime1());
        holder.upSTime2.setId(list.get(position).getIdSTime2());
        holder.upEDay1.setId(list.get(position).getIdEDay1());
        holder.upEDay2.setId(list.get(position).getIdEDay2());
        holder.upETime1.setId(list.get(position).getIdETime1());
        holder.upETime2.setId(list.get(position).getIdETime2());
        holder.upYear.setId(list.get(position).getIdYear());
        holder.upSDay1.setText(list.get(position).getSDay1());
        holder.upSDay2.setText(list.get(position).getSDay2());
        holder.upSTime1.setText(list.get(position).getSTime1());
        holder.upSTime2.setText(list.get(position).getSTime2());
        holder.upEDay1.setText(list.get(position).getEDay1());
        holder.upEDay2.setText(list.get(position).getEDay2());
        holder.upETime1.setText(list.get(position).getETime1());
        holder.upETime2.setText(list.get(position).getETime2());

        holder.upYear.setText(list.get(position).getYear());

        holder.upTask.setText(list.get(position).getTaskName());
        holder.upTask.setId(list.get(position).getIdTask());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}
