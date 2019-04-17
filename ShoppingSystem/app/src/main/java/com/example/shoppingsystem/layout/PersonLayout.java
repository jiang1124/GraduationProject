package com.example.shoppingsystem.layout;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.shoppingsystem.R;
import com.example.shoppingsystem.activity.LoginActivity;
import com.example.shoppingsystem.activity.MyAddressActivity;
import com.example.shoppingsystem.activity.MyCollectionActivity;
import com.example.shoppingsystem.activity.MyHistoryActivity;
import com.example.shoppingsystem.activity.MyOrderActivity;
import com.example.shoppingsystem.emtity.User;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by 79124 on 2019/4/9.
 */

public class PersonLayout extends LinearLayout {
    @InjectView(R.id.tv_my_address)
    TextView myAddressTV;
    @InjectView(R.id.tv_my_collection)
    TextView myCollectionTV;
    @InjectView(R.id.tv_my_history)
    TextView myHistoryTV;
    @InjectView(R.id.tv_my_order)
    TextView myOrderTV;
    @InjectView(R.id.iv_my_head)
    ImageView myHead;

    private User user;
    private Context context;
    public PersonLayout(Context context, AttributeSet attrs){
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.person_layout,this);
        this.context = context;
        ButterKnife.inject(this);

        initUser();
        ImageView personImage = (ImageView) findViewById(R.id.iv_my_head);
        personImage.setImageResource(user.getUserImageId());
    }

    @OnClick({R.id.tv_my_address,R.id.tv_my_collection,R.id.tv_my_history,R.id.tv_my_order,R.id.iv_my_head})
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_my_address:
                Intent addressIntent = new Intent(context, MyAddressActivity.class);
                context.startActivity(addressIntent);
                break;
            case R.id.tv_my_collection:
                Intent collectionIntent = new Intent(context, MyCollectionActivity.class);
                context.startActivity(collectionIntent);
                break;
            case R.id.tv_my_history:
                Intent historyIntent = new Intent(context, MyHistoryActivity.class);
                context.startActivity(historyIntent);
                break;
            case R.id.tv_my_order:
                Intent orderIntent = new Intent(context, MyOrderActivity.class);
                context.startActivity(orderIntent);
                break;
            case R.id.iv_my_head:
                Intent headIntent = new Intent(context, LoginActivity.class);
                context.startActivity(headIntent);
                break;
            default:
                break;
        }
    }

    private void initUser(){
        String id ="1",name = "小明",password = "666666";
        int imageId = R.drawable.dota,lv = 2,integral =99;
        this.user = new User(id,name,imageId,password,lv,integral);
    }
}
