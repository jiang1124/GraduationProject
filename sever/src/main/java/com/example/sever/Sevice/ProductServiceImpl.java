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
    public List<Product> findSVTopFive() {
        return productRepo.findSVTopFive();
    }

    @Override
    public List<Product> findKeyMany(String id) {
        return productRepo.findKeyMany(id);
    }

    @Override
    public int add(Product product) {
        return 0;
    }

    @Override
    public int update(Product product) {
        return 0;
    }

    @Override
    public int delete(int id) {
        return 0;
    }
}
