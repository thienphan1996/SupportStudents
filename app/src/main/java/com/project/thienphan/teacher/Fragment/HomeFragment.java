package com.project.thienphan.teacher.Fragment;

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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.project.thienphan.supportstudent.R;
import com.project.thienphan.teacher.Adapter.TeacherAdapter;
import com.project.thienphan.timesheet.Database.TimesheetDatabase;
import com.project.thienphan.timesheet.Model.ClassItem;
import com.project.thienphan.timesheet.Model.Subject;
import com.project.thienphan.timesheet.Support.TimesheetProgressDialog;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    View view;
    RecyclerView rcvTeacher;
    ArrayList<ClassItem> lstClass;
    ArrayList<Subject> lstSubject;
    TeacherAdapter teacherAdapter;
    DatabaseReference mydb;
    String teacherID = "TC123";

    TimesheetProgressDialog dialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home,container,false);
        rcvTeacher = view.findViewById(R.id.rcv_teacher);
        addControls();
        dialog.show(getFragmentManager(),"");
        return view;
    }

    private void addControls() {
        mydb = TimesheetDatabase.getTimesheetDatabase();
        dialog = new TimesheetProgressDialog();
        lstClass = new ArrayList<>();
        lstSubject = new ArrayList<>();
        teacherAdapter = new TeacherAdapter(lstClass, new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rcvTeacher.setLayoutManager(layoutManager);
        rcvTeacher.setAdapter(teacherAdapter);
        getTeacherClass();
    }
    private void getTeacherClass() {
        if (teacherID != null){
            mydb.child(getString(R.string.CHILD_TEACHER)).child(teacherID).child("subjects").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot item: dataSnapshot.getChildren()){
                        lstSubject.add(new Subject(item.getKey(),item.getValue().toString()));
                    }
                    getDataByTeacher(lstSubject);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

    private void getDataByTeacher(final ArrayList<Subject> lstSubject) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (lstSubject != null){
                    for (Subject item : lstSubject){
                        getClassFromSubject(item);
                    }
                }
                if (isAdded()){
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            teacherAdapter.notifyDataSetChanged();
                            dialog.dismiss();
                        }
                    });
                }
            }
        }).start();
    }

    private void getClassFromSubject(Subject subject) {
        mydb.child(getString(R.string.CHILD_ALLCLASS)).child(subject.getSubCode()).child(teacherID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot != null){
                    ClassItem classItem = dataSnapshot.getValue(ClassItem.class);
                    if (classItem != null && !lstClass.contains(classItem)){
                        lstClass.add(classItem);
                    }
                    teacherAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
