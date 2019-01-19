package com.project.thienphan.parent.View;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.project.thienphan.parent.Adapter.LearningResultAdapter;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learning_result);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        addControls();
        addEvents();
    }

    private void addEvents() {

    }

    private void addControls() {
        rcvLearningResult = findViewById(R.id.rcv_learning_result);
        lstLearningResult = new ArrayList<>();
        adapterLearningResult = new LearningResultAdapter(lstLearningResult);
        timesheetPreferences = new TimesheetPreferences(this);
        dialog = new TimesheetProgressDialog();
        mydb = TimesheetDatabase.getTimesheetDatabase();
        user = timesheetPreferences.get(getString(R.string.USER),String.class);
        if(!user.isEmpty() && user.length() > 2 && user.substring(0,2).toLowerCase().equals("ph")){
            user = user.substring(2,user.length());
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rcvLearningResult.setLayoutManager(layoutManager);
        rcvLearningResult.setAdapter(adapterLearningResult);
        GetData();
    }

    private void GetData() {
        this.dialog.show(getSupportFragmentManager(),"dialog");
        this.mydb.child(getString(R.string.CHILD_LEARNING_RESULT)).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot child : dataSnapshot.getChildren()){
                    LearningResult item = child.getValue(LearningResult.class);
                    if (item != null){
                        lstLearningResult.add(item);
                    }
                }
                adapterLearningResult.notifyDataSetChanged();
                dialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
