package com.project.thienphan.timesheet.View;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.project.thienphan.supportstudent.R;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
