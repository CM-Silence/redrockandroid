package com.example.helloandroid;

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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

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
        ArrayList<MyFragment> fragmentList= new ArrayList();
        fragmentList.add(new MonLessonFragment());
        fragmentList.add(new TueLessonFragment());
        fragmentList.add(new WedLessonFragment());

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
