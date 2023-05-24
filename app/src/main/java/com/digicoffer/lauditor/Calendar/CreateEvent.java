package com.digicoffer.lauditor.Calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.digicoffer.lauditor.Calendar.Models.CalendarDo;
import com.digicoffer.lauditor.Matter.Models.ViewMatterModel;
import com.digicoffer.lauditor.Matter.ViewMatter;
import com.digicoffer.lauditor.R;
import com.digicoffer.lauditor.Webservice.AsyncTaskCompleteListener;
import com.digicoffer.lauditor.Webservice.HttpResultDo;
import com.digicoffer.lauditor.Webservice.WebServiceHelper;
import com.digicoffer.lauditor.common.AndroidUtils;
import com.digicoffer.lauditor.common.Constants;
import com.digicoffer.lauditor.common_adapters.CommonSpinnerAdapter;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.minidns.record.A;
import org.pgpainless.key.selection.key.util.And;

import java.util.ArrayList;
import java.util.Locale;

public class CreateEvent extends Fragment implements AsyncTaskCompleteListener {
    private AlertDialog progressDialog;
    @Override
    public void onClick(View view) {

    }
    private Spinner sp_project,sp_matter_name,sp_task,sp_time_zone,sp_repetetion;
    private TextInputEditText tv_event_creation_date,tv_event_start_time,tv_event_end_time,tv_meeting_link,tv_dialing_number,tv_location,tv_description;
    private AppCompatButton add_notification,btn_cancel_timesheet,btn_save_timesheet;
    private static String selected_project;
    private static String selected_matter;
    ArrayList<CalendarDo> projectList = new ArrayList<>();
    ArrayList<ViewMatterModel>matterList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.create_event,container,false);
        sp_project = view.findViewById(R.id.sp_project);
        sp_matter_name = view.findViewById(R.id.sp_matter_name);
        sp_task = view.findViewById(R.id.sp_task);
        sp_time_zone = view.findViewById(R.id.sp_time_zone);
        sp_repetetion = view.findViewById(R.id.sp_repetetion);
        tv_event_creation_date = view.findViewById(R.id.tv_event_creation_date);
        tv_event_start_time = view.findViewById(R.id.tv_event_start_time);
        tv_event_end_time = view.findViewById(R.id.tv_event_end_time);
        tv_meeting_link = view.findViewById(R.id.tv_meeting_link);
        tv_dialing_number = view.findViewById(R.id.tv_dialing_number);
        tv_location = view.findViewById(R.id.tv_dialing_number);
        tv_description = view.findViewById(R.id.tv_description);

        projectList.clear();
        CalendarDo LegalMatter = new CalendarDo("Legal Matter");
        CalendarDo GeneralMatter = new CalendarDo("General Matter");
        CalendarDo overhead = new CalendarDo("Overhead");
        CalendarDo Others = new CalendarDo("Others");
        CalendarDo reminders = new CalendarDo("Reminders");

        projectList.add(LegalMatter);
        projectList.add(GeneralMatter);
        projectList.add(overhead);
        projectList.add(Others);
        projectList.add(reminders);

        final CommonSpinnerAdapter spinner_adapter = new CommonSpinnerAdapter((Activity) getContext(), projectList);

        sp_project.setAdapter(spinner_adapter);
        sp_project.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selected_project = projectList.get(adapterView.getSelectedItemPosition()).getProjectName();
                loadProjectData(selected_project);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        return view;
    }

    private void loadProjectData(String selected_project) {
            switch (selected_project){

                case "Legal Matter":
                    String project_type = "legal";
                    callProjectWebservice(project_type);
                    break;
                case "General Matter":
                    break;
                case "Overhead":
                    break;
                case "Others":
                    break;
                case "Reminders":
                    break;
            }
    }

    private void callProjectWebservice(String selected_project) {
        try {
            progressDialog = AndroidUtils.get_progress(getActivity());
            JSONObject postdata = new JSONObject();
            WebServiceHelper.callHttpWebService(this, getContext(), WebServiceHelper.RestMethodType.GET, "matter/" + selected_project, "Matter List", postdata.toString());
        } catch (Exception e) {
            if (progressDialog != null && progressDialog.isShowing()) {
                AndroidUtils.dismiss_dialog(progressDialog);
            }
            e.printStackTrace();
        }
    }

    @Override
    public void onAsyncTaskComplete(HttpResultDo httpResult) {
        if (progressDialog.isShowing() && progressDialog != null) {
            AndroidUtils.dismiss_dialog(progressDialog);
        }
        if (httpResult.getResult() == WebServiceHelper.ServiceCallStatus.Success) {
            try {
                JSONObject result = new JSONObject(httpResult.getResponseContent());
                boolean error = result.getBoolean("error");
                if (httpResult.getRequestType().equals("Matter List")) {
                    if (error) {
                        String msg = result.getString("msg");
                        AndroidUtils.showToast(msg, getContext());
                    } else {
                        JSONArray matters = result.getJSONArray("matters");
                        try {
                            loadMattersList(matters);
                        } catch (Exception e) {
                            AndroidUtils.showAlert(e.getMessage(),getContext());
                            e.printStackTrace();
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else{
            AndroidUtils.showAlert("Something went wrong",getContext());
        }
    }

    private void loadMattersList(JSONArray matters) throws JSONException {
        matterList.clear();
        for (int i=0;i<matters.length();i++){
            JSONObject jsonObject = matters.getJSONObject(i);
            ViewMatterModel viewMatterModel = new ViewMatterModel();
            viewMatterModel.setId(jsonObject.getString("id"));
            viewMatterModel.setTitle(jsonObject.getString("title"));
            matterList.add(viewMatterModel);
        }
        loadMatterSpinnerList(matterList);
    }

    private void loadMatterSpinnerList(ArrayList<ViewMatterModel> matterList) {

        final CommonSpinnerAdapter spinner_adapter = new CommonSpinnerAdapter((Activity) getContext(), matterList);

        sp_matter_name.setAdapter(spinner_adapter);
        sp_matter_name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selected_matter = matterList.get(adapterView.getSelectedItemPosition()).getId();
//                loadProjectData(selected_project);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

}
