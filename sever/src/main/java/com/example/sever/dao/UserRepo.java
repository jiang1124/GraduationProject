package com.example.sever.dao;

import com.example.sever.Entity.User;

import java.util.List;

public interface UserRepo {

    public User findOne(String name,String password);

    public User add(String name,String password);
}
