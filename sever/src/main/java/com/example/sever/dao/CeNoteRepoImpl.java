package com.example.sever.dao;

import com.example.sever.Entity.CeNote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CeNoteRepoImpl implements CeNoteRepo {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<CeNote> findAll() {

        List<CeNote> res = jdbcTemplate.query("select * from usermoney", new Object[]{},
                new BeanPropertyRowMapper(CeNote.class));

        return res;
    }

    @Override
    public CeNote findOne(int id) {

        List<CeNote> res = jdbcTemplate.query("select * from usermoney where id = ?",new Object[]{id},
                new BeanPropertyRowMapper(CeNote.class));

        if(res!=null && res.size()>0){
            return res.get(0);
        }else{
            return null;
        }
    }

    @Override
    public int add(CeNote note) {
        return jdbcTemplate.update("INSERT INTO usermoney(id,name,password,money)" +
                        " VALUES (?,?,?,?,)",
                note.getId(),note.getName(),note.getPassword(),note.getMoney());
    }

    @Override
    public int update(CeNote note) {
        return jdbcTemplate.update("UPDATE usermoney set id=?,name=?,password=?,money=?where id=?",
                note.getId(),note.getName(),note.getPassword(),note.getId());
    }

    @Override
    public int delete(int id) {
        return jdbcTemplate.update("DELETE FROM TABLE usermoney WHERE id=?",id);
    }
}
