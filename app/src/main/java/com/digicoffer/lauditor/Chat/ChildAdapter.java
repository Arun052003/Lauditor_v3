package com.digicoffer.lauditor.Chat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.digicoffer.lauditor.Chat.Model.ChildDO;
import com.digicoffer.lauditor.R;

import java.util.ArrayList;
import java.util.EventListener;

public class ChildAdapter extends RecyclerView.Adapter<ChildAdapter.MyViewHolder>{


    ArrayList<ChildDO> filteredList = new ArrayList<>();
    //    ChildAdapter.EventListener context;
    Context cContext;
    ChildDO childDo;
    EventListener context;
    String relationship_id;
    View view;

    public ChildAdapter(ArrayList<ChildDO> child_list, Context context, EventListener mcontext) {
        this.filteredList = child_list;
        this.cContext = context;
        this.context = mcontext;
    }
    public interface EventListener {
//        void Copy(ChildDo childDo);
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sub,parent,false);
        return new ChildAdapter.MyViewHolder(view);

    }
    @Override
    public void onBindViewHolder(@NonNull ChildAdapter.MyViewHolder holder, int position) {
        ChildDO childDO = filteredList.get(position);
        holder.tv_name.setText(childDO.getName());
    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name;
        LinearLayoutCompat ll_users;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_name);
            ll_users = itemView.findViewById(R.id.ll_clients);
        }
    }
}
