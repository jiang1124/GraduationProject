package com.example.shoppingsystem.Entity;

import java.io.Serializable;
import java.util.List;

public class Shop implements Serializable {
    private int store_id;
    private String store_name;
    private boolean isSelect_shop;      //店铺是否在购物车中被选中
    private List<Goods> goods;

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

    public boolean isSelect_shop() {
        return isSelect_shop;
    }

    public void setSelect_shop(boolean select_shop) {
        isSelect_shop = select_shop;
    }

    public List<Goods> getGoods() {
        return goods;
    }

    public void setGoods(List<Goods> goods) {
        this.goods = goods;
    }

    @Override
    public String toString() {
        return "Car{" +
                "store_id=" + store_id +
                ", store_name='" + store_name + '\'' +
                ", isSelect_shop=" + isSelect_shop +
                ", goods=" + goods +
                '}';
    }
}
