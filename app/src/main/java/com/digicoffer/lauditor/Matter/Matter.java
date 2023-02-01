package com.digicoffer.lauditor.Matter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.digicoffer.lauditor.R;
import com.digicoffer.lauditor.common.AndroidUtils;

import java.util.ArrayList;
import java.util.List;

public class Matter extends Fragment {

    com.google.android.material.imageview.ShapeableImageView siv_upload,siv_view;
    private HorizontalScrollView scrollView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.create_matter, container, false);
//        siv_upload = view.findViewById(R.id.upload_icon);
//        siv_view = view.findViewById(R.id.view_icon);
//        siv_upload.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                FragmentTransaction ft = getChildFragmentManager().beginTransaction();
//                ChildFrgament childFragment = new ChildFrgament();
//                ft.replace(R.id.child_container,childFragment);
//                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
//                ft.addToBackStack(null);
//                ft.commit();
////                FragmentManager childFragmentManager = getChildFragmentManager();
////                childFragmentManager.beginTransaction().add(R.id.child_container, childFragment).commit();
//            }
//        });
//        siv_view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Fragment childFragment = new ChildFrgamentTwo();
//                FragmentManager childFragmentManager = getChildFragmentManager();
//                childFragmentManager.beginTransaction().add(R.id.child_container, childFragment).commit();
//            }
//        });
//        try {
//            scrollView = view.findViewById(R.id.scroll_view);
//
//            List<String> items = new ArrayList<>();
//            items.add("Item 1");
//            items.add("Item 2");
//            items.add("Item 3");
//
//            NumberAdapter adapter = new NumberAdapter(getContext(), items);
//            for (int i = 0; i < adapter.getCount(); i++) {
//                View item = adapter.getView(i, null, scrollView);
//                scrollView.addView(item);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            AndroidUtils.showAlert(e.getMessage(),getContext());
//        }
        return view;
    }

}
