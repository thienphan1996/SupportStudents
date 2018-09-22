package com.project.thienphan.timesheet.View;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.project.thienphan.supportstudent.R;
import com.rilixtech.materialfancybutton.MaterialFancyButton;

public class LoginActivity extends AppCompatActivity {

    MaterialFancyButton btnLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        addControls();
        addEvents();
    }

    private void addEvents() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
                startActivity(intent);
            }
        });
    }

    private void addControls() {
        btnLogin = findViewById(R.id.btn_login);

    }
}
