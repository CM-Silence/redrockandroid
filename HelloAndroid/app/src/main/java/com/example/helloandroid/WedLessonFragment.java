package com.example.helloandroid;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;

public class WedLessonFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sec_vp2_wedlesson,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initRv(view);
    }

    private void initRv(View view){
        RecyclerView mRvLesson = view.findViewById(R.id.rv_sec_lesson);
        ArrayList<LessonBean> lessonList= new ArrayList();
        lessonList.add(new LessonBean("软件\n工程\n导论\n\n\n2308","软件工程导论","2308","19:00-20:40"));
        lessonList.add(new LessonBean("高等\n数学\n1\n\n\n3409","高等数学1","3409","8:00-9:40"));
        lessonList.add(new LessonBean("大学\n英语\n2\n\n\n4308","大学英语2","4308","4:15-5:55"));
        lessonList.add(new LessonBean("计算机\n应用能\n力开发\n\n\nA501","计算机应用能力开发","A501","14:00-17:55"));
        lessonList.add(new LessonBean("军事\n理论\n\n\n\n网络教室","军事理论","网络教室","14:00-17:00"));

        RvLessonAdapter rvLessonAdapter = new RvLessonAdapter(lessonList);
        mRvLesson.setAdapter(rvLessonAdapter);
        mRvLesson.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
    }
}