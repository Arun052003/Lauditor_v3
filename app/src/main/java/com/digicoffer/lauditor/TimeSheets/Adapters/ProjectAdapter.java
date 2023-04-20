package com.digicoffer.lauditor.TimeSheets.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.digicoffer.lauditor.R;
import com.digicoffer.lauditor.TimeSheets.Models.ProjectTMModel;
import com.digicoffer.lauditor.TimeSheets.Models.ProjectsModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.MyviewHolder> {
    ArrayList<ProjectsModel> projectList = new ArrayList<>();
    ArrayList<ProjectTMModel> projectTmList = new ArrayList<>();
    Context context;
    String selected_project;
    private int totalItemCount;
    private int itemsToLoad;
    public ProjectAdapter(ArrayList<ProjectsModel> projectList, ArrayList<ProjectTMModel> projectTmList, Context cContext) {
        this.projectList = projectList;
        this.projectTmList = projectTmList;
        this.context = cContext;
        this.selected_project = selected_project;
        this.totalItemCount = projectList.size();
        this.itemsToLoad = 5;
    }

    @NonNull
    @Override
    public ProjectAdapter.MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.projects_recyclerview, parent, false);
//        AndroidUtils.showAlert(projectList.toString(),context);
        return new ProjectAdapter.MyviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProjectAdapter.MyviewHolder holder, int position) {
        ProjectsModel projectsModel = projectList.get(position);

        holder.tv_project_case_number.setText(projectsModel.getCaseNo());
        holder.tv_project_name.setText(projectsModel.getProjectName());
        try {
            for (int i = 0; i < projectList.size(); i++) {
                for (int j = 0; j < projectsModel.getTeamMembers().length(); j++) {
                    if (projectList.get(i).getMatterId().equals(projectsModel.getMatterId())) {
                        JSONObject jsonObject1 = projectsModel.getTeamMembers().getJSONObject(j);
                        ProjectTMModel projectTMModel = new ProjectTMModel();
                        projectTMModel.setBillableHours(jsonObject1.getString("billableHours"));
                        projectTMModel.setName(jsonObject1.getString("name"));
                        projectTMModel.setNonBillablehours(jsonObject1.getString("nonBillablehours"));
                        projectTMModel.setTotal(jsonObject1.getString("total"));
                        projectTmList.add(projectTMModel);
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        holder.rv_tm_projects.setLayoutManager(new GridLayoutManager(context, 1));
        String status = "Projects";
        ProjectTMAdapter projectTMAdapter = new ProjectTMAdapter(projectTmList);
        holder.rv_tm_projects.setAdapter(projectTMAdapter);
        holder.rv_tm_projects.setHasFixedSize(true);
//            notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return Math.min(itemsToLoad, totalItemCount);
    }
    public void loadMoreItems() {
        itemsToLoad += 5;
        notifyDataSetChanged();
    }
    public class MyviewHolder extends RecyclerView.ViewHolder {
        TextView tv_project_case_number, tv_project_name;
        RecyclerView rv_tm_projects;

        public MyviewHolder(@NonNull View itemView) {
            super(itemView);
            tv_project_case_number = itemView.findViewById(R.id.tv_project_case_number);
            tv_project_name = itemView.findViewById(R.id.tv_project_name);
            rv_tm_projects = itemView.findViewById(R.id.rv_tm_projects);
        }
    }
}
