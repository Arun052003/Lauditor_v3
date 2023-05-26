package com.digicoffer.lauditor.Calendar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.digicoffer.lauditor.R;
import com.digicoffer.lauditor.TimeSheets.NonSubmittedTimesheets;
import com.digicoffer.lauditor.Webservice.AsyncTaskCompleteListener;
import com.digicoffer.lauditor.Webservice.HttpResultDo;
import com.tuyenmonkey.mkloader.model.Line;

public class Calendar extends Fragment implements AsyncTaskCompleteListener,View.OnClickListener {
    LinearLayoutCompat ll_view_type;
    TextView tv_create_event,tv_view_calendar,tv_day_view,tv_month_view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
     View view = inflater.inflate(R.layout.calendar,container,false);
     return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ll_view_type= view.findViewById(R.id.ll_view_type);
        tv_create_event = view.findViewById(R.id.tv_create_event);
        tv_view_calendar = view.findViewById(R.id.tv_view_calendar);
        tv_day_view = view.findViewById(R.id.tv_day_view);
        tv_month_view = view.findViewById(R.id.tv_month_view);
        loadView();
        tv_view_calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadView();
            }
        });
        tv_create_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadCreateEvent();
            }
        });
        tv_view_calendar.setOnClickListener(this);
        tv_create_event.setOnClickListener(this);
        super.onViewCreated(view, savedInstanceState);
    }

    private void loadView() {
        tv_view_calendar.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.button_right_green_count));
        tv_create_event.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.button_left_background));
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        CreateEvent nonSubmittedTimesheets = new CreateEvent();
        ft.replace(R.id.child_container_timesheets, nonSubmittedTimesheets);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.addToBackStack(null);
        ft.commit();

    }
    private void loadCreateEvent(){
        tv_view_calendar.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.button_right_background));
        tv_create_event.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.button_left_green_background));

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_view_calendar:
                loadView();
                break;
            case R.id.tv_create_event:
                loadCreateEvent();
                break;
        }
    }

    @Override
    public void onAsyncTaskComplete(HttpResultDo httpResult) {

    }
}