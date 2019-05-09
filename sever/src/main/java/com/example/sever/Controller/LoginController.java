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
    public User Verification(String name,String password){
        System.out.println("login_name:"+name);
        System.out.println("login_password:"+password);
        User user =userService.findOne(name,password);
        System.out.println(user);
        return user;
    }

    @RequestMapping("/registered")
    public User Registered(String name,String password){
        System.out.println("registered_name:"+name);
        System.out.println("registered_password:"+password);
        User user =userService.add(name,password);
        System.out.println(user);
        return user;
    }

}
