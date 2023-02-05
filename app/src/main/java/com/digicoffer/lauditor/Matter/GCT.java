package com.digicoffer.lauditor.Matter;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.digicoffer.lauditor.Documents.DocumentsListAdpater.GroupsListAdapter;
import com.digicoffer.lauditor.Documents.models.DocumentsModel;
import com.digicoffer.lauditor.Matter.Adapters.GroupsAdapter;
import com.digicoffer.lauditor.Matter.Models.GroupsModel;
import com.digicoffer.lauditor.R;
import com.digicoffer.lauditor.Webservice.AsyncTaskCompleteListener;
import com.digicoffer.lauditor.Webservice.HttpResultDo;
import com.digicoffer.lauditor.Webservice.WebServiceHelper;
import com.digicoffer.lauditor.common.AndroidUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.minidns.record.A;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class GCT extends Fragment implements View.OnClickListener, AsyncTaskCompleteListener {

    TextView matter_date,at_add_groups, at_add_clients, at_assigned_team_members;
    boolean[] selectedLanguage;

    Button btn_add_groups, btn_add_clients, btn_assigned_team_members;
    LinearLayout ll_selected_groups, ll_selected_clients, ll_assigned_team_members;
    AlertDialog progress_dialog;
    ArrayList<GroupsModel> selected_groups_list = new ArrayList<>();
    ArrayList<GroupsModel> groupsList = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.gct_layout, container, false);
        matter_date = view.findViewById(R.id.matter_date);
        at_add_groups = view.findViewById(R.id.at_add_groups);
        at_add_clients = view.findViewById(R.id.at_add_clients);
        at_assigned_team_members = view.findViewById(R.id.at_assigned_team_members);
        btn_add_groups = view.findViewById(R.id.btn_add_groups);
        btn_add_groups.setOnClickListener(this);
        btn_add_clients = view.findViewById(R.id.btn_add_clients);
        btn_add_clients.setOnClickListener(this);
        btn_assigned_team_members = view.findViewById(R.id.btn_add_clients);
        btn_assigned_team_members.setOnClickListener(this);
        ll_selected_groups = view.findViewById(R.id.ll_selected_groups);
        ll_assigned_team_members = view.findViewById(R.id.ll_assigned_team_members);
        ll_selected_clients = view.findViewById(R.id.ll_selected_clients);
        Calendar myCalendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

            private void updateLabel() {
                String myFormat = "dd-MM-yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                matter_date.setText(sdf.format(myCalendar.getTime()));
            }
        };
        matter_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
            }
        });
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String formattedDate = df.format(c);
        matter_date.setText(formattedDate);
//        callGroupsWebservice();
        return view;
    }

    private void callGroupsWebservice() {
        progress_dialog = AndroidUtils.get_progress(getActivity());
        JSONObject postdata = new JSONObject();
        WebServiceHelper.callHttpWebService(this, getContext(), WebServiceHelper.RestMethodType.GET, "v3/groups", "Groups", postdata.toString());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add_groups:
                if (groupsList.size()==0) {
                    callGroupsWebservice();
                }else{
                    GroupsPopup();
                }
                break;
            case R.id.btn_add_clients:
                break;
            case R.id.btn_assigned_team_members:
                break;
        }
    }

    @Override
    public void onAsyncTaskComplete(HttpResultDo httpResult) {
        if (progress_dialog != null && progress_dialog.isShowing())
            AndroidUtils.dismiss_dialog(progress_dialog);
        if (httpResult.getResult() == WebServiceHelper.ServiceCallStatus.Success) {
            try {
                JSONObject result = new JSONObject(httpResult.getResponseContent());
                if (httpResult.getRequestType().equals("Groups")) {
                    JSONArray data = result.getJSONArray("data");
                    loadGroupsData(data);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    private void loadGroupsData(JSONArray data) {
        try {
            for (int i = 0; i < data.length(); i++) {
                JSONObject jsonObject = data.getJSONObject(i);
                GroupsModel groupsModel = new GroupsModel();
                groupsModel.setGroup_id(jsonObject.getString("id"));
                groupsModel.setGroup_name(jsonObject.getString("name"));
                groupsList.add(groupsModel);
            }
            selectedLanguage = new boolean[groupsList.size()];
//            GroupsAlert();
            GroupsPopup();
        } catch (JSONException e) {
            e.printStackTrace();
            AndroidUtils.showAlert(e.getMessage(), getContext());
        }
    }
    private void GroupsPopup() {
        try {

            for (int i = 0; i < groupsList.size(); i++) {
                for (int j = 0; j < selected_groups_list.size(); j++) {
                    if (groupsList.get(i).getGroup_id().matches(selected_groups_list.get(j).getGroup_id())) {
                        GroupsModel groupsModel = groupsList.get(i);
                        groupsModel.setChecked(true);
//                        selected_groups_list.set(j,documentsModel);

                    }
                }
            }
            selected_groups_list.clear();
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
            GroupsAdapter documentsAdapter = new GroupsAdapter(groupsList);
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
//                    ArrayList<String>
                    for (int i = 0; i < documentsAdapter.getList_item().size(); i++) {
                        GroupsModel groupsModel = documentsAdapter.getList_item().get(i);
                        if (groupsModel.isChecked()) {
                            selected_groups_list.add(groupsModel);
                            //                           jsonArray.put(selected_documents_list.get(i).getGroup_name());
                        }
                    }

                    String[] value = new String[selected_groups_list.size()];
                    for (int i = 0; i < selected_groups_list.size(); i++) {
//                                value += "," + family_members.get(i);
//                               value.add(family_members.get(i));
                        value[i] = selected_groups_list.get(i).getGroup_name();

                    }

                    String str = String.join(",", value);
                    at_add_groups.setText(str);
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

}
