package com.project.thienphan.timesheet.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.thienphan.supportstudent.R;
import com.project.thienphan.timesheet.Model.TimesheetItem;

import java.util.ArrayList;

public class TimesheetAdapter extends RecyclerView.Adapter<TimesheetAdapter.TimesheetHolder> {

    ArrayList<TimesheetItem> Data;
    AdapterView.OnItemClickListener onClickListener;
    ArrayList<Integer> itemClick;

    public TimesheetAdapter(ArrayList<TimesheetItem> data) {
        Data = data;
    }


    @NonNull
    @Override
    public TimesheetHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.item_timesheet,viewGroup,false);
        return new TimesheetHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final TimesheetHolder timesheetHolder, int i) {
        timesheetHolder.tvSubjectCode.setText(this.Data.get(i).getSubjectCode());
        timesheetHolder.tvSubjectName.setText(this.Data.get(i).getSubjectName());
        timesheetHolder.tvSubjectGroup.setText(this.Data.get(i).getSubjectGroup() + "");
        timesheetHolder.tvSubjectTeacher.setText(this.Data.get(i).getSubjectTeacher());
        timesheetHolder.tvSubjectTime.setText(this.Data.get(i).getSubjectTime());
        timesheetHolder.tvSubjectLocation.setText(this.Data.get(i).getSubjectLocation());
        timesheetHolder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setOnItemClick(timesheetHolder.getAdapterPosition());
            }
        });
    }

    private void setOnItemClick(int position) {

    }

    @Override
    public int getItemCount() {
        return Data.size();
    }

    public class TimesheetHolder extends RecyclerView.ViewHolder {

        TextView tvSubjectCode, tvSubjectName, tvSubjectGroup, tvSubjectTeacher, tvSubjectTime, tvSubjectLocation;
        View container;
        ImageView imgChecked;
        public TimesheetHolder(@NonNull View itemView) {
            super(itemView);
            container = itemView;
            tvSubjectCode = itemView.findViewById(R.id.tv_item_ts_subject_code);
            tvSubjectName = itemView.findViewById(R.id.tv_item_ts_subject_name);
            tvSubjectGroup = itemView.findViewById(R.id.tv_item_ts_group);
            tvSubjectTeacher = itemView.findViewById(R.id.tv_item_ts_teacher_name);
            tvSubjectTime = itemView.findViewById(R.id.tv_item_ts_time);
            tvSubjectLocation = itemView.findViewById(R.id.tv_item_ts_location);
            imgChecked = itemView.findViewById(R.id.img_delete_ts_item_checked);
        }
    }
}
