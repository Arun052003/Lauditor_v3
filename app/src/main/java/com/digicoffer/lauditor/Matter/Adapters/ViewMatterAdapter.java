package com.digicoffer.lauditor.Matter.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.digicoffer.lauditor.Matter.Models.ViewMatterModel;
import com.digicoffer.lauditor.R;
import com.digicoffer.lauditor.common.AndroidUtils;

import org.json.JSONObject;

import java.util.ArrayList;

public class ViewMatterAdapter extends RecyclerView.Adapter<ViewMatterAdapter.MyViewHolder>  {
    ArrayList<ViewMatterModel> itemsArrayList;
    ArrayList<ViewMatterModel> list_item;
    Context context;
    @NonNull
    @Override
    public ViewMatterAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_matter_recyclerview_design, parent, false);
        return new ViewMatterAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewMatterAdapter.MyViewHolder holder, int position) {
            ViewMatterModel viewMatterModel = itemsArrayList.get(position);

        try {
            JSONObject owner = viewMatterModel.getOwner();
            String owner_name = owner.getString("name");
            String owner_id = owner.getString("id");
            holder.tv_matter_title.setText(viewMatterModel.getTitle());
            holder.tv_case_number.setText(viewMatterModel.getCaseNumber());
            holder.tv_owner_name.setText(owner_name);
            holder.tv_date_of_filling.setText(viewMatterModel.getDate_of_filling());

        } catch (Exception e) {
            AndroidUtils.showToast(e.getMessage(),context);
            e.printStackTrace();
        }
    }

    public ViewMatterAdapter(ArrayList<ViewMatterModel> itemsArrayList, Context context) {
        this.itemsArrayList = itemsArrayList;
        this.list_item = itemsArrayList;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return itemsArrayList.size();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_matter_title,tv_case_number,tv_date_of_filling,tv_client_name,tv_owner_name,tv_initiated;
        ImageView iv_initiated;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_matter_title = itemView.findViewById(R.id.tv_matter_title);
            tv_case_number = itemView.findViewById(R.id.tv_case_number);
            tv_date_of_filling = itemView.findViewById(R.id.tv_date_of_filling);
            tv_client_name = itemView.findViewById(R.id.tv_client_name);
            tv_owner_name = itemView.findViewById(R.id.tv_owner_name);
            tv_initiated = itemView.findViewById(R.id.tv_initiated);
            iv_initiated = itemView.findViewById(R.id.iv_initiated);

        }
    }
}
