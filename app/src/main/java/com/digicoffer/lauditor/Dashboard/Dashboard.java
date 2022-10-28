package com.digicoffer.lauditor.Dashboard;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextClock;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.digicoffer.lauditor.R;
import com.digicoffer.lauditor.common.AndroidUtils;

import org.pgpainless.key.selection.key.util.And;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Dashboard extends Fragment {


    private TextClock tv_date_time,tv_time,tv_am_pm;
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private String date;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("SimpleDateFormat")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dashboard_screen, container, false);
        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
        date = dateFormat.format(calendar.getTime());
        Date date_new = AndroidUtils.stringToDateTimeDefault(date, "MMM dd YYYY HH:mm:ss aa");
        String time = AndroidUtils.getDateToString(date_new,"HH:mm");
        String date = AndroidUtils.getDateToString(date_new,"MMM dd YYYY");
        String am_pm = AndroidUtils.getDateToString(date_new,"aa");
        tv_am_pm = v.findViewById(R.id.am_pm);
//        date = String.valueOf(calendar.getTime());

//        String time = dateFormat.format(calendar.getTimeInMillis());
        tv_time = v.findViewById(R.id.time);
//        Time time = new Time();
//        date = dateFormat.format(calendar.getTime());
        tv_date_time = v.findViewById(R.id.tv_date);
//        tv_time.setText(time);
//        tv_date_time.setText(date);
//        tv_am_pm.setText(am_pm);
        return v;
    }
}
