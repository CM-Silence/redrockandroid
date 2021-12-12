package com.example.helloandroid.page.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.helloandroid.base.LessonFragmentBase;

import java.util.ArrayList;

public class Vp2LessonAdapter extends FragmentStateAdapter {
    ArrayList<LessonFragmentBase> fragmentList; //用于储存碎片的列表

    public Vp2LessonAdapter(@NonNull FragmentActivity fragmentActivity, ArrayList<LessonFragmentBase> fragments) {
        super(fragmentActivity);
        this.fragmentList = fragments;
    }

    //从提供的Fragment数据源中拿数据
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragmentList.get(position);
    }

    //返回Fragment的个数
    @Override public int getItemCount() {
        return fragmentList.size();
    }
}
