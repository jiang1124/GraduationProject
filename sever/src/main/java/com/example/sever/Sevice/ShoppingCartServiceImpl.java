package com.example.sever.Sevice;

import com.example.sever.Entity.ShoppingCar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.sever.dao.ShoppingCartRepo;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Autowired
    private ShoppingCartRepo shoppingCartRepo;

    @Override
    public ShoppingCar findCar(int user_id) {
        return shoppingCartRepo.findCar(user_id);
    }
}
