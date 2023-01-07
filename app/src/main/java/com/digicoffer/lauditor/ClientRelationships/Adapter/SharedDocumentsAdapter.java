package com.digicoffer.lauditor.ClientRelationships.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.digicoffer.lauditor.ClientRelationships.Model.SharedDocumentsDo;
import com.digicoffer.lauditor.Groups.GroupModels.GroupModel;
import com.digicoffer.lauditor.R;
import com.digicoffer.lauditor.common.AndroidUtils;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class SharedDocumentsAdapter extends RecyclerView.Adapter<SharedDocumentsAdapter.ViewHolder> implements Filterable {
    ArrayList<SharedDocumentsDo> sharedList = new ArrayList<>();
    ArrayList<SharedDocumentsDo> list_item = new ArrayList<>();
    String Shared_tag = "";
    Context mContext;
    int selectedPosition = -1;
    SharedDocumentsAdapter.EventListener eventListener;
    public SharedDocumentsAdapter(ArrayList<SharedDocumentsDo> sharedList, String shared_tag, Context mcontext,SharedDocumentsAdapter.EventListener listner) {
        this.sharedList = sharedList;
        this.list_item = sharedList;
        this.Shared_tag = shared_tag;
        this.mContext = mcontext;
        this.eventListener = listner;
    }
    public ArrayList<SharedDocumentsDo> getList_item() {
        return sharedList;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    sharedList = list_item;
                } else {
                    ArrayList<SharedDocumentsDo> filteredList = new ArrayList<>();
                    for (SharedDocumentsDo row : list_item) {
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
                    sharedList = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.count = sharedList.size();
                filterResults.values = sharedList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                sharedList = (ArrayList<SharedDocumentsDo>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface EventListener {
        void CopyDocument(SharedDocumentsDo sharedDocumentsDo);

        void viewDocument(SharedDocumentsDo sharedDocumentsDo);
    }
    @NonNull
    @Override
    public SharedDocumentsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (Shared_tag == "withme") {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.shared_with_us, parent, false);
            return new SharedDocumentsAdapter.ViewHolder(itemView);
        } else if (Shared_tag == "byme"){
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.shared_documents_by_us, parent, false);
            return new SharedDocumentsAdapter.ViewHolder(itemView);

        }else{
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.client_firm_documents,parent,false);
            return new SharedDocumentsAdapter.ViewHolder(itemView);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(@NonNull SharedDocumentsAdapter.ViewHolder holder, int position) {
        SharedDocumentsDo sharedDocumentsDo = sharedList.get(position);
//        list_item = sharedList;
        if (Shared_tag == "byme") {
            holder.cb_documents.setChecked(sharedList.get(position).isChecked());
            holder.cb_documents.setTag(position);
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
        }
        else if(Shared_tag == "withme"){
                holder.tv_doc_name.setText(sharedDocumentsDo.getName());
                holder.iv_copy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        eventListener.CopyDocument(sharedDocumentsDo);
                    }
                });
                holder.iv_view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        eventListener.viewDocument(sharedDocumentsDo);
                    }
                });
//                holder
        }else{
            holder.cb_documents.setChecked(sharedList.get(position).isChecked());
            holder.cb_documents.setTag(position);
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
        }
//        if (position == position - 1){
//            holder.ll_dashed_line.setVisibility(View.GONE);
//        }

    }


    public void selectOrDeselectAll(boolean isChecked)
    {
        for(int i = 0; i<list_item.size(); i++)
        {
//            if (list_item.get(i).isIsenabled())
//            if (list_item.get(i).isIsenabled()) {
            list_item.get(i).setChecked(isChecked);
//            }else {
//                list_item.get(i).setChecked(false);
//            }
        }
        notifyDataSetChanged();
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
//            ll_dashed_line = itemView.findViewById(R.id.ll_dashed_line);
            iv_view = itemView.findViewById(R.id.iv_view);
            iv_copy = itemView.findViewById(R.id.iv_copy);
        }
    }
}
