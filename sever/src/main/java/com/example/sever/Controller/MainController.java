package com.example.sever.Controller;


import com.example.sever.Entity.Product;
import com.example.sever.Entity.ShoppingCar;
import com.example.sever.Sevice.ProductService;
import com.example.sever.Sevice.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin//跨域所用
@RestController
public class MainController {


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



//    @Autowired
//    private CeNoteService ceNoteService;
//
//    @RequestMapping("/findAll")
//    public List<CeNote> findAll() {
//        return ceNoteService.findAll();
//    }
//
//    @RequestMapping("/findOne")
//    public CeNote findOne(int id) {
//        return ceNoteService.findOne(id);
//    }
////
////    @PostMapping("/add")
////    public Result add(@RequestBody CeNote note) {
////
////        try {
////            ceNoteService.add(note);
////            return new Result(true,"成功");
////        } catch (Exception e) {
////            return new Result(false,"失败");
////        }
////
////    }
}
