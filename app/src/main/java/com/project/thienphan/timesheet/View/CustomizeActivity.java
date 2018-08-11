package com.project.thienphan.timesheet.View;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.project.thienphan.supportstudent.R;
import com.project.thienphan.timesheet.Adapter.TimesheetAdapter;
import com.project.thienphan.timesheet.Database.TimesheetDatabase;
import com.project.thienphan.timesheet.Model.TimesheetItem;
import com.rilixtech.materialfancybutton.MaterialFancyButton;

import java.util.ArrayList;

public class CustomizeActivity extends AppCompatActivity {

    RecyclerView rcvCustomTimesheet;
    TimesheetAdapter adapterRecyclerView;
    ArrayList<TimesheetItem> listRecyclerView;
    MaterialFancyButton btnDelete;

    DatabaseReference mydb = TimesheetDatabase.getTimesheetDatabase();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customize);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setupView();
        setupData();
        setupEvents();
    }

    private void setupEvents() {
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void setupData() {
        this.mydb.child(getString(R.string.CHILD_TIMESHEET)).child("thienphan").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()){
                    TimesheetItem item = data.getValue(TimesheetItem.class);
                    listRecyclerView.add(item);
                }
                adapterRecyclerView.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        adapterRecyclerView.notifyDataSetChanged();
    }

    private void setupView() {
        listRecyclerView = new ArrayList<>();
        rcvCustomTimesheet = findViewById(R.id.rcv_custom_ts);
        adapterRecyclerView = new TimesheetAdapter(listRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rcvCustomTimesheet.setLayoutManager(layoutManager);
        rcvCustomTimesheet.setAdapter(adapterRecyclerView);
        btnDelete = findViewById(R.id.btn_create_ts);
        btnDelete.setEnabled(false);
    }
}
