package com.project.thienphan.timesheet.View;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.project.thienphan.supportstudent.R;
import com.project.thienphan.timesheet.Adapter.AddTimesheetAdapter;
import com.project.thienphan.timesheet.Common.TimesheetPreferences;
import com.project.thienphan.timesheet.Database.TimesheetDatabase;
import com.project.thienphan.timesheet.Model.TimesheetItem;
import com.project.thienphan.timesheet.Notification.TimesheetReceiver;
import com.project.thienphan.timesheet.Support.InfoDialog;
import com.rilixtech.materialfancybutton.MaterialFancyButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class CreateTimesheet extends AppCompatActivity {

    private DatabaseReference mydb;

    private ArrayList<String> subjectList;
    private Spinner mySpinner;
    private ArrayList<String> spinnerList;
    private ArrayAdapter<String> adapterSpinner;

    private RecyclerView rcvCreateTimesheet;
    private AddTimesheetAdapter adapterRecyclerview;
    ArrayList<TimesheetItem> recyclerviewList;


    private TimesheetPreferences timesheetPreferences;
    private Gson gson;
    private int currentPosition = 0;

    private MaterialFancyButton btnCreateTimesheet;
    LinearLayout lnlAddtsProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_timesheet);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        
        this.mydb = TimesheetDatabase.getTimesheetDatabase();
        SetupView();
        SetupData();
        addEvents();
    }

    private void SetupData() {
        subjectList = new ArrayList<>();
        spinnerList = new ArrayList<>();
        timesheetPreferences = new TimesheetPreferences(this);
        gson = new Gson();
        spinnerList.add(this.getString(R.string.select_subject));

        adapterSpinner = new ArrayAdapter<String>(CreateTimesheet.this,android.R.layout.simple_list_item_1,spinnerList);
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        mySpinner.setAdapter(adapterSpinner);

        recyclerviewList = new ArrayList<>();
        adapterRecyclerview = new AddTimesheetAdapter(recyclerviewList, new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                btnCreateTimesheet.setEnabled(true);
                currentPosition = i;
                adapterRecyclerview.notifyDataSetChanged();
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rcvCreateTimesheet.setLayoutManager(layoutManager);
        rcvCreateTimesheet.setAdapter(adapterRecyclerview);
    }

    private void SetupView() {
        mySpinner = findViewById(R.id.spn_create_ts_subject);
        btnCreateTimesheet = findViewById(R.id.btn_create_ts);
        btnCreateTimesheet.setEnabled(false);
        rcvCreateTimesheet = findViewById(R.id.rcv_create_timesheet);
        lnlAddtsProgress = findViewById(R.id.lnl_add_ts_progress);
    }

    private void addEvents() {
        this.getSubjects(this.getString(R.string.CHILD_SUBJECTS));
        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ((TextView)adapterView.getChildAt(0)).setTextColor(Color.rgb(00, 00, 00));
                adapterSpinner.notifyDataSetChanged();
                if (subjectList.size() > 0 && i > 0){
                    RegisterSubjectSelected(subjectList.get(i-1));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        btnCreateTimesheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handingCreateTimesheet();
            }
        });
    }

    private void RegisterSubjectSelected(String s) {
        this.mydb.child(this.getString(R.string.CHILD_CLASS)).child(s).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                recyclerviewList.clear();
                for (DataSnapshot item : dataSnapshot.getChildren()){
                    TimesheetItem timesheet = item.getValue(TimesheetItem.class);
                    recyclerviewList.add(timesheet);
                }
                adapterRecyclerview.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void handingCreateTimesheet() {
        lnlAddtsProgress.setVisibility(View.VISIBLE);
        this.mydb.child(getString(R.string.CHILD_TIMESHEET)).child("thienphan").child(recyclerviewList.get(currentPosition).getSubjectCode()).setValue(recyclerviewList.get(currentPosition));
        lnlAddtsProgress.setVisibility(View.GONE);

        /*String mytimesheet = timesheetPreferences.get(getString(R.string.MY_TIMESHEET),String.class);
        if (mytimesheet.isEmpty()){
            ArrayList<String> tsList = new ArrayList<>();
            tsList.add(recyclerviewList.get(currentPosition).getSubjectCode());
            String data = gson.toJson(tsList);
            timesheetPreferences.put(getString(R.string.MY_TIMESHEET),data);
        }
        else {
            ArrayList<String> tsList = gson.fromJson(mytimesheet,ArrayList.class);
            tsList.add(recyclerviewList.get(currentPosition).getSubjectCode());
            String data = gson.toJson(tsList);
            timesheetPreferences.put(getString(R.string.MY_TIMESHEET),data);
        }*/

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        int today = calendar.get(Calendar.DAY_OF_WEEK);
        if (today == 1){
            int moveDay = 8 - recyclerviewList.get(currentPosition).getDayofWeek();
            calendar.setTimeInMillis(System.currentTimeMillis()- 86400000*moveDay);
        }
        else {
            int moveDay = today - recyclerviewList.get(currentPosition).getDayofWeek();
            calendar.setTimeInMillis(System.currentTimeMillis()- 86400000*moveDay);
        }

        Intent alertIntent = new Intent(getApplicationContext(), TimesheetReceiver.class);
        alertIntent.putExtra(getString(R.string.NOTICATION_ID), Integer.parseInt(recyclerviewList.get(currentPosition).getSubjectCode().substring(2,5)));
        alertIntent.putExtra(getString(R.string.NOTICATION_TITLE),recyclerviewList.get(currentPosition).getSubjectName());
        alertIntent.putExtra(getString(R.string.NOTICATION_BODY),"Bạn ơi sắp đến giờ học môn "+recyclerviewList.get(currentPosition).getSubjectName()+" rồi.");

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 1, alertIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(), alarmManager.INTERVAL_DAY*7, pendingIntent);

        for (int i = 0; i < spinnerList.size(); i++){
            if(spinnerList.get(i).toLowerCase().trim().equals(recyclerviewList.get(currentPosition).getSubjectName().toLowerCase().trim())){
                spinnerList.remove(i);
                subjectList.remove(i-1);
                if (i == spinnerList.size()){
                    RegisterSubjectSelected(subjectList.get(subjectList.size()-1));
                }
                else RegisterSubjectSelected(subjectList.get(i-1));
                break;
            }
        }
        adapterSpinner.notifyDataSetChanged();
        recyclerviewList.clear();
        adapterRecyclerview.notifyDataSetChanged();

        InfoDialog.ShowInfoDiaLog(CreateTimesheet.this,"Thông báo","Thêm thành công");
    }

    private void getSubjects(String key) {
        String mytimesheet = timesheetPreferences.get(getString(R.string.MY_TIMESHEET),String.class);
        final ArrayList<String> mtsList = gson.fromJson(mytimesheet,ArrayList.class);
        this.mydb.child(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot item : dataSnapshot.getChildren()){
                    if (!isExist(item.getKey().toString(),mtsList)){
                        spinnerList.add(item.getValue().toString());
                        subjectList.add(item.getKey().toString());
                    }
                }
                adapterSpinner.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                InfoDialog.ShowInfoDiaLog(CreateTimesheet.this,"Lỗi",databaseError.toString());
            }
        });
    }

    private boolean isExist(String key,ArrayList<String> list){
        if (list != null && list.size() > 0){
            for (String item : list){
                if (key.equals(item))
                    return true;
            }
        }
        return false;
    }
}

