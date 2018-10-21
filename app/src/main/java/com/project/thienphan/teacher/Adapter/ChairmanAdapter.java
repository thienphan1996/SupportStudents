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

import com.project.thienphan.supportstudent.R;
import com.project.thienphan.timesheet.Model.StudentInfomation;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class ChairmanAdapter extends RecyclerView.Adapter<ChairmanAdapter.ChairmanViewHolder> {

    ArrayList<StudentInfomation> Data;
    AdapterView.OnItemClickListener onClick;
    Resources resources;

    public ChairmanAdapter(ArrayList<StudentInfomation> data, Resources resources, AdapterView.OnItemClickListener onClick) {
        Data = data;
        this.onClick = onClick;
        this.resources = resources;
    }

    @NonNull
    @Override
    public ChairmanViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.item_student_in_class,viewGroup,false);
        return new ChairmanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChairmanViewHolder chairmanViewHolder, int i) {
        chairmanViewHolder.tvName.setText(Data.get(i).getHoTen());
        chairmanViewHolder.tvStudentCode.setText(Data.get(i).getMaSV());
        String[] name = Data.get(i).getHoTen().split(" ");
        if (name != null){
            String englishName = CovertStringToURL(name[name.length-1]);
            String mail = englishName.toLowerCase() + Data.get(i).getMaSV().toLowerCase() + "@student.ctu.edu.vn";
            chairmanViewHolder.tvMail.setText(mail);
        }
        SetImageView(chairmanViewHolder.imgAvatar,i);
    }

    public String CovertStringToURL(String str) {
        try {
            String temp = Normalizer.normalize(str, Normalizer.Form.NFD);
            Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
            return pattern.matcher(temp).replaceAll("").toLowerCase().replaceAll(" ", "-").replaceAll("đ", "d");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    private void SetImageView(ImageView imgAvatar, int i) {
        if (Data.get(i).getGioiTinh().toLowerCase().equals("nam")){
            imgAvatar.setImageDrawable(resources.getDrawable(R.drawable.avatar_male));
        }
        else {
            imgAvatar.setImageDrawable(resources.getDrawable(R.drawable.avatar_female));
        }
    }

    @Override
    public int getItemCount() {
        return Data.size();
    }

    public class ChairmanViewHolder extends RecyclerView.ViewHolder{

        View container;
        ImageView imgAvatar;
        TextView tvName,tvStudentCode,tvMail;
        public ChairmanViewHolder(@NonNull View itemView) {
            super(itemView);
            container = itemView;
            imgAvatar = itemView.findViewById(R.id.img_item_chairman_avatar);
            tvName = itemView.findViewById(R.id.tv_item_chairman_name);
            tvStudentCode = itemView.findViewById(R.id.tv_item_chairman_student_code);
            tvMail = itemView.findViewById(R.id.tv_item_chairman_mail);
        }
    }
}
