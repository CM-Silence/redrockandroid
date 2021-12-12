package com.example.helloandroid.page.fragment;

import static android.content.Context.MODE_PRIVATE;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.example.helloandroid.R;
import com.example.helloandroid.page.dialog.RegisterDialog;
import com.example.helloandroid.bean.UserBean;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LoginFragment extends Fragment implements View.OnClickListener , RegisterDialog.RegisterListener {
    private static final String ARG_USER_BEAN = "userBean";

    public static LoginFragment newInstance(@Nullable UserBean userBean) {
        LoginFragment fragment = new LoginFragment();
        if (userBean != null) {
            Bundle args = new Bundle();
            args.putSerializable(ARG_USER_BEAN, userBean);
            fragment.setArguments(args);
        }
        return fragment;
    }

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
    public static List<UserBean> userList = new ArrayList<>();
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    public LoginFragment() {
        // Fragment 必须有无参构造器
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_login, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        initUserBean();
        initClick();
    }

    //初始化控件
    private void initView(View view) {
        //获取各个控件
        mBtnLogin = view.findViewById(R.id.btn_main_login);
        mBtnRegister = view.findViewById(R.id.btn_main_register);
        mEtUsername = view.findViewById(R.id.et_main_username);
        mEtPassword = view.findViewById(R.id.et_main_password);
        mTilUsername = view.findViewById(R.id.til_main_username);
        mTilPassword = view.findViewById(R.id.til_main_password);
        mCbRememberPassword = view.findViewById(R.id.cb_main_rememberPassword);
        mCbAutoLogin = view.findViewById(R.id.cb_main_autoLogin);

        //获取sharedPreferences以及edit
        sharedPreferences = requireContext().getSharedPreferences("data", MODE_PRIVATE);
        editor = sharedPreferences.edit();

    }


    private void initClick() {
        mBtnLogin.setOnClickListener(this);
        mBtnRegister.setOnClickListener(this);
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

    //初始化账号信息
    private void initUserBean() {
        userList = new ArrayList<>();
        //获取已注册过的账号密码
        int listSize = sharedPreferences.getInt("listSize", 0);
        if (listSize > 0) {
            for (int i = 0; i < listSize; i++) {
                userList.add(UserBean.getBeanFromString(sharedPreferences.getString("user" + i, null)));
            }
        }
        //登录过的账号可以直接显示
        mEtUsername.setText(sharedPreferences.getString("username", ""));

        //保留某个账号记住密码或者自动登录的操作
        for (int i = 0; i < userList.size(); i++) {
            if (mEtUsername.getText().toString().equals(userList.get(i).getUsername())) {
                if (userList.get(i).isAutoLogin()) {
                    replaceFragment(new SecondFragment());
                    mCbAutoLogin.setChecked(true);
                }
                if (userList.get(i).isRememberPassword()) {
                    mEtPassword.setText(userList.get(i).getPassword());
                    mCbRememberPassword.setChecked(true);
                }
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
                    Toast.makeText(getContext(), "登录成功!", Toast.LENGTH_SHORT).show();
                    replaceFragment(new SecondFragment());
                    return; //找到则退出方法进入其他界面
                }
            }
        }
        //找不到则告知用户以下信息
        Toast.makeText(getContext(), "我好不容易登录一次,你却让我输得这么彻底! 焯!(账号或密码错误)", Toast.LENGTH_SHORT).show();
        Log.d("LoginActivity","Can't login.");
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_main_login: {
                login();
                break;
            }
            case R.id.btn_main_register: {
                registerFragment.show(requireActivity().getFragmentManager(), "registerDialog");
            }

        }
    }

    //获取注册信息,保存注册的账号密码
    @Override
    public void getRegisterInput(String username, String password)
    {
        Toast.makeText(getContext(), "注册成功!账号:"+username + "密码:" + password, Toast.LENGTH_LONG).show();
        UserBean user = new UserBean(username,password);
        //将对象以字符串的形式储存到sharePreferences中(+i是为了储存多个字符串)
        editor.putString("user" + userList.size(), user.getBeanString());
        userList.add(user);
        editor.putInt("listSize",userList.size());
        editor.apply();
    }

    //切换碎片的方法
    private void replaceFragment(Fragment fragment){
        fragmentManager = requireActivity().getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.hide(Objects.requireNonNull(requireActivity().getSupportFragmentManager().findFragmentById(R.id.main_fragment)));
        fragmentTransaction.add(R.id.main_fragment, fragment);
        fragmentTransaction.addToBackStack(null);   //添加进回退栈
        fragmentTransaction.commit();
    }
}

