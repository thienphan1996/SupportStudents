package com.project.thienphan.parent.Fragment;

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
import com.project.thienphan.parent.Adapter.CommentAdapter;
import com.project.thienphan.parent.Model.Comment;
import com.project.thienphan.parent.Model.NotifyMessage;
import com.project.thienphan.supportstudent.R;
import com.project.thienphan.timesheet.Common.TimesheetPreferences;
import com.project.thienphan.timesheet.Database.TimesheetDatabase;
import com.project.thienphan.timesheet.Support.TimesheetProgressDialog;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class NotifyFragment extends Fragment {

    View view;
    RecyclerView rcvNotify;
    CommentAdapter commentAdapter;
    ArrayList<Comment> lstSubject;
    DatabaseReference mydb;
    TimesheetProgressDialog dialog;
    String user = "";
    TimesheetPreferences timesheetPreferences;

    public NotifyFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_learning_result_notify, container,false);
        addControls();
        return view;
    }

    private void addControls() {
        rcvNotify = view.findViewById(R.id.rcv_learning_result_notify);
        lstSubject = new ArrayList<>();
        mydb = TimesheetDatabase.getTimesheetDatabase();
        dialog = new TimesheetProgressDialog();
        timesheetPreferences = new TimesheetPreferences(getContext());
        user = timesheetPreferences.get(getString(R.string.USER),String.class);
        getData();
        commentAdapter = new CommentAdapter(lstSubject, getContext(), getActivity(), new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                lstSubject.get(i).setShowModal(!lstSubject.get(i).isShowModal());
                commentAdapter.notifyDataSetChanged();
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rcvNotify.setLayoutManager(layoutManager);
        rcvNotify.setAdapter(commentAdapter);
        commentAdapter.notifyDataSetChanged();
    }

    private void getData() {
        dialog.show(getFragmentManager(), "dialog");
        String key = !user.isEmpty() ? user.toUpperCase() : "";
        if (!key.isEmpty()){
            this.mydb.child(getString(R.string.CHILD_TEACHER_COMMENT)).child(key).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    lstSubject.clear();
                    for(DataSnapshot data : dataSnapshot.getChildren()){
                        Comment item = data.getValue(Comment.class);
                        item.setShowModal(false);
                        lstSubject.add(item);
                    }
                    commentAdapter.notifyDataSetChanged();
                    dialog.dismiss();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }
}
