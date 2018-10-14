package com.project.thienphan.teacher.Adapter;

import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.thienphan.supportstudent.R;
import com.project.thienphan.timesheet.Model.StudentInfomation;

import java.util.ArrayList;

public class CheckStudentAdapter extends RecyclerView.Adapter<CheckStudentAdapter.CheckStudentViewHolder> {

    ArrayList<StudentInfomation> Data;
    AdapterView.OnItemClickListener onClick;
    Resources resources;

    public CheckStudentAdapter(Resources resources,ArrayList<StudentInfomation> data, AdapterView.OnItemClickListener onClick) {
        this.resources = resources;
        Data = data;
        this.onClick = onClick;
    }

    @NonNull
    @Override
    public CheckStudentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.item_check_student,viewGroup,false);
        return new CheckStudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CheckStudentViewHolder checkStudentViewHolder, final int i) {
        checkStudentViewHolder.tvNameCheckStudent.setText(Data.get(i).getHoTen());
        if (Data.get(i).getGioiTinh().equals("Nữ")){
            checkStudentViewHolder.imgCheckStudent.setImageDrawable(resources.getDrawable(R.drawable.ic_female));
        }
        else {
            checkStudentViewHolder.imgCheckStudent.setImageDrawable(resources.getDrawable(R.drawable.ic_male));
        }
        if (!Data.get(i).isCheck()){
            checkStudentViewHolder.lnCheckStudent.setBackgroundColor(resources.getColor(R.color.bg_gray_light));
        }
        else {
            checkStudentViewHolder.lnCheckStudent.setBackground(resources.getDrawable(R.drawable.bg_img_student_leave));
        }
        checkStudentViewHolder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClick.onItemClick(null,view,checkStudentViewHolder.getAdapterPosition(),0);
            }
        });
    }

    @Override
    public int getItemCount() {
        return Data.size();
    }

    public class CheckStudentViewHolder extends RecyclerView.ViewHolder {
        ImageView imgCheckStudent;
        TextView tvNameCheckStudent;
        LinearLayout lnCheckStudent;
        View container;
        public CheckStudentViewHolder(@NonNull View itemView) {
            super(itemView);
            container = itemView;
            lnCheckStudent = itemView.findViewById(R.id.ln_item_check_student);
            imgCheckStudent = itemView.findViewById(R.id.img_item_check_student);
            tvNameCheckStudent = itemView.findViewById(R.id.tv_item_name_check_student);
        }
    }
}
