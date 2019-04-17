package com.example.sever.Controller;


import com.example.sever.Entity.Recipient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/test")
@RestController
public class TestController {

    @RequestMapping("/recipientTran")
    public class RecipientTran{
        @RequestMapping("/recipientList")
        public List<Recipient> getRecipientList(){
            List<Recipient> recipientList = new ArrayList<>();
            Recipient one = new Recipient("张三","安徽省合肥市","13112345678","1");
            Recipient two = new Recipient("李四","安徽省合肥市","13212345678","1");
            Recipient three = new Recipient("王二","安徽省合肥市","13312345678","1");
            Recipient four = new Recipient("赵大","安徽省合肥市","13412345678","1");
            recipientList.add(one);
            recipientList.add(two);
            recipientList.add(three);
            recipientList.add(four);
            return recipientList;
        }
    }

}
