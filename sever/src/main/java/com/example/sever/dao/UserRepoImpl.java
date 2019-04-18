package com.example.sever.dao;

import com.example.sever.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepoImpl implements UserRepo {
    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Override
    public User findOne(String name,String password) {
        List<User> res = jdbcTemplate.query("select * from user where user_name = ? and user_password = ?",
                new Object[]{name,password},new BeanPropertyRowMapper(User.class));
        if(res!=null && res.size()>0){
            return res.get(0);
        }else{
            return null;
        }
    }

    @Override
    public User add(String name, String password) {
        jdbcTemplate.update("INSERT INTO user(user_name,user_password) VALUES (?,?)",name,password);
        return findOne(name,password);
    }
}
