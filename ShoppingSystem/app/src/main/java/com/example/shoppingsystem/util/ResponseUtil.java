package com.example.shoppingsystem.util;

import com.example.shoppingsystem.Entity.Car;
import com.example.shoppingsystem.Entity.OrderMain;
import com.example.shoppingsystem.Entity.Product;
import com.example.shoppingsystem.Entity.Store;
import com.example.shoppingsystem.Entity.User;
import com.example.shoppingsystem.Entity.Recipient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 79124 on 2019/4/17.
 */

public class ResponseUtil {
    final static public String Web = "http://10.0.2.2:8080";

    /**
     * 将返回的JSon数据解析成RecipientList
     */
    public static List<Recipient> handleRecipientListResponse(String Response){
        try{
            Gson gson = new Gson();
            List<Recipient> recipientList = new ArrayList<>();
            recipientList =gson.fromJson(Response, new TypeToken<List<Recipient>>() {}.getType());
            return recipientList;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将返回的JSon数据解析成Recipient实体类
     */
    public static Recipient handleRecipientResponse(String Response){
        try{
            Gson gson = new Gson();
            Recipient recipient =gson.fromJson(Response, Recipient.class);
            return recipient;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将返回的JSon数据解析成ProductList
     */
    public static List<Product> handleProductList(String Response){
        try{
            Gson gson = new Gson();
            List<Product> productList = new ArrayList<>();
            productList =gson.fromJson(Response, new TypeToken<List<Product>>() {}.getType());
            return productList;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 将返回的JSon数据解析成User
     */
    public static User handleUser(String Response){
        try{
            Gson gson = new Gson();
            User user =gson.fromJson(Response, User.class);
            return user;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 将返回的JSon数据解析成ShoppingCar
     */
    public static Car handleShoppingCart(String Response){
        try{
            Gson gson = new Gson();
            Car shoppingCar =gson.fromJson(Response, Car.class);
            return shoppingCar;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 将返回的JSon数据解析成ProductList
     */
    public static List<OrderMain> handleOrderMainList(String Response){
        try{
            Gson gson = new Gson();
            List<OrderMain> orderMainList = new ArrayList<>();
            orderMainList =gson.fromJson(Response, new TypeToken<List<OrderMain>>() {}.getType());
            return orderMainList;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 将返回的JSon数据解析成ShoppingCar
     */
    public static Store handleStore(String Response){
        try{
            Gson gson = new Gson();
            Store store =gson.fromJson(Response, Store.class);
            return store;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
