package com.example.shoppingsystem;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends BaseActiity implements View.OnClickListener{

    private List<Product> productList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null)
            actionBar.hide();
        initFruits();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.product_list_layout_recycler);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        ProductListAdapter adapter = new ProductListAdapter(productList,MainActivity.this);
        recyclerView.setAdapter(adapter);

        Button homeButton = (Button) findViewById(R.id.home_button);
        Button sortButton = (Button) findViewById(R.id.sort_button);
        Button shoppingCartButton =(Button) findViewById(R.id.shopping_cart_button);
        Button personButton =(Button) findViewById(R.id.person_button);
        homeButton.setOnClickListener(this);
        sortButton.setOnClickListener(this);
        shoppingCartButton.setOnClickListener(this);
        personButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        LinearLayout homeView = (LinearLayout) findViewById(R.id.product_list_layout_main);
        LinearLayout sortView = (LinearLayout) findViewById(R.id.sort_layout_main);
        LinearLayout shoppingCartView = (LinearLayout) findViewById(R.id.shopping_cart_layout_main);
        LinearLayout personView = (LinearLayout) findViewById(R.id.person_layout_main);
        switch (v.getId()){
            case R.id.home_button:
                PageSwitch(sortView, shoppingCartView, personView, homeView);
                break;
            case R.id.sort_button:
                PageSwitch(homeView, shoppingCartView, personView, sortView);
                break;
            case R.id.shopping_cart_button:
                PageSwitch(homeView, sortView, personView, shoppingCartView);
                break;
            case R.id.person_button:
                PageSwitch(homeView, sortView, shoppingCartView, personView);
                break;
            default:
                break;
        }
    }
//页面切换
    private final void PageSwitch(LinearLayout goneView1,LinearLayout goneView2,LinearLayout goneView3,LinearLayout visibleView){
        goneView1.setVisibility(View.GONE);
        goneView2.setVisibility(View.GONE);
        goneView3.setVisibility(View.GONE);
        visibleView.setVisibility(View.VISIBLE);
    }

    private void initFruits() {
            Product apple = new Product("1",getRandomLengthName("Apple"), R.drawable.apple_pic);
            productList.add(apple);
            Product banana = new Product("2",getRandomLengthName("Banana"), R.drawable.banana_pic);
            productList.add(banana);
            Product orange = new Product("4",getRandomLengthName("Orange"), R.drawable.orange_pic);
            productList.add(orange);
            Product watermelon = new Product("5",getRandomLengthName("Watermelon"), R.drawable.watermelon_pic);
            productList.add(watermelon);
            Product pear = new Product("6",getRandomLengthName("Pear"), R.drawable.pear_pic);
            productList.add(pear);
            Product grape = new Product("7",getRandomLengthName("Grape"), R.drawable.grape_pic);
            productList.add(grape);
            Product pineapple = new Product("8",getRandomLengthName("Pineapple"), R.drawable.pineapple_pic);
            productList.add(pineapple);
            Product strawberry = new Product("9",getRandomLengthName("Strawberry"), R.drawable.strawberry_pic);
            productList.add(strawberry);
            Product cherry = new Product("10",getRandomLengthName("Cherry"),R.drawable.cherry_pic);
            productList.add(cherry);
            Product mango = new Product("11",getRandomLengthName("Mango"), R.drawable.mango_pic);
            productList.add(mango);
    }

    private String getRandomLengthName(String name) {
        Random random = new Random();
        int length = random.nextInt(20) + 1;
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            builder.append(name);
        }
        return builder.toString();
    }
}
