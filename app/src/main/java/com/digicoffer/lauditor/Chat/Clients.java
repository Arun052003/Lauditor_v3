package com.digicoffer.lauditor.Chat;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.digicoffer.lauditor.Chat.Model.ChatDo;
import com.digicoffer.lauditor.Chat.Model.ClientRelationshipsDo;
import com.digicoffer.lauditor.R;
import com.digicoffer.lauditor.Webservice.AsyncTaskCompleteListener;
import com.digicoffer.lauditor.Webservice.HttpResultDo;
import com.digicoffer.lauditor.Webservice.WebServiceHelper;
import com.digicoffer.lauditor.common.AndroidUtils;
import com.google.android.material.textfield.TextInputEditText;
import com.tuyenmonkey.mkloader.model.Line;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Clients extends Fragment implements AsyncTaskCompleteListener,ChatAdapter.EventListener  {
    AlertDialog progress_dialog;
    RecyclerView rv_Clientrelationships;
    TextInputEditText et_Search;
    ArrayList<ClientRelationshipsDo> Clientlist = new ArrayList<ClientRelationshipsDo>();
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.client, container, false);
        rv_Clientrelationships = view.findViewById(R.id.rv_clientrelationships);
        et_Search = view.findViewById(R.id.et_Search);
        callWebservice();
   return view;
    }

    private void callWebservice() {
        progress_dialog = AndroidUtils.get_progress(getActivity());
        JSONObject postData = new JSONObject();
        try {
            WebServiceHelper.callHttpWebService(this, getContext(), WebServiceHelper.RestMethodType.GET, "v2/relationship", "CLIENT_RELATIONSHIP", postData.toString());
        } catch (Exception e) {
            if (progress_dialog != null && progress_dialog.isShowing())
                AndroidUtils.dismiss_dialog(progress_dialog);
        }
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onAsyncTaskComplete(HttpResultDo httpResult) {
        if (progress_dialog != null && progress_dialog.isShowing())
            AndroidUtils.dismiss_dialog(progress_dialog);
        if (httpResult.getResult() == WebServiceHelper.ServiceCallStatus.Success) {
            try {
                JSONObject result = new JSONObject(httpResult.getResponseContent());
                if (httpResult.getRequestType() == "CLIENT_RELATIONSHIP") {
                    if (!result.getBoolean("error")) {
                        JSONObject jsonObject = result.getJSONObject("data");
                        JSONArray jsonArray = jsonObject.getJSONArray("relationships");
                        et_Search.setText("");
                        loadClientRelationshipsData(jsonArray);
                    }
                    else {
                        AndroidUtils.showValidationALert("Alert", String.valueOf(result.get("msg")), getContext());
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    private void loadClientRelationshipsData(JSONArray jsonArray) {
        try {

            Clientlist.clear();
            for (int i=0;i<jsonArray.length();i++){
                ClientRelationshipsDo clientRelationshipsDo = new ClientRelationshipsDo();
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                clientRelationshipsDo.setAdminName(jsonObject.getString("adminName"));
                clientRelationshipsDo.setCanAccept(jsonObject.getBoolean("canAccept"));
                clientRelationshipsDo.setClientType(jsonObject.getString("clientType"));
                clientRelationshipsDo.setConsent(jsonObject.getString("consent"));
                clientRelationshipsDo.setCreated(jsonObject.getString("created"));
                clientRelationshipsDo.setGroups(jsonObject.getJSONArray("groups"));
                clientRelationshipsDo.setGuid(jsonObject.getString("guid"));
                clientRelationshipsDo.setId(jsonObject.getString("id"));
                clientRelationshipsDo.setAccepted(jsonObject.getBoolean("isAccepted"));
                clientRelationshipsDo.setClient(jsonObject.getBoolean("isClient"));
                clientRelationshipsDo.setEditable(jsonObject.getBoolean("isEditable"));
                clientRelationshipsDo.setMatterList(jsonObject.getJSONArray("matterList"));
                clientRelationshipsDo.setName(jsonObject.getString("name"));
//                clientRelationshipsDo.setExpanded(false);
                if (clientRelationshipsDo.isAccepted()){
                    Clientlist.add(clientRelationshipsDo);
                }

            }


            loadRecycleView();
        } catch (Exception e) {

        }
    }
    private void loadRecycleView() {
        rv_Clientrelationships.setLayoutManager(new GridLayoutManager(getContext(), 1));
        final ChatAdapter adapter = new ChatAdapter(Clientlist, this,getContext(),getActivity());
        Log.d("Client_list", String.valueOf(Clientlist.size()));
        rv_Clientrelationships.setAdapter(adapter);
        et_Search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                adapter.getFilter().filter(et_Search.getText().toString());
            }

        });

    }

    @Override
    public void view_users(JSONObject jsonObj, String UserName, ChatAdapter.MyViewHolder holder) throws JSONException {
        Log.d("JSONOBJECT",jsonObj.toString());
        final String jid = jsonObj.getString("uid");
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getContext());
        String currentJID = pref
                .getString("xmpp_jid", null);
        JSONArray user = jsonObj.getJSONArray("users");
        Log.d("JSONOBJECT", String.valueOf(user.length()));
//        int clickedPosition = new_holder.getAdapterPosition();
//        if (clickedPosition >= 0 && clickedPosition < list_item.size()) {
        // Get the clicked item's data
//            ClientRelationshipsDo clickedItem = list_item.get(clickedPosition);
        for (int i = 0; i < user.length(); i++) {
            final JSONObject data = user.getJSONObject(i);
            String id = data.getString("id");
            View layout = LayoutInflater.from(getContext()).inflate(R.layout.item_sub, null);
          TextView  tv_name_users = (TextView) layout.findViewById(R.id.tv_name);
           LinearLayoutCompat ll_clients = (LinearLayoutCompat) layout.findViewById(R.id.ll_clients);
            ll_clients.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
//                        move_message_fragment(data.getString("id"), data.getString("name"), jid);
                    }
                    catch (Exception e) {
                        AndroidUtils.logMsg(e.getMessage());
                    }
                }
            });
//            new ChatHistoryTask(id, jid,tv_name_users).execute("");
//            TextView tv_username = (TextView) layout.findViewById(R.id.tv_userName);
            tv_name_users.setText(data.getString("name"));
//            Log.d("MHolder",new_holder.toString());
            holder.ll_users.addView(layout); // +1 to add it below the clicked item

            // Increment clickedPosition by 1 to account for the newly added view
//                clickedPosition++;
        }
//        }
        Log.d("MHolder", String.valueOf(holder.ll_users.getChildCount()));

    }
}
