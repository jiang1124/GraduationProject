package com.example.shoppingsystem.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.shoppingsystem.Application.BaseApplication;
import com.example.shoppingsystem.R;
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
import butterknife.OnClick;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends BaseActivity {
    @InjectView(R.id.home_button)
    Button homeButton;
    @InjectView(R.id.search_button)
    Button searchButton;
    @InjectView(R.id.sort_button)
    Button sortButton;
    @InjectView(R.id.shopping_cart_button)
    Button shoppingCartButton;
    @InjectView(R.id.person_button)
    Button personButton;
    @InjectView(R.id.home_layout_main)
    LinearLayout homeView;
    @InjectView(R.id.sort_layout_main)
    LinearLayout sortView;
    @InjectView(R.id.shopping_cart_layout_main)
    LinearLayout shoppingCartView;
    @InjectView(R.id.person_layout_main)
    LinearLayout personView;
    @InjectView(R.id.rv_home_list)
    RecyclerView homeRecyclerView;

    private List<Product> productList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.inject(this);
        initHome("http://10.0.2.2:8080/home");
    }

    /*
     * 下菜单栏点击事件及搜索点击事件
     */
    @OnClick({R.id.home_button,R.id.search_button,R.id.sort_button,R.id.shopping_cart_button,R.id.person_button})
    public void onClick(View v){
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
    private void initHome(String websiteAddress) {
        HttpUtil.sendOkHttpRequest(websiteAddress, new okhttp3.Callback() {
            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                LogUtil.d("Main标记0",responseText);
                final List<Product> productList = ResponseUtil.handleProductList(responseText);
                LogUtil.d("Main标记1",productList.get(0).getProductName());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (productList != null) {
                            GridLayoutManager layoutManager = new GridLayoutManager(MainActivity.this, 2);
                            homeRecyclerView.setLayoutManager(layoutManager);
                            ProductListAdapter productListAdapter = new ProductListAdapter(productList);
                            homeRecyclerView.setAdapter(productListAdapter);
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

}
