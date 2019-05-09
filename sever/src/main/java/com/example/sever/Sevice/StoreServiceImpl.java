package com.example.sever.Sevice;

import com.example.sever.Entity.Store;
import com.example.sever.dao.StoreRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StoreServiceImpl implements StoreService {

    @Autowired
    private StoreRepo storeRepo;
    @Override
    public Store findOne(int store_id) {
        return storeRepo.findOne(store_id);
    }

    @Override
    public Store findOne(String store_name, String password) {
        return storeRepo.findOne(store_name, password);
    }

    @Override
    public String addOne(String store_name, String password) {
        return storeRepo.addOne(store_name, password);
    }

    @Override
    public Store update(int store_id,String store_name, String password, String store_detail, String address) {
        return storeRepo.update(store_id,store_name, password, store_detail, address);
    }
}
