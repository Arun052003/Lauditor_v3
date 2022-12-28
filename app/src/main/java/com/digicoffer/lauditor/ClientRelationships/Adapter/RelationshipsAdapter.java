package com.digicoffer.lauditor.ClientRelationships.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.digicoffer.lauditor.ClientRelationships.Model.RelationshipsModel;
import com.digicoffer.lauditor.Groups.Adapters.SearchAdapter;
import com.digicoffer.lauditor.R;

import org.minidns.record.A;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class RelationshipsAdapter extends RecyclerView.Adapter<RelationshipsAdapter.MyViewHolder> implements Filterable {
   ArrayList<RelationshipsModel> relationshipsList = new ArrayList<>();
   ArrayList<RelationshipsModel> itemsList = new ArrayList<>();

    public RelationshipsAdapter(ArrayList<RelationshipsModel> relationshipsList) {
        this.relationshipsList = relationshipsList;
        itemsList = relationshipsList;
    }

    @Override
    public Filter getFilter() {
        return null;
    }

    @NonNull
    @Override
    public RelationshipsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_relationships_design, parent, false);
        return new RelationshipsAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RelationshipsAdapter.MyViewHolder holder, int position) {
                    RelationshipsModel relationshipsModel = relationshipsList.get(position);
                    holder.tv_relationship_name.setText(relationshipsModel.getAdminName());
                    holder.tv_created_date.setText(relationshipsModel.getCreated());
//                    holder.tv
    }

    @Override
    public int getItemCount() {
        return relationshipsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_relationship_name,tv_created_date,tv_consumer,tv_more_details,tv_initiated;
        ImageView iv_initiated,iv_groups_relationships,iv_clock_relationships,iv_delete_relationships;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_relationship_name = itemView.findViewById(R.id.tv_relationship_name);
            tv_created_date = itemView.findViewById(R.id.tv_created_date);
            tv_consumer = itemView.findViewById(R.id.tv_consumer);
            tv_more_details = itemView.findViewById(R.id.tv_more_details);
            tv_initiated = itemView.findViewById(R.id.tv_initiated);
            iv_initiated = itemView.findViewById(R.id.iv_initiated);
            iv_groups_relationships = itemView.findViewById(R.id.iv_groups_relationships);
            iv_clock_relationships = itemView.findViewById(R.id.iv_clock_relationships);
            iv_delete_relationships = itemView.findViewById(R.id.iv_delete_relationships);
        }
    }
}
