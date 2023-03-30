package com.digicoffer.lauditor.TimeSheets;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.digicoffer.lauditor.Matter.ViewMatter;
import com.digicoffer.lauditor.R;
import com.digicoffer.lauditor.common.AndroidUtils;
import com.digicoffer.lauditor.common.Constants;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeSheets extends Fragment {
TextView tv_aggregated_ts,tv_my_ts,tv_ns_timesheet,tv_submitted,tv_week,tv_month;
LinearLayoutCompat ll_timesheet_type,ll_submitted_type;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.timesheet, container, false);
        ll_submitted_type = view.findViewById(R.id.ll_submitted_type);
        ll_timesheet_type = view.findViewById(R.id.ll_timesheet_type);
        tv_aggregated_ts  =view.findViewById(R.id.tv_aggregated_ts);
        tv_my_ts = view.findViewById(R.id.tv_my_ts);
        tv_ns_timesheet = view.findViewById(R.id.tv_ns_timesheet);
        tv_submitted = view.findViewById(R.id.tv_submitted);
        tv_week = view.findViewById(R.id.tv_week);
        tv_month = view.findViewById(R.id.tv_month);
        AndroidUtils.showToast(Constants.ROLE,getContext());
        if (Constants.ROLE.equals("SU")){
            ll_timesheet_type.setVisibility(View.VISIBLE);
        }else{
            ll_timesheet_type.setVisibility(View.GONE);
        }
        Calendar calendar = Calendar.getInstance();
        getWeekDateRange(calendar);
// Set the calendar to the current week's Monday
//        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

        // Calculate the first and last dates of the current week
//        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
//         firstDate = dateFormat.format(calendar.getTime());
//        calendar.add(Calendar.DATE, 4);
//       lastDate = dateFormat.format(calendar.getTime());

        // Set the TextView to display the first and last dates
        TextView tv_from_date_timesheet = view.findViewById(R.id.tv_from_date_timesheet);
        TextView tv_to_date_timesheet = view.findViewById(R.id.tv_to_date_timesheet);
        String updated_date = getWeekDateRange(calendar);
        String [] seperated = updated_date.split("-");
        tv_from_date_timesheet.setText(seperated[0]);
        tv_to_date_timesheet.setText(seperated[1]);

        // Set up the onClickListeners for the next and previous buttons
        ImageButton iv_next_week = view.findViewById(R.id.iv_next_week);
        ImageButton iv_previous_week = view.findViewById(R.id.iv_previous_week);
        loadFragment();
        iv_next_week.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
//                Calendar calendar = Calendar.getInstance();
                    // Increment the Calendar by one week

//                    calendar.add(Calendar.DATE, 7);
//
//
//                    // Update the TextView to display the new week's dates
//                    String firstDate_new = dateFormat.format(calendar.getTime());
//                    calendar.add(Calendar.DAY_OF_WEEK, 4);
//                    String lastDate_new = dateFormat.format(calendar.getTime());
//                    tv_from_date_timesheet.setText(firstDate_new);
//                    tv_to_date_timesheet.setText(lastDate_new);
                    calendar.add(Calendar.WEEK_OF_YEAR, 1);
                    String updated_date = getWeekDateRange(calendar);
                    String [] seperated = updated_date.split("-");


                    tv_from_date_timesheet.setText(seperated[0]);
                    tv_to_date_timesheet.setText(seperated[1]);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        iv_previous_week.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Calendar calendar = Calendar.getInstance();
                // Decrement the Calendar by one week
//                calendar.add(Calendar.DAY_OF_WEEK, -7);
//
//                // Update the TextView to display the new week's dates
//                String firstDate_new = dateFormat.format(calendar.getTime());
//                calendar.add(Calendar.DAY_OF_WEEK, 4);
//                String lastDate_new = dateFormat.format(calendar.getTime());
//                tv_from_date_timesheet.setText(firstDate_new);
//                tv_to_date_timesheet.setText(lastDate_new);
                calendar.add(Calendar.WEEK_OF_YEAR, -1);
                String updated_date = getWeekDateRange(calendar);
                String [] seperated = updated_date.split("-");
                tv_from_date_timesheet.setText(seperated[0]);
                tv_to_date_timesheet.setText(seperated[1]);
            }
        });
        return view;
    }

    private void loadFragment() {

        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        NonSubmittedTimesheets nonSubmittedTimesheets = new NonSubmittedTimesheets();
        ft.replace(R.id.child_container_timesheets,nonSubmittedTimesheets);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.addToBackStack(null);
        ft.commit();
    }

    private String getWeekDateRange(Calendar calendar) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        String startDate = format.format(calendar.getTime());
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
        String endDate = format.format(calendar.getTime());
        return startDate + " - " + endDate;
    }

}
