package com.example.shoppingsystem.layout;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;


import com.example.shoppingsystem.Application.BaseApplication;
import com.example.shoppingsystem.R;
import com.example.shoppingsystem.adapter.ProductListAdapter;
import com.example.shoppingsystem.emtity.Product;
import com.example.shoppingsystem.util.LogUtil;
import com.example.shoppingsystem.util.ResponseUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

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
        initProductList("http://10.0.2.2:8080/home");
    }

    /*
     * 初始化
     */
    private void initProductList(String websiteAddress) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(websiteAddress)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String responseData = response.body().string();
            LogUtil.d("jihao",responseData);
            productList = ResponseUtil.handleProductList(responseData);
            GridLayoutManager layoutManager = new GridLayoutManager(BaseApplication.getContext(), 2);
            recyclerView.setLayoutManager(layoutManager);
            ProductListAdapter productListAdapter = new ProductListAdapter(productList);
            recyclerView.setAdapter(productListAdapter);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
