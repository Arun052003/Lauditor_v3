package com.digicoffer.lauditor.TimeSheets.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.digicoffer.lauditor.R;
import com.digicoffer.lauditor.TimeSheets.Models.TaskModel;

import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.MyViewholder> {

    ArrayList<TaskModel> taskModels = new ArrayList<>();

    public TaskAdapter(ArrayList<TaskModel> taskModels) {
        this.taskModels = taskModels;
    }

    @NonNull
    @Override
    public TaskAdapter.MyViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.weekly_timesheets,parent,false);
        return new TaskAdapter.MyViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskAdapter.MyViewholder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class MyViewholder extends RecyclerView.ViewHolder {
        public MyViewholder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
