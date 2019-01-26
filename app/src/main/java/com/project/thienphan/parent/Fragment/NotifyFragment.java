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

import com.project.thienphan.parent.Adapter.CommentAdapter;
import com.project.thienphan.parent.Model.Comment;
import com.project.thienphan.parent.Model.NotifyMessage;
import com.project.thienphan.supportstudent.R;

import java.util.ArrayList;

public class NotifyFragment extends Fragment {

    View view;
    RecyclerView rcvNotify;
    CommentAdapter commentAdapter;
    ArrayList<Comment> lstSubject;

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
        getData();
        commentAdapter = new CommentAdapter(lstSubject, getContext(), new AdapterView.OnItemClickListener() {
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
        ArrayList<NotifyMessage> list = new ArrayList<>();
        list.add(new NotifyMessage("Kính chào quý PH. Kính gửi quý phụ huynh quá trình học của bé...", false));
        ArrayList<NotifyMessage> list1 = new ArrayList<>();
        list.add(new NotifyMessage("Cảm ơn thầy, cô...", true));
        this.lstSubject.add(new Comment("Kids - Family and Friends 2", false, list));
        this.lstSubject.add(new Comment("Kids - Family&Friends 2B", false, list1));
        this.lstSubject.add(new Comment("Kids - Family&Friends 2A", false, list));
        this.lstSubject.add(new Comment("Kids - Canbridge Starter 6 weeks", false, list1));
        this.lstSubject.add(new Comment("Kids - Family&Friends 1B", false, list));
        this.lstSubject.add(new Comment("Kids - Family&Friends 1A", false, list1));
        this.lstSubject.add(new Comment("Kids - Family and Friends Starter", false, list));
    }
}
