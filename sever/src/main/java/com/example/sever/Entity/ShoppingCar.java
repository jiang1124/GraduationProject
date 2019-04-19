package com.example.sever.Entity;

import java.util.List;

/**
 * 购物车数据类
 */

public class ShoppingCar {

    private int user_id;
    private List<Store> stores;

    public List<Store> getStores() {
        return stores;
    }

    public void setStores(List<Store> stores) {
        this.stores = stores;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public static class Store {

        private int store_id;
        private String store_name;
        private boolean isSelect_shop;      //店铺是否在购物车中被选中
        private List<Goods> goods;
//

        public Store(){}

//
        public List<Goods> getGoods() {
            return goods;
        }

        public void setGoods(List<Goods> goods) {
            this.goods = goods;
        }

        public boolean getIsSelect_shop() {
            return isSelect_shop;
        }

        public void setIsSelect_shop(boolean select_shop) {
            isSelect_shop = select_shop;
        }

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

        @Override
        public String toString() {
            return "Store{" +
                    "store_id=" + store_id +
                    ", store_name='" + store_name + '\'' +
                    ", isSelect_shop=" + isSelect_shop +
                    ", goods=" + goods +
                    '}';
        }

        public static class Goods{
            private Product product;
            private int product_num;
            private boolean isSelect_product;//商品是否在购物车中被选中

            //

            public Goods(){}


            //


            public boolean getIsSelect_product() {
                return isSelect_product;
            }

            public void setIsSelect_product(boolean select_product) {
                isSelect_product = select_product;
            }

            public int getProduct_num() {
                return product_num;
            }

            public void setProduct_num(int product_num) {
                this.product_num = product_num;
            }

            public Product getProduct() {
                return product;
            }

            public void setProduct(Product product) {
                this.product = product;
            }

            @Override
            public String toString() {
                return "Goods{" +
                        "product=" + product +
                        ", product_num=" + product_num +
                        ", isSelect_product=" + isSelect_product +
                        '}';
            }
        }
    }

    @Override
    public String toString() {
        return "ShoppingCar{" +
                "user_id=" + user_id +
                ", stores=" + stores +
                '}';
    }
}
