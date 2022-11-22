package com.digicoffer.lauditor.Groups.Adapters;

import android.app.Activity;
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

import com.digicoffer.lauditor.Groups.GroupModels.ActionModel;
import com.digicoffer.lauditor.Groups.GroupModels.ViewGroupModel;
import com.digicoffer.lauditor.R;
import com.digicoffer.lauditor.common_adapters.CommonSpinnerAdapter;

import java.util.ArrayList;
import java.util.EventListener;

public class ViewGroupsAdpater extends  RecyclerView.Adapter<ViewGroupsAdpater.ViewHolder>{
    ArrayList<ViewGroupModel> itemsArrayList;
    ArrayList<ActionModel> actions_List = new ArrayList<ActionModel>();
    InterfaceListener eventListener;
    Context mcontext;
    int hidingItemIndex = 0;
    public ViewGroupsAdpater(ArrayList<ViewGroupModel> itemsArrayList,Context context,InterfaceListener eventListener) {
        this.itemsArrayList = itemsArrayList;
        this.mcontext = context;
        this.eventListener = eventListener;
    }


    public interface InterfaceListener {
        void EditGroup(ViewGroupModel viewGroupModel);

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
        actions_List.add(new ActionModel("Add|Remove"));
        actions_List.add(new ActionModel("Edit Group"));
        actions_List.add(new ActionModel("Update Group Members"));
        actions_List.add(new ActionModel("Change Group Head"));
        actions_List.add(new ActionModel("Group Activity Log"));
        final CommonSpinnerAdapter spinner_adapter = new CommonSpinnerAdapter((Activity) mcontext, actions_List);
        holder.sp_action.setAdapter(spinner_adapter);
        holder.sp_action.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
               String name = actions_List.get(holder.sp_action.getSelectedItemPosition()).getName();
               if (name.equals("Edit Group")){
                   eventListener.EditGroup(viewGroupModel);
               }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

//        holder.sp_action.setOnItemSelectedListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                int selected_position_name = holder.sp_action.getSelectedItemPosition();
//                if (selected_position_name==1){
//                   eventListener.EditGroup(viewGroupModel);
//                }
//            }
//        });

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
