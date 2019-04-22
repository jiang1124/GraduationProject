package com.example.sever.dao;

import com.example.sever.Entity.Recipient;

import java.util.List;

public interface RecipientRepo {

    public List<Recipient> findAll(int user_id);

    public int deleteOne(int user_id);

    public int updateRecipient(int address_id,int user_id,String recipient_name,String phone,String city,String address,String state);

    public int addRecipient(int user_id,String recipient_name,String phone,String city,String address,String state);
}
