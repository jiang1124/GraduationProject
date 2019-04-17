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

import com.example.shoppingsystem.R;
import com.example.shoppingsystem.adapter.ProductListAdapter;
import com.example.shoppingsystem.Application.BaseApplication;
import com.example.shoppingsystem.emtity.Product;
import com.example.shoppingsystem.util.HttpUtil;
import com.example.shoppingsystem.util.ResponseUtil;
import com.example.shoppingsystem.util.ToastUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import okhttp3.Response;

public class SearchActivity extends AppCompatActivity {
    @InjectView(R.id.rv_product_list)
    RecyclerView recyclerView;
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

    private List<Product> productList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Intent intent = getIntent();
        String key = intent.getStringExtra("key");
        String sort = intent.getStringExtra("sort");
        ButterKnife.inject(this);

        initSearchProductList("http://10.0.2.2:8080//home");
        GridLayoutManager layoutManager = new GridLayoutManager(SearchActivity.this, 2);
        recyclerView.setLayoutManager(layoutManager);
        ProductListAdapter productListAdapter = new ProductListAdapter(productList);
        recyclerView.setAdapter(productListAdapter);

        if(key!=null)
            searchEdit.setText(key);
        else
            searchEdit.setText(sort);
    }

    @OnClick({R.id.btn_search_in_search,R.id.tv_default_queue,R.id.tv_price_queue,R.id.tv_sales_volume_queue})
    public void onClick(View v){
        switch (v.getId()) {
            case R.id.btn_search_in_search:
                ToastUtil.makeText(BaseApplication.getContext(),"You click search");
                break;
            case R.id.tv_default_queue:
                shiftQueueText(priceQueueText,salesVolumeQueueText,defaultQueueText);
                break;
            case R.id.tv_price_queue:
                shiftQueueText(defaultQueueText,salesVolumeQueueText,priceQueueText);
                break;
            case R.id.tv_sales_volume_queue:
                shiftQueueText(defaultQueueText,priceQueueText,salesVolumeQueueText);
                break;
            default:
                break;
        }
    }

    /*
     * 初始化
     */
    private void initSearchProductList(String websiteAddress) {
        HttpUtil.sendOkHttpRequest(websiteAddress, new okhttp3.Callback() {
            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                final List<Product> productList = ResponseUtil.handleProductList(responseText);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (productList != null) {
                            GridLayoutManager layoutManager = new GridLayoutManager(BaseApplication.getContext(), 2);
                            recyclerView.setLayoutManager(layoutManager);
                            ProductListAdapter productListAdapter = new ProductListAdapter(productList);
                            recyclerView.setAdapter(productListAdapter);
                        } else {
                            ToastUtil.makeText(BaseApplication.getContext(), "获取数据失败");
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
