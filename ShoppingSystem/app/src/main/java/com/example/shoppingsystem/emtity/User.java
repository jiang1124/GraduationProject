package com.example.shoppingsystem.emtity;

import java.io.Serializable;

/**
 * Created by 79124 on 2019/3/31.
 */

public class User implements Serializable {

    private String userId;//用户id
    private String userName;//用户名
    private int userImageId;//用户头像id
    private String userPassword;//用户密码
    private int userLevel;//用户等级
    private int userIntegral;//用户积分

    public User(String userId,String userName,int userImageId,String userPassword,int userLevel,int userIntegral){
        this.userId = userId;
        this.userName = userName;
        this.userImageId = userImageId;
        this.userPassword = userPassword;
        this.userLevel = userLevel;
        this.userIntegral = userIntegral;
    }

    public String getUserId() {
        return userId;
    }

    public int getUserImageId() {
        return userImageId;
    }

    public int getUserIntegral() {
        return userIntegral;
    }

    public int getUserLevel() {
        return userLevel;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUserImageId(int userImageId) {
        this.userImageId = userImageId;
    }

    public void setUserIntegral(int userIntegral) {
        this.userIntegral = userIntegral;
    }

    public void setUserLevel(int userLevel) {
        this.userLevel = userLevel;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}
