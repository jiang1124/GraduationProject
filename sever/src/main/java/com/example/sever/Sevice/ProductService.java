package com.example.sever.Sevice;

import com.example.sever.Entity.Product;

import java.util.List;

public interface ProductService {

        public List<Product> findStoreProducts(int store_id,int page);

        public List<Product> findStoreProducts(int store_id,String key,int page);

        public List<Product> findSVTopFive();

        public List<Product> findKeyMany(String key,int page);

        public List<Product> findSortMany(String sort,int page);

        public List<Product> findKeyByPrice(String key,int page);

        public List<Product> findKeyBySale(String key,int page);

        public List<Product> findHistory(int user_id);

        public List<Product> findCollection(int user_id);

        public int addCollection(int user_id,int product_id,int store_id);

        public int addHistory(int user_id,int product_id,int store_id);

        public int deleteCollection(int user_id,int product_id);

        public int deleteHistory(int user_id,int product_id);

        public String updateProduct(int product_id, String product_name,double product_price,double product_favl,double extra_money,String type,String detail);

        public String addProduct(int store_id, String product_name,double product_price,double product_favl,double extra_money,String type,String detail);

        public String delProduct(int product_id);

}
