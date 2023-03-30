package com.digicoffer.lauditor.TimeSheets;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.digicoffer.lauditor.R;
import com.digicoffer.lauditor.TimeSheets.Models.TimeSheetModel;
import com.digicoffer.lauditor.Webservice.AsyncTaskCompleteListener;
import com.digicoffer.lauditor.Webservice.HttpResultDo;
import com.digicoffer.lauditor.Webservice.WebServiceHelper;
import com.digicoffer.lauditor.common.AndroidUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.pgpainless.key.selection.key.util.And;

import java.util.ArrayList;

public class NonSubmittedTimesheets extends Fragment implements AsyncTaskCompleteListener {
            AlertDialog progressDialog;
            ArrayList<TimeSheetModel> timeSheetsList = new ArrayList<>();
            boolean status = true;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.not_submitted_timesheet,container,false);
        callTimeSheetsWebservice();
        for (int i=0;i<timeSheetsList.size();i++){
            if (timeSheetsList.get(i).isFrozen()){
                status = true;
            }else
            {
                status = false;
            }
        }
        if (status == true){
            view.setAlpha(0.5f);
            AndroidUtils.showAlert("TimeSheet already submitted",getContext());
//
            disableAllViews(view);
//                OpenPopup();
            // Add a LinearLayout as the container
        }
        return view;
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

    private void callTimeSheetsWebservice() {
        try{
            progressDialog = AndroidUtils.get_progress(getActivity());
            JSONObject data = new JSONObject();
            WebServiceHelper.callHttpWebService(this,getContext(), WebServiceHelper.RestMethodType.GET,"v3/user/timesheets","TimeSheets",data.toString());
        }catch (Exception e){
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
if (progressDialog.isShowing()&&progressDialog!=null){
    AndroidUtils.dismiss_dialog(progressDialog);
}
if(httpResult.getResult()==WebServiceHelper.ServiceCallStatus.Success){
    try{
        JSONObject result = new JSONObject(httpResult.getResponseContent());
        if (httpResult.getRequestType().equals("TimeSheets")) {
            JSONObject dates = result.getJSONObject("dates");
//                    AndroidUtils.showAlert(data.toString(),getContext());
            loadTimesheetData(dates);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}
    }

    private void loadTimesheetData(JSONObject dates) throws JSONException {
        TimeSheetModel timeSheetModel = new TimeSheetModel();
        timeSheetModel.setFrozen(dates.getBoolean("isFrozen"));
        timeSheetModel.setCurrentWeek(dates.getString("currentWeek"));
        timeSheetModel.setNextWeek(dates.getString("nextWeek"));
        timeSheetModel.setPrevWeek(dates.getString("prevWeek"));
        timeSheetsList.add(timeSheetModel);
    }
}
