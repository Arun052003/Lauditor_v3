package com.digicoffer.lauditor.Matter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.digicoffer.lauditor.R;
import com.google.android.material.textfield.TextInputEditText;

public class MatterInformation extends Fragment implements View.OnClickListener {
    TextInputEditText tv_search_client,tv_matter_num,tv_case_type,tv_matter_description,tv_dof,tv_court,tv_judge;
    TextView tv_high_priority,tv_medium_priority,tv_low_priority,tv_status_active,tv_status_pending;
    Button btn_add_advocate;
    AppCompatButton btn_cancel_save,btn_create;
    LinearLayout ll_add_advocate;
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
