package com.example.sever.Controller;

import com.example.sever.Entity.User;
import com.example.sever.Sevice.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/login")
@RestController
public class LoginController {

    @Autowired
    private UserService userService;

    @RequestMapping("/verification")
    public User Verification(String account,String password){
        System.out.println("login_name:"+account);
        System.out.println("login_password:"+password);
        User user =userService.findOne(account,password);
        System.out.println(user);
        return user;
    }

    @RequestMapping("/registered")
    public User Registered(String account,String password){
        System.out.println("registered_name:"+account);
        System.out.println("registered_password:"+password);
        User user =userService.add(account,password);
        System.out.println(user);
        return user;
    }

}
