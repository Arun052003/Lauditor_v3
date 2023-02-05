package com.digicoffer.lauditor.Matter;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.digicoffer.lauditor.Matter.Models.AdvocateModel;
import com.digicoffer.lauditor.R;
import com.google.android.material.textfield.TextInputEditText;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MatterInformation extends Fragment implements View.OnClickListener {
    TextInputEditText tv_search_client,tv_matter_num,tv_case_type,tv_matter_description,tv_dof,tv_court,tv_judge;
    TextView tv_high_priority,tv_medium_priority,tv_low_priority,tv_status_active,tv_status_pending;
    Button btn_add_advocate;
    ArrayList<AdvocateModel> advocates_list = new ArrayList<>();
    AppCompatButton btn_cancel_save,btn_create;
    LinearLayout ll_add_advocate;
    TextView tv_opponent_name;
    View v;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.matter_information, container, false);
        tv_search_client = view.findViewById(R.id.tv_search_client);
        tv_matter_num = view.findViewById(R.id.tv_matter_num);
        tv_case_type = view.findViewById(R.id.tv_case_type);
        tv_matter_description = view.findViewById(R.id.tv_matter_description);
        tv_dof = view.findViewById(R.id.tv_dof);
        tv_court = view.findViewById(R.id.tv_court);
        tv_judge = view.findViewById(R.id.tv_judge);
        tv_high_priority = view.findViewById(R.id.tv_high_priority);
        tv_high_priority.setOnClickListener(this);
        tv_medium_priority = view.findViewById(R.id.tv_medium_priority);
        tv_medium_priority.setOnClickListener(this);
        tv_low_priority = view.findViewById(R.id.tv_low_priority);
        tv_low_priority.setOnClickListener(this);
        tv_status_pending = view.findViewById(R.id.tv_status_pending);
        tv_status_pending.setOnClickListener(this);
        tv_status_active = view.findViewById(R.id.tv_status_active);
        tv_status_active.setOnClickListener(this);
        btn_add_advocate = view.findViewById(R.id.btn_add_advocate);
        btn_add_advocate.setOnClickListener(this);
        btn_cancel_save = view.findViewById(R.id.btn_cancel_save);
        btn_create = view.findViewById(R.id.btn_create);
        ll_add_advocate = view.findViewById(R.id.ll_add_advocate);


        return  view;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_high_priority:
                loadHighPriorityUI();
                break;
            case R.id.tv_medium_priority:
                loadMediumPriorityUI();
                break;
            case R.id.tv_low_priority:
                loadLowPriorityUI();
                break;
            case R.id.tv_status_pending:
                loadPendingUI();
                break;
            case R.id.tv_status_active:
              loadActiveUI();
                break;
            case R.id.btn_add_advocate:
                String TAG = "";
                int position = 0;

                loadAdvocateUI("","", "", TAG, position, tv_opponent_name,v);
                break;
        }
    }

    private void loadAdvocateUI(String advocate_name, String email, String number, String TAG, int position,final TextView tv_opponent_name,View view_advocate) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.add_opponent_advocate,null);
        TextInputEditText tv_advocate_name = view.findViewById(R.id.tv_advocate_name);
        TextInputEditText tv_advocate_email = view.findViewById(R.id.tv_advocate_email);
        TextInputEditText tv_advocate_phone = view.findViewById(R.id.tv_advocate_phone);
        AppCompatButton btn_cancel_tag = view.findViewById(R.id.btn_cancel_tag);
        AppCompatButton btn_save_tag = view.findViewById(R.id.btn_save_tag);


        final AlertDialog dialog = builder.create();
        btn_cancel_tag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        if (TAG=="Filled"){
            tv_advocate_name.setText(advocate_name);
            tv_advocate_email.setText(email);
            tv_advocate_phone.setText(number);
            btn_save_tag.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (tv_advocate_name.getText().toString().equals("")){
                        tv_advocate_name.setError("Name is required");
                        tv_advocate_name.requestFocus();
                    }else if(tv_advocate_email.getText().toString().equals("")){
                        tv_advocate_email.setError("Email is required");
                        tv_advocate_email.requestFocus();
                    }else if (!(tv_advocate_email.getText().toString().matches(Patterns.EMAIL_ADDRESS.toString()))){
                        tv_advocate_email.setError("Please enter a valid");
                        tv_advocate_email.requestFocus();
                    }else if (tv_advocate_phone.getText().toString().equals("")){
                        tv_advocate_phone.setError("Phone is required");
                        tv_advocate_phone.requestFocus();
                    }else if(!(tv_advocate_phone.getText().toString().matches(Patterns.PHONE.toString()))){
                        tv_advocate_phone.setError("Please enter a valid phone number");
                        tv_advocate_phone.requestFocus();
                    }else{
                        dialog.dismiss();
                        loadEditedData(tv_advocate_name.getText().toString(),tv_advocate_email.getText().toString(),tv_advocate_phone.getText().toString(),position,view_advocate,tv_opponent_name);


//                        loadOpponentsList(advocates_list);
                    }
                }
            });
        }else{
            btn_save_tag.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (tv_advocate_name.getText().toString().equals("")){
                        tv_advocate_name.setError("Name is required");
                        tv_advocate_name.requestFocus();
                    }else if(tv_advocate_email.getText().toString().equals("")){
                        tv_advocate_email.setError("Email is required");
                        tv_advocate_email.requestFocus();
                    }else if (!(tv_advocate_email.getText().toString().matches(Patterns.EMAIL_ADDRESS.toString()))){
                        tv_advocate_email.setError("Please enter a valid");
                        tv_advocate_email.requestFocus();
                    }else if (tv_advocate_phone.getText().toString().equals("")){
                        tv_advocate_phone.setError("Phone is required");
                        tv_advocate_phone.requestFocus();
                    }else if(!(tv_advocate_phone.getText().toString().matches(Patterns.PHONE.toString()))){
                        tv_advocate_phone.setError("Please enter a valid phone number");
                        tv_advocate_phone.requestFocus();
                    }else{
                        AdvocateModel advocateModel = new AdvocateModel();
                        advocateModel.setAdvocate_name(tv_advocate_name.getText().toString());
                        advocateModel.setEmail(tv_advocate_email.getText().toString());
                        advocateModel.setNumber(tv_advocate_phone.getText().toString());
                        advocates_list.add(advocateModel);
                        dialog.dismiss();
                        loadOpponentsList(advocates_list);
                    }
                }
            });
        }

        dialog.setCancelable(false);
        dialog.setView(view);
        dialog.show();

    }

    private void loadEditedData(String adv_name, String adv_email, String adv_phone, int position, View view_advocate, TextView tv_opponent_name) {
        AdvocateModel advocateModel = new AdvocateModel();
        advocateModel.setAdvocate_name(adv_name);
        advocateModel.setEmail(adv_email);
        advocateModel.setNumber(adv_phone);
        advocates_list.set(position,advocateModel);
        tv_opponent_name = view_advocate.findViewById(R.id.tv_opponent_name);
    }

    private void loadOpponentsList(ArrayList<AdvocateModel> advocates_list) {
        for (int i=0;i<advocates_list.size();i++){
            View view_opponents = LayoutInflater.from(getContext()).inflate(R.layout.edit_opponent_advocate, null);
            TextView tv_opponent_name = view_opponents.findViewById(R.id.tv_opponent_name);
            tv_opponent_name.setText(advocates_list.get(i).getAdvocate_name());
            ImageView iv_edit_opponent = view_opponents.findViewById(R.id.iv_edit_opponent);
            ImageView iv_remove_opponent  =view_opponents.findViewById(R.id.iv_remove_opponent);
            iv_remove_opponent.setTag(i);
            iv_remove_opponent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = 0;
                    if (v.getTag() instanceof Integer) {
                        position = (Integer) v.getTag();
                        v = ll_add_advocate.getChildAt(position);
                        ll_add_advocate.removeView(v);
                        AdvocateModel advocateModel = advocates_list.get(position);
                        advocateModel.setAdvocate_name("");
                        advocateModel.setEmail("");
                        advocateModel.setNumber("");
                        advocates_list.set(position, advocateModel);
                        advocates_list.remove(position);
//                        add_tags_listing();
//                                    ll_added_tags.removeAllViews();
                    }
                }
            });
            iv_edit_opponent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = 0;
                    if (v.getTag() instanceof Integer) {
                        v = ll_add_advocate.getChildAt(position);
                        AdvocateModel advocateModel = advocates_list.get(position);
                        String TAG = "Filled";
                        loadAdvocateUI(advocateModel.getAdvocate_name(),advocateModel.getEmail(),advocateModel.getNumber(),TAG,position,tv_opponent_name,v);
//                        edit_tags(documentsModel1.getTag_type(), documentsModel1.getTag_name(), position, view, tv_tag_document_name);
                    }
                }
            });
            ll_add_advocate.addView(view_opponents);
        }
    }

    private void loadActiveUI() {
        tv_status_active.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.button_left_green_round_background));
        tv_status_pending.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.button_right_round_background));

    }

    private void loadPendingUI() {
        tv_status_active.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.button_left_round_background));
        tv_status_pending.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.button_right_green_round_background));

    }

    private void loadLowPriorityUI() {
        tv_high_priority.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.button_left_round_background));
        tv_medium_priority.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.radiobutton_centre_background));
        tv_low_priority.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.button_right_green_round_background));

    }

    private void loadMediumPriorityUI() {
        tv_high_priority.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.button_left_round_background));
        tv_medium_priority.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.radiobutton_centre_green_background));
        tv_low_priority.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.button_right_round_background));
    }

    private void loadHighPriorityUI() {
        tv_high_priority.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.button_left_green_round_background));
        tv_medium_priority.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.radiobutton_centre_background));
        tv_low_priority.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.button_right_round_background));
    }

}
