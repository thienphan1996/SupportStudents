package com.project.thienphan.teacher.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.project.thienphan.supportstudent.R;
import com.project.thienphan.teacher.Fragment.HomeFragment;

public class AddNotification extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notification);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle bundle = new Bundle();
        bundle.putBoolean(getString(R.string.FROM_ADD_NOTIFICATION), true);
        HomeFragment homeFragment = new HomeFragment();
        homeFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .add(android.R.id.content, homeFragment)
                .commit();
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
