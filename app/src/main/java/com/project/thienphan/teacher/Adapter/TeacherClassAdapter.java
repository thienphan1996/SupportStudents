package com.project.thienphan.teacher.Adapter;

import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.thienphan.supportstudent.R;
import com.project.thienphan.timesheet.Model.ClassItem;

import java.util.ArrayList;

public class TeacherClassAdapter extends RecyclerView.Adapter<TeacherClassAdapter.TeacherClassViewHolder> {

    ArrayList<ClassItem> Data;
    Resources resources;
    AdapterView.OnItemClickListener onClick;
    int fromActivity = 0;

    public TeacherClassAdapter(int from, ArrayList<ClassItem> data, Resources resources, AdapterView.OnItemClickListener onItemClickListener) {
        Data = data;
        this.resources = resources;
        onClick = onItemClickListener;
        this.fromActivity = from;
    }

    @NonNull
    @Override
    public TeacherClassViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.item_teacher_class,viewGroup,false);
        return new TeacherClassViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final TeacherClassViewHolder teacherClassViewHolder, int i) {
        if (Data.get(i).getSubjectName().isEmpty()){
            if (i <= 11){
                teacherClassViewHolder.tvClassName.setVisibility(View.GONE);
                teacherClassViewHolder.tvClassTime.setVisibility(View.GONE);
                teacherClassViewHolder.tvClassLocation.setVisibility(View.GONE);
                teacherClassViewHolder.tvLableTime.setVisibility(View.GONE);
                if (this.fromActivity == 1){
                    teacherClassViewHolder.lnTeacherClass.setBackground(resources.getDrawable(R.color.bg_gray_light));
                }
                else {
                    if (i <= 5){
                        teacherClassViewHolder.lnTeacherClass.setBackground(resources.getDrawable(R.drawable.bg_dayofweek_morning_disable));
                    }
                    else {
                        teacherClassViewHolder.lnTeacherClass.setBackground(resources.getDrawable(R.drawable.bg_dayofweek_afternoon_disable));
                    }
                }
            }
            else {
                teacherClassViewHolder.lnTeacherClass.setVisibility(View.GONE);
            }
        }
        else {
            teacherClassViewHolder.tvClassName.setText(Data.get(i).getSubjectName());
            teacherClassViewHolder.tvClassTime.setText(Data.get(i).getSubjectTime());
            teacherClassViewHolder.tvClassLocation.setText(Data.get(i).getSubjectLocation());
            SetBackgroundColor(teacherClassViewHolder.lnTeacherClass, Data.get(i).getDayofWeek(), i);
        }
        teacherClassViewHolder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClick.onItemClick(null,view,teacherClassViewHolder.getAdapterPosition(),0);
            }
        });
    }

    private void SetBackgroundColor(LinearLayout lnTeacherClass,long dayofweek, int index) {
        if (this.fromActivity == 1){
            switch ((int)dayofweek){
                case 2:
                    lnTeacherClass.setBackground(resources.getDrawable(R.color.bg_monday));
                    break;
                case 3:
                    lnTeacherClass.setBackground(resources.getDrawable(R.color.bg_tuesday));
                    break;
                case 4:
                    lnTeacherClass.setBackground(resources.getDrawable(R.color.bg_webnesday));
                    break;
                case 5:
                    lnTeacherClass.setBackground(resources.getDrawable(R.color.bg_thursday));
                    break;
                case 6:
                    lnTeacherClass.setBackground(resources.getDrawable(R.color.bg_friday));
                    break;
                case 7:
                    lnTeacherClass.setBackground(resources.getDrawable(R.color.bg_satuday));
                    break;
            }
        }
        else if (fromActivity == 2){
            if (index <= 5){
                lnTeacherClass.setBackground(resources.getDrawable(R.drawable.bg_dayofweek_morning));
            }
            else {
                lnTeacherClass.setBackground(resources.getDrawable(R.drawable.bg_dayofweek_afternoon));
            }
        }
    }

    @Override
    public int getItemCount() {
        return Data.size();
    }

    public class TeacherClassViewHolder extends RecyclerView.ViewHolder {

        LinearLayout lnTeacherClass;
        TextView tvClassName, tvClassTime, tvClassLocation, tvLableTime;
        View container;
        public TeacherClassViewHolder(@NonNull View itemView) {
            super(itemView);
            container = itemView;
            lnTeacherClass = itemView.findViewById(R.id.ln_item_teacher_class);
            tvClassName = itemView.findViewById(R.id.tv_item_teacher_class_name);
            tvClassTime = itemView.findViewById(R.id.tv_item_teacher_class_time);
            tvLableTime = itemView.findViewById(R.id.tv_item_lable_class_time);
            tvClassLocation = itemView.findViewById(R.id.tv_item_teacher_class_location);
        }
    }
}
