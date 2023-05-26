package com.digicoffer.lauditor.Calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.digicoffer.lauditor.Calendar.Models.CalendarDo;
import com.digicoffer.lauditor.Calendar.Models.TaskDo;
import com.digicoffer.lauditor.Matter.Models.ViewMatterModel;
import com.digicoffer.lauditor.R;
import com.digicoffer.lauditor.Webservice.AsyncTaskCompleteListener;
import com.digicoffer.lauditor.Webservice.HttpResultDo;
import com.digicoffer.lauditor.Webservice.WebServiceHelper;
import com.digicoffer.lauditor.common.AndroidUtils;
import com.digicoffer.lauditor.common_adapters.CommonSpinnerAdapter;
import com.digicoffer.lauditor.common_objects.TimeZonesDO;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.Locale;

public class CreateEvent extends Fragment implements AsyncTaskCompleteListener,View.OnClickListener {
    private AlertDialog progressDialog;


    final int[] mHour = new int[1];
    final int[] mMinute = new int[1];
    private boolean timeZonesTaskCompleted = false;
    final String[] AM_PM = new String[1];
    int offset;
    private Spinner sp_project, sp_matter_name, sp_task, sp_time_zone, sp_repetetion;
    private TextInputEditText tv_event_creation_date, tv_event_start_time, tv_event_end_time, tv_meeting_link, tv_dialing_number, tv_location, tv_description;
    private AppCompatButton add_notification, btn_cancel_timesheet, btn_save_timesheet;
    private String selected_project;
    private String selected_matter;
    private String selected_task;
    Hashtable<String, Integer> timesPosHash = new Hashtable<>();
    ArrayList<TimeZonesDO> timeZonesList = new ArrayList<TimeZonesDO>();
    private LinearLayout ll_project,ll_matter_name,ll_message,ll_task;
    ArrayList<CalendarDo> projectList = new ArrayList<>();
    ArrayList<ViewMatterModel> matterList = new ArrayList<>();
    ArrayList<TaskDo> legalTaksList = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        callTimeZoneWebservice();
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.create_event, container, false);
        sp_project = view.findViewById(R.id.sp_project);
        sp_matter_name = view.findViewById(R.id.sp_matter_name);
        sp_task = view.findViewById(R.id.sp_task);
        sp_time_zone = view.findViewById(R.id.sp_time_zone);
        sp_repetetion = view.findViewById(R.id.sp_repetetion);
        tv_event_creation_date = view.findViewById(R.id.tv_event_creation_date);
        tv_event_creation_date.setOnClickListener(this);
        tv_event_start_time = view.findViewById(R.id.tv_event_start_time);
        tv_event_start_time.setOnClickListener(this);
        tv_event_end_time = view.findViewById(R.id.tv_event_end_time);
        tv_event_end_time.setOnClickListener(this);
        tv_meeting_link = view.findViewById(R.id.tv_meeting_link);
        tv_dialing_number = view.findViewById(R.id.tv_dialing_number);
        tv_location = view.findViewById(R.id.tv_dialing_number);
        tv_description = view.findViewById(R.id.tv_description);
        ll_project = view.findViewById(R.id.ll_project);
        ll_matter_name = view.findViewById(R.id.ll_matter_name);
        ll_message = view.findViewById(R.id.ll_message);
        ll_task = view.findViewById(R.id.ll_task);
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
        legalTaksList.clear();
//        callTimeZoneWebservice();
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
    private void callTimeZoneWebservice() {
        try {


            progressDialog = AndroidUtils.get_progress(getActivity());
            JSONObject postData = new JSONObject();
            WebServiceHelper.callHttpWebService(this, getContext(), WebServiceHelper.RestMethodType.GET, "event/timezones", "TIMEZONES", postData.toString());
        } catch (Exception e) {
            if (progressDialog != null && progressDialog.isShowing()) {
                AndroidUtils.dismiss_dialog(progressDialog);
            }
            e.printStackTrace();
        }
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_event_creation_date:

                final Calendar myCalendar = Calendar.getInstance();
                final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, month);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        updateLabel();
                    }

                    private void updateLabel() {
                        String myFormat = "MM-dd-yyyy";
                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                        tv_event_creation_date.setText(sdf.format(myCalendar.getTime()));
                    }
                };
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), date, myCalendar.get(java.util.Calendar.YEAR), myCalendar.get(java.util.Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
                break;
            case R.id.tv_event_start_time:

                final Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);

                final TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                if (hourOfDay < 12) {
                                    AM_PM[0] = "AM";
                                } else {
                                    AM_PM[0] = "PM";
                                }

                                tv_event_start_time.setText(hourOfDay + ":" + minute + "" + AM_PM[0]);

                            }
                        }, hour, minute, true);
                timePickerDialog.show();
                break;
            case R.id.tv_event_end_time:
                final Calendar calendar = Calendar.getInstance();
                mHour[0] = calendar.get(Calendar.HOUR_OF_DAY);
                mMinute[0] = calendar.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                final TimePickerDialog timePickerDialog_end_time = new TimePickerDialog(getContext(),
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                final Calendar c = Calendar.getInstance();
                                mHour[0] = c.get(Calendar.HOUR_OF_DAY);
                                mMinute[0] = c.get(Calendar.MINUTE);
                                if (hourOfDay < 12) {
                                    AM_PM[0] = "AM";
                                } else {
                                    AM_PM[0] = "PM";
                                }

//                                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss",Locale.ENGLISH);
                                tv_event_end_time.setText(hourOfDay + ":" + minute + "" + AM_PM[0]);

                            }
                        }, mHour[0], mMinute[0], true);
                timePickerDialog_end_time.show();
                break;
        }
    }
    private void loadProjectData(String selected_project) {
        switch (selected_project) {

            case "Legal Matter":
                legalTaksList.clear();
                unHideMatterDetails();
                String matter_legal = "legal";
                callProjectWebservice(matter_legal);
                TaskDo caseFilling = new TaskDo("Case Filling");
                TaskDo consultation = new TaskDo("Consultation");
                TaskDo clb = new TaskDo("Creating Legal Breifs");
                TaskDo mwc = new TaskDo("Meeting with client");
                TaskDo hearing = new TaskDo("Hearing");
                legalTaksList.add(caseFilling);
                legalTaksList.add(consultation);
                legalTaksList.add(clb);
                legalTaksList.add(mwc);
                legalTaksList.add(hearing);
                loadTaskList(legalTaksList);
                break;
            case "General Matter":
                legalTaksList.clear();
                unHideMatterDetails();
                String matter_general = "general";
                callProjectWebservice(matter_general);
                TaskDo consultation_general = new TaskDo("Consultation");
                TaskDo draft_agreements = new TaskDo("Draft agreements");
                TaskDo fwa = new TaskDo("Filling with authorities");
                TaskDo mwc_general = new TaskDo("Meeting with client");
                TaskDo paf = new TaskDo("Prepare annual fillings");
                legalTaksList.add(consultation_general);
                legalTaksList.add(draft_agreements);
                legalTaksList.add(fwa);
                legalTaksList.add(mwc_general);
                legalTaksList.add(paf);
                loadTaskList(legalTaksList);
                break;
            case "Overhead":
                legalTaksList.clear();
                hideMatterDetails();
                TaskDo conference = new TaskDo("Conference");
                TaskDo holidays = new TaskDo("Holidays");
                TaskDo research = new TaskDo("Research");
                TaskDo training = new TaskDo("Training");
                TaskDo vacation = new TaskDo("Vacation");
                legalTaksList.add(conference);
                legalTaksList.add(holidays);
                legalTaksList.add(research);
                legalTaksList.add(training);
                legalTaksList.add(vacation);
                loadTaskList(legalTaksList);
                break;
            case "Others":
                legalTaksList.clear();
                hideMatterDetails();
                TaskDo business_development = new TaskDo("Business Development");
                TaskDo personal = new TaskDo("Personal");
                TaskDo doctor_appointment = new TaskDo("Doctor Appointment");
                TaskDo lunch_dinner = new TaskDo("Lunch/Dinner");
                TaskDo misc = new TaskDo("Misc");
                legalTaksList.add(business_development);
                legalTaksList.add(personal);
                legalTaksList.add(doctor_appointment);
                legalTaksList.add(lunch_dinner);
                legalTaksList.add(misc);
                loadTaskList(legalTaksList);
                break;
            case "Reminders":
                legalTaksList.clear();
                hideAlldetails();

                break;
        }
    }

    private void hideAlldetails() {
        ll_matter_name.setVisibility(View.GONE);
        ll_message.setVisibility(View.VISIBLE);
        ll_task.setVisibility(View.GONE);
    }

    private void hideMatterDetails() {

        ll_matter_name.setVisibility(View.GONE);
        ll_message.setVisibility(View.GONE);
        ll_task.setVisibility(View.VISIBLE);
    }

    private void unHideMatterDetails() {

        ll_matter_name.setVisibility(View.VISIBLE);
        ll_message.setVisibility(View.GONE);
        ll_task.setVisibility(View.VISIBLE);
    }

    private void loadTaskList(ArrayList<TaskDo> legalTaksList) {
        final CommonSpinnerAdapter spinner_adapter = new CommonSpinnerAdapter((Activity) getContext(), legalTaksList);

        sp_task.setAdapter(spinner_adapter);
        sp_task.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selected_task = legalTaksList.get(adapterView.getSelectedItemPosition()).getTaskName();

//                loadProjectData(selected_project);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void callProjectWebservice(String selected_project) {
        try {
            progressDialog = AndroidUtils.get_progress(getActivity());
            JSONObject postdata = new JSONObject();
            WebServiceHelper.callHttpWebService(this, getContext(), WebServiceHelper.RestMethodType.GET, "v3/matter/" + selected_project, "Matter List", postdata.toString());
        } catch (Exception e) {
            AndroidUtils.showToast(e.getMessage(), getContext());
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
//                boolean error = result.getBoolean("error");
                if (httpResult.getRequestType().equals("Matter List")) {
                    if (!timeZonesTaskCompleted) {

                        return;
                    }
                    JSONArray matters = result.getJSONArray("matters");
                    try {
                        loadMattersList(matters);
                    } catch (Exception e) {
                        AndroidUtils.showAlert(e.getMessage(), getContext());
                        e.printStackTrace();
                    }
                }else  if (httpResult.getRequestType().equals("TIMEZONES")) {
                    if (!result.getBoolean("error")) {

                        JSONArray jsonArray = result.getJSONArray("timezones");
                        load_timezones(jsonArray);
//                        Thre
                        timeZonesTaskCompleted = true;
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            AndroidUtils.showAlert("Something went wrong", getContext());
        }
    }
    private void load_timezones(JSONArray jsonArray) throws JSONException {
        TimeZonesDO timeZonesDO;
        timeZonesList.clear();
        timesPosHash.clear();
        for (int i = 0; i < jsonArray.length(); i++) {
            timeZonesDO = new TimeZonesDO();
            timeZonesDO.setNAME(String.valueOf(jsonArray.getJSONArray(i).get(1)));
            timeZonesDO.setGMT(String.valueOf(jsonArray.getJSONArray(i).get(0)));
            timeZonesList.add(timeZonesDO);
            timesPosHash.put(String.valueOf(jsonArray.getJSONArray(i).get(0)), i);
        }
        loadTimeZonespinner();
//        callLegalwebservice();

    }

    private void loadTimeZonespinner() {
        final CommonSpinnerAdapter adapter = new CommonSpinnerAdapter(getActivity(), timeZonesList);
        sp_time_zone.setAdapter(adapter);
//        callTimeZoneWebservice();
        sp_time_zone.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                offset = Integer.parseInt(timeZonesList.get(sp_time_zone.getSelectedItemPosition()).getGMT());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void loadMattersList(JSONArray matters) throws JSONException {
        matterList.clear();
//        AndroidUtils.showAlert(matters.toString(),getContext());
        for (int i = 0; i < matters.length(); i++) {
            JSONObject jsonObject = matters.getJSONObject(i);
            ViewMatterModel viewMatterModel = new ViewMatterModel();
            viewMatterModel.setId(jsonObject.getString("id"));
            viewMatterModel.setTitle(jsonObject.getString("title"));
            matterList.add(viewMatterModel);
        }
//        AndroidUtils.showAlert(matters.toString(),getContext());
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
