package com.example.helloandroid.page.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import com.example.helloandroid.R;
import com.example.helloandroid.bean.LessonBean;
import com.example.helloandroid.page.adapter.Vp2LessonAdapter;
import com.example.helloandroid.base.LessonFragmentBase;
import com.example.helloandroid.page.activity.MainActivity;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.Objects;


public class SecondFragment extends Fragment implements View.OnClickListener{
    private Button mBtnLogout;
    private ViewPager2 mVp2Lesson;
    private TabLayout mTlPage;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_second, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        initVp2();
    }

    private void initView(View view){
        mBtnLogout = view.findViewById(R.id.btn_sec_logout);
        mVp2Lesson = view.findViewById(R.id.vp2_sec_lesson);
        mTlPage = view.findViewById(R.id.tl_sec_page);
        mBtnLogout.setOnClickListener(this);
    }

    private void initVp2(){
        ArrayList<LessonFragmentBase> fragmentList= new ArrayList();

        ArrayList<LessonBean> lessonList1 = new ArrayList();
        lessonList1.add(new LessonBean("高等\n数学\nA\n\n\n3409","高等数学A","3409","8:00-9:40"));
        lessonList1.add(new LessonBean("大学\n体育\n1\n\n\n太极运动场","大学体育1","太极运动场","10:15-11:55"));
        lessonList1.add(new LessonBean("软件\n工程\n导论\n\n\n2308","软件工程导论","2308","19:00-20:40"));
        fragmentList.add(new LessonFragment("周一",lessonList1));

        ArrayList<LessonBean> lessonList2 = new ArrayList();
        lessonList2.add(new LessonBean("大学\n英语\n2\n\n\n4308","大学英语2","4308","16:15-17:55"));
        lessonList2.add(new LessonBean("形式\n与政\n策\n\n\n3303","形势与政策","3303","19:00-20:40"));
        fragmentList.add(new LessonFragment("周二",lessonList2));

        ArrayList<LessonBean> lessonList3 = new ArrayList();
        lessonList3.add(new LessonBean("高等\n数学\n1\n\n\n3409","高等数学1","3409","8:00-9:40"));
        lessonList3.add(new LessonBean("新生\n研讨\n课\n\n\n2402","新生研讨课","2402","10:15-11:55"));
        lessonList3.add(new LessonBean("计算机\n应用能\n力开发\n\n\nA501","计算机应用能力开发","A501","14:00-17:55"));
        lessonList3.add(new LessonBean("软件\n工程\n导论\n\n\n2308","软件工程导论","2308","19:00-20:40"));
        fragmentList.add(new LessonFragment("周三",lessonList3));

        ArrayList<LessonBean> lessonList4 = new ArrayList();
        lessonList4.add(new LessonBean("高等\n数学\nA\n\n\n3409","高等数学A","3409","8:00-9:40"));
        lessonList4.add(new LessonBean("大学\n英语\n2\n\n\n4308","大学英语2","4308","4:15-5:55"));
        lessonList4.add(new LessonBean("思想\n道德\n与法治\n\n\n3209","思想道德与法治","3209","19:00-21:35"));
        fragmentList.add(new LessonFragment("周四",lessonList4));

        ArrayList<LessonBean> lessonList5 = new ArrayList();
        lessonList5.add(new LessonBean("高等\n数学\nA\n\n\n3409","高等数学A","3409","8:00-9:40"));
        lessonList5.add(new LessonBean("大学\n英语\n2\n\n\n4308","大学英语2","4308","4:15-5:55"));
        lessonList5.add(new LessonBean("编程\n基础\n1\n\n\nC405","编程基础1","C405","19:00-22:30"));
        fragmentList.add(new LessonFragment("周五",lessonList5));


        Vp2LessonAdapter vp2LessonAdapter = new Vp2LessonAdapter(requireActivity(),fragmentList);
        mVp2Lesson.setAdapter(vp2LessonAdapter);

        new TabLayoutMediator(mTlPage, mVp2Lesson, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                //在这里给Tab设置Text
                tab.setText(fragmentList.get(position).getDAY()); } }).attach();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_sec_logout:{
                replaceFragment(MainActivity.fragment);
                break;
            }

        }
    }

    //切换碎片的方法
    private void replaceFragment(Fragment fragment){
        fragmentManager = requireActivity().getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.remove(Objects.requireNonNull(requireActivity().getSupportFragmentManager().findFragmentById(R.id.main_fragment)));
        fragmentTransaction.show(fragment);
        fragmentTransaction.commit();
    }
}
