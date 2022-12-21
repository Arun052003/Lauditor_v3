package com.digicoffer.lauditor.ClientRelationships;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.digicoffer.lauditor.R;
import com.digicoffer.lauditor.Webservice.AsyncTaskCompleteListener;
import com.digicoffer.lauditor.Webservice.HttpResultDo;

public class ClientRelationship extends Fragment implements AsyncTaskCompleteListener {

    private RadioGroup rv_add_relationships;
    RadioButton rb_add_relationship, rb_view_relationships;

    @Override
    public void onClick(View view) {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.client_relationships, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rv_add_relationships = view.findViewById(R.id.rgTask);
        rb_add_relationship = view.findViewById(R.id.add_relationship);
        rb_view_relationships = view.findViewById(R.id.view_relationship);
        rb_add_relationship.setTextColor(getContext().getResources().getColor(R.color.white));
        rb_view_relationships.setTextColor(getContext().getResources().getColor(R.color.black));
        rv_add_relationships.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.add_relationship:
                        rb_add_relationship.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.button_left_green_background));
                        rb_add_relationship.setTextColor(getContext().getResources().getColor(R.color.white));
                        rb_view_relationships.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.button_right_background));
                        rb_add_relationship.setTextColor(getContext().getResources().getColor(R.color.white));
                        rb_view_relationships.setTextColor(getContext().getResources().getColor(R.color.black));
                        break;
                    case R.id.view_relationship:

                        rb_add_relationship.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.button_left_background));
                        rb_view_relationships.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.button_right_green_count));
                        rb_add_relationship.setTextColor(getContext().getResources().getColor(R.color.black));
                        rb_view_relationships.setTextColor(getContext().getResources().getColor(R.color.white));
                        break;
                }
            }
        });

    }

    @Override
    public void onAsyncTaskComplete(HttpResultDo httpResult) {

    }
}
