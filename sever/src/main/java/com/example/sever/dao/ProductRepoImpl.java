package com.example.sever.dao;

import com.example.sever.Entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductRepoImpl implements ProductRepo{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Product> findSVTopFive() {
        List<Product> res = jdbcTemplate.query("select * from product order by productSaleVolume desc  limit 5", new Object[]{},
                new BeanPropertyRowMapper(Product.class));

        return res;
    }

    @Override
    public List<Product> findKeyMany(String id) {
        List<Product> res = jdbcTemplate.query("select * from product where productId = ?", new Object[]{id},
                new BeanPropertyRowMapper(Product.class));
        if(res!=null && res.size()>0){
            return res;
        }else{
            return null;
        }
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
