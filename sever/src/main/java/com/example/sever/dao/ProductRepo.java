package com.example.sever.dao;

import com.example.sever.Entity.Product;

import java.util.List;

public interface ProductRepo {

    public List<Product> findSVTopFive();

    public List<Product> findKeyMany(String id);

    public int add(Product product);

    public int update(Product product);

    public int delete(int id);
}
