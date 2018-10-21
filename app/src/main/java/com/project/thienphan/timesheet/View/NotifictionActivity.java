package com.project.thienphan.timesheet.View;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.project.thienphan.supportstudent.R;
import com.project.thienphan.timesheet.Adapter.NotificationAdapter;
import com.project.thienphan.timesheet.Common.TimesheetPreferences;
import com.project.thienphan.timesheet.Model.NotificationItem;
import com.project.thienphan.timesheet.Model.TimesheetItem;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class NotifictionActivity extends AppCompatActivity {

    ArrayList<NotificationItem> notificationList;
    RecyclerView rcvNotification;
    NotificationAdapter notificationAdapter;

    TimesheetPreferences timesheetPreferences;

    Gson gson;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifiction);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        addControls();
        addEvents();
    }

    private void addEvents() {

    }

    private void addControls() {
        timesheetPreferences = new TimesheetPreferences(getApplicationContext());
        gson = new Gson();
        rcvNotification = findViewById(R.id.rcv_nofitications);
        notificationList = new ArrayList<>();
        notificationAdapter = new NotificationAdapter(notificationList, getResources(), new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                notificationList.get(i).setSeen(true);
                notificationAdapter.notifyDataSetChanged();
                setNotificationIsSeen(i);
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rcvNotification.setLayoutManager(layoutManager);
        rcvNotification.setAdapter(notificationAdapter);
        GetDateFromPreferences();
        notificationAdapter.notifyDataSetChanged();
    }

    private void setNotificationIsSeen(int i) {
        int total = timesheetPreferences.get(getString(R.string.NOTIFICATION_TOTAL),Integer.class);
        String notifications = timesheetPreferences.get(getString(R.string.NOTIFICATION_STUDENT),String.class);
        Type myType = new TypeToken<ArrayList<NotificationItem>>(){}.getType();
        ArrayList<NotificationItem> data = (ArrayList<NotificationItem>) gson.fromJson(notifications, myType);
        if (data != null){
            if (!data.get(i).isSeen()){
                if (total > 0){
                    total--;
                    timesheetPreferences.put(getString(R.string.NOTIFICATION_TOTAL),total);
                }
            }
            data.get(i).setSeen(true);
            String dataToString = gson.toJson(data);
            timesheetPreferences.put(getString(R.string.NOTIFICATION_STUDENT),dataToString);
        }
    }

    private void GetDateFromPreferences() {
        String notifications = timesheetPreferences.get(getString(R.string.NOTIFICATION_STUDENT),String.class);
        if (!notifications.isEmpty()){
            Type myType = new TypeToken<ArrayList<NotificationItem>>(){}.getType();
            ArrayList<NotificationItem> data = (ArrayList<NotificationItem>) gson.fromJson(notifications, myType);
            if (data != null){
                for (NotificationItem item : data){
                    notificationList.add(item);
                }
            }
            notificationAdapter.notifyDataSetChanged();
        }
    }
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
