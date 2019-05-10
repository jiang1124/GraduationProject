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
    public List<Product> searchKeyProductList(String key,int page){
        System.out.println("/search/k key:"+key);
        return productService.findKeyMany(key,page);
    }

    @RequestMapping(path = "/search/s")
    public List<Product> searchSortProductList(String sort,int page){
        System.out.println("/search/s sort:"+sort);
        return productService.findSortMany(sort,page);
    }

    @RequestMapping(path = "/search/p")
    public List<Product> findKeyByPrice(String key,int page){
        System.out.println("/search/p sort:"+key);
        return productService.findKeyByPrice(key,page);
    }

    @RequestMapping(path = "/search/v")
    public List<Product> findKeyBySale(String key,int page){
        System.out.println("/search/p sort:"+key);
        return  productService.findKeyBySale(key,page);
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
    public List<Product> storeProducts(int store_id,int page){
        return productService.findStoreProducts(store_id,page);
    }

    @RequestMapping("/storeSearchProducts")
    public List<Product> storeSearchProducts(int store_id,String product_name,int page){
        return productService.findStoreProducts(store_id,product_name,page);
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
