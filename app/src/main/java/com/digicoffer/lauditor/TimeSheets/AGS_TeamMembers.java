package com.digicoffer.lauditor.TimeSheets;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.digicoffer.lauditor.R;
import com.digicoffer.lauditor.TimeSheets.Models.TMModel;
import com.digicoffer.lauditor.Webservice.AsyncTaskCompleteListener;
import com.digicoffer.lauditor.Webservice.HttpResultDo;
import com.digicoffer.lauditor.Webservice.WebServiceHelper;
import com.digicoffer.lauditor.common.AndroidUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class AGS_TeamMembers extends Fragment implements AsyncTaskCompleteListener {
    private TextView tv_matter_name, tv_matter_hours, tv_matter_minutes, tv_matter_billable, tv_matter_task_name, tv_total_hours;
    LinearLayout ll_total_hours;
    AlertDialog progress_dialog;
    ArrayList<TMModel> teamList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.weekly_timesheets, container, false);
        tv_matter_billable = view.findViewById(R.id.tv_matter_billable);
        tv_matter_hours = view.findViewById(R.id.tv_matter_hours);
        tv_matter_minutes = view.findViewById(R.id.tv_matter_minutes);
        tv_matter_name = view.findViewById(R.id.tv_matter_name);
        tv_matter_task_name = view.findViewById(R.id.tv_matter_task_name);
        tv_total_hours = view.findViewById(R.id.tv_total_hours);
        callTeamMembersWebservice();
        return view;
    }

    private void callTeamMembersWebservice() {
        progress_dialog = AndroidUtils.get_progress(getActivity());
        JSONObject postdata = new JSONObject();
        WebServiceHelper.callHttpWebService(this, getContext(), WebServiceHelper.RestMethodType.GET, "matter/timesheets/tms-all-weekly", "Team Members", postdata.toString());

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
                if (httpResult.getRequestType().equals("Team Members")) {
                    JSONArray jsonArray = result.getJSONArray("timesheets");
                    loadTmData(jsonArray);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void loadTmData(JSONArray jsonArray) throws JSONException {
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            TMModel tmModel = new TMModel();
            tmModel.setId(jsonObject.getString("id"));
            tmModel.setName(jsonObject.getString("name"));
            tmModel.setTb(jsonObject.getString("tb"));
            tmModel.setTnb(jsonObject.getString("tnb"));
            tmModel.setTotal(jsonObject.getString("total"));
            teamList.add(tmModel);
        }
        loadRecyclerview();
    }

    private void loadRecyclerview() {
    }
}
