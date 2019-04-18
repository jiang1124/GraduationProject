package com.example.sever.Sevice;

import com.example.sever.Entity.User;

import java.util.List;

public interface UserService {

    public User findOne(String name,String password);

    public User add(String name,String password);
}
