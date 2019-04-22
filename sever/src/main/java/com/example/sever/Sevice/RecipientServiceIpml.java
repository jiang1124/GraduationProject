package com.example.sever.Sevice;


import com.example.sever.Entity.Recipient;
import com.example.sever.dao.RecipientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipientServiceIpml implements RecipientService {

    @Autowired
    private RecipientRepo recipientRepo;

    @Override
    public List<Recipient> findAll(int user_id) {
        return recipientRepo.findAll(user_id);
    }

    @Override
    public Recipient findOne(int user_id) {
        return recipientRepo.findOne(user_id);
    }

    @Override
    public int deleteOne(int address_id) {
        return recipientRepo.deleteOne(address_id);
    }

    @Override
    public int updateRecipient(int address_id, int user_id, String recipient_name, String phone, String city, String address, String state) {
        return recipientRepo.updateRecipient(address_id, user_id, recipient_name, phone, city, address, state);
    }

    @Override
    public int addRecipient(int user_id, String recipient_name, String phone, String city, String address, String state) {
        return recipientRepo.addRecipient(user_id, recipient_name, phone, city, address, state);
    }
}
