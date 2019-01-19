package com.project.thienphan.parent;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.project.thienphan.parent.View.LearningResultActivity;
import com.project.thienphan.supportstudent.R;
import com.project.thienphan.teacher.Adapter.TeacherClassAdapter;
import com.project.thienphan.timesheet.Database.TimesheetDatabase;
import com.project.thienphan.timesheet.Model.ClassItem;
import com.project.thienphan.timesheet.Model.LearningResult;
import com.project.thienphan.timesheet.Model.TimesheetItem;
import com.project.thienphan.timesheet.Support.TimesheetProgressDialog;
import com.project.thienphan.timesheet.View.HomeActivity;
import com.project.thienphan.timesheet.View.LoginActivity;
import com.project.thienphan.timesheet.View.NewsActivity;
import com.project.thienphan.timesheet.View.NotifictionActivity;
import com.project.thienphan.timesheet.View.PrivateActivity;

import java.util.ArrayList;

public class ParentActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    RecyclerView rcvParents;
    ArrayList<ClassItem> data;
    TeacherClassAdapter adapterParents;
    DatabaseReference mydb;
    String studentCode = "B1411358";
    TimesheetProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                Intent intent = new Intent(ParentActivity.this,NotifictionActivity.class);
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

    private void addEvents() {

    }

    private void addControls() {
        rcvParents = findViewById(R.id.rcv_parent_timesheet);
        dialog = new TimesheetProgressDialog();
        mydb = TimesheetDatabase.getTimesheetDatabase();
        data = new ArrayList<>();
        int fromActivity = 2;
        adapterParents = new TeacherClassAdapter(fromActivity, data, getResources(), new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
        rcvParents.setLayoutManager(new GridLayoutManager(getApplicationContext(), 6));
        rcvParents.setAdapter(adapterParents);
        GetData();
    }

    private void GetData() {
        dialog.show(getSupportFragmentManager(),"dialog");
        this.mydb.child(getString(R.string.CHILD_TIMESHEET)).child(studentCode).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                data.clear();
                for (DataSnapshot child : dataSnapshot.getChildren()){
                    TimesheetItem item = child.getValue(TimesheetItem.class);
                    if (item != null){
                        ClassItem classItem = new ClassItem();
                        classItem.setSubjectName(item.getSubjectName());
                        classItem.setSubjectLocation(item.getSubjectLocation());
                        classItem.setSubjectTime(item.getSubjectTime());
                        classItem.setDayofWeek((long) item.getDayofWeek());
                        data.add(classItem);
                    }
                }
                ArrayList<ClassItem> list = new ArrayList<>();
                list.addAll(data);
                data.clear();
                data.addAll(ClassItem.SortTeacherClass(list));
                adapterParents.notifyDataSetChanged();
                dialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.parent, menu);
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

        if (id == R.id.nav_parents_score) {
            Intent intent = new Intent(ParentActivity.this,LearningResultActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_parents_child_info) {
            Intent intent = new Intent(ParentActivity.this,PrivateActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_parents_news) {
            Intent intent = new Intent(ParentActivity.this, NewsActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_parents_sign_out) {
            confirmSignOut();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void confirmSignOut() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Bạn có muốn đăng xuất không ?")
                .setCancelable(false)
                .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        SharedPreferences preferences = getSharedPreferences(getString(R.string.TIMESHEET_PREFS), 0);
                        preferences.edit().clear().commit();
                        Intent intent = new Intent(ParentActivity.this,LoginActivity.class);
                        startActivity(intent);
                        finish();
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
