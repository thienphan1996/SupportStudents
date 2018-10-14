package com.project.thienphan.timesheet.View;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.project.thienphan.supportstudent.R;
import com.project.thienphan.teacher.View.TeacherActivity;
import com.project.thienphan.timesheet.Common.TimesheetPreferences;
import com.project.thienphan.timesheet.Database.TimesheetDatabase;
import com.project.thienphan.timesheet.Model.UserAccount;
import com.project.thienphan.timesheet.Support.TimesheetProgressDialog;
import com.rilixtech.materialfancybutton.MaterialFancyButton;

public class LoginActivity extends AppCompatActivity {

    MaterialFancyButton btnLogin;
    EditText edtAccount, edtPassword;
    SwitchCompat swtSavePassword;

    DatabaseReference mydb;
    TimesheetPreferences timesheetPreferences;
    TimesheetProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        addControls();
        addEvents();
    }

    private void addEvents() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verifyLogin(view);
            }
        });
    }

    private void verifyLogin(final View view) {
        final String account = edtAccount.getText().toString().trim();
        final String password = edtPassword.getText().toString().trim();

        if (!account.isEmpty() && !password.isEmpty()){
            dialog.show(getSupportFragmentManager(),"");
            mydb.child(getString(R.string.CHILD_ACCOUNTS)).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getChildren() != null){
                        for (DataSnapshot item : dataSnapshot.getChildren()){
                            UserAccount invalidAccount = item.getValue(UserAccount.class);
                            if (invalidAccount != null
                                && invalidAccount.getAccount().toLowerCase().equals(account.toLowerCase())
                                && invalidAccount.getPassword().equals(password))
                            {
                                timesheetPreferences.put(getString(R.string.USER),account);
                                if (swtSavePassword.isChecked()){
                                    timesheetPreferences.put(getString(R.string.SAVE_PASSWORD),account);
                                }
                                Intent intent;
                                if ( account.toLowerCase().substring(0,2).equals("tc") ){
                                    intent = new Intent(LoginActivity.this,TeacherActivity.class);
                                }
                                else {
                                    intent = new Intent(LoginActivity.this,HomeActivity.class);
                                }
                                startActivity(intent);
                                finish();
                            }
                        }
                        dialog.dismiss();
                        Snackbar.make(view, getString(R.string.LOGIN_FAILED), Snackbar.LENGTH_LONG)
                                .setAction("OK", null).show();
                    }
                    dialog.dismiss();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Snackbar.make(view, getString(R.string.CONNECT_FAILED), Snackbar.LENGTH_LONG)
                            .setAction("OK", null).show();
                    dialog.dismiss();
                }
            });
        }
    }

    private void addControls() {
        mydb = TimesheetDatabase.getTimesheetDatabase();
        timesheetPreferences = new TimesheetPreferences(getApplicationContext());
        btnLogin = findViewById(R.id.btn_login);
        edtAccount = findViewById(R.id.edt_account);
        edtPassword = findViewById(R.id.edt_password);
        swtSavePassword = findViewById(R.id.swt_save_password);
        dialog = new TimesheetProgressDialog();
    }
}
