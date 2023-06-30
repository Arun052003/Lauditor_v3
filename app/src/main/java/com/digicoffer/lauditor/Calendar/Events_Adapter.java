package com.digicoffer.lauditor.Calendar;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.digicoffer.lauditor.Calendar.Models.Events_Do;
import com.digicoffer.lauditor.R;

import java.util.ArrayList;

public class Events_Adapter extends RecyclerView.Adapter<Events_Adapter.MyViewHolder> implements Filterable, View.OnClickListener {

    ArrayList<Events_Do> list_item = new ArrayList<Events_Do>();
    ArrayList<Events_Do> filtered_list = new ArrayList<Events_Do>();
    private Events_Adapter.EventListener context;
    Context mcontext;

    public Events_Adapter(ArrayList<Events_Do> events_list, EventListener mcontext, Context context) {
        this.list_item = events_list;
        this.filtered_list = events_list;
        this.context = mcontext;
        this.mcontext =context;
    }
    public interface EventListener {
        void onEvent(int layout, Fragment fragment);

        void view_events(String id,Events_Do events_do);
        void delete(String event_id,boolean recur);
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public Filter getFilter() {
        return null;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.events_recyler_list, parent, false);
        return new Events_Adapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull Events_Adapter.MyViewHolder holder, int position) {

        for (Events_Do event : filtered_list) {
            Log.d("Event List",event.getEvent_Name());
        }
        final Events_Do events_do = filtered_list.get(position);
        AndroidUtils.showToast(events_do.toString(),mcontext);

        final String from_ts = events_do.getEvent_start_time();
        Date event_date = AndroidUtils.stringToDateTimeDefault(from_ts, "yyyy-MM-dd'T'HH:mm:ss");

        final String converted_date  = AndroidUtils.getDateToString(event_date, "yyyy-MM-dd");
        final String converted_time = AndroidUtils.getDateToString(event_date, "HH:mm");
        final String to_ts = events_do.getEvent_end_time();
        Date end_date = AndroidUtils.stringToDateTimeDefault(to_ts,"yyyy-MM-dd'T'HH:mm:ss");
        final String converted_end_date = AndroidUtils.getDateToString(end_date, "HH:mm");

        holder.events_names.setText(converted_date);
        if (events_do.isOwner()){
            holder.ib_view_events.setVisibility(View.VISIBLE);
            holder.ll_rsvp.setVisibility(View.GONE);
        }else{
            holder.ib_view_events.setVisibility(View.GONE);
            holder.ll_rsvp.setVisibility(View.VISIBLE);
        }
        holder.event_title.setText(events_do.getTitle());
        holder.event_time.setText(converted_time+"-"+converted_end_date);
        holder.event_timezone.setText(events_do.getTimezone_location());
        holder.event_description.setText(events_do.getDescription());
        holder.tv_meeting_link.setText(events_do.getMeeting_link());
        holder.tv_phone_dialin.setText(events_do.getDialin());
        holder.tv_location.setText(events_do.getLocation());
        holder.bt_hide_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        final boolean recur = events_do.isRecurring();
        if(events_do.isAll_day()){
            holder.time.setText("All Day");
        }
        else {
            holder.time.setText(events_do.getConverted_Start_time() + " " + "To" + " " + events_do.getCOnverted_End_time());
        }
        holder.ib_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.view_events(events_do.getEvent_id(),events_do);
            }
        });
        holder.ib_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                context.delete(events_do.getEvent_id(),recur);
            }
        });

    }

    @Override
    public int getItemCount() {
        return filtered_list.size() ;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView events_names,tv_meeting_link,tv_phone_dialin,tv_location,tv_yes,tv_no,tv_maybe,event_title,event_time,event_description,event_timezone;
        TextView time;
        ImageButton ib_view_events,ib_delete_events;
        ImageButton ib_view;
        ImageButton ib_delete;
        AppCompatButton bt_hide_details;
        LinearLayout ll_notifications,ll_view_more,ll_rsvp,ll_documents,ll_team_members,ll_clients;
        public MyViewHolder(@NonNull View itemView) {

            super(itemView);
            events_names = (TextView)itemView.findViewById(R.id.events_names);
            time = (TextView)itemView.findViewById(R.id.time);
            event_title = (TextView)itemView.findViewById(R.id.event_title);
            ib_view_events = (ImageButton) itemView.findViewById(R.id.ib_view_events);
            tv_yes = (TextView) itemView.findViewById(R.id.tv_yes) ;
            tv_no = (TextView) itemView.findViewById(R.id.tv_no);
            tv_maybe = (TextView) itemView.findViewById(R.id.tv_maybe);
            event_time = (TextView) itemView.findViewById(R.id.event_time);
            event_timezone = (TextView) itemView.findViewById(R.id.event_timezone);
            ib_delete_events = (ImageButton)itemView.findViewById(R.id.ib_delete_events);
            event_description = (TextView) itemView.findViewById(R.id.event_description);
            tv_meeting_link = (TextView) itemView.findViewById(R.id.tv_meeting_link);
            tv_phone_dialin = (TextView) itemView.findViewById(R.id.tv_phone_dialin);
            tv_location = (TextView) itemView.findViewById(R.id.tv_location);
            bt_hide_details = (AppCompatButton) itemView.findViewById(R.id.bt_hide_details);
            ll_notifications = (LinearLayout) itemView.findViewById(R.id.ll_notifications);
            ll_view_more = (LinearLayout) itemView.findViewById(R.id.ll_view_more);
            ll_documents = (LinearLayout) itemView.findViewById(R.id.ll_documents);
            ll_team_members = (LinearLayout) itemView.findViewById(R.id.ll_team_members);
            ll_clients = (LinearLayout) itemView.findViewById(R.id.ll_clients);
            ll_rsvp = (LinearLayout) itemView.findViewById(R.id.ll_rsvp);
        }
    }
}
