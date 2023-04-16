package com.digicoffer.lauditor.TimeSheets.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.digicoffer.lauditor.R;
import com.digicoffer.lauditor.TimeSheets.Models.EventsModel;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class WeeklyTSAdapter extends RecyclerView.Adapter<WeeklyTSAdapter.MyViewHolder> {
    ArrayList<EventsModel>eventsList = new ArrayList<>();
    public WeeklyTSAdapter(ArrayList<EventsModel>eventsModels) {
    this.eventsList = eventsModels;
    }

    @NonNull
    @Override
    public WeeklyTSAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.weekly_timesheets,parent,false);
        return new WeeklyTSAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WeeklyTSAdapter.MyViewHolder holder, int position) {
        EventsModel eventsModel = eventsList.get(position);
        holder.tv_matter_task_name.setText(eventsModel.getTaskName());

    }

    @Override
    public int getItemCount() {
        return eventsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_matter_name,tv_matter_hours,tv_matter_minutes,tv_matter_billable,tv_matter_task_name;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_matter_name = itemView.findViewById(R.id.tv_matter_name);
            tv_matter_hours = itemView.findViewById(R.id.tv_matter_hours);
            tv_matter_minutes = itemView.findViewById(R.id.tv_matter_minutes);
            tv_matter_billable = itemView.findViewById(R.id.tv_matter_billable);
            tv_matter_task_name = itemView.findViewById(R.id.tv_matter_task_name);


        }
    }
}
