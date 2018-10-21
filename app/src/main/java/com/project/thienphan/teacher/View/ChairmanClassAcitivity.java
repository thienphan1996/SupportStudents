package com.project.thienphan.teacher.View;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.project.thienphan.supportstudent.R;
import com.project.thienphan.teacher.Adapter.ChairmanAdapter;
import com.project.thienphan.timesheet.Common.TimesheetPreferences;
import com.project.thienphan.timesheet.Database.TimesheetDatabase;
import com.project.thienphan.timesheet.Model.StudentInfomation;
import com.project.thienphan.timesheet.Model.TeacherInfo;
import com.project.thienphan.timesheet.Support.TimesheetProgressDialog;

import java.util.ArrayList;

public class ChairmanClassAcitivity extends AppCompatActivity {

    ArrayList<StudentInfomation> lstStudent;
    RecyclerView rcvListStudent;
    ChairmanAdapter chairmanAdapter;
    TimesheetPreferences timesheetPreferences;
    Gson gson;
    String classCode = "";
    DatabaseReference mydb;
    TimesheetProgressDialog dialog;
    TeacherInfo teacherInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chairman_class_acitivity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        addControls();
    }

    private void addControls() {
        mydb = TimesheetDatabase.getTimesheetDatabase();
        dialog = new TimesheetProgressDialog();
        timesheetPreferences = new TimesheetPreferences(getApplicationContext());
        gson = new Gson();
        rcvListStudent = findViewById(R.id.rcv_chairman);
        lstStudent = new ArrayList<>();
        chairmanAdapter = new ChairmanAdapter(lstStudent, getResources(), new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rcvListStudent.setLayoutManager(layoutManager);
        rcvListStudent.setAdapter(chairmanAdapter);
        GetTeacherInfo();
    }

    private void GetTeacherInfo() {
        String teacher = timesheetPreferences.get(getString(R.string.TEACHER_INFO),String.class);
        teacherInfo = gson.fromJson(teacher,TeacherInfo.class);
        if (teacherInfo != null){
            classCode = teacherInfo.getClassCode().toUpperCase();
            GetStudents();
        }
    }

    private void GetStudents() {
        dialog.show(getSupportFragmentManager(),"dialog");
        mydb.child(getString(R.string.CHILD_STUDENTINFO)).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot item : dataSnapshot.getChildren()){
                    StudentInfomation student = item.getValue(StudentInfomation.class);
                    if (student.getLopHoc().toUpperCase().equals(classCode)){
                        lstStudent.add(student);
                    }
                }
                chairmanAdapter.notifyDataSetChanged();
                dialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                dialog.dismiss();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.chairman_class_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_send_notification) {
            Intent intent = new Intent(getApplicationContext(),SendNofication.class);
            intent.putExtra(getString(R.string.SUBJECT_CODE),teacherInfo.getClassCode().toUpperCase());
            intent.putExtra(getString(R.string.SUBJECT_NAME),"Cố vấn học tập");
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
