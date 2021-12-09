package com.example.helloandroid;

import java.io.Serializable;

public class UserBean implements Serializable {
    private String username;
    private String password;
    private boolean isRememberPassword;
    private boolean isAutoLogin;

    UserBean(String username,String password){
        this.username = username;
        this.password = password;
        this.isRememberPassword = false;
        this.isAutoLogin = false;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRememberPassword(boolean rememberPassword) {
        isRememberPassword = rememberPassword;
    }

    public void setAutoLogin(boolean autoLogin) {
        isAutoLogin = autoLogin;
    }

    public boolean isRememberPassword() {
        return isRememberPassword;
    }

    public boolean isAutoLogin() {
        return isAutoLogin;
    }

    public String getBeanString(){
        return username + "," + password + "," + isRememberPassword + "," + isAutoLogin;
    }

    public static UserBean getBeanFromString(String data){
        String[] split = data.split(",");
        UserBean user = new UserBean(split[0],split[1]);
        user.isRememberPassword = Boolean.parseBoolean(split[2]);
        user.isAutoLogin = Boolean.parseBoolean(split[3]);
        return user;
    }
}
