package com.example.sever.Sevice;

import com.example.sever.Entity.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.sever.dao.ShoppingCartRepo;

import java.util.List;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Autowired
    private ShoppingCartRepo shoppingCartRepo;

    @Override
    public Car findCar(int user_id) {
        return shoppingCartRepo.findCar(user_id);
    }

    @Override
    public Car updateNum(int user_id, int product_id,int product_num) {
        return shoppingCartRepo.updateNum(user_id,product_id,product_num);
    }

    @Override
    public Car delProduct(int user_id, String productIds) {
        return shoppingCartRepo.delProduct(user_id,productIds);
    }

    @Override
    public String addProduct(int user_id, int product_id) {
        return shoppingCartRepo.addProduct(user_id,product_id);
    }
}
