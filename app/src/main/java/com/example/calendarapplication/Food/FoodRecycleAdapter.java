package com.example.calendarapplication.Food;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.calendarapplication.R;

import java.util.List;

public class FoodRecycleAdapter extends RecyclerView.Adapter<FoodViewHolder> {

    private List<FoodRowData> list;

    public FoodRecycleAdapter(List<FoodRowData> foodList) {
        this.list = foodList;
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_view_row,parent,false);
        FoodViewHolder vh = new FoodViewHolder(inflate);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        holder.startView.setText(list.get(position).getStartView());
        holder.endView.setText(list.get(position).getEndView());
        holder.taskView.setText(list.get(position).getTaskView());
        holder.tag.setTag(list.get(position).getTag());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
