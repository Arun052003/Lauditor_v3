package com.digicoffer.lauditor.Matter.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.digicoffer.lauditor.Groups.Adapters.ViewGroupsAdpater;
import com.digicoffer.lauditor.Groups.GroupModels.ActionModel;
import com.digicoffer.lauditor.Matter.Models.ViewMatterModel;
import com.digicoffer.lauditor.R;
import com.digicoffer.lauditor.common.AndroidUtils;
import com.digicoffer.lauditor.common_adapters.CommonSpinnerAdapter;

import org.json.JSONObject;
import org.pgpainless.key.selection.key.util.And;

import java.util.ArrayList;

public class ViewMatterAdapter extends RecyclerView.Adapter<ViewMatterAdapter.MyViewHolder> implements Filterable {
    ArrayList<ViewMatterModel> itemsArrayList;
    ArrayList<ViewMatterModel> list_item;
    ArrayList<ActionModel> actions_List = new ArrayList<ActionModel>();
    Context context;
    InterfaceListener eventListener;
    ViewMatterModel new_view_model;

    public ViewMatterAdapter(ArrayList<ViewMatterModel> itemsArrayList, Context context, InterfaceListener eventListener) {
        this.itemsArrayList = itemsArrayList;
        this.list_item = itemsArrayList;
        this.context = context;
        this.eventListener = eventListener;
    }

    @NonNull
    @Override
    public ViewMatterAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_matter_recyclerview_design, parent, false);
        return new ViewMatterAdapter.MyViewHolder(itemView);
    }

    public interface InterfaceListener {

        void View_Details(ViewMatterModel viewMatterModel);

        void DeleteMatter(ViewMatterModel viewMatterModel, ArrayList<ViewMatterModel> itemsArrayList);

        void Edit_Matter_Info(ViewMatterModel viewMatterModel, ArrayList<ViewMatterModel> itemsArrayList);

        void Update_Group(ViewMatterModel viewMatterModel);

        void Close_Matter(ViewMatterModel viewMatterModel);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewMatterAdapter.MyViewHolder holder, int position) {
        ViewMatterModel viewMatterModel = itemsArrayList.get(position);
//        new_view_model = viewMatterModel;
        itemsArrayList = list_item;

        try {
            JSONObject owner = viewMatterModel.getOwner();
            String owner_name = owner.getString("name");
            String owner_id = owner.getString("id");
            holder.tv_matter_title.setText(viewMatterModel.getTitle());
            holder.tv_case_number.setText(viewMatterModel.getCaseNumber());
            holder.tv_owner_name.setText(owner_name);
            holder.tv_date_of_filling.setText(viewMatterModel.getDate_of_filling());
            String upperString = viewMatterModel.getStatus().substring(0, 1).toUpperCase() + viewMatterModel.getStatus().substring(1).toLowerCase();

            if (viewMatterModel.getStatus().equals("Active")) {
                holder.tv_initiated.setText(upperString);
                holder.iv_initiated.setImageDrawable(context.getResources().getDrawable(R.drawable.green_circular));
//                notifyDataSetChanged();
            } else {
                holder.tv_initiated.setText(upperString);
                holder.iv_initiated.setImageDrawable(context.getResources().getDrawable(R.drawable.red_circular));
//                notifyDataSetChanged();
            }
            actions_List.clear();
            actions_List.add(new ActionModel("Choose Actions"));
            actions_List.add(new ActionModel("View Details"));
            actions_List.add(new ActionModel("Edit Matter Info"));
            actions_List.add(new ActionModel("Update Group(s)"));
            actions_List.add(new ActionModel("Close Matter"));
            actions_List.add(new ActionModel("Delete"));

            final CommonSpinnerAdapter spinner_adapter = new CommonSpinnerAdapter((Activity) context, actions_List);
            holder.sp_action.setAdapter(spinner_adapter);
            holder.sp_action.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    try {
                        String name = actions_List.get(adapterView.getSelectedItemPosition()).getName();

                        if (name == "View Details") {

//                            AndroidUtils.showToast(new_view_model);
                            eventListener.View_Details(viewMatterModel);

                        } else if (name == "Delete") {
                            eventListener.DeleteMatter(viewMatterModel, itemsArrayList);
                        } else if (name == "Edit Matter Info") {
                            eventListener.Edit_Matter_Info(viewMatterModel, itemsArrayList);
                        } else if (name == "Update Group(s)") {

                                eventListener.Update_Group(viewMatterModel);

                        } else if (name == "Close Matter") {

                                eventListener.Close_Matter(viewMatterModel);

                        }
                    }  catch (Exception e) {
                    e.printStackTrace();
                    AndroidUtils.showAlert(e.getMessage(), context);
                }
                }


                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        } catch (Exception e) {
            AndroidUtils.showToast(e.getMessage(), context);
            e.printStackTrace();
        }
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
                    ArrayList<ViewMatterModel> filteredList = new ArrayList<>();
                    for (ViewMatterModel row : list_item) {
//                            if (row.isChecked()){
//                                row.setChecked(false);
//                            }else
//                            {
//                                row.setChecked(true  );
//                            }
                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (AndroidUtils.isNull(row.getTitle()).toLowerCase().contains(charString.toLowerCase())) {
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
                itemsArrayList = (ArrayList<ViewMatterModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

//    @Override
//    public long getItemId(int position) {
//        return position;
//    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_matter_title, tv_case_number, tv_date_of_filling, tv_client_name, tv_owner_name, tv_initiated;
        ImageView iv_initiated;
        Spinner sp_action;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_matter_title = itemView.findViewById(R.id.tv_matter_title);
            tv_case_number = itemView.findViewById(R.id.tv_case_number);
            tv_date_of_filling = itemView.findViewById(R.id.tv_date_of_filling);
            tv_client_name = itemView.findViewById(R.id.tv_client_name);
            tv_owner_name = itemView.findViewById(R.id.tv_owner_name);
            tv_initiated = itemView.findViewById(R.id.tv_initiated);
            iv_initiated = itemView.findViewById(R.id.iv_initiated);
            sp_action = itemView.findViewById(R.id.sp_action);
        }
    }
}
