package com.example.shoppingsystem;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * Created by 79124 on 2019/4/1.
 */

public class MenuLayout extends LinearLayout{
    public MenuLayout(Context context, AttributeSet attrs){
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.menu_layout,this);
        Button homeButton = (Button) findViewById(R.id.home_button);
        Button sortButton = (Button) findViewById(R.id.sort_button);
        Button shoppingCartButton = (Button) findViewById(R.id.shopping_cart_button);
        Button personButton = (Button) findViewById(R.id.person_button);
    }
}
