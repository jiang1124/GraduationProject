package com.example.sever.Controller;


import com.example.sever.Entity.Product;
import com.example.sever.Sevice.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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

    @RequestMapping(path = "/history")
    public List<Product> findHistory(int user_id){
        System.out.println("/history user_id:"+user_id);
        List<Product> products = new ArrayList<>();
        products = productService.findHistory(user_id);
        System.out.println("/history result："+products.toString());
        return products;
    }

    @RequestMapping(path = "/collection")
    public List<Product> findCollection(int user_id){
        System.out.println("/collection user_id:"+user_id);
        List<Product> products = new ArrayList<>();
        products = productService.findCollection(user_id);
        System.out.println("/collection result："+products.toString());
        return products;
    }

    @RequestMapping("/addCollection")
    public int addCollection(int user_id,int product_id,int store_id){
        return productService.addCollection(user_id, product_id, store_id);
    }

    @RequestMapping("/addHistory")
    public int addHistory(int user_id,int product_id,int store_id){
        return productService.addHistory(user_id,product_id,store_id);
    }

    @RequestMapping("/deleteCollection")
    public int deleteCollection(int user_id,int product_id){
        return productService.deleteCollection(user_id, product_id);
    }

    @RequestMapping("/deleteHistory")
    public int deleteHistory(int user_id,int product_id){
        return productService.deleteHistory(user_id,product_id);
    }

    @RequestMapping("/storeProducts")
    public List<Product> storeProducts(int store_id){
        return productService.findStoreProducts(store_id);
    }

    @RequestMapping("/updateProduct")
    public String updateProduct(int product_id, String product_name,double product_price,double product_favl,double extra_money,String type){
        return productService.updateProduct(product_id, product_name, product_price, product_favl, extra_money, type);
    }

    @RequestMapping("/addProductDetail")
    public String addProduct(int store_id, String product_name,double product_price,double product_favl,double extra_money,String type){
        return productService.addProduct(store_id, product_name, product_price, product_favl, extra_money, type);
    }

    @RequestMapping("/deleteProduct")
    public String delProduct(int product_id){
        return productService.delProduct(product_id);
    }
}
