package com.project.thienphan.timesheet.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.project.thienphan.supportstudent.R;
import com.project.thienphan.timesheet.Model.NotificationItem;
import com.rilixtech.materialfancybutton.MaterialFancyButton;

public class NotificationDetails extends AppCompatActivity {

    NotificationItem notification;
    MaterialFancyButton btnBack;
    TextView tvTitle, tvMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        if (intent != null){
            notification = new NotificationItem();
            String subjectName = intent.getStringExtra(getString(R.string.SUBJECT_NAME));
            String message = intent.getStringExtra(getString(R.string.MESSAGE));
            notification.setMessage(message);
            notification.setTitle(subjectName);
        }

        addControls();
        addEvents();
    }

    private void addEvents() {
        if (notification != null){
            tvTitle.setText(notification.getTitle());
            tvMessage.setText(notification.getMessage());
        }

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void addControls() {
        btnBack = findViewById(R.id.btn_notification_detail_back);
        tvMessage = findViewById(R.id.tv_notification_detail_message);
        tvTitle = findViewById(R.id.tv_notification_detail_title);
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
