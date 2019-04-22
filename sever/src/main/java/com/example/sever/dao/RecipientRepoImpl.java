package com.example.sever.dao;

import com.example.sever.Entity.Recipient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class RecipientRepoImpl implements RecipientRepo {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Recipient> findAll(int user_id) {
        List<Recipient> res = jdbcTemplate.query("select * from recipient where user_id = ?",
                new Object[]{user_id},new BeanPropertyRowMapper(Recipient.class));
        return res;
    }

    @Override
    public int deleteOne(int address_id) {
        return jdbcTemplate.update("DELETE FROM recipient WHERE address_id=?",address_id);
    }

    @Override
    public int updateRecipient(int address_id, int user_id, String recipient_name, String phone, String city, String address, String state) {
        String realState;
        if(state.equals("设为默认")) {
            realState = "default";
            jdbcTemplate.update("update recipient set state = ? where user_id = ?","non-default",user_id);
        }
        else{
            realState = "non-default";
        }
        jdbcTemplate.update("UPDATE recipient set user_id=?,recipient_name=?,phone=?,city=?, address = ? ,state = ? where address_id=?",
               user_id,recipient_name,phone,city,address,realState,address_id);
        return 0;
    }

    @Override
    public int addRecipient(int user_id, String recipient_name, String phone, String city, String address, String state) {
        String realState;
        if(state.equals("设为默认")) {
            realState = "default";
            jdbcTemplate.update("update recipient set state = ? where user_id = ?","non-default",user_id);
        }
        else{
            realState = "non-default";
        }
        jdbcTemplate.update("INSERT INTO recipient(user_id,recipient_name,phone,city,address,state)" +
                "VALUE (?,?,?,?,?,?)",user_id,recipient_name,phone,city,address,realState);
        return 0;
    }
}
