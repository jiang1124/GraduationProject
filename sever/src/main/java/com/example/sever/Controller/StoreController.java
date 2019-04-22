package com.example.sever.Controller;


import com.example.sever.Entity.Store;
import com.example.sever.Sevice.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StoreController {

    @Autowired
    private StoreService storeService;

    @RequestMapping("/findStoreName")
    public Store findStoreName(int store_id){
        return storeService.findOne(store_id);
    }
}
