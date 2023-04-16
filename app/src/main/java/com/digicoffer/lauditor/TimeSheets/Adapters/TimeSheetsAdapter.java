package com.digicoffer.lauditor.TimeSheets.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.digicoffer.lauditor.Groups.Adapters.SearchAdapter;
import com.digicoffer.lauditor.R;
import com.digicoffer.lauditor.TimeSheets.Models.EventsModel;
import com.digicoffer.lauditor.TimeSheets.Models.WeekModel;
import com.digicoffer.lauditor.common.AndroidUtils;

import org.minidns.record.A;
import org.w3c.dom.Text;

import java.util.ArrayList;

public class TimeSheetsAdapter extends RecyclerView.Adapter<TimeSheetsAdapter.MyViewHolder> {
    ArrayList<WeekModel> weeksList = new ArrayList<>();
    ArrayList<EventsModel> eventsList = new ArrayList<>();
    Context context;

    public TimeSheetsAdapter(ArrayList<WeekModel>weeksList,ArrayList<EventsModel> eventsModels,Context cContext) {
        this.weeksList = weeksList;
        this.eventsList = eventsModels;
        this.context = cContext;
    }

    @NonNull
    @Override


    public TimeSheetsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.timesheets_recyclerview,parent,false);
        return new TimeSheetsAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TimeSheetsAdapter.MyViewHolder holder, int position) {
            WeekModel weekModel = weeksList.get(position);
            holder.tv_date.setText(weekModel.getValue());
        AndroidUtils.showAlert(eventsList.toString(),context);
        holder.rv_time_sheets.setLayoutManager(new GridLayoutManager(context,1));
        WeeklyTSAdapter weeklyTSAdapter= new WeeklyTSAdapter(eventsList);
        holder.rv_time_sheets.setAdapter(weeklyTSAdapter);
        holder.rv_time_sheets.setHasFixedSize(true);
    }

    @Override
    public int getItemCount() {
        return weeksList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_date;
        RecyclerView rv_time_sheets;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_date = itemView.findViewById(R.id.tv_date);
            rv_time_sheets = itemView.findViewById(R.id.rv_time_sheets);
        }
    }
}
