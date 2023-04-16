package com.digicoffer.lauditor.TimeSheets.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.digicoffer.lauditor.R;
import com.digicoffer.lauditor.TimeSheets.Models.ProjectTMModel;

import java.util.ArrayList;

public class ProjectTMAdapter extends RecyclerView.Adapter<ProjectTMAdapter.MyviewHolder> {
    ArrayList<ProjectTMModel> projectTmList = new ArrayList<>();

    public ProjectTMAdapter(ArrayList<ProjectTMModel> projectTmList) {
        this.projectTmList = projectTmList;
    }

    @NonNull
    @Override
    public ProjectTMAdapter.MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.weekly_timesheets,parent,false);
        return new ProjectTMAdapter.MyviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProjectTMAdapter.MyviewHolder holder, int position) {
        ProjectTMModel projectTMModel = projectTmList.get(position);
        holder.ll_total_hours.setVisibility(View.VISIBLE);
        holder.tv_matter_name.setText(projectTMModel.getName());
        holder.tv_matter_billable.setText("Billable");
        holder.tv_matter_task_name.setText("Non Billable");
        holder.tv_matter_hours.setText(projectTMModel.getBillableHours());
        holder.tv_matter_minutes.setText(projectTMModel.getNonBillablehours());
        holder.tv_total_hours.setText(projectTMModel.getTotal());
    }

    @Override
    public int getItemCount() {
        return projectTmList.size();
    }

    public class MyviewHolder extends RecyclerView.ViewHolder {
        private TextView tv_matter_name, tv_matter_hours, tv_matter_minutes, tv_matter_billable, tv_matter_task_name, tv_total_hours;
        LinearLayout ll_total_hours;
        public MyviewHolder(@NonNull View itemView) {

            super(itemView);
            tv_matter_billable = itemView.findViewById(R.id.tv_matter_billable);
            tv_matter_hours = itemView.findViewById(R.id.tv_matter_hours);
            tv_matter_minutes = itemView.findViewById(R.id.tv_matter_minutes);
            tv_matter_name = itemView.findViewById(R.id.tv_matter_name);
            tv_matter_task_name =itemView.findViewById(R.id.tv_matter_task_name);
            tv_total_hours = itemView.findViewById(R.id.tv_total_hours);
            ll_total_hours = itemView.findViewById(R.id.ll_total_hours);
        }
    }
}
