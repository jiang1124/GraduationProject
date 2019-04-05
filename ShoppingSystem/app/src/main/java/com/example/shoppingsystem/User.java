package com.example.shoppingsystem;

/**
 * Created by 79124 on 2019/3/31.
 */

public class User {

    private String userId;//用户id
    private String userName;//用户名
    private int userImageId;//用户头像id
    private String userPassword;//用户密码
    private String userLevel;//用户等级
    private String userIntegral;//用户积分

    public void User(String userId,String userName,int userImageId,String userPassword,String userLevel,String userIntegral){
        this.userId = userId;
        this.userName = userName;
        this.userImageId = userImageId;
        this.userPassword = userPassword;
        this.userLevel = userLevel;
        this.userIntegral = userIntegral;
    }
    public void setUserId(String userId){
        this.userId = userId;
    }
    public void setUserName(String userName){
        this.userName = userName;
    }
    public void setUserPassword(String userPassword){
        this.userPassword = userPassword;
    }
    public void setUserLevel(String userLevel){
        this.userLevel = userLevel;
    }
    public void setUserIntegral(String userIntegral){
        this.userIntegral = userIntegral;
    }
    public void setUserImage(int userImageId){
        this.userImageId = userImageId;
    }
    public String getUserId(){
        return userId;
    }
    public String getUserName(){
        return userName;
    }
    public String getUserPassword(){
        return userPassword;
    }
    public String getUserLevel(){
        return userLevel;
    }
    public String getUserIntegral(){
        return userIntegral;
    }
    public int getUserImage(){
        return userImageId;
    }
}
