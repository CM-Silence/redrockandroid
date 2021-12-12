package com.example.helloandroid.page.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.helloandroid.R;
import com.example.helloandroid.bean.LessonBean;
import com.example.helloandroid.page.adapter.RvLessonAdapter;

import java.util.ArrayList;

public class LessonFragment extends com.example.helloandroid.base.LessonFragmentBase {
    private final String DAY;
    private final ArrayList<LessonBean> lessonList;
    private TextView mTvDay;

    public LessonFragment(String day, ArrayList<LessonBean> lessonList){
        this.DAY = day;
        this.lessonList = lessonList;
    }

    @Override
    public String getDAY() {
        return DAY;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sec_vp2_lesson,container,false);
    }

    @Override
    protected void initRv(View view) {
        mTvDay = view.findViewById(R.id.tv_sec_day);
        mTvDay.setText(getDAY());

        RecyclerView mRvLesson = view.findViewById(R.id.rv_sec_lesson);
        RvLessonAdapter rvLessonAdapter = new RvLessonAdapter(lessonList);
        mRvLesson.setAdapter(rvLessonAdapter);
        mRvLesson.setLayoutManager(new StaggeredGridLayoutManager(5,StaggeredGridLayoutManager.VERTICAL));
    }
}
