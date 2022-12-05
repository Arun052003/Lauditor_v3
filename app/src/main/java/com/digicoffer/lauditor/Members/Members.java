package com.digicoffer.lauditor.Members;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.digicoffer.lauditor.Groups.Adapters.ViewGroupsAdpater;
import com.digicoffer.lauditor.Groups.GroupModels.GroupModel;
import com.digicoffer.lauditor.Groups.GroupModels.ViewGroupModel;
import com.digicoffer.lauditor.R;
import com.digicoffer.lauditor.Webservice.AsyncTaskCompleteListener;
import com.digicoffer.lauditor.Webservice.HttpResultDo;
import com.digicoffer.lauditor.Webservice.WebServiceHelper;
import com.digicoffer.lauditor.common.AndroidUtils;
import com.digicoffer.lauditor.common_adapters.CommonSpinnerAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Members extends Fragment implements AsyncTaskCompleteListener {
    TextView tv_member_name,tv_designation,tv_email,tv_confirm_email,tv_default_rate,tv_create_members,tv_view_members,et_search_members;
    Spinner sp_default_currency;
    AppCompatButton btn_cancel_members,bt_save_members,bt_cancel,bt_save;
    RecyclerView rv_selected_member,rv_view_members;
    String default_currency = "";
    CardView cv_details,cv_members_details;
    GroupsAdapter groupsAdapter = null;
    ArrayList<String> currency_list = new ArrayList<>();
    ArrayList<MembersModel> members_list = new ArrayList<>();
    ArrayList<GroupModel> MembersList = new ArrayList<>();
    ArrayList<ViewGroupModel> groupsList = new ArrayList<>();
    AlertDialog progress_dialog;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.create_members, container, false);
        tv_member_name = v.findViewById(R.id.tv_create_member_name);
        tv_designation = v.findViewById(R.id.tv_designation);
        tv_email = v.findViewById(R.id.tv_email);
        rv_view_members = v.findViewById(R.id.rv_view_members);
        tv_confirm_email = v.findViewById(R.id.tv_confirm_email);
        tv_default_rate = v.findViewById(R.id.tv_default_rate);
        tv_create_members = v.findViewById(R.id.tv_create_members);
        tv_view_members = v.findViewById(R.id.tv_view_members);
        et_search_members = v.findViewById(R.id.et_search_members);
        sp_default_currency = v.findViewById(R.id.sp_default_currency);
        btn_cancel_members = v.findViewById(R.id.btn_cancel_members);
        bt_save_members = v.findViewById(R.id.btn_save_members);
        cv_details = v.findViewById(R.id.cv_details);
        cv_members_details = v.findViewById(R.id.cv_details_2);
//        bt_cancel = v.findViewById(R.id.btn_cancel_members);
//        bt_save = v.findViewById(R.id.btn_save_members);
        rv_selected_member = v.findViewById(R.id.rv_selected_member);
        tv_create_members.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.button_left_green_background));
        tv_view_members.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.button_right_background));
        callGroupsWebservice();
        tv_create_members.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_create_members.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.button_left_green_background));
                tv_view_members.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.button_right_background));
                CreateMembersData();
            }
        });
        tv_view_members.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewMembersData();

            }
        });
        currency_list = getCurrency_list();
        final CommonSpinnerAdapter adapter = new CommonSpinnerAdapter(getActivity(),currency_list);
        sp_default_currency.setAdapter(adapter);
        sp_default_currency.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                default_currency = currency_list.get(adapterView.getSelectedItemPosition());
//                AndroidUtils.showToast("selected item is:"+default_currency,getContext());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        bt_save_members.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!validation()){
                    callCreateMemberWebservice(tv_member_name.getText().toString().trim(),tv_designation.getText().toString().trim(),tv_default_rate.getText().toString().trim(),tv_email.getText().toString().trim(),tv_confirm_email.getText().toString().trim());
                }

            }
        });
        return v;
    }

    private void callGroupsWebservice() {
        JSONObject postdata = new JSONObject();
        WebServiceHelper.callHttpWebService(this,getContext(), WebServiceHelper.RestMethodType.GET,"v3/groups","Get Groups",postdata.toString());


    }
boolean validation(){
        boolean status = false;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    Pattern pattern;
    Matcher matcher;
    if (tv_member_name.getText().toString().equals("")){
        tv_member_name.setError("Name is required ");
        AndroidUtils.showToast("Name is required ",getContext());
        status = true;
    }
    else if(tv_designation.getText().toString().equals("")){
        tv_designation.setError("Designation is required");
        AndroidUtils.showToast("Designation is required",getContext());
        status = true;
    }else if (tv_default_rate.getText().toString().equals("")){
        tv_default_rate.setError("Hourly rate is required");
        AndroidUtils.showToast("Hourly rate is required",getContext());
        status = true;
    }else if(tv_email.getText().toString().equals("")){
        tv_email.setError("Email is required");
        AndroidUtils.showToast("Email is required",getContext());
        status = true;
    }else if(tv_confirm_email.getText().toString().equals("")){
        tv_confirm_email.setError("Confirm email is required");
        AndroidUtils.showToast("Confirm email is required",getContext());
        status = true;
    }
    else if (!tv_email.getText().toString().equals(tv_confirm_email.getText().toString())){
        tv_confirm_email.setError("Email and Confirm Email doesn't match");
        AndroidUtils.showToast("Email and Confirm Email doesn't match",getContext());
        status = true;
    }else {
        if(!tv_email.getText().toString().trim().equals(""))
        {
            pattern = Pattern.compile(emailPattern);
            matcher = pattern.matcher(tv_email.getText().toString());
            if (!matcher.matches()){
                tv_email.setError("Enter a valid email address");
//                tv_email.requestFocus();
                status = true;
            }
        }
    }
    return status;
}
    private void CreateMembersData() {
        cv_details.setVisibility(View.VISIBLE);
        cv_members_details.setVisibility(View.VISIBLE);
        members_list.clear();
        rv_view_members.removeAllViews();
    }

    private void ViewMembersData() {
        tv_view_members.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.button_right_green_count));
        tv_create_members.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.button_left_background));
        cv_details.setVisibility(View.GONE);
        cv_members_details.setVisibility(View.GONE);
        callViewGroupsWebservice();
    }

    private void callViewGroupsWebservice() {

        try {
            JSONObject postdata = new JSONObject();
            progress_dialog = AndroidUtils.get_progress(getActivity());
            WebServiceHelper.callHttpWebService(this, getContext(), WebServiceHelper.RestMethodType.GET, "v3/members", "Get Members", postdata.toString());
        } catch (Exception e) {
            if (progress_dialog != null && progress_dialog.isShowing())
                AndroidUtils.dismiss_dialog(progress_dialog);
        }
    }

    private void callCreateMemberWebservice(String name, String designation, String default_rate, String email, String confirm_email) {
        try {
            JSONObject postdata = new JSONObject();
            JSONArray groups = new JSONArray();
            for (int i=0;i<groupsAdapter.getList_item().size();i++){
                ViewGroupModel viewGroupModel = groupsAdapter.getList_item().get(i);
                if (viewGroupModel.isChecked()){
                    groups.put(viewGroupModel.getId());
                }

            }
            progress_dialog = AndroidUtils.get_progress(getActivity());
            postdata.put("currency",default_currency);
            postdata.put("defaultRate",default_rate);
            postdata.put("designation",designation);
            postdata.put("email",email);
            postdata.put("emailConfirm",confirm_email);
            postdata.put("groups",groups);
            postdata.put("name",name);
            WebServiceHelper.callHttpWebService(this, getContext(), WebServiceHelper.RestMethodType.POST, "v3/member", "Create Members",postdata.toString());
        } catch (Exception e) {
            if (progress_dialog != null && progress_dialog.isShowing())
                AndroidUtils.dismiss_dialog(progress_dialog);
        }
    }

    @Override
    public void onClick(View view) {

    }
private ArrayList<String> getCurrency_list(){
        ArrayList<String> currency = new ArrayList<>();
        currency.add("USDollar(USD)");
        currency.add("Euro(EUR)");
        currency.add("Japanese Yen(JPY)");
        currency.add("Pound(GBP)");
        currency.add("AustralianDollar(AUD)");
        currency.add("CanadianDOllar(CAD)");
        currency.add("SwissFranc(CHF)");
        currency.add("KuwaitiDinar(KWD)");
        currency.add("BahrainiDinar(BHD)");
        return currency;
}
    @Override
    public void onAsyncTaskComplete(HttpResultDo httpResult) {
          if(progress_dialog !=null && progress_dialog.isShowing())
              AndroidUtils.dismiss_dialog(progress_dialog);
           if (httpResult.getResult() == WebServiceHelper.ServiceCallStatus.Success);
        try{
            JSONObject result = new JSONObject(httpResult.getResponseContent());
            if (httpResult.getRequestType().equals("Create Members")) {
              AndroidUtils.showToast(result.getString("msg"),getContext());
                ViewMembersData();
            }else if(httpResult.getRequestType().equals("Get Members")){
                JSONObject data = result.getJSONObject("data");
                JSONArray users = data.getJSONArray("users");
                loadMembers(users);
            }else if (httpResult.getRequestType().equals("Get Groups")){
                JSONArray data = result.getJSONArray("data");
                loadViewGroups(data);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadViewGroups(JSONArray data) throws JSONException {

        ViewGroupModel viewGroupModel;
        groupsList.clear();
        for (int i = 0; i < data.length(); i++) {
            JSONObject jsonObject = data.getJSONObject(i);
            viewGroupModel = new ViewGroupModel();
            viewGroupModel.setId(jsonObject.getString("id"));
            String date = jsonObject.getString("created");
            Date date_new = AndroidUtils.stringToDateTimeDefault(date, "yyyy-MM-dd'T'HH:mm:ss.SSS");
            String created = AndroidUtils.getDateToString(date_new, "MMM dd YYYY");
            viewGroupModel.setCreated(created);
            JSONArray members = jsonObject.getJSONArray("members");
            viewGroupModel.setMembers(members);
            viewGroupModel.setDescription(jsonObject.getString("description"));
            viewGroupModel.setName(jsonObject.getString("name"));
            JSONObject group_head = jsonObject.getJSONObject("groupHead");
            viewGroupModel.setGroup_head_id(group_head.getString("id"));
            viewGroupModel.setGroup_head_name(group_head.getString("name"));
            viewGroupModel.setOwner_name(group_head.getString("name"));
            groupsList.add(viewGroupModel);
        }
        Log.i("ArrayList", "info" + groupsList.toString());
        String mtag = "VG";
        loadGroupsRecylerview();

    }

    private void loadGroupsRecylerview() {

        if (groupsList.size()!=0) {
            rv_selected_member.setLayoutManager(new GridLayoutManager(getContext(), 1));
             groupsAdapter = new GroupsAdapter(groupsList);
            rv_selected_member.setAdapter(groupsAdapter);
            rv_selected_member.setHasFixedSize(true);
        }
        else{
            cv_members_details.setVisibility(View.GONE);
        }
    }

    private void loadMembers(JSONArray users) throws JSONException {
        MembersModel membersModel;
        members_list.clear();
        for (int i = 0; i < users.length(); i++) {
            JSONObject jsonObject = users.getJSONObject(i);
            membersModel = new MembersModel();
            membersModel.setId(jsonObject.getString("id"));
            membersModel.setName(jsonObject.getString("name"));
            membersModel.setCurrency(jsonObject.getString("currency"));
            membersModel.setDefaultRate(jsonObject.getString("defaultRate"));
            membersModel.setDesignation(jsonObject.getString("designation"));
            membersModel.setEmail(jsonObject.getString("email"));
            membersModel.setLastLogin(jsonObject.getString("lastLogin"));
            membersModel.setGroups(jsonObject.getJSONArray("groups"));

            members_list.add(membersModel);
        }

        loadRecylcerview();

    }

    private void loadRecylcerview() {
       rv_view_members.setLayoutManager(new GridLayoutManager(getContext(), 1));
       MembersAdapter adapter = new MembersAdapter(members_list);
       rv_view_members.setAdapter(adapter);
       rv_view_members.setHasFixedSize(true);
    }
}
