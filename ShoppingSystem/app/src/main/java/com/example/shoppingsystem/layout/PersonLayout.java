package com.example.shoppingsystem.layout;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.shoppingsystem.R;
import com.example.shoppingsystem.activity.LoginActivity;
import com.example.shoppingsystem.activity.MyAddressActivity;
import com.example.shoppingsystem.activity.MyCollectionActivity;
import com.example.shoppingsystem.activity.MyHistoryActivity;
import com.example.shoppingsystem.activity.MyOrderActivity;
import com.example.shoppingsystem.Entity.User;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by 79124 on 2019/4/9.
 */

public class PersonLayout extends LinearLayout {

    public PersonLayout(Context context, AttributeSet attrs){
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.person_layout,this);
    }
}
