package com.example.shoppingsystem.layout;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.example.shoppingsystem.R;
import com.example.shoppingsystem.adapter.ProductListAdapter;
import com.example.shoppingsystem.emtity.Product;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by 79124 on 2019/4/14.
 */

public class HomeLayout extends LinearLayout {
    @InjectView(R.id.rv_home_list)
    RecyclerView recyclerView;

    private List<Product> productList = new ArrayList<>();

    public HomeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.home_layout,this);

        ButterKnife.inject(this);
        initProductList();
        GridLayoutManager layoutManager = new GridLayoutManager(context, 2);
        recyclerView.setLayoutManager(layoutManager);
        ProductListAdapter productListAdapter = new ProductListAdapter(productList);
        recyclerView.setAdapter(productListAdapter);
    }

    /*
     * 初始化
     */
    private void initProductList() {
        Product apple = new Product("1", "Apple", R.drawable.apple_pic,15,9,"苹果",99,"水果",10,"红色");
        productList.add(apple);
        Product banana = new Product("2", "Banana", R.drawable.banana_pic,25,19,"香蕉",34,"水果",10,"黄色");
        productList.add(banana);
        Product orange = new Product("3", "Orange", R.drawable.orange_pic,35,29,"橘子",659,"水果",10,"橙色");
        productList.add(orange);
        Product watermelon = new Product("4", "Watermelon", R.drawable.watermelon_pic,45,39,"西瓜",95,"水果",10,"绿色");
        productList.add(watermelon);
        Product pear = new Product("5", "Pear", R.drawable.pear_pic,55,49,"梨子",94,"水果",10,"黄色");
        productList.add(pear);
        Product grape = new Product("6", "Grape", R.drawable.grape_pic,65,59,"葡萄",96,"水果",10,"紫色");
        productList.add(grape);
        Product pineapple = new Product("7", "Pineapple", R.drawable.pineapple_pic,75,69,"菠萝",39,"水果",10,"黄色");
        productList.add(pineapple);
        Product strawberry = new Product("8", "Strawberry", R.drawable.strawberry_pic,85,79,"草莓",89,"水果",10,"红色");
        productList.add(strawberry);
        Product cherry = new Product("9", "Cherry", R.drawable.cherry_pic,95,89,"樱桃",19,"水果",10,"红色");
        productList.add(cherry);
        Product mango = new Product("10", "Mango", R.drawable.mango_pic,105,99,"芒果",84,"水果",10,"黄色");
        productList.add(mango);
    }
}
