package com.digicoffer.lauditor.ClientRelationships;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.digicoffer.lauditor.ClientRelationships.Model.SearchModel;
import com.digicoffer.lauditor.R;
import com.digicoffer.lauditor.Webservice.AsyncTaskCompleteListener;
import com.digicoffer.lauditor.Webservice.HttpResultDo;
import com.digicoffer.lauditor.Webservice.WebServiceHelper;
import com.digicoffer.lauditor.common.AndroidUtils;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ClientRelationship extends Fragment implements AsyncTaskCompleteListener,View.OnClickListener {

    private RadioGroup rg_add_relationships,rg_individual_entity;
    ArrayList<SearchModel> searchModelsList = new ArrayList<>();
    RadioButton rb_add_relationship, rb_view_relationships,rb_individual,rb_entity;
    LinearLayout ll_entity_name,ll_contact_person,ll_first_name,ll_last_name,ll_contatc_phone;
    TextInputEditText et_search_individual,tv_individual_email,tv_individual_confirm_email,tv_individual_firstname,tv_individual_last_name,tv_entity_name,tv_entity_contact_person,tv_entity_phone_number;
    Button btn_search_individual;
    TextView tv_response;
    CardView cv_details;


    @Override
    public void onClick(View view) {
    switch (view.getId()){

        case R.id.btn_search_individual:
            try{
//                searchModelsList.clear();
//                tv_response.setText("");
            callSearchIndividualWebservice();
    } catch (JSONException e) {
                e.printStackTrace();
                AndroidUtils.showToast(e.getMessage(),getContext());
            }
    }
    }

    private void callSearchIndividualWebservice() throws JSONException {
        JSONObject postdata = new JSONObject();
        postdata.put("email",et_search_individual.getText().toString());
        WebServiceHelper.callHttpWebService(this,getContext(), WebServiceHelper.RestMethodType.POST,"v2/relationship/search/consumer","Search Consumer",postdata.toString());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.client_relationships, container, false);
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
        rb_entity = view.findViewById(R.id.add_entity);
        ll_entity_name = view.findViewById(R.id.ll_entity_name);
        ll_contact_person  = view.findViewById(R.id.ll_contact_person);
        ll_contatc_phone = view.findViewById(R.id.ll_entity_number);
        et_search_individual = view.findViewById(R.id.et_search_individual);
        btn_search_individual = view.findViewById(R.id.btn_search_individual);
        tv_response = view.findViewById(R.id.tv_response);
        cv_details = view.findViewById(R.id.cv_details);
        tv_individual_email = view.findViewById(R.id.tv_individual_email);
        tv_entity_contact_person = view.findViewById(R.id.tv_entity_contact_person);
        tv_entity_name = view.findViewById(R.id.tv_entity_name);
        tv_entity_phone_number = view.findViewById(R.id.tv_entity_phone_number);
        tv_individual_confirm_email = view.findViewById(R.id.tv_individual_confirm_email);
        tv_individual_email = view.findViewById(R.id.tv_individual_email);
        tv_individual_firstname = view.findViewById(R.id.tv_individual_firstname);
        tv_individual_last_name = view.findViewById(R.id.tv_individual_last_name);
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
                        rb_individual.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.button_left_background));
                        rb_entity.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.button_right_green_count));
                        rb_individual.setTextColor(getContext().getResources().getColor(R.color.black));
                        rb_entity.setTextColor(getContext().getResources().getColor(R.color.white));
                        break;
                }
            }
        });

    }

    private void disableAlpha() {

        tv_individual_email.setBackground(getContext().getResources().getDrawable(R.drawable.rectangle_grey_background));
        tv_individual_confirm_email.setBackground(getContext().getResources().getDrawable(R.drawable.rectangle_grey_background));
        tv_individual_firstname.setBackground(getContext().getResources().getDrawable(R.drawable.rectangle_grey_background));
        tv_individual_last_name.setBackground(getContext().getResources().getDrawable(R.drawable.rectangle_grey_background));
        tv_entity_phone_number.setBackground(getContext().getResources().getDrawable(R.drawable.rectangle_grey_background));
        tv_entity_contact_person.setBackground(getContext().getResources().getDrawable(R.drawable.rectangle_grey_background));
        tv_entity_name.setBackground(getContext().getResources().getDrawable(R.drawable.rectangle_grey_background));

    }
    private void enableAlpha(){
        tv_individual_email.getBackground().setAlpha(100);
        tv_individual_confirm_email.getBackground().setAlpha(100);
        tv_individual_firstname.getBackground().setAlpha(100);
        tv_individual_last_name.getBackground().setAlpha(100);
        tv_entity_phone_number.getBackground().setAlpha(100);
        tv_entity_contact_person.getBackground().setAlpha(100);
        tv_entity_name.getBackground().setAlpha(100);
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
        if (httpResult.getResult()== WebServiceHelper.ServiceCallStatus.Success){
            try{
                JSONObject result = new JSONObject(httpResult.getResponseContent());
                Log.i("Tag","Info:"+httpResult.getStatus_code());
                Log.i("Tag","Info:"+result);
                boolean error = result.getBoolean("error");
                String msg = result.getString("msg");
                if (httpResult.getRequestType().equals("Search Consumer")){
                        loadIndividualData(result);

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void loadIndividualData(JSONObject result) throws JSONException{
        SearchModel searchModel = new SearchModel();
        searchModel.setMsg(result.getString("msg"));
        searchModel.setError(result.getBoolean("error"));
        if (searchModel.getError()){
            Log.i("Tag","Info:"+searchModel.getError());
            tv_response.setText(et_search_individual.getText().toString()+"-"+"not found. Please in the details below to send relationship invite");
            tv_response.setTextColor(getContext().getResources().getColor(R.color.Red));
            disableAlpha();
            enableIndividualData();
            clearIndividualData();
//                  et_search_individual.setText("");
        }
        else{
            Log.i("Tag","Info:"+searchModel.getError());
            searchModel.setFirstName(result.getString("firstName"));
            searchModel.setLastName(result.getString("lastName"));
            searchModel.setCountry(result.getString("country"));
            searchModel.setConsumerID(result.getString("consumerId"));
            searchModel.setName(result.getString("name"));
            tv_response.setText(searchModel.getMsg());
            tv_response.setTextColor(getContext().getResources().getColor(R.color.Blue_text_color));
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

    }

    private void enableIndividualData() {

        tv_individual_firstname.setEnabled(true);
        tv_individual_last_name.setEnabled(true);
        tv_individual_email.setEnabled(true);
        tv_individual_confirm_email.setEnabled(true);
        tv_entity_phone_number.setEnabled(true);
        tv_entity_name.setEnabled(true);
        tv_entity_contact_person.setEnabled(true);
    }

    private void disableIndividualData(){
        tv_individual_firstname.setEnabled(false);
        tv_individual_last_name.setEnabled(false);
        tv_individual_email.setEnabled(false);
        tv_individual_confirm_email.setEnabled(false);
        tv_entity_phone_number.setEnabled(false);
        tv_entity_name.setEnabled(false);
        tv_entity_contact_person.setEnabled(false);
    }

    private void loadUI(SearchModel searchModel) {
//        disableAlpha();
        enableAlpha();
        disableIndividualData();
        tv_individual_firstname.setText(searchModel.getFirstName());
        tv_individual_last_name.setText(searchModel.getLastName());
        tv_individual_email.setText(et_search_individual.getText().toString());
        tv_individual_confirm_email.setText(et_search_individual.getText().toString());

    }
}
