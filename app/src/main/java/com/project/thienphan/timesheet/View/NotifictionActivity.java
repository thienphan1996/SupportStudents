package com.project.thienphan.timesheet.View;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.project.thienphan.parent.Model.Comment;
import com.project.thienphan.parent.Model.NotifyMessage;
import com.project.thienphan.supportstudent.R;
import com.project.thienphan.timesheet.Adapter.NotificationAdapter;
import com.project.thienphan.timesheet.Common.TimesheetPreferences;
import com.project.thienphan.timesheet.Database.TimesheetDatabase;
import com.project.thienphan.timesheet.Model.NotificationItem;
import com.project.thienphan.timesheet.Model.TimesheetItem;
import com.project.thienphan.timesheet.Support.TimesheetProgressDialog;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class NotifictionActivity extends AppCompatActivity {

    ArrayList<NotificationItem> notificationList;
    RecyclerView rcvNotification;
    NotificationAdapter notificationAdapter;

    TimesheetPreferences timesheetPreferences;
    DatabaseReference mydb;
    TimesheetProgressDialog dialog;

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
        mydb = TimesheetDatabase.getTimesheetDatabase();
        dialog = new TimesheetProgressDialog();
        gson = new Gson();
        rcvNotification = findViewById(R.id.rcv_nofitications);
        notificationList = new ArrayList<>();
        notificationAdapter = new NotificationAdapter(notificationList, getResources(), new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(NotifictionActivity.this, NotificationDetails.class);
                intent.putExtra(getString(R.string.MESSAGE), notificationList.get(i).getMessage());
                intent.putExtra(getString(R.string.SUBJECT_NAME), notificationList.get(i).getTitle());
                startActivity(intent);
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rcvNotification.setLayoutManager(layoutManager);
        rcvNotification.setAdapter(notificationAdapter);
        GetData();
        notificationAdapter.notifyDataSetChanged();
    }

    private void GetData() {
        dialog.show(getSupportFragmentManager(), "dialog");
        String user = timesheetPreferences.get(getString(R.string.USER), String.class);
        if (!user.isEmpty()){
            mydb.child(getString(R.string.CHILD_TEACHER_COMMENT)).child(user.toUpperCase()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    notificationList.clear();
                    for (DataSnapshot item : dataSnapshot.getChildren()){
                        Comment comment = item.getValue(Comment.class);
                        if (comment != null && comment.getMessages() != null && comment.getMessages().size() > 0){
                            for (NotifyMessage message : comment.getMessages()){
                                if (!message.isParent()){
                                    NotificationItem notificationItem = new NotificationItem();
                                    notificationItem.setTitle(comment.getSubjectName());
                                    notificationItem.setMessage(message.getMessage());
                                    notificationItem.setSubject(comment.getSubjectCode());
                                    notificationItem.setTime(message.getCreateAt());
                                    notificationItem.setCreateAt(message.getCreateAtMilisecons());
                                    notificationList.add(notificationItem);
                                }
                            }
                        }
                    }
                    ArrayList<NotificationItem> sortList = new ArrayList<>();
                    sortList.addAll(notificationList);
                    notificationList.clear();
                    notificationList.addAll(NotificationItem.SortNotifications(sortList));
                    notificationAdapter.notifyDataSetChanged();
                    dialog.dismiss();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
