package com.example.helloandroid.page.adapter;

import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helloandroid.R;
import com.example.helloandroid.bean.LessonBean;

import java.util.ArrayList;

public class RvLessonAdapter extends RecyclerView.Adapter<RvLessonAdapter.InnerHolder>{
    ArrayList<LessonBean> lessonList; //用于储存课程的列表

    public RvLessonAdapter(ArrayList<LessonBean> lessonList) {
        this.lessonList = lessonList; //通过构造方法传入数据
    }

    @NonNull
    @Override
    public RvLessonAdapter.InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new InnerHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_second_lesson, parent, false));
    }

    //绑定数据
    @Override
    public void onBindViewHolder(@NonNull RvLessonAdapter.InnerHolder holder, int position) {
        holder.btnLesson.setText(lessonList.get(position).getFullName());
        holder.initData(lessonList.get(position).getName(),lessonList.get(position).getClassroom(),lessonList.get(position).getTime());
    }

    @Override
    public int getItemCount() {
        return lessonList.size(); //获取数据个数(要生产多少个View)
    }

    //内部静态类
    public static class InnerHolder extends RecyclerView.ViewHolder
    {
        Button btnLesson;
        private String name;
        private String classroom;
        private String time;

        public InnerHolder(@NonNull View itemView) {
            super(itemView);
            btnLesson = itemView.findViewById(R.id.btn_sec_show);
            initClickListener();
        }

        private void initData(String name, String classroom, String time){
            this.name = name;
            this.classroom = classroom;
            this.time = time;
        }

        private void initClickListener(){
            btnLesson.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(v.getContext());
                    dialog.setTitle("课程信息");
                    dialog.setMessage("课程名称:" + name + "\n教室:" + classroom + "\n时间:" + time);
                    dialog.show();
                }
            });
        }
    }
}
