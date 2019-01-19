package com.project.thienphan.parent.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.project.thienphan.supportstudent.R;
import com.project.thienphan.timesheet.Model.LearningResult;

import java.util.ArrayList;

public class LearningResultAdapter extends RecyclerView.Adapter<LearningResultAdapter.LearningResultViewHolder> {

    ArrayList<LearningResult> data;

    public LearningResultAdapter(ArrayList<LearningResult> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public LearningResultViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.item_learning_result,viewGroup,false);
        return new LearningResultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LearningResultViewHolder learningResultViewHolder, int i) {
        if (data.get(i) instanceof LearningResult){
            learningResultViewHolder.tvSubjectName.setText(data.get(i).getSubjectName());
            learningResultViewHolder.tvGiuaKy.setText(data.get(i).getGiuaKy().toString());
            learningResultViewHolder.tvCuoiKy.setText(data.get(i).getCuoiKy().toString());
            learningResultViewHolder.tvTrungBinh.setText(data.get(i).getTrungBinh().toString());
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class LearningResultViewHolder extends RecyclerView.ViewHolder {
        TextView tvSubjectName, tvGiuaKy, tvCuoiKy, tvTrungBinh;
        public LearningResultViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSubjectName = itemView.findViewById(R.id.tv_item_learning_result_sub_name);
            tvGiuaKy = itemView.findViewById(R.id.tv_item_learning_result_giua_ky);
            tvCuoiKy = itemView.findViewById(R.id.tv_item_learning_result_cuoi_ky);
            tvTrungBinh = itemView.findViewById(R.id.tv_item_learning_result_trung_binh);
        }
    }
}
