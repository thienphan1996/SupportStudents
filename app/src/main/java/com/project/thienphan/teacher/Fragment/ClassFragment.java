package com.project.thienphan.teacher.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.project.thienphan.supportstudent.R;
import com.project.thienphan.teacher.Adapter.TeacherClassAdapter;
import com.project.thienphan.teacher.View.CheckStudentActivity;
import com.project.thienphan.timesheet.Common.TimesheetPreferences;
import com.project.thienphan.timesheet.Database.TimesheetDatabase;
import com.project.thienphan.timesheet.Model.ClassItem;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

public class ClassFragment extends Fragment {

    View view;
    RecyclerView rcvTeacherClass;
    TeacherClassAdapter teacherClassAdapter;
    ArrayList<ClassItem> lstTeacherClass;
    TimesheetPreferences timesheetPreferences;
    DatabaseReference mydb;
    String teacherCode = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_class, container, false);

        addControls();
        return view;
    }

    private void addControls() {
        timesheetPreferences = new TimesheetPreferences(getContext());
        mydb = TimesheetDatabase.getTimesheetDatabase();
        teacherCode = timesheetPreferences.get(getString(R.string.USER), String.class);
        rcvTeacherClass = view.findViewById(R.id.rcv_teacher_class);
        lstTeacherClass = new ArrayList<>();
        teacherClassAdapter = new TeacherClassAdapter(lstTeacherClass, getResources(), new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String students = lstTeacherClass.get(i).getStudents();
                if (students != null && !students.isEmpty()){
                    Intent intent = new Intent(getActivity(), CheckStudentActivity.class);
                    intent.putExtra(getString(R.string.SUBJECT_CODE),lstTeacherClass.get(i).getSubjectCode());
                    intent.putExtra(getString(R.string.LIST_STUDENT),students);
                    startActivity(intent);
                }
            }
        });
        rcvTeacherClass.setLayoutManager(new GridLayoutManager(getContext(), 6));
        rcvTeacherClass.setAdapter(teacherClassAdapter);
        getData();
    }

    private void getData() {
        if (!teacherCode.isEmpty()) {
            mydb.child(getString(R.string.CHILD_TEACHER)).child(teacherCode.toUpperCase()).child(getString(R.string.CHILD_SUBJECTS).toLowerCase()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    ArrayList<String> lstClassCode = new ArrayList<>();
                    for (DataSnapshot item : dataSnapshot.getChildren()) {
                        String classCode = item.getKey();
                        lstClassCode.add(classCode);
                    }
                    GetTeacherClass(lstClassCode);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

    private void GetTeacherClass(final ArrayList<String> lstClasscode) {
        for (int i = 0; i < lstClasscode.size(); i++){
            String classCode = lstClasscode.get(i);
            final int finalI = i;
            mydb.child(getString(R.string.CHILD_ALLCLASS)).child(classCode.toUpperCase()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot item : dataSnapshot.getChildren()) {
                        if (item.getKey().toString().toUpperCase().equals(teacherCode.toUpperCase())) {
                            ClassItem classItem = item.getValue(ClassItem.class);
                            lstTeacherClass.add(classItem);
                            break;
                        }
                    }
                    if (finalI == lstClasscode.size() - 1){
                        ArrayList<ClassItem> list = new ArrayList<>();
                        list.addAll(lstTeacherClass);
                        lstTeacherClass.clear();
                        lstTeacherClass.addAll(ClassItem.SortTeacherClass(list));
                        teacherClassAdapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }
}
