package com.example.sever.dao;

import com.example.sever.Entity.Store;

public interface StoreRepo {

    public Store findOne(int store_id);

    public Store findOne(String store_name,String password);
}
