package com.project.thienphan.teacher.View;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.project.thienphan.supportstudent.R;
import com.project.thienphan.teacher.Adapter.CheckStudentAdapter;
import com.project.thienphan.timesheet.Common.TimesheetPreferences;
import com.project.thienphan.timesheet.Database.TimesheetDatabase;
import com.project.thienphan.timesheet.Model.StudentInfomation;
import com.project.thienphan.timesheet.Support.InfoDialog;
import com.project.thienphan.timesheet.Support.TimesheetProgressDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class CheckStudentActivity extends AppCompatActivity {

    String students,subject;
    DatabaseReference mydb;
    ArrayList<StudentInfomation> lstStudent;
    RecyclerView rcvCheckStudent;
    CheckStudentAdapter checkStudentAdapter;
    TimesheetProgressDialog dialog;
    ArrayList<String> lstCheckLeave;
    TimesheetPreferences timesheetPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_student);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        addControls();
    }

    private void addControls() {
        mydb = TimesheetDatabase.getTimesheetDatabase();
        lstStudent = new ArrayList<>();
        lstCheckLeave = new ArrayList<>();
        rcvCheckStudent = findViewById(R.id.rcv_check_student);
        dialog = new TimesheetProgressDialog();
        timesheetPreferences = new TimesheetPreferences(getApplicationContext());
        checkStudentAdapter = new CheckStudentAdapter(getResources(),lstStudent, new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                lstStudent.get(i).setCheck(!lstStudent.get(i).isCheck());
                if (lstStudent.get(i).isCheck()){
                    lstCheckLeave.add(lstStudent.get(i).getMaSV());
                }
                checkStudentAdapter.notifyDataSetChanged();
            }
        });
        Intent intent = getIntent();
        if (intent != null){
            students = intent.getStringExtra(getString(R.string.LIST_STUDENT));
            subject = intent.getStringExtra(getString(R.string.SUBJECT_CODE));
            GetListStudents();
        }

        rcvCheckStudent.setLayoutManager(new GridLayoutManager(this, 3));
        rcvCheckStudent.setAdapter(checkStudentAdapter);
    }

    private void GetListStudents() {
        if (!students.isEmpty()){
            dialog.show(getSupportFragmentManager(),"dialog");
            final String[] list = students.split(",");
            this.mydb.child(getString(R.string.CHILD_STUDENTINFO)).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot item : dataSnapshot.getChildren()){
                        for (String key : list){
                            if (key.equals(item.getKey())){
                                StudentInfomation std = item.getValue(StudentInfomation.class);
                                lstStudent.add(std);
                            }
                        }
                    }
                    checkStudentAdapter.notifyDataSetChanged();
                    dialog.dismiss();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    dialog.dismiss();
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.check_student, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_submit) {
            if (lstCheckLeave != null){
                SubmitLeaveStudent();
            }
        }
        if (id == R.id.menu_report) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void SubmitLeaveStudent() {
        String account = timesheetPreferences.get(getString(R.string.SAVE_PASSWORD),String.class);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Calendar calendar = Calendar.getInstance();
        String time = sdf.format(calendar.getTime());
        String students = this.lstCheckLeave.toString();
        if (!account.isEmpty() && !subject.isEmpty()){
            this.mydb.child(getString(R.string.CHILD_LEAVESTUDENT)).child(account).child(subject).child(time).setValue(students);
        }
    }
}
