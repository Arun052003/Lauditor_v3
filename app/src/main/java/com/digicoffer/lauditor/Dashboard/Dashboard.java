package com.digicoffer.lauditor.Dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.digicoffer.lauditor.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Dashboard extends Fragment {

    private TextView tv_date_time;
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private String date;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dashboard_screen, container, false);
        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("MMM dd yyyy");
        date = dateFormat.format(calendar.getTime());
        tv_date_time = v.findViewById(R.id.tv_date);
        tv_date_time.setText(date);
        return v;
    }
}
