package com.example.sever.Sevice;

import com.example.sever.Entity.Store;

public interface StoreService {

    public Store findOne(int store_id);

    public Store findOne(String store_name,String password);
}
