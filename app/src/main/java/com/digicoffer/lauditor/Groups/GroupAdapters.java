package com.digicoffer.lauditor.Groups;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.digicoffer.lauditor.Groups.GroupModels.GroupItems;
import com.digicoffer.lauditor.Groups.GroupModels.GroupModel;
import com.digicoffer.lauditor.R;

import java.util.ArrayList;

public class GroupAdapters extends RecyclerView.Adapter<GroupAdapters.ViewHolder>{

    ArrayList<GroupModel> itemsArrayList;

    public GroupAdapters(ArrayList<GroupModel> itemsArrayList) {
        this.itemsArrayList = itemsArrayList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.select_team_members, parent, false);
        return new GroupAdapters.ViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull GroupAdapters.ViewHolder holder, int position) {
        final GroupModel groupModel = itemsArrayList.get(position);
        holder.cb_team_members.setChecked(itemsArrayList.get(position).isSelected());
        holder.cb_team_members.setTag(position);
        holder.cb_team_members.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer pos = (Integer) holder.cb_team_members.getTag();
                if (itemsArrayList.get(pos).isChecked()){
                    itemsArrayList.get(pos).setChecked(false);
                }else
                {
                    itemsArrayList.get(pos).setChecked(true);
                }
            }
        });
        holder.tv_tm_name.setText(groupModel.getName());
    }

    public ArrayList<GroupModel> getList_item() {
        return itemsArrayList;
    }
    @Override
    public int getItemCount() {
        return itemsArrayList.size();
    }

    public void selectOrDeselectAll(boolean isChecked) {
        for (int i = 0; i < itemsArrayList.size(); i++) {
            itemsArrayList.get(i).setChecked(isChecked);
        }
        notifyDataSetChanged();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        private CheckBox cb_team_members;
        private TextView tv_tm_name;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cb_team_members = itemView.findViewById(R.id.chk_selected);
            tv_tm_name = itemView.findViewById(R.id.tv_tm_name);
        }
    }
}
