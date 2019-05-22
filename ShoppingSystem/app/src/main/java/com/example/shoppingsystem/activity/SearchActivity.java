package com.example.shoppingsystem.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.shoppingsystem.Entity.User;
import com.example.shoppingsystem.R;
import com.example.shoppingsystem.adapter.ProductListAdapter;
import com.example.shoppingsystem.Application.BaseApplication;
import com.example.shoppingsystem.Entity.Product;
import com.example.shoppingsystem.adapter.ProductListsAdapter;
import com.example.shoppingsystem.util.HttpUtil;
import com.example.shoppingsystem.util.ILoadMoreData;
import com.example.shoppingsystem.util.LogUtil;
import com.example.shoppingsystem.util.ResponseUtil;
import com.example.shoppingsystem.util.ToastUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import okhttp3.Response;

public class SearchActivity extends AppCompatActivity implements ILoadMoreData{
    @InjectView(R.id.tb_search)
    Toolbar searchTB;
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
    private User user;
    private String searchContentStr;
    private String Web =ResponseUtil.Web;
    private String addressStr;

    private GridLayoutManager layoutManager;
    private ProductListsAdapter productListAdapter;
    private int page =0;

    private SwipeRefreshLayout swipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Intent intent = getIntent();
        String key = intent.getStringExtra("key");
        String sort = intent.getStringExtra("sort");
        user = (User) intent.getSerializableExtra("User");
        ButterKnife.inject(this);
        setSupportActionBar(searchTB);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        addressStr = Web+"/search";
        if(key!=null) {
            addressStr = addressStr + "/k?key=" + key;
            searchEdit.setText(key);
        }
        else if(sort!=null) {
            addressStr = addressStr + "/s?sort=" + sort;
            searchEdit.setText(sort);
        }
        searchContentStr=searchEdit.getText().toString();
        LogUtil.d("searchNetAddress:",addressStr);
        initSearchProductList(addressStr+"&page="+page);
        initRefresh();
        initRecyclerView();
    }

    @Override
    protected void onStart(){
        super.onStart();
    }

    @OnClick({R.id.btn_search_in_search,R.id.tv_default_queue,R.id.tv_price_queue,R.id.tv_sales_volume_queue})
    public void onClick(View v){
        switch (v.getId()) {
            case R.id.btn_search_in_search:
                page=0;
                searchContentStr = searchEdit.getText().toString().trim();
                if(searchContentStr.length()>0) {
                    addressStr = Web+"/search/k?key=" + searchContentStr;
                    getList(addressStr);
                }else {
                    ToastUtil.makeText(this,"请输入搜索内容");
                }
                break;
            case R.id.tv_default_queue:
                if(searchContentStr.length()>0) {
                    addressStr = Web+"/search/k?key=" + searchContentStr;
                    getList(addressStr);
                    shiftQueueText(priceQueueText,salesVolumeQueueText,defaultQueueText);
                }else {
                    ToastUtil.makeText(this,"请输入搜索内容");
                }
                break;
            case R.id.tv_price_queue:
                if(searchContentStr.length()>0) {
                    addressStr = Web+"/search/p?key=" + searchContentStr;
                    getList(addressStr);
                    shiftQueueText(defaultQueueText,salesVolumeQueueText,priceQueueText);
                }else {
                    ToastUtil.makeText(this,"请输入搜索内容");
                }
                break;
            case R.id.tv_sales_volume_queue:
                if(searchContentStr.length()>0) {
                    addressStr = Web+"/search/v?key=" + searchContentStr;
                    getList(addressStr);
                    shiftQueueText(defaultQueueText,priceQueueText,salesVolumeQueueText);
                }else {
                    ToastUtil.makeText(this,"请输入搜索内容");
                }
                break;
            default:
                break;
        }
    }

    private void getList(String addressStr){
        page = 0;
        productList.clear();
        initSearchProductList(addressStr+"&page="+page);
    }

    /*
     * 初始化
     */
    private void initRecyclerView() {
        layoutManager = new GridLayoutManager(BaseApplication.getContext(), 2);
        SearchRecyclerView.setLayoutManager(layoutManager);
        productListAdapter = new ProductListsAdapter(this, this, SearchRecyclerView, productList);
        if(user!=null)
            productListAdapter.setUserId(user.getUser_id());
        SearchRecyclerView.setAdapter(productListAdapter);
    }

    private void initRefresh(){
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setColorSchemeResources(
                R.color.colorPrimary,
                R.color.green,
                R.color.red
        );
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){
            @Override
            public void onRefresh(){
                refreshOrderList(addressStr);
            }
        });
    }

    private void refreshOrderList(final String webAddress){
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        page=0;
                        productList.clear();
                        initSearchProductList(webAddress + "&page=" + page);
                        productListAdapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        }.start();
    }

    private void initSearchProductList(String websiteAddress) {
        LogUtil.d("搜索内容：",websiteAddress);
        HttpUtil.sendOkHttpRequest(websiteAddress, new okhttp3.Callback() {
            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {
                String responseText = response.body().string();
                LogUtil.d("searchStr:",responseText);
                final List<Product> products = ResponseUtil.handleProductList(responseText);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        page+=1;
                        if (products != null) {
                            productList.addAll(products);
                            productListAdapter.setIsAll(false);
                            productListAdapter.notifyDataSetChanged();
                        } else {
                            productListAdapter.setIsAll(true);
                            if(productList==null)
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


    private void shiftQueueText(TextView textView1,TextView textView2,TextView textView3){
        textView1.setTextSize(15);
        textView1.setTextColor(Color.parseColor("#4c4c4c"));
        textView2.setTextSize(15);
        textView2.setTextColor(Color.parseColor("#4c4c4c"));
        textView3.setTextSize(18);
        textView3.setTextColor(Color.parseColor("#1a1a1a"));
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
    @Override
    public void loadMoreData() {
        initSearchProductList(addressStr+"&page="+page);
        productListAdapter.notifyDataSetChanged();
    }
}
