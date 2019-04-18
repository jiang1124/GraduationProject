package com.example.sever.Controller;


import com.example.sever.Entity.Product;
import com.example.sever.Sevice.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin//跨域所用
@RestController
public class HomeController {


    @Autowired
    private ProductService productService;

    @RequestMapping("/home")
    //select * from table_name limit 10
    public List<Product> homeProductList(){
        return productService.findSVTopFive();
    }

    @RequestMapping(path = "/search/k")
    public List<Product> searchKeyProductList(String key){
        System.out.println("/home/search/k key:"+key);
        List<Product> products = productService.findKeyMany(key);
        System.out.println("/home/search/k result："+products.toString());
        return products;
    }

    @RequestMapping(path = "/search/s")
    public List<Product> searchSortProductList(String sort){
        System.out.println("/home/search/s sort:"+sort);
        List<Product> products = productService.findSortMany(sort);
        System.out.println("/home/search/s result："+products.toString());
        return products;
    }
}
