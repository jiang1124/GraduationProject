package com.example.sever.dao;

import com.example.sever.Entity.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StoreRepoImpl implements StoreRepo {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Override
    public Store findOne(int store_id) {
        List<Store> res = jdbcTemplate.query("select * from store where store_id = ?;", new Object[]{store_id},
                new BeanPropertyRowMapper(Store.class));
        if (res != null && res.size() > 0) {
            return res.get(0);
        }
        return null;
    }

    @Override
    public Store findOne(String store_name, String password) {
        List<Store> res = jdbcTemplate.query("select * from store where store_name = ? and store_password = ?;",
                new Object[]{store_name,password}, new BeanPropertyRowMapper(Store.class));
        if (res != null && res.size() > 0) {
            return res.get(0);
        }
        return null;
    }

    @Override
    public String addOne(String store_name, String password) {
        jdbcTemplate.update(" insert into store(store_name,store_password) value(?,?);",
                store_name,password);
        return "成功";
    }

    @Override
    public Store update(int store_id,String store_name, String password, String store_detail, String address) {
        jdbcTemplate.update("update store set store_name = ?,store_password = ?,store_detail=?,address=? where store_id=? ",
                store_name, password, store_detail, address,store_id);
        for(int i=0;i<10;i++){

        }
        return findOne(store_id);
    }
}
