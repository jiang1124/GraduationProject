package com.example.shoppingsystem.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.shoppingsystem.Entity.User;
import com.example.shoppingsystem.R;
import com.example.shoppingsystem.adapter.ProductListAdapter;
import com.example.shoppingsystem.Application.BaseApplication;
import com.example.shoppingsystem.Entity.Product;
import com.example.shoppingsystem.util.HttpUtil;
import com.example.shoppingsystem.util.LogUtil;
import com.example.shoppingsystem.util.ResponseUtil;
import com.example.shoppingsystem.util.ToastUtil;
import com.lljjcoder.style.citylist.Toast.ToastUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import okhttp3.Response;

public class SearchActivity extends AppCompatActivity {
    @InjectView(R.id.rv_search_list)
    RecyclerView SearchRecyclerView;
    @InjectView(R.id.et_search_in_search)
    EditText searchEdit;
    @InjectView(R.id.btn_search_in_search)
    Button searchButton;
    @InjectView(R.id.tv_default_queue)
    TextView defaultQueueText;
    @InjectView(R.id.tv_price_queue)
    TextView priceQueueText;
    @InjectView(R.id.tv_sales_volume_queue)
    TextView salesVolumeQueueText;

    private List<Product> productList=new ArrayList<>();
    private String netAddress;
    private User user;
    private String searchContentStr;
    private String Web = "http://10.0.2.2:8080";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Intent intent = getIntent();
        String key = intent.getStringExtra("key");
        String sort = intent.getStringExtra("sort");
        user = (User) intent.getSerializableExtra("User");
        ButterKnife.inject(this);

        netAddress = Web+"/search";
        if(key!=null) {
            netAddress = netAddress + "/k?key=" + key;
            searchEdit.setText(key);
        }
        else if(sort!=null) {
            netAddress = netAddress + "/s?sort=" + sort;
            searchEdit.setText(sort);
        }
        searchContentStr=searchEdit.getText().toString();
        LogUtil.d("searchNetAddress:",netAddress);
        initSearchProductList(netAddress);
    }

    @OnClick({R.id.btn_search_in_search,R.id.tv_default_queue,R.id.tv_price_queue,R.id.tv_sales_volume_queue})
    public void onClick(View v){
        switch (v.getId()) {
            case R.id.btn_search_in_search:
                searchContentStr = searchEdit.getText().toString().trim();
                if(searchContentStr!=null) {
                    String addressStr = Web+"/search/k?key=" + searchContentStr;
                    initSearchProductList(addressStr);
                }else {
                    ToastUtil.makeText(this,"请输入搜索内容");
                }
                break;
            case R.id.tv_default_queue:
                if(searchContentStr!=null) {
                    String addressStr = Web+"/search/k?key=" + searchContentStr;
                    initSearchProductList(addressStr);
                    shiftQueueText(priceQueueText,salesVolumeQueueText,defaultQueueText);
                }else {
                    ToastUtil.makeText(this,"请输入搜索内容");
                }
                break;
            case R.id.tv_price_queue:
                if(searchContentStr!=null) {
                    String addressStr = Web+"/search/p?key=" + searchContentStr;
                    initSearchProductList(addressStr);
                    shiftQueueText(defaultQueueText,salesVolumeQueueText,priceQueueText);
                }else {
                    ToastUtil.makeText(this,"请输入搜索内容");
                }
                break;
            case R.id.tv_sales_volume_queue:
                if(searchContentStr!=null) {
                    String addressStr = Web+"/search/v?key=" + searchContentStr;
                    initSearchProductList(addressStr);
                    shiftQueueText(defaultQueueText,priceQueueText,salesVolumeQueueText);
                }else {
                    ToastUtil.makeText(this,"请输入搜索内容");
                }
                break;
            default:
                break;
        }
    }

    /*
     * 初始化
     */
    private void initSearchProductList(String websiteAddress) {
        LogUtil.d("搜索内容：",websiteAddress);
        HttpUtil.sendOkHttpRequest(websiteAddress, new okhttp3.Callback() {
            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {
                String responseText = response.body().string();
                LogUtil.d("searchStr:",responseText);
                if(productList!=null)
                    productList.clear();
                productList = ResponseUtil.handleProductList(responseText);
                if(productList!=null)
                    LogUtil.d("searchResult:",productList.toString());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (productList != null) {
                            GridLayoutManager layoutManager = new GridLayoutManager(BaseApplication.getContext(), 2);
                            SearchRecyclerView.setLayoutManager(layoutManager);
                            ProductListAdapter productListAdapter = new ProductListAdapter(productList);
                            if(user!=null)
                                productListAdapter.setUserId(user.getUser_id());
                            SearchRecyclerView.setAdapter(productListAdapter);
                            productListAdapter.notifyDataSetChanged();
                        } else {
                            ToastUtil.makeText(BaseApplication.getContext(), "没有该商品");
                        }
                    }
                });
            }
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtil.makeText(BaseApplication.getContext(), "获取数据失败");
                    }
                });
            }
        });
    }


    private final void shiftQueueText(TextView textView1,TextView textView2,TextView textView3){
        textView1.setTextSize(15);
        textView1.setTextColor(Color.parseColor("#4c4c4c"));
        textView2.setTextSize(15);
        textView2.setTextColor(Color.parseColor("#4c4c4c"));
        textView3.setTextSize(18);
        textView3.setTextColor(Color.parseColor("#1a1a1a"));
    }
}
