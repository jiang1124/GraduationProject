package com.example.sever.Controller;

import com.example.sever.Entity.CeNote;
import com.example.sever.Sevice.CeNoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin//跨域所用
@RestController
public class CeNoteController {

    @Autowired
    private CeNoteService ceNoteService;

    @RequestMapping("/findAll")
    public List<CeNote> findAll() {
        return ceNoteService.findAll();
    }

    @RequestMapping("/findOne")
    public CeNote findOne(int id) {
        return ceNoteService.findOne(id);
    }
//
//    @PostMapping("/add")
//    public Result add(@RequestBody CeNote note) {
//
//        try {
//            ceNoteService.add(note);
//            return new Result(true,"成功");
//        } catch (Exception e) {
//            return new Result(false,"失败");
//        }
//
//    }


}
