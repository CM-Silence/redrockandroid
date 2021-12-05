package com.example.helloandroid;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class RegisterDialog extends DialogFragment {
    private TextInputEditText mEtRegisterUsername;
    private TextInputEditText mEtFirPassword;
    private TextInputEditText mEtSecPassword;
    private TextInputLayout mTilUsername;
    private TextInputLayout mTilFirPassword;
    private TextInputLayout mTilSecPassword;

    //用于给LoginActivity传递数据(账号密码)的接口
    public interface RegisterListener
    {
        void getRegisterInput(String username, String Password);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().setTitle("注册账号"); //设置对话框标题
        getDialog().setCanceledOnTouchOutside(false); //对话框不能在用户碰到对话框以外的地方时关闭(绕口)
        View view = inflater.inflate(R.layout.activity_main_register,container);
        return view;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_main_register, null);

        //获取各个控件
        mEtRegisterUsername = view.findViewById(R.id.et_main_register_username);
        mEtFirPassword = view.findViewById(R.id.et_main_register_firstPassword);
        mEtSecPassword = view.findViewById(R.id.et_main_register_secondPassword);
        mTilUsername = view.findViewById(R.id.til_username);
        mTilFirPassword = view.findViewById(R.id.til_firstPassword);
        mTilSecPassword = view.findViewById(R.id.til_secondPassword);
        initView();


        //设置对话框的按键及点击事件
        builder.setView(view).setPositiveButton("注册",
                (dialog, id) -> register()).setNegativeButton("返回",
                (dialog, id) -> Toast.makeText(getActivity(), "我好不容易注册一次,你却让我输得这么彻底! 焯!", Toast.LENGTH_SHORT).show());
        return builder.create();
    }

    //获取用户输入
    private void register(){
        String username = mEtRegisterUsername.getText().toString();
        String firstPassport = mEtFirPassword.getText().toString();
        String secondPassport = mEtSecPassword.getText().toString();
        if(LoginActivity.usernameList.size() > 0) {
            for (int i = 0; i < LoginActivity.usernameList.size(); i++) {
                if (username.equals(LoginActivity.usernameList.get(i))) {
                    Toast.makeText(getActivity(), "我好不容易注册一次,你却让我输得这么彻底! 焯!(该账号已被注册!)", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        }
        if(firstPassport.equals(secondPassport) && username.length() >= 8 && username.length() <= 10 && firstPassport.length() >= 6 && firstPassport.length() <= 12) {
            RegisterListener listener = (RegisterListener) getActivity();
            listener.getRegisterInput(username,firstPassport);
        }
        else if(!firstPassport.equals(secondPassport)){
            Toast.makeText(getActivity(), "我好不容易注册一次,你却让我输得这么彻底! 焯!(两次输入的密码不同)", Toast.LENGTH_LONG).show();
        }
        else if(username.length() < 8 || firstPassport.length() < 6 || username.length() > 10 || firstPassport.length() > 12){
            Toast.makeText(getActivity(), "我好不容易注册一次,你却让我输得这么彻底! 焯!(账号或密码长度不够,账号由8-10个数字组成,密码由6-12个字符组成!)", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(getActivity(), "我好不容易注册一次,你却让我输得这么彻底! 焯!(未知的错误)", Toast.LENGTH_SHORT).show();
        }
    }

    private void initView(){
        mTilUsername.getEditText().setFocusable(true);
        mTilUsername.getEditText().setFocusableInTouchMode(true);
        mTilUsername.getEditText().requestFocus();
        mEtRegisterUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(mEtRegisterUsername.getText().toString().length() < 8 || mEtRegisterUsername.getText().toString().length() > 10){
                    mTilUsername.setError("用户名长度为8-10");
                }
                else{
                    mTilUsername.setError("");
                }
            }
        });
        mEtFirPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(mEtFirPassword.getText().toString().length() < 6 || mEtFirPassword.getText().toString().length() > 12){
                    mTilFirPassword.setError("密码长度为6-12");
                }
                else{
                    mTilFirPassword.setError("");
                }
            }
        });
        mEtSecPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!mEtSecPassword.getText().toString().equals(mEtFirPassword.getText().toString())){
                    mTilSecPassword.setError("两次输入的密码不一致");
                }
                else{
                    mTilSecPassword.setError("");
                }
            }
        });
    }
}
