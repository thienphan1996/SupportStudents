package com.project.thienphan.timesheet.View;

import android.app.Activity;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import com.project.thienphan.supportstudent.R;
import com.project.thienphan.teacher.View.TeacherActivity;
import com.project.thienphan.timesheet.Common.TimesheetPreferences;

public class SplashActivity extends Activity {

    LinearLayout lnSplashActivity;
    TimesheetPreferences timesheetPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        lnSplashActivity = findViewById(R.id.ln_splash);
        timesheetPreferences = new TimesheetPreferences(this);
        Animation animation = AnimationUtils.loadAnimation(this,R.anim.in);
        lnSplashActivity.setAnimation(animation);

        CountDownTimer timer = new CountDownTimer(1500,1000) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                Intent intent = null;
                String savePassword = timesheetPreferences.get(getString(R.string.SAVE_PASSWORD),String.class);
                if (savePassword == null || savePassword.isEmpty()){
                    intent = new Intent(getApplicationContext(),LoginActivity.class);
                }
                else {
                    intent = new Intent(getApplicationContext(),HomeActivity.class);
                }
                startActivity(intent);
                overridePendingTransition(R.anim.in,R.anim.out);
                finish();
            }
        };
        timer.start();
    }
}
