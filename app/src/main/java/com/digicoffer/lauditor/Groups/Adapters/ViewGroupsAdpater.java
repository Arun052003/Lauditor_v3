package com.digicoffer.lauditor.Groups.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.digicoffer.lauditor.Groups.GroupModels.ActionModel;
import com.digicoffer.lauditor.Groups.GroupModels.ViewGroupModel;
import com.digicoffer.lauditor.Groups.ViewGroupsItemClickListener;
import com.digicoffer.lauditor.R;
import com.digicoffer.lauditor.Webservice.ItemClickListener;
import com.digicoffer.lauditor.common_adapters.CommonSpinnerAdapter;

import java.util.ArrayList;

public class ViewGroupsAdpater extends RecyclerView.Adapter<ViewGroupsAdpater.ViewHolder> {
    ArrayList<ViewGroupModel> itemsArrayList;
    ArrayList<ActionModel> actions_List = new ArrayList<ActionModel>();
    InterfaceListener eventListener;
    Context mcontext;
    ViewGroupsItemClickListener itemClickListener;
    String mTag = "";
    int selectedPosition = -1;
    private boolean isSpinnerInitial = true;

    int hidingItemIndex = 0;

    public ViewGroupsAdpater(ArrayList<ViewGroupModel> itemsArrayList, Context context, InterfaceListener eventListener, String Tag, ViewGroupsItemClickListener itemClickListener) {
        this.itemsArrayList = itemsArrayList;
        this.mcontext = context;
        this.eventListener = eventListener;
        this.mTag = Tag;
        this.itemClickListener = itemClickListener;
    }


    public interface InterfaceListener {
        void EditGroup(ViewGroupModel viewGroupModel);

        void DeleteGroup(ViewGroupModel viewGroupModel);

        void CGH(ViewGroupModel viewGroupModel, ArrayList<ViewGroupModel> itemsArrayList);
    }

    @NonNull
    @Override
    public ViewGroupsAdpater.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mTag == "VG") {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_groups, parent, false);
            return new ViewGroupsAdpater.ViewHolder(itemView);
        } else {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.assign_group_head, parent, false);
            return new ViewGroupsAdpater.ViewHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewGroupsAdpater.ViewHolder holder, int position) {

        ViewGroupModel viewGroupModel = itemsArrayList.get(position);
        if (mTag == "VG") {
            holder.tv_user_type.setText(viewGroupModel.getName());
            holder.tv_owner_name.setText(viewGroupModel.getOwner_name());
            holder.tv_date.setText(viewGroupModel.getCreated());
            holder.tv_description.setText(viewGroupModel.getDescription());
            actions_List.clear();
//        actions_List.add(new ActionModel("Add|Remove"));
            actions_List.add(new ActionModel("Choose Actions"));
            actions_List.add(new ActionModel("Edit Group"));
            actions_List.add(new ActionModel("Delete"));
            actions_List.add(new ActionModel("Update Group Members"));
            actions_List.add(new ActionModel("Change Group Head"));
            actions_List.add(new ActionModel("Group Activity Log"));
            final CommonSpinnerAdapter spinner_adapter = new CommonSpinnerAdapter((Activity) mcontext, actions_List);
            holder.sp_action.setAdapter(spinner_adapter);
            holder.sp_action.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                    String name = actions_List.get(adapterView.getSelectedItemPosition()).getName();
                    if (name == "Edit Group") {
                        eventListener.EditGroup(viewGroupModel);
                    } else if (name == "Delete") {
                        eventListener.DeleteGroup(viewGroupModel);
                    } else if (name == "Change Group Head") {
                        eventListener.CGH(viewGroupModel, itemsArrayList);
                    }


                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }
  else{
            holder.rb_group_head.setText(viewGroupModel.getGroup_name());
            holder.rb_group_head.setChecked(position == selectedPosition);
            holder.rb_group_head.setTag(viewGroupModel.getGroup_id());
//          holder.rb_group_head.
            holder.rb_group_head.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b) {
                        selectedPosition = holder.getAdapterPosition();
                        itemClickListener.onClick(viewGroupModel.getGroup_id());
//                        int copyOfLastCheckedPosition = selectedPosition;
//                        selectedPosition = holder.getAdapterPosition();
//                        notifyItemChanged(copyOfLastCheckedPosition);
//                        notifyItemChanged(selectedPosition);
//                            holder.rb_group_head.itemcl
//                        itemClickListener.onClick(
//                                holder.radioButton.getText()
//                                        .toString());
                    }
                }
            });
        }


    }


    @Override
    public int getItemCount() {
        return itemsArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_user_type, tv_owner_name, tv_date, tv_description;
        private Spinner sp_action;
        private RadioButton rb_group_head;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_user_type = itemView.findViewById(R.id.tv_group_name);
            tv_owner_name = itemView.findViewById(R.id.tv_group_head);
            tv_date = itemView.findViewById(R.id.tv_date);
            tv_description = itemView.findViewById(R.id.tv_description);
            sp_action = itemView.findViewById(R.id.sp_action);
            rb_group_head = itemView.findViewById(R.id.rb_selected);
        }
    }
}
