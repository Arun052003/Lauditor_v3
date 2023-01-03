package com.digicoffer.lauditor.ClientRelationships.Adapter;

import android.annotation.SuppressLint;
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
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.digicoffer.lauditor.ClientRelationships.Model.ProfileDo;
import com.digicoffer.lauditor.ClientRelationships.Model.RelationshipsModel;
import com.digicoffer.lauditor.ClientRelationships.Model.SharedDocumentsDo;
import com.digicoffer.lauditor.Groups.Adapters.GroupAdapters;
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
import org.pgpainless.key.selection.key.util.And;

import java.util.ArrayList;
import java.util.Date;

public class RelationshipsAdapter extends RecyclerView.Adapter<RelationshipsAdapter.MyViewHolder> implements Filterable, AsyncTaskCompleteListener, SharedDocumentsAdapter.EventListener {
    ArrayList<RelationshipsModel> relationshipsList = new ArrayList<>();
    ArrayList<RelationshipsModel> itemsList = new ArrayList<>();
    ArrayList<ProfileDo> citizenList = new ArrayList<>();
    int position = 0;
    Context mcontext;
    ArrayList<ViewGroupModel> updatedMembersList = new ArrayList<>();
    ArrayList<SharedDocumentsDo>shared_list = new ArrayList<>();
    RelationshipsAdapter.MyViewHolder mholder;
    RelationshipsModel relationshipsModel_new;
    ArrayList<ViewGroupModel> groupsList = new ArrayList<>();
    AlertDialog ad_dialog, ad_dialog_delete,ad_dialog_copy;
    ViewGroup mParent;
    String shared_tag = "";
    private  String shared_relationship_id = "";
    FragmentActivity mActivity;
    RelationshipsAdapter.EventListener eventListener;
    private RelationshipsModel relationshipmodel_profile;
    private String FLAG = "";
    View view = null;

    public RelationshipsAdapter(ArrayList<RelationshipsModel> relationshipsList, Context context, FragmentActivity activity, RelationshipsAdapter.EventListener listener) {
        this.relationshipsList = relationshipsList;
        this.itemsList = relationshipsList;
        this.mcontext = context;
        this.mActivity = activity;
        this.eventListener = listener;
    }

    @Override
    public Filter getFilter() {
        return null;
    }

    @Override
    public void CopyDocument(SharedDocumentsDo sharedDocumentsDo) {
        try {
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mcontext);
            LayoutInflater inflater = mActivity.getLayoutInflater();
            View view = inflater.inflate(R.layout.delete_relationship, null);
            TextInputEditText tv_confirmation = view.findViewById(R.id.et_confirmation);
            tv_confirmation.setText("Are you sure you want to copy " + sharedDocumentsDo.getName() + "?");
            AppCompatButton bt_yes = view.findViewById(R.id.btn_yes);
            AppCompatButton btn_no = view.findViewById(R.id.btn_No);
            bt_yes.setText("Copy");
            btn_no.setText("Cancel");
            btn_no.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ad_dialog_copy.dismiss();
                }
            });
            bt_yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   callCopyDocumentWebservice(sharedDocumentsDo);
                }
            });
            final AlertDialog dialog = dialogBuilder.create();
            ad_dialog_copy = dialog;
            dialog.setView(view);
            dialog.show();
        } catch (Exception e) {
            AndroidUtils.showToast(e.getMessage(), mcontext);
        }
    }

    private void callCopyDocumentWebservice(SharedDocumentsDo sharedDocumentsDo) {
        JSONObject jsonObject = new JSONObject();
        WebServiceHelper.callHttpWebService(this,mcontext, WebServiceHelper.RestMethodType.GET,"v2/relationship/"+shared_relationship_id+"/"+sharedDocumentsDo.getId()+"/copy","Copy Document",jsonObject.toString());
    }

    public interface EventListener {
        void RefreshViewRelationshipsData();
    }

    @NonNull
    @Override
    public RelationshipsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mParent = parent;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_relationships_design, parent, false);
        return new RelationshipsAdapter.MyViewHolder(view);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onBindViewHolder(@NonNull RelationshipsAdapter.MyViewHolder holder, int position) {
//        updatedMembersList.clear();
        RelationshipsModel relationshipsModel = relationshipsList.get(position);
        position = position;
        if (relationshipsModel.isCanAccept()) {
            holder.btn_accept.setVisibility(View.VISIBLE);
            holder.iv_groups_relationships.setVisibility(View.GONE);
        }
        {
            holder.btn_accept.setVisibility(View.GONE);
            holder.iv_groups_relationships.setVisibility(View.VISIBLE);
            ImageView iv_initiated = view.findViewById(R.id.iv_initiated);
            if (relationshipsModel.isAccepted()) {
                holder.tv_initiated.setText("Accepted");

                iv_initiated.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.green_circular));
            } else {
                holder.tv_initiated.setText("Not Accepted");
                iv_initiated.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.red_circular));
            }
        }
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
                    callGroupsWebservice(holder, relationshipsModel);
//                    holder.tv_relationship_name.setText("Sai");
//                    holder.cv_documents.setVisibility(View.VISIBLE);
//                    holder.ll_documents.setVisibility(View.VISIBLE);
                } catch (Exception e) {
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
        FLAG = "first_click";
        holder.tv_more_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (FLAG != "second_click") {
                    unhideProfileDetails(relationshipsModel, holder);
                } else {
                    FLAG = "first_click";
                    holder.cv_Profile.setVisibility(View.GONE);
                    holder.rg_profile.setVisibility(View.GONE);
                    citizenList.clear();


                }
            }
        });

        holder.iv_delete_relationships.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteRelationships(relationshipsModel, relationshipsModel.getName());
            }
        });
        holder.rg_profile.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                switch (i) {
                    case R.id.rb_profile:
                        unhideProfileDetails(relationshipsModel, holder);

                        break;
                    case R.id.rb_share_button:
                        holder.cv_Profile.setVisibility(View.GONE);
                        holder.rb_share_document.setBackgroundDrawable(mcontext.getResources().getDrawable(R.drawable.radiobutton_centre_green_background));
                        holder.rb_profile.setBackgroundDrawable(mcontext.getResources().getDrawable(R.drawable.button_left_background));
                        holder.rg_shared_status.setVisibility(View.VISIBLE);
//                        holder.ll_documents.setVisibility(View.VISIBLE);
                        holder.nestedScrollView.setVisibility(View.VISIBLE);
                        holder.ll_documents.setVisibility(View.VISIBLE);
                         shared_tag = "withme";
                        callSharedDocumentsWebservice(relationshipsModel.getId(),shared_tag,holder);
//                        hideProfileData(holder);
                }
            }
        });
        holder.rg_shared_status.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.rb_shared_by_us:
                        holder.nestedScrollView.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void callSharedDocumentsWebservice(String id, String shared_tag, MyViewHolder holder) {
        mholder = holder;
        shared_relationship_id = id;
        JSONObject jsonObject = new JSONObject();
        WebServiceHelper.callHttpWebService(this,mcontext, WebServiceHelper.RestMethodType.GET,"v2/relationship/"+id+"/docs/shared/"+shared_tag,"Shared Documents",jsonObject.toString());
    }

    private void hideProfileData(MyViewHolder holder) {


    }

    private void unhideProfileDetails(RelationshipsModel relationshipsModel, MyViewHolder holder) {
        mholder = holder;
        holder.rb_share_document.setBackgroundDrawable(mcontext.getResources().getDrawable(R.drawable.radiobutton_centre_background));
        holder.rb_profile.setBackgroundDrawable(mcontext.getResources().getDrawable(R.drawable.button_left_green_background));
        holder.rg_shared_status.setVisibility(View.GONE);
        relationshipmodel_profile = relationshipsModel;
        holder.nestedScrollView.setVisibility(View.GONE);
        holder.ll_documents.setVisibility(View.GONE);
        callProfileWebservice(relationshipsModel.getId());
    }

    private void callProfileWebservice(String id) {
        JSONObject jsonObject = new JSONObject();
        WebServiceHelper.callHttpWebService(this, mcontext, WebServiceHelper.RestMethodType.GET, "v2/relationship/" + id + "/profile", "Profile", jsonObject.toString());
    }


    @SuppressLint("SetTextI18n")
    private void deleteRelationships(RelationshipsModel relationshipsModel, String name) {
        try {
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mcontext);
            LayoutInflater inflater = mActivity.getLayoutInflater();
            View view = inflater.inflate(R.layout.delete_relationship, null);
            TextInputEditText tv_confirmation = view.findViewById(R.id.et_confirmation);
            tv_confirmation.setText("You will be no longer be able to communicate and exchange information with " + name + ".Are you sure you want to end this digital relationship?");
            AppCompatButton bt_yes = view.findViewById(R.id.btn_yes);
            AppCompatButton btn_no = view.findViewById(R.id.btn_No);
            btn_no.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ad_dialog_delete.dismiss();
                }
            });
            bt_yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    callDeleteGroupsWebservice(relationshipsModel.getId());
                }
            });
            final AlertDialog dialog = dialogBuilder.create();
            ad_dialog_delete = dialog;
            dialog.setView(view);
            dialog.show();
        } catch (Exception e) {
            AndroidUtils.showToast(e.getMessage(), mcontext);
        }
    }

    private void callDeleteGroupsWebservice(String id) {
        try {
            JSONObject jsonObject = new JSONObject();
            WebServiceHelper.callHttpWebService(this, mcontext, WebServiceHelper.RestMethodType.POST, "v2/relationship/" + id + "/terminate/request", "Delete Relationship", jsonObject.toString());
        } catch (Exception e) {
            AndroidUtils.showToast(e.getMessage(), mcontext);
        }
    }

    private void callGroupsWebservice(MyViewHolder holder, RelationshipsModel relationshipsModel) {
        try {
            for (int j = 0; j < relationshipsModel.getGroups().length(); j++) {
                ViewGroupModel viewGroupModel = new ViewGroupModel();
                JSONObject jsonObject = relationshipsModel.getGroups().getJSONObject(j);
                viewGroupModel.setGroup_id(jsonObject.getString("id"));
                viewGroupModel.setGroup_name(jsonObject.getString("name"));
                updatedMembersList.add(viewGroupModel);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        relationshipsModel_new = relationshipsModel;
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
            boolean error = result.getBoolean("error");
            String msg = "";


            if (httpResult.getRequestType().equals("Get Groups")) {
                if (error) {
                    msg = result.getString("msg");
                    AndroidUtils.showToast(msg, mcontext);
                } else {
                    JSONArray data = result.getJSONArray("data");
                    loadViewGroups(data);
                }

            } else if (httpResult.getRequestType().equals("Update Group Access")) {

                if (error) {
                    msg = result.getString("msg");
                    AndroidUtils.showToast(msg, mcontext);
                } else {
                    AndroidUtils.showToast(msg, mcontext);
                    groupsList.clear();
                    relationshipsList.clear();
                    ad_dialog.dismiss();
                    eventListener.RefreshViewRelationshipsData();
                }

            } else if (httpResult.getRequestType().equals("Delete Relationship")) {
                if (error) {
                    msg = result.getString("msg");
                    AndroidUtils.showToast(msg, mcontext);
                } else {
                    msg = result.getString("msg");
                    AndroidUtils.showToast(msg, mcontext);
                    groupsList.clear();
                    relationshipsList.clear();
                    ad_dialog_delete.dismiss();
                    eventListener.RefreshViewRelationshipsData();
                }
            } else if (httpResult.getRequestType().equals("Profile")) {
                if (error) {
                    msg = result.getString("msg");
                    AndroidUtils.showToast(msg, mcontext);
                } else {
                    mholder.rg_profile.setVisibility(View.VISIBLE);
                    JSONObject data = result.getJSONObject("data");
                    Log.d("TAG", "Data:" + data.toString());
                    loadProfile(data);
                }
            }else if(httpResult.getRequestType().equals("Shared Documents")){
                if (error) {
                    msg = result.getString("msg");
                    AndroidUtils.showToast(msg, mcontext);
                }else {
                    JSONObject documents = result.getJSONObject("documents");
                    loadSharedWithmeDocuments(documents);
                }
            }else if(httpResult.getRequestType().equals("Copy Document")){
                msg = result.getString("msg");
                if (error){
                    ad_dialog_copy.dismiss();
                    AndroidUtils.showAlert(msg, mcontext);
//                    AndroidUtils.showToast(msg, mcontext);
                }else
                {
                    ad_dialog_copy.dismiss();
                    AndroidUtils.showToast(msg, mcontext);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void loadSharedWithmeDocuments(JSONObject documents)throws JSONException {
        JSONArray identity = documents.getJSONArray("identity");
        for (int i=0;i<identity.length();i++){
            SharedDocumentsDo sharedDocumentsDo = new SharedDocumentsDo();
            JSONObject docs = identity.getJSONObject(i);
            sharedDocumentsDo.setCategory(docs.getString("category"));
            sharedDocumentsDo.setCreated(docs.getString("created"));
            sharedDocumentsDo.setDescription(docs.getString("description"));
            sharedDocumentsDo.setDoctype(docs.getString("doctype"));
            sharedDocumentsDo.setId(docs.getString("id"));
            sharedDocumentsDo.setShareOn(docs.getString("sharedOn"));
            sharedDocumentsDo.setName(docs.getString("name"));
            shared_list.add(sharedDocumentsDo);
        }
        loadDocumentsRecyclerview();
    }

    private void loadDocumentsRecyclerview() {
        mholder.rv_documents.removeAllViews();
        mholder.rv_documents.setLayoutManager(new GridLayoutManager(mcontext, 1));

       SharedDocumentsAdapter adapter = new SharedDocumentsAdapter(shared_list,shared_tag,mcontext,this);
        mholder.rv_documents.setAdapter(adapter);
        mholder.rv_documents.setHasFixedSize(true);
    }

    private void loadProfile(JSONObject data) throws JSONException {
        try {
            FLAG = "second_click";
            mholder.cv_Profile.setVisibility(View.VISIBLE);
            mholder.tv_first_name.setText(relationshipmodel_profile.getName());
            mholder.tv_email.setText(data.getString("email"));
            mholder.tv_contact_name.setText(data.getString("first_name"));
            mholder.tv_mobile.setText(data.getString("mobile"));
            JSONArray citizen = data.getJSONArray("citizen");
            for (int i = 0; i < citizen.length(); i++) {
                JSONObject jsonObject = citizen.getJSONObject(i);
                ProfileDo profileDo = new ProfileDo();
                profileDo.setIndex(jsonObject.getString("index"));
                profileDo.setCountry(jsonObject.getString("country"));
                citizenList.add(profileDo);
            }
            for (int i = 0; i < citizenList.size(); i++) {
                if (citizenList.get(i).getIndex().equals("citizen_primary")) {
                    mholder.tv_country.setText(citizenList.get(i).getCountry());
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            AndroidUtils.showToast(e.getMessage(), mcontext);
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
        btn_send_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                et_search_relationships.setText("");
                try {
                    callUpdateGroupAcess(relationshipsModel_new.getId(), groupsAdapter.getList_item());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        dialog.setView(view);
        dialog.show();
    }

    private void callUpdateGroupAcess(String id, ArrayList<ViewGroupModel> list_item) throws JSONException {
        JSONObject postData = new JSONObject();
        JSONArray acls = new JSONArray();
        if (list_item.size() != 0) {
            for (int i = 0; i < list_item.size(); i++) {
                ViewGroupModel viewGroupModel = list_item.get(i);
                if (viewGroupModel.isChecked()) {
                    acls.put(viewGroupModel.getId());
                }
            }
            postData.put("acls", acls);
            WebServiceHelper.callHttpWebService(this, mcontext, WebServiceHelper.RestMethodType.PUT, "v2/relationship/" + id + "/acls", "Update Group Access", postData.toString());
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_relationship_name, tv_created_date, tv_consumer, tv_more_details, tv_initiated, tv_first_name, tv_email, tv_country, tv_contact_name, tv_mobile, tv_website, tv_billing_currency;
        ImageView iv_initiated, iv_groups_relationships, iv_clock_relationships, iv_delete_relationships;
        RadioGroup rg_profile, rg_shared_status, rg_shared_type, rg_document_type;
        CardView cv_Profile;
        Button btn_accept;
        LinearLayout ll_documents;
        RecyclerView rv_documents;
        RadioButton rb_share_document, rb_profile, rb_shared_by_us, rb_shared_with_us;
        NestedScrollView nestedScrollView;

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
            rg_profile = itemView.findViewById(R.id.profile);
            rg_shared_status = itemView.findViewById(R.id.shared_status);
            rg_shared_type = itemView.findViewById(R.id.shared_type);
            rg_document_type = itemView.findViewById(R.id.document_type);
            tv_first_name = itemView.findViewById(R.id.tv_first_name);
            tv_email = itemView.findViewById(R.id.tv_email);
            tv_country = itemView.findViewById(R.id.tv_country);
            tv_contact_name = itemView.findViewById(R.id.tv_contact_name);
            tv_mobile = itemView.findViewById(R.id.tv_mobile);
            tv_website = itemView.findViewById(R.id.tv_website);
            tv_billing_currency = itemView.findViewById(R.id.tv_billing_currency);
            cv_Profile = itemView.findViewById(R.id.cv_profile);
            btn_accept = itemView.findViewById(R.id.accept);
            rb_profile = itemView.findViewById(R.id.rb_profile);
            rb_share_document = itemView.findViewById(R.id.rb_share_button);
            rb_shared_by_us = itemView.findViewById(R.id.rb_shared_by_us);
            rb_shared_with_us = itemView.findViewById(R.id.rb_shared_with_us);
            nestedScrollView = itemView.findViewById(R.id.nestedScrollView);
            ll_documents = itemView.findViewById(R.id.ll_documents);
            rv_documents = itemView.findViewById(R.id.rv_shared_documents);
        }
    }
}
