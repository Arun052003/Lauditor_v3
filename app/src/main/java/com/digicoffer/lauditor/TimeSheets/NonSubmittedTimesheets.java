package com.digicoffer.lauditor.TimeSheets;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.digicoffer.lauditor.Matter.Models.MatterModel;
import com.digicoffer.lauditor.R;
import com.digicoffer.lauditor.TimeSheets.Models.TSMatterModel;
import com.digicoffer.lauditor.TimeSheets.Models.TasksModel;
import com.digicoffer.lauditor.TimeSheets.Models.TimeSheetModel;
import com.digicoffer.lauditor.Webservice.AsyncTaskCompleteListener;
import com.digicoffer.lauditor.Webservice.HttpResultDo;
import com.digicoffer.lauditor.Webservice.WebServiceHelper;
import com.digicoffer.lauditor.common.AndroidUtils;
import com.digicoffer.lauditor.common_adapters.CommonSpinnerAdapter;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.minidns.record.A;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class NonSubmittedTimesheets extends Fragment implements AsyncTaskCompleteListener {
    AlertDialog progressDialog;
    ArrayList<TimeSheetModel> timeSheetsList = new ArrayList<>();
    ArrayList<TSMatterModel> matterList = new ArrayList<>();
    ArrayList<TasksModel> tasksList = new ArrayList<>();
    String selected_matter = "";
    String selected_matter_id = "";
    String selected_matter_type = "";
    String selected_task = "";
    boolean date_status = false;
    View view;
    Spinner sp_project,sp_task,sp_status,sp_date;
    TextInputEditText tv_hours,tv_total_hours;
    androidx.appcompat.widget.AppCompatButton btn_cancel_timesheet,btn_save_timesheet;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.not_submitted_timesheet, container, false);
//        if()
        sp_project = view.findViewById(R.id.sp_project);
        sp_task = view.findViewById(R.id.sp_task);
        sp_status = view.findViewById(R.id.sp_status);
        sp_date = view.findViewById(R.id.sp_date);
        tv_hours =view.findViewById(R.id.tv_hours);
        tv_total_hours = view.findViewById(R.id.tv_total_hours);
        btn_cancel_timesheet = view.findViewById(R.id.btn_cancel_timesheet);
        btn_save_timesheet = view.findViewById(R.id.btn_save_timesheet);
        Bundle bundle = getArguments();
        String date = bundle.getString("date");
        AndroidUtils.showToast(date, getContext());
        if (date.equals("") || date.equals(null)) {
//             date_status = false;
            callCurrentDateTimeSheetsWebservice(date, date_status);
        } else {
            callTimeSheetsWebservice(date);
        }


//        AndroidUtils.showToast(String.valueOf(status),getContext());
//        if (status){
//            TextView textView = new TextView(getContext());
////            textView.setText("TimeSheet already submitted");
////            ((LinearLayout) view).addView(textView);
////                OpenPopup();
//            // Add a LinearLayout as the container
//        }
        return view;
    }

    private void callCurrentDateTimeSheetsWebservice(String date, boolean date_status) {
        try {
            progressDialog = AndroidUtils.get_progress(getActivity());
            JSONObject data = new JSONObject();
            AndroidUtils.showToast(String.valueOf(date_status), getContext());

            WebServiceHelper.callHttpWebService(this, getContext(), WebServiceHelper.RestMethodType.GET, "v3/user/timesheets", "TimeSheets", data.toString());


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void disableAllViews(View view) {
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                View child = viewGroup.getChildAt(i);
                disableAllViews(child);
            }
        } else {
            view.setEnabled(false);
        }
    }

    private void OpenPopup() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.custom_dialog, null);
        builder.setView(dialogView);

// Add dialog actions (e.g. positive/negative buttons) here

        AlertDialog dialog = builder.create();
//        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    private void callTimeSheetsWebservice(String date) {
        try {
            progressDialog = AndroidUtils.get_progress(getActivity());
            JSONObject data = new JSONObject();
//            AndroidUtils.showToast(String.valueOf(date_status),getContext());
            SimpleDateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
            Date new_date = inputFormat.parse(date);
            String outputDate = outputFormat.format(new_date);
            WebServiceHelper.callHttpWebService(this, getContext(), WebServiceHelper.RestMethodType.GET, "v3/user/timesheets/" + outputDate, "TimeSheets", data.toString());


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void enableDisableViewGroup(ViewGroup viewGroup, boolean enabled) {
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = viewGroup.getChildAt(i);
            view.setEnabled(enabled);
            if (view instanceof ViewGroup) {
                enableDisableViewGroup((ViewGroup) view, enabled);
            }
        }
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onAsyncTaskComplete(HttpResultDo httpResult) {
        if (progressDialog.isShowing() && progressDialog != null) {
            AndroidUtils.dismiss_dialog(progressDialog);
        }
        if (httpResult.getResult() == WebServiceHelper.ServiceCallStatus.Success) {
            try {
                JSONObject result = new JSONObject(httpResult.getResponseContent());
                if (httpResult.getRequestType().equals("TimeSheets")) {
                    JSONObject dates = result.getJSONObject("dates");
//                    AndroidUtils.showAlert(data.toString(),getContext());
                    timeSheetsList.clear();
                    loadTimesheetData(dates,result);
                }else if(httpResult.getRequestType().equals("Tasks")){
                    JSONArray tasks = result.getJSONArray("tasks");
                    tasksList.clear();
                    loadTasks(tasks);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void loadTasks(JSONArray tasks) throws JSONException{
        for (int i=0;i<tasks.length();i++){
            JSONObject jsonObject = tasks.getJSONObject(i);
            TasksModel tasksModel = new TasksModel();
            tasksModel.setDisplayValue(jsonObject.getString("displayValue"));
            tasksModel.setReturnValue(jsonObject.getString("returnValue"));
            tasksList.add(tasksModel);

        }

        final CommonSpinnerAdapter spinner_adapter = new CommonSpinnerAdapter((Activity) getContext(), tasksList);
        sp_task.setAdapter(spinner_adapter);
        sp_task.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selected_task = tasksList.get(adapterView.getSelectedItemPosition()).getDisplayValue();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void loadTimesheetData(JSONObject dates, JSONObject result) throws JSONException {
        timeSheetsList.clear();
        TimeSheetModel timeSheetModel = new TimeSheetModel();
        timeSheetModel.setFrozen(dates.getBoolean("isFrozen"));
        timeSheetModel.setCurrentWeek(dates.getString("currentWeek"));
        timeSheetModel.setNextWeek(dates.getString("nextWeek"));
        timeSheetModel.setPrevWeek(dates.getString("prevWeek"));
        JSONObject timesheets = result.getJSONObject("timesheetList");
        JSONArray matters = timesheets.getJSONArray("matters");
        for (int i=0;i<matters.length();i++){
            JSONObject jsonObject = matters.getJSONObject(i);
            TSMatterModel matterModel  = new TSMatterModel();
            matterModel.setMattername(jsonObject.getString("matterName"));
            matterModel.setMatterid(jsonObject.getString("matterId"));
            if (jsonObject.has("matterType")){
                matterModel.setMatter_type(jsonObject.getString("matterType"));
            }
            matterList.add(matterModel);
        }
        timeSheetsList.add(timeSheetModel);

        for (int i = 0; i < timeSheetsList.size(); i++) {
//            AndroidUtils.showToast(String.valueOf(timeSheetsList.get(i).isFrozen()),getContext());
            if (timeSheetsList.get(i).isFrozen()) {
                view.setAlpha(0.5f);

//
                disableAllViews(view);
                AndroidUtils.showAlert("TimeSheet already submitted,please select other week", getContext());
//
            }
            else{
                final CommonSpinnerAdapter spinner_adapter = new CommonSpinnerAdapter((Activity) getContext(), matterList);

                sp_project.setAdapter(spinner_adapter);
                sp_project.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        selected_matter = matterList.get(adapterView.getSelectedItemPosition()).getMattername();
                        selected_matter_id = matterList.get(adapterView.getSelectedItemPosition()).getMatterid();
                   try{
                       selected_matter_type = matterList.get(adapterView.getSelectedItemPosition()).getMatter_type();
                   } catch (NullPointerException e) {
                       e.printStackTrace();
                   }
                   if (selected_matter_type!=null){
                       callTaskWebservice(selected_matter_type);
                   }else{
                       callTaskWebservice(selected_matter_id);
                   }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }
        }

    }

    private void callTaskWebservice(String selected_matter_type) {
        progressDialog = AndroidUtils.get_progress(getActivity());
        try{
            JSONObject jsonObject = new JSONObject();
            WebServiceHelper.callHttpWebService(this,getContext(), WebServiceHelper.RestMethodType.GET,"v3/event/tasks/"+selected_matter_type,"Tasks",jsonObject.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
