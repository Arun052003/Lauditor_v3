package com.digicoffer.lauditor.TimeSheets;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.digicoffer.lauditor.R;
import com.digicoffer.lauditor.TimeSheets.Adapters.ProjectAdapter;
import com.digicoffer.lauditor.TimeSheets.Models.ProjectTMModel;
import com.digicoffer.lauditor.TimeSheets.Models.ProjectsModel;
import com.digicoffer.lauditor.Webservice.AsyncTaskCompleteListener;
import com.digicoffer.lauditor.Webservice.HttpResultDo;
import com.digicoffer.lauditor.Webservice.WebServiceHelper;
import com.digicoffer.lauditor.common.AndroidUtils;
import com.digicoffer.lauditor.common_adapters.CommonSpinnerAdapter;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class AGS_Projects extends Fragment implements AsyncTaskCompleteListener {
    private Spinner sp_ags_project, sp_ags_tm;
    private TextInputEditText et_search_matter;
    private RecyclerView rv_projects;
    private String date;
    private TextView tv_billable_hours,tv_non_billable_hours,tv_total_project_hours;
    private AlertDialog progress_dialog;
    private ArrayList<ProjectsModel> projectsList = new ArrayList<>();
    private String selected_project;
    private ArrayList<ProjectTMModel> projectTmList = new ArrayList<>();
    private String selected_tm;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ags_projects, container, false);
        Bundle bundle = getArguments();
        date = bundle.getString("date");
        sp_ags_project = view.findViewById(R.id.sp_ags_project);
        sp_ags_tm = view.findViewById(R.id.sp_ags_tm);
        et_search_matter = view.findViewById(R.id.et_search_matter);
        rv_projects = view.findViewById(R.id.rv_projects);
        tv_billable_hours = view.findViewById(R.id.tv_billable_hours);
        tv_non_billable_hours = view.findViewById(R.id.tv_non_billable_hours);
        tv_total_project_hours = view.findViewById(R.id.tv_total_project_hours);

        try {
            callProjectsWebService();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return view;
    }

    private void callProjectsWebService() throws ParseException {
        progress_dialog = AndroidUtils.get_progress(getActivity());
        JSONObject postdata = new JSONObject();

        if (date.equals("") || date.equals(null)) {
            WebServiceHelper.callHttpWebService(this, getContext(), WebServiceHelper.RestMethodType.GET, "matter/timesheets/project-all-weekly", "Projects", postdata.toString());
        } else {
//            AndroidUtils.showToast(String.valueOf(date_status),getContext());
            SimpleDateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
            SimpleDateFormat outputFormat = new SimpleDateFormat("ddMMyyyy", Locale.US);
            Date new_date = inputFormat.parse(date);
            String outputDate = outputFormat.format(new_date);
            WebServiceHelper.callHttpWebService(this, getContext(), WebServiceHelper.RestMethodType.GET, "matter/timesheets/project-all-weekly" + "-" + outputDate, "Projects", postdata.toString());

        }
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onAsyncTaskComplete(HttpResultDo httpResult) {
        if (progress_dialog.isShowing() && progress_dialog != null) {
            AndroidUtils.dismiss_dialog(progress_dialog);
        }
        if (httpResult.getResult() == WebServiceHelper.ServiceCallStatus.Success) {
            try {
                JSONObject result = new JSONObject(httpResult.getResponseContent());

                if (httpResult.getRequestType().equals("Projects")) {
                    JSONObject jsonObject = result.getJSONObject("timesheets");
                    JSONArray jsonArray  = jsonObject.getJSONArray("data");
                    JSONObject grandtotal = jsonObject.getJSONObject("grandTotal");
                    loadProjects(jsonArray,grandtotal);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void loadProjects(JSONArray jsonArray, JSONObject grandtotal) throws JSONException {
        for (int i=0;i<jsonArray.length();i++){
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            ProjectsModel projectsModel = new ProjectsModel();
            projectsModel.setCaseNo(jsonObject.getString("caseNo"));
            projectsModel.setProjectName(jsonObject.getString("projectName"));
            projectsModel.setClientNames(jsonObject.getJSONArray("clientNames"));
            projectsModel.setMatterId(jsonObject.getString("matterId"));
            projectsModel.setTeamMembers(jsonObject.getJSONArray("teamMembers"));
            projectsList.add(projectsModel);
        }
        loadProjectsRecyclerview(grandtotal);
    }

    private void loadProjectsRecyclerview(JSONObject grandtotal) throws JSONException {
        tv_billable_hours.setText(grandtotal.getString("billable"));
        tv_non_billable_hours.setText(grandtotal.getString("nonbillable"));
        tv_total_project_hours.setText(grandtotal.getString("total"));
        final CommonSpinnerAdapter spinner_adapter = new CommonSpinnerAdapter((Activity) getContext(), projectsList);
        sp_ags_project.setAdapter(spinner_adapter);
        sp_ags_project.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
               projectTmList.clear();
                selected_project = projectsList.get(adapterView.getSelectedItemPosition()).getProjectName();
               try {
                   for (int i = 0; i < projectsList.size(); i++) {

                       for (int j = 0; j < projectsList.get(i).getTeamMembers().length(); j++) {
                           if (selected_project.equals(projectsList.get(i).getProjectName())) {
                               JSONObject jsonObject = projectsList.get(i).getTeamMembers().getJSONObject(i);
                               ProjectTMModel projectTMModel = new ProjectTMModel();
                               projectTMModel.setBillableHours(jsonObject.getString("billableHours"));
                               projectTMModel.setName(jsonObject.getString("name"));
                               projectTMModel.setNonBillablehours(jsonObject.getString("nonBillablehours"));
                               projectTMModel.setTotal(jsonObject.getString("total"));
                               projectTmList.add(projectTMModel);
                           }
                       }
                   }
               } catch (JSONException e) {
                   e.printStackTrace();
               }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        final CommonSpinnerAdapter status_adapter = new CommonSpinnerAdapter((Activity) getContext(), projectTmList);
        sp_ags_tm.setAdapter(status_adapter);
        sp_ags_tm.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selected_tm = projectTmList.get(adapterView.getSelectedItemPosition()).getName();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
            loadRecyclerview();
    }

    private void loadRecyclerview() {
        rv_projects.setLayoutManager(new GridLayoutManager(getContext(),1));
        ProjectAdapter projectAdapter = new ProjectAdapter(projectsList,projectTmList,getContext());
        rv_projects.setAdapter(projectAdapter);
        rv_projects.setHasFixedSize(true);
    }
}
