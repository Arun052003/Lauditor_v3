package com.digicoffer.lauditor.Chat;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.digicoffer.lauditor.Chat.Model.ChatDo;
import com.digicoffer.lauditor.Chat.Model.ClientRelationshipsDo;
import com.digicoffer.lauditor.R;
import com.digicoffer.lauditor.Webservice.AsyncTaskCompleteListener;
import com.digicoffer.lauditor.Webservice.HttpResultDo;
import com.digicoffer.lauditor.Webservice.WebServiceHelper;
import com.digicoffer.lauditor.common.AndroidUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder> implements Filterable, View.OnClickListener, AsyncTaskCompleteListener {
    ArrayList<ClientRelationshipsDo> list_item = new ArrayList<ClientRelationshipsDo>();
    ArrayList<ClientRelationshipsDo> filtered_list  = new ArrayList <ClientRelationshipsDo>();
    private ChatAdapter.EventListener context;
    private Context frag_context;
    ClientRelationshipsDo relationshipsDoRow;
    String User_Name = "";
    Activity activity;
    AlertDialog progress_dialog;


    public ChatAdapter(ArrayList<ClientRelationshipsDo> contactsList, EventListener mcontext, Context context, FragmentActivity activity) {
        this.list_item = contactsList;
        this.filtered_list = contactsList;
        this.context = mcontext;
        this.frag_context = context;
        this.activity = activity;

    }

    public interface EventListener {
        void view_users(JSONObject jsonObject,String UserName);
    }
    @Override
    public void onClick(View v) {

    }

    @Override
    public void onAsyncTaskComplete(HttpResultDo httpResult) {
        if (progress_dialog != null && progress_dialog.isShowing())
            AndroidUtils.dismiss_dialog(progress_dialog);
        if (httpResult.getResult() == WebServiceHelper.ServiceCallStatus.Success) {
            try {
                JSONObject result = new JSONObject(httpResult.getResponseContent());
                if (httpResult.getRequestType() == "USERS_CHAT_LIST") {
                    if (!result.getBoolean("error")) {
                        JSONObject jsonObj = result.getJSONObject("data");
                       if(relationshipsDoRow.getClientType().toUpperCase().equals("BUSINESS")||relationshipsDoRow.getClientType().toUpperCase().equals("PROFESSIONAL")){
////                        move_message_fragment(jsonObj);
                            User_Name = relationshipsDoRow.getName();
                            ViewUserList(jsonObj,User_Name);
                        }
                        else {
                            move_message_consumer_fragment(jsonObj);
                        }
                    }

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void move_message_consumer_fragment(JSONObject jsonObj) {
    }

    private void ViewUserList(JSONObject jsonObj, String user_name)throws JSONException {
        JSONArray user = jsonObj.getJSONArray("users");

    }


    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    filtered_list = list_item;

                } else {
                    ArrayList<ClientRelationshipsDo> filteredList = new ArrayList<>();
                    for (ClientRelationshipsDo row : list_item) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (AndroidUtils.isNull(row.getName()).toLowerCase().contains(charString.toLowerCase()) || AndroidUtils.isNull(row.getClientType()).toLowerCase().contains(charString.toLowerCase()) || AndroidUtils.isNull(row.getCreated()).toLowerCase().contains(charString.toLowerCase()) || AndroidUtils.isNull(row.getConsent()).toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    filtered_list = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filtered_list;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence cha, FilterResults filterResults) {
                filtered_list = (ArrayList<ClientRelationshipsDo>) filterResults.values;
                notifyDataSetChanged();
            }

        };
    }

    @NonNull
    @Override
    public ChatAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.client_list, parent, false);
        return new ChatAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatAdapter.MyViewHolder holder, int position) {
                ClientRelationshipsDo clientRelationshipsDo = filtered_list.get(position);
                holder.tv_name.setText(clientRelationshipsDo.getName());
                if (clientRelationshipsDo.getClientType().equalsIgnoreCase("Consumer")){
                    holder.plus_icon.setVisibility(View.GONE);
                }else{
                    holder.plus_icon.setVisibility(View.VISIBLE);
                }
        holder.plus_icon.setOnClickListener(v -> {
            if (clientRelationshipsDo.isExpanded()) {
                // If already expanded, remove the sub-items and update the button icon
                clientRelationshipsDo.setExpanded(false);
                holder.plus_icon.setImageResource(R.drawable.plus_icon);
                removeSubItems(holder.itemView, clientRelationshipsDo);
            } else {
                // If not expanded, add the sub-items and update the button icon
                clientRelationshipsDo.setExpanded(true);
                holder.plus_icon.setImageResource(R.drawable.minus_icon);
                addSubItems(holder.itemView, clientRelationshipsDo);
            }
        });
    }
    private void callChatUsersListWebservice(String id,String User_Name) {
        progress_dialog = AndroidUtils.get_progress(activity);
        JSONObject postData = new JSONObject();
        try {
            WebServiceHelper.callHttpWebService(this, frag_context, WebServiceHelper.RestMethodType.GET, "relationship/" + id + "/users/notify", "USERS_CHAT_LIST", postData.toString());
        } catch (Exception e) {
            if (progress_dialog != null && progress_dialog.isShowing())
                AndroidUtils.dismiss_dialog(progress_dialog);
        }
    }
    private void addSubItems(View parentView, ClientRelationshipsDo mainItem) {
        callChatUsersListWebservice(mainItem.getId(),User_Name);
        // Inflate sub-item views and add them to the main item layout
//        List<String> subItems = mainItem.getSubItems();

    }
    private void removeSubItems(View parentView, ClientRelationshipsDo mainItem) {
        // Remove sub-item views from the main item layout
        List<String> subItems = mainItem.getSubItems();
        if (subItems != null && subItems.size() > 0) {
            ((LinearLayout) parentView).removeViews(mainItem.getSubItems().size(), 2);
        }
    }
    @Override
    public int getItemCount() {
        return filtered_list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name;
        ImageView plus_icon;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_name);
            plus_icon  = itemView.findViewById(R.id.plus_icon);
        }
    }
}
