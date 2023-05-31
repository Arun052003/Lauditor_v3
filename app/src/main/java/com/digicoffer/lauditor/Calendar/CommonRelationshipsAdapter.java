package com.digicoffer.lauditor.Calendar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.digicoffer.lauditor.Calendar.Models.TeamDo;
import com.digicoffer.lauditor.Matter.Adapters.GroupsAdapter;
import com.digicoffer.lauditor.Matter.Models.ClientsModel;
import com.digicoffer.lauditor.Matter.Models.GroupsModel;
import com.digicoffer.lauditor.Matter.Models.TeamModel;
import com.digicoffer.lauditor.Matter.Models.ViewMatterModel;
import com.digicoffer.lauditor.R;

import java.util.ArrayList;

public class CommonRelationshipsAdapter extends RecyclerView.Adapter<CommonRelationshipsAdapter.Viewholder> {
    ArrayList<TeamDo> sharedList = new ArrayList<>();
    ArrayList<TeamDo> list_item = new ArrayList<>();
    ArrayList<TeamDo> clientsList = new ArrayList<>();
    ArrayList<TeamDo> tmList = new ArrayList<>();
    ArrayList<TeamDo> groupsList = new ArrayList<>();

    String TAG = "Groups";


    public CommonRelationshipsAdapter( ArrayList<TeamDo> teamList) {
//        this.sharedList = sharedList;
//        this.list_item = sharedList;
//        this.clientsList = clientsList;
        this.tmList = teamList;
//        this.groupsList = groups_list;
//        this.TAG = Tag;
    }

    @NonNull
    @Override
    public CommonRelationshipsAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.shared_documents_by_us, parent, false);
        return new CommonRelationshipsAdapter.Viewholder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CommonRelationshipsAdapter.Viewholder holder, int position) {
//        if (TAG == "Groups") {
//            TeamDo groupsModel = sharedList.get(position);
//            holder.cb_documents.setChecked(sharedList.get(position).isChecked());
//            holder.cb_documents.setTag(position);
//            holder.tv_tm_name.setText(groupsModel.getName());
//            holder.cb_documents.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Integer pos = (Integer) holder.cb_documents.getTag();
//                    if (sharedList.get(pos).isChecked()) {
//                        sharedList.get(pos).setChecked(false);
//                    } else {
//                        sharedList.get(pos).setChecked(true);
//                    }
//                }
//            });
//        } else if (TAG == "Clients") {
//            TeamDo clientsModel = clientsList.get(position);
//            holder.cb_documents.setChecked(clientsList.get(position).isChecked());
//            holder.cb_documents.setTag(position);
//            holder.tv_tm_name.setText(clientsModel.getName());
//            holder.cb_documents.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Integer pos = (Integer) holder.cb_documents.getTag();
//                    if (clientsList.get(pos).isChecked()) {
//                        clientsList.get(pos).setChecked(false);
//                    } else {
//                        clientsList.get(pos).setChecked(true);
//                    }
//                }
//            });
//        } else if (TAG == "TM") {
            TeamDo teamModel = tmList.get(position);
            holder.cb_documents.setChecked(tmList.get(position).isChecked());
            holder.cb_documents.setTag(position);
            holder.tv_tm_name.setText(teamModel.getName());
            holder.cb_documents.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Integer pos = (Integer) holder.cb_documents.getTag();
                    if (tmList.get(pos).isChecked()) {
                        tmList.get(pos).setChecked(false);
                    } else {
                        tmList.get(pos).setChecked(true);
                    }
                }
            });
//        }else if(TAG == "UGM"){
//            TeamDo viewMatterModel = groupsList.get(position);
//            holder.cb_documents.setChecked(groupsList.get(position).isChecked());
//            holder.cb_documents.setTag(position);
//            holder.tv_tm_name.setText(viewMatterModel.getName());
//            holder.cb_documents.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Integer pos = (Integer) holder.cb_documents.getTag();
//                    if (groupsList.get(pos).isChecked()) {
//                        groupsList.get(pos).setChecked(false);
//                    } else {
//                        groupsList.get(pos).setChecked(true);
//                    }
//                }
//            });
//        }

    }

    public ArrayList<TeamDo> getClientsList_item() {
        return clientsList;
    }

    public ArrayList<TeamDo> getList_item() {
        return sharedList;
    }

    public ArrayList<TeamDo> getTmList() {
        return tmList;
    }

    public ArrayList<TeamDo> getGroupsList(){
        return groupsList;
    }

    @Override
    public int getItemCount() {
//        if (TAG == "Groups") {
//            return sharedList.size();
//        } else if (TAG == "Clients") {
//            return clientsList.size();
//        } else if(TAG == "UGM"){
//            return groupsList.size();
//        }else  {
            return tmList.size();
//        }
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
