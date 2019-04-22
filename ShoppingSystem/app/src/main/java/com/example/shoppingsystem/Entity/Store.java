package com.example.shoppingsystem.Entity;

import java.io.Serializable;

public class Store implements Serializable {
    private int store_id;
    private String store_name;
    private String store_password;
    private String store_detail;
    private double fav_info;
    private int pro_num;
    private String address;

    public int getStore_id() {
        return store_id;
    }

    public void setStore_id(int store_id) {
        this.store_id = store_id;
    }

    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    public String getStore_password() {
        return store_password;
    }

    public void setStore_password(String store_password) {
        this.store_password = store_password;
    }

    public String getStore_detail() {
        return store_detail;
    }

    public void setStore_detail(String store_detail) {
        this.store_detail = store_detail;
    }

    public double getFav_info() {
        return fav_info;
    }

    public void setFav_info(double fav_info) {
        this.fav_info = fav_info;
    }

    public int getPro_num() {
        return pro_num;
    }

    public void setPro_num(int pro_num) {
        this.pro_num = pro_num;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
