package com.digicoffer.lauditor.Matter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.TextView;

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

    com.google.android.material.imageview.ShapeableImageView siv_matter_icon,siv_groups,siv_documents;
    private HorizontalScrollView scrollView;
    TextView tv_legal_matter,tv_general_matter;
    TextView tv_create,tv_view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.create_matter, container, false);
        siv_matter_icon = view.findViewById(R.id.siv_matter_icon);
        siv_groups = view.findViewById(R.id.siv_groups);
        siv_documents = view.findViewById(R.id.siv_documents);
        tv_legal_matter = view.findViewById(R.id.tv_legal_matter);
        tv_general_matter = view.findViewById(R.id.tv_general_matter);
        tv_create = view.findViewById(R.id.tv_create_matter);
        tv_view = view.findViewById(R.id.tv_view_matter);
//        siv_upload = view.findViewById(R.id.upload_icon);
//        siv_view = view.findViewById(R.id.view_icon);
        loadMatterInformation();
        siv_matter_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              loadMatterInformation();
//                FragmentManager childFragmentManager = getChildFragmentManager();
//                childFragmentManager.beginTransaction().add(R.id.child_container, childFragment).commit();
            }
        });
        siv_groups.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadGCT();
            }
        });
        siv_documents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadDocuments();
            }
        });
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

    private void loadDocuments() {
        siv_matter_icon.setImageDrawable(getContext().getResources().getDrawable(R.mipmap.single_document_icon));
        siv_groups.setImageDrawable(getContext().getResources().getDrawable(R.mipmap.groups_material_icon));
        siv_documents.setImageDrawable(getContext().getResources().getDrawable(R.mipmap.documents_copy_white));

        Fragment childFragment = new MatterDocuments();
        FragmentManager childFragmentManager = getChildFragmentManager();
        childFragmentManager.beginTransaction().add(R.id.child_container, childFragment).commit();

    }

    private void loadGCT() {
        siv_matter_icon.setImageDrawable(getContext().getResources().getDrawable(R.mipmap.single_document_icon));
        siv_groups.setImageDrawable(getContext().getResources().getDrawable(R.mipmap.groups_material_icon_white));
        siv_documents.setImageDrawable(getContext().getResources().getDrawable(R.mipmap.documents_copy));
       Fragment childFragment = new GCT();
        FragmentManager childFragmentManager = getChildFragmentManager();
        childFragmentManager.beginTransaction().add(R.id.child_container, childFragment).commit();
    }

    private void loadMatterInformation() {
        siv_matter_icon.setImageDrawable(getContext().getResources().getDrawable(R.mipmap.single_document_icon_white));
        siv_groups.setImageDrawable(getContext().getResources().getDrawable(R.mipmap.groups_material_icon));
        siv_documents.setImageDrawable(getContext().getResources().getDrawable(R.mipmap.documents_copy));
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        MatterInformation matterInformation = new MatterInformation();
        ft.replace(R.id.child_container,matterInformation);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.addToBackStack(null);
        ft.commit();
    }

}
