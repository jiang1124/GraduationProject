package com.example.sever.dao;

import com.example.sever.Entity.Goods;
import com.example.sever.Entity.OrderExpand;
import com.example.sever.Entity.OrderMain;

import java.util.List;

public interface OrderRepo {

    public List<OrderMain> findOrderMainAll(int user_id);

    public List<OrderMain> findOrderMainPay(int user_id);

    public List<OrderMain> findOrderMainNonPay(int user_id);

    public List<OrderMain> findOrderMainFinish(int user_id);

    public int addOrder(int store_id, int user_id, String pay_info, double all_money, String goodsStr);

    public int updateOrderMain(int order_id,String state);

    public List<OrderMain> storeFindOrderMain(int store_id);

    public List<OrderExpand> findOrderProduct(String order_id);

    public String updateOrderState(String order_id,String extra);
}
