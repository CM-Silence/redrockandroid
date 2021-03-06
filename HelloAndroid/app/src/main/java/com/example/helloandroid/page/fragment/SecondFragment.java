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
        lessonList1.add(new LessonBean("??????\n??????\nA\n\n\n3409","????????????A","3409","8:00-9:40"));
        lessonList1.add(new LessonBean("??????\n??????\n1\n\n\n???????????????","????????????1","???????????????","10:15-11:55"));
        lessonList1.add(new LessonBean("??????\n??????\n??????\n\n\n2308","??????????????????","2308","19:00-20:40"));
        fragmentList.add(new LessonFragment("??????",lessonList1));

        ArrayList<LessonBean> lessonList2 = new ArrayList();
        lessonList2.add(new LessonBean("??????\n??????\n2\n\n\n4308","????????????2","4308","16:15-17:55"));
        lessonList2.add(new LessonBean("??????\n??????\n???\n\n\n3303","???????????????","3303","19:00-20:40"));
        fragmentList.add(new LessonFragment("??????",lessonList2));

        ArrayList<LessonBean> lessonList3 = new ArrayList();
        lessonList3.add(new LessonBean("??????\n??????\n1\n\n\n3409","????????????1","3409","8:00-9:40"));
        lessonList3.add(new LessonBean("??????\n??????\n???\n\n\n2402","???????????????","2402","10:15-11:55"));
        lessonList3.add(new LessonBean("?????????\n?????????\n?????????\n\n\nA501","???????????????????????????","A501","14:00-17:55"));
        lessonList3.add(new LessonBean("??????\n??????\n??????\n\n\n2308","??????????????????","2308","19:00-20:40"));
        fragmentList.add(new LessonFragment("??????",lessonList3));

        ArrayList<LessonBean> lessonList4 = new ArrayList();
        lessonList4.add(new LessonBean("??????\n??????\nA\n\n\n3409","????????????A","3409","8:00-9:40"));
        lessonList4.add(new LessonBean("??????\n??????\n2\n\n\n4308","????????????2","4308","4:15-5:55"));
        lessonList4.add(new LessonBean("??????\n??????\n?????????\n\n\n3209","?????????????????????","3209","19:00-21:35"));
        fragmentList.add(new LessonFragment("??????",lessonList4));

        ArrayList<LessonBean> lessonList5 = new ArrayList();
        lessonList5.add(new LessonBean("??????\n??????\nA\n\n\n3409","????????????A","3409","8:00-9:40"));
        lessonList5.add(new LessonBean("??????\n??????\n2\n\n\n4308","????????????2","4308","4:15-5:55"));
        lessonList5.add(new LessonBean("??????\n??????\n1\n\n\nC405","????????????1","C405","19:00-22:30"));
        fragmentList.add(new LessonFragment("??????",lessonList5));


        Vp2LessonAdapter vp2LessonAdapter = new Vp2LessonAdapter(requireActivity(),fragmentList);
        mVp2Lesson.setAdapter(vp2LessonAdapter);

        new TabLayoutMediator(mTlPage, mVp2Lesson, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                //????????????Tab??????Text
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

    //?????????????????????
    private void replaceFragment(Fragment fragment){
        fragmentManager = requireActivity().getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.remove(Objects.requireNonNull(requireActivity().getSupportFragmentManager().findFragmentById(R.id.main_fragment)));
        fragmentTransaction.show(fragment);
        fragmentTransaction.commit();
    }
}
