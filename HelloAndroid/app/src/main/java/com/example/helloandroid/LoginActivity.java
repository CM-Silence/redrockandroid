package com.example.helloandroid;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, RegisterDialog.RegisterListener {
    private MaterialButton mBtnLogin;
    private MaterialButton mBtnRegister;
    private TextInputEditText mEtUsername;
    private TextInputEditText mEtPassword;
    private TextInputLayout mTilUsername;
    private TextInputLayout mTilPassword;
    private CheckBox mCbRememberPassword;
    private CheckBox mCbAutoLogin;
    private final RegisterDialog registerFragment = new RegisterDialog();

    //用于储存用户账号密码的东西
    protected static SharedPreferences sharedPreferences;
    protected static SharedPreferences.Editor editor;
    protected static List<String> usernameList;
    protected static List<String> passwordList;

    //开始该活动的方法
    public static void startActivity(Context context){
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionbar = getSupportActionBar();
        if(actionbar != null){
            actionbar.hide();
        }
        initView();
    }

    //初始化
    private void initView(){
        //获取各个控件
        mBtnLogin = findViewById(R.id.btn_main_login);
        mBtnLogin.setOnClickListener(this);
        mBtnRegister = findViewById(R.id.btn_main_register);
        mBtnRegister.setOnClickListener(this);
        mEtUsername = findViewById(R.id.et_main_username);
        mEtPassword = findViewById(R.id.et_main_password);
        mTilUsername = findViewById(R.id.til_main_username);
        mTilPassword = findViewById(R.id.til_main_password);
        mCbRememberPassword = findViewById(R.id.cb_main_rememberPassword);
        mCbAutoLogin = findViewById(R.id.cb_main_autoLogin);
        usernameList = new ArrayList<>();
        passwordList = new ArrayList<>();

        //获取sharedPreferences以及edit
        sharedPreferences = getSharedPreferences("data",MODE_PRIVATE);
        editor = sharedPreferences.edit();

        //记住密码选项是否被勾上
        boolean isRemember = sharedPreferences.getBoolean("rememberPassport",false);
        boolean isAutoLogin = sharedPreferences.getBoolean("autoLogin",false);

        if(isRemember){
            mEtPassword.setText(sharedPreferences.getString("password",""));
            mCbRememberPassword.setChecked(true);
        }
        mEtUsername.setText(sharedPreferences.getString("username",""));

        //获取已注册过的账号密码
        int listSize = sharedPreferences.getInt("listSize",0);
        if(listSize > 0) {
            for (int i = 0; i < listSize; i++) {
                usernameList.add(sharedPreferences.getString("existUsername" + i, null));
                passwordList.add(sharedPreferences.getString("existPassword" + i, null));
            }
        }

        if(isAutoLogin){
            SecondActivity.startActivity(this);
            mCbAutoLogin.setChecked(true);
        }
    }

    //点击检测
    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.btn_main_login:{
                login();
                break;
            }
            case R.id.btn_main_register:{
                //打开用于注册的FragmentDialog
                registerFragment.show(getFragmentManager(),"register");
                break;
            }
        }
    }

    //用于登录的方法
    private void login(){
        //记录用户输入
        String username = mEtUsername.getText().toString();
        String password = mEtPassword.getText().toString();

        //在账号密码列表中寻找与用户输入匹配的账号密码
        if(usernameList.size() > 0) {
            for (int i = 0; i < usernameList.size(); i++) {
                if (username.equals(usernameList.get(i)) && password.equals(passwordList.get(i))) {

                    if (mCbRememberPassword.isChecked()) {
                        editor.putBoolean("rememberPassport", true);
                        editor.putString("password", password);
                    } else {
                        editor.putBoolean("rememberPassport", false);
                        editor.putString("password", "");
                    }
                    editor.putBoolean("autoLogin", mCbAutoLogin.isChecked());
                    editor.putString("username", username);
                    editor.commit();
                    Toast.makeText(this, "登录成功!", Toast.LENGTH_SHORT).show();
                    SecondActivity.startActivity(this);
                    return; //找到则退出方法进入其他界面
                }
            }
        }
        //找不到则告知用户以下信息
        Toast.makeText(this, "我好不容易登录一次,你却让我输得这么彻底! 焯!(账号或密码错误)", Toast.LENGTH_SHORT).show();
        Log.d("LoginActivity","Can't login.");
    }

    //获取注册信息,保存注册的账号密码
    @Override
    public void getRegisterInput(String username, String password)
    {
        Toast.makeText(this, "注册成功!账号:"+username + "密码:" + password, Toast.LENGTH_LONG).show();
        usernameList.add(username);
        passwordList.add(password);
        editor.putInt("listSize",usernameList.size());
        for (int i = 0; i < usernameList.size(); i++){
            editor.putString("existUsername"+i,usernameList.get(i));
            editor.putString("existPassword"+i,passwordList.get(i));
        }
        editor.commit();
    }
}