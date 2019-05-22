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

    @RequestMapping("/storeLogin")
    public Store storeLogin(String account,String password){
        System.out.println("a:"+account+",p:"+password);
        return storeService.findOne(account, password);
    }

    @RequestMapping("/storeRegister")
    public String storeRegister(String account,String password){
        return storeService.addOne(account, password);
    }
    @RequestMapping("/updateStore")
    public Store updateStore(int store_id,String store_name,String store_password,String store_detail,String store_address){
        return storeService.update(store_id,store_name, store_password, store_detail, store_address);
    }
}
