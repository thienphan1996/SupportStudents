package com.project.thienphan.parent.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.thienphan.parent.Model.NotifyMessage;
import com.project.thienphan.supportstudent.R;

import java.util.ArrayList;

public class NotifyMessageAdapter extends RecyclerView.Adapter<NotifyMessageAdapter.MessageViewHolder> {

    ArrayList<NotifyMessage> data;

    public NotifyMessageAdapter(ArrayList<NotifyMessage> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public NotifyMessageAdapter.MessageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        if (i == 1){
            View view = inflater.inflate(R.layout.teacher_message, viewGroup, false);
            return new TearcherMessageViewHolder(view);
        }
        return new ParentMessageViewHolder(inflater.inflate(R.layout.parent_message, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NotifyMessageAdapter.MessageViewHolder messageViewHolder, int i) {
        NotifyMessage message = this.data.get(i);
        if (message instanceof NotifyMessage){
            messageViewHolder.bind(message);
        }
    }

    @Override
    public int getItemViewType(int position) {
        boolean isParent = this.data.get(position).isParent();
        int viewType = isParent ? 2 : 1;
        return viewType;
    }

    @Override
    public int getItemCount() {
        return this.data.size();
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder{
        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void bind(NotifyMessage message){

        }
    }

    public class TearcherMessageViewHolder extends NotifyMessageAdapter.MessageViewHolder{
        ImageView imgTeacherMessage;
        TextView tvTeacherMessage;
        public TearcherMessageViewHolder(@NonNull View itemView) {
            super(itemView);
            imgTeacherMessage = itemView.findViewById(R.id.img_item_learning_result_tearcher_message);
            tvTeacherMessage = itemView.findViewById(R.id.tv_item_learning_result_tearcher_message);
        }

        @Override
        public void bind(NotifyMessage message) {
            tvTeacherMessage.setText(message.getMessage());
        }
    }

    public class ParentMessageViewHolder extends NotifyMessageAdapter.MessageViewHolder{
        ImageView imgParentMessage;
        TextView tvParentMessage;
        public ParentMessageViewHolder(@NonNull View itemView) {
            super(itemView);
            imgParentMessage = itemView.findViewById(R.id.img_item_learning_result_parent_message);
            tvParentMessage = itemView.findViewById(R.id.tv_item_learning_result_parent_message);
        }

        @Override
        public void bind(NotifyMessage message) {
            tvParentMessage.setText(message.getMessage());
        }
    }
}
