package com.example.shoppingsystem.Entity;

import java.io.Serializable;
import java.util.List;

public class Car implements Serializable{
    private int user_id;
    private List<Shop> stores;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public List<Shop> getStores() {
        return stores;
    }

    public void setStores(List<Shop> stores) {
        this.stores = stores;
    }

    @Override
    public String toString() {
        return "Car{" +
                "user_id=" + user_id +
                ", stores=" + stores +
                '}';
    }
}
