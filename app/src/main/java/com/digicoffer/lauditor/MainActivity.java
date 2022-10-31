package com.digicoffer.lauditor;

import static java.security.AccessController.getContext;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.digicoffer.lauditor.Dashboard.Dashboard;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    ExtendedFloatingActionButton mAddFab;
    Animation fabOpen, fabClose, rotateForward, rotateBackward;
    FloatingActionButton fab_relationships,fab_documents,fab_timesheet,fab_matter,fab_more;
    TextView tv_relations,tv_documents,tv_timesheet,tv_matter,tv_more;
    Boolean isAllFabsVisible;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAddFab = findViewById(R.id.fb_menu);
        fabOpen = AnimationUtils.loadAnimation
                (this,R.anim.fab_open);
        fabClose = AnimationUtils.loadAnimation
                (this,R.anim.fab_close);
        rotateForward = AnimationUtils.loadAnimation
                (this,R.anim.rotate_forward);
        rotateBackward = AnimationUtils.loadAnimation
                (this,R.anim.rotate_backward);
        fab_relationships = findViewById(R.id.fb_relationships);
        fab_relationships.setVisibility(View.GONE);
        fab_documents = findViewById(R.id.fb_documents);
        fab_documents.setVisibility(View.GONE);
        fab_matter = findViewById(R.id.fb_matter);
        fab_matter.setVisibility(View.GONE);
        fab_timesheet  = findViewById(R.id.fb_timesheets);
        fab_timesheet.setVisibility(View.GONE);
        fab_more = findViewById(R.id.fb_more);
        fab_more.setVisibility(View.GONE);
        tv_relations = findViewById(R.id.tv_relationships);
        tv_relations.setVisibility(View.GONE);
        tv_documents = findViewById(R.id.tv_documents);
        tv_documents.setVisibility(View.GONE);
        tv_timesheet = findViewById(R.id.tv_timesheet);
        tv_timesheet.setVisibility(View.GONE);
        tv_matter = findViewById(R.id.tv_matter);
        tv_matter.setVisibility(View.GONE);
        tv_more = findViewById(R.id.tv_more);
        tv_more.setVisibility(View.GONE);
        Fragment fragment = new Dashboard();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.id_framelayout, fragment);
        ft.commit();
        isAllFabsVisible = false;
        mAddFab.shrink();
        mAddFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    animateFab();

                } catch (Exception e) {
                    Log.e("Error","Error"+e.getMessage());
                    e.printStackTrace();
                }
            }
        });
//        Sup
    }
    private void animateFab(){

        LinearLayout.LayoutParams lp = new
                LinearLayout.LayoutParams(tv_relations.getWidth(),tv_relations.getHeight());
        lp.setMargins(0,0,165,15);
        if (!isAllFabsVisible) {
            mAddFab.startAnimation(rotateForward);

            mAddFab.setIcon(ContextCompat.getDrawable(MainActivity.this, R.drawable.up_arrow));
//                    mAddFab.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_full_sad));
            fab_relationships.setVisibility(View.VISIBLE);
            fab_relationships.startAnimation(fabOpen);
            fab_documents.setVisibility(View.VISIBLE);
            fab_documents.startAnimation(fabOpen);
            fab_matter.setVisibility(View.VISIBLE);
            fab_matter.startAnimation(fabOpen);
            fab_timesheet.setVisibility(View.VISIBLE);
            fab_timesheet.startAnimation(fabOpen);
            fab_more.setVisibility(View.VISIBLE);
            fab_more.startAnimation(fabOpen);
            tv_relations.setVisibility(View.VISIBLE);
//            tv_relations.setLayoutParams(lp);
//            tv_relations.startAnimation(fabOpen);
            tv_documents.setVisibility(View.VISIBLE);
//            tv_documents.startAnimation(fabOpen);
            tv_timesheet.setVisibility(View.VISIBLE);
//            tv_timesheet.startAnimation(fabOpen);
            tv_matter.setVisibility(View.VISIBLE);
//            tv_matter.startAnimation(fabOpen);
            tv_more.setVisibility(View.VISIBLE);
//            tv_more.startAnimation(fabOpen);
//                    mAddFab.extend();
            isAllFabsVisible = true;
        } else {
            mAddFab.startAnimation(rotateBackward);
            mAddFab.setIcon(ContextCompat.getDrawable(MainActivity.this, R.drawable.down_arrow));
            fab_relationships.setVisibility(View.GONE);
            fab_relationships.startAnimation(fabClose);
            fab_documents.setVisibility(View.GONE);
            fab_documents.startAnimation(fabClose);
            fab_matter.setVisibility(View.GONE);
            fab_matter.startAnimation(fabClose);
            fab_timesheet.setVisibility(View.GONE);
            fab_timesheet.startAnimation(fabClose);
            fab_more.setVisibility(View.GONE);
            fab_more.startAnimation(fabClose);
            tv_relations.setVisibility(View.GONE);
//            tv_relations.startAnimation(fabClose);
            tv_documents.setVisibility(View.GONE);
//            tv_documents.startAnimation(fabClose);
            tv_timesheet.setVisibility(View.GONE);
//            tv_timesheet.startAnimation(fabClose);
            tv_matter.setVisibility(View.GONE);
//            tv_matter.startAnimation(fabClose);
            tv_more.setVisibility(View.GONE);
//            tv_more.startAnimation(fabClose);
            mAddFab.shrink();
            isAllFabsVisible = false;
        }

    }
}