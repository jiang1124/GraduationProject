package com.example.sever.Controller;

import com.example.sever.Entity.OrderExpand;
import com.example.sever.Entity.OrderMain;
import com.example.sever.Sevice.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin//跨域所用
@RestController
public class OrderController {
    @Autowired
    private OrderService orderService;

    @RequestMapping("/addOrder")
    public void addOrder(int store_id, String goodsStr,int user_id,String state,double all_money){
        System.out.println("addOrder stores_id:"+store_id+" goodsStr:"+goodsStr+"user_id"+user_id);
        orderService.addOrder(store_id, user_id, state, all_money,goodsStr);
    }

    @RequestMapping("/findAllOrder")
    public List<OrderMain> findAllOrder(int user_id,String type){
        System.out.println("findAllOrder user_id:"+user_id);
        if(type.equals("nopay")) {
            return orderService.findOrderMainNonPay(user_id);
        }else if(type.equals("pay")){
            return orderService.findOrderMainPay(user_id);
        }else {
            return orderService.findOrderMainAll(user_id);
        }
    }

    @RequestMapping("/storeOrderList")
    public List<OrderMain> storeFindOrderMain(int store_id,int page){
        return orderService.storeFindOrderMain(store_id,page);
    }

    @RequestMapping("/findOrderProduct")
    public List<OrderExpand> findOrderProduct(String order_id,int page){
        return orderService.findOrderProduct(order_id,page);
    }

    @RequestMapping("/updateOrderState")
    public String updateOrderState(String order_id,String extra){
        return orderService.updateOrderState(order_id,extra);
    }
}
