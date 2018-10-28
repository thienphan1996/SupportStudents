package com.project.thienphan.timesheet.View;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.project.thienphan.supportstudent.R;
import com.project.thienphan.timesheet.Adapter.HomeAdapter;
import com.project.thienphan.timesheet.Common.TimesheetPreferences;
import com.project.thienphan.timesheet.Database.TimesheetDatabase;
import com.project.thienphan.timesheet.Model.StudentInfomation;
import com.project.thienphan.timesheet.Model.TimesheetItem;

import java.util.ArrayList;

public class PrivateActivity extends AppCompatActivity {

    TimesheetPreferences timesheetPreferences;
    TextView txtMaSV,txtHoten,txtGioitinh,txtNgaysinh,txtKhoahoc,txtLop,txtHedaotao,txtManganh;

    CardView cardPrivateInfo;
    DatabaseReference mydb;

    ArrayList<TimesheetItem> lstSubjects;
    RecyclerView rcvSubjects;
    HomeAdapter subjectAdapter;

    String user = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_private);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        addControls();
    }

    private void addControls() {
        txtMaSV = findViewById(R.id.txt_pri_masv);
        txtHoten = findViewById(R.id.txt_pri_hoten);
        txtGioitinh = findViewById(R.id.txt_pri_gioitinh);
        txtNgaysinh = findViewById(R.id.txt_pri_namsinh);
        txtKhoahoc = findViewById(R.id.txt_pri_khoahoc);
        txtLop = findViewById(R.id.txt_pri_lop);
        txtHedaotao = findViewById(R.id.txt_pri_nganh);
        txtManganh = findViewById(R.id.txt_pri_manganh);
        cardPrivateInfo = findViewById(R.id.card_private_info);
        mydb = TimesheetDatabase.getTimesheetDatabase();
        timesheetPreferences = new TimesheetPreferences(this);
        user = timesheetPreferences.get(getString(R.string.USER),String.class);

        rcvSubjects = findViewById(R.id.rcv_private);
        lstSubjects = new ArrayList<>();
        subjectAdapter = new HomeAdapter(lstSubjects, new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(PrivateActivity.this,TimesheetDetails.class);
                String subCode = lstSubjects.get(i).getSubjectCode();
                String url = getString(R.string.sebject_doc_url) + subCode + ".pdf";
                intent.putExtra(getString(R.string.TS_DETAILS),url);
                startActivity(intent);
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rcvSubjects.setLayoutManager(layoutManager);
        rcvSubjects.setAdapter(subjectAdapter);
        GetData();
    }

    private void GetData() {
        if (user != null && !user.isEmpty()){
            mydb.child(getString(R.string.CHILD_STUDENTINFO)).child(user.toUpperCase()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    AddDataToView(dataSnapshot);
                    GetAllSubjects();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        else {
            cardPrivateInfo.setVisibility(View.GONE);
        }
    }

    private void GetAllSubjects() {
        if (!user.isEmpty()){
            this.mydb.child(getString(R.string.CHILD_TIMESHEET)).child(user.toUpperCase()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot child : dataSnapshot.getChildren()){
                        TimesheetItem item = child.getValue(TimesheetItem.class);
                        lstSubjects.add(item);
                    }
                    subjectAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

    private void AddDataToView(DataSnapshot dataSnapshot) {
        if (dataSnapshot.getChildren() != null){
            StudentInfomation student = dataSnapshot.getValue(StudentInfomation.class);
            if (student != null){
                txtManganh.setText(student.getMaNganh());
                txtHedaotao.setText(student.getHeDaoTao());
                txtLop.setText(student.getLopHoc());
                txtKhoahoc.setText(student.getKhoaHoc()+"");
                txtNgaysinh.setText(student.getNgaySinh());
                txtGioitinh.setText(student.getGioiTinh());
                txtHoten.setText(student.getHoTen());
                txtMaSV.setText(student.getMaSV());
            }
        }
    }
}
