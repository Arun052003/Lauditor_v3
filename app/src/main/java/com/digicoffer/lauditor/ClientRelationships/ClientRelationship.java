package com.digicoffer.lauditor.ClientRelationships;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.digicoffer.lauditor.R;
import com.digicoffer.lauditor.Webservice.AsyncTaskCompleteListener;
import com.digicoffer.lauditor.Webservice.HttpResultDo;

public class ClientRelationship extends Fragment implements AsyncTaskCompleteListener {

    private RadioGroup rg_add_relationships,rg_individual_entity;
    RadioButton rb_add_relationship, rb_view_relationships,rb_individual,rb_entity;
    LinearLayout ll_entity_name,ll_contact_person,ll_first_name,ll_last_name,ll_contatc_phone;


    @Override
    public void onClick(View view) {

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

    }
}
