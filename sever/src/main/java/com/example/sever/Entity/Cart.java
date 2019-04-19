package com.example.sever.Entity;

public class Cart {
    private int user_id;
    private int product_id;
    private int store_id;
    private int product_num;
    private String product_name;
    private String store_name;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getStore_id() {
        return store_id;
    }

    public void setStore_id(int store_id) {
        this.store_id = store_id;
    }

    public int getProduct_num() {
        return product_num;
    }

    public void setProduct_num(int product_num) {
        this.product_num = product_num;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "user_id=" + user_id +
                ", product_id=" + product_id +
                ", store_id=" + store_id +
                ", product_num=" + product_num +
                ", product_name='" + product_name + '\'' +
                ", store_name='" + store_name + '\'' +
                '}';
    }
}
