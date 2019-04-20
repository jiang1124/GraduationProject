package com.example.sever.dao;

import com.example.sever.Entity.Product;

import java.util.List;

public interface ProductRepo {

    public List<Product> findSVTopFive();

    public List<Product> findKeyMany(String key);

    public List<Product> findSortMany(String sort);

    public List<Product> findKeyByPrice(String key);

    public List<Product> findKeyBySale(String key);

    public int add(Product product);

    public int update(Product product);

    public int delete(int id);
}
