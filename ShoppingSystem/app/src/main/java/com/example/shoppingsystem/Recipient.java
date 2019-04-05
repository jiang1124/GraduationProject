package com.example.shoppingsystem;

/**
 * Created by 79124 on 2019/4/3.
 */

public class Recipient {
    //收件人id，收件人名，地址，电话号码，用户id，状态
    private String recipientId;
    private String recipientName;
    private String recipientAddress;
    private String recipientPhone;
    private String userId;
    private String recipientState;

    public Recipient(String recipientId,String recipientName,String recipientAddress,String recipientPhone,String userId,String recipientState)
    {
        this.recipientId = recipientId;
        this.recipientName = recipientName;
        this.recipientAddress = recipientAddress;
        this.recipientPhone = recipientPhone;
        this.userId = userId;
        this.recipientState = recipientState;
    }

    public void setRecipientId(String recipientId){
        this.recipientId = recipientId;
    }
    public void setRecipientName(String recipientName){
        this.recipientName = recipientName;
    }
    public void setRecipientAddress(String recipientAddress){
        this.recipientAddress = recipientAddress;
    }
    public void setRecipientPhone(String recipientPhone) {
        this.recipientPhone = recipientPhone;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public void setRecipientState(String recipientState) {
        this.recipientState = recipientState;
    }

    public String getRecipientId() {
        return recipientId;
    }
    public String getRecipientName() {
        return recipientName;
    }
    public String getRecipientAddress() {
        return recipientAddress;
    }
    public String getRecipientPhone() {
        return recipientPhone;
    }
    public String getUserId() {
        return userId;
    }
    public String getRecipientState() {
        return recipientState;
    }
}
