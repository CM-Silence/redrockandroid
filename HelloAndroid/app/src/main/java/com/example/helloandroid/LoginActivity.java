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
    private Button mBtnLogin;
    private Button mBtnRegister;
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
    protected static List<UserBean> userList;

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
        initUserBean();
        initClick();
    }

    //初始化
    private void initView(){
        //获取各个控件
        mBtnLogin = findViewById(R.id.btn_main_login);
        mBtnRegister = findViewById(R.id.btn_main_register);
        mEtUsername = findViewById(R.id.et_main_username);
        mEtPassword = findViewById(R.id.et_main_password);
        mTilUsername = findViewById(R.id.til_main_username);
        mTilPassword = findViewById(R.id.til_main_password);
        mCbRememberPassword = findViewById(R.id.cb_main_rememberPassword);
        mCbAutoLogin = findViewById(R.id.cb_main_autoLogin);

        //获取sharedPreferences以及edit
        sharedPreferences = getSharedPreferences("data",MODE_PRIVATE);
        editor = sharedPreferences.edit();

    }

    private void initClick(){
        mBtnLogin.setOnClickListener(this);
        mBtnRegister.setOnClickListener(this);
    }

    private void initUserBean(){
        userList = new ArrayList<>();
        //获取已注册过的账号密码
        int listSize = sharedPreferences.getInt("listSize",0);
        if(listSize > 0) {
            for (int i = 0; i < listSize; i++) {
                userList.add(UserBean.getBeanFromString(sharedPreferences.getString("user" + i, null)));
            }
        }
        //登录过的账号可以直接显示
        mEtUsername.setText(sharedPreferences.getString("username",""));

        //保留某个账号记住密码或者自动登录的操作
        for (int i = 0; i < userList.size(); i++) {
            if(mEtUsername.getText().toString().equals(userList.get(i).getUsername())) {
                if (userList.get(i).isAutoLogin()) {
                    SecondActivity.startActivity(this);
                    mCbAutoLogin.setChecked(true);
                }
                if (userList.get(i).isRememberPassword()) {
                    mEtPassword.setText(userList.get(i).getPassword());
                    mCbRememberPassword.setChecked(true);
                }
            }
        }

        //输入的账号若之前选择了记住密码则直接将密码填进mEtPassword中
        mEtUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                for (int i = 0; i < userList.size(); i++) {
                    if(mEtUsername.getText().toString().equals(userList.get(i).getUsername()) && userList.get(i).isRememberPassword()) {
                        mEtPassword.setText(userList.get(i).getPassword());
                        mCbAutoLogin.setChecked(userList.get(i).isAutoLogin());
                        mCbRememberPassword.setChecked(true);
                    }
                }
            }
        });
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
        if(userList.size() > 0) {
            for (int i = 0; i < userList.size(); i++) {
                if (username.equals(userList.get(i).getUsername()) && password.equals(userList.get(i).getPassword())) {
                    if (mCbRememberPassword.isChecked()) {
                        editor.putString("password", password);
                        userList.get(i).setRememberPassword(true);
                    } else {
                        editor.putString("password", "");
                        userList.get(i).setRememberPassword(false);
                    }
                    userList.get(i).setAutoLogin(mCbAutoLogin.isChecked());

                    //记录登陆成功过的账号
                    editor.putString("username", username);

                    //记录该用户的选择
                    editor.putString("user" + i,userList.get(i).getBeanString());

                    editor.apply();
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
        UserBean user = new UserBean(username,password);
        //将对象以字符串的形式储存到sharePreferences中(+i是为了储存多个字符串)
        editor.putString("user" + userList.size(), user.getBeanString());
        userList.add(user);
        editor.putInt("listSize",userList.size());
        editor.apply();
    }
}