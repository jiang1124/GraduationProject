package com.example.sever.Sevice;

import com.example.sever.Entity.CeNote;

import java.util.List;

public interface CeNoteService {

    public List<CeNote> findAll();

    public CeNote findOne(int id);

    public int add(CeNote note);

    public int update(CeNote note);

    public int delete(int id);

}

