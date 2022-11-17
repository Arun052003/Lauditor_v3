package com.digicoffer.lauditor.Groups.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.digicoffer.lauditor.Groups.GroupModels.GroupModel;
import com.digicoffer.lauditor.R;
import com.digicoffer.lauditor.common.AndroidUtils;

import java.util.ArrayList;

public class GroupAdapters extends RecyclerView.Adapter<GroupAdapters.ViewHolder> implements Filterable {

    ArrayList<GroupModel> itemsArrayList;
    ArrayList<GroupModel> list_item;
    public GroupAdapters(ArrayList<GroupModel> itemsArrayList) {
        this.itemsArrayList = itemsArrayList;
        this.list_item = itemsArrayList;
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
        itemsArrayList = list_item;
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
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    itemsArrayList = list_item;
                } else {
                    ArrayList<GroupModel> filteredList = new ArrayList<>();
                    for (GroupModel row : list_item) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (AndroidUtils.isNull(row.getName()).toLowerCase().contains(charString.toLowerCase()) ) {
                            filteredList.add(row);
                        }

                    }

                    itemsArrayList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.count = itemsArrayList.size();
                filterResults.values = itemsArrayList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                itemsArrayList = (ArrayList<GroupModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public void selectOrDeselectAll(boolean isChecked) {
        for (int i = 0; i < list_item.size(); i++) {
//            if (itemsArrayList.get(i).isChecked()){
            if (isChecked){
                list_item.get(i).setSelected(true);

            }else{
                list_item.get(i).setSelected(false);
            }
//            else {
//                itemsArrayList.get(i).setChecked(true);
//            }
//            if (itemsArrayList.get(i).isSelected()){
//                itemsArrayList.get(i).setChecked(false);
//            }
//            else {
//                itemsArrayList.get(i).setChecked(true);
//            }
//            list_item.get(i).setChecked(isChecked);
//            itemsArrayList.get(i).setChecked(isChecked);
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
