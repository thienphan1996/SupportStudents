package com.project.thienphan.teacher.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.project.thienphan.supportstudent.R;
import com.project.thienphan.timesheet.Model.LeaveStudentModel;

import java.util.ArrayList;

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ReportViewHolder> {

    ArrayList<LeaveStudentModel> Data;

    public ReportAdapter(ArrayList<LeaveStudentModel> data) {
        Data = data;
    }

    @NonNull
    @Override
    public ReportViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.item_leave_report,viewGroup,false);
        return new ReportViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReportViewHolder reportViewHolder, int i) {
        reportViewHolder.tvName.setText(Data.get(i).getStudentName());
        reportViewHolder.tvLeaveTotal.setText(Data.get(i).getLeaveDayTotal() + "");
    }

    @Override
    public int getItemCount() {
        return Data.size();
    }

    public class ReportViewHolder extends RecyclerView.ViewHolder {
        TextView tvName,tvLeaveTotal;
        public ReportViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_item_report_name);
            tvLeaveTotal = itemView.findViewById(R.id.tv_item_report_leave_total);
        }
    }
}
