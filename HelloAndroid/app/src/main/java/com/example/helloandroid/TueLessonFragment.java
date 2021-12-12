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

public class TueLessonFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sec_vp2_tuelesson,container,false);
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
        lessonList.add(new LessonBean("高等\n数学\nA\n\n\n3409","高等数学A","3409","14:00-15:40"));
        lessonList.add(new LessonBean("形式\n与政\n策\n\n\n3303","形势与政策","3303","19:00-20:40"));
        lessonList.add(new LessonBean("编程\n基础\n1\n\n\nC405","编程基础1","C405","19:00-22:30"));
        lessonList.add(new LessonBean("大学\n体育\n1\n\n\n太极运动场","大学体育1","太极运动场","10:15-11:55"));

        RvLessonAdapter rvLessonAdapter = new RvLessonAdapter(lessonList);
        mRvLesson.setAdapter(rvLessonAdapter);
        mRvLesson.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
    }
}
