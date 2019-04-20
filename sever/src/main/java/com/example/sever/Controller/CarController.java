package com.example.sever.Controller;

import com.example.sever.Entity.Car;
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
    public Car showShoppingCart(int id){
        System.out.println("/shoppingCart:"+id);
        Car shoppingCar = shoppingCartService.findCar(id);
        System.out.println("/shoppingCart result："+shoppingCar.toString());
        return shoppingCar;
    }

    @RequestMapping("/addProduct")
    public String addProduct(int user_id, int product_id){
        System.out.println("/user_id, "+user_id+" product_id:"+product_id);
        String Str = shoppingCartService.addProduct(user_id,product_id);
        System.out.println("/shoppingCart result："+Str);
        return Str;
    }

    @RequestMapping("/updateProductNum")
    public Car updateProductNum(int user_id,int product_id,int product_num){
        System.out.println("/updateProductNum:"+"user_id:"+user_id+"product_id:"+product_id+"product_num:"+product_num);
        Car shoppingCar = shoppingCartService.updateNum(user_id, product_id, product_num);
        System.out.println("/updateProductNum result："+shoppingCar.toString());
        return shoppingCar;
    }

    @RequestMapping("/delProduct")
    public Car delProduct(int user_id, String productIds){
        System.out.println("/delProduct:"+"user_id:"+user_id+"productIdList:"+productIds);
        Car shoppingCar = shoppingCartService.delProduct(user_id, productIds);
        System.out.println("/delProduct result："+shoppingCar.toString());
        return shoppingCar;
    }
}
