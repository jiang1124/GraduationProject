package com.example.sever.dao;

import com.example.sever.Entity.Cart;
import com.example.sever.Entity.Product;
import com.example.sever.Entity.ShoppingCar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ShoppingCartRepoImpl implements ShoppingCartRepo {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public ShoppingCar findCar(int user_id) {
       /* ShoppingCar shoppingCar =new ShoppingCar();
        Product product = new Product();
        product.setProduct_id(1);
        product.setPro_name("Apple");
        product.setPro_favl(12);
        product.setExtra_money(10);
        product.setPro_detail("苹果");
        product.setPro_image("http://10.0.2.2:8080/image/product/apple_pic.png");
        product.setType("水果");
        product.setStore_id(1);
        product.setPro_sale(10);
        product.setPro_num(10);
        ShoppingCar.Store.Goods good = new ShoppingCar.Store.Goods();
        good.setProduct(product);
        List<ShoppingCar.Store.Goods>gs = new ArrayList<>();
        gs.add(good);
        ShoppingCar.Store store =new ShoppingCar.Store();
        store.setGoods(gs);
        store.setStore_id(1);
        store.setStore_name("一号店");
        List<ShoppingCar.Store> ss=new ArrayList<>();
        ss.add(store);
        shoppingCar.setStores(ss);
        shoppingCar.setUser_id(1);
        return shoppingCar;
        */
       System.out.println("user_id:"+user_id);
        ShoppingCar shoppingCar =new ShoppingCar();
        List<Cart> car = jdbcTemplate.query("select * from cart where user_id = ? order by store_id",
                    new Object[]{user_id},new BeanPropertyRowMapper(Cart.class));
        System.out.println("cart:"+car.get(0).toString());
        int storeId=0;
        List<ShoppingCar.Store> ss=new ArrayList<>();
        List<ShoppingCar.Store.Goods>gs = new ArrayList<>();
        ShoppingCar.Store store =new ShoppingCar.Store();
        ShoppingCar.Store.Goods good = new ShoppingCar.Store.Goods();
        for(int i=0;i<car.size();++i){
            if(storeId!=car.get(i).getStore_id()) {
                storeId=car.get(i).getStore_id();
                System.out.println(storeId);
//                if(gs.size()>0){
                    store.setGoods(gs);
                    store.setStore_name(car.get(i).getStore_name());
                    store.setStore_id(car.get(i).getStore_id());
                    System.out.println("store:"+store.toString());
                    ss.add(store);
                    System.out.println("ss:"+ss.toString());
                    gs.clear();
//                }
            }
            List<Product> products =jdbcTemplate.query("select * from product where product_id = ?",
                    new Object[]{car.get(i).getProduct_id()},new BeanPropertyRowMapper(Product.class));
            Product product = products.get(0);
            System.out.println("product:"+product);
            good.setProduct(product);
            good.setProduct_num(car.get(i).getProduct_num());
            System.out.println("good:"+good);
            gs.add(good);
            System.out.println("gs:"+gs.toString());
        }
        shoppingCar.setUser_id(user_id);
        shoppingCar.setStores(ss);
        if(shoppingCar!=null)
            return shoppingCar;
        return null;
    }
}
