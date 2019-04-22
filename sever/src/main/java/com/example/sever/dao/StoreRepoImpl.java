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
        return null;
    }
}
