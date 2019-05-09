package com.example.store.Activity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.store.Application.BaseApplication;
import com.example.store.Entity.OrderMain;
import com.example.store.Entity.Product;
import com.example.store.Entity.Store;
import com.example.store.R;
import com.example.store.Utils.HttpUtil;
import com.example.store.Utils.LogUtil;
import com.example.store.Utils.ResponseUtil;
import com.example.store.Utils.ToastUtil;
import com.example.store.adapter.OrderListAdapter;
import com.example.store.adapter.ProductListAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;

public class OrderListActivity extends BaseActivity {
    private List<OrderMain> orderMainList = new ArrayList<>();
    private Store store;
    private String Web = ResponseUtil.Web;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);
        Intent getIntent = getIntent();
        store = (Store) getIntent.getSerializableExtra("Store");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
    @Override
    protected void onStart(){
        super.onStart();
        initOrder(Web + "/storeOrderList?store_id=" + store.getStore_id());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initOrder(String webAddress) {
        HttpUtil.sendOkHttpRequest(webAddress, new okhttp3.Callback() {
            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {
                String responseText = response.body().string();
                LogUtil.d("ProductListActivity 回应结果：", responseText);
                orderMainList = ResponseUtil.handleOrderMainList(responseText);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (orderMainList != null) {
                            OrderListAdapter orderListAdapter = new OrderListAdapter(OrderListActivity.this, R.layout.item_order_child, orderMainList);
                            ListView listView = (ListView) findViewById(R.id.order_list);
                            listView.setAdapter(orderListAdapter);
                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    OrderMain orderMain = orderMainList.get(position);
                                    Intent intent = new Intent(OrderListActivity.this, OrderDetailActivity.class);
                                    intent.putExtra("OrderMain", orderMain);
                                    intent.putExtra("Store", store);
                                    startActivity(intent);
                                }
                            });
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
                        ToastUtil.makeText(BaseApplication.getContext(), "网络出错");
                    }
                });
            }
        });
    }
}
