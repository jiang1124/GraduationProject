package com.example.shoppingsystem.Activity;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;

import com.example.shoppingsystem.Application.BaseApplication;
import com.example.shoppingsystem.Entity.Product;
import com.example.shoppingsystem.Entity.User;
import com.example.shoppingsystem.R;
import com.example.shoppingsystem.adapter.LineProductAdapter;
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

public class MyCollectionActivity extends BaseActivity {
    @InjectView(R.id.tb_collection)
    Toolbar tbCollection;

    private String Web = "http://10.0.2.2:8080";
    private List<Product> productList = new ArrayList<>();
    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_collection);
        ButterKnife.inject(this);
        setSupportActionBar(tbCollection);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        Intent getIntent = getIntent();
        user = (User) getIntent.getSerializableExtra("User");
        initProduct(Web+"/collection?user_id="+user.getUser_id());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public  void initProduct(String websiteAddress){
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
                            LineProductAdapter lineProductAdapter = new LineProductAdapter(BaseApplication.getContext(),R.layout.item_product_order_child,productList);
                            final ListView orderListView = (ListView) findViewById(R.id.lv_collection_product_list);
                            orderListView.setAdapter(lineProductAdapter);
                        } else {
                            ToastUtil.makeText(BaseApplication.getContext(), "没有商品");
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
}
