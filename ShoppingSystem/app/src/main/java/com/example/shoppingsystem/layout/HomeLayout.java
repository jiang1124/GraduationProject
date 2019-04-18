package com.example.shoppingsystem.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;


import com.example.shoppingsystem.R;

/**
 * Created by 79124 on 2019/4/14.
 */

public class HomeLayout extends LinearLayout {


    public HomeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.home_layout,this);
    }

}
