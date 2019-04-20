package com.example.sever.dao;

import com.example.sever.Entity.*;
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
    public Car findCar(int user_id) {
       System.out.println("user_id:"+user_id);
        Car shoppingCar =new Car();
        shoppingCar.setUser_id(user_id);

        List<Cart> cart = jdbcTemplate.query("select distinct store_id,store_name from cart where user_id = ?",
                    new Object[]{user_id},new BeanPropertyRowMapper(Cart.class));
        System.out.println("cart:"+cart.toString());
        List<Shop> shops = new ArrayList<>();
        for(int i=0;i<cart.size();i++){
            int s_id =cart.get(i).getStore_id();
            Shop shop = new Shop();
            shop.setStore_id(s_id);
            String s_name =cart.get(i).getStore_name();
            shop.setStore_name(s_name);
            shop.setGoods(jdbcTemplate.query("select p.product_id,p.store_id,pro_name," +
                            "pro_detail,pro_price,pro_favl,pro_num,type,pro_sale,pro_image,extra_money," +
                            "product_num from product as p inner JOIN cart as c where p.product_id = c.product_id " +
                            "and p.store_id = ? and c.user_id = ?;",
                    new Object[]{s_id,user_id},new BeanPropertyRowMapper(Goods.class)));
            System.out.println("shops:"+shops.toString());
            shops.add(shop);
        }
        shoppingCar.setStores(shops);
        if(shoppingCar!=null)
            return shoppingCar;
        return null;
    }

    @Override
    public Car updateNum(int user_id, int product_id,int product_num) {
        jdbcTemplate.update("UPDATE cart set product_num = ? where user_id = ? and product_id = ?",
                product_num, user_id, product_id);
        return findCar(user_id);
    }

    @Override
    public Car delProduct(int user_id, String productIds) {
        if(productIds!=null&&productIds.length()>0){
            int product_id = 0;
            for(int i=0;i<productIds.length();i++){
                char id = productIds.charAt(i);
                if(id != ','&&i!=productIds.length()){
                    product_id = product_id * 10 + ( id - '0' );
                }else {
                    jdbcTemplate.update("delete from cart where user_id = ? and product_id = ?", user_id, product_id);
                    product_id = 0;
                }
            }
        }
        return findCar(user_id);
    }

    @Override
    public String addProduct(int user_id, int product_id) {
        List<Cart> cart = jdbcTemplate.query("select * from cart where user_id = ? and product_id = ?",
                new Object[]{user_id,product_id},new BeanPropertyRowMapper(Cart.class));
        if(cart.size()>0) {
            int num = cart.get(0).getProduct_num()+1;
            jdbcTemplate.update("UPDATE cart set product_num = ? where user_id = ? and product_id = ?",
                    num, user_id, product_id);
        }
        else {
            cart.clear();
            cart = jdbcTemplate.query("SELECT p.store_id,p.pro_name product_name,s.store_name " +
                    "FROM product as p INNER JOIN store as s " +
                    "WHERE p.store_id = s.store_id and p.product_id=?",new Object[]{product_id},new BeanPropertyRowMapper(Cart.class));
            Cart car = cart.get(0);
            jdbcTemplate.update("INSERT INTO cart(user_id,product_id,store_id,product_num,product_name,store_name)" +
                        " VALUES (?,?,?,?,?,?)",
                user_id,product_id,car.getStore_id(),1,car.getProduct_name(),car.getStore_name());
        }
        return "success";
    }
}
