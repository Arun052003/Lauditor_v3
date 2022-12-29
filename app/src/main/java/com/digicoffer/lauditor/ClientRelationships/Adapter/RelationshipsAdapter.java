package com.digicoffer.lauditor.ClientRelationships.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.digicoffer.lauditor.ClientRelationships.Model.RelationshipsModel;
import com.digicoffer.lauditor.Groups.GroupModels.ViewGroupModel;
import com.digicoffer.lauditor.Members.GroupsAdapter;
import com.digicoffer.lauditor.R;
import com.digicoffer.lauditor.Webservice.AsyncTaskCompleteListener;
import com.digicoffer.lauditor.Webservice.HttpResultDo;
import com.digicoffer.lauditor.Webservice.WebServiceHelper;
import com.digicoffer.lauditor.common.AndroidUtils;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

public class RelationshipsAdapter extends RecyclerView.Adapter<RelationshipsAdapter.MyViewHolder> implements Filterable, AsyncTaskCompleteListener {
    ArrayList<RelationshipsModel> relationshipsList = new ArrayList<>();
    ArrayList<RelationshipsModel> itemsList = new ArrayList<>();
    int position = 0;
    Context mcontext;
    ArrayList<ViewGroupModel> updatedMembersList = new ArrayList<>();
    RelationshipsAdapter.MyViewHolder mholder;
    ArrayList<ViewGroupModel> groupsList = new ArrayList<>();
    AlertDialog ad_dialog;
    ViewGroup mParent;
    FragmentActivity mActivity;
    public RelationshipsAdapter(ArrayList<RelationshipsModel> relationshipsList, Context context, FragmentActivity activity) {
        this.relationshipsList = relationshipsList;
        this.itemsList = relationshipsList;
        this.mcontext = context;
        this.mActivity = activity;
    }

    @Override
    public Filter getFilter() {
        return null;
    }

    @NonNull
    @Override
    public RelationshipsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mParent = parent;
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_relationships_design, parent, false);
        return new RelationshipsAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RelationshipsAdapter.MyViewHolder holder, int position) {
//        updatedMembersList.clear();
        RelationshipsModel relationshipsModel = relationshipsList.get(position);
        position = position;

        Log.i("Tag", "Relationship:" + relationshipsModel.getAdminName());
        holder.tv_relationship_name.setText(relationshipsModel.getAdminName());
        holder.tv_created_date.setText("Created " + relationshipsModel.getCreated());
        holder.tv_consumer.setText(relationshipsModel.getClientType());
//                    if (relationshipsModel.isAccepted()){
//                        holder.tv_initiated.setText("Accepted");
//                    }else{
//                        holder.tv_initiated.setText("Not Accepted");
//                    }
        holder.iv_groups_relationships.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    groupsList.clear();
                    updatedMembersList.clear();
                    callGroupsWebservice(holder,relationshipsModel);
//                    holder.tv_relationship_name.setText("Sai");
//                    holder.cv_documents.setVisibility(View.VISIBLE);
//                    holder.ll_documents.setVisibility(View.VISIBLE);
                }
                catch (Exception e) {
                    AndroidUtils.showToast(e.getMessage(), view.getContext());
                    e.printStackTrace();
                }
            }
        });
//        holder.iv_groups_relationships.setOnClickListener((View.OnClickListener) this);
        String text = "More Details";
        SpannableString ss = new SpannableString(text);
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {

            }
        };
        ss.setSpan(clickableSpan, 0, 12, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        holder.tv_more_details.setText(ss);
        holder.tv_more_details.setMovementMethod(LinkMovementMethod.getInstance());
//                    holder.tv
    }

    private void callGroupsWebservice(MyViewHolder holder, RelationshipsModel relationshipsModel) {
        try{
            for (int j=0;j<relationshipsModel.getGroups().length();j++) {
                ViewGroupModel viewGroupModel = new ViewGroupModel();
                JSONObject jsonObject = relationshipsModel.getGroups().getJSONObject(j);
                viewGroupModel.setGroup_id(jsonObject.getString("id"));
                viewGroupModel.setGroup_name(jsonObject.getString("name"));
                updatedMembersList.add(viewGroupModel);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mholder = holder;
        JSONObject postdata = new JSONObject();
        WebServiceHelper.callHttpWebService(this, mcontext, WebServiceHelper.RestMethodType.GET, "v3/groups", "Get Groups", postdata.toString());


    }

    @Override
    public int getItemCount() {
        return relationshipsList.size();
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onAsyncTaskComplete(HttpResultDo httpResult) {
//        if (progress_dialog != null && progress_dialog.isShowing())
//            AndroidUtils.dismiss_dialog(progress_dialog);
        if (httpResult.getResult() == WebServiceHelper.ServiceCallStatus.Success) ;
        try {
            JSONObject result = new JSONObject(httpResult.getResponseContent());
            if (httpResult.getRequestType().equals("Get Groups")) {
                JSONArray data = result.getJSONArray("data");
                loadViewGroups(data);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void loadViewGroups(JSONArray data) throws JSONException {

        ViewGroupModel viewGroupModel;
        groupsList.clear();
        for (int i = 0; i < data.length(); i++) {
            JSONObject jsonObject = data.getJSONObject(i);
            viewGroupModel = new ViewGroupModel();
            viewGroupModel.setId(jsonObject.getString("id"));
            String date = jsonObject.getString("created");
            Date date_new = AndroidUtils.stringToDateTimeDefault(date, "yyyy-MM-dd'T'HH:mm:ss.SSS");
            String created = AndroidUtils.getDateToString(date_new, "MMM dd YYYY");
            viewGroupModel.setCreated(created);
            JSONArray members = jsonObject.getJSONArray("members");
            viewGroupModel.setMembers(members);
            viewGroupModel.setDescription(jsonObject.getString("description"));
            viewGroupModel.setName(jsonObject.getString("name"));
            JSONObject group_head = jsonObject.getJSONObject("groupHead");
            viewGroupModel.setGroup_head_id(group_head.getString("id"));
            viewGroupModel.setGroup_head_name(group_head.getString("name"));
            viewGroupModel.setOwner_name(group_head.getString("name"));
            for (int j = 0; j < data.length(); j++) {
                for (int k = 0; k < updatedMembersList.size(); k++) {
                    if (viewGroupModel.getId().matches(updatedMembersList.get(k).getGroup_id())) {
                        viewGroupModel.setChecked(true);
                    }
                }
            }
            groupsList.add(viewGroupModel);
        }
        Log.i("ArrayList", "info" + updatedMembersList.size());
        String mtag = "VG";
        loadGroupsRecylerview();

    }

    private void loadGroupsRecylerview() {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mcontext);
        LayoutInflater inflater = mActivity.getLayoutInflater();
        View view = inflater.inflate(R.layout.groups_recylerview_popup, null);
        RecyclerView rv_relationship_groups;
        TextInputEditText et_search_relationships;
        AppCompatButton btn_send_request, btn_relationships_cancel;
        rv_relationship_groups = view.findViewById(R.id.rv_relationship_groups);
        et_search_relationships = view.findViewById(R.id.et_search_relationships);
        btn_send_request = view.findViewById(R.id.btn_send_request);
        btn_relationships_cancel = view.findViewById(R.id.btn_relationships_cancel);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mcontext, LinearLayoutManager.VERTICAL, false);
        rv_relationship_groups.setLayoutManager(layoutManager);
        rv_relationship_groups.setHasFixedSize(true);
        GroupsAdapter groupsAdapter = new GroupsAdapter(groupsList);
        rv_relationship_groups.setAdapter(groupsAdapter);
        et_search_relationships.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                groupsAdapter.getFilter().filter(et_search_relationships.getText().toString());
            }

        });
        final AlertDialog dialog = dialogBuilder.create();
      ad_dialog = dialog;
        btn_relationships_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                et_search_relationships.setText("");
                groupsList.clear();
                updatedMembersList.clear();
                dialog.dismiss();
            }
        });
        dialog.setView(view);
        dialog.show();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_relationship_name, tv_created_date, tv_consumer, tv_more_details, tv_initiated;
        ImageView iv_initiated, iv_groups_relationships, iv_clock_relationships, iv_delete_relationships;
        RadioGroup rb_profile, rb_shared_status, rb_shared_type, rb_document_type;
//        CardView cv_documents;



        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_relationship_name = itemView.findViewById(R.id.tv_relationship_name);
            tv_created_date = itemView.findViewById(R.id.tv_created_date);
            tv_consumer = itemView.findViewById(R.id.tv_consumer);
//            cv_documents = itemView.findViewById(R.id.cv_documents);
            tv_more_details = itemView.findViewById(R.id.tv_more_details);
            tv_initiated = itemView.findViewById(R.id.tv_initiated);
            iv_initiated = itemView.findViewById(R.id.iv_initiated);
            iv_groups_relationships = itemView.findViewById(R.id.iv_groups_relationships);
            iv_clock_relationships = itemView.findViewById(R.id.iv_clock_relationships);
            iv_delete_relationships = itemView.findViewById(R.id.iv_delete_relationships);
            rb_profile = itemView.findViewById(R.id.profile);
            rb_shared_status = itemView.findViewById(R.id.shared_status);
            rb_shared_type = itemView.findViewById(R.id.shared_type);
            rb_document_type = itemView.findViewById(R.id.document_type);



        }
    }
}
