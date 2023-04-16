package com.digicoffer.lauditor.TimeSheets.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.digicoffer.lauditor.ClientRelationships.Model.RelationshipsModel;
import com.digicoffer.lauditor.Matter.Models.TeamModel;
import com.digicoffer.lauditor.R;
import com.digicoffer.lauditor.TimeSheets.Models.TMModel;
import com.digicoffer.lauditor.common.AndroidUtils;

import java.util.ArrayList;

public class TeamMembersTSAdapter extends RecyclerView.Adapter<TeamMembersTSAdapter.MyviewHolder> implements Filterable {
    ArrayList<TMModel> teamList = new ArrayList<>();
    ArrayList<TMModel> itemsList = new ArrayList<>();
    String status;
    public TeamMembersTSAdapter(ArrayList<TMModel> teamList,String sStatus) {
        this.teamList = teamList;
        this.itemsList = teamList;
        this.status = sStatus;
    }

    @NonNull
    @Override
    public TeamMembersTSAdapter.MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.weekly_timesheets,parent,false);
        return new TeamMembersTSAdapter.MyviewHolder(view);
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    teamList = itemsList;
                } else {
                    ArrayList<TMModel> filteredList = new ArrayList<>();
                    for (TMModel row : itemsList) {
//                            if (row.isChecked()){
//                                row.setChecked(false);
//                            }else
//                            {
//                                row.setChecked(true  );
//                            }
                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (AndroidUtils.isNull(row.getName()).toLowerCase().contains(charString.toLowerCase()) ) {
                            filteredList.add(row);
                        }
                    }
                    teamList = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.count = teamList.size();
                filterResults.values = teamList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                teamList = (ArrayList<TMModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(@NonNull TeamMembersTSAdapter.MyviewHolder holder, int position) {
        TMModel tmModel = teamList.get(position);
        holder.ll_total_hours.setVisibility(View.VISIBLE);
        holder.tv_matter_name.setText(tmModel.getName());
        holder.tv_matter_billable.setText("Billable");
        holder.tv_matter_task_name.setText("Non Billable");
        holder.tv_matter_hours.setText(tmModel.getTb());
        holder.tv_matter_minutes.setText(tmModel.getTnb());
        holder.tv_total_hours.setText(tmModel.getTotal());

    }

    @Override
    public int getItemCount() {
        return teamList.size();
    }

    public class MyviewHolder extends RecyclerView.ViewHolder {
        private TextView tv_matter_name, tv_matter_hours, tv_matter_minutes, tv_matter_billable, tv_matter_task_name, tv_total_hours;
        LinearLayout ll_total_hours;
        public MyviewHolder(@NonNull View itemView) {
            super(itemView);
            tv_matter_billable = itemView.findViewById(R.id.tv_matter_billable);
            tv_matter_hours = itemView.findViewById(R.id.tv_matter_hours);
            tv_matter_minutes = itemView.findViewById(R.id.tv_matter_minutes);
            tv_matter_name = itemView.findViewById(R.id.tv_matter_name);
            tv_matter_task_name =itemView.findViewById(R.id.tv_matter_task_name);
            tv_total_hours = itemView.findViewById(R.id.tv_total_hours);
            ll_total_hours = itemView.findViewById(R.id.ll_total_hours);
        }
    }
}
