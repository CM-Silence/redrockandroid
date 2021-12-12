package com.example.helloandroid.page.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.helloandroid.R;

public class WelcomeActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ActionBar actionbar = getSupportActionBar();
        if(actionbar != null){
            actionbar.hide();
        }
        new Thread(){
           public void run(){
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                   e.printStackTrace();
                }
                MainActivity.startActivity(WelcomeActivity.this);
                finish();
            }
       }.start();

    }
}

