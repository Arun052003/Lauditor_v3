package com.digicoffer.lauditor.Matter;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;
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
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.digicoffer.lauditor.Documents.Documents;
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
import com.digicoffer.lauditor.common.BottomSheetUploadFile;
import com.digicoffer.lauditor.common.Constants;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnErrorListener;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.minidns.record.A;
import org.pgpainless.key.selection.key.util.And;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class MatterDocuments extends Fragment implements AsyncTaskCompleteListener,View.OnClickListener ,BottomSheetUploadFile.OnPhotoSelectedListner{

    private TextView matter_date,tv_document_library,tv_device_drive,at_add_documents,tv_selected_file;
    private LinearLayout ll_add_documents,ll_selected_documents,ll_select_doc,ll_uploaded_documents;
    private Button btn_browse,btn_add_documents;
    private ImageView imageView;
    ArrayList<TeamModel> selected_tm_list = new ArrayList<>();
    ArrayList<ClientsModel> selected_clients_list = new ArrayList<>();
    BottomSheetUploadFile bottommSheetUploadDocument;
    AppCompatButton btn_cancel_save,btn_create;
    boolean [] selectedDocument;
    private Bitmap mSelectedBitmap;
    ArrayList<MatterModel> matterArraylist ;
    private File mSelectedUri;
    String ADAPTER_TAG = "Documents";
    ArrayList<DocumentsModel> documentsList = new ArrayList<>();
    ArrayList<DocumentsModel> selected_documents_list = new ArrayList<>();
    ArrayList<DocumentsModel> upload_documents_list = new ArrayList<>();
    JSONArray exisiting_group_acls;
    JSONArray existing_documents;
    JSONArray existing_documents_list;
    JSONArray existing_clients;
    JSONArray existing_members;
    JSONArray existing_groups_list;
    JSONArray existing_clients_list;
    JSONArray existing_tm_list;
    ArrayList<GroupsModel> selected_groups_list = new ArrayList<>();
    private AlertDialog progress_dialog;
    String filename;
    File file;
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
        ll_uploaded_documents = view.findViewById(R.id.ll_uploaded_documents);
        ll_select_doc = view.findViewById(R.id.ll_select_doc);
        btn_browse = view.findViewById(R.id.btn_browse);
        btn_browse.setOnClickListener(this);
        btn_add_documents = view.findViewById(R.id.btn_add_documents);
        btn_add_documents.setOnClickListener(this);
        btn_cancel_save = view.findViewById(R.id.btn_cancel_save);
        btn_create = view.findViewById(R.id.btn_submit);
        btn_create.setOnClickListener(this);
        matter = (Matter) getParentFragment();
        matterArraylist  = matter.getMatter_arraylist();
        if (matterArraylist.size()!=0) {
            for (int i = 0; i < matterArraylist.size(); i++) {
                MatterModel matterModel = matterArraylist.get(i);
                exisiting_group_acls = matterModel.getGroup_acls();
                existing_clients = matterModel.getClients();
                existing_members = matterModel.getMembers();
                existing_groups_list = matterModel.getGroups_list();
                existing_clients_list = matterModel.getClients_list();
                existing_tm_list = matterModel.getMembers_list();
                if (matterModel.getDocuments()!=null) {
                    existing_documents = matterModel.getDocuments();
                }if(matterModel.getDocuments_list()!=null){
                    existing_documents_list = matterModel.getDocuments_list();
                }

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
                if (existing_documents!=null){
                    for (int i=0;i<existing_documents.length();i++){
                        DocumentsModel documentsModel = new DocumentsModel();
                        JSONObject jsonObject =existing_documents.getJSONObject(i);
                        documentsModel.setDocid(jsonObject.getString("id"));
                        documentsModel.setName(jsonObject.getString("name"));
                        documentsModel.setUser_id(jsonObject.getString("user_id"));
                        documentsModel.setDoctype(jsonObject.getString("doctype"));
                        selected_documents_list.add(documentsModel);
                    }
                }
                if(existing_documents_list!=null)
                {
                    for (int i=0;i<existing_documents_list.length();i++){
                        DocumentsModel documentsModel = new DocumentsModel();
                        JSONObject jsonObject =existing_documents_list.getJSONObject(i);
                        documentsModel.setDocid(jsonObject.getString("id"));
                        documentsModel.setName(jsonObject.getString("name"));
                        documentsModel.setUser_id(jsonObject.getString("user_id"));
                        documentsModel.setDoctype(jsonObject.getString("doctype"));
                        documentsList.add(documentsModel);
                    }
                    for (int i=0;i<exisiting_group_acls.length();i++){
                        GroupsModel groupsModel = new GroupsModel();
                        JSONObject jsonObject = exisiting_group_acls.getJSONObject(i);
                        groupsModel.setGroup_id(jsonObject.getString("id"));
                        groupsModel.setGroup_name(jsonObject.getString("name"));
                        groupsModel.setChecked(jsonObject.getBoolean("isChecked"));
                        selected_groups_list.add(groupsModel);
                    }
                    for (int i=0;i<existing_groups_list.length();i++){
                        GroupsModel groupsModel = new GroupsModel();
                        JSONObject jsonObject = existing_groups_list.getJSONObject(i);
                        groupsModel.setGroup_id(jsonObject.getString("id"));
                        groupsModel.setGroup_name(jsonObject.getString("name"));
                        groupsList.add(groupsModel);
                    }

                    for (int i=0;i<existing_clients.length();i++){
                        ClientsModel clientsModel = new ClientsModel();
                        JSONObject jsonObject = existing_clients.getJSONObject(i);
                        clientsModel.setClient_id(jsonObject.getString("id"));
                        clientsModel.setClient_name(jsonObject.getString("name"));
                        clientsModel.setClient_type(jsonObject.getString("type"));
                        selected_clients_list.add(clientsModel);
                    }
                    for (int i=0;i<existing_clients_list.length();i++){
                        ClientsModel clientsModel = new ClientsModel();
                        JSONObject jsonObject = existing_clients_list.getJSONObject(i);
                        clientsModel.setClient_id(jsonObject.getString("id"));
                        clientsModel.setClient_name(jsonObject.getString("name"));
                        clientsModel.setClient_type(jsonObject.getString("type"));
                        clientsList.add(clientsModel);
                    }
                    for (int i=0;i<existing_members.length();i++){
                        TeamModel teamModel = new TeamModel();
                        JSONObject jsonObject = existing_members.getJSONObject(i);
                        teamModel.setTm_id(jsonObject.getString("id"));
                        teamModel.setTm_name(jsonObject.getString("name"));
                        teamModel.setUser_id(jsonObject.getString("user_id"));
                        selected_tm_list.add(teamModel);
                    }
                    for (int i=0;i<existing_tm_list.length();i++){
                        TeamModel teamModel = new TeamModel();
                        JSONObject jsonObject = existing_tm_list.getJSONObject(i);
                        teamModel.setTm_id(jsonObject.getString("id"));
                        teamModel.setTm_name(jsonObject.getString("name"));
                        teamModel.setUser_id(jsonObject.getString("user_id"));
                        tmList.add(teamModel);
                    }
                }

                if (selected_documents_list.size()!=0){
                    loadSelectedDocuments();
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
            case R.id.btn_browse:
                checkPermissionREAD_EXTERNAL_STORAGE(getContext());
                break;
            case R.id.btn_submit:
                submitMatterInformation();
                break;


        }
    }

    private void submitMatterInformation() {

        if(selected_documents_list.size()==0){
            AndroidUtils.showToast("Please add atleast one document",getContext());
        }else{
            try {
                JSONArray documents = new JSONArray();
                JSONArray new_documents_list = new JSONArray();
                JSONArray clients = new JSONArray();
                JSONArray group_acls = new JSONArray();
                JSONArray members = new JSONArray();
                JSONArray new_groups_list = new JSONArray();
                JSONArray new_clients_list = new JSONArray();
                JSONArray new_tm_list =new JSONArray();
//                for (int i=0;i<matterArraylist.size();i++) {
                    MatterModel matterModel = new MatterModel();
                    for (int d = 0; d < selected_documents_list.size(); d++) {
                        DocumentsModel documentsModel = selected_documents_list.get(d);
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("docid", documentsModel.getDocid());
                        jsonObject.put("doctype", documentsModel.getDoctype());
                        jsonObject.put("user_id", documentsModel.getUser_id());
                        jsonObject.put("name", documentsModel.getName());
                        documents.put(jsonObject);
                    }
                    for (int e = 0; e< documentsList.size(); e++) {
                        DocumentsModel documentsModel = documentsList.get(e);
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("docid", documentsModel.getDocid());
                        jsonObject.put("doctype", documentsModel.getDoctype());
                        jsonObject.put("user_id", documentsModel.getUser_id());
                        jsonObject.put("name", documentsModel.getName());
                        new_documents_list.put(jsonObject);
                    }
//                    for (int s = 0; i < selected_groups_list.size(); i++) {
//                        try {
//                            GroupsModel groupsModel = selected_groups_list.get(i);
//                            JSONObject jsonObject = new JSONObject();
//                            jsonObject.put("id", groupsModel.getGroup_id());
//                            jsonObject.put("name", groupsModel.getGroup_name());
//                            jsonObject.put("isChecked", groupsModel.isChecked());
//                            group_acls.put(jsonObject);
//
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                    for (int i = 0; i < groupsList.size(); i++) {
//                        try {
//                            GroupsModel groupsModel = groupsList.get(i);
//                            JSONObject jsonObject = new JSONObject();
//                            jsonObject.put("id", groupsModel.getGroup_id());
//                            jsonObject.put("name", groupsModel.getGroup_name());
//                            new_groups_list.put(jsonObject);
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                    for (int i = 0; i < selected_clients_list.size(); i++) {
//                        try {
//                            ClientsModel clientsModel = selected_clients_list.get(i);
//                            JSONObject jsonObject = new JSONObject();
//                            jsonObject.put("id", clientsModel.getClient_id());
//                            jsonObject.put("type", clientsModel.getClient_type());
//                            jsonObject.put("name", clientsModel.getClient_name());
//                            clients.put(jsonObject);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//
//                    }
//                    for (int i = 0; i < clientsList.size(); i++) {
//                        ClientsModel clientsModel = clientsList.get(i);
//                        JSONObject jsonObject = new JSONObject();
//                        jsonObject.put("id", clientsModel.getClient_id());
//                        jsonObject.put("type", clientsModel.getClient_type());
//                        jsonObject.put("name", clientsModel.getClient_name());
//                        new_clients_list.put(jsonObject);
//                    }
//                    for (int i = 0; i < selected_tm_list.size(); i++) {
//                        try {
//                            TeamModel teamModel = selected_tm_list.get(i);
//                            JSONObject team_object = new JSONObject();
//                            team_object.put("id", teamModel.getTm_id());
//                            team_object.put("name", teamModel.getTm_name());
//                            team_object.put("user_id", teamModel.getUser_id());
////                        team_object.put("")
//                            members.put(team_object);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                    for (int i = 0; i < tmList.size(); i++) {
//                        try {
//                            TeamModel teamModel = tmList.get(i);
//                            JSONObject team_object = new JSONObject();
//                            team_object.put("id", teamModel.getTm_id());
//                            team_object.put("name", teamModel.getTm_name());
//                            team_object.put("user_id", teamModel.getUser_id());
////                        team_object.put("")
//                            new_tm_list.put(team_object);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }

//                    matterModel.setClients(clients);
//                    matterModel.setGroup_acls(group_acls);
//                    matterModel.setMembers(members);
//                    matterModel.setGroups_list(new_groups_list);
//                    matterModel.setClients_list(new_clients_list);
//                    matterModel.setMembers_list(new_tm_list);
                    matterModel.setDocuments(documents);
                    matterModel.setDocuments_list(new_documents_list);

                    matterArraylist.add(matterModel);
//                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean checkPermissionREAD_EXTERNAL_STORAGE(
            final Context context) {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context,
                    Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        (Activity) context,
                        Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    AndroidUtils.showDialog("External storage", context,
                            Manifest.permission.READ_EXTERNAL_STORAGE);

                } else {
                    ActivityCompat
                            .requestPermissions(
                                    (Activity) context,
                                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                    123);
                }
                return false;
            } else {
                BottomSheetUploadfile();
                return true;
            }

        } else {
            return true;
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
//        selected_documents_list.clear();
//        documentsList.clear();
        ll_selected_documents.removeAllViews();
        loadUploadedDocuments();
//        at_add_documents.setText("");
//        tv_selected_file.setText("");
        tv_document_library.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.button_left_background));
        tv_device_drive.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.button_right_green_count));
        ll_add_documents.setVisibility(View.GONE);
        ll_select_doc.setVisibility(View.VISIBLE);
//        ll_selected_documents.removeAllViews();
//        documentsList.clear();
        at_add_documents.setText("Select Document");
    }

    private void loadDocumentLibraryUI() {
//        selected_documents_list.clear();
//        documentsList.clear();
        ll_uploaded_documents.removeAllViews();
        loadSelectedDocuments();
//        at_add_documents.setText("");
//        tv_selected_file.setText("");
        tv_document_library.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.button_left_green_background));
        tv_device_drive.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.button_right_background));
        ll_add_documents.setVisibility(View.VISIBLE);
        ll_select_doc.setVisibility(View.GONE);
    }
    private void BottomSheetUploadfile() {
        bottommSheetUploadDocument = new BottomSheetUploadFile();
        bottommSheetUploadDocument.show(getParentFragmentManager(), "");
        bottommSheetUploadDocument.setTargetFragment(MatterDocuments.this, 1);
    }

    @SuppressLint("Range")
    @Override
    public void getImagepath(File imagepath, Uri ImageURI) throws IOException {
        if ((imagepath == null)) {
            mSelectedBitmap = null;
            mSelectedUri = imagepath;
            String uri = imagepath.toString();
            ImageLoader imageLoader = ImageLoader.getInstance();
            imageLoader.init(ImageLoaderConfiguration.createDefault(getActivity()));
            imageLoader.displayImage(String.valueOf(Uri.fromFile(new File(uri))), imageView);
            file = imagepath;
            Cursor c = getContext().getContentResolver().query(ImageURI, null, null, null, null);
            c.moveToFirst();
            String[] content_type = file.getName().split(".");
            String file_name = c.getString(c.getColumnIndex(OpenableColumns.DISPLAY_NAME));
            tv_selected_file.setText(file_name);

            load_documents( file_name, file);
        } else {
            file = getFile(getContext(), ImageURI);
            Log.i("FILE", "Info:" + file.toString());
            String file_name = file.getName();
            tv_selected_file.setText(file_name);
//            DocumentsModel documentsModel = new DocumentsModel();
//            documentsModel.setName(file.getName());
//            docsList.add(documentsModel);
            load_documents( file_name, file);
//            docsList.add()
        }

    }
    private void load_documents( String file_name, File file) {
//        for (int i = 0; i < docsList.size(); i++) {
//            View view = LayoutInflater.from(getContext()).inflate(R.layout.displays_documents_list, null);
//            TextView tv_docname = view.findViewById(R.id.tv_document_name);
//            tv_docname.setText(docsList.get(i).getName());
        String doc_type = "";
        String docname = "";
        String content_string = file_name.replace(".", "/");
        String[] content_type = content_string.split("/");
        if (content_type.length >= 2) {
            doc_type = content_type[1];
            docname = content_type[0];
        }
        DocumentsModel documentsModel = new DocumentsModel();
        documentsModel.setName(docname);
        documentsModel.setDescription(docname);
        documentsModel.setFile(file);
        documentsModel.setIsenabled(true);
        upload_documents_list.add(documentsModel);

//        if (docsList.size() == 1) {
//            ll_hide_document_details.setVisibility(View.VISIBLE);
//            hideDisableDownloadBackground();
//        } else if (docsList.size() < 1) {
//            ll_hide_document_details.setVisibility(View.GONE);
////            hideDisableDownloadBackground();
//        }
        String tag = "view_tags";
        loadUploadedDocuments();
//        loadSelectedDocuments();
//        loadRecyclerview(tag, subtag);

//            ll_documents.addView(view);
//        }
    }
    public static File getFile(Context context, Uri uri) throws IOException {
        File destinationFilename = new File(context.getFilesDir().getPath() + File.separatorChar + queryName(context, uri));
        try (InputStream ins = context.getContentResolver().openInputStream(uri)) {
            createFileFromStream(ins, destinationFilename);
        } catch (Exception ex) {
            Log.e("Save File", ex.getMessage());
            ex.printStackTrace();
        }
        return destinationFilename;
    }
    public static void createFileFromStream(InputStream ins, File destination) {
        try (OutputStream os = new FileOutputStream(destination)) {
            byte[] buffer = new byte[4096];
            int length;
            while ((length = ins.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
            os.flush();
        } catch (Exception ex) {
            Log.e("Save File", ex.getMessage());
            ex.printStackTrace();
        }
    }

    private static String queryName(Context context, Uri uri) {
        Cursor returnCursor =
                context.getContentResolver().query(uri, null, null, null, null);
        assert returnCursor != null;
        int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
        returnCursor.moveToFirst();
        String name = returnCursor.getString(nameIndex);
        returnCursor.close();
        return name;
    }

    @Override
    public void getImageBitmap(Bitmap bitmap) {
        imageView.setImageBitmap(bitmap);
        mSelectedBitmap = bitmap;
        mSelectedUri = null;
//        tv_upload_file.setEnabled(false);
        File filesDir = getContext().getFilesDir();
        File imageFile = new File(filesDir, "bitmap" + ".jpg");
        OutputStream os;
        try {
            os = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
            os.flush();
            os.close();
            file = imageFile;
            tv_selected_file.setText(file.getName());
            DocumentsModel documentsModel = new DocumentsModel();
            documentsModel.setName(file.getName());
            selected_documents_list.add(documentsModel);
//            loadSelectedDocuments();
        } catch (Exception e) {
            Log.e(getClass().getSimpleName(), "Error writing bitmap", e);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200 && resultCode == Activity.RESULT_OK) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContext().getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            file = new File(picturePath);
            filename = file.getName();
            tv_selected_file.setText(file.getName());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 123:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // do your stuff
//                    upload_file();
                } else {
                    AndroidUtils.showToast("GET_ACCOUNTS Denied", getContext());
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions,
                        grantResults);
        }
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
private void loadUploadedDocuments(){
    String[] value = new String[upload_documents_list.size()];
    for (int i = 0; i < upload_documents_list.size(); i++) {
//                                value += "," + family_members.get(i);
//                               value.add(family_members.get(i));
        value[i] = upload_documents_list.get(i).getName();

    }

    String str = String.join(",", value);
//    at_add_documents.setText(str);
    tv_selected_file.setText(str);
//        selected_tm.setVisibility(View.VISIBLE);
    ll_uploaded_documents.removeAllViews();
    ll_selected_documents.removeAllViews();
    for(int i=0;i<upload_documents_list.size();i++){
        View view_opponents = LayoutInflater.from(getContext()).inflate(R.layout.edit_opponent_advocate, null);
        TextView tv_opponent_name = view_opponents.findViewById(R.id.tv_opponent_name);
        tv_opponent_name.setText(upload_documents_list.get(i).getName());
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
                        v = ll_uploaded_documents.getChildAt(position);
                        ll_uploaded_documents.removeView(v);
//                            ll_selected_groups.addView(view_opponents,position);
                        DocumentsModel documentsModel = upload_documents_list.get(position);
                        documentsModel.setChecked(false);
                        upload_documents_list.remove(position);
//                            selected_groups_list.set(position, groupsModel);
                        String[] value = new String[upload_documents_list.size()];
                        for (int i = 0; i < upload_documents_list.size(); i++) {
                            value[i] = upload_documents_list.get(i).getName();
                        }

                        String str = String.join(",", value);
//                        at_add_documents.setText(str);
                        tv_selected_file.setText(str);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    AndroidUtils.showAlert(e.getMessage(), getContext());
                }
            }
        });
        iv_edit_opponent.setVisibility(View.GONE);
        ll_uploaded_documents.addView(view_opponents);
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
//        tv_selected_file.setText(str);
//        selected_tm.setVisibility(View.VISIBLE);
        ll_uploaded_documents.removeAllViews();
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
                            tv_selected_file.setText(str);
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

