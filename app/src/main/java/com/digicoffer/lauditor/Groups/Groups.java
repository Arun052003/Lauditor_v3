package com.digicoffer.lauditor.Groups;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.digicoffer.lauditor.Groups.GroupModels.GroupModel;
import com.digicoffer.lauditor.R;

import java.util.ArrayList;

public class Groups extends Fragment  {
   RecyclerView rv_select_team_members;
   TextView tv_create_group,tv_view_group,tv_add_tm,tv_practice_head;
   ArrayList<GroupModel> selectedTMArrayList = new ArrayList<GroupModel>();
    private CheckBox chk_select_all;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.groups, container, false);
        rv_select_team_members = v.findViewById(R.id.rv_selected_tm);
        tv_create_group =v.findViewById(R.id.tv_create_group);
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
            }
        });
        tv_add_tm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_practice_head.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.button_right_background));
                tv_add_tm.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.button_left_green_background));
            }
        });
        chk_select_all =  v.findViewById(R.id.chk_select_all);
        try {
            loadRecylcerview();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return v;
    }

    private void loadRecylcerview() {
        selectedTMArrayList.add(new GroupModel("Akash Kumar"));
        selectedTMArrayList.add(new GroupModel("Priyanka Chopra"));
        selectedTMArrayList.add(new GroupModel("Akhileswar"));
        selectedTMArrayList.add(new GroupModel("Dilshan"));
        selectedTMArrayList.add(new GroupModel("Sachin Tendulkar"));
        selectedTMArrayList.add(new GroupModel("Akash Kumar"));
        selectedTMArrayList.add(new GroupModel("Priyanka Chopra"));
        selectedTMArrayList.add(new GroupModel("Akhileswar"));
        selectedTMArrayList.add(new GroupModel("Dilshan"));
        selectedTMArrayList.add(new GroupModel("Sachin Tendulkar"));
        selectedTMArrayList.add(new GroupModel("Akash Kumar"));
        selectedTMArrayList.add(new GroupModel("Priyanka Chopra"));
        selectedTMArrayList.add(new GroupModel("Akhileswar"));
        selectedTMArrayList.add(new GroupModel("Dilshan"));
        selectedTMArrayList.add(new GroupModel("Sachin Tendulkar"));
        rv_select_team_members.setLayoutManager(new GridLayoutManager(getContext(), 1));
        final GroupAdapters adapter = new GroupAdapters(selectedTMArrayList);
        rv_select_team_members.setAdapter(adapter);
        rv_select_team_members.setHasFixedSize(true);
        rv_select_team_members.notify();
        chk_select_all.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                adapter.selectOrDeselectAll(isChecked);
            }
        });
    }
}
