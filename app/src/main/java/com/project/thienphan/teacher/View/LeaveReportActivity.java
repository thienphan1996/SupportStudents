package com.project.thienphan.teacher.View;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.project.thienphan.supportstudent.R;
import com.project.thienphan.teacher.Adapter.ReportAdapter;
import com.project.thienphan.timesheet.Common.TimesheetPreferences;
import com.project.thienphan.timesheet.Database.TimesheetDatabase;
import com.project.thienphan.timesheet.Model.LeaveStudentModel;
import com.project.thienphan.timesheet.Model.StudentInfomation;
import com.project.thienphan.timesheet.Support.TimesheetProgressDialog;

import java.util.ArrayList;
import java.util.Arrays;

public class LeaveReportActivity extends AppCompatActivity {

    TimesheetPreferences timesheetPreferences;
    ArrayList<LeaveStudentModel> lstLeaveStudent;
    RecyclerView rcvLeaveReport;
    ReportAdapter reportAdapter;
    String subject,account;
    DatabaseReference mydb;
    TimesheetProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_report);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        addControls();
    }

    private void addControls() {
        timesheetPreferences = new TimesheetPreferences(getApplicationContext());
        account = timesheetPreferences.get(getString(R.string.SAVE_PASSWORD),String.class);
        dialog = new TimesheetProgressDialog();
        mydb = TimesheetDatabase.getTimesheetDatabase();
        Intent intent = getIntent();
        if (intent != null){
            subject = intent.getStringExtra(getString(R.string.SUBJECT_CODE));
        }
        lstLeaveStudent = new ArrayList<>();
        rcvLeaveReport = findViewById(R.id.rcv_leave_report);
        reportAdapter = new ReportAdapter(lstLeaveStudent);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rcvLeaveReport.setLayoutManager(layoutManager);
        rcvLeaveReport.setAdapter(reportAdapter);
        GetLeaveStudent();
    }

    private void GetLeaveStudent() {
        if (account != null && subject != null){
            dialog.show(getSupportFragmentManager(),"dialog");
            this.mydb.child(getString(R.string.CHILD_LEAVESTUDENT)).child(account.toUpperCase()).child(subject).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot item : dataSnapshot.getChildren()){
                        String day = item.getKey();
                        String values = item.getValue(String.class);
                        if (values != null){
                            String[] students = values.substring(1,values.length()-1).split(",");
                            for (String stdCode : students){
                                LeaveStudentModel model = new LeaveStudentModel();
                                model.setLeaveDay(day);
                                model.setStudentCode(stdCode);
                                model.setLeaveDayTotal(1);
                                if (!lstLeaveStudent.contains(model)){
                                    lstLeaveStudent.add(model);
                                }
                                else {
                                    for (LeaveStudentModel content : lstLeaveStudent){
                                        if (content.getStudentCode().equals(stdCode)){
                                            content.setLeaveDayTotal(content.getLeaveDayTotal()+1);
                                            continue;
                                        }
                                    }
                                }
                            }
                        }
                    }
                    GetStudentDetails();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    private void GetStudentDetails() {
        if (lstLeaveStudent != null){
            this.mydb.child(getString(R.string.CHILD_STUDENTINFO)).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot item : dataSnapshot.getChildren()){
                        String stdCode = item.getKey();
                        for (LeaveStudentModel record : lstLeaveStudent){
                            if (record.getStudentCode().trim().toLowerCase().equals(stdCode.trim().toLowerCase())){
                                StudentInfomation student = item.getValue(StudentInfomation.class);
                                record.setStudentName(student.getHoTen());
                                continue;
                            }
                        }
                    }
                    reportAdapter.notifyDataSetChanged();
                    dialog.dismiss();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    dialog.dismiss();
                }
            });
        }
    }
}
