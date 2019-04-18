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
        List<Product> res = jdbcTemplate.query("select * from product order by pro_sale desc  limit 10", new Object[]{},
                new BeanPropertyRowMapper(Product.class));

        return res;
    }

    @Override
    public List<Product> findKeyMany(String key) {
        if(key !="") {
            List<Product> res = jdbcTemplate.query("select * from product where pro_name like ?;", new Object[]{"%" + key + "%"},
                    new BeanPropertyRowMapper(Product.class));
            if (res != null && res.size() > 0) {
                return res;
            }
        }
        return null;
    }

    @Override
    public List<Product> findSortMany(String sort) {
        List<Product> res = jdbcTemplate.query("select * from product where type = ?;", new Object[]{sort},
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
