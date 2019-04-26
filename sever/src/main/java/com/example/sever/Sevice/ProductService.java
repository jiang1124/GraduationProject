package com.example.sever.Sevice;

import com.example.sever.Entity.Product;

import java.util.List;

public interface ProductService {


        public List<Product> findSVTopFive();

        public List<Product> findKeyMany(String key);

        public List<Product> findSortMany(String sort);

        public List<Product> findKeyByPrice(String key);

        public List<Product> findKeyBySale(String key);

        public List<Product> findHistory(int user_id);

        public List<Product> findCollection(int user_id);

        public int addCollection(int user_id,int product_id,int store_id);

        public int addHistory(int user_id,int product_id,int store_id);

        public int deleteCollection(int user_id,int product_id);

        public int deleteHistory(int user_id,int product_id);

}
