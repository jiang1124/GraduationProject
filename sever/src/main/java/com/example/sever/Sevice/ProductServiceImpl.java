package com.example.sever.Sevice;

import com.example.sever.Entity.Product;
import com.example.sever.dao.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepo productRepo;

    @Override
    public List<Product> findStoreProducts(int store_id) {
        return productRepo.findStoreProducts(store_id);
    }

    @Override
    public List<Product> findSVTopFive() {
        return productRepo.findSVTopFive();
    }

    @Override
    public List<Product> findKeyMany(String key) {
        return productRepo.findKeyMany(key);
    }

    @Override
    public List<Product> findSortMany(String sort) {
        return productRepo.findSortMany(sort);
    }

    @Override
    public List<Product> findKeyByPrice(String key) {
        return productRepo.findKeyByPrice(key);
    }

    @Override
    public List<Product> findKeyBySale(String key) {
        return productRepo.findKeyBySale(key);
    }

    @Override
    public List<Product> findHistory(int user_id) {
        return productRepo.findHistory(user_id);
    }

    @Override
    public List<Product> findCollection(int user_id) {
        return productRepo.findCollection(user_id);
    }

    @Override
    public int addCollection(int user_id, int product_id, int store_id) {
        return productRepo.addCollection(user_id, product_id, store_id);
    }

    @Override
    public int addHistory(int user_id, int product_id, int store_id) {
        return productRepo.addHistory(user_id, product_id, store_id);
    }

    @Override
    public int deleteCollection(int user_id, int product_id) {
        return productRepo.deleteCollection(user_id, product_id);
    }

    @Override
    public int deleteHistory(int user_id, int product_id) {
        return productRepo.deleteHistory(user_id, product_id);
    }

    @Override
    public String updateProduct(int product_id, String product_name, double product_price, double product_favl, double extra_money, String type) {
        return productRepo.updateProduct(product_id, product_name, product_price, product_favl, extra_money, type);
    }

    @Override
    public String addProduct(int store_id, String product_name, double product_price, double product_favl, double extra_money, String type) {
        return productRepo.addProduct(store_id, product_name, product_price, product_favl, extra_money, type);
    }

    @Override
    public String delProduct(int product_id) {
        return productRepo.delProduct(product_id);
    }
}
