package com.digicoffer.lauditor.Members;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.digicoffer.lauditor.Groups.Adapters.ViewGroupsAdpater;
import com.digicoffer.lauditor.Groups.GroupModels.GroupModel;
import com.digicoffer.lauditor.Groups.GroupModels.ViewGroupModel;
import com.digicoffer.lauditor.Groups.Groups;
import com.digicoffer.lauditor.R;

import org.minidns.record.A;

import java.util.ArrayList;

public class GroupsAdapter extends RecyclerView.Adapter<GroupsAdapter.ViewHolder> {
    ArrayList<ViewGroupModel> groupsList = new ArrayList<>();

    public GroupsAdapter(ArrayList<ViewGroupModel> groupsList) {
        this.groupsList = groupsList;
    }

    @NonNull
    @Override
    public GroupsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.select_team_members, parent, false);
        return new GroupsAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupsAdapter.ViewHolder holder, int position) {
                ViewGroupModel groupModel = groupsList.get(position);
        holder.cb_team_members.setChecked(groupsList.get(position).isChecked());
        holder.cb_team_members.setTag(position);

//            holder.cb_team_members.isChecked() = itemsArrayList.get(position).isChecked();
        holder.cb_team_members.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer pos = (Integer) holder.cb_team_members.getTag();
                if (groupsList.get(pos).isChecked()) {
                    groupsList.get(pos).setChecked(false);
//                        itemsArrayList.add(itemsArrayList.get(pos));
                } else {
                    groupsList.get(pos).setChecked(true);
//                        itemsArrayList.remove(itemsArrayList.get(pos));
                }

            }
        });
//            holder.cb_team_members.setChecked(true);
        holder.tv_tm_name.setText(groupModel.getName());

    }
    public ArrayList<ViewGroupModel> getList_item() {
        return groupsList;
    }
    @Override
    public int getItemCount() {
        return groupsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox cb_team_members;
        TextView tv_tm_name;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cb_team_members = itemView.findViewById(R.id.chk_selected);
            tv_tm_name = itemView.findViewById(R.id.tv_tm_name);
        }
    }
}
