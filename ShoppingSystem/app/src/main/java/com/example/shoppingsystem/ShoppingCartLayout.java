package com.example.shoppingsystem;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

/**
 * Created by 79124 on 2019/4/9.
 */

public class ShoppingCartLayout extends LinearLayout {
    public ShoppingCartLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.shopping_cart_layout, this);
    }
}
