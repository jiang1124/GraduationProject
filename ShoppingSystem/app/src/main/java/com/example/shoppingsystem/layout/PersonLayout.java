package com.example.shoppingsystem.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.example.shoppingsystem.R;
import com.example.shoppingsystem.classfile.User;

/**
 * Created by 79124 on 2019/4/9.
 */

public class PersonLayout extends LinearLayout {
    private User user;
    public PersonLayout(Context context, AttributeSet attrs){
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.person_layout,this);

//        initUser();
//        ImageView personImage = (ImageView) findViewById(R.id.person_image);
//        personImage.setImageResource(user.getUserImageId());
    }

    private void initUser(){
        String id ="1",name = "小明",password = "666666";
        int imageId = R.drawable.users_1,lv = 2,integral =99;
        user = new User(id,name,imageId,password,lv,integral);
    }
}
