package com.digicoffer.lauditor.Groups;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.digicoffer.lauditor.Groups.Adapters.GroupAdapters;
import com.digicoffer.lauditor.Groups.Adapters.ViewGroupsAdpater;
import com.digicoffer.lauditor.Groups.GroupModels.GroupModel;
import com.digicoffer.lauditor.Groups.GroupModels.ViewGroupModel;
import com.digicoffer.lauditor.R;
import com.digicoffer.lauditor.Webservice.AsyncTaskCompleteListener;
import com.digicoffer.lauditor.Webservice.HttpResultDo;
import com.digicoffer.lauditor.Webservice.ItemClickListener;
import com.digicoffer.lauditor.Webservice.WebServiceHelper;
import com.digicoffer.lauditor.common.AndroidUtils;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

public class Groups extends Fragment implements AsyncTaskCompleteListener ,ViewGroupsAdpater.InterfaceListener {
    RecyclerView rv_select_team_members, rv_view_groups;
    TextInputEditText et_search;
    TextInputLayout tv_selected_members;
    ItemClickListener itemClickListener;
    String group_head = "";
    TextView tv_create_group, tv_view_group, tv_add_tm, tv_practice_head,tv_group_name,tv_group_description;
    ArrayList<GroupModel> selectedTMArrayList = new ArrayList<GroupModel>();
    public static String TM_TYPE = "";
    ArrayList<ViewGroupModel> viewGroupModelArrayList = new ArrayList<>();
    ArrayList<GroupModel> assignGroupsList = new ArrayList<>();
    private CheckBox chk_select_all;
    CardView cv_groups, cv_details;
    GroupAdapters adapter = null;
    AlertDialog progress_dialog;
    AppCompatButton btn_cancel, btn_save,btn_cancel_edit,btn_update;
    TextInputEditText et_Search;
    LinearLayoutCompat ll_tm, ll_select_all, ll_buttons,ll_group_list,ll_select_tm,ll_edit_groups;

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.groups, container, false);
        rv_select_team_members = v.findViewById(R.id.rv_selected_tm);
        rv_view_groups = v.findViewById(R.id.rv_view_group);
        et_search = v.findViewById(R.id.et_search);
        tv_group_name = v.findViewById(R.id.tv_group_name);
        tv_group_description = v.findViewById(R.id.tv_description);
        ll_edit_groups = v.findViewById(R.id.ll_edit_buttons);
        cv_groups = v.findViewById(R.id.cv_details);
        et_Search = v.findViewById(R.id.et_search_tm);
        cv_details = v.findViewById(R.id.cv_details_2);
        tv_selected_members = v.findViewById(R.id.filledTextField3);
        ll_tm = v.findViewById(R.id.linearLayoutCompat1);
        btn_cancel_edit = v.findViewById(R.id.btn_cancel_edit);
        btn_update = v.findViewById(R.id.btn_update);

        ll_select_all = v.findViewById(R.id.ll_select_all);
        ll_group_list = v.findViewById(R.id.linearLayoutCompat);
        ll_select_tm = v.findViewById(R.id.linearLayoutCompat2);
        ll_buttons = v.findViewById(R.id.ll_buttons);
        tv_create_group = v.findViewById(R.id.tv_create_group);
        tv_view_group = v.findViewById(R.id.tv_view_group);
        tv_add_tm = v.findViewById(R.id.add_tm);
        tv_practice_head = v.findViewById(R.id.add_phead);
        btn_cancel = v.findViewById(R.id.btn_cancel);
        btn_save = v.findViewById(R.id.btn_save);
        tv_create_group.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.button_left_green_background));
        tv_add_tm.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.button_left_green_background));
        tv_view_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewGroupsData();
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

    private void ViewGroupsData() {
        tv_view_group.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.button_right_green_count));
        tv_view_group.setTextColor(getContext().getResources().getColor(R.color.white));
        tv_create_group.setTextColor(getContext().getResources().getColor(R.color.black));
        tv_create_group.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.button_left_background));
        tv_group_name.setText("");
        tv_group_description.setText("");
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

        rv_view_groups.setLayoutManager(new GridLayoutManager(getContext(), 1));
        final ViewGroupsAdpater adapter = new ViewGroupsAdpater(viewGroupModelArrayList, getContext(),this);
        rv_view_groups.setAdapter(adapter);
        rv_view_groups.setHasFixedSize(true);
    }

    private void loadRecylcerview(ArrayList<GroupModel> selectedTMArrayList, String tmType) {
        tv_create_group.setTextColor(getContext().getResources().getColor(R.color.white));
        tv_view_group.setTextColor(getContext().getResources().getColor(R.color.black));
        if (tmType == "TM") {
            chk_select_all.setVisibility(View.GONE);
            tv_practice_head.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.button_right_background));
            tv_add_tm.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.button_left_green_background));


        } else {
            chk_select_all.setVisibility(View.GONE);
            tv_add_tm.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.button_left_background));
            tv_practice_head.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.button_right_green_count));
        }


        rv_select_team_members.removeAllViews();
        rv_select_team_members.setLayoutManager(new GridLayoutManager(getContext(), 1));
        adapter = new GroupAdapters(selectedTMArrayList, tmType,itemClickListener);
        rv_select_team_members.setAdapter(adapter);
        rv_select_team_members.setHasFixedSize(true);
//        rv_select_team_members.notify();
        et_Search.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                adapter.getFilter().filter(et_Search.getText().toString());
            }

        });
        itemClickListener = new ItemClickListener() {
            @Override
            public void onClick(String s) {
                rv_select_team_members.post(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                        group_head = s;
//                        AndroidUtils.showToast("Selected:"+s, getContext());
                    }
                });
            }
        };
        chk_select_all.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                adapter.selectOrDeselectAll(isChecked);

            }
        });
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tmType == "TM"){
                    assignGroupHead(adapter.getList_item());
                }else {
                    try {
                            callCreateGroupWebservice(tv_group_name.getText().toString().trim(), tv_group_description.getText().toString().trim(), adapter.getList_item(), group_head);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
//                {
//                    for (int i=1;i<adapter.getList_item().size();i++){
//                        if (adapter.getList_item().size()>1){
//                            i--;
//                        }else{
//
//                        }
//                    }
//                }

            }
        });
//        btn_cancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                selectedTMArrayList.clear();
//
//                callMembersWebservice();
//            }
//        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_practice_head.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.button_right_background));
                tv_add_tm.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.button_left_green_background));
                if (selectedTMArrayList.size() == 0) {
                    AndroidUtils.showToast("No team Members selected", getContext());
                } else {
                    adapter.selectOrDeselectAll(false);
                    adapter.getList_item().clear();
                    selectedTMArrayList.clear();
                    rv_select_team_members.removeAllViews();
                    callMembersWebservice();
                }
//                selectedTMArrayList.clear();

//                adapter.getList_item().clear();
            }
        });
    }

    private void callCreateGroupWebservice(String tv_group_name, String tv_group_description, ArrayList<GroupModel> list_item, String group_head) {
        try {
            JSONObject postData = new JSONObject();
            JSONArray members = new JSONArray();
            for (int i = 0; i < list_item.size(); i++) {
                GroupModel model = list_item.get(i);
                members.put(model.getId());
            }
            postData.put("name",tv_group_name);
            postData.put("description",tv_group_description);
            postData.put("groupHead",group_head);
            postData.put("members",members);
//                AndroidUtils.showToast("Selected:"+members+" "+"GH"+group_head,getContext());
            WebServiceHelper.callHttpWebService(this, getContext(), WebServiceHelper.RestMethodType.POST, "v3/group", "Create Groups", postData.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void assignGroupHead(ArrayList<GroupModel> list_item) {

//        selectedTMArrayList.clear();

        for (int i = 0; i < list_item.size(); i++) {
            GroupModel groupModel = list_item.get(i);
            if (groupModel.isChecked()) {
                assignGroupsList.add(groupModel);
            }
        }
        TM_TYPE = "GH";

        if (assignGroupsList.size() != 0) {
            if(tv_group_name.getText().toString().equals("")){
                tv_group_name.setError("Group name Required");
                AndroidUtils.showToast("Group name Required",getContext());
                assignGroupsList.clear();
            }else if(tv_group_description.getText().toString().equals("")){
                tv_group_description.setError("Description Required");
                AndroidUtils.showToast("Description Required",getContext());
                assignGroupsList.clear();
            }else{
                loadRecylcerview(assignGroupsList, TM_TYPE);
            }
        } else {
            AndroidUtils.showToast("Please select atleast one team member", getContext());
        }


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
                } else if (httpResult.getRequestType().equals("Get Members")) {
                    JSONObject data = result.getJSONObject("data");
                    JSONArray users = data.getJSONArray("users");
                    loadMembers(users);
                }else if(httpResult.getRequestType().equals("Create Groups")){
//                    JSONObject jsonObject = result.getJSONObject("msg");
                    ViewGroupsData();
                    AndroidUtils.showToast(result.getString("msg"),getContext());
                }else if(httpResult.getRequestType().equals("Update Groups")){
                    ViewGroupsData();
                    AndroidUtils.showToast(result.getString("msg"),getContext());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void loadMembers(JSONArray users) throws JSONException {
        GroupModel groupModel;
        selectedTMArrayList.clear();
        for (int i = 0; i < users.length(); i++) {
            JSONObject jsonObject = users.getJSONObject(i);
            groupModel = new GroupModel();
            groupModel.setId(jsonObject.getString("id"));
            groupModel.setName(jsonObject.getString("name"));
            selectedTMArrayList.add(groupModel);
        }
        TM_TYPE = "TM";
        loadRecylcerview(selectedTMArrayList, TM_TYPE);

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

    @Override
    public void EditGroup(ViewGroupModel viewGroupModel) {
            hideData();
            tv_group_name.setText(viewGroupModel.getName());
            tv_group_description.setText(viewGroupModel.getDescription());
            btn_cancel_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    unhideData();
                    ViewGroupsData();
                }
            });
            btn_update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    unhideData();
                    if(tv_group_name.getText().toString().equals("")){
                        tv_group_name.setError("Group name Required");
                        AndroidUtils.showToast("Group name Required",getContext());
//                        assignGroupsList.clear();
                    }else if(tv_group_description.getText().toString().equals("")){
                        tv_group_description.setError("Description Required");
                        AndroidUtils.showToast("Description Required",getContext());
//                        assignGroupsList.clear();
                    }else {
                        try{
                        callUpdateGroups(tv_group_name.getText().toString().trim(),tv_group_description.getText().toString().trim(),viewGroupModel.getId());
                    } catch (JSONException ex) {
                            ex.printStackTrace();
                        }
                    }
//                    ViewGroupsData();

                }
            });

    }

    private void callUpdateGroups(String group_name, String description, String id) throws JSONException  {
        try{
        JSONObject postData = new JSONObject();
        JSONArray members = new JSONArray();
//        for (int i = 0; i < list_item.size(); i++) {
//            GroupModel model = list_item.get(i);
//            members.put(model.getId());
//        }
        postData.put("name",group_name);
        postData.put("description",description);
//        postData.put("groupHead",group_head);
//        postData.put("members",members);
//                AndroidUtils.showToast("Selected:"+members+" "+"GH"+group_head,getContext());
        WebServiceHelper.callHttpWebService(this, getContext(), WebServiceHelper.RestMethodType.PATCH, "v3/group/"+id, "Update Groups", postData.toString());
    } catch (Exception e) {
        e.printStackTrace();
    }
    }

    private void hideData() {
        ll_group_list.setVisibility(View.GONE);
        ll_tm.setVisibility(View.GONE);
        ll_select_all.setVisibility(View.GONE);
        ll_select_tm.setVisibility(View.GONE);
        rv_view_groups.setVisibility(View.GONE);
        et_search.setVisibility(View.GONE);
        cv_groups.setVisibility(View.VISIBLE);
        ll_edit_groups.setVisibility(View.VISIBLE);
        tv_selected_members.setVisibility(View.GONE);
    }

    private void unhideData() {
        ll_group_list.setVisibility(View.VISIBLE);
        ll_tm.setVisibility(View.VISIBLE);
        ll_select_all.setVisibility(View.VISIBLE);
        ll_select_tm.setVisibility(View.VISIBLE);
        rv_view_groups.setVisibility(View.VISIBLE);
        et_search.setVisibility(View.VISIBLE);
        cv_groups.setVisibility(View.GONE);
        ll_edit_groups.setVisibility(View.GONE);
        tv_selected_members.setVisibility(View.VISIBLE);
    }
}
