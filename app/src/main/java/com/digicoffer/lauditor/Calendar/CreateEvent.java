package com.digicoffer.lauditor.Calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.digicoffer.lauditor.Calendar.Models.CalendarDo;
import com.digicoffer.lauditor.Calendar.Models.MinutesDO;
import com.digicoffer.lauditor.Calendar.Models.RelationshipsDO;
import com.digicoffer.lauditor.Calendar.Models.TaskDo;
import com.digicoffer.lauditor.Calendar.Models.TeamDo;
import com.digicoffer.lauditor.Matter.Models.ViewMatterModel;
import com.digicoffer.lauditor.R;
import com.digicoffer.lauditor.Webservice.AsyncTaskCompleteListener;
import com.digicoffer.lauditor.Webservice.HttpResultDo;
import com.digicoffer.lauditor.Webservice.WebServiceHelper;
import com.digicoffer.lauditor.common.AndroidUtils;
import com.digicoffer.lauditor.common.Constants;
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

public class CreateEvent extends Fragment implements AsyncTaskCompleteListener, View.OnClickListener {
    private static String ADAPTER_TAG = "";
    private AlertDialog progressDialog;
    MultiAutoCompleteTextView at_family_members;
    final ArrayList<String> Repetetions = new ArrayList<>();
    JSONArray notification = new JSONArray();
    LinearLayout ll_client_team_members, ll_selected_entites, selected_individual, ll_individual_list, ll_selected_team_members, selected_tm, selected_groups;
    final int[] mHour = new int[1];
    final int[] mMinute = new int[1];
    private ArrayList<String> selectedValues = new ArrayList<>();
    String selected_hour_type = "";
    private boolean timeZonesTaskCompleted = false;

    private boolean matterTaskCompleted = false;
    private boolean tmTaskCompleted = false;
    final String[] AM_PM = new String[1];
    int offset;
    private Button btn_add_groups, btn_add_clients, btn_assigned_team_members, btn_individual;
    String team_member_id;
    int adapter_position = 0;
    ArrayList<RelationshipsDO> entities_list = new ArrayList<>();
    ArrayList<RelationshipsDO> individual_list = new ArrayList<>();
    ArrayList<RelationshipsDO> selected_individual_list = new ArrayList<>();
    ArrayList<RelationshipsDO> selected_entity_client_list = new ArrayList<>();
    ArrayList<RelationshipsDO> new_selected_client_list = new ArrayList<>();
    ArrayList<RelationshipsDO> entity_client_list = new ArrayList<>();
    ArrayList<TeamDo> selected_tm_list = new ArrayList<>();
    private TextView at_add_groups, at_add_clients, at_assigned_team_members, at_individual;
    private Spinner sp_entities, sp_project, sp_matter_name, sp_task, sp_time_zone, sp_repetetion, sp_add_team_member, sp_add_entity, sp_client_team_members;
    private TextInputEditText tv_event_creation_date, tv_event_start_time, tv_event_end_time, tv_meeting_link, tv_dialing_number, tv_location, tv_description;
    private AppCompatButton add_notification, btn_cancel_timesheet, btn_save_timesheet;
    private String selected_project;
    private String selected_matter;
    private String selected_task;

    Hashtable<String, Integer> timesPosHash = new Hashtable<>();
    ArrayList<TimeZonesDO> timeZonesList = new ArrayList<TimeZonesDO>();
    private LinearLayout ll_project, ll_matter_name, ll_message, ll_task, ll_add_notification;
    ArrayList<CalendarDo> projectList = new ArrayList<>();
    ArrayList<ViewMatterModel> matterList = new ArrayList<>();
    ArrayList<TeamDo> teamList = new ArrayList<>();
    ArrayList<TaskDo> legalTaksList = new ArrayList<>();
    private String repeat_interval;

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
        at_individual = view.findViewById(R.id.at_individual);
        sp_task = view.findViewById(R.id.sp_task);
        sp_time_zone = view.findViewById(R.id.sp_time_zone);
        sp_repetetion = view.findViewById(R.id.sp_repetetion);
        at_add_groups = view.findViewById(R.id.at_add_groups);
        ll_add_notification = view.findViewById(R.id.ll_add_notification);
        sp_entities = view.findViewById(R.id.sp_entities);
        add_notification = view.findViewById(R.id.add_notification);
        add_notification.setOnClickListener(this);
        at_assigned_team_members = view.findViewById(R.id.at_assigned_team_members);
        btn_add_groups = view.findViewById(R.id.btn_add_groups);
        selected_groups = view.findViewById(R.id.selected_groups);
        btn_individual = view.findViewById(R.id.btn_individual);
        selected_individual = view.findViewById(R.id.selected_individual);
        ll_individual_list = view.findViewById(R.id.ll_individual_list);
        btn_individual.setOnClickListener(this);
        btn_add_groups.setOnClickListener(this);
//        btn_add_clients = view.findViewById(R.id.btn_add_clients);
//        btn_add_clients.setOnClickListener(this);
        selected_tm = view.findViewById(R.id.selected_tm);
        ll_client_team_members = view.findViewById(R.id.ll_client_team_members);
        ll_selected_entites = view.findViewById(R.id.ll_selected_entites);
        ll_selected_team_members = view.findViewById(R.id.ll_selected_team_members);
        btn_assigned_team_members = view.findViewById(R.id.btn_assigned_team_members);
        btn_assigned_team_members.setOnClickListener(this);
//        at_assigned_team_members = view.findViewById(R.id.at_assigned_team_members);
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
//        CalendarDo LegalMatter = new CalendarDo("Legal Matter");
//        CalendarDo GeneralMatter = new CalendarDo("General Matter");
//        CalendarDo overhead = new CalendarDo("Overhead");
//        CalendarDo Others = new CalendarDo("Others");
//        CalendarDo reminders = new CalendarDo("Reminders");
        if (Constants.ROLE.equals("AAM")) {

            projectList.add(new CalendarDo("Overhead"));
            projectList.add(new CalendarDo("Others"));
            projectList.add(new CalendarDo("Reminders"));


        } else {
            projectList.add(new CalendarDo("Legal Matter"));
            projectList.add(new CalendarDo("General Matter"));
            projectList.add(new CalendarDo("Overhead"));
            projectList.add(new CalendarDo("Others"));
            projectList.add(new CalendarDo("Reminders"));
        }

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
            case R.id.btn_add_groups:
                callTeamMemberWebservice();
                break;
            case R.id.btn_individual:
                load_individual_Popup();
                break;
            case R.id.btn_assigned_team_members:
                loadEntityClientPopup();
                break;
            case R.id.add_notification:
                NotificationPopup();
                break;
        }

    }

    private void NotificationPopup() {
        View view_opponents = LayoutInflater.from(getContext()).inflate(R.layout.add_calendar_notification, null);
        Spinner sp_minutes = view_opponents.findViewById(R.id.sp_minutes);
        ArrayList<MinutesDO> minutes_list = new ArrayList<>();
        minutes_list.add(new MinutesDO("Minutes"));
        minutes_list.add(new MinutesDO("Hours"));
        minutes_list.add(new MinutesDO("Days"));
        minutes_list.add(new MinutesDO("Weeks"));
        TextInputEditText tv_numbers = view_opponents.findViewById(R.id.tv_numbers);
        ImageView iv_delete_notification = view_opponents.findViewById(R.id.iv_delete_notification);
        tv_numbers.setText("1");
        final int position =  ll_add_notification.getChildCount();
        ll_add_notification.setTag(position);
        final CommonSpinnerAdapter spinner_adapter = new CommonSpinnerAdapter((Activity) getContext(), minutes_list);
        sp_minutes.setAdapter(spinner_adapter);
        sp_minutes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selected_hour_type = minutes_list.get(adapterView.getSelectedItemPosition()).getName();
                selectedValues.clear();
                loadnewInput(tv_numbers);
                View selectedView = ll_add_notification.getChildAt(i);
                AppCompatSpinner selectedSpinnerTextView = selectedView.findViewById(R.id.sp_minutes);
                selectedSpinnerTextView.setSelection(i);
                if (i >= 0 && i < selectedValues.size()) {
                    selectedValues.set(i, selected_hour_type + ": " + tv_numbers.getText().toString());
                }
//                loadUpdatedInput(tv_numbers);
//              position = i;

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
//        iv_delete_notification.setTag();
        iv_delete_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = ll_add_notification.indexOfChild(view_opponents);

                // Remove the view from the LinearLayout
                ll_add_notification.removeView(view_opponents);

                // Remove the view from the ArrayList
//                selectedValues.remove(position);
            }
        });

        ll_add_notification.addView(view_opponents);
        selectedValues.add(selected_hour_type + ": " + tv_numbers.getText().toString());

    }

    private void loadnewInput(TextInputEditText tv_numbers) {
        View parentView = (View) tv_numbers.getParent();
        TextView inputFieldValueTextView = parentView.findViewById(R.id.tv_numbers);
        inputFieldValueTextView.setText(tv_numbers.getText().toString());
        int position = ll_add_notification.indexOfChild(parentView);
        if (position >= 0 && position < selectedValues.size()) {
            // Update the input field value in the ArrayList
            selectedValues.set(position, selected_hour_type + ": " + tv_numbers.getText().toString());
        }
    }

    private void loadUpdatedInput(TextInputEditText tv_numbers) {
        tv_numbers.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Do nothing

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Do nothing
            }

            @Override
            public void afterTextChanged(Editable s) {
                loadTextchangedData(s,tv_numbers,selected_hour_type);
            }
        });
    }

    private void loadTextchangedData(Editable s, TextInputEditText tv_numbers, String selected_hour_type){
//        if (!s.toString().isEmpty()) {
            int number = Integer.parseInt(tv_numbers.getText().toString());
//            if (number > 4) {
//                tv_numbers.setText("4");
//                tv_numbers.setSelection(tv_numbers.getText().length());
//            } else if (number <= 0) {
//                tv_numbers.setText("1");
//                tv_numbers.setSelection(tv_numbers.getText().length());
//
//            }
            String value = number + "-"+selected_hour_type.toLowerCase(Locale.ROOT);
//            selectedValues.set(position, value);
//                                notification.put(selectedValues);
            StringBuilder sb = new StringBuilder();
            for (String notification : selectedValues) {
                sb.append(notification).append("\n");
            }
            AndroidUtils.showToast(sb.toString(),getContext());
//        }
    }
    private void load_individual_Popup() {
        try {
            for (int i = 0; i < individual_list.size(); i++) {
                for (int j = 0; j < selected_individual_list.size(); j++) {
                    if (individual_list.get(i).getId().matches(selected_individual_list.get(j).getId())) {
                        RelationshipsDO teamModel = individual_list.get(i);
                        teamModel.setChecked(true);
//                        selected_groups_list.set(j,documentsModel);
                    }
                }
            }
            selected_individual_list.clear();
//            selected_tm_list.clear();
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
            LayoutInflater inflater = getActivity().getLayoutInflater();
            View view = inflater.inflate(R.layout.groups_list_adapter, null);
            RecyclerView rv_groups = view.findViewById(R.id.rv_relationship_documents);
            ImageView iv_cancel = view.findViewById(R.id.close_groups);
            AppCompatButton btn_groups_cancel = view.findViewById(R.id.btn_groups_cancel);
            AppCompatButton btn_save_group = view.findViewById(R.id.btn_save_group);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            rv_groups.setLayoutManager(layoutManager);
            rv_groups.setHasFixedSize(true);
            ADAPTER_TAG = "INDIVIDUAL";
            CommonRelationshipsAdapter documentsAdapter = new CommonRelationshipsAdapter(teamList, ADAPTER_TAG, individual_list, entity_client_list);
            rv_groups.setAdapter(documentsAdapter);
            AlertDialog dialog = dialogBuilder.create();
            iv_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
            btn_groups_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
            btn_save_group.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for (int i = 0; i < documentsAdapter.getIndividual_List().size(); i++) {
                        RelationshipsDO teamModel = documentsAdapter.getIndividual_List().get(i);
                        if (teamModel.isChecked()) {
                            selected_individual_list.add(teamModel);
//                        AndroidUtils.showAlert(selected_individual_list.toString(),getContext());
//                        new_selected_individual_list.add(teamModel);
                            //                           jsonArray.put(selected_documents_list.get(i).getGroup_name());
                        }
                    }


                    loadSelectedIndividual();
                    dialog.dismiss();
                }
            });
            dialog.setCancelable(false);
            dialog.setView(view);
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
            AndroidUtils.showAlert(e.getMessage(), getContext());
        }

    }

    private void loadSelectedIndividual() {

        String[] value = new String[selected_individual_list.size()];
        for (int i = 0; i < selected_individual_list.size(); i++) {
//                                value += "," + family_members.get(i);
//                               value.add(family_members.get(i));
            value[i] = selected_individual_list.get(i).getName();

        }

        String str = String.join(",", value);
        at_individual.setText(str);
        if (selected_individual_list.size() == 0) {
            selected_individual.setVisibility(View.GONE);
        } else {
            selected_individual.setVisibility(View.VISIBLE);
        }

        ll_individual_list.removeAllViews();
        for (int i = 0; i < selected_individual_list.size(); i++) {
            View view_opponents = LayoutInflater.from(getContext()).inflate(R.layout.edit_opponent_advocate, null);
            TextView tv_opponent_name = view_opponents.findViewById(R.id.tv_opponent_name);
            tv_opponent_name.setText(selected_individual_list.get(i).getName());
            ImageView iv_edit_opponent = view_opponents.findViewById(R.id.iv_edit_opponent);
            ImageView iv_remove_opponent = view_opponents.findViewById(R.id.iv_remove_opponent);
            iv_remove_opponent.setTag(i);
            iv_remove_opponent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        int position = 0;
                        if (v.getTag() instanceof Integer) {
                            position = (Integer) v.getTag();
                            v = ll_individual_list.getChildAt(position);
                            ll_individual_list.removeView(v);
//                            ll_selected_groups.addView(view_opponents,position);
                            RelationshipsDO teamModel = selected_individual_list.get(position);
                            teamModel.setChecked(false);
                            selected_individual_list.remove(position);
                            if (selected_individual_list.size() == 0) {
                                selected_individual.setVisibility(View.GONE);
                            }
//                            selected_groups_list.set(position, groupsModel);
                            String[] value = new String[selected_individual_list.size()];
                            for (int i = 0; i < selected_individual_list.size(); i++) {
                                value[i] = selected_individual_list.get(i).getName();
                            }

                            String str = String.join(",", value);
                            at_individual.setText(str);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        AndroidUtils.showAlert(e.getMessage(), getContext());
                    }
                }
            });
            iv_edit_opponent.setVisibility(View.GONE);
            ll_individual_list.addView(view_opponents);
        }
    }


    private void callClientsWebservice() {
        try {
            progressDialog = AndroidUtils.get_progress(getActivity());
            JSONObject postdata = new JSONObject();
            WebServiceHelper.callHttpWebService(this, getContext(), WebServiceHelper.RestMethodType.GET, "v2/relationship/client/list", "Client List", postdata.toString());
        } catch (Exception e) {
            AndroidUtils.showToast(e.getMessage(), getContext());
            if (progressDialog != null && progressDialog.isShowing()) {
                AndroidUtils.dismiss_dialog(progressDialog);
            }
            e.printStackTrace();
        }
    }

    private void TeamMembersPopup() {
        try {


//            teamList.clear();

            for (int i = 0; i < teamList.size(); i++) {
                for (int j = 0; j < selected_tm_list.size(); j++) {
                    if (teamList.get(i).getId().matches(selected_tm_list.get(j).getId())) {
                        TeamDo teamModel = teamList.get(i);
                        teamModel.setChecked(true);
//                        selected_groups_list.set(j,documentsModel);

                    }
                }
            }
            selected_tm_list.clear();
//            selected_tm_list.clear();
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
            LayoutInflater inflater = getActivity().getLayoutInflater();
            View view = inflater.inflate(R.layout.groups_list_adapter, null);
            RecyclerView rv_groups = view.findViewById(R.id.rv_relationship_documents);
            ImageView iv_cancel = view.findViewById(R.id.close_groups);
            AppCompatButton btn_groups_cancel = view.findViewById(R.id.btn_groups_cancel);
            AppCompatButton btn_save_group = view.findViewById(R.id.btn_save_group);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            rv_groups.setLayoutManager(layoutManager);
            rv_groups.setHasFixedSize(true);
            ADAPTER_TAG = "TM";
            CommonRelationshipsAdapter documentsAdapter = new CommonRelationshipsAdapter(teamList, ADAPTER_TAG, individual_list, entity_client_list);
            rv_groups.setAdapter(documentsAdapter);
            AlertDialog dialog = dialogBuilder.create();
            iv_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
            btn_groups_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
            btn_save_group.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for (int i = 0; i < documentsAdapter.getTmList().size(); i++) {
                        TeamDo teamModel = documentsAdapter.getTmList().get(i);
                        if (teamModel.isChecked()) {
                            selected_tm_list.add(teamModel);
                            //                           jsonArray.put(selected_documents_list.get(i).getGroup_name());
                        }
                    }


                    loadSelectedTM();
                    dialog.dismiss();
                }
            });
            dialog.setCancelable(false);
            dialog.setView(view);
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
            AndroidUtils.showAlert(e.getMessage(), getContext());
        }
    }

    private void loadSelectedClients() {
        String[] value = new String[selected_entity_client_list.size()];
        for (int i = 0; i < selected_entity_client_list.size(); i++) {
//                                value += "," + family_members.get(i);
//                               value.add(family_members.get(i));
            value[i] = selected_entity_client_list.get(i).getName();
        }
        String str = String.join(",", value);
        at_assigned_team_members.setText(str);
        selected_tm.setVisibility(View.VISIBLE);
        ll_client_team_members.removeAllViews();
        for (int i = 0; i < selected_entity_client_list.size(); i++) {
            View view_opponents = LayoutInflater.from(getContext()).inflate(R.layout.edit_opponent_advocate, null);
            TextView tv_opponent_name = view_opponents.findViewById(R.id.tv_opponent_name);
            tv_opponent_name.setText(selected_entity_client_list.get(i).getName());
            ImageView iv_edit_opponent = view_opponents.findViewById(R.id.iv_edit_opponent);
            ImageView iv_remove_opponent = view_opponents.findViewById(R.id.iv_remove_opponent);
            iv_remove_opponent.setTag(i);
            iv_remove_opponent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        int position = 0;
                        if (v.getTag() instanceof Integer) {
                            position = (Integer) v.getTag();
                            v = ll_client_team_members.getChildAt(position);
                            ll_client_team_members.removeView(v);
//                            ll_selected_groups.addView(view_opponents,position);
                            RelationshipsDO teamModel = selected_entity_client_list.get(position);
                            teamModel.setChecked(false);
                            selected_entity_client_list.remove(position);
//                            selected_groups_list.set(position, groupsModel);
                            String[] value = new String[selected_entity_client_list.size()];
                            for (int i = 0; i < selected_entity_client_list.size(); i++) {
                                value[i] = selected_entity_client_list.get(i).getName();
                            }

                            String str = String.join(",", value);
                            at_assigned_team_members.setText(str);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        AndroidUtils.showAlert(e.getMessage(), getContext());
                    }
                }
            });
            iv_edit_opponent.setVisibility(View.GONE);
            ll_client_team_members.addView(view_opponents);
        }
    }

    private void loadSelectedTM() {
        String[] value = new String[selected_tm_list.size()];
        for (int i = 0; i < selected_tm_list.size(); i++) {
//                                value += "," + family_members.get(i);
//                               value.add(family_members.get(i));
            value[i] = selected_tm_list.get(i).getName();

        }

        String str = String.join(",", value);
        at_add_groups.setText(str);
        selected_groups.setVisibility(View.VISIBLE);
        ll_selected_team_members.removeAllViews();
        for (int i = 0; i < selected_tm_list.size(); i++) {
            View view_opponents = LayoutInflater.from(getContext()).inflate(R.layout.edit_opponent_advocate, null);
            TextView tv_opponent_name = view_opponents.findViewById(R.id.tv_opponent_name);
            tv_opponent_name.setText(selected_tm_list.get(i).getName());
            ImageView iv_edit_opponent = view_opponents.findViewById(R.id.iv_edit_opponent);
            ImageView iv_remove_opponent = view_opponents.findViewById(R.id.iv_remove_opponent);
            iv_remove_opponent.setTag(i);
            iv_remove_opponent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        int position = 0;
                        if (v.getTag() instanceof Integer) {
                            position = (Integer) v.getTag();
                            v = ll_selected_team_members.getChildAt(position);
                            ll_selected_team_members.removeView(v);
//                            ll_selected_groups.addView(view_opponents,position);
                            TeamDo teamModel = selected_tm_list.get(position);
                            teamModel.setChecked(false);
                            selected_tm_list.remove(position);
//                            selected_groups_list.set(position, groupsModel);
                            String[] value = new String[selected_tm_list.size()];
                            for (int i = 0; i < selected_tm_list.size(); i++) {
                                value[i] = selected_tm_list.get(i).getName();
                            }

                            String str = String.join(",", value);
                            at_add_groups.setText(str);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        AndroidUtils.showAlert(e.getMessage(), getContext());
                    }
                }
            });
            iv_edit_opponent.setVisibility(View.GONE);
            ll_selected_team_members.addView(view_opponents);
        }
    }

    private void loadProjectData(String selected_project) {
        switch (selected_project) {

            case "Legal Matter":
                legalTaksList.clear();
                unHideMatterDetails();
                loadRepetetions();
                String matter_legal = "legal";
                callProjectWebservice(matter_legal);
//                callClientsWebservice();
//                TaskDo caseFilling = new TaskDo("Case Filling");
//                TaskDo consultation = new TaskDo("Consultation");
//                TaskDo clb = new TaskDo("Creating Legal Breifs");
//                TaskDo mwc = new TaskDo("Meeting with client");
//                TaskDo hearing = new TaskDo("Hearing");
                legalTaksList.add(new TaskDo("Case Filling"));
                legalTaksList.add(new TaskDo("Consultation"));
                legalTaksList.add(new TaskDo("Creating Legal Breifs"));
                legalTaksList.add(new TaskDo("Meeting with client"));
                legalTaksList.add(new TaskDo("Hearing"));
                loadTaskList(legalTaksList);
                break;
            case "General Matter":
                legalTaksList.clear();
                unHideMatterDetails();
                loadRepetetions();
                String matter_general = "general";
                callProjectWebservice(matter_general);
//                TaskDo consultation_general = new TaskDo("Consultation");
//                TaskDo draft_agreements = new TaskDo("Draft agreements");
//                TaskDo fwa = new TaskDo("Filling with authorities");
//                TaskDo mwc_general = new TaskDo("Meeting with client");
//                TaskDo paf = new TaskDo("Prepare annual fillings");
                legalTaksList.add(new TaskDo("Consultation"));
                legalTaksList.add(new TaskDo("Draft agreements"));
                legalTaksList.add(new TaskDo("Filling with authorities"));
                legalTaksList.add(new TaskDo("Meeting with client"));
                legalTaksList.add(new TaskDo("Prepare annual fillings"));
                loadTaskList(legalTaksList);
                break;
            case "Overhead":
                legalTaksList.clear();
                hideMatterDetails();
                loadRepetetions();
//                TaskDo conference = new TaskDo("Conference");
//                TaskDo holidays = new TaskDo("Holidays");
//                TaskDo research = new TaskDo("Research");
//                TaskDo training = new TaskDo("Training");
//                TaskDo vacation = new TaskDo("Vacation");
                legalTaksList.add(new TaskDo("Conference"));
                legalTaksList.add(new TaskDo("Holidays"));
                legalTaksList.add(new TaskDo("Research"));
                legalTaksList.add(new TaskDo("Training"));
                legalTaksList.add(new TaskDo("Vacation"));
                loadTaskList(legalTaksList);
                break;
            case "Others":
                legalTaksList.clear();
                hideMatterDetails();
                loadRepetetions();
                TaskDo business_development = new TaskDo("Business Development");
                TaskDo personal = new TaskDo("Personal");
                TaskDo doctor_appointment = new TaskDo("Doctor Appointment");
                TaskDo lunch_dinner = new TaskDo("Lunch/Dinner");
                TaskDo misc = new TaskDo("Misc");
                legalTaksList.add(new TaskDo("Business Development"));
                legalTaksList.add(new TaskDo("Personal"));
                legalTaksList.add(new TaskDo("Doctor Appointment"));
                legalTaksList.add(new TaskDo("Lunch/Dinner"));
                legalTaksList.add(new TaskDo("Misc"));
                loadTaskList(legalTaksList);
                break;
            case "Reminders":
                legalTaksList.clear();
                hideAlldetails();
                loadRepetetions();

                break;
        }
    }

    private void loadRepetetions() {
        Repetetions.clear();
        Repetetions.add("None");
        Repetetions.add("Weekly");
        Repetetions.add("Bi- Weekly");
        Repetetions.add("Monthly");
        Repetetions.add("Yearly");
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, Repetetions);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_repetetion.setAdapter(adapter1);
        sp_repetetion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                repeat_interval = Repetetions.get(sp_repetetion.getSelectedItemPosition());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
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

    private void callTeamMemberWebservice() {
        try {
            progressDialog = AndroidUtils.get_progress(getActivity());
            JSONObject postdata = new JSONObject();
            WebServiceHelper.callHttpWebService(this, getContext(), WebServiceHelper.RestMethodType.GET, "v3/event/tms", "Team List", postdata.toString());
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
                        matterTaskCompleted = true;
                        loadMattersList(matters);

                    } catch (Exception e) {
                        AndroidUtils.showAlert(e.getMessage(), getContext());
                        e.printStackTrace();
                    }
                } else if (httpResult.getRequestType().equals("TIMEZONES")) {
                    if (!result.getBoolean("error")) {

                        JSONArray jsonArray = result.getJSONArray("timezones");
                        load_timezones(jsonArray);
//                        Thre
                        timeZonesTaskCompleted = true;
                    } else {
                        AndroidUtils.showAlert("Something went wrong", getContext());
                    }
                } else if (httpResult.getRequestType().equals("Team List")) {
                    if (!matterTaskCompleted) {

                        return;
                    }
                    if (!result.getBoolean("error")) {
                        JSONArray jsonArray = result.getJSONArray("users");
                        loadTeamList(jsonArray);
                        tmTaskCompleted = true;
//                        Thre

                    } else {
                        AndroidUtils.showAlert("Something went wrong", getContext());
                    }
                } else if (httpResult.getRequestType().equals("Client List")) {
                    if (Constants.ROLE.equals("AAM")) {
                        if (!timeZonesTaskCompleted) {

                            return;
                        }
                        if (!result.getBoolean("error")) {

                            JSONObject jsonObject = result.getJSONObject("data");
                            JSONArray jsonArray = jsonObject.getJSONArray("relationships");
                            loadRelationshipsList(jsonArray);
                        } else {
                            AndroidUtils.showAlert("Something went wrong", getContext());
                        }
                    } else {
                        if (!result.getBoolean("error")) {

                            JSONObject jsonObject = result.getJSONObject("data");
                            JSONArray jsonArray = jsonObject.getJSONArray("relationships");
                            loadRelationshipsList(jsonArray);
                        } else {
                            AndroidUtils.showAlert("Something went wrong", getContext());
                        }
                    }
//                    if (!matterTaskCompleted) {
////                        AndroidUtils.showToast("Please add team members",getContext());
//                        return;
//                    }

                } else if (httpResult.getRequestType().equals("Entity Client List")) {
                    if (!result.getBoolean("error")) {
                        JSONArray jsonArray = result.getJSONArray("users");
                        loadEntity_Clients(jsonArray);
                    } else {
                        AndroidUtils.showAlert("Something went wrong", getContext());
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            AndroidUtils.showAlert("Something went wrong", getContext());
        }
    }

    private void loadEntity_Clients(JSONArray jsonArray) throws JSONException {
        entity_client_list.clear();
        for (int i = 0; i < jsonArray.length(); i++) {
            RelationshipsDO relationshipsDO = new RelationshipsDO();
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            relationshipsDO.setId(jsonObject.getString("id"));
            relationshipsDO.setName(jsonObject.getString("name"));
            entity_client_list.add(relationshipsDO);
        }

//        loadSelectedClients();
    }

    private void loadEntityClientPopup() {
        try {
            for (int i = 0; i < entity_client_list.size(); i++) {
                for (int j = 0; j < selected_entity_client_list.size(); j++) {
                    if (entity_client_list.get(i).getId().matches(selected_entity_client_list.get(j).getId())) {
                        RelationshipsDO teamModel = entity_client_list.get(i);
                        teamModel.setChecked(true);
//                        selected_groups_list.set(j,documentsModel);
                    }
                }
            }
            selected_entity_client_list.clear();
//            selected_tm_list.clear();
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
            LayoutInflater inflater = getActivity().getLayoutInflater();
            View view = inflater.inflate(R.layout.groups_list_adapter, null);
            RecyclerView rv_groups = view.findViewById(R.id.rv_relationship_documents);
            ImageView iv_cancel = view.findViewById(R.id.close_groups);
            AppCompatButton btn_groups_cancel = view.findViewById(R.id.btn_groups_cancel);
            AppCompatButton btn_save_group = view.findViewById(R.id.btn_save_group);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            rv_groups.setLayoutManager(layoutManager);
            rv_groups.setHasFixedSize(true);
            ADAPTER_TAG = "ENTITY";
            CommonRelationshipsAdapter documentsAdapter = new CommonRelationshipsAdapter(teamList, ADAPTER_TAG, individual_list, entity_client_list);
            rv_groups.setAdapter(documentsAdapter);
            AlertDialog dialog = dialogBuilder.create();
            iv_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
            btn_groups_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
            btn_save_group.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for (int i = 0; i < documentsAdapter.getEntity_client_list().size(); i++) {
                        RelationshipsDO teamModel = documentsAdapter.getEntity_client_list().get(i);
                        if (teamModel.isChecked()) {
                            selected_entity_client_list.add(teamModel);
//                        AndroidUtils.showAlert(selected_individual_list.toString(),getContext());
//                        new_selected_client_list.add(teamModel);
                            //                           jsonArray.put(selected_documents_list.get(i).getGroup_name());
                        }
                    }
                    loadSelectedClients();
                    dialog.dismiss();
                }
            });
            dialog.setCancelable(false);
            dialog.setView(view);
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
            AndroidUtils.showAlert(e.getMessage(), getContext());
        }

    }

    private void loadRelationshipsList(JSONArray jsonArray) throws JSONException {
        individual_list.clear();
        entities_list.clear();
        for (int i = 0; i < jsonArray.length(); i++) {
            RelationshipsDO relationshipsDO = new RelationshipsDO();
            JSONObject jsonObject = jsonArray.getJSONObject(i);
//                String type =   ;

            if (jsonObject.getString("type").equals("consumer")) {
                relationshipsDO.setId(jsonObject.getString("id"));
                relationshipsDO.setName(jsonObject.getString("name"));
                relationshipsDO.setType(jsonObject.getString("type"));
                individual_list.add(relationshipsDO);
            } else {
                relationshipsDO.setId(jsonObject.getString("id"));
                relationshipsDO.setName(jsonObject.getString("name"));
                relationshipsDO.setType(jsonObject.getString("type"));
                entities_list.add(relationshipsDO);
            }
        }
        loadEntitiesSpinnerData();
    }

    private void loadEntitiesSpinnerData() {
        final CommonSpinnerAdapter adapter = new CommonSpinnerAdapter(getActivity(), entities_list);
        sp_entities.setAdapter(adapter);
//        callTimeZoneWebservice();
        sp_entities.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                String id = entities_list.get(sp_entities.getSelectedItemPosition()).getId();
                callEntityClientWebservice(id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void callEntityClientWebservice(String id) {
        try {
            progressDialog = AndroidUtils.get_progress(getActivity());
            JSONObject postdata = new JSONObject();
            WebServiceHelper.callHttpWebService(this, getContext(), WebServiceHelper.RestMethodType.GET, "related/entities/tms/" + id, "Entity Client List", postdata.toString());
        } catch (Exception e) {
            AndroidUtils.showToast(e.getMessage(), getContext());
            if (progressDialog != null && progressDialog.isShowing()) {
                AndroidUtils.dismiss_dialog(progressDialog);
            }
            e.printStackTrace();
        }
    }

    private void loadTeamList(JSONArray jsonArray) throws JSONException {
        teamList.clear();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            TeamDo teamDo = new TeamDo();
            teamDo.setId(jsonObject.getString("id"));
            teamDo.setName(jsonObject.getString("name"));
            teamList.add(teamDo);
        }
        TeamMembersPopup();
    }

    private void loadTeamSpinner() {
//        final CommonSpinnerAdapter adapter = new CommonSpinnerAdapter(getActivity(), teamList);
//        sp_add_team_member.setAdapter(adapter);
////        callTimeZoneWebservice();
//        sp_add_team_member.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                team_member_id = teamList.get(sp_add_team_member.getSelectedItemPosition()).getId();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });
        final AutocompleteAdapter matterAutocompleteAdapter = new AutocompleteAdapter(getContext(), 1, teamList);
//        callLegalwebservice();
        at_family_members.setInputType(InputType.TYPE_NULL);
        at_family_members.setThreshold(0);
        at_family_members.setAdapter(matterAutocompleteAdapter);
        at_family_members.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
        at_family_members.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TeamDo selectedItem = matterAutocompleteAdapter.getItem(position);
                matterAutocompleteAdapter.remove(selectedItem);
                matterAutocompleteAdapter.notifyDataSetChanged();
            }
        });

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
        if (Constants.ROLE.equals("AAM")) {
            callClientsWebservice();
        }
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
        callClientsWebservice();
//            callTeamMemberWebservice();
    }


}
