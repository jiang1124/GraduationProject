package com.example.sever.Sevice;

import com.example.sever.Entity.Goods;
import com.example.sever.Entity.OrderExpand;
import com.example.sever.Entity.OrderMain;
import com.example.sever.dao.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepo orderRepo;

    @Override
    public List<OrderMain> findOrderMainAll(int user_id) {
        return orderRepo.findOrderMainAll(user_id);
    }

    @Override
    public List<OrderMain> findOrderMainPay(int user_id) {
        return orderRepo.findOrderMainPay(user_id);
    }

    @Override
    public List<OrderMain> findOrderMainNonPay(int user_id) {
        return orderRepo.findOrderMainNonPay(user_id);
    }

    @Override
    public List<OrderMain> findOrderMainFinish(int user_id) {
        return orderRepo.findOrderMainFinish(user_id);
    }

    @Override
    public int addOrder(int store_id, int user_id, String pay_info, double all_money, String goodsStr) {
        return orderRepo.addOrder(store_id, user_id, pay_info, all_money,goodsStr);
    }

    @Override
    public int updateOrderMain(int order_id, String state) {
        return orderRepo.updateOrderMain(order_id,state);
    }

    @Override
    public List<OrderMain> storeFindOrderMain(int store_id,int page) {
        return orderRepo.storeFindOrderMain(store_id,page);
    }

    @Override
    public List<OrderExpand> findOrderProduct(String order_id,int page) {
        return orderRepo.findOrderProduct(order_id,page);
    }

    @Override
    public String updateOrderState(String order_id, String extra) {
        return orderRepo.updateOrderState(order_id, extra);
    }
}
