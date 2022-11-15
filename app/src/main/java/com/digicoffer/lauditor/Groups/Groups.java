package com.digicoffer.lauditor.Groups;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.digicoffer.lauditor.Groups.GroupModels.GroupModel;
import com.digicoffer.lauditor.Groups.GroupModels.ViewGroupModel;
import com.digicoffer.lauditor.R;
import com.digicoffer.lauditor.Webservice.AsyncTaskCompleteListener;
import com.digicoffer.lauditor.Webservice.HttpResultDo;
import com.digicoffer.lauditor.Webservice.WebServiceHelper;
import com.digicoffer.lauditor.common.AndroidUtils;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.minidns.record.A;

import java.util.ArrayList;
import java.util.Date;

public class Groups extends Fragment implements AsyncTaskCompleteListener {
    RecyclerView rv_select_team_members, rv_view_groups;
    TextInputEditText et_search;
    TextView tv_create_group, tv_view_group, tv_add_tm, tv_practice_head;
    ArrayList<GroupModel> selectedTMArrayList = new ArrayList<GroupModel>();
    ArrayList<ViewGroupModel> viewGroupModelArrayList = new ArrayList<>();
    private CheckBox chk_select_all;
    CardView cv_groups, cv_details;
    AlertDialog progress_dialog;
    LinearLayoutCompat ll_tm, ll_select_all, ll_buttons;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.groups, container, false);
        rv_select_team_members = v.findViewById(R.id.rv_selected_tm);
        rv_view_groups = v.findViewById(R.id.rv_view_group);
        et_search = v.findViewById(R.id.et_search);
        cv_groups = v.findViewById(R.id.cv_details);
        cv_details = v.findViewById(R.id.cv_details_2);
        ll_tm = v.findViewById(R.id.linearLayoutCompat1);
        ll_select_all = v.findViewById(R.id.ll_select_all);
        ll_buttons = v.findViewById(R.id.ll_buttons);
        tv_create_group = v.findViewById(R.id.tv_create_group);
        tv_view_group = v.findViewById(R.id.tv_view_group);
        tv_add_tm = v.findViewById(R.id.add_tm);
        tv_practice_head = v.findViewById(R.id.add_phead);
        tv_create_group.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.button_left_green_background));
        tv_add_tm.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.button_left_green_background));
        tv_view_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_view_group.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.button_right_green_count));
                tv_create_group.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.button_left_background));
                cv_groups.setVisibility(View.GONE);
                ll_buttons.setVisibility(View.GONE);
                ll_tm.setVisibility(View.GONE);
                ll_select_all.setVisibility(View.GONE);
                selectedTMArrayList.removeAll(selectedTMArrayList);
                rv_select_team_members.removeAllViews();
                cv_details.setVisibility(View.GONE);
                rv_view_groups.setVisibility(View.VISIBLE);
                et_search.setVisibility(View.VISIBLE);
                callViewGroupsWebservice();

            }
        });
        tv_practice_head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_add_tm.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.button_left_background));
                tv_practice_head.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.button_right_green_count));
            }
        });
        tv_create_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_view_group.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.button_right_background));
                tv_create_group.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.button_left_green_background));
                cv_groups.setVisibility(View.VISIBLE);
                cv_details.setVisibility(View.VISIBLE);
                ll_buttons.setVisibility(View.VISIBLE);
                ll_tm.setVisibility(View.VISIBLE);
                ll_select_all.setVisibility(View.VISIBLE);
                rv_view_groups.setVisibility(View.GONE);
                et_search.setVisibility(View.GONE);
                viewGroupModelArrayList.removeAll(viewGroupModelArrayList);
                rv_view_groups.removeAllViews();
              callMembersWebservice();

            }
        });
        tv_add_tm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                tv_practice_head.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.button_right_background));
                tv_add_tm.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.button_left_green_background));
            }
        });
        chk_select_all = v.findViewById(R.id.chk_select_all);
        try {
            callMembersWebservice();
//            loadRecylcerview();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return v;
    }

    private void callMembersWebservice() {
        try {
            JSONObject postdata = new JSONObject();
            progress_dialog = AndroidUtils.get_progress(getActivity());
            WebServiceHelper.callHttpWebService(this, getContext(), WebServiceHelper.RestMethodType.GET, "v3/member/groups", "Get Members", postdata.toString());
        } catch (Exception e) {
            if (progress_dialog != null && progress_dialog.isShowing())
                AndroidUtils.dismiss_dialog(progress_dialog);
        }
    }

    private void callViewGroupsWebservice() {
        try {
            JSONObject postdata = new JSONObject();
            progress_dialog = AndroidUtils.get_progress(getActivity());
            WebServiceHelper.callHttpWebService(this, getContext(), WebServiceHelper.RestMethodType.GET, "v3/groups", "Get Groups", postdata.toString());
        } catch (Exception e) {
            if (progress_dialog != null && progress_dialog.isShowing())
                AndroidUtils.dismiss_dialog(progress_dialog);
        }
    }

    private void loadViewGroupsRecylerview() {
//        viewGroupModelArrayList.add(new ViewGroupModel("Admin & Account Management"
//                ,"joe carpenter","jan 02 2022"));
//        viewGroupModelArrayList.add(new ViewGroupModel("Super User"
//                ,"joe carpenter","jan 02 2022"));
//        viewGroupModelArrayList.add(new ViewGroupModel("Litigation"
//                ,"joe carpenter","jan 02 2022"));
//        viewGroupModelArrayList.add(new ViewGroupModel("Family Law"
//                ,"joe carpenter","jan 02 2022"));
        rv_view_groups.setLayoutManager(new GridLayoutManager(getContext(), 1));
        final ViewGroupsAdpater adapter = new ViewGroupsAdpater(viewGroupModelArrayList);
        rv_view_groups.setAdapter(adapter);
        rv_view_groups.setHasFixedSize(true);
    }

    private void loadRecylcerview() {
//        selectedTMArrayList.add(new GroupModel("Akash Kumar"));
//        selectedTMArrayList.add(new GroupModel("Priyanka Chopra"));
//        selectedTMArrayList.add(new GroupModel("Akhileswar"));
//        selectedTMArrayList.add(new GroupModel("Dilshan"));
//        selectedTMArrayList.add(new GroupModel("Sachin Tendulkar"));
//        selectedTMArrayList.add(new GroupModel("Akash Kumar"));
//        selectedTMArrayList.add(new GroupModel("Priyanka Chopra"));
//        selectedTMArrayList.add(new GroupModel("Akhileswar"));
//        selectedTMArrayList.add(new GroupModel("Dilshan"));
//        selectedTMArrayList.add(new GroupModel("Sachin Tendulkar"));
//        selectedTMArrayList.add(new GroupModel("Akash Kumar"));
//        selectedTMArrayList.add(new GroupModel("Priyanka Chopra"));
//        selectedTMArrayList.add(new GroupModel("Akhileswar"));
//        selectedTMArrayList.add(new GroupModel("Dilshan"));
//        selectedTMArrayList.add(new GroupModel("Sachin Tendulkar"));
        rv_select_team_members.setLayoutManager(new GridLayoutManager(getContext(), 1));
        final GroupAdapters adapter = new GroupAdapters(selectedTMArrayList);
        rv_select_team_members.setAdapter(adapter);
        rv_select_team_members.setHasFixedSize(true);
//        rv_select_team_members.notify();
        chk_select_all.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

//                for (int i=0;i<adapter.getList_item().size();i++){
//                    if (adapter.getList_item().get(i).isSelected()) {
                        adapter.selectOrDeselectAll(isChecked);
//                    }
//                    else
//                    {
//                        adapter.selectOrDeselectAll(false);
//                    }
//                }
//                JSONArray selected_list = new JSONArray();
//                for (int i=0;i<adapter.getList_item().size();i++){
//                    GroupModel groupModel = adapter.getList_item().get(i);
//
//                    if (groupModel.isChecked()){
//
//                    }else {
//                        groupModel.setSelected(true);
//                    }
//                }
            }
        });
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onAsyncTaskComplete(HttpResultDo httpResult) {
        if (progress_dialog != null && progress_dialog.isShowing())
            AndroidUtils.dismiss_dialog(progress_dialog);
        if (httpResult.getResult() == WebServiceHelper.ServiceCallStatus.Success) {
            try {
                JSONObject result = new JSONObject(httpResult.getResponseContent());
                if (httpResult.getRequestType().equals("Get Groups")) {
                    JSONArray data = result.getJSONArray("data");
                    loadViewGroups(data);
                }else if(httpResult.getRequestType().equals("Get Members")){
                    JSONObject data = result.getJSONObject("data");
                    JSONArray users = data.getJSONArray("users");
                    loadMembers(users);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void loadMembers(JSONArray users) throws JSONException {
        GroupModel groupModel;
        selectedTMArrayList.clear();
        for (int i=0;i<users.length();i++){
            JSONObject jsonObject = users.getJSONObject(i);
            groupModel = new GroupModel();
            groupModel.setId(jsonObject.getString("id"));
            groupModel.setName(jsonObject.getString("name"));
            selectedTMArrayList.add(groupModel);
        }
        loadRecylcerview();

    }

    private void loadViewGroups(JSONArray data) throws JSONException {
        ViewGroupModel viewGroupModel;
        viewGroupModelArrayList.clear();
        for (int i = 0; i < data.length(); i++) {
            JSONObject jsonObject = data.getJSONObject(i);
            viewGroupModel = new ViewGroupModel();
            viewGroupModel.setId(jsonObject.getString("id"));

            String date = jsonObject.getString("created");

            Date date_new = AndroidUtils.stringToDateTimeDefault(date, "yyyy-MM-dd'T'HH:mm:ss.SSS");
            String created = AndroidUtils.getDateToString(date_new, "MMM dd YYYY");
            viewGroupModel.setCreated(created);

            viewGroupModel.setDescription(jsonObject.getString("description"));
            viewGroupModel.setName(jsonObject.getString("name"));
            JSONObject group_head = jsonObject.getJSONObject("groupHead");
            viewGroupModel.setOwner_name(group_head.getString("name"));
            viewGroupModelArrayList.add(viewGroupModel);
        }
        Log.i("ArrayList", "info" + viewGroupModelArrayList.toString());
        loadViewGroupsRecylerview();
    }
}
