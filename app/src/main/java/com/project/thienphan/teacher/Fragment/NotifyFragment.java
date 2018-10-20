package com.project.thienphan.teacher.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.project.thienphan.supportstudent.R;
import com.project.thienphan.teacher.View.AddNotification;
import com.project.thienphan.timesheet.Adapter.NotificationAdapter;
import com.project.thienphan.timesheet.Common.TimesheetPreferences;
import com.project.thienphan.timesheet.Model.NotificationItem;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class NotifyFragment extends Fragment {

    View view;
    FloatingActionButton btnAddNotify;
    RecyclerView rcvAddNotification;
    ArrayList<NotificationItem> lstNotification;
    NotificationAdapter notificationAdapter;
    TimesheetPreferences timesheetPreferences;
    Gson gson;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_notify,container,false);

        btnAddNotify = view.findViewById(R.id.fab_add_notify);
        btnAddNotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AddNotification.class);
                startActivity(intent);
            }
        });
        addControls();
        return view;
    }

    private void addControls() {
        timesheetPreferences = new TimesheetPreferences(getContext());
        gson = new Gson();
        rcvAddNotification = view.findViewById(R.id.rcv_add_notification);
        lstNotification = new ArrayList<>();
        notificationAdapter = new NotificationAdapter(lstNotification, getResources(), new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rcvAddNotification.setLayoutManager(layoutManager);
        rcvAddNotification.setAdapter(notificationAdapter);
        notificationAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        GetDateFromPreferences();
    }

    private void GetDateFromPreferences() {
        lstNotification.clear();
        String notifications = timesheetPreferences.get(getString(R.string.NOTIFICATION_TEACHER),String.class);
        if (!notifications.isEmpty()){
            Type myType = new TypeToken<ArrayList<NotificationItem>>(){}.getType();
            ArrayList<NotificationItem> data = (ArrayList<NotificationItem>) gson.fromJson(notifications, myType);
            if (data != null){
                for (NotificationItem item : data){
                    lstNotification.add(item);
                }
            }
            notificationAdapter.notifyDataSetChanged();
        }
    }
}
