package com.example.sever.dao;

import com.example.sever.Entity.Goods;
import com.example.sever.Entity.OrderExpand;
import com.example.sever.Entity.OrderMain;
import com.example.sever.Entity.Recipient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Calendar;
import java.util.List;

@Repository
public class OrderRepoImpl implements OrderRepo{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<OrderMain> findOrderMainAll(int user_id) {
        return jdbcTemplate.query("select * from order_main where user_id = ?",
                new Object[]{user_id},new BeanPropertyRowMapper(OrderMain.class));
    }

    @Override
    public List<OrderMain> findOrderMainPay(int user_id) {
        return jdbcTemplate.query("select * from order_main where user_id = ? and pay_info = ?",
                new Object[]{user_id,"已支付"},new BeanPropertyRowMapper(OrderMain.class));
    }

    @Override
    public List<OrderMain> findOrderMainNonPay(int user_id) {

        return jdbcTemplate.query("select * from order_main where user_id = ? and pay_info = ?",
                new Object[]{user_id,"未支付"},new BeanPropertyRowMapper(OrderMain.class));
    }

    @Override
    public List<OrderMain> findOrderMainFinish(int user_id) {
        return null;
    }

    @Override
    public int addOrder(int store_id, int user_id, String pay_info, double all_money, String goodsStr) {

        String today = dateStr(1);
        String timeStr = dateStr(2);
        String order_id = user_id+store_id+timeStr;
        Recipient recipient = findDefaultAddress(user_id);
        String address = recipient.getCity()+" "+recipient.getAddress();

        jdbcTemplate.update("INSERT order_main(order_id,store_id,user_id,date,recipient_name,phone,address,pay_info,all_money) " +
                "VALUE (?,?,?,?,?,?,?,?,?)",order_id,store_id,user_id,today,recipient.getRecipient_name(),recipient.getPhone()
                ,address,pay_info,all_money);
        int product_id =0;
        for(int i = 0;i<goodsStr.length();i++) {
            char idStr = goodsStr.charAt(i);
            if(idStr!=','){
                product_id=product_id*10+(idStr-'0');
               }else{
                List<Goods> goods = jdbcTemplate.query("select p.product_id,p.store_id,pro_name," +
                                "pro_detail,pro_price,pro_favl,pro_num,type,pro_sale,pro_image,extra_money," +
                                "product_num from product as p inner JOIN cart as c where p.product_id = c.product_id " +
                                "and p.product_id = ? and c.user_id = ?;",
                        new Object[]{product_id,user_id},new BeanPropertyRowMapper(Goods.class));
                Goods g = goods.get(0);
                jdbcTemplate.update("INSERT order_expand(order_id,product_id,store_id,pro_price,pro_num,pro_name,pro_image,extra_money) " +
                        "VALUE (?,?,?,?,?,?,?,?)", order_id,g.getProduct_id(), store_id,g.getPro_favl(),g.getPro_num(),g.getPro_name(),g.getPro_image(),g.getExtra_money());
                product_id =0;
            }

        }
        return 0;
    }

    @Override
    public int updateOrderMain(int order_id, String state) {
        return 0;
    }

    public String dateStr(int type){
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
        String dateStr = ""+year+month+day+hour+minute+second;
        if(type==1)
            return date;
        else
            return dateStr;
    }

    public Recipient findDefaultAddress(int user_id){
        List<Recipient> res = jdbcTemplate.query("select * from recipient where user_id = ? and state = ?",
                new Object[]{user_id,"default"},new BeanPropertyRowMapper(Recipient.class));
        if(res!=null&&res.size()>0)
            return res.get(0);
        return null;
    }

    @Override
    public List<OrderMain> storeFindOrderMain(int store_id) {
        return jdbcTemplate.query("select * from order_main where store_id = ? and pay_info!='未支付'",
                new Object[]{store_id},new BeanPropertyRowMapper(OrderMain.class));
    }

    @Override
    public List<OrderExpand> findOrderProduct(String order_id) {
        return jdbcTemplate.query("select * from order_expand where order_id = ?",
                new Object[]{order_id},new BeanPropertyRowMapper(OrderExpand.class));
    }

    @Override
    public String updateOrderState(String order_id, String extra) {
         jdbcTemplate.update("update order_main set pay_info = '已处理',extra=? where order_id=? ",
                 extra,order_id);
        return "成功";
    }
}
