package com.project.thienphan.timesheet.View;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.project.thienphan.supportstudent.R;
import com.project.thienphan.timesheet.Adapter.TimesheetAdapter;
import com.project.thienphan.timesheet.Database.TimesheetDatabase;
import com.project.thienphan.timesheet.Model.TimesheetItem;
import com.project.thienphan.timesheet.Notification.TimesheetReceiver;
import com.project.thienphan.timesheet.Support.InfoDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity {

    DatabaseReference mydb = TimesheetDatabase.getTimesheetDatabase();

    RecyclerView rcvTimesheet;
    ArrayList<TimesheetItem> timesheetList;
    TimesheetAdapter timesheetAdapter;

    Button btnMonday,btnTuesday,btnWednesday,btnThurday,btnFriday,btnSaturday;
    Button btnActive = null;
    Button btnCreate;
    Button btnCustom;
    TextView txtListEmpty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addControls();
        addEvents();
    }

    private void addControls() {
        txtListEmpty = findViewById(R.id.tv_ts_empty_list);
        rcvTimesheet = findViewById(R.id.rcv_timesheet);
        SetupButton();
        timesheetList = new ArrayList<>();
        timesheetAdapter = new TimesheetAdapter(timesheetList,null);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rcvTimesheet.setLayoutManager(layoutManager);
        rcvTimesheet.setAdapter(timesheetAdapter);
    }

    private void SetupButton() {
        btnMonday = findViewById(R.id.btn_ts_monday);
        btnTuesday = findViewById(R.id.btn_ts_tuesday);
        btnWednesday = findViewById(R.id.btn_ts_wednesday);
        btnThurday = findViewById(R.id.btn_ts_thurday);
        btnFriday = findViewById(R.id.btn_ts_friday);
        btnSaturday = findViewById(R.id.btn_ts_saturday);
        btnCreate = findViewById(R.id.btn_ts_create);
        btnCustom = findViewById(R.id.btn_ts_custom);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        int today = calendar.get(Calendar.DAY_OF_WEEK);
        switch (today){
            case 1:
                btnActive = btnMonday;
                btnMonday.setEnabled(false);
                GetData(2);
                break;
            case 2:
                btnActive = btnMonday;
                btnMonday.setEnabled(false);
                GetData(2);
                break;
            case 3:
                btnActive = btnTuesday;
                btnTuesday.setEnabled(false);
                GetData(3);
                break;
            case 4:
                btnActive = btnWednesday;
                btnWednesday.setEnabled(false);
                GetData(4);
                break;
            case 5:
                btnActive = btnThurday;
                btnThurday.setEnabled(false);
                GetData(5);
                break;
            case 6:
                btnActive = btnFriday;
                btnFriday.setEnabled(false);
                GetData(6);
                break;
            case 7:
                btnActive = btnSaturday;
                btnSaturday.setEnabled(false);
                GetData(7);
                break;
            default:
                break;
        }
    }

    private void GetData(final int dayofweek) {
        txtListEmpty.setVisibility(View.GONE);
        this.mydb.child(getString(R.string.CHILD_TIMESHEET)).child("thienphan").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                timesheetList.clear();
                for (DataSnapshot child : dataSnapshot.getChildren()){
                    TimesheetItem item = child.getValue(TimesheetItem.class);
                    timesheetList.add(item);
                }
                ArrayList<TimesheetItem> temp = TimesheetItem.getTimesheetByDayofWeek(timesheetList,dayofweek);
                timesheetList.clear();
                timesheetList.addAll(temp);
                timesheetAdapter.notifyDataSetChanged();
                if (timesheetList.size()==0){
                    txtListEmpty.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                InfoDialog.ShowInfoDiaLog(MainActivity.this,"Lỗi",databaseError.toString());
            }
        });
    }

    private void addEvents() {
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view == btnMonday){
                    setActionButton(btnMonday,2);
                }
                else if (view == btnTuesday){
                    setActionButton(btnTuesday,3);
                }
                else if (view == btnWednesday){
                    setActionButton(btnWednesday,4);
                }
                else if (view == btnThurday){
                    setActionButton(btnThurday,5);
                }
                else if (view == btnFriday){
                    setActionButton(btnFriday,6);
                }
                else if (view == btnSaturday){
                    setActionButton(btnSaturday,7);
                }
            }
        };
        btnMonday.setOnClickListener(onClickListener);
        btnTuesday.setOnClickListener(onClickListener);
        btnWednesday.setOnClickListener(onClickListener);
        btnThurday.setOnClickListener(onClickListener);
        btnFriday.setOnClickListener(onClickListener);
        btnSaturday.setOnClickListener(onClickListener);

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,CreateTimesheet.class);
                startActivity(intent);
            }
        });

        btnCustom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,CustomizeActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setActionButton(Button button,int dayofweek) {
        GetData(dayofweek);
        button.setEnabled(false);
        button.setTextColor(getResources().getColor(R.color.dayofweek));
        btnActive.setTextColor(getResources().getColor(android.R.color.white));
        btnActive.setEnabled(true);
        btnActive = button;
    }
}
