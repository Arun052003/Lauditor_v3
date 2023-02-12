package com.digicoffer.lauditor.Matter;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.digicoffer.lauditor.Matter.Adapters.DocumentsAdapter;
import com.digicoffer.lauditor.Matter.Adapters.GroupsAdapter;
import com.digicoffer.lauditor.Matter.Models.ClientsModel;
import com.digicoffer.lauditor.Matter.Models.DocumentsModel;
import com.digicoffer.lauditor.Matter.Models.GroupsModel;
import com.digicoffer.lauditor.Matter.Models.MatterModel;
import com.digicoffer.lauditor.Matter.Models.TeamModel;
import com.digicoffer.lauditor.R;
import com.digicoffer.lauditor.Webservice.AsyncTaskCompleteListener;
import com.digicoffer.lauditor.Webservice.HttpResultDo;
import com.digicoffer.lauditor.Webservice.WebServiceHelper;
import com.digicoffer.lauditor.common.AndroidUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.minidns.record.A;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class MatterDocuments extends Fragment implements AsyncTaskCompleteListener,View.OnClickListener {

    private TextView matter_date,tv_document_library,tv_device_drive,at_add_documents,tv_selected_file;
    private LinearLayout ll_add_documents,ll_selected_documents,ll_select_doc;
    private Button btn_browse,btn_add_documents;
    AppCompatButton btn_cancel_save,btn_create;
    boolean [] selectedDocument;
    ArrayList<MatterModel> matterArraylist ;
    String ADAPTER_TAG = "Documents";
    ArrayList<DocumentsModel> documentsList = new ArrayList<>();
    ArrayList<DocumentsModel> selected_documents_list = new ArrayList<>();
    JSONArray exisiting_group_acls;
    ArrayList<GroupsModel> selected_groups_list = new ArrayList<>();
    private AlertDialog progress_dialog;
    Matter matter;
    private ArrayList<GroupsModel> groupsList = new ArrayList<>();
    private ArrayList<ClientsModel> clientsList = new ArrayList<>();
    private ArrayList<TeamModel> tmList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.documents_matter, container, false);
        Calendar myCalendar = Calendar.getInstance();
        matter_date = view.findViewById(R.id.matter_date);
        tv_document_library = view.findViewById(R.id.tv_document_library);
        tv_document_library.setOnClickListener(this);
        tv_device_drive = view.findViewById(R.id.tv_device_drive);
        tv_device_drive.setOnClickListener(this);
        at_add_documents = view.findViewById(R.id.at_add_documents);
        tv_selected_file = view.findViewById(R.id.tv_selected_file);
        ll_add_documents = view.findViewById(R.id.ll_add_documents);
        ll_selected_documents = view.findViewById(R.id.ll_selected_documents);
        ll_select_doc = view.findViewById(R.id.ll_select_doc);
        btn_browse = view.findViewById(R.id.btn_browse);
        btn_add_documents = view.findViewById(R.id.btn_add_documents);
        btn_add_documents.setOnClickListener(this);
        btn_cancel_save = view.findViewById(R.id.btn_cancel_save);
        btn_create = view.findViewById(R.id.btn_create);
        matter = (Matter) getParentFragment();
        matterArraylist  = matter.getMatter_arraylist();
        if (matterArraylist.size()!=0) {
            for (int i = 0; i < matterArraylist.size(); i++) {
                MatterModel matterModel = matterArraylist.get(i);
                exisiting_group_acls = matterModel.getGroup_acls();
            }
            try {
                for (int i = 0; i < exisiting_group_acls.length(); i++) {
                    GroupsModel groupsModel = new GroupsModel();
                    JSONObject jsonObject = exisiting_group_acls.getJSONObject(i);
                    groupsModel.setGroup_id(jsonObject.getString("id"));
                    groupsModel.setGroup_name(jsonObject.getString("name"));
                    groupsModel.setChecked(jsonObject.getBoolean("isChecked"));
                    selected_groups_list.add(groupsModel);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

            private void updateLabel() {
                String myFormat = "dd-MM-yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                matter_date.setText(sdf.format(myCalendar.getTime()));
            }
        };
        matter_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
            }
        });
        return  view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_document_library:
                loadDocumentLibraryUI();
                break;
            case R.id.tv_device_drive:
                loadDeviceDriveUI();
                break;
            case R.id.btn_add_documents:
                if(documentsList.size()==0) {
                    callDocumentsWebService();
                }else
                {
                    DocumentsPopUp();
                }
                break;

        }
    }

    private void callDocumentsWebService() {
        try{
        progress_dialog = AndroidUtils.get_progress(getActivity());
        JSONArray group_acls = new JSONArray();
        JSONObject postdata = new JSONObject();
        for (int i = 0; i < selected_groups_list.size(); i++) {
            JSONObject jsonObject = new JSONObject();
            GroupsModel groupsModel = selected_groups_list.get(i);
            group_acls.put(groupsModel.getGroup_id());
        }
        postdata.put("group_acls", group_acls);
        postdata.put("attachment_type", "documents");
        WebServiceHelper.callHttpWebService(this, getContext(), WebServiceHelper.RestMethodType.PUT, "matter/attachments", "Documents",postdata.toString());
    } catch (JSONException e) {
        e.printStackTrace();
    }
    }

    private void loadDeviceDriveUI() {
        tv_document_library.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.button_left_background));
        tv_device_drive.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.button_right_green_count));
        ll_add_documents.setVisibility(View.GONE);
        ll_select_doc.setVisibility(View.VISIBLE);
        ll_selected_documents.removeAllViews();
        documentsList.clear();
        at_add_documents.setText("Select Document");
    }

    private void loadDocumentLibraryUI() {
        tv_document_library.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.button_left_green_background));
        tv_device_drive.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.button_right_background));
        ll_add_documents.setVisibility(View.VISIBLE);
        ll_select_doc.setVisibility(View.GONE);
    }

    @Override
    public void onAsyncTaskComplete(HttpResultDo httpResult) {
        if (progress_dialog != null && progress_dialog.isShowing())
            AndroidUtils.dismiss_dialog(progress_dialog);
        if (httpResult.getResult() == WebServiceHelper.ServiceCallStatus.Success) {
            try {
                JSONObject result = new JSONObject(httpResult.getResponseContent());
                if (httpResult.getRequestType().equals("Documents")) {
                    JSONArray data = result.getJSONArray("documents");
//                    AndroidUtils.showAlert(data.toString(),getContext());
                    loadDocumentsData(data);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void loadDocumentsData(JSONArray data) {
        try {
            for (int i = 0; i < data.length(); i++) {
                JSONObject jsonObject = data.getJSONObject(i);
                DocumentsModel documentsModel = new DocumentsModel();
                documentsModel.setDocid(jsonObject.getString("docid"));
                documentsModel.setDescription(jsonObject.getString("description"));
                documentsModel.setDocid(jsonObject.getString("docid"));
                documentsModel.setDoctype(jsonObject.getString("doctype"));
                documentsModel.setName(jsonObject.getString("name"));
                documentsModel.setUser_id(jsonObject.getString("user_id"));
                documentsList.add(documentsModel);
            }
            DocumentsPopUp();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void DocumentsPopUp() {
        try{
            for (int i = 0; i < documentsList.size(); i++) {
                for (int j = 0; j < selected_documents_list.size(); j++) {
                    if (documentsList.get(i).getDocid().matches(selected_documents_list.get(j).getDocid())) {
                        DocumentsModel documentsModel = documentsList.get(i);
                        documentsModel.setChecked(true);
//                        selected_groups_list.set(j,documentsModel);

                    }
                }
            }
            selected_documents_list.clear();
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
            LayoutInflater inflater = getActivity().getLayoutInflater();
            View view = inflater.inflate(R.layout.groups_list_adapter, null);
            RecyclerView rv_groups = view.findViewById(R.id.rv_relationship_documents);
            ImageView iv_cancel = view.findViewById(R.id.close_groups);
            AppCompatButton btn_groups_cancel = view.findViewById(R.id.btn_groups_cancel);
            AppCompatButton btn_save_group = view.findViewById(R.id.btn_save_group);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            rv_groups.setLayoutManager(layoutManager);
            rv_groups.setHasFixedSize(true);
//            ADAPTER_TAG = "Documents";
            DocumentsAdapter documentsAdapter = new DocumentsAdapter(documentsList);
            rv_groups.setAdapter(documentsAdapter);
            AlertDialog dialog = dialogBuilder.create();
            iv_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
            btn_groups_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
            btn_save_group.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    ArrayList<String>
                    for (int i = 0; i < documentsAdapter.getDocumentsList().size(); i++) {
                        DocumentsModel documentsModel = documentsAdapter.getDocumentsList().get(i);
                        if (documentsModel.isChecked()) {
                            selected_documents_list.add(documentsModel);
                            //                           jsonArray.put(selected_documents_list.get(i).getGroup_name());
                        }
                    }


                    loadSelectedDocuments();
//                    loadSelectedClients();
//                    loadSelectedGroups();
                    dialog.dismiss();
                }

            });
            dialog.setCancelable(false);
            dialog.setView(view);
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
            AndroidUtils.showAlert(e.getMessage(), getContext());
        }
        }

    private void loadSelectedDocuments() {
        String[] value = new String[selected_documents_list.size()];
        for (int i = 0; i < selected_documents_list.size(); i++) {
//                                value += "," + family_members.get(i);
//                               value.add(family_members.get(i));
            value[i] = selected_documents_list.get(i).getName();

        }

        String str = String.join(",", value);
        at_add_documents.setText(str);
//        selected_tm.setVisibility(View.VISIBLE);
        ll_selected_documents.removeAllViews();
        for(int i=0;i<selected_documents_list.size();i++){
            View view_opponents = LayoutInflater.from(getContext()).inflate(R.layout.edit_opponent_advocate, null);
            TextView tv_opponent_name = view_opponents.findViewById(R.id.tv_opponent_name);
            tv_opponent_name.setText(selected_documents_list.get(i).getName());
            ImageView iv_edit_opponent = view_opponents.findViewById(R.id.iv_edit_opponent);
            ImageView iv_remove_opponent = view_opponents.findViewById(R.id.iv_remove_opponent);
            iv_remove_opponent.setTag(i);
            iv_remove_opponent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        int position = 0;
                        if (v.getTag() instanceof Integer) {
                            position = (Integer) v.getTag();
                            v = ll_selected_documents.getChildAt(position);
                            ll_selected_documents.removeView(v);
//                            ll_selected_groups.addView(view_opponents,position);
                            DocumentsModel documentsModel = selected_documents_list.get(position);
                            documentsModel.setChecked(false);
                            selected_documents_list.remove(position);
//                            selected_groups_list.set(position, groupsModel);
                            String[] value = new String[selected_documents_list.size()];
                            for (int i = 0; i < selected_documents_list.size(); i++) {
                                value[i] = selected_documents_list.get(i).getName();
                            }

                            String str = String.join(",", value);
                            at_add_documents.setText(str);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        AndroidUtils.showAlert(e.getMessage(), getContext());
                    }
                }
            });
            iv_edit_opponent.setVisibility(View.GONE);
            ll_selected_documents.addView(view_opponents);
        }
    }
}

