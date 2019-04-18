package com.example.sever.Controller;


import com.example.sever.Entity.Recipient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/person")
@RestController
public class PersonController {

    @RequestMapping("/recipientList")
    public List<Recipient> getRecipientList(){
        List<Recipient> recipientList = new ArrayList<>();
        return recipientList;
    }

}
