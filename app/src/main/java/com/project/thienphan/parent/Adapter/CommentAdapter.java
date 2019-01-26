package com.project.thienphan.parent.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.project.thienphan.parent.Model.Comment;
import com.project.thienphan.parent.Model.NotifyMessage;
import com.project.thienphan.supportstudent.R;
import com.project.thienphan.timesheet.Common.TimesheetPreferences;
import com.project.thienphan.timesheet.Database.TimesheetDatabase;
import com.project.thienphan.timesheet.Support.InfoDialog;
import com.project.thienphan.timesheet.Support.TimesheetProgressDialog;
import com.rilixtech.materialfancybutton.MaterialFancyButton;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {

    ArrayList<Comment> data;
    AdapterView.OnItemClickListener onClick;
    Context context;
    DatabaseReference mydb;
    TimesheetPreferences timesheetPreferences;
    Activity activity;
    String user = "";

    public CommentAdapter(ArrayList<Comment> data, Context context, Activity activity, AdapterView.OnItemClickListener onClick) {
        this.data = data;
        this.onClick = onClick;
        this.context = context;
        this.mydb = TimesheetDatabase.getTimesheetDatabase();
        this.timesheetPreferences = new TimesheetPreferences(context);
        this.activity = activity;
        if (this.timesheetPreferences != null){
            user = timesheetPreferences.get(this.context.getString(R.string.USER), String.class);
        }
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.item_learning_result_notify, viewGroup, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CommentViewHolder commentViewHolder, final int i) {
        Comment item = this.data.get(i);
        commentViewHolder.tvSubjectName.setText(item.getSubjectName());

        if (data.get(i).isShowModal() && data.get(i).getMessages() != null && data.get(i).getMessages().size() > 0){
            commentViewHolder.lnMessages.setVisibility(View.VISIBLE);
            commentViewHolder.imgModal.setImageDrawable(context.getDrawable(R.drawable.ic_keyboard_arrow_up_black_36dp));
        }
        else {
            commentViewHolder.lnMessages.setVisibility(View.GONE);
            commentViewHolder.imgModal.setImageDrawable(context.getDrawable(R.drawable.ic_keyboard_arrow_down_black_36dp));
        }

        if (item.getMessages() != null && item.getMessages().size() > 0){
            commentViewHolder.notifyMessageAdapter = new NotifyMessageAdapter(data.get(i).getMessages());
            LinearLayoutManager layoutManager = new LinearLayoutManager(context);
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            commentViewHolder.rcvItemMessage.setLayoutManager(layoutManager);
            commentViewHolder.rcvItemMessage.setAdapter(commentViewHolder.notifyMessageAdapter);
            commentViewHolder.notifyMessageAdapter.notifyDataSetChanged();
        }

        commentViewHolder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClick.onItemClick(null, view, commentViewHolder.getAdapterPosition(), 0);
            }
        });


        commentViewHolder.btnParentSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String parentReply = commentViewHolder.edtParentMessage.getText().toString();
                String subjectCode = data.get(i).getSubjectCode();
                ArrayList<NotifyMessage> messages = data.get(i).getMessages();
                if (!parentReply.isEmpty() && !user.isEmpty()){
                    NotifyMessage message = messages.get(messages.size() - 1);
                    if (!message.isParent() && !message.isHasReply()){
                        messages.get(messages.size() - 1).setHasReply(true);
                        NotifyMessage parentMessage = new NotifyMessage();
                        parentMessage.setId(message.getId());
                        parentMessage.setHasReply(true);
                        parentMessage.setParent(true);
                        parentMessage.setMessage(commentViewHolder.edtParentMessage.getText().toString());
                        messages.add(parentMessage);
                        mydb.child(context.getString(R.string.CHILD_TEACHER_COMMENT) + "/" + user.toUpperCase() + "/" + subjectCode.toUpperCase() + "/messages").setValue(messages);
                    }
                    else {
                        InfoDialog.ShowInfoDiaLog(activity, "Thông báo", "Quý phụ huynh đã có phản hồi trước đó, xin phản hồi lại ở lần nhận xét tiếp theo.");
                    }
                }
                commentViewHolder.notifyMessageAdapter.notifyDataSetChanged();
                commentViewHolder.edtParentMessage.setText("");
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
        NotifyMessageAdapter notifyMessageAdapter;
        EditText edtParentMessage;
        MaterialFancyButton btnParentSendMessage;
        ImageView imgModal;
        View container;
        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            container = itemView;
            tvSubjectName = itemView.findViewById(R.id.tv_item_learning_result_sub_title);
            lnMessages = itemView.findViewById(R.id.ln_item_learning_result_modal);
            edtParentMessage = itemView.findViewById(R.id.edt_parent_message);
            btnParentSendMessage = itemView.findViewById(R.id.btn_parent_send_message);
            imgModal = itemView.findViewById(R.id.img_item_learning_result_modal);

            rcvItemMessage = itemView.findViewById(R.id.rcv_item_learning_result_notify);
        }
    }
}
