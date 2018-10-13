package com.project.thienphan.timesheet.Adapter;

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
import com.project.thienphan.timesheet.Model.NotificationItem;

import java.util.ArrayList;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {

    ArrayList<NotificationItem> Data;
    AdapterView.OnItemClickListener onClick;
    Resources resources;

    public NotificationAdapter(ArrayList<NotificationItem> data,Resources resources, AdapterView.OnItemClickListener onClick) {
        Data = data;
        this.onClick = onClick;
        this.resources = resources;
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.item_notification,viewGroup,false);
        return new NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final NotificationViewHolder notificationViewHolder, int i) {
        notificationViewHolder.tvSubject.setText(Data.get(i).getSubject());
        notificationViewHolder.tvTitle.setText(Data.get(i).getTitle());
        notificationViewHolder.tvMessage.setText(Data.get(i).getMessage());
        notificationViewHolder.tvTime.setText(Data.get(i).getTime());
        if (!Data.get(i).isSeen()){
            notificationViewHolder.lnNotification.setBackgroundColor(resources.getColor(R.color.bg_gray_light));
        }
        else {
            notificationViewHolder.lnNotification.setBackgroundColor(resources.getColor(android.R.color.white));
        }
        notificationViewHolder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClick.onItemClick(null,view,notificationViewHolder.getAdapterPosition(),0);
            }
        });
        setImageFromTitle(Data.get(i).getTitle(),notificationViewHolder.imgNotification);
    }

    private void setImageFromTitle(String title,ImageView imageView) {
        if (title.contains("thi")){
            imageView.setImageDrawable(this.resources.getDrawable(R.drawable.ic_exam));
        }
        else if (title.toLowerCase().contains("nghi") || title.toLowerCase().contains("nghỉ")){
            imageView.setImageDrawable(this.resources.getDrawable(R.drawable.ic_off));
        }
        else if (title.toLowerCase().contains("thay doi") || title.toLowerCase().contains("thay đổi")){
            imageView.setImageDrawable(this.resources.getDrawable(R.drawable.adjust_24dp));
        }
        else if (title.toLowerCase().contains("thong bao") || title.toLowerCase().contains("thông báo")){
            imageView.setImageDrawable(this.resources.getDrawable(R.drawable.ic_notifications_black_24dp));
        }
        else {
            imageView.setImageDrawable(this.resources.getDrawable(R.mipmap.ic_launcher));
        }
    }

    @Override
    public int getItemCount() {
        return Data.size();
    }

    public class NotificationViewHolder extends RecyclerView.ViewHolder{
        ImageView imgNotification;
        TextView tvSubject,tvTitle,tvMessage,tvTime;
        View container;
        LinearLayout lnNotification;
        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);
            container = itemView;
            lnNotification = itemView.findViewById(R.id.ln_item_notification);
            imgNotification = itemView.findViewById(R.id.img_item_notification);
            tvSubject = itemView.findViewById(R.id.tv_item_notification_subject);
            tvTitle = itemView.findViewById(R.id.tv_item_notification_title);
            tvMessage = itemView.findViewById(R.id.tv_item_notification_message);
            tvTime = itemView.findViewById(R.id.tv_item_notification_time);
        }
    }
}
