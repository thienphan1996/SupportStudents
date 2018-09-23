package com.project.thienphan.timesheet.View;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
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
import com.project.thienphan.timesheet.Adapter.HomeAdapter;
import com.project.thienphan.timesheet.Adapter.TimesheetAdapter;
import com.project.thienphan.timesheet.Common.TimesheetPreferences;
import com.project.thienphan.timesheet.Database.TimesheetDatabase;
import com.project.thienphan.timesheet.Model.TimesheetItem;
import com.project.thienphan.timesheet.Notification.TimesheetReceiver;
import com.project.thienphan.timesheet.Support.InfoDialog;

import java.lang.reflect.Type;
import java.util.ArrayList;
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
    TextView txtListEmpty;
    int backClick = 0;

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
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                Intent intent = new Intent(HomeActivity.this,CreateTimesheet.class);
                startActivity(intent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        addControls();
        addEvents();
    }

    private void GenerateNotification() {
        String mytimesheet = timesheetPreferences.get(getString(R.string.MY_TIMESHEET),String.class);
        if (!mytimesheet.isEmpty()){
            String ts = timesheetPreferences.get(getString(R.string.MY_TIMESHEET),String.class);
            Type myType = new TypeToken<ArrayList<TimesheetItem>>(){}.getType();
            ArrayList<TimesheetItem> tsList = (ArrayList<TimesheetItem>) gson.fromJson(ts,myType);
            Calendar calendar = Calendar.getInstance();
            if (tsList != null){
                try {
                    for (int i = 0; i < tsList.size(); i++){
                        TimesheetItem item = tsList.get(i);
                        int repeat = (item.getDayofWeek()+7) - Calendar.DAY_OF_WEEK;
                        float session = Integer.parseInt(item.getSubjectTime()) < 678 ? 6.5f : 13;
                        Calendar nextWeek = Calendar.getInstance();
                        nextWeek.set(Calendar.YEAR,Calendar.MONTH,Calendar.DATE + repeat);
                        Intent alertIntent = new Intent(getApplicationContext(), TimesheetReceiver.class);
                        int id = Integer.parseInt(item.getSubjectCode().substring(2,5));
                        alertIntent.putExtra(getString(R.string.NOTICATION_ID), id);
                        alertIntent.putExtra(getString(R.string.NOTICATION_TITLE),item.getSubjectName());
                        alertIntent.putExtra(getString(R.string.NOTICATION_BODY),"Bạn ơi sắp đến giờ học môn "+item.getSubjectName()+" rồi.");
                        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 1, alertIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                        alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                                SystemClock.elapsedRealtime() + AlarmManager.INTERVAL_HALF_HOUR,
                                AlarmManager.INTERVAL_HALF_HOUR, pendingIntent);
                    }
                }
                catch (Exception e){
                    InfoDialog.ShowInfoDiaLog(this,"Error",e.getMessage());
                }
            }
        }
    }

    private void addControls() {
        txtListEmpty = findViewById(R.id.tv_ts_empty_list);
        rcvTimesheet = findViewById(R.id.rcv_timesheet);
        SetupButton();
        RegisterNotification("thienphan");
        timesheetList = new ArrayList<>();
        homeAdapter = new HomeAdapter(timesheetList, new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(HomeActivity.this,TimesheetDetails.class);
                intent.putExtra(getString(R.string.TS_DETAILS),timesheetList.get(i).getSubjectCode());
                startActivity(intent);
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rcvTimesheet.setLayoutManager(layoutManager);
        rcvTimesheet.setAdapter(homeAdapter);
        timesheetPreferences = new TimesheetPreferences(getApplicationContext());
        gson = new Gson();
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
                break;
            case 2:
                btnActive = btnMonday;
                btnMonday.setEnabled(false);
                GetData(2);
                break;
            case 3:
                btnActive = btnTuesday;
                btnTuesday.setEnabled(false);
                GetData(3);
                break;
            case 4:
                btnActive = btnWednesday;
                btnWednesday.setEnabled(false);
                GetData(4);
                break;
            case 5:
                btnActive = btnThurday;
                btnThurday.setEnabled(false);
                GetData(5);
                break;
            case 6:
                btnActive = btnFriday;
                btnFriday.setEnabled(false);
                GetData(6);
                break;
            case 7:
                btnActive = btnSaturday;
                btnSaturday.setEnabled(false);
                GetData(7);
                break;
            default:
                break;
        }
    }

    private void GetData(final int dayofweek) {
        txtListEmpty.setVisibility(View.GONE);
        this.mydb.child(getString(R.string.CHILD_TIMESHEET)).child("thienphan").addValueEventListener(new ValueEventListener() {
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
                }
                ArrayList<TimesheetItem> temp = TimesheetItem.getTimesheetByDayofWeek(timesheetList,dayofweek);
                timesheetList.clear();
                timesheetList.addAll(temp);
                homeAdapter.notifyDataSetChanged();
                if (timesheetList.size()==0){
                    txtListEmpty.setVisibility(View.VISIBLE);
                }
                GenerateNotification();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                InfoDialog.ShowInfoDiaLog(HomeActivity.this,"Lỗi",databaseError.toString());
            }
        });
    }

    private void setActionButton(Button button,int dayofweek) {
        GetData(dayofweek);
        button.setEnabled(false);
        button.setTextColor(getResources().getColor(R.color.dayofweek));
        btnActive.setTextColor(getResources().getColor(android.R.color.white));
        btnActive.setEnabled(true);
        btnActive = button;
    }

    @Override
    public void onBackPressed() {
        backClick++;
        if (backClick == 1){
            Toast.makeText(this,getString(R.string.DOUBLE_BACK),Toast.LENGTH_SHORT).show();
        }
        if (backClick == 2){
            finish();
        }
    }

    @Override
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
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

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
                            SharedPreferences preferences = getSharedPreferences(getString(R.string.TIMESHEET_PREFS), 0);
                            preferences.edit().clear().commit();
                            Intent intent = new Intent(HomeActivity.this,LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        else if (type == 2){
                            android.os.Process.killProcess(android.os.Process.myPid());
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
}
