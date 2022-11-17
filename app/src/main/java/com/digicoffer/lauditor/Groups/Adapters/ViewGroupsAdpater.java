package com.digicoffer.lauditor.Groups.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.digicoffer.lauditor.Groups.GroupModels.ViewGroupModel;
import com.digicoffer.lauditor.R;

import java.util.ArrayList;

public class ViewGroupsAdpater extends  RecyclerView.Adapter<ViewGroupsAdpater.ViewHolder>{
    ArrayList<ViewGroupModel> itemsArrayList;
    ArrayList<String> actions_List = new ArrayList<String>();

    Context mcontext;
    int hidingItemIndex = 0;
    public ViewGroupsAdpater(ArrayList<ViewGroupModel> itemsArrayList,Context context) {
        this.itemsArrayList = itemsArrayList;
        this.mcontext = context;
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
            actions_List.clear();
            actions_List.add("");
        actions_List.add("Add|Remove");
        actions_List.add("Edit Group");
        actions_List.add("Update Group Members");
        actions_List.add("Change Group Head");
        actions_List.add("Group Activity Log");
        final int listsize = actions_List.size() - 1;
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(mcontext,android.R.layout.simple_spinner_item, actions_List) {
            @Override
            public int getCount() {
                return(listsize); // Truncate the list
            }
        };

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.sp_action.setAdapter(dataAdapter);
        holder.sp_action.setSelection(listsize);

    }

    @Override
    public int getItemCount() {
        return itemsArrayList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_user_type,tv_owner_name,tv_date,tv_description;
        private Spinner sp_action;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_user_type  = itemView.findViewById(R.id.tv_group_name);
            tv_owner_name = itemView.findViewById(R.id.tv_group_head);
            tv_date  = itemView.findViewById(R.id.tv_date);
            tv_description = itemView.findViewById(R.id.tv_description);
            sp_action = itemView.findViewById(R.id.sp_action);
        }
    }
}
