package com.digicoffer.lauditor.TimeSheets;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.digicoffer.lauditor.R;
import com.digicoffer.lauditor.TimeSheets.Models.DateModel;
import com.digicoffer.lauditor.TimeSheets.Models.WeekDateInfo;
import com.digicoffer.lauditor.common.AndroidUtils;
import com.digicoffer.lauditor.common.Constants;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TimeSheets extends Fragment {
    TextView tv_aggregated_ts, tv_my_ts, tv_ns_timesheet, tv_submitted, tv_week, tv_month;
    LinearLayoutCompat ll_timesheet_type, ll_submitted_type;
    private static final int DIRECTION_PREVIOUS = -1;
    private static final int DIRECTION_NEXT = 1;
    private ArrayList<DateModel> datesList = new ArrayList<>();
    //    private ArrayAdapter<DateModel> weekAdapter = new ArrayAdapter<DateModel>(getContext(), android.R.layout.simple_spinner_item, datesList);
    Calendar calendar = Calendar.getInstance();
    WeekDateInfo weekDateInfo;

    //    getWeekDateRange(calendar);
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.timesheet, container, false);
        ll_submitted_type = view.findViewById(R.id.ll_submitted_type);
        ll_timesheet_type = view.findViewById(R.id.ll_timesheet_type);
        tv_aggregated_ts = view.findViewById(R.id.tv_aggregated_ts);
        tv_my_ts = view.findViewById(R.id.tv_my_ts);
        tv_ns_timesheet = view.findViewById(R.id.tv_ns_timesheet);
        tv_submitted = view.findViewById(R.id.tv_submitted);
        tv_week = view.findViewById(R.id.tv_week);
        tv_month = view.findViewById(R.id.tv_month);
        AndroidUtils.showToast(Constants.ROLE, getContext());
        if (Constants.ROLE.equals("SU")) {
            ll_timesheet_type.setVisibility(View.VISIBLE);
        } else {
            ll_timesheet_type.setVisibility(View.GONE);
        }


// Set the calendar to the current week's Monday
//        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

        // Calculate the first and last dates of the current week
//        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
//         firstDate = dateFormat.format(calendar.getTime());
//        calendar.add(Calendar.DATE, 4);
//       lastDate = dateFormat.format(calendar.getTime());

        // Set the TextView
        // to display the first and last dates
        weekDateInfo = getWeekDateRange(calendar);
        TextView tv_from_date_timesheet = view.findViewById(R.id.tv_from_date_timesheet);
        TextView tv_to_date_timesheet = view.findViewById(R.id.tv_to_date_timesheet);
//        String updated_date = getWeekDateRange(calendar);
//        String [] seperated = updated_date.split("-");
//        tv_from_date_timesheet.setText(seperated[0]);
//        tv_to_date_timesheet.setText(seperated[1]);
        if (weekDateInfo != null && weekDateInfo.getWeekDates() != null && !weekDateInfo.getWeekDates().isEmpty()) {
            tv_from_date_timesheet.setText(weekDateInfo.getWeekDates().get(0));
            tv_to_date_timesheet.setText(weekDateInfo.getWeekDates().get(weekDateInfo.getWeekDates().size() - 1));
        }
        String s = "";
        // Set up the onClickListeners for the next and previous buttons
        ImageButton iv_next_week = view.findViewById(R.id.iv_next_week);
        ImageButton iv_previous_week = view.findViewById(R.id.iv_previous_week);
        loadFragment(s);
        tv_aggregated_ts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadAggregatedTimesheets();
            }
        });
        tv_my_ts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadMyTimeSheets();
            }
        });
        tv_submitted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadSubmittedTimesheets();
            }
        });
        tv_ns_timesheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadNsTimesheets();
            }
        });
        tv_week.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadWeek();
            }
        });
        tv_month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadMonth();
            }
        });
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
//                    String updated_date = getWeekDateRange(calendar);
//                    String [] seperated = updated_date.split("-");
//
                    weekDateInfo = getWeekDateRange(calendar);
//                    tv_from_date_timesheet.setText(seperated[0]);
//                    tv_to_date_timesheet.setText(seperated[1]);
                    if (weekDateInfo != null && weekDateInfo.getWeekDates() != null && !weekDateInfo.getWeekDates().isEmpty()) {
                        tv_from_date_timesheet.setText(weekDateInfo.getWeekDates().get(0));
                        tv_to_date_timesheet.setText(weekDateInfo.getWeekDates().get(weekDateInfo.getWeekDates().size() - 1));
                    }
                    loadFragment(tv_from_date_timesheet.getText().toString());
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
//                String updated_date = getWeekDateRange(calendar);
//                String [] seperated = updated_date.split("-");
//                tv_from_date_timesheet.setText(seperated[0]);
//                tv_to_date_timesheet.setText(seperated[1]);
//                loadFragment(seperated[0]);
                weekDateInfo = getWeekDateRange(calendar);
                if (weekDateInfo != null && weekDateInfo.getWeekDates() != null && !weekDateInfo.getWeekDates().isEmpty()) {
                    tv_from_date_timesheet.setText(weekDateInfo.getWeekDates().get(0));
                    tv_to_date_timesheet.setText(weekDateInfo.getWeekDates().get(weekDateInfo.getWeekDates().size() - 1));
                }
                loadFragment(tv_from_date_timesheet.getText().toString());

            }
        });
        return view;
    }

    private void loadAggregatedTimesheets() {
        tv_aggregated_ts.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.button_left_green_background));
        tv_my_ts.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.button_right_background));

    }

    private void loadMyTimeSheets() {
        tv_aggregated_ts.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.button_left_background));
        tv_my_ts.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.button_right_green_count));

    }

    private void loadNsTimesheets() {
        tv_ns_timesheet.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.button_left_green_background));
        tv_submitted.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.button_right_background));

    }

    private void loadWeek() {
        tv_week.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.button_left_green_background));
        tv_month.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.button_right_background));

    }

    private void loadMonth() {
        tv_week.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.button_left_background));
        tv_month.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.button_right_green_count));

    }

    private void loadSubmittedTimesheets() {
        tv_submitted.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.button_right_green_count));
        tv_ns_timesheet.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.button_left_background));

    }

    private void loadFragment(String s) {
        loadAggregatedTimesheets();
        loadNsTimesheets();
        Bundle bundle = new Bundle();
        bundle.putString("date", s);
//        AndroidUtils.showToast(s,getContext());
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();

        NonSubmittedTimesheets nonSubmittedTimesheets = new NonSubmittedTimesheets();
        nonSubmittedTimesheets.setArguments(bundle);
        ft.replace(R.id.child_container_timesheets, nonSubmittedTimesheets);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.addToBackStack(null);
        ft.commit();
    }

    //    private String getWeekDateRange(Calendar calendar) {
////        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
////        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
////        String startDate = format.format(calendar.getTime());
////
////        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
//////        calendar.add(Calendar.DATE, 7);
////        calendar.add(Calendar.WEEK_OF_YEAR, 1);
////        String endDate = format.format(calendar.getTime());
////        return startDate + " - " + endDate;
//        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
//
//        // Set the calendar to the current week's Monday
//        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
//        int firstDayOfWeek = calendar.getFirstDayOfWeek();
//        calendar.add(Calendar.DATE, firstDayOfWeek - dayOfWeek);
//
//        // Calculate the start and end dates of the week
//        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
//        String startDate = format.format(calendar.getTime());
//
//        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
//        calendar.add(Calendar.WEEK_OF_YEAR, 1); // Add a week to get the next Sunday
//        String endDate = format.format(calendar.getTime());
//        return startDate + " - " + endDate;
//    }
//private String getWeekDateRange(Calendar calendar) {
//    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
//
//    // Calculate the current week's Monday
//    while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
//        calendar.add(Calendar.DATE, -1);
//    }
//    String startDate = format.format(calendar.getTime());
//
//    // Calculate the current week's Sunday
//    calendar.add(Calendar.DATE, 6);
//    String endDate = format.format(calendar.getTime());
//
//    return startDate + " - " + endDate;
//}
    private WeekDateInfo getWeekDateRange(Calendar calendar) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

        while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
            calendar.add(Calendar.DATE, -1);
        }
        String startDate = format.format(calendar.getTime());

        ArrayList<String> weekDates = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            String date = format.format(calendar.getTime());
            weekDates.add(date);
            calendar.add(Calendar.DATE, 1);
        }
        calendar.add(Calendar.DATE, -1);
        String endDate = format.format(calendar.getTime());

        WeekDateInfo weekDateInfo = new WeekDateInfo(startDate + " - " + endDate, weekDates);
        return weekDateInfo;
    }

    private void loadWeekDates(int direction) {
        // Get the first date of the current week
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

        // Adjust the calendar object based on the direction parameter
        if (direction == DIRECTION_PREVIOUS) {
            calendar.add(Calendar.DAY_OF_YEAR, -7); // go back one week
        } else if (direction == DIRECTION_NEXT) {
            calendar.add(Calendar.DAY_OF_YEAR, 7); // go forward one week
        }

        // Create a DateFormat object to format the dates as strings
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        // Clear the current datesList
        datesList.clear();

        // Add the dates of the week to the datesList (Monday to Friday only)
        for (int i = 0; i < 5; i++) {
            Date date = calendar.getTime();
            String dayOfWeek = dateFormat.format(date);
//            DateModel dateModel = new DateModel()
            if (i != 0 && i != 6) {
                // Exclude Saturday and Sunday

                DateModel dateModel = new DateModel(dayOfWeek);
                datesList.add(dateModel);
            }
            calendar.add(Calendar.DAY_OF_YEAR, 1);
        }
        // Update the spinner adapter
//        weekAdapter.notifyDataSetChanged();
    }
}
