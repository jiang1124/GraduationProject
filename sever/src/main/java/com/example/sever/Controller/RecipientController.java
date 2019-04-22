package com.example.sever.Controller;


import com.example.sever.Entity.Recipient;
import com.example.sever.Sevice.RecipientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin//跨域所用
@RestController
public class RecipientController {

    @Autowired
    private RecipientService recipientService;

    @RequestMapping("/recipient")
    public List<Recipient> getRecipientList(int user_id) {
        System.out.println("recipient/all userId:"+user_id);
        return recipientService.findAll(user_id);
    }

    @RequestMapping("/delAddress")
    public List<Recipient> delAddress(int address_id,int user_id){
        System.out.println("delAddress/one address_id:"+address_id);
        recipientService.deleteOne(address_id);
        System.out.println("delAddress/one user_id:"+user_id);
        return recipientService.findAll(user_id);
    }
    @RequestMapping("updateAddress")
    public int updateAddress(int address_id, int user_id, String recipient_name,
                             String phone, String city, String address, String state){
        System.out.println("updateAddress/one address_id:"+address_id);
        return recipientService.updateRecipient(address_id, user_id, recipient_name, phone, city, address, state);
    }

    @RequestMapping("addAddress")
    public int addAddress( int user_id, String recipient_name, String phone, String city, String address, String state){
        System.out.println("addAddress/one user_id:"+user_id);
        return recipientService.addRecipient( user_id, recipient_name, phone, city, address, state);
    }
}
