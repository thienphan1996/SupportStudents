package com.project.thienphan.timesheet.Adapter;

import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.thienphan.supportstudent.R;
import com.project.thienphan.timesheet.Model.TagContent;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsHolder>  {

    ArrayList<TagContent> data;
    AdapterView.OnItemClickListener onClick;
    Resources resources;

    public NewsAdapter(ArrayList<TagContent> data, Resources resources, AdapterView.OnItemClickListener onItemClickListener) {
        this.data = data;
        this.onClick = onItemClickListener;
        this.resources = resources;
    }

    @NonNull
    @Override
    public NewsHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.news_item,viewGroup,false);
        return new NewsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final NewsHolder newsHolder, int i) {
        newsHolder.txtNewsContent.setText(this.data.get(i).getBody());
        newsHolder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClick.onItemClick(null,view,newsHolder.getAdapterPosition(),0);
            }
        });
        SetImageFromBodyType(newsHolder.imgItemNews,this.data.get(i));
    }

    public void SetImageFromBodyType(ImageView imgItemNews, TagContent content) {
        switch (content.getBodyType()){
            case TagContent.INFO:
                imgItemNews.setImageDrawable(this.resources.getDrawable(R.drawable.ic_info_black_24dp));
                break;
            case TagContent.NOTIFY:
                imgItemNews.setImageDrawable(this.resources.getDrawable(R.drawable.ic_notifications_black_24dp));
                break;
            case TagContent.PLAN:
                imgItemNews.setImageDrawable(this.resources.getDrawable(R.drawable.ic_event_note_black_24dp));
                break;
            case TagContent.CALENDAR:
                imgItemNews.setImageDrawable(this.resources.getDrawable(R.drawable.ic_perm_contact_calendar_black_24dp));
                break;
            case TagContent.REGISTER:
                imgItemNews.setImageDrawable(this.resources.getDrawable(R.drawable.register_24dp));
                break;
            case TagContent.STUDENT:
                imgItemNews.setImageDrawable(this.resources.getDrawable(R.drawable.student_24dp_green));
                break;
            case TagContent.WORK:
                imgItemNews.setImageDrawable(this.resources.getDrawable(R.drawable.work_24dp));
                break;
            case TagContent.ADJUST:
                imgItemNews.setImageDrawable(this.resources.getDrawable(R.drawable.adjust_24dp));
                break;
            case TagContent.RESULT:
                imgItemNews.setImageDrawable(this.resources.getDrawable(R.drawable.result_24dp));
                break;
            case TagContent.WELCOME:
                imgItemNews.setImageDrawable(this.resources.getDrawable(R.drawable.welcome_24dp));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class NewsHolder extends RecyclerView.ViewHolder{

        ImageView imgItemNews;
        TextView txtNewsContent;
        View container;
        public NewsHolder(@NonNull View itemView) {
            super(itemView);
            container = itemView;
            imgItemNews = itemView.findViewById(R.id.img_item_news);
            txtNewsContent = itemView.findViewById(R.id.txt_item_content);
        }
    }
}
