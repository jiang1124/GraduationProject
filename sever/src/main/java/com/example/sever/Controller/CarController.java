package com.example.sever.Controller;

import com.example.sever.Entity.ShoppingCar;
import com.example.sever.Sevice.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin//跨域所用
@RestController
public class CarController {
    @Autowired
    private ShoppingCartService shoppingCartService;

    @RequestMapping("/shoppingCart")
    public ShoppingCar showShoppingCart(int id){
        System.out.println("/shoppingCart:"+id);
        ShoppingCar shoppingCar = shoppingCartService.findCar(id);
        System.out.println("/shoppingCart result："+shoppingCar.toString());
        return shoppingCar;
    }
}
