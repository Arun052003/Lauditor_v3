package com.digicoffer.lauditor.Matter;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Patterns;
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

import com.digicoffer.lauditor.Matter.Models.AdvocateModel;
import com.digicoffer.lauditor.Matter.Models.MatterModel;
import com.digicoffer.lauditor.R;
import com.digicoffer.lauditor.common.AndroidUtils;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MatterInformation extends Fragment implements View.OnClickListener {
    TextInputEditText tv_matter_title, tv_matter_num, tv_case_type, tv_matter_description, tv_dof, tv_court, tv_judge;
    TextView tv_high_priority, tv_medium_priority, tv_low_priority, tv_status_active, tv_status_pending;
    Button btn_add_advocate;
    ArrayList<AdvocateModel> advocates_list = new ArrayList<>();
    ArrayList<MatterModel> matterArraylist;
    AppCompatButton btn_cancel_save, btn_create;
    LinearLayout ll_add_advocate;
    JSONArray existing_opponents;
    TextView tv_opponent_name;
    String CASE_PRIORITY = "High";
    String STATUS = "Active";
    View v;
    Matter matter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.matter_information, container, false);
        tv_matter_title = view.findViewById(R.id.tv_matter_title);
        tv_matter_num = view.findViewById(R.id.tv_matter_num);
        tv_case_type = view.findViewById(R.id.tv_case_type);
        tv_matter_description = view.findViewById(R.id.tv_matter_description);
        tv_dof = view.findViewById(R.id.tv_dof);
        tv_court = view.findViewById(R.id.tv_court);
        tv_judge = view.findViewById(R.id.tv_judge);
        tv_high_priority = view.findViewById(R.id.tv_high_priority);
        tv_high_priority.setOnClickListener(this);
        tv_medium_priority = view.findViewById(R.id.tv_medium_priority);
        tv_medium_priority.setOnClickListener(this);
        tv_low_priority = view.findViewById(R.id.tv_low_priority);
        tv_low_priority.setOnClickListener(this);
        tv_status_pending = view.findViewById(R.id.tv_status_pending);
        tv_status_pending.setOnClickListener(this);
        tv_status_active = view.findViewById(R.id.tv_status_active);
        tv_status_active.setOnClickListener(this);
        btn_add_advocate = view.findViewById(R.id.btn_add_advocate);
        btn_add_advocate.setOnClickListener(this);
        btn_cancel_save = view.findViewById(R.id.btn_cancel_save);
        btn_cancel_save.setOnClickListener(this);
        btn_create = view.findViewById(R.id.btn_create);
        btn_create.setOnClickListener(this);
        ll_add_advocate = view.findViewById(R.id.ll_add_advocate);
        matter = (Matter) getParentFragment();
        Calendar myCalendar = Calendar.getInstance();
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
                tv_dof.setText(sdf.format(myCalendar.getTime()));
              }
        };
        tv_dof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
            }
        });
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String formattedDate = df.format(c);
        tv_dof.setText(formattedDate);
        matterArraylist = matter.getMatter_arraylist();

        if (matterArraylist.size() != 0) {
            for (int i = 0; i < matterArraylist.size(); i++) {
                MatterModel matterModel = matterArraylist.get(i);
                if (matterModel.getMatter_title() != null) {
                    tv_matter_title.setText(matterModel.getMatter_title());
                } else {
                    tv_matter_title.setText("");
                }
                if (matterModel.getCase_number() != null) {
                    tv_matter_num.setText(matterModel.getCase_number());
                } else {
                    tv_matter_num.setText("");
                }
                if (matterModel.getCase_type() != null) {
                    tv_case_type.setText(matterModel.getCase_type());
                } else {
                    tv_case_type.setText("");
                }
                if (matterModel.getDescription() != null) {
                    tv_matter_description.setText(matterModel.getDescription());
                } else {
                    tv_matter_description.setText("");
                }
                if (matterModel.getDate_of_filing() != null) {
                    tv_dof.setText(matterModel.getDate_of_filing());
                } else {
                    tv_dof.setText("");
                }
                if (matterModel.getCourt() != null) {
                    tv_court.setText(matterModel.getCourt());
                } else {
                    tv_court.setText("");
                }
                if (matterModel.getJudge() != null) {
                    tv_judge.setText(matterModel.getJudge());
                } else {
                    tv_judge.setText("");
                }
                if (matterModel.getCase_priority() != null) {

                    CASE_PRIORITY = matterModel.getCase_priority();
                }
                if (matterModel.getStatus() != null) {
                    STATUS = matterModel.getStatus();
                }
                if (matterArraylist.get(i).getOpponent_advocate() != null) {
                    existing_opponents = matterArraylist.get(i).getOpponent_advocate();
                    try {
                        for (int j = 0; j < existing_opponents.length(); j++) {
                            try {
                                JSONObject jsonObject = existing_opponents.getJSONObject(j);
                                AdvocateModel advocateModel = new AdvocateModel();
                                advocateModel.setAdvocate_name(jsonObject.getString("name"));
                                advocateModel.setNumber(jsonObject.getString("phone"));
                                advocateModel.setEmail(jsonObject.getString("email"));
                                advocates_list.add(advocateModel);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                        loadOpponentsList();
                    } catch (Exception e) {
                        e.printStackTrace();
                        AndroidUtils.showAlert(e.getMessage(), getContext());
                    }
                }


//            for (int j=0;)
            }
            if (CASE_PRIORITY == "High") {
                loadHighPriorityUI();
            } else if (CASE_PRIORITY == "Medium") {
                loadMediumPriorityUI();
            } else {
                loadLowPriorityUI();
            }

            if (STATUS == "Active") {
                loadActiveUI();
            } else {
                loadPendingUI();
            }


        }


        return view;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_high_priority:
                loadHighPriorityUI();
                break;
            case R.id.tv_medium_priority:
                loadMediumPriorityUI();
                break;
            case R.id.tv_low_priority:
                loadLowPriorityUI();
                break;
            case R.id.tv_status_pending:
                loadPendingUI();
                break;
            case R.id.tv_status_active:
                loadActiveUI();
                break;
            case R.id.btn_add_advocate:

                loadAdvocateUI();
                break;
            case R.id.btn_create:
                saveMatterInformation();
                break;
        }
    }

    private void saveMatterInformation() {
        if (tv_matter_title.getText().toString().equals("")) {
            tv_matter_title.setError("Title Required");
            tv_matter_title.requestFocus();
        } else if (tv_matter_num.getText().toString().equals("")) {
            tv_matter_num.setError("Case Number Required");
            tv_matter_num.requestFocus();
        } else if (tv_matter_description.getText().toString().equals("")) {
            tv_matter_description.setError("Description is Required");
            tv_matter_description.requestFocus();
        } else if (tv_dof.getText().toString().equals("")) {
            tv_dof.setError("Date of Filing is Required");
            tv_dof.requestFocus();
        } else {
//            JSONArray group_acls = new JSONArray();
//            JSONArray client = new JSONArray();
//            JSONArray members = new JSONArray();
            MatterModel matterModel = new MatterModel();
            matterModel.setMatter_title(tv_matter_title.getText().toString());
            matterModel.setCase_number(tv_matter_num.getText().toString());
            matterModel.setCase_type(tv_case_type.getText().toString());
            matterModel.setDescription(tv_matter_description.getText().toString());
            matterModel.setDate_of_filing(tv_dof.getText().toString());
            matterModel.setCourt(tv_court.getText().toString());
            matterModel.setJudge(tv_judge.getText().toString());
            matterModel.setCase_priority(CASE_PRIORITY);
            matterModel.setStatus(STATUS);
            JSONArray jsonArray = new JSONArray();
            try {
                for (int i = 0; i < advocates_list.size(); i++) {
                    AdvocateModel advocateModel = advocates_list.get(i);
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("name", advocateModel.getAdvocate_name());
                    jsonObject.put("email", advocateModel.getEmail());
                    jsonObject.put("phone", advocateModel.getNumber());
                    jsonArray.put(jsonObject);

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            matterModel.setOpponent_advocate(jsonArray);
            if(matterArraylist.size()==0) {
                matterArraylist.add(matterModel);
            }else{
                matterArraylist.set(0,matterModel);
            }
            matter.loadGCT();
        }
    }


    private void loadAdvocateUI() {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            LayoutInflater inflater = getActivity().getLayoutInflater();
            View view = inflater.inflate(R.layout.add_opponent_advocate, null);
            TextInputEditText tv_advocate_name = view.findViewById(R.id.tv_advocate_name);
            TextInputEditText tv_advocate_email = view.findViewById(R.id.tv_advocate_email);
            TextInputEditText tv_advocate_phone = view.findViewById(R.id.tv_advocate_phone);
            AppCompatButton btn_cancel_tag = view.findViewById(R.id.btn_cancel_tag);
            AppCompatButton btn_save_tag = view.findViewById(R.id.btn_save_tag);
            final AlertDialog dialog = builder.create();
            btn_cancel_tag.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            btn_save_tag.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (tv_advocate_name.getText().toString().equals("")) {
                        tv_advocate_name.setError("Name is required");
                        tv_advocate_name.requestFocus();
                    } else if (tv_advocate_email.getText().toString().equals("")) {
                        tv_advocate_email.setError("Email is required");
                        tv_advocate_email.requestFocus();
                    } else if (!(tv_advocate_email.getText().toString().matches(Patterns.EMAIL_ADDRESS.toString()))) {
                        tv_advocate_email.setError("Please enter a valid");
                        tv_advocate_email.requestFocus();
                    } else if (tv_advocate_phone.getText().toString().equals("")) {
                        tv_advocate_phone.setError("Phone is required");
                        tv_advocate_phone.requestFocus();
                    } else if (!(tv_advocate_phone.getText().toString().matches(Patterns.PHONE.toString()))) {
                        tv_advocate_phone.setError("Please enter a valid phone number");
                        tv_advocate_phone.requestFocus();
                    } else {
                        AdvocateModel advocateModel = new AdvocateModel();
                        advocateModel.setAdvocate_name(tv_advocate_name.getText().toString());
                        advocateModel.setEmail(tv_advocate_email.getText().toString());
                        advocateModel.setNumber(tv_advocate_phone.getText().toString());
                        advocates_list.add(advocateModel);
                        dialog.dismiss();
                        loadOpponentsList();
                    }
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

    private void loadEditedData(String adv_name, String adv_email, String adv_phone, int position, View view_advocate, TextView tv_opponent_name) {
        AdvocateModel advocateModel = new AdvocateModel();
        advocateModel.setAdvocate_name(adv_name);
        advocateModel.setEmail(adv_email);
        advocateModel.setNumber(adv_phone);
        advocates_list.set(position, advocateModel);
        tv_opponent_name = view_advocate.findViewById(R.id.tv_opponent_name);
        tv_opponent_name.setText(advocateModel.getAdvocate_name());
    }

    private void loadOpponentsList() {
        ll_add_advocate.removeAllViews();
        for (int i = 0; i < advocates_list.size(); i++) {
            View view_opponents = LayoutInflater.from(getContext()).inflate(R.layout.edit_opponent_advocate, null);
            TextView tv_opponent_name = view_opponents.findViewById(R.id.tv_opponent_name);
            tv_opponent_name.setText(advocates_list.get(i).getAdvocate_name());
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
                            v = ll_add_advocate.getChildAt(position);
                            ll_add_advocate.removeView(v);
                            AdvocateModel advocateModel = advocates_list.get(position);
                            advocateModel.setAdvocate_name("");
                            advocateModel.setEmail("");
                            advocateModel.setNumber("");
                            advocates_list.set(position, advocateModel);
                            advocates_list.remove(position);
//                        add_tags_listing();
//                                    ll_added_tags.removeAllViews();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        AndroidUtils.showAlert(e.getMessage(), getContext());
                    }
                }
            });
            iv_edit_opponent.setTag(i);
            iv_edit_opponent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = 0;
                    if (view.getTag() instanceof Integer) {
                        position = (Integer) view.getTag();
                        view = ll_add_advocate.getChildAt(position);
//                        ll_add_advocate.addView(view);
                        AdvocateModel advocateModel = advocates_list.get(position);
                        EditAdvocateUI(advocateModel.getAdvocate_name(), advocateModel.getEmail(), advocateModel.getNumber(), position, tv_opponent_name, view);
//                        loadAdvocateUI();
//                        edit_tags(documentsModel1.getTag_type(), documentsModel1.getTag_name(), position, view, tv_tag_document_name);
                    }
                }
            });
            ll_add_advocate.addView(view_opponents);
        }
    }

    private void EditAdvocateUI(String advocate_name, String email, String number, int position, TextView tv_opponent_name, View view_advocate) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            LayoutInflater inflater = getActivity().getLayoutInflater();
            View view = inflater.inflate(R.layout.add_opponent_advocate, null);
            TextInputEditText tv_advocate_name = view.findViewById(R.id.tv_advocate_name);
            TextInputEditText tv_advocate_email = view.findViewById(R.id.tv_advocate_email);
            TextInputEditText tv_advocate_phone = view.findViewById(R.id.tv_advocate_phone);
            AppCompatButton btn_cancel_tag = view.findViewById(R.id.btn_cancel_tag);
            AppCompatButton btn_save_tag = view.findViewById(R.id.btn_save_tag);
            final AlertDialog dialog = builder.create();
            btn_cancel_tag.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            tv_advocate_name.setText(advocate_name);
            tv_advocate_email.setText(email);
            tv_advocate_phone.setText(number);
            btn_save_tag.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (tv_advocate_name.getText().toString().equals("")) {
                        tv_advocate_name.setError("Name is required");
                        tv_advocate_name.requestFocus();
                    } else if (tv_advocate_email.getText().toString().equals("")) {
                        tv_advocate_email.setError("Email is required");
                        tv_advocate_email.requestFocus();
                    } else if (!(tv_advocate_email.getText().toString().matches(Patterns.EMAIL_ADDRESS.toString()))) {
                        tv_advocate_email.setError("Please enter a valid");
                        tv_advocate_email.requestFocus();
                    } else if (tv_advocate_phone.getText().toString().equals("")) {
                        tv_advocate_phone.setError("Phone is required");
                        tv_advocate_phone.requestFocus();
                    } else if (!(tv_advocate_phone.getText().toString().matches(Patterns.PHONE.toString()))) {
                        tv_advocate_phone.setError("Please enter a valid phone number");
                        tv_advocate_phone.requestFocus();
                    } else {
                        dialog.dismiss();
                        loadEditedData(tv_advocate_name.getText().toString(), tv_advocate_email.getText().toString(), tv_advocate_phone.getText().toString(), position, view_advocate, tv_opponent_name);


//                        loadOpponentsList(advocates_list);
                    }
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

    private void loadActiveUI() {
        STATUS = "Active";
        tv_status_active.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.button_left_green_round_background));
        tv_status_pending.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.button_right_round_background));

    }

    private void loadPendingUI() {
        STATUS = "Pending";
        tv_status_active.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.button_left_round_background));
        tv_status_pending.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.button_right_green_round_background));

    }

    private void loadLowPriorityUI() {
        CASE_PRIORITY = "Low";
        tv_high_priority.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.button_left_round_background));
        tv_medium_priority.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.radiobutton_centre_background));
        tv_low_priority.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.button_right_green_round_background));

    }

    private void loadMediumPriorityUI() {
        CASE_PRIORITY = "Medium";
        tv_high_priority.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.button_left_round_background));
        tv_medium_priority.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.radiobutton_centre_green_background));
        tv_low_priority.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.button_right_round_background));
    }

    private void loadHighPriorityUI() {
        CASE_PRIORITY = "High";
        tv_high_priority.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.button_left_green_round_background));
        tv_medium_priority.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.radiobutton_centre_background));
        tv_low_priority.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.button_right_round_background));
    }

}
