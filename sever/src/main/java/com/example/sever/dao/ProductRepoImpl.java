package com.example.sever.dao;

import com.example.sever.Entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Calendar;
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
    public List<Product> findKeyByPrice(String key) {
        if(key !="") {
            List<Product> res = jdbcTemplate.query("select * from product where pro_name like ? order by pro_price;", new Object[]{"%" + key + "%"},
                    new BeanPropertyRowMapper(Product.class));
            if (res != null && res.size() > 0) {
                return res;
            }
        }
        return null;
    }

    @Override
    public List<Product> findKeyBySale(String key) {
        if(key !="") {
            List<Product> res = jdbcTemplate.query("select * from product where pro_name like ? order by pro_sale desc;", new Object[]{"%" + key + "%"},
                    new BeanPropertyRowMapper(Product.class));
            if (res != null && res.size() > 0) {
                return res;
            }
        }
        return null;
    }

    @Override
    public List<Product> findHistory(int user_id) {
        List<Product> res = jdbcTemplate.query("SELECT * FROM history as h INNER JOIN product as p where user_id = ? and h.product_id = p.product_id order by date;",
                new Object[]{user_id}, new BeanPropertyRowMapper(Product.class));
        return res;
    }

    @Override
    public List<Product> findCollection(int user_id) {
        List<Product> res = jdbcTemplate.query("SELECT * FROM collection as c INNER JOIN product as p where user_id = ? and c.product_id = p.product_id;",
                new Object[]{user_id}, new BeanPropertyRowMapper(Product.class));
        return res;
    }

    @Override
    public int addCollection(int user_id, int product_id, int store_id) {
        List<Product> res = jdbcTemplate.query("SELECT * FROM collection  where user_id = ? and product_id = ?;",
                new Object[]{user_id,product_id}, new BeanPropertyRowMapper(Product.class));
        if(res!=null&&res.size()>0)
            return 1;
        else{
            jdbcTemplate.update("INSERT INTO collection(user_id,product_id,store_id)VALUE (?,?,?);",user_id,product_id,store_id);
            return 0;
        }
    }

    @Override
    public int addHistory(int user_id, int product_id, int store_id) {
        List<Product> res = jdbcTemplate.query("SELECT * FROM history  where user_id = ? and product_id = ?;",
                new Object[]{user_id,product_id}, new BeanPropertyRowMapper(Product.class));
        String date = dateStr();
        if(res!=null&&res.size()>0) {
            jdbcTemplate.update("UPDATE history set date = ? where user_id=? and product_id = ?",date,user_id,product_id);
            return 1;
        }
        else{
            jdbcTemplate.update("INSERT INTO history(user_id,product_id,store_id,date)VALUE (?,?,?,?);",user_id,product_id,store_id,date);
            return 0;
        }
    }

    @Override
    public int deleteCollection(int user_id, int product_id) {
        jdbcTemplate.update("DELETE from collection where user_id =? and product_id =?",user_id,product_id);
        return 0;
    }

    @Override
    public int deleteHistory(int user_id, int product_id) {
        jdbcTemplate.update("DELETE from history where user_id =? and product_id =?",user_id,product_id);
        return 0;
    }

    public String dateStr(){
        //创建Calendar对象
        Calendar cal=Calendar.getInstance();

        //用Calendar类提供的方法获取年、月、日、时、分、秒
        int year  =cal.get(Calendar.YEAR);   //年
        int month =cal.get(Calendar.MONTH)+1;  //月  默认是从0开始  即1月获取到的是0
        int day   =cal.get(Calendar.DAY_OF_MONTH);  //日，即一个月中的第几天
        int hour  =cal.get(Calendar.HOUR_OF_DAY);  //小时
        int minute=cal.get(Calendar.MINUTE);   //分
        int second=cal.get(Calendar.SECOND);  //秒

        //拼接成字符串输出
        String date=year+"-"+month+"-"+day+" "+hour+":"+minute+":"+second;
        return date;
    }
}
