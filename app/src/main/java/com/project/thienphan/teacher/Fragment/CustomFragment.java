package com.project.thienphan.teacher.Fragment;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ShortcutManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.project.thienphan.supportstudent.R;
import com.project.thienphan.teacher.Adapter.CustomizeAdapter;
import com.project.thienphan.teacher.View.ChairmanClassAcitivity;
import com.project.thienphan.timesheet.Common.TimesheetPreferences;
import com.project.thienphan.timesheet.Model.TeacherInfo;
import com.project.thienphan.timesheet.View.HomeActivity;
import com.project.thienphan.timesheet.View.LoginActivity;
import com.project.thienphan.timesheet.View.NewsActivity;

import java.util.ArrayList;
import java.util.Arrays;

public class CustomFragment extends Fragment {

    View view;
    RecyclerView rcvCustomize;
    CustomizeAdapter customizeAdapter;
    TextView tvTeacherName;
    TimesheetPreferences timesheetPreferences;
    Gson gson;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_custom,container,false);
        addControls();
        return view;
    }

    private void addControls() {
        timesheetPreferences = new TimesheetPreferences(getContext());
        gson = new Gson();
        tvTeacherName = view.findViewById(R.id.tv_customize_teacher_name);
        GetTeacherInfo();
        rcvCustomize = view.findViewById(R.id.rcv_customize_teacher_activity);
        customizeAdapter = new CustomizeAdapter(getResources(), new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        Intent intent = new Intent(getContext(), NewsActivity.class);
                        startActivity(intent);
                        break;
                    case 1:
                        Intent intent1 = new Intent(getContext(), ChairmanClassAcitivity.class);
                        startActivity(intent1);
                        break;
                    case 2:
                        Uri uri = Uri.parse("https://www.ctu.edu.vn/");
                        startActivity(new Intent(Intent.ACTION_VIEW, uri));
                        break;
                    case 3:
                        ShowLogoutDialog();
                        break;
                }
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rcvCustomize.setLayoutManager(layoutManager);
        rcvCustomize.setAdapter(customizeAdapter);
    }

    private void GetTeacherInfo() {
        String teacher = timesheetPreferences.get(getString(R.string.TEACHER_INFO), String.class);
        TeacherInfo teacherInfo = gson.fromJson(teacher,TeacherInfo.class);
        if (teacherInfo != null){
            tvTeacherName.setText(teacherInfo.getTeacherName());
        }
    }

    @TargetApi(25)
    private void removeShorcuts() {
        ArrayList<String> lstShortcut = new ArrayList<>();
        lstShortcut.add("shortcut1");
        lstShortcut.add("shortcut2");
        ShortcutManager shortcutManager = getActivity().getSystemService(ShortcutManager.class);
        shortcutManager.disableShortcuts(lstShortcut);
        shortcutManager.removeAllDynamicShortcuts();
    }

    private void ShowLogoutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(getString(R.string.LOGOUT_CONFIRM));
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                removeShorcuts();
                SharedPreferences preferences = getActivity().getSharedPreferences(getString(R.string.TIMESHEET_PREFS), 0);
                preferences.edit().clear().commit();
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
