package com.project.thienphan.parent.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.thienphan.supportstudent.R;
import com.project.thienphan.timesheet.Model.LearningResult;

import java.util.ArrayList;

public class LearningResultAdapter extends RecyclerView.Adapter<LearningResultAdapter.LearningResultViewHolder> {

    ArrayList<LearningResult> data;
    AdapterView.OnItemClickListener onClick;

    public LearningResultAdapter(ArrayList<LearningResult> data, AdapterView.OnItemClickListener onClick) {
        this.data = data;
        this.onClick = onClick;
    }

    @NonNull
    @Override
    public LearningResultViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.item_learning_result,viewGroup,false);
        return new LearningResultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final LearningResultViewHolder learningResultViewHolder, int i) {
        if (data.get(i) instanceof LearningResult){
            LearningResult item = data.get(i);
            learningResultViewHolder.tvSubjectName.setText(data.get(i).getSubjectName());
            Double sumScore = 0.0;
            if (item.isShowModal()){
                learningResultViewHolder.lnModal.setVisibility(View.VISIBLE);
            }
            else learningResultViewHolder.lnModal.setVisibility(View.GONE);

            if (item.getKillNumberOne() > 0.0){
                learningResultViewHolder.lnNumOne.setVisibility(View.VISIBLE);
                learningResultViewHolder.tvScoreOne.setText(item.getKillNumberOne().toString());
                sumScore+=item.getKillNumberOne();
            }
            if (item.getKillNumberTwo() > 0.0) {
                learningResultViewHolder.lnNumTwo.setVisibility(View.VISIBLE);
                learningResultViewHolder.tvScoreTwo.setText(item.getKillNumberTwo().toString());
                sumScore+=item.getKillNumberTwo();
            }
            if (item.getKillNumberThree() > 0.0) {
                learningResultViewHolder.lnNumThree.setVisibility(View.VISIBLE);
                learningResultViewHolder.tvScoreThree.setText(item.getKillNumberThree().toString());
                sumScore+=item.getKillNumberThree();
            }
            if (item.getKillNumberFour() > 0.0) {
                learningResultViewHolder.lnNumFour.setVisibility(View.VISIBLE);
                learningResultViewHolder.tvScoreFour.setText(item.getKillNumberFour().toString());
                sumScore+=item.getKillNumberFour();
            }
            if (item.getKillNumberFive() > 0.0) {
                learningResultViewHolder.lnNumFive.setVisibility(View.VISIBLE);
                learningResultViewHolder.tvScoreFive.setText(item.getKillNumberFive().toString());
                sumScore+=item.getKillNumberFive();
            }
            learningResultViewHolder.tvSumScore.setText(sumScore.toString());
            if (item.getLastComment() != null && !item.getLastComment().isEmpty()){
                learningResultViewHolder.tvLastComment.setVisibility(View.VISIBLE);
                learningResultViewHolder.tvLastComment.setText(item.getLastComment());
            }
        }
        learningResultViewHolder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClick.onItemClick(null, view, learningResultViewHolder.getAdapterPosition(), 0);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class LearningResultViewHolder extends RecyclerView.ViewHolder {
        View container;
        LinearLayout lnNumOne, lnNumTwo, lnNumThree, lnNumFour, lnNumFive, lnModal;
        TextView tvSubjectName, tvScoreOne, tvScoreTwo, tvScoreThree, tvScoreFour, tvScoreFive, tvSumScore, tvLastComment;
        public LearningResultViewHolder(@NonNull View itemView) {
            super(itemView);
            container = itemView;
            tvSubjectName = itemView.findViewById(R.id.tv_item_learning_result_sub_name);
            lnModal = itemView.findViewById(R.id.ln_item_learning_result_modal);
            lnNumOne = itemView.findViewById(R.id.ln_item_learning_result_num_one);
            lnNumTwo = itemView.findViewById(R.id.ln_item_learning_result_num_two);
            lnNumThree = itemView.findViewById(R.id.ln_item_learning_result_num_three);
            lnNumFour = itemView.findViewById(R.id.ln_item_learning_result_num_four);
            lnNumFive = itemView.findViewById(R.id.ln_item_learning_result_num_five);

            tvScoreOne = itemView.findViewById(R.id.tv_item_learning_result_score_one);
            tvScoreTwo = itemView.findViewById(R.id.tv_item_learning_result_score_two);
            tvScoreThree = itemView.findViewById(R.id.tv_item_learning_result_score_three);
            tvScoreFour = itemView.findViewById(R.id.tv_item_learning_result_score_four);
            tvScoreFive = itemView.findViewById(R.id.tv_item_learning_result_score_five);

            tvSumScore = itemView.findViewById(R.id.tv_item_learning_result_sum_score);
            tvLastComment = itemView.findViewById(R.id.tv_item_learning_result_last_comment);
        }
    }
}
