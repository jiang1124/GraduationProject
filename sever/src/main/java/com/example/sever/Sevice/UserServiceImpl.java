package com.example.sever.Sevice;

import com.example.sever.Entity.User;
import com.example.sever.dao.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepo userRepo;

    @Override
    public User findOne(String name,String password) {
        return userRepo.findOne(name,password);
    }

    @Override
    public User add(String name, String password) {
        return userRepo.add(name, password);
    }
}
