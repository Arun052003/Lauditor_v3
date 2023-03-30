package com.digicoffer.lauditor.TimeSheets;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.digicoffer.lauditor.R;
import com.digicoffer.lauditor.TimeSheets.Models.TimeSheetModel;
import com.digicoffer.lauditor.Webservice.AsyncTaskCompleteListener;
import com.digicoffer.lauditor.Webservice.HttpResultDo;
import com.digicoffer.lauditor.Webservice.WebServiceHelper;
import com.digicoffer.lauditor.common.AndroidUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class NonSubmittedTimesheets extends Fragment implements AsyncTaskCompleteListener {
    AlertDialog progressDialog;
    ArrayList<TimeSheetModel> timeSheetsList = new ArrayList<>();
    boolean status = true;
    boolean date_status = false;
    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.not_submitted_timesheet, container, false);
//        if()
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
                    loadTimesheetData(dates);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void loadTimesheetData(JSONObject dates) throws JSONException {
        timeSheetsList.clear();
        TimeSheetModel timeSheetModel = new TimeSheetModel();
        timeSheetModel.setFrozen(dates.getBoolean("isFrozen"));
        timeSheetModel.setCurrentWeek(dates.getString("currentWeek"));
        timeSheetModel.setNextWeek(dates.getString("nextWeek"));
        timeSheetModel.setPrevWeek(dates.getString("prevWeek"));
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
        }
    }
}
