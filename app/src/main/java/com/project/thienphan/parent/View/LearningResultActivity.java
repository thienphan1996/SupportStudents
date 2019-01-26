package com.project.thienphan.parent.View;

import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.project.thienphan.parent.Adapter.LearningResultAdapter;
import com.project.thienphan.parent.Adapter.PagerAdapter;
import com.project.thienphan.supportstudent.R;
import com.project.thienphan.timesheet.Common.TimesheetPreferences;
import com.project.thienphan.timesheet.Database.TimesheetDatabase;
import com.project.thienphan.timesheet.Model.LearningResult;
import com.project.thienphan.timesheet.Support.TimesheetProgressDialog;

import java.util.ArrayList;

public class LearningResultActivity extends AppCompatActivity {

    RecyclerView rcvLearningResult;
    ArrayList<LearningResult> lstLearningResult;
    LearningResultAdapter adapterLearningResult;
    String user = "";
    TimesheetPreferences timesheetPreferences;
    DatabaseReference mydb;
    TimesheetProgressDialog dialog;

    ViewPager pager;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learning_result);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        addControls();
    }

    private void addControls() {
        pager = findViewById(R.id.vpg_learning_result);
        tabLayout = findViewById(R.id.tab_learning_result);
        FragmentManager manager = getSupportFragmentManager();
        PagerAdapter adapter = new PagerAdapter(manager);
        pager.setAdapter(adapter);
        tabLayout.setupWithViewPager(pager);
        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(pager));
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
