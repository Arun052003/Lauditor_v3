package com.digicoffer.lauditor.Dashboard;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextClock;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.digicoffer.lauditor.DahboardModels.ClientChatModel;
import com.digicoffer.lauditor.DahboardModels.EmailModel;
import com.digicoffer.lauditor.DahboardModels.Item;
import com.digicoffer.lauditor.DahboardModels.MeetingModel;
import com.digicoffer.lauditor.DahboardModels.RelationshipRequestModel;
import com.digicoffer.lauditor.DahboardModels.TeamChatModel;
import com.digicoffer.lauditor.MainActivity;
import com.digicoffer.lauditor.R;
import com.digicoffer.lauditor.common.AndroidUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.minidns.record.A;
import org.pgpainless.key.selection.key.util.And;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Dashboard extends Fragment {


    private TextClock tv_date_time,tv_time,tv_am_pm;
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private String date;
    MainActivity mainActivity;

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
        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
        date = dateFormat.format(calendar.getTime());
        try {
            mainActivity = (MainActivity) getActivity();
        } catch (Exception e) {
            e.printStackTrace();
        }


        Date date_new = AndroidUtils.stringToDateTimeDefault(date, "MMM dd YYYY HH:mm:ss aa");
        String time = AndroidUtils.getDateToString(date_new,"HH:mm");
        String date = AndroidUtils.getDateToString(date_new,"MMM dd YYYY");
        String am_pm = AndroidUtils.getDateToString(date_new,"aa");
        tv_am_pm = v.findViewById(R.id.am_pm);
        tv_time = v.findViewById(R.id.time);
        tv_date_time = v.findViewById(R.id.tv_date);
        rv_myday = v.findViewById(R.id.rv_myday);
        itemArrayList.clear();

        try {
            loadRecyclerview();
        } catch (Exception e) {
            Log.e("Error","Info"+e.getMessage());
        }

        return v;
    }

    private void loadRecyclerview() {
        //Meetings
        MeetingModel meetingModel = new MeetingModel(5,"9:00am to 9:00pm","Meeting with Tripati");
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
        rv_myday.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv_myday.setAdapter(new DashboardAdapter(itemArrayList));
        rv_myday.scrollToPosition(-1);
        rv_myday.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                try {
                    if (dy>0 && mainActivity.ll_bottom_menu.getVisibility() == View.VISIBLE){
                        mainActivity.ll_bottom_menu.setVisibility(View.GONE);
                    }else if (dy<0 && mainActivity.ll_bottom_menu.getVisibility() != View.VISIBLE){
                        mainActivity.ll_bottom_menu.setVisibility(View.VISIBLE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }
}
