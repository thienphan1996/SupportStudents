package com.project.thienphan.teacher.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.project.thienphan.supportstudent.R;
import com.project.thienphan.teacher.Fragment.ClassFragment;
import com.project.thienphan.teacher.Fragment.CustomFragment;
import com.project.thienphan.teacher.Fragment.HomeFragment;
import com.project.thienphan.teacher.Fragment.NotifyFragment;
import com.subkhansarif.bottomnavbar.BottomMenu;
import com.subkhansarif.bottomnavbar.IBottomClickListener;
import com.subkhansarif.bottomnavbar.LottieBottomNavbar;

import java.util.ArrayList;

public class TeacherActivity extends AppCompatActivity implements IBottomClickListener {

    ArrayList<BottomMenu> menu;
    LottieBottomNavbar bottom_navbar;
    int backClist = 0;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);
        
        addControls();
    }

    private void addControls() {
        Boolean fromAddNotification = false;
        Intent intent = getIntent();
        if (intent != null){
            fromAddNotification = intent.getBooleanExtra(getString(R.string.FROM_ADD_NOTIFICATION),false);
        }
        menu = new ArrayList<>();
        menu.add(new BottomMenu(0L, new HomeFragment(), getString(R.string.teacher_home), R.drawable.ic_home_black_24dp, null));
        menu.add(new BottomMenu(1L, new ClassFragment(), getString(R.string.teacher_class), R.drawable.ic_class_black_24dp, null));
        menu.add(new BottomMenu(2L, new NotifyFragment(), getString(R.string.teacher_notify), R.drawable.ic_chat_bubble_black_24dp, null));
        menu.add(new BottomMenu(3L, new CustomFragment(), getString(R.string.teacher_custom), R.drawable.ic_more_horiz_black_24dp, null));
        bottom_navbar = findViewById(R.id.bottom_navbar);
        bottom_navbar.setFragmentManager(getSupportFragmentManager());
        bottom_navbar.setMenu(menu);
        if (!fromAddNotification){
            bottom_navbar.setSelected(0);
        }
        else {
            bottom_navbar.setSelected(2);
        }
        bottom_navbar.setMenuClickListener(this);
    }

    @Override
    public void menuClicked(int i, long l) {

    }

    @Override
    public void onBackPressed() {
        backClist++;
        if (backClist == 1){
            Toast.makeText(this,getString(R.string.DOUBLE_BACK),Toast.LENGTH_SHORT).show();
        }
        if (backClist == 2){
            finish();
        }
    }
}
