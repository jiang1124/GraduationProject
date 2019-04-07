package com.example.shoppingsystem;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class ProductActivity extends BaseActiity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        //隐藏活动标题
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null)
            actionBar.hide();
        Intent intent = getIntent();
        Product product = (Product) intent.getSerializableExtra("Product");
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.product_layout);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        ProductAdapter adapter = new ProductAdapter(product);
        recyclerView.setAdapter(adapter);
    }
}
