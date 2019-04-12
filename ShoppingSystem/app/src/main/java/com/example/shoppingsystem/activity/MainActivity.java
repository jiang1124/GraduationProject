package com.example.shoppingsystem.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.shoppingsystem.R;
import com.example.shoppingsystem.classfile.BaseActivity;
import com.example.shoppingsystem.adapter.ProductListAdapter;
import com.example.shoppingsystem.classfile.Product;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements View.OnClickListener{

    private List<Product> productList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        initProductList();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.product_list_layout_main);
        GridLayoutManager layoutManager = new GridLayoutManager(MainActivity.this, 3);
        recyclerView.setLayoutManager(layoutManager);
        ProductListAdapter productListAdapter = new ProductListAdapter(productList,MainActivity.this);
        recyclerView.setAdapter(productListAdapter);

        Button searchButton = (Button) findViewById(R.id.search_button);
        searchButton.setOnClickListener(this);

        Button homeButton = (Button) findViewById(R.id.home_button);
        Button sortButton = (Button) findViewById(R.id.sort_button);
        Button shoppingCartButton =(Button) findViewById(R.id.shopping_cart_button);
        Button personButton =(Button) findViewById(R.id.person_button);
        homeButton.setOnClickListener(this);
        sortButton.setOnClickListener(this);
        shoppingCartButton.setOnClickListener(this);
        personButton.setOnClickListener(this);
    }

    /*
     * 下菜单栏点击事件及搜索点击事件
     */
    @Override
    public void onClick(View v){
        RecyclerView homeView = (RecyclerView) findViewById(R.id.product_list_layout_main);
        LinearLayout sortView = (LinearLayout) findViewById(R.id.sort_layout_main);
        LinearLayout shoppingCartView = (LinearLayout) findViewById(R.id.shopping_cart_layout_main);
        LinearLayout personView = (LinearLayout) findViewById(R.id.person_layout_main);
        View searchView = (View) findViewById(R.id.toolbar_main);
        switch (v.getId()){
            case R.id.search_button:
                EditText searchEditText = (EditText) findViewById(R.id.search_edit);
                String searchContent = searchEditText.getText().toString();
                Intent intent = new Intent(MainActivity.this,SearchActivity.class);
                intent.putExtra("key",searchContent);
                startActivity(intent);
                break;
            case R.id.home_button:
                PageSwitch(sortView, shoppingCartView, personView, homeView);
                searchView.setVisibility(View.VISIBLE);
                break;
            case R.id.sort_button:
                PageSwitch(homeView, shoppingCartView, personView, sortView);
                searchView.setVisibility(View.GONE);
                break;
            case R.id.shopping_cart_button:
                PageSwitch(homeView, sortView, personView, shoppingCartView);
                searchView.setVisibility(View.GONE);
                break;
            case R.id.person_button:
                PageSwitch(homeView, sortView, shoppingCartView, personView);
                searchView.setVisibility(View.GONE);
                break;
            default:
                break;
        }
    }

    /*
     * 页面切换
     */
    private final void PageSwitch(View goneView1,View goneView2,View goneView3,View visibleView){
        goneView1.setVisibility(View.GONE);
        goneView2.setVisibility(View.GONE);
        goneView3.setVisibility(View.GONE);
        visibleView.setVisibility(View.VISIBLE);
    }

    /*
     * 初始化
     */
    private void initProductList() {
            Product apple = new Product("1", "Apple", R.drawable.apple_pic,15,9,"苹果",99,"水果",10,"红色");
            productList.add(apple);
            Product banana = new Product("2", "Banana", R.drawable.banana_pic,25,19,"香蕉",34,"水果",10,"黄色");
            productList.add(banana);
            Product orange = new Product("4", "Orange", R.drawable.orange_pic,35,29,"橘子",659,"水果",10,"橙色");
            productList.add(orange);
            Product watermelon = new Product("5", "Watermelon", R.drawable.watermelon_pic,45,39,"西瓜",95,"水果",10,"绿色");
            productList.add(watermelon);
            Product pear = new Product("6", "Pear", R.drawable.pear_pic,55,49,"梨子",94,"水果",10,"黄色");
            productList.add(pear);
            Product grape = new Product("7", "Grape", R.drawable.grape_pic,65,59,"葡萄",96,"水果",10,"紫色");
            productList.add(grape);
            Product pineapple = new Product("8", "Pineapple", R.drawable.pineapple_pic,75,69,"菠萝",39,"水果",10,"黄色");
            productList.add(pineapple);
            Product strawberry = new Product("9", "Strawberry", R.drawable.strawberry_pic,85,79,"草莓",89,"水果",10,"红色");
            productList.add(strawberry);
            Product cherry = new Product("10", "Cherry", R.drawable.cherry_pic,95,89,"樱桃",19,"水果",10,"红色");
            productList.add(cherry);
            Product mango = new Product("11", "Mango", R.drawable.mango_pic,105,99,"芒果",84,"水果",10,"黄色");
            productList.add(mango);
    }

}
