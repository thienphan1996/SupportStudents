package com.project.thienphan.parent.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.thienphan.parent.Model.Comment;
import com.project.thienphan.parent.Model.NotifyMessage;
import com.project.thienphan.supportstudent.R;
import com.rilixtech.materialfancybutton.MaterialFancyButton;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {

    ArrayList<Comment> data;
    AdapterView.OnItemClickListener onClick;
    Context context;

    public CommentAdapter(ArrayList<Comment> data, Context context, AdapterView.OnItemClickListener onClick) {
        this.data = data;
        this.onClick = onClick;
        this.context = context;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.item_learning_result_notify, viewGroup, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CommentViewHolder commentViewHolder, int i) {
        Comment item = this.data.get(i);
        commentViewHolder.tvSubjectName.setText(item.getSubjectName());
        if (item.isShowModal()){
            commentViewHolder.lnMessages.setVisibility(View.VISIBLE);
        }
        else commentViewHolder.lnMessages.setVisibility(View.GONE);

        commentViewHolder.lstItemMessage.clear();
        commentViewHolder.lstItemMessage.addAll(item.getMessages());
        commentViewHolder.notifyMessageAdapter.notifyDataSetChanged();

        commentViewHolder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClick.onItemClick(null, view, commentViewHolder.getAdapterPosition(), 0);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.data.size();
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder {
        TextView tvSubjectName;
        LinearLayout lnMessages;
        RecyclerView rcvItemMessage;
        ArrayList<NotifyMessage> lstItemMessage;
        NotifyMessageAdapter notifyMessageAdapter;
        EditText edtParentMessage;
        MaterialFancyButton btnParentSendMessage;
        View container;
        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            container = itemView;
            tvSubjectName = itemView.findViewById(R.id.tv_item_learning_result_sub_title);
            lnMessages = itemView.findViewById(R.id.ln_item_learning_result_modal);
            edtParentMessage = itemView.findViewById(R.id.edt_parent_message);
            btnParentSendMessage = itemView.findViewById(R.id.btn_parent_send_message);

            rcvItemMessage = itemView.findViewById(R.id.rcv_item_learning_result_notify);
            lstItemMessage = new ArrayList<>();
            notifyMessageAdapter = new NotifyMessageAdapter(lstItemMessage);
            LinearLayoutManager layoutManager = new LinearLayoutManager(context);
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            rcvItemMessage.setLayoutManager(layoutManager);
            rcvItemMessage.setAdapter(notifyMessageAdapter);

            btnParentSendMessage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    lstItemMessage.add(new NotifyMessage(edtParentMessage.getText().toString(), true));
                    notifyMessageAdapter.notifyDataSetChanged();
                    edtParentMessage.setText("");
                }
            });
        }
    }
}
