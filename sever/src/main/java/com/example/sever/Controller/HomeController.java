package com.example.sever.Controller;


import com.example.sever.Entity.Product;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Controller
@RestController
public class HomeController {

    @RequestMapping("/home")
    public List<Product> homeProductList(){
        List<Product> productsList = new ArrayList<>();
        Product apple = new Product("1", "Apple", "http://10.0.2.2:8080/image/apple_pic.png",15,9,"苹果",99,"水果",10,"红色");
        productsList.add(apple);
        Product banana = new Product("2", "Banana", "http://10.0.2.2:8080/image/banana_pic.png",25,19,"香蕉",34,"水果",10,"黄色");
        productsList.add(banana);
        Product orange = new Product("3", "Orange", "http://10.0.2.2:8080/image/orange_pic.png",35,29,"橘子",659,"水果",10,"橙色");
        productsList.add(orange);
        Product watermelon = new Product("4", "Watermelon", "http://10.0.2.2:8080/image/watermelon_pic.png",45,39,"西瓜",95,"水果",10,"绿色");
        productsList.add(watermelon);
        Product pear = new Product("5", "Pear", "http://10.0.2.2:8080/image/pear_pic.png",55,49,"梨子",94,"水果",10,"黄色");
        productsList.add(pear);
        Product grape = new Product("6", "Grape", "http://10.0.2.2:8080/image/grape_pic.png",65,59,"葡萄",96,"水果",10,"紫色");
        productsList.add(grape);
        Product pineapple = new Product("7", "Pineapple", "http://10.0.2.2:8080/image/pineapple_pic.png",75,69,"菠萝",39,"水果",10,"黄色");
        productsList.add(pineapple);
        Product strawberry = new Product("8", "Strawberry", "http://10.0.2.2:8080/image/strawberry_pic.png",85,79,"草莓",89,"水果",10,"红色");
        productsList.add(strawberry);
        Product cherry = new Product("9", "Cherry", "http://10.0.2.2:8080/image/cherry_pic.png",95,89,"樱桃",19,"水果",10,"红色");
        productsList.add(cherry);
        Product mango = new Product("10", "Mango", "http://10.0.2.2:8080/image/mango_pic.png",105,99,"芒果",84,"水果",10,"黄色");
        productsList.add(mango);
        return productsList;
    }
}
