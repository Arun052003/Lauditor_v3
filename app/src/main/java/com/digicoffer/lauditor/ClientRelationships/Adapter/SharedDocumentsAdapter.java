package com.digicoffer.lauditor.ClientRelationships.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.digicoffer.lauditor.ClientRelationships.Model.SharedDocumentsDo;
import com.digicoffer.lauditor.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class SharedDocumentsAdapter extends RecyclerView.Adapter<SharedDocumentsAdapter.ViewHolder> {
    ArrayList<SharedDocumentsDo> sharedList = new ArrayList<>();
    ArrayList<SharedDocumentsDo> list_item = new ArrayList<>();
    String Shared_tag = "";
    Context mContext;
    SharedDocumentsAdapter.EventListener eventListener;
    public SharedDocumentsAdapter(ArrayList<SharedDocumentsDo> sharedList, String shared_tag, Context mcontext,SharedDocumentsAdapter.EventListener listner) {
        this.sharedList = sharedList;
        this.list_item = list_item;
        this.Shared_tag = shared_tag;
        this.mContext = mcontext;
        this.eventListener = listner;
    }
    public interface EventListener {
        void CopyDocument(SharedDocumentsDo sharedDocumentsDo);
    }
    @NonNull
    @Override
    public SharedDocumentsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (Shared_tag == "byme") {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.shared_documents_by_us, parent, false);
            return new SharedDocumentsAdapter.ViewHolder(itemView);
        } else {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.shared_with_us, parent, false);
            return new SharedDocumentsAdapter.ViewHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull SharedDocumentsAdapter.ViewHolder holder, int position) {
        SharedDocumentsDo sharedDocumentsDo = sharedList.get(position);
        list_item = sharedList;
        if (Shared_tag == "byme") {
            holder.cb_documents.setChecked(sharedList.get(position).isChecked());
            holder.cb_documents.setTag(position);
//            if(groupModel.isIsenabled()==null)
            if (sharedList.get(position).isIsenabled()) {
                holder.cb_documents.setEnabled(true);
            } else {
                holder.cb_documents.setEnabled(false);
            }
            holder.tv_tm_name.setText(sharedDocumentsDo.getName());
            holder.cb_documents.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Integer pos = (Integer) holder.cb_documents.getTag();
                    if (sharedList.get(pos).isChecked()) {
                        sharedList.get(pos).setChecked(false);

//                        itemsArrayList.add(itemsArrayList.get(pos));
                    } else {
                        sharedList.get(pos).setChecked(true);
//                        itemsArrayList.remove(itemsArrayList.get(pos));
                    }


                }
            });
        } else {
                holder.tv_doc_name.setText(sharedDocumentsDo.getName());
                holder.iv_copy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        eventListener.CopyDocument(sharedDocumentsDo);
                    }
                });
//                holder
        }
//        if (position == position - 1){
//            holder.ll_dashed_line.setVisibility(View.GONE);
//        }

    }





    @Override
    public int getItemCount() {
        return sharedList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CheckBox cb_documents;
        private TextView tv_tm_name,tv_doc_name;
        private LinearLayout ll_dashed_line;
        private ImageButton iv_view,iv_copy;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cb_documents = itemView.findViewById(R.id.chk_selected);
            tv_tm_name = itemView.findViewById(R.id.tv_tm_name);
            tv_doc_name = itemView.findViewById(R.id.tv_doc_name);
            ll_dashed_line = itemView.findViewById(R.id.ll_dashed_line);
            iv_view = itemView.findViewById(R.id.iv_view);
            iv_copy = itemView.findViewById(R.id.iv_copy);
        }
    }
}
