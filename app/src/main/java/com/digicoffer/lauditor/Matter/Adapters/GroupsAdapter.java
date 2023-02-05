package com.digicoffer.lauditor.Matter.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.digicoffer.lauditor.Matter.Models.GroupsModel;
import com.digicoffer.lauditor.R;

import java.util.ArrayList;

public class GroupsAdapter extends RecyclerView.Adapter<GroupsAdapter.Viewholder>  {
        ArrayList<GroupsModel> sharedList = new ArrayList<>();
        ArrayList<GroupsModel> list_item = new ArrayList<>();

public GroupsAdapter(ArrayList<GroupsModel> sharedList) {
        this.sharedList = sharedList;
        this.list_item = sharedList;
        }

@NonNull
@Override
public GroupsAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.shared_documents_by_us, parent, false);
        return new GroupsAdapter.Viewholder(itemView);
        }

@Override
public void onBindViewHolder(@NonNull GroupsAdapter.Viewholder holder, int position) {
        GroupsModel groupsModel = sharedList.get(position);
        holder.cb_documents.setChecked(sharedList.get(position).isChecked());
        holder.cb_documents.setTag(position);
        holder.tv_tm_name.setText(groupsModel.getGroup_name());
        holder.cb_documents.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View view) {
        Integer pos = (Integer) holder.cb_documents.getTag();
        if (sharedList.get(pos).isChecked()) {
        sharedList.get(pos).setChecked(false);
        } else {
        sharedList.get(pos).setChecked(true);
        }
        }
        });
        }
public ArrayList<GroupsModel> getList_item() {
        return sharedList;
        }
@Override
public int getItemCount() {
        return sharedList.size();
        }

@Override
public long getItemId(int position) {
        return position;
        }
@Override
public int getItemViewType(int position) {
        return position;
        }

public class Viewholder extends RecyclerView.ViewHolder {
    private TextView tv_tm_name;
    private CheckBox cb_documents;
    public Viewholder(@NonNull View itemView) {
        super(itemView);
        cb_documents = itemView.findViewById(R.id.chk_selected);
        tv_tm_name = itemView.findViewById(R.id.tv_tm_name);
    }
}
}
