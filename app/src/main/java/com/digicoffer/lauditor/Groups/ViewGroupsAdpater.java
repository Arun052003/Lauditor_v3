package com.digicoffer.lauditor.Groups;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.digicoffer.lauditor.Groups.GroupModels.GroupModel;
import com.digicoffer.lauditor.Groups.GroupModels.ViewGroupModel;
import com.digicoffer.lauditor.R;

import java.util.ArrayList;

public class ViewGroupsAdpater extends  RecyclerView.Adapter<ViewGroupsAdpater.ViewHolder>{
    ArrayList<ViewGroupModel> itemsArrayList;

    public ViewGroupsAdpater(ArrayList<ViewGroupModel> itemsArrayList) {
        this.itemsArrayList = itemsArrayList;
    }

    @NonNull
    @Override
    public ViewGroupsAdpater.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_groups, parent, false);
        return new ViewGroupsAdpater.ViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewGroupsAdpater.ViewHolder holder, int position) {
            ViewGroupModel viewGroupModel = itemsArrayList.get(position);
            holder.tv_user_type.setText(viewGroupModel.getName());
            holder.tv_owner_name.setText(viewGroupModel.getOwner_name());
            holder.tv_date.setText(viewGroupModel.getCreated());
            holder.tv_description.setText(viewGroupModel.getDescription());
    }

    @Override
    public int getItemCount() {
        return itemsArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_user_type,tv_owner_name,tv_date,tv_description;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_user_type  = itemView.findViewById(R.id.tv_group_name);
            tv_owner_name = itemView.findViewById(R.id.tv_group_head);
            tv_date  = itemView.findViewById(R.id.tv_date);
            tv_description = itemView.findViewById(R.id.tv_description);
        }
    }
}
