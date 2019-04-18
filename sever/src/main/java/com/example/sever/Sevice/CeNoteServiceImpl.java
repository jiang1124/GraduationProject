package com.example.sever.Sevice;


import com.example.sever.Entity.CeNote;
import com.example.sever.dao.CeNoteRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CeNoteServiceImpl implements CeNoteService {


    @Autowired
    private CeNoteRepo ceNote;

    @Override
    public List<CeNote> findAll() {
        return ceNote.findAll();
    }

    @Override
    public CeNote findOne(int id) {
        return ceNote.findOne(id);
    }

    @Override
    public int add(CeNote note) {
        note.setMoney(1);
        return ceNote.add(note);
    }

    @Override
    public int update(CeNote note) {
        note.setMoney(2);
        return ceNote.update(note);
    }

    @Override
    public int delete(int id) {
        return ceNote.delete(id);
    }
}
