package com.example.sever.dao;



import com.example.sever.Entity.CeNote;

import java.util.List;

public interface CeNoteRepo {

    public List<CeNote> findAll();

    public CeNote findOne(int id);

    public int add(CeNote note);

    public int update(CeNote note);

    public int delete(int id);

}
