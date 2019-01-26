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
import com.project.thienphan.parent.Adapter.LearningResultAdapter;
import com.project.thienphan.parent.Model.NotifyMessage;
import com.project.thienphan.supportstudent.R;
import com.project.thienphan.timesheet.Common.TimesheetPreferences;
import com.project.thienphan.timesheet.Database.TimesheetDatabase;
import com.project.thienphan.timesheet.Model.LearningResult;
import com.project.thienphan.timesheet.Support.TimesheetProgressDialog;

import java.util.ArrayList;

public class ResultFragment extends Fragment {

    View view;

    RecyclerView rcvLearningResult;
    ArrayList<LearningResult> lstLearningResult;
    LearningResultAdapter adapterLearningResult;
    String user = "";
    TimesheetPreferences timesheetPreferences;
    DatabaseReference mydb;
    TimesheetProgressDialog dialog;
    public ResultFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_learning_result, container, false);
        addControls();
        return view;
    }

    private void addControls() {
        rcvLearningResult = view.findViewById(R.id.rcv_learning_result);
        lstLearningResult = new ArrayList<>();
        adapterLearningResult = new LearningResultAdapter(lstLearningResult, new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                lstLearningResult.get(i).setShowModal(!lstLearningResult.get(i).isShowModal());
                adapterLearningResult.notifyDataSetChanged();
            }
        });
        timesheetPreferences = new TimesheetPreferences(getContext());
        dialog = new TimesheetProgressDialog();
        mydb = TimesheetDatabase.getTimesheetDatabase();
        user = timesheetPreferences.get(getString(R.string.USER),String.class);
        if(!user.isEmpty() && user.length() > 2 && user.substring(0,2).toLowerCase().equals("ph")){
            user = user.substring(2,user.length());
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rcvLearningResult.setLayoutManager(layoutManager);
        rcvLearningResult.setAdapter(adapterLearningResult);
        GetData();
    }

    private void GetData() {
        this.mydb.child(getString(R.string.CHILD_LEARNING_RESULT)).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot child : dataSnapshot.getChildren()){
                    LearningResult item = child.getValue(LearningResult.class);
                    if (item != null && item.getMSSV().toLowerCase().equals(user.toLowerCase())){
                        item.setShowModal(false);
                        lstLearningResult.add(item);
                    }
                }
                getLastCommentBySubjectCode();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getLastCommentBySubjectCode() {
        if (lstLearningResult != null && lstLearningResult.size() > 0){
            for (final LearningResult item : lstLearningResult){
                mydb.child(getString(R.string.CHILD_TEACHER_COMMENT)).child(user.toUpperCase()).child(item.getSubjectCode().toUpperCase() + "/messages").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot result : dataSnapshot.getChildren()){
                            NotifyMessage message = result.getValue(NotifyMessage.class);
                            String lastComment = "";
                            if (!message.isParent()){
                                lastComment = "Nhận xét " + (message.getId()+1) + " : " + message.getMessage();
                                item.setLastComment(lastComment);
                            }
                        }
                        adapterLearningResult.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        }
    }
}
