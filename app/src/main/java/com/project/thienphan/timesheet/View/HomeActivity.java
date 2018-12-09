package com.project.thienphan.timesheet.View;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.project.thienphan.supportstudent.R;
import com.project.thienphan.teacher.Service.PushNotificationService;
import com.project.thienphan.timesheet.Adapter.HomeAdapter;
import com.project.thienphan.timesheet.Adapter.TimesheetAdapter;
import com.project.thienphan.timesheet.Common.TimesheetPreferences;
import com.project.thienphan.timesheet.Database.TimesheetDatabase;
import com.project.thienphan.timesheet.Model.StudentInfomation;
import com.project.thienphan.timesheet.Model.TimesheetItem;
import com.project.thienphan.timesheet.Notification.TimesheetReceiver;
import com.project.thienphan.timesheet.Support.InfoDialog;
import com.project.thienphan.timesheet.Support.TimesheetProgressDialog;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    DatabaseReference mydb = TimesheetDatabase.getTimesheetDatabase();
    TimesheetPreferences timesheetPreferences;
    Gson gson;

    RecyclerView rcvTimesheet;
    ArrayList<TimesheetItem> timesheetList;
    HomeAdapter homeAdapter;

    Button btnMonday,btnTuesday,btnWednesday,btnThurday,btnFriday,btnSaturday;
    Button btnActive = null;
    //Button btnCreate;
    //Button btnCustom;
    TextView txtListEmpty,txtNotificationTotal,txtName;
    int backClick = 0;
    ArrayList<String> lstSubjectCode;
    String userCode = "";
    String classCode = "";
    TimesheetProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this,NotifictionActivity.class);
                startActivity(intent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View header = navigationView.getHeaderView(0);
        txtName = header.findViewById(R.id.tv_header_name);
        navigationView.setNavigationItemSelectedListener(this);
        addControls();
        addEvents();
        //GenerateNotification();
    }


    private void GenerateNotification() {
        try {
            boolean firstLogin = timesheetPreferences.get(getString(R.string.FIRST_LOGIN),Boolean.class);
            String savePassword = timesheetPreferences.get(getString(R.string.SAVE_PASSWORD),String.class);
            if (!firstLogin && !savePassword.isEmpty()){
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());
                calendar.set(Calendar.HOUR_OF_DAY, 6);
                Intent alertIntent = new Intent(getApplicationContext(), TimesheetReceiver.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 1, alertIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                        AlarmManager.INTERVAL_DAY, pendingIntent);
                timesheetPreferences.put(getString(R.string.FIRST_LOGIN),true);
            }
        }
        catch (Exception e){
            InfoDialog.ShowInfoDiaLog(this,"Error",e.getMessage());
        }
    }

    private void addControls() {
        txtListEmpty = findViewById(R.id.tv_ts_empty_list);
        txtNotificationTotal = findViewById(R.id.tv_notification_total);
        rcvTimesheet = findViewById(R.id.rcv_timesheet);
        timesheetPreferences = new TimesheetPreferences(getApplicationContext());
        dialog = new TimesheetProgressDialog();
        GetUserCode();
        SetupButton();
        timesheetList = new ArrayList<>();
        lstSubjectCode = new ArrayList<>();
        homeAdapter = new HomeAdapter(timesheetList, new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(HomeActivity.this,TimesheetDetails.class);
                String subCode = timesheetList.get(i).getSubjectCode();
                String url = getString(R.string.sebject_doc_url) + subCode + ".pdf";
                intent.putExtra(getString(R.string.TS_DETAILS),url);
                startActivity(intent);
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rcvTimesheet.setLayoutManager(layoutManager);
        rcvTimesheet.setAdapter(homeAdapter);
        gson = new Gson();
        if (Build.VERSION.SDK_INT >= 25) {
            createShorcut();
        }
    }

    private void GetUserCode() {
        userCode = timesheetPreferences.get(getString(R.string.USER),String.class);
        if (!userCode.isEmpty()){
            GetStudentInfo();
        }
    }

    private void GetStudentInfo() {
        mydb.child(getString(R.string.CHILD_STUDENTINFO)).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot item: dataSnapshot.getChildren()){
                    StudentInfomation student = item.getValue(StudentInfomation.class);
                    if (student != null){
                        if (student.getMaSV().toUpperCase().equals(userCode.toUpperCase())){
                            txtName.setText(student.getHoTen());
                            classCode = student.getLopHoc().toUpperCase();
                            RegisterNotification(classCode);
                            continue;
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        GetNotificationTotal();
    }

    private void GetNotificationTotal() {
        int total = timesheetPreferences.get(getString(R.string.NOTIFICATION_TOTAL),Integer.class);
        if (total > 0){
            txtNotificationTotal.setText(total + "");
        }
        else {
            txtNotificationTotal.setVisibility(View.GONE);
        }
    }

    private void RegisterNotification(String user) {
        FirebaseMessaging.getInstance().subscribeToTopic(user)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "subscribed";
                        if (!task.isSuccessful()) {
                            msg = "subscribe failed";
                        }
                        Log.d("msg Status: ", msg);
                        //Toast.makeText(HomeActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void addEvents() {
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view == btnMonday){
                    setActionButton(btnMonday,2);
                }
                else if (view == btnTuesday){
                    setActionButton(btnTuesday,3);
                }
                else if (view == btnWednesday){
                    setActionButton(btnWednesday,4);
                }
                else if (view == btnThurday){
                    setActionButton(btnThurday,5);
                }
                else if (view == btnFriday){
                    setActionButton(btnFriday,6);
                }
                else if (view == btnSaturday){
                    setActionButton(btnSaturday,7);
                }
            }
        };
        btnMonday.setOnClickListener(onClickListener);
        btnTuesday.setOnClickListener(onClickListener);
        btnWednesday.setOnClickListener(onClickListener);
        btnThurday.setOnClickListener(onClickListener);
        btnFriday.setOnClickListener(onClickListener);
        btnSaturday.setOnClickListener(onClickListener);

        /*btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this,CreateTimesheet.class);
                startActivity(intent);
            }
        });

        btnCustom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this,CustomizeActivity.class);
                startActivity(intent);
            }
        });*/
    }

    private void SetupButton() {
        btnMonday = findViewById(R.id.btn_ts_monday);
        btnTuesday = findViewById(R.id.btn_ts_tuesday);
        btnWednesday = findViewById(R.id.btn_ts_wednesday);
        btnThurday = findViewById(R.id.btn_ts_thurday);
        btnFriday = findViewById(R.id.btn_ts_friday);
        btnSaturday = findViewById(R.id.btn_ts_saturday);
        //btnCreate = findViewById(R.id.btn_ts_create);
        //btnCustom = findViewById(R.id.btn_ts_custom);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        int today = calendar.get(Calendar.DAY_OF_WEEK);
        switch (today){
            case 1:
                btnActive = btnMonday;
                btnMonday.setEnabled(false);
                GetData(2);
                btnMonday.setTextColor(getResources().getColor(R.color.colorPrimary));
                break;
            case 2:
                btnActive = btnMonday;
                btnMonday.setEnabled(false);
                GetData(2);
                btnMonday.setTextColor(getResources().getColor(R.color.colorPrimary));
                break;
            case 3:
                btnActive = btnTuesday;
                btnTuesday.setEnabled(false);
                GetData(3);
                btnTuesday.setTextColor(getResources().getColor(R.color.colorPrimary));
                break;
            case 4:
                btnActive = btnWednesday;
                btnWednesday.setEnabled(false);
                GetData(4);
                btnMonday.setTextColor(getResources().getColor(R.color.colorPrimary));
                break;
            case 5:
                btnActive = btnThurday;
                btnThurday.setEnabled(false);
                GetData(5);
                btnThurday.setTextColor(getResources().getColor(R.color.colorPrimary));
                break;
            case 6:
                btnActive = btnFriday;
                btnFriday.setEnabled(false);
                GetData(6);
                btnFriday.setTextColor(getResources().getColor(R.color.colorPrimary));
                break;
            case 7:
                btnActive = btnSaturday;
                btnSaturday.setEnabled(false);
                GetData(7);
                btnSaturday.setTextColor(getResources().getColor(R.color.colorPrimary));
                break;
            default:
                break;
        }
    }

    private void GetData(final int dayofweek) {
        txtListEmpty.setVisibility(View.GONE);
        dialog.show(getSupportFragmentManager(),"dialog");
        if (!userCode.isEmpty()){
            this.mydb.child(getString(R.string.CHILD_TIMESHEET)).child(userCode.toUpperCase()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    timesheetList.clear();
                    for (DataSnapshot child : dataSnapshot.getChildren()){
                        TimesheetItem item = child.getValue(TimesheetItem.class);
                        timesheetList.add(item);
                    }
                    String mytimesheet = timesheetPreferences.get(getString(R.string.MY_TIMESHEET),String.class);
                    if (mytimesheet.isEmpty() && timesheetList != null){
                        String data = gson.toJson(timesheetList);
                        timesheetPreferences.put(getString(R.string.MY_TIMESHEET),data);
                        //subscribe TOPIC
                        for (TimesheetItem item: timesheetList){
                            RegisterNotification(item.getSubjectCode().toUpperCase());
                            lstSubjectCode.add(item.getSubjectCode().toUpperCase());
                        }
                    }
                    ArrayList<TimesheetItem> temp = TimesheetItem.getTimesheetByDayofWeek(timesheetList,dayofweek);
                    timesheetList.clear();
                    timesheetList.addAll(temp);
                    homeAdapter.notifyDataSetChanged();
                    if (timesheetList.size()==0){
                        txtListEmpty.setVisibility(View.VISIBLE);
                    }
                    dialog.dismiss();
                    //GenerateNotification();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    InfoDialog.ShowInfoDiaLog(HomeActivity.this,"Lỗi",databaseError.toString());
                    dialog.dismiss();
                }
            });
        }
    }

    private void setActionButton(Button button,int dayofweek) {
        GetData(dayofweek);
        btnActive.setTextColor(getResources().getColor(android.R.color.white));
        btnActive.setEnabled(true);
        btnActive = button;
        button.setEnabled(false);
        button.setTextColor(getResources().getColor(R.color.colorPrimary));
    }

    @TargetApi(Build.VERSION_CODES.N_MR1)
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void createShorcut() {
        ShortcutManager sM = getSystemService(ShortcutManager.class);

        Intent intent1 = new Intent(getApplicationContext(), SplashActivity.class);
        intent1.putExtra(getString(R.string.FROM_NOTIFICATION),true);
        intent1.setAction(Intent.ACTION_VIEW);

        ShortcutInfo shortcut1 = new ShortcutInfo.Builder(this, "shortcut1")
                .setIntent(intent1)
                .setShortLabel("Thông báo")
                .setLongLabel("Thông báo")
                .setDisabledMessage("Đăng nhập để mở shortcut")
                .setIcon(Icon.createWithResource(this, R.drawable.ic_notifications_black_24dp))
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

    @Override
    public void onBackPressed() {
        backClick++;
        if (backClick == 1){
            Toast.makeText(this,getString(R.string.DOUBLE_BACK),Toast.LENGTH_SHORT).show();
        }
        if (backClick == 2){
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            finish();
        }
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_news) {
            Intent intent = new Intent(HomeActivity.this, NewsActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_private) {
            Intent intent = new Intent(HomeActivity.this,PrivateActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_elctu) {
            Uri uri = Uri.parse("https://www.ctu.edu.vn/");
            startActivity(new Intent(Intent.ACTION_VIEW, uri));
        } else if (id == R.id.nav_logout) {
            showConfirmDialog(getString(R.string.LOGOUT_CONFIRM),1);
        } else if (id == R.id.nav_about) {
            Intent intent = new Intent(HomeActivity.this,AboutActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_out) {
            showConfirmDialog(getString(R.string.QUICK_CONFIRM),2);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showConfirmDialog(String message, final int type) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message)
                .setCancelable(false)
                .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (type == 1){
                            try {
                                DeleteNotification(classCode);
                                for (String code : lstSubjectCode){
                                    DeleteNotification(code);
                                }
                                removeShorcuts();
                            }catch (Exception e){
                            }
                            SharedPreferences preferences = getSharedPreferences(getString(R.string.TIMESHEET_PREFS), 0);
                            preferences.edit().clear().commit();
                            Intent intent = new Intent(HomeActivity.this,LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        else if (type == 2){
                            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                            drawer.closeDrawer(GravityCompat.START);
                            dialog.dismiss();
                            finish();
                        }
                    }
                })
                .setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
    private void DeleteNotification(String topic) {
        Intent alertIntent = new Intent(getApplicationContext(), TimesheetReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 1, alertIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
        FirebaseMessaging.getInstance().unsubscribeFromTopic(topic)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "subscribed";
                        if (!task.isSuccessful()) {
                            msg = "subscribe failed";
                        }
                        Log.d("msg Status: ", msg);
                        //Toast.makeText(HomeActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });
    }
    @TargetApi(25)
    private void removeShorcuts() {
        ArrayList<String> lstShortcut = new ArrayList<>();
        lstShortcut.add("shortcut1");
        lstShortcut.add("shortcut2");
        ShortcutManager shortcutManager = getSystemService(ShortcutManager.class);
        shortcutManager.disableShortcuts(lstShortcut);
        shortcutManager.removeAllDynamicShortcuts();
    }
}
