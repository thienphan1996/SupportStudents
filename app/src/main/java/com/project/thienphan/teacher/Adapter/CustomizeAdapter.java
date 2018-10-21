package com.project.thienphan.teacher.Adapter;

import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.project.thienphan.supportstudent.R;

import java.util.ArrayList;

public class CustomizeAdapter extends RecyclerView.Adapter<CustomizeAdapter.CustomizeViewHolder> {

    ArrayList<String> Data;
    AdapterView.OnItemClickListener onClick;
    Resources resources;

    public CustomizeAdapter(Resources resources, AdapterView.OnItemClickListener onClick) {
        this.onClick = onClick;
        this.resources = resources;
        Data = new ArrayList<>();
        Data.add("Tin tức");
        Data.add("Cố vấn học tập");
        Data.add("Liên kết");
        Data.add("Đăng xuất");
    }

    @NonNull
    @Override
    public CustomizeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.item_customize,viewGroup,false);
        return new CustomizeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CustomizeViewHolder customizeViewHolder, int i) {
        customizeViewHolder.tvLable.setText(Data.get(i).toString());
        SetImageView(customizeViewHolder.imgCustomize,i);
        if (i == 3){
            customizeViewHolder.viewCustomize.setVisibility(View.GONE);
        }
        customizeViewHolder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClick.onItemClick(null,view,customizeViewHolder.getAdapterPosition(),0);
            }
        });
    }

    private void SetImageView(ImageView imgCustomize, int i) {
        switch (i){
            case 0:
                imgCustomize.setImageDrawable(resources.getDrawable(R.drawable.ic_fiber_new_black_24dp));
                break;
            case 1:
                imgCustomize.setImageDrawable(resources.getDrawable(R.drawable.ic_people));
                break;
            case 2:
                imgCustomize.setImageDrawable(resources.getDrawable(R.drawable.ic_link));
                break;
            case 3:
                imgCustomize.setImageDrawable(resources.getDrawable(R.drawable.ic_logout));
                break;
            default:
                break;
        }
    }

    @Override
    public int getItemCount() {
        return this.Data.size();
    }

    public class CustomizeViewHolder extends RecyclerView.ViewHolder {

        ImageView imgCustomize;
        TextView tvLable;
        View container;
        View viewCustomize;
        public CustomizeViewHolder(@NonNull View itemView) {
            super(itemView);
            container = itemView;
            viewCustomize = itemView.findViewById(R.id.view_customize);
            imgCustomize = itemView.findViewById(R.id.img_item_customize);
            tvLable = itemView.findViewById(R.id.tv_item_customize_lable);
        }
    }
}
