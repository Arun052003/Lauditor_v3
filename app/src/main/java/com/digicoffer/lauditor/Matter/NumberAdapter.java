package com.digicoffer.lauditor.Matter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.digicoffer.lauditor.R;

import java.util.List;

public class NumberAdapter extends BaseAdapter {
    private Context mContext;
    private List<String> mItems;

    public NumberAdapter(Context context, List<String> items) {
        mContext = context;
        mItems = items;
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public Object getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_number, parent, false);
        }

        TextView numberTextView = view.findViewById(R.id.number_text_view);
        numberTextView.setText(String.valueOf(position + 1));

        TextView itemTextView = view.findViewById(R.id.item_text_view);
        itemTextView.setText(mItems.get(position));

        return view;
    }
}