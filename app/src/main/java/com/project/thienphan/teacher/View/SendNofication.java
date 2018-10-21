package com.project.thienphan.teacher.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.project.thienphan.supportstudent.R;
import com.project.thienphan.teacher.Service.PushNotificationService;
import com.rilixtech.materialfancybutton.MaterialFancyButton;

public class SendNofication extends AppCompatActivity {

    EditText edtTitle,edtMessage;
    MaterialFancyButton btnSend;

    String subjectCode = "";
    String subjectName = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_nofication);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        addControls();
        addEvents();
    }

    private void addEvents() {
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = edtTitle.getText().toString();
                String message = edtMessage.getText().toString();
                if (!title.isEmpty() && !message.isEmpty() && !subjectCode.isEmpty() && !subjectName.isEmpty()){
                    SendNotification( subjectName,subjectCode,title,message,subjectCode.toUpperCase());
                }
            }
        });
    }

    private void addControls() {
        edtTitle = findViewById(R.id.edt_notification_title);
        edtMessage = findViewById(R.id.edt_notification_message);
        btnSend = findViewById(R.id.btn_notification_send);
        Intent intent = getIntent();
        if (intent != null){
            subjectCode = intent.getStringExtra(getString(R.string.SUBJECT_CODE));
            subjectName = intent.getStringExtra(getString(R.string.SUBJECT_NAME));
        }
    }


    private void SendNotification(String subjectName,String subject, String title, String message, String topic) {
        PushNotificationService service = new PushNotificationService(getSupportFragmentManager(),this);
        service.Send(subjectName,subject,title,message,topic);
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
