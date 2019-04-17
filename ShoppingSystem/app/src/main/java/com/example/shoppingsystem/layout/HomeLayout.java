package com.example.shoppingsystem.layout;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.example.shoppingsystem.Application.BaseApplication;
import com.example.shoppingsystem.R;
import com.example.shoppingsystem.activity.MyAddressActivity;
import com.example.shoppingsystem.adapter.AddressAdapter;
import com.example.shoppingsystem.adapter.ProductListAdapter;
import com.example.shoppingsystem.emtity.Product;
import com.example.shoppingsystem.emtity.Recipient;
import com.example.shoppingsystem.util.HttpUtil;
import com.example.shoppingsystem.util.LogUtil;
import com.example.shoppingsystem.util.ResponseUtil;
import com.example.shoppingsystem.util.ToastUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
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
//        initProductList("http://10.0.2.2:8080//home");
//        if(productList !=null ){
//            GridLayoutManager layoutManager = new GridLayoutManager(BaseApplication.getContext(), 2);
//            recyclerView.setLayoutManager(layoutManager);
//            ProductListAdapter productListAdapter = new ProductListAdapter(productList);
//            recyclerView.setAdapter(productListAdapter);
//        }
//        else {
//            ToastUtil.makeText(BaseApplication.getContext(),"获取数据失败");
//        }

    }

    /*
     * 初始化
     */
//    private void initProductList(String websiteAddress) {
//        HttpUtil.sendOkHttpRequest(websiteAddress, new okhttp3.Callback() {
//            @Override
//            public void onResponse(okhttp3.Call call, Response response) throws IOException {
//                final String responseText = response.body().string();
//                final List<Product> productList = ResponseUtil.handleProductList(responseText);
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        if(productList !=null ){
//                            GridLayoutManager layoutManager = new GridLayoutManager(BaseApplication.getContext(), 2);
//                            recyclerView.setLayoutManager(layoutManager);
//                            ProductListAdapter productListAdapter = new ProductListAdapter(productList);
//                            recyclerView.setAdapter(productListAdapter);
//                        }
//                        else {
//                            ToastUtil.makeText(BaseApplication.getContext(),"获取数据失败");
//                        }
//                    }
//                });
//            }
//
//            @Override
//            public void onFailure(okhttp3.Call call, IOException e) {
//                e.printStackTrace();
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        ToastUtil.makeText(BaseApplication.getContext(),"获取数据失败");
//                    }
//                });
//            }
//        });
//    }
}
