package com.example.sever.Sevice;

import com.example.sever.Entity.Car;

import java.util.List;

public interface ShoppingCartService {

    public Car findCar(int user_id);

    public Car updateNum(int user_id,int product_id,int product_num);

    public Car delProduct(int user_id, String productIds);

    public String addProduct(int user_id,int product_id);
}
