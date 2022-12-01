package com.digicoffer.lauditor.Members;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.digicoffer.lauditor.R;

import java.util.ArrayList;

public class MembersAdapter extends RecyclerView.Adapter<MembersAdapter.ViewHolder> {
    ArrayList<MembersModel> members_list = new ArrayList<>();

    public MembersAdapter(ArrayList<MembersModel> members_list) {
        this.members_list = members_list;
    }

    @NonNull
    @Override
    public MembersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_members,parent,false);
        return new MembersAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MembersAdapter.ViewHolder holder, int position) {
            MembersModel membersModel = members_list.get(position);
            holder.tv_members_name.setText(membersModel.getName());
            holder.tv_litigation.setText(membersModel.getDesignation());
            holder.tv_currency.setText(membersModel.getDefaultRate());
            holder.tv_currency_type.setText(membersModel.getCurrency());
            holder.tv_email.setText(membersModel.getEmail());

    }

    @Override
    public int getItemCount() {
        return members_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_members_name,tv_member_type,tv_litigation,tv_email,tv_currency,tv_currency_type;
        Spinner sp_action;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_members_name = itemView.findViewById(R.id.tv_member_name);
            tv_member_type = itemView.findViewById(R.id.tv_member_type);
            tv_litigation = itemView.findViewById(R.id.tv_litigation);
            tv_currency_type = itemView.findViewById(R.id.tv_currency_type);
            tv_currency = itemView.findViewById(R.id.tv_currency);
            tv_email = itemView.findViewById(R.id.tv_email_id);
            sp_action = itemView.findViewById(R.id.sp_action);
        }
    }
}
