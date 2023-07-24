package com.digicoffer.lauditor.Dashboard;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextClock;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.digicoffer.lauditor.Dashboard.DahboardModels.MydayModels.ClientChatModel;
import com.digicoffer.lauditor.Dashboard.DahboardModels.MydayModels.EmailModel;
import com.digicoffer.lauditor.Dashboard.DahboardModels.Item;
import com.digicoffer.lauditor.Dashboard.DahboardModels.MydayModels.MeetingModel;
import com.digicoffer.lauditor.Dashboard.DahboardModels.MydayModels.RelationshipRequestModel;
import com.digicoffer.lauditor.Dashboard.DahboardModels.MydayModels.TeamChatModel;
import com.digicoffer.lauditor.Dashboard.DahboardModels.PracticeHeadModels.PracticeModel;
import com.digicoffer.lauditor.MainActivity;
import com.digicoffer.lauditor.NewModel;
import com.digicoffer.lauditor.R;
import com.digicoffer.lauditor.common.AndroidUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Dashboard extends Fragment {

    private NewModel mViewModel;
    private TextClock tv_date_time,tv_time,tv_am_pm;
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private String date;
    private Button bt_MyDay,bt_KPI;
    MainActivity mainActivity;
    private static String KPI_DATA ="";
    private RecyclerView rv_myday;
    ArrayList<Item> itemArrayList = new ArrayList<>();
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint({"SimpleDateFormat", "MissingInflatedId"})
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

          View v = inflater.inflate(R.layout.dashboard_screen, container, false);

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        try {
            mViewModel = new ViewModelProvider(requireActivity()).get(NewModel.class);
            mViewModel.setData("Dashboard");
            calendar = Calendar.getInstance();
            dateFormat = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
            date = dateFormat.format(calendar.getTime());
            try {
//            mainActivity = (MainActivity) getActivity();
            } catch (Exception e) {
                e.printStackTrace();
            }
            bt_KPI = v.findViewById(R.id.bt_kpi);
            bt_MyDay = v.findViewById(R.id.bt_Myday);
            bt_MyDay.setBackgroundColor(getContext().getResources().getColor(R.color.grey_color_dark));
            Date date_new = AndroidUtils.stringToDateTimeDefault(date, "MMM dd YYYY HH:mm:ss aa");
            String time = AndroidUtils.getDateToString(date_new, "HH:mm");
            String date = AndroidUtils.getDateToString(date_new, "MMM dd YYYY");
            String am_pm = AndroidUtils.getDateToString(date_new, "aa");
            tv_am_pm = v.findViewById(R.id.am_pm);
            tv_time = v.findViewById(R.id.time);
            tv_date_time = v.findViewById(R.id.tv_date);
            rv_myday = v.findViewById(R.id.rv_myday);
            itemArrayList.clear();

            try {
                bt_MyDay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        itemArrayList.clear();
                        KPI_DATA = "";
                        bt_MyDay.setBackgroundColor(getContext().getResources().getColor(R.color.grey_color_dark));
                        bt_KPI.setBackgroundColor(getContext().getResources().getColor(R.color.white));
                        loadMyDayData();
                    }
                });
                bt_KPI.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        KPI_DATA = "";
                        itemArrayList.clear();
                        bt_MyDay.setBackgroundColor(getContext().getResources().getColor(R.color.white));
                        bt_KPI.setBackgroundColor(getContext().getResources().getColor(R.color.grey_color_dark));
                        load_TM_KPI_data();
                    }
                });
                loadMyDayData();
            } catch (Exception e) {
                Log.e("Error", "Info" + e.getMessage());
            }


        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    private void loadMyDayData(){
        MeetingModel meetingModel = new MeetingModel(5,"9:00am to 9:00pm","Meeting with Rajendra");
        itemArrayList.add(new Item(0,meetingModel));

        //RelationshipRequest
        RelationshipRequestModel requestModel = new RelationshipRequestModel("Jhon Smith","FEB 28 2022| 08:18 AM",3);
        itemArrayList.add(new Item(1,requestModel));

        //Client Chat
        ClientChatModel clientChatModel = new ClientChatModel("Mon 10:22 AM","Vijaynath Bhuvanagiri","We will not be meeting today.Let us chat tommarow",5);
        itemArrayList.add(new Item(2,clientChatModel));

        //Team Chat
        TeamChatModel teamChatModel = new TeamChatModel("Mon 10:22 AM","Raksha S","I'm having breakfast.Can we talk at 12?",5);
        itemArrayList.add(new Item(3,teamChatModel));

        //Email
        EmailModel emailModel = new EmailModel("Adobe Creative Cloud","New ways to spice up your creativity","Mon Feb 28, 11:37 PM(11 hours ago)",5);
        itemArrayList.add(new Item(4,emailModel));
        KPI_DATA = "MyDay";

        loadRecyclerview(KPI_DATA);
    }


    private void loadKPIdata(){
//        for (int i=0;i<=8;i++){
            PracticeModel practiceModel_tbh = new PracticeModel(800,72,0);
            itemArrayList.add(new Item(0,practiceModel_tbh));
//        }
        PracticeModel practiceModel_nbh = new PracticeModel(140,28,0);
        itemArrayList.add(new Item(1,practiceModel_nbh));
        PracticeModel practiceModel_ar = new PracticeModel(10000,0,0);
        itemArrayList.add(new Item(2,practiceModel_ar));
        PracticeModel practiceModel_abr = new PracticeModel(200,0,0);
        itemArrayList.add(new Item(3,practiceModel_abr));
        PracticeModel practiceModel_ts = new PracticeModel(30,3,27);
        itemArrayList.add(new Item(4,practiceModel_ts));
        PracticeModel practiceModel_matter = new PracticeModel(128,4,0);
        itemArrayList.add(new Item(5,practiceModel_matter));
        PracticeModel practiceModel_usl = new PracticeModel(4,0,0);
        itemArrayList.add(new Item(6,practiceModel_usl));
        PracticeModel practiceModel_cr = new PracticeModel(128,4,3);
        itemArrayList.add(new Item(7,practiceModel_cr));
        KPI_DATA = "PH_KPI";
        loadRecyclerview(KPI_DATA);

    }
 private void loadSuperKPIdata(){
     PracticeModel practiceModel_tbh = new PracticeModel(800,72,0);
     itemArrayList.add(new Item(0,practiceModel_tbh));
//        }
     PracticeModel practiceModel_nbh = new PracticeModel(140,28,0);
     itemArrayList.add(new Item(1,practiceModel_nbh));
     PracticeModel practiceModel_ar = new PracticeModel(10000,0,0);
     itemArrayList.add(new Item(2,practiceModel_ar));
     PracticeModel practiceModel_abr = new PracticeModel(200,0,0);
     itemArrayList.add(new Item(3,practiceModel_abr));
     PracticeModel practiceModel_ts = new PracticeModel(30,3,27);
     itemArrayList.add(new Item(4,practiceModel_ts));
     PracticeModel practiceModel_matter = new PracticeModel(128,4,0);
     itemArrayList.add(new Item(5,practiceModel_matter));
     PracticeModel practiceModel_usl = new PracticeModel(4,0,0);
     itemArrayList.add(new Item(6,practiceModel_usl));
     PracticeModel practiceModel_cr = new PracticeModel(128,4,3);
     itemArrayList.add(new Item(7,practiceModel_cr));
     KPI_DATA = "SU_KPI";
     loadRecyclerview( KPI_DATA);
 }
    private void load_TM_KPI_data(){
        PracticeModel practiceModel_tbh = new PracticeModel(800,72,0);
        itemArrayList.add(new Item(0,practiceModel_tbh));
        PracticeModel practiceModel_nbh = new PracticeModel(140,28,0);
        itemArrayList.add(new Item(1,practiceModel_nbh));
        PracticeModel practiceModel_ar = new PracticeModel(10000,0,0);
        itemArrayList.add(new Item(2,practiceModel_ar));
        PracticeModel practiceModel_abr = new PracticeModel(200,0,0);
        itemArrayList.add(new Item(3,practiceModel_abr));
        PracticeModel practiceModel_ts = new PracticeModel(30,3,27);
        itemArrayList.add(new Item(4,practiceModel_ts));
        KPI_DATA = "TM_KPI";
        loadRecyclerview(KPI_DATA);
    }
    private void load_Admin_data(){
        PracticeModel practiceModel_tbh = new PracticeModel(800,72,0);
        itemArrayList.add(new Item(0,practiceModel_tbh));
        PracticeModel practiceModel_nbh = new PracticeModel(140,28,0);
        itemArrayList.add(new Item(1,practiceModel_nbh));
        PracticeModel practiceModel_ar = new PracticeModel(10000,0,0);
        itemArrayList.add(new Item(2,practiceModel_ar));
        PracticeModel practiceModel_abr = new PracticeModel(200,0,0);
        itemArrayList.add(new Item(3,practiceModel_abr));
        PracticeModel practiceModel_ts = new PracticeModel(30,3,27);
        itemArrayList.add(new Item(4,practiceModel_ts));
        KPI_DATA = "Admin";
        loadRecyclerview(KPI_DATA);
    }

    private void setViewModelData(String data) {
        mViewModel.setData(data);
    }
    private void loadRecyclerview(String data) {
        //Meetings

        rv_myday.setLayoutManager(new LinearLayoutManager(getActivity()));

            rv_myday.setAdapter(new MyDayAdapter(itemArrayList,data));

        rv_myday.scrollToPosition(-1);
}
}
