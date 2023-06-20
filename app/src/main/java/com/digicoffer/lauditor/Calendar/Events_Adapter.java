package com.digicoffer.lauditor.Calendar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.digicoffer.lauditor.Calendar.Models.Events_Do;
import com.digicoffer.lauditor.R;
import com.digicoffer.lauditor.common.AndroidUtils;

import java.util.ArrayList;
import java.util.Date;

public class Events_Adapter extends RecyclerView.Adapter<Events_Adapter.MyViewHolder> implements Filterable, View.OnClickListener {

    ArrayList<Events_Do> list_item = new ArrayList<Events_Do>();
    ArrayList<Events_Do> filtered_list = new ArrayList<Events_Do>();
    private Events_Adapter.EventListener context;

    public Events_Adapter(ArrayList<Events_Do> events_list, EventListener mcontext) {
        this.list_item = events_list;
        this.filtered_list = events_list;
        this.context = mcontext;
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
        final Events_Do events_do = filtered_list.get(position);
        holder.event_name.setText(events_do.getEvent_Name());
        final boolean recur = events_do.isRecurring();
        final String from_ts = events_do.getEvent_start_time();
        Date event_date = AndroidUtils.stringToDateTimeDefault(from_ts, "yyyy-MM-dd'T'HH:mm:ss");
        final String converted_date  = AndroidUtils.getDateToString(event_date, "yyyy-MM-dd");
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
        TextView event_name;
        TextView time;
        ImageButton ib_edit;
        ImageButton ib_view;
        ImageButton ib_delete;
        public MyViewHolder(@NonNull View itemView) {

            super(itemView);
            event_name = (TextView)itemView.findViewById(R.id.events_names);
            time = (TextView)itemView.findViewById(R.id.time);
            ib_view = (ImageButton) itemView.findViewById(R.id.ib_view_events);

            ib_delete = (ImageButton)itemView.findViewById(R.id.ib_delete_events);
        }
    }
}
