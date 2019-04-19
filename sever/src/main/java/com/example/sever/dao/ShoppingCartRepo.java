package com.example.sever.dao;

import com.example.sever.Entity.ShoppingCar;

public interface ShoppingCartRepo {

    public ShoppingCar findCar(int user_id);
}
