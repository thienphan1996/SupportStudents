package com.project.thienphan.timesheet.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.project.thienphan.supportstudent.R;
import com.project.thienphan.timesheet.Model.TimesheetItem;

import java.util.ArrayList;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeViewHolder> {

    ArrayList<TimesheetItem> Data;
    AdapterView.OnItemClickListener onClickListener;

    public HomeAdapter(ArrayList<TimesheetItem> data, AdapterView.OnItemClickListener onClickListener) {
        Data = data;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.item_timesheet,viewGroup,false);
        return new HomeAdapter.HomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final HomeViewHolder homeViewHolder, int i) {
        homeViewHolder.tvSubjectName.setText(this.Data.get(i).getSubjectName());
        homeViewHolder.tvSubjectGroup.setText(this.Data.get(i).getSubjectGroup() + "");
        homeViewHolder.tvSubjectTeacher.setText(this.Data.get(i).getSubjectTeacher());
        homeViewHolder.tvSubjectTime.setText(this.Data.get(i).getSubjectTime());
        homeViewHolder.tvSubjectLocation.setText(this.Data.get(i).getSubjectLocation());
        homeViewHolder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickListener.onItemClick(null,view,homeViewHolder.getAdapterPosition(),0);
            }
        });
    }

    @Override
    public int getItemCount() {
        return Data.size();
    }

    public class HomeViewHolder extends RecyclerView.ViewHolder {
        TextView tvSubjectName, tvSubjectGroup, tvSubjectTeacher, tvSubjectTime, tvSubjectLocation;
        View container;
        public HomeViewHolder(@NonNull View itemView) {
            super(itemView);
            container = itemView;
            tvSubjectName = itemView.findViewById(R.id.tv_item_ts_subject_name);
            tvSubjectGroup = itemView.findViewById(R.id.tv_item_ts_group);
            tvSubjectTeacher = itemView.findViewById(R.id.tv_item_ts_teacher_name);
            tvSubjectTime = itemView.findViewById(R.id.tv_item_ts_time);
            tvSubjectLocation = itemView.findViewById(R.id.tv_item_ts_location);
        }
    }
}
