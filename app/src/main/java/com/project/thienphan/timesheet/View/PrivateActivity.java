package com.project.thienphan.timesheet.View;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.project.thienphan.supportstudent.R;
import com.project.thienphan.timesheet.Common.TimesheetPreferences;
import com.project.thienphan.timesheet.Database.TimesheetDatabase;
import com.project.thienphan.timesheet.Model.StudentInfomation;

public class PrivateActivity extends AppCompatActivity {

    TimesheetPreferences timesheetPreferences;
    TextView txtMaSV,txtHoten,txtGioitinh,txtNgaysinh,txtKhoahoc,txtLop,txtHedaotao,txtManganh;

    CardView cardPrivateInfo;
    DatabaseReference mydb;
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
        GetData();
    }

    private void GetData() {
        String user = timesheetPreferences.get(getString(R.string.USER),String.class);
        if (user != null && !user.isEmpty()){
            mydb.child(getString(R.string.CHILD_STUDENTINFO)).child(user.toUpperCase()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    AddDataToView(dataSnapshot);
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
