package com.digicoffer.lauditor.ClientRelationships;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.digicoffer.lauditor.ClientRelationships.Model.CountriesDO;
import com.digicoffer.lauditor.ClientRelationships.Model.SearchModel;
import com.digicoffer.lauditor.Groups.GroupModels.ViewGroupModel;
import com.digicoffer.lauditor.Members.GroupsAdapter;
import com.digicoffer.lauditor.R;
import com.digicoffer.lauditor.Webservice.AsyncTaskCompleteListener;
import com.digicoffer.lauditor.Webservice.HttpResultDo;
import com.digicoffer.lauditor.Webservice.WebServiceHelper;
import com.digicoffer.lauditor.common.AndroidUtils;
import com.digicoffer.lauditor.common_adapters.CommonSpinnerAdapter;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.pgpainless.key.selection.key.util.And;

import java.util.ArrayList;
import java.util.Date;

public class ClientRelationship extends Fragment implements AsyncTaskCompleteListener, View.OnClickListener {

    private RadioGroup rg_add_relationships, rg_individual_entity;
    ArrayList<SearchModel> searchModelsList = new ArrayList<>();
    RadioButton rb_add_relationship, rb_view_relationships, rb_individual, rb_entity;
    LinearLayout ll_entity_name, ll_contact_person, ll_first_name, ll_last_name, ll_contatc_phone;
    TextInputEditText et_search_relationships, et_search_individual, tv_individual_email, tv_individual_confirm_email, tv_individual_firstname, tv_individual_last_name, tv_entity_name, tv_entity_contact_person, tv_entity_phone_number;
    Button btn_search_individual, btn_relationships_cancel, btn_send_request;
    TextView tv_response;
    TextInputLayout tl_individual_country;
    Spinner sp_country;
    GroupsAdapter groupsAdapter;
    RecyclerView rv_relationship_groups;
    private CheckBox chk_select_all;
    ArrayList<ViewGroupModel> groupsList = new ArrayList<>();
    ArrayList<CountriesDO> countriesList = new ArrayList<>();
    CardView cv_details;
    private String country_name;


    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.btn_search_individual:
                try {
//                searchModelsList.clear();
//                tv_response.setText("");
                    callSearchIndividualWebservice();
                } catch (JSONException e) {
                    e.printStackTrace();
                    AndroidUtils.showToast(e.getMessage(), getContext());
                }
        }
    }


    private void callGroupsWebservice() {
        JSONObject postdata = new JSONObject();
        WebServiceHelper.callHttpWebService(this, getContext(), WebServiceHelper.RestMethodType.GET, "v3/groups", "Get Groups", postdata.toString());


    }

    private void callSearchIndividualWebservice() throws JSONException {
        JSONObject postdata = new JSONObject();
        postdata.put("email", et_search_individual.getText().toString());
        WebServiceHelper.callHttpWebService(this, getContext(), WebServiceHelper.RestMethodType.POST, "v2/relationship/search/consumer", "Search Consumer", postdata.toString());
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        callCountriesWebService();
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.client_relationships, container, false);
        callGroupsWebservice();
        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rg_add_relationships = view.findViewById(R.id.rgTask);
        rb_add_relationship = view.findViewById(R.id.add_relationship);
        rb_view_relationships = view.findViewById(R.id.view_relationship);
        rg_individual_entity = view.findViewById(R.id.entity);
        rb_individual = view.findViewById(R.id.add_individiual);
        rv_relationship_groups = view.findViewById(R.id.rv_relationship_groups);
        rb_entity = view.findViewById(R.id.add_entity);
        ll_entity_name = view.findViewById(R.id.ll_entity_name);
        ll_contact_person = view.findViewById(R.id.ll_contact_person);
        ll_contatc_phone = view.findViewById(R.id.ll_entity_number);
        sp_country = view.findViewById(R.id.sp_country);
        et_search_individual = view.findViewById(R.id.et_search_individual);
        btn_search_individual = view.findViewById(R.id.btn_search_individual);
        et_search_relationships = view.findViewById(R.id.et_search_relationships);
        tv_response = view.findViewById(R.id.tv_response);
        cv_details = view.findViewById(R.id.cv_details);
        chk_select_all = view.findViewById(R.id.chk_select_all);
        tv_individual_email = view.findViewById(R.id.tv_individual_email);
        tv_entity_contact_person = view.findViewById(R.id.tv_entity_contact_person);
        tv_entity_name = view.findViewById(R.id.tv_entity_name);
        tv_entity_phone_number = view.findViewById(R.id.tv_entity_phone_number);
        tv_individual_confirm_email = view.findViewById(R.id.tv_individual_confirm_email);
        tv_individual_email = view.findViewById(R.id.tv_individual_email);
        tl_individual_country = view.findViewById(R.id.tl_individual_country);
        tv_individual_firstname = view.findViewById(R.id.tv_individual_firstname);
        tv_individual_last_name = view.findViewById(R.id.tv_individual_last_name);
        btn_send_request = view.findViewById(R.id.btn_send_request);
        btn_relationships_cancel = view.findViewById(R.id.btn_relationships_cancel);
        enableAlpha();
        btn_search_individual.setOnClickListener(this);
        rb_add_relationship.setTextColor(getContext().getResources().getColor(R.color.white));
        rb_view_relationships.setTextColor(getContext().getResources().getColor(R.color.black));
        rg_add_relationships.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.add_relationship:
                        rb_add_relationship.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.button_left_green_background));
                        rb_add_relationship.setTextColor(getContext().getResources().getColor(R.color.white));
                        rb_view_relationships.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.button_right_background));
                        rb_add_relationship.setTextColor(getContext().getResources().getColor(R.color.white));
                        rb_view_relationships.setTextColor(getContext().getResources().getColor(R.color.black));
                        break;
                    case R.id.view_relationship:

                        rb_add_relationship.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.button_left_background));
                        rb_view_relationships.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.button_right_green_count));
                        rb_add_relationship.setTextColor(getContext().getResources().getColor(R.color.black));
                        rb_view_relationships.setTextColor(getContext().getResources().getColor(R.color.white));
                        break;
                }
            }
        });
        rg_individual_entity.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.add_individiual:
                        hideEntityData();
                        rb_individual.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.button_left_green_background));
                        rb_entity.setTextColor(getContext().getResources().getColor(R.color.white));
                        rb_entity.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.button_right_background));
                        rb_individual.setTextColor(getContext().getResources().getColor(R.color.white));
                        rb_entity.setTextColor(getContext().getResources().getColor(R.color.black));
                        break;
                    case R.id.add_entity:
                        unhideEntityData();
                        clearIndividualData();
                        rb_individual.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.button_left_background));
                        rb_entity.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.button_right_green_count));
                        rb_individual.setTextColor(getContext().getResources().getColor(R.color.black));
                        rb_entity.setTextColor(getContext().getResources().getColor(R.color.white));
                        break;
                }
            }
        });
        tl_individual_country.setEnabled(false);
        sp_country.setEnabled(false);

    }
    public void callCountriesWebService() {
        JSONObject jsonData = new JSONObject();
        try {

            WebServiceHelper.callHttpWebService(this, getContext(), WebServiceHelper.RestMethodType.GET, "countries", "COUNTRIES", jsonData.toString());
        } catch (Exception e) {

        }
    }
    private void disableAlpha() {

        tv_individual_email.setBackground(getContext().getResources().getDrawable(R.drawable.rectangle_grey_background));
        tv_individual_confirm_email.setBackground(getContext().getResources().getDrawable(R.drawable.rectangle_grey_background));
        tv_individual_firstname.setBackground(getContext().getResources().getDrawable(R.drawable.rectangle_grey_background));
        tv_individual_last_name.setBackground(getContext().getResources().getDrawable(R.drawable.rectangle_grey_background));
        tv_entity_phone_number.setBackground(getContext().getResources().getDrawable(R.drawable.rectangle_grey_background));
        tv_entity_contact_person.setBackground(getContext().getResources().getDrawable(R.drawable.rectangle_grey_background));
        tv_entity_name.setBackground(getContext().getResources().getDrawable(R.drawable.rectangle_grey_background));
        btn_relationships_cancel.setBackground(getContext().getResources().getDrawable(R.drawable.cancel_button_background));
        btn_send_request.setBackground(getContext().getResources().getDrawable(R.drawable.save_button_background));
        tl_individual_country.setBackground(getContext().getResources().getDrawable(R.drawable.rectangle_grey_background));

    }

    private void enableAlpha() {
        tv_individual_email.getBackground().setAlpha(100);
        tv_individual_confirm_email.getBackground().setAlpha(100);
        tv_individual_firstname.getBackground().setAlpha(100);
        tv_individual_last_name.getBackground().setAlpha(100);
        tv_entity_phone_number.getBackground().setAlpha(100);
        tv_entity_contact_person.getBackground().setAlpha(100);
        tv_entity_name.getBackground().setAlpha(100);
        btn_relationships_cancel.getBackground().setAlpha(100);
        btn_send_request.getBackground().setAlpha(100);
        tl_individual_country.getBackground().setAlpha(100);

//        rv_relationship_groups.getBackground().setAlpha(100);
    }

    private void hideEntityData() {
        ll_entity_name.setVisibility(View.GONE);
        ll_contact_person.setVisibility(View.GONE);
        ll_contatc_phone.setVisibility(View.GONE);
    }

    private void unhideEntityData() {
        ll_entity_name.setVisibility(View.VISIBLE);
        ll_contact_person.setVisibility(View.VISIBLE);
        ll_contatc_phone.setVisibility(View.VISIBLE);
    }

    @Override
    public void onAsyncTaskComplete(HttpResultDo httpResult) {
        if (httpResult.getResult() == WebServiceHelper.ServiceCallStatus.Success) {
            try {
                JSONObject result = new JSONObject(httpResult.getResponseContent());
                Log.i("Tag", "Info:" + httpResult.getStatus_code());
                Log.i("Tag", "Info:" + result);

                if (httpResult.getRequestType().equals("Search Consumer")) {
                    loadIndividualData(result);

                } else if (httpResult.getRequestType().equals("Get Groups")) {
                    JSONArray data = result.getJSONArray("data");
                    loadViewGroups(data);
                }else if (httpResult.getRequestType() == "COUNTRIES") {

                    JSONArray jsonArray = (new JSONObject(result.getString("data"))).getJSONArray("countries");
                    CountriesDO countriesDO;
                    countriesList.clear();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        countriesDO = new CountriesDO();
                        countriesDO.setName(String.valueOf(jsonArray.getJSONArray(i).get(1)));
                        countriesDO.setValue(String.valueOf(jsonArray.getJSONArray(i).get(0)));
                        countriesList.add(countriesDO);
                    }
                    loadCountryData();

//                    callHealthProfileWebservice();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void loadCountryData() {
        CommonSpinnerAdapter adapter = new CommonSpinnerAdapter(getActivity(), countriesList);
        Log.i("ArrayList","Info:"+countriesList);
//        ArrayAdapter adaptador = new ArrayAdapter(User_Profile.this, android.R.layout.simple_spinner_item, sorted_countriesList);
//        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

//        spinner.setAdapter(adaptador);
        sp_country.setAdapter(adapter);
        sp_country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                country_name = countriesList.get(position).getName();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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

//        if (groupsList.size() != 0) {

        rv_relationship_groups.setLayoutManager(new GridLayoutManager(getContext(), 1));
        groupsAdapter = new GroupsAdapter(groupsList);
        rv_relationship_groups.setAdapter(groupsAdapter);
        rv_relationship_groups.setHasFixedSize(true);
        et_search_relationships.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                groupsAdapter.getFilter().filter(s);
            }

        });
        btn_relationships_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                et_search_relationships.setText("");
                groupsList.clear();
                clearIndividualData();
                callGroupsWebservice();
            }
        });
        btn_send_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!individualValidation()){
                    callIndividualRequestWebservice();
                }
            }
        });
        chk_select_all.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                groupsAdapter.selectOrDeselectAll(isChecked);
            }
        });

//        }
//        else {
////            cv_members_details.setVisibility(View.GONE);
//        }
    }

    private void callIndividualRequestWebservice() {
        Log.i("Tag","Country_Name:"+country_name);

          AndroidUtils.showToast("Request sent successfully", getContext());

    }

    private void loadIndividualData(JSONObject result) throws JSONException {

        SearchModel searchModel = new SearchModel();
        searchModel.setMsg(result.getString("msg"));
        searchModel.setError(result.getBoolean("error"));
        if (searchModel.getError()) {
            Log.i("Tag", "Info:" + searchModel.getError());
            tv_response.setText(et_search_individual.getText().toString() + "-" + "not found. Please fill in the details below to send relationship invite");
            tv_response.setTextColor(getContext().getResources().getColor(R.color.Red));
            disableAlpha();
            enableIndividualData();
            clearIndividualData();
//                  et_search_individual.setText("");
        } else {
            Log.i("Tag", "Info:" + searchModel.getError());
            searchModel.setFirstName(result.getString("firstName"));
            searchModel.setLastName(result.getString("lastName"));
            searchModel.setCountry(result.getString("country"));
            searchModel.setConsumerID(result.getString("consumerId"));
            searchModel.setName(result.getString("name"));
            tv_response.setText(searchModel.getMsg());
            tv_response.setTextColor(getContext().getResources().getColor(R.color.Blue_text_color));
            for (int i=0;i<countriesList.size();i++){
                if (countriesList.get(i).getName().equals(searchModel.getCountry())){
                    sp_country.setSelection(i);
                }
            }
            loadUI(searchModel);
//
//                    et_search_individual.setText("");

        }
//        searchModelsList.add(searchModel);

    }

    private void clearIndividualData() {
        tv_individual_firstname.setText("");
        tv_individual_last_name.setText("");
        tv_individual_email.setText("");
        tv_individual_confirm_email.setText("");
        et_search_individual.setText("");
        for (int i = 0; i < countriesList.size(); i++) {
            if (countriesList.get(i).getName().equals("Choose country")) {
                sp_country.setSelection(i);
            }
        }
    }

    private void enableIndividualData() {

        tv_individual_firstname.setEnabled(true);
        tv_individual_last_name.setEnabled(true);
        tv_individual_email.setEnabled(true);
        tv_individual_confirm_email.setEnabled(true);
        tv_entity_phone_number.setEnabled(true);
        tv_entity_name.setEnabled(true);
        tv_entity_contact_person.setEnabled(true);
        btn_send_request.setEnabled(true);
        btn_relationships_cancel.setEnabled(true);
        tl_individual_country.setEnabled(true);
        sp_country.setEnabled(true);
//        rv_relationship_groups.setEnabled(true);
    }

    private void disableIndividualData() {
        tv_individual_firstname.setEnabled(false);
        tv_individual_last_name.setEnabled(false);
        tv_individual_email.setEnabled(false);
        tv_individual_confirm_email.setEnabled(false);
        tv_entity_phone_number.setEnabled(false);
        tv_entity_name.setEnabled(false);
        tv_entity_contact_person.setEnabled(false);
        btn_send_request.setEnabled(false);
        btn_relationships_cancel.setEnabled(false);
        sp_country.setEnabled(false);
//        rv_relationship_groups.setEnabled(false);
    }

    private void loadUI(SearchModel searchModel) {
//        disableAlpha();
        enableAlpha();
        disableIndividualData();
        btn_relationships_cancel.setBackground(getContext().getResources().getDrawable(R.drawable.cancel_button_background));
        btn_send_request.setBackground(getContext().getResources().getDrawable(R.drawable.save_button_background));
        btn_send_request.setEnabled(true);
        btn_relationships_cancel.setEnabled(true);

        tv_individual_firstname.setText(searchModel.getFirstName());
        tv_individual_last_name.setText(searchModel.getLastName());
        tv_individual_email.setText(et_search_individual.getText().toString());
        tv_individual_confirm_email.setText(et_search_individual.getText().toString());

    }

    private boolean individualValidation(){
        boolean status = false;
        if(tv_individual_email.getText().toString().equals("")){
            tv_individual_email.setError("Email is required");
            tv_individual_email.requestFocus();
            AndroidUtils.showToast("Email is required",getContext());
            status = true;
        }else if(tv_individual_confirm_email.getText().toString().equals("")){
            tv_individual_confirm_email.setError("Confirm email is required");
            tv_individual_confirm_email.requestFocus();
            AndroidUtils.showToast("Confirm email is required",getContext());
            status = true;
        } else if (!tv_individual_email.getText().toString().equals(tv_individual_confirm_email.getText().toString())) {
            tv_individual_confirm_email.setError("Email and Confirm Email doesn't match");
            AndroidUtils.showToast("Email and Confirm Email doesn't match", getContext());
            status = true;
        }
        else if(tv_individual_firstname.getText().toString().equals("")){
            tv_individual_firstname.setError("First Name is required");
            tv_individual_firstname.requestFocus();
            AndroidUtils.showToast("First Name is required",getContext());
            status = true;
        }else if(tv_individual_last_name.getText().toString().equals("")){
            tv_individual_last_name.setError("Last Name is required");
            tv_individual_last_name.requestFocus();
            AndroidUtils.showToast("Last Name is required",getContext());
            status = true;
        }else if(country_name.equals("Choose country")){
            AndroidUtils.showToast("Please select a country",getContext());
        }else{
             if(!tv_individual_email.getText().toString().equals("")&& Patterns.EMAIL_ADDRESS.matcher(tv_individual_email.getText().toString()).matches()){
                status = false;
            }else
             {
                 tv_individual_email.setError("Enter a valid email address");
                 AndroidUtils.showToast("Enter a valid email address", getContext());
                 status = true;
             }
        }

        return status;
    }
}
