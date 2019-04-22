package com.example.sever.Sevice;

import com.example.sever.Entity.Recipient;

import java.util.List;

public interface RecipientService {

    public List<Recipient> findAll(int user_id);

    public Recipient findOne(int user_id);

    public int deleteOne(int address_id);

    public int updateRecipient(int address_id,int user_id,String recipient_name,String phone,String city,String address,String state);

    public int addRecipient(int user_id,String recipient_name,String phone,String city,String address,String state);
}
