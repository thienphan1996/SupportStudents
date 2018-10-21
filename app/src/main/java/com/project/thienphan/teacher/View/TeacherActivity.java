package com.project.thienphan.teacher.View;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.project.thienphan.supportstudent.R;
import com.project.thienphan.teacher.Fragment.ClassFragment;
import com.project.thienphan.teacher.Fragment.CustomFragment;
import com.project.thienphan.teacher.Fragment.HomeFragment;
import com.project.thienphan.teacher.Fragment.NotifyFragment;
import com.project.thienphan.timesheet.View.SplashActivity;
import com.subkhansarif.bottomnavbar.BottomMenu;
import com.subkhansarif.bottomnavbar.IBottomClickListener;
import com.subkhansarif.bottomnavbar.LottieBottomNavbar;

import java.util.ArrayList;
import java.util.Arrays;

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
        menu = new ArrayList<>();
        menu.add(new BottomMenu(0L, new HomeFragment(), getString(R.string.teacher_home), R.drawable.ic_home_black_24dp, null));
        menu.add(new BottomMenu(1L, new ClassFragment(), getString(R.string.teacher_class), R.drawable.ic_class_black_24dp, null));
        menu.add(new BottomMenu(2L, new NotifyFragment(), getString(R.string.teacher_notify), R.drawable.ic_chat_bubble_black_24dp, null));
        menu.add(new BottomMenu(3L, new CustomFragment(), getString(R.string.teacher_custom), R.drawable.ic_more_horiz_black_24dp, null));
        bottom_navbar = findViewById(R.id.bottom_navbar);
        bottom_navbar.setFragmentManager(getSupportFragmentManager());
        bottom_navbar.setMenu(menu);
        bottom_navbar.setSelected(0);
        bottom_navbar.setMenuClickListener(this);
        if (Build.VERSION.SDK_INT >= 25) {
            createShorcut();
        }
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


    @TargetApi(Build.VERSION_CODES.N_MR1)
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void createShorcut() {
        ShortcutManager sM = getSystemService(ShortcutManager.class);

        Intent intent1 = new Intent(getApplicationContext(), SplashActivity.class);
        intent1.putExtra(getString(R.string.FROM_SHORTCUT_ADD_NOTIFY),true);
        intent1.setAction(Intent.ACTION_VIEW);

        ShortcutInfo shortcut1 = new ShortcutInfo.Builder(this, "shortcut1")
                .setIntent(intent1)
                .setShortLabel("Gửi thông báo")
                .setLongLabel("Gửi thông báo")
                .setDisabledMessage("Đăng nhập để mở shortcut")
                .setIcon(Icon.createWithResource(this, R.drawable.ic_add_alert_black_24dp))
                .build();

        Intent intent2 = new Intent(getApplicationContext(),SplashActivity.class);
        intent2.putExtra(getString(R.string.FROM_SHORTCUT_NEWS),true);
        intent2.setAction(Intent.ACTION_VIEW);
        ShortcutInfo shortcut2 = new ShortcutInfo.Builder(this, "shortcut2")
                .setIntent(intent2)
                .setShortLabel("Tin tức")
                .setLongLabel("Tin tức")
                .setDisabledMessage("Đăng nhập để mở shortcut")
                .setIcon(Icon.createWithResource(this, R.drawable.ic_fiber_new_black_24dp))
                .build();
        ArrayList<ShortcutInfo> lstShortcut = new ArrayList<>();
        lstShortcut.add(shortcut1);
        lstShortcut.add(shortcut2);

        sM.setDynamicShortcuts(lstShortcut);
    }

}
