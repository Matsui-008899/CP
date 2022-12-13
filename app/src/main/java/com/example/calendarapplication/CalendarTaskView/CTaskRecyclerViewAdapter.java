package com.example.calendarapplication.CalendarTaskView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.calendarapplication.R;

import java.util.List;

public class CTaskRecyclerViewAdapter extends RecyclerView.Adapter<CTaskViewHolder> {

    private List<CTaskViewRowData> list;

    public  CTaskRecyclerViewAdapter(List<CTaskViewRowData> list){
        this.list = list;
    }
    @NonNull
    @Override
    public CTaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate
                (R.layout.calendar_task_view_row,parent,false);
        CTaskViewHolder vh = new CTaskViewHolder(inflate);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull CTaskViewHolder holder, int position) {
        holder.startDayView.setText(list.get(position).getStartDayView());
        holder.startTimeView.setText(list.get(position).getStartTimeView());
        holder.endDayView.setText(list.get(position).getEndDayView());
        holder.endTimeView.setText(list.get(position).getEndTimeView());
        holder.taskView.setText(list.get(position).getTaskView());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
