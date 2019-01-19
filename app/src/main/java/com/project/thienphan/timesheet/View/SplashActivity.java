package com.project.thienphan.timesheet.View;

import android.app.Activity;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import com.project.thienphan.parent.ParentActivity;
import com.project.thienphan.supportstudent.R;
import com.project.thienphan.teacher.View.AddNotification;
import com.project.thienphan.teacher.View.TeacherActivity;
import com.project.thienphan.timesheet.Common.TimesheetPreferences;

public class SplashActivity extends Activity {

    LinearLayout lnSplashActivity;
    TimesheetPreferences timesheetPreferences;
    Boolean fromNotification = false;
    Boolean fromShortcutNews = false;
    Boolean fromShortcutAddNotify = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        lnSplashActivity = findViewById(R.id.ln_splash);
        timesheetPreferences = new TimesheetPreferences(this);
        Animation animation = AnimationUtils.loadAnimation(this,R.anim.in);
        lnSplashActivity.setAnimation(animation);
        Intent i = getIntent();
        if (i != null){
            fromNotification = i.getBooleanExtra(getString(R.string.FROM_NOTIFICATION),false);
            fromShortcutNews = i.getBooleanExtra(getString(R.string.FROM_SHORTCUT_NEWS),false);
            fromShortcutAddNotify = i.getBooleanExtra(getString(R.string.FROM_SHORTCUT_ADD_NOTIFY),false);
        }

        CountDownTimer timer = new CountDownTimer(1500,1000) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                Intent intent = null;
                String savePassword = timesheetPreferences.get(getString(R.string.SAVE_PASSWORD),String.class);
                if (fromNotification){
                    intent = new Intent(getApplicationContext(),NotifictionActivity.class);
                }
                else if (fromShortcutNews){
                    intent = new Intent(getApplicationContext(),NewsActivity.class);
                }
                else if(fromShortcutAddNotify){
                    intent = new Intent(getApplicationContext(),AddNotification.class);
                }
                else if (savePassword == null || savePassword.isEmpty()){
                    intent = new Intent(getApplicationContext(), LoginActivity.class);
                }
                else if (savePassword != null && savePassword.length() > 2 && savePassword.toLowerCase().substring(0,2).equals("tc")){
                    intent = new Intent(getApplicationContext(), TeacherActivity.class);
                }
                else if (savePassword != null && savePassword.length() > 2 && savePassword.toLowerCase().substring(0,2).equals("ph")){
                    intent = new Intent(getApplicationContext(), ParentActivity.class);
                }
                else {
                    intent = new Intent(getApplicationContext(), HomeActivity.class);
                }
                startActivity(intent);
                overridePendingTransition(R.anim.in,R.anim.out);
                finish();
            }
        };
        timer.start();
    }
}
