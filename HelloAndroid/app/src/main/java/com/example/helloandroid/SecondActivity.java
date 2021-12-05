package com.example.helloandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SecondActivity extends AppCompatActivity implements View.OnClickListener{

    private Button mBtnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        initView();
    }

    //开始该活动的方法
    public static void startActivity(Context context){
        Intent intent = new Intent(context, SecondActivity.class);
        context.startActivity(intent);
    }

    private void initView(){
        mBtnLogout = findViewById(R.id.btn_sec_logout);
        mBtnLogout.setOnClickListener(this);
    }

    //点击检测
    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.btn_sec_logout:{
                finish();
                break;
            }

        }
    }
}