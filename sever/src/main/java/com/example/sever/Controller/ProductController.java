package com.example.sever.Controller;


import com.example.sever.Entity.Product;
import com.example.sever.Sevice.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin//跨域所用
@RestController
public class ProductController {


    @Autowired
    private ProductService productService;

    @RequestMapping("/home")
    //select * from table_name limit 10
    public List<Product> homeProductList(){
        System.out.println(productService.findSVTopFive());
        return productService.findSVTopFive();
    }

    @RequestMapping(path = "/search/k")
    public List<Product> searchKeyProductList(String key){
        System.out.println("/search/k key:"+key);
        List<Product> products = productService.findKeyMany(key);
        System.out.println("/search/k result："+products.toString());
        return products;
    }

    @RequestMapping(path = "/search/s")
    public List<Product> searchSortProductList(String sort){
        System.out.println("/search/s sort:"+sort);
        List<Product> products = productService.findSortMany(sort);
        System.out.println("/search/s result："+products.toString());
        return products;
    }

    @RequestMapping(path = "/search/p")
    public List<Product> findKeyByPrice(String key){
        System.out.println("/search/p sort:"+key);
        List<Product> products = productService.findKeyByPrice(key);
        System.out.println("/search/p result："+products.toString());
        return products;
    }

    @RequestMapping(path = "/search/v")
    public List<Product> findKeyBySale(String key){
        System.out.println("/search/p sort:"+key);
        List<Product> products = productService.findKeyBySale(key);
        System.out.println("/search/p result："+products.toString());
        return products;
    }
}
