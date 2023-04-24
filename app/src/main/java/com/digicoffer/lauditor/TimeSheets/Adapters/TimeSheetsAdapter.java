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
import com.digicoffer.lauditor.TimeSheets.Models.EventsModel;
import com.digicoffer.lauditor.TimeSheets.Models.TSMatterModel;
import com.digicoffer.lauditor.TimeSheets.Models.TaskModel;
import com.digicoffer.lauditor.TimeSheets.Models.WeekModel;
import com.digicoffer.lauditor.common.AndroidUtils;

import org.json.JSONException;

import java.util.ArrayList;


public class TimeSheetsAdapter extends RecyclerView.Adapter<TimeSheetsAdapter.MyViewHolder> {
    ArrayList<WeekModel> weeksList = new ArrayList<>();
    ArrayList<EventsModel> eventsList = new ArrayList<>();
    ArrayList<TSMatterModel> matterList = new ArrayList<>();
    ArrayList<TaskModel> monday = new ArrayList<>();
    ArrayList<TaskModel> tuesday = new ArrayList<>();
    ArrayList<TaskModel> wednessday = new ArrayList<>();
    ArrayList<TaskModel> thursday = new ArrayList<>();
    ArrayList<TaskModel> friday = new ArrayList<>();
    ArrayList<TaskModel> saturday = new ArrayList<>();
    ArrayList<TaskModel> sunday = new ArrayList<>();

    Context context;

    public TimeSheetsAdapter(ArrayList<WeekModel> weeksList, ArrayList<EventsModel> eventsModels, Context cContext) {
        this.weeksList = weeksList;
        this.eventsList = eventsModels;
        this.context = cContext;
    }

    @NonNull
    @Override


    public TimeSheetsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.timesheets_recyclerview, parent, false);
        return new TimeSheetsAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TimeSheetsAdapter.MyViewHolder holder, int position) {
        WeekModel weekModel = weeksList.get(position);
//        AndroidUtils.showAlert(String.valueOf(eventsList.size()), context);
        try {
            for (int e = 0; e < eventsList.size(); e++) {
                EventsModel eventsModel = eventsList.get(e);
//                for (int i = 0; i < eventsModel.getMon().length(); i++) {
                TaskModel taskModel = new TaskModel();
                if (eventsModel.getMon().has("taskId")) {
                    taskModel.setTaskid(eventsModel.getMon().getString("taskId"));
                }
                if (eventsModel.getMon().has("matterId")) {
                    taskModel.setMatterid(eventsModel.getMon().getString("matterId"));
                }
                taskModel.setMinutes(eventsModel.getMon().getString("minutes"));
                taskModel.setHours(eventsModel.getMon().getString("hours"));

                taskModel.setTask_name(eventsModel.getTaskName());
                if (eventsModel.getBilling() != "" || !(eventsModel.getBilling() == null)) {
                    taskModel.setTask_billing(eventsModel.getBilling());
                }
                taskModel.setTask_matter_name(eventsModel.getMatter_name());
                taskModel.setTask_matter_id(eventsModel.getMatter_id());
                if (!(taskModel.getTaskid() == null) || taskModel.getTaskid() != "") {
                    monday.add(taskModel);
                }
//                }
//                for (int i = 0; i < eventsModel.getTue().length(); i++) {
                TaskModel taskModel_tue = new TaskModel();
                if (eventsModel.getTue().has("taskId")) {
                    taskModel_tue.setTaskid(eventsModel.getTue().getString("taskId"));
                }
                if (eventsModel.getTue().has("matterId")) {
                    taskModel_tue.setMatterid(eventsModel.getTue().getString("matterId"));
                }
                taskModel_tue.setMinutes(eventsModel.getTue().getString("minutes"));
                taskModel_tue.setHours(eventsModel.getTue().getString("hours"));

                taskModel_tue.setTask_name(eventsModel.getTaskName());
                if (eventsModel.getBilling() != "" || !(eventsModel.getBilling() == null)) {
                    taskModel_tue.setTask_billing(eventsModel.getBilling());
                }
                taskModel_tue.setTask_matter_name(eventsModel.getMatter_name());
                taskModel_tue.setTask_matter_id(eventsModel.getMatter_id());
                if (!(taskModel_tue.getTaskid() == null) || taskModel_tue.getTaskid() != "") {
                    tuesday.add(taskModel_tue);
                }
//                }
//                for (int i = 0; i < eventsList.get(i).getWed().length(); i++) {
                TaskModel taskModel_wed = new TaskModel();
                if (eventsModel.getWed().has("taskId")) {
                    taskModel_wed.setTaskid(eventsModel.getWed().getString("taskId"));
                }
                if (eventsModel.getWed().has("matterId")) {
                    taskModel_wed.setMatterid(eventsModel.getWed().getString("matterId"));
                }
                taskModel_wed.setMinutes(eventsModel.getWed().getString("minutes"));
                taskModel_wed.setHours(eventsModel.getWed().getString("hours"));

                taskModel_wed.setTask_name(eventsModel.getTaskName());
                if (eventsModel.getBilling() != "" || !(eventsModel.getBilling() == null)) {
                    taskModel_wed.setTask_billing(eventsModel.getBilling());
                }
                taskModel_wed.setTask_matter_name(eventsModel.getMatter_name());
                taskModel_wed.setTask_matter_id(eventsModel.getMatter_id());
                if (!(taskModel_wed.getTaskid() == null) || taskModel_wed.getTaskid() != "") {
                    wednessday.add(taskModel_wed);
                }
//                }
//                for (int i = 0; i < eventsModel.getThu().length(); i++) {
                    TaskModel taskModel_thu = new TaskModel();
                    if (eventsModel.getThu().has("taskId")) {
                        taskModel_thu.setTaskid(eventsModel.getThu().getString("taskId"));
                    }
                    if (eventsModel.getThu().has("matterId")) {
                        taskModel_thu.setMatterid(eventsModel.getThu().getString("matterId"));
                    }
                    taskModel_thu.setMinutes(eventsModel.getThu().getString("minutes"));
                    taskModel_thu.setHours(eventsModel.getThu().getString("hours"));
                    taskModel_thu.setTask_name(eventsModel.getTaskName());
                    if (eventsModel.getBilling() != "" || !(eventsModel.getBilling() == null)) {
                        taskModel_thu.setTask_billing(eventsModel.getBilling());
                    }
                    taskModel_thu.setTask_matter_name(eventsModel.getMatter_name());
                    taskModel_thu.setTask_matter_id(eventsModel.getMatter_id());
                    if (!(taskModel_thu.getTaskid() == null) || taskModel_thu.getTaskid() != "") {
                        thursday.add(taskModel_thu);
                    }
//                }


//                for (int i = 0; i < eventsModel.getFri().length(); i++) {
                TaskModel taskModel_fri = new TaskModel();
                if (eventsModel.getFri().has("taskId")) {
                    taskModel_fri.setTaskid(eventsModel.getFri().getString("taskId"));
                }
                if (eventsModel.getFri().has("matterId")) {
                    taskModel_fri.setMatterid(eventsModel.getFri().getString("matterId"));
                }
                taskModel_fri.setMinutes(eventsModel.getFri().getString("minutes"));
                taskModel_fri.setHours(eventsModel.getFri().getString("hours"));

                taskModel_fri.setTask_name(eventsModel.getTaskName());
                if (eventsModel.getBilling() != "" || !(eventsModel.getBilling() == null)) {
                    taskModel_fri.setTask_billing(eventsModel.getBilling());
                }
                taskModel_fri.setTask_matter_name(eventsModel.getMatter_name());
                taskModel_fri.setTask_matter_id(eventsModel.getMatter_id());
                if (!(taskModel_fri.getTaskid() == null) || taskModel_fri.getTaskid() != "") {
                    friday.add(taskModel_fri);
                }
//                }
//                for (int i = 0; i < eventsModel.getSat().length(); i++) {
                TaskModel taskModel_sat = new TaskModel();
                if (eventsModel.getSat().has("taskId")) {
                    taskModel_sat.setTaskid(eventsModel.getSat().getString("taskId"));
                }
                if (eventsModel.getSat().has("matterId")) {
                    taskModel_sat.setMatterid(eventsModel.getSat().getString("matterId"));
                }
                taskModel_sat.setMinutes(eventsModel.getSat().getString("minutes"));
                taskModel_sat.setHours(eventsModel.getSat().getString("hours"));
                taskModel_sat.setTask_name(eventsModel.getTaskName());
                if (eventsModel.getBilling() != "" || !(eventsModel.getBilling() == null)) {
                    taskModel_sat.setTask_billing(eventsModel.getBilling());
                }
                taskModel_sat.setTask_matter_name(eventsModel.getMatter_name());
                taskModel_sat.setTask_matter_id(eventsModel.getMatter_id());
                if (!(taskModel_sat.getTaskid() == null) || taskModel_sat.getTaskid() != "") {
                    saturday.add(taskModel_sat);
                }
//                }
//                for (int i = 0; i < eventsModel.getSun().length(); i++) {
                TaskModel taskModel_sun = new TaskModel();
                if (eventsModel.getSun().has("taskId")) {
                    taskModel_sun.setTaskid(eventsModel.getSun().getString("taskId"));
                }
                if (eventsModel.getSun().has("matterId")) {
                    taskModel_sun.setMatterid(eventsModel.getSun().getString("matterId"));
                }
                taskModel_sun.setMinutes(eventsModel.getSun().getString("minutes"));
                taskModel_sun.setHours(eventsModel.getSun().getString("hours"));
                taskModel_sun.setTask_name(eventsModel.getTaskName());
                if (eventsModel.getBilling() != "" || !(eventsModel.getBilling() == null)) {
                    taskModel_sun.setTask_billing(eventsModel.getBilling());
                }
                taskModel_sun.setTask_matter_name(eventsModel.getMatter_name());
                taskModel_sun.setTask_matter_id(eventsModel.getMatter_id());
                if (!(taskModel_sun.getTaskid() == null) || taskModel_sun.getTaskid() != "") {
                    sunday.add(taskModel_sun);
                }
            }
//            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

//            for (int i=0;i<eventsList.size();i++){
//                EventsModel eventsModel = eventsList.get(i);
//                JSONObject
//            }

        holder.tv_date.setText(weekModel.getValue());
        String inputString = weekModel.getValue();
        String[] parts = inputString.split(" "); // Split the string by whitespace
        String dayOfWeek = parts[0];

        if (dayOfWeek.equals("Mon")) {
//        AndroidUtils.showAlert(eventsList.toString(),context);
            try {
                loadRecyclerview(holder, monday);
            } catch (Exception e) {
                AndroidUtils.showAlert(e.getMessage(), context);
                e.printStackTrace();
            }

        }
        if (dayOfWeek.equals("Tue")) {
//        AndroidUtils.showAlert(eventsList.toString(),context);
            try {
                loadRecyclerview(holder, tuesday);
            } catch (Exception e) {
                AndroidUtils.showAlert(e.getMessage(), context);
                e.printStackTrace();
            }

        }
        if (dayOfWeek.equals("Wed")) {
//        AndroidUtils.showAlert(eventsList.toString(),context);
            try {
                loadRecyclerview(holder, wednessday);
            } catch (Exception e) {
                AndroidUtils.showAlert(e.getMessage(), context);
                e.printStackTrace();
            }

        }
        if (dayOfWeek.equals("Thu")) {
//        AndroidUtils.showAlert(eventsList.toString(),context);
            try {
                loadRecyclerview(holder, thursday);
            } catch (Exception e) {
                AndroidUtils.showAlert(e.getMessage(), context);
                e.printStackTrace();
            }

        }
        if (dayOfWeek.equals("Fri")) {
//        AndroidUtils.showAlert(eventsList.toString(),context);
            try {
                loadRecyclerview(holder, friday);
            } catch (Exception e) {
                AndroidUtils.showAlert(e.getMessage(), context);
                e.printStackTrace();
            }

        }
        if (dayOfWeek.equals("Sat")) {
//        AndroidUtils.showAlert(eventsList.toString(),context);
            try {
                loadRecyclerview(holder, saturday);
            } catch (Exception e) {
                AndroidUtils.showAlert(e.getMessage(), context);
                e.printStackTrace();
            }

        }
        if (dayOfWeek.equals("Sun")) {
//        AndroidUtils.showAlert(eventsList.toString(),context);
            try {
                loadRecyclerview(holder, sunday);
            } catch (Exception e) {
                AndroidUtils.showAlert(e.getMessage(), context);
                e.printStackTrace();
            }

        }
//        holder.rv_time_sheets.setLayoutManager(new GridLayoutManager(context,1));
//        TaskAdapter weeklyTSAdapter= new TaskAdapter(monday);
//        holder.rv_time_sheets.setAdapter(weeklyTSAdapter);
//        holder.rv_time_sheets.setHasFixedSize(true);

    }

    private void loadRecyclerview(MyViewHolder holder, ArrayList<TaskModel> list) throws Exception {
        holder.rv_time_sheets.setLayoutManager(new GridLayoutManager(context, 1));
        WeeklyTSAdapter weeklyTSAdapter = new WeeklyTSAdapter(list);
        holder.rv_time_sheets.setAdapter(weeklyTSAdapter);
        holder.rv_time_sheets.setHasFixedSize(true);
    }

    @Override
    public int getItemCount() {
        return weeksList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_date;
        RecyclerView rv_time_sheets;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_date = itemView.findViewById(R.id.tv_date);
            rv_time_sheets = itemView.findViewById(R.id.rv_time_sheets);
        }
    }
}
