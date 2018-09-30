package com.project.thienphan.teacher.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.project.thienphan.supportstudent.R;
import com.project.thienphan.timesheet.Model.ClassItem;

import java.util.ArrayList;

public class TeacherAdapter extends RecyclerView.Adapter<TeacherAdapter.TeacherHolder> {

    ArrayList<ClassItem> Data;
    AdapterView.OnItemClickListener onClick;

    public TeacherAdapter(ArrayList<ClassItem> data, AdapterView.OnItemClickListener onClick) {
        Data = data;
        this.onClick = onClick;
    }

    @NonNull
    @Override
    public TeacherHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.item_class,viewGroup,false);
        return new TeacherHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final TeacherHolder teacherHolder, int i) {
        teacherHolder.tvSubName.setText(Data.get(i).getSubjectName());
        teacherHolder.tvStuNumber.setText(Data.get(i).getStudentNumber() + "");
        teacherHolder.tvRoom.setText(Data.get(i).getSubjectLocation());
        teacherHolder.tvDayOfWeek.setText(Data.get(i).getDayofWeek() + "");
        teacherHolder.tvTime.setText(Data.get(i).getSubjectTime() + "");
        teacherHolder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClick.onItemClick(null,view,teacherHolder.getAdapterPosition(),0);
            }
        });
    }

    @Override
    public int getItemCount() {
        return Data.size();
    }

    public class TeacherHolder extends RecyclerView.ViewHolder {
        TextView tvSubName,tvStuNumber,tvRoom,tvDayOfWeek,tvTime;
        View container;
        public TeacherHolder(@NonNull View itemView) {
            super(itemView);
            container = itemView;
            tvSubName = itemView.findViewById(R.id.tv_item_teacher_sub_name);
            tvStuNumber = itemView.findViewById(R.id.tv_item_teacher_stu_number);
            tvRoom = itemView.findViewById(R.id.tv_item_teacher_room);
            tvDayOfWeek = itemView.findViewById(R.id.tv_item_teacher_dayofweek);
            tvTime = itemView.findViewById(R.id.tv_item_teacher_time);
        }
    }
}
