package com.example.shoppingsystem.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.shoppingsystem.Application.BaseApplication;
import com.example.shoppingsystem.Entity.OrderMain;
import com.example.shoppingsystem.Entity.User;
import com.example.shoppingsystem.R;
import com.example.shoppingsystem.adapter.OrderListAdapter;
import com.example.shoppingsystem.adapter.ProductListAdapter;
import com.example.shoppingsystem.util.HttpUtil;
import com.example.shoppingsystem.util.LogUtil;
import com.example.shoppingsystem.util.ResponseUtil;
import com.example.shoppingsystem.util.ToastUtil;

import java.io.IOException;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import okhttp3.Response;

public class MyOrderActivity extends BaseActivity {
    @InjectView(R.id.tb_order)
    Toolbar tbOrder;
    @InjectView(R.id.tv_nopay_queue)
    TextView nopayQueueTV;
    @InjectView(R.id.tv_pay_queue)
    TextView payQueueTV;
    @InjectView(R.id.tv_all_queue)
    TextView allQueueTV;

    private String Web = "http://10.0.2.2:8080";
    private User user;
    private List<OrderMain> orderMainList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);
        ButterKnife.inject(this);
        setSupportActionBar(tbOrder);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("User");
        init("nopay");
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
    @OnClick({R.id.tv_nopay_queue,R.id.tv_pay_queue,R.id.tv_all_queue})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_nopay_queue:
                init("nopay");
                shiftQueueText(payQueueTV,allQueueTV,nopayQueueTV);
                break;
            case R.id.tv_pay_queue:
                init("pay");
                shiftQueueText(nopayQueueTV,allQueueTV,payQueueTV);
                break;
            case R.id.tv_all_queue:
                init("all");
                shiftQueueText(payQueueTV,nopayQueueTV,allQueueTV);
                break;
            default:
                break;
        }
    }

    private void shiftQueueText(TextView textView1, TextView textView2, TextView textView3){
        textView1.setTextSize(15);
        textView1.setTextColor(Color.parseColor("#4c4c4c"));
        textView2.setTextSize(15);
        textView2.setTextColor(Color.parseColor("#4c4c4c"));
        textView3.setTextSize(18);
        textView3.setTextColor(Color.parseColor("#1a1a1a"));
    }

    private void init(String type){
        String websiteAddress=Web+"/findAllOrder?user_id="+user.getUser_id()+"&type="+type;
        LogUtil.d("搜索内容：",websiteAddress);
        HttpUtil.sendOkHttpRequest(websiteAddress, new okhttp3.Callback() {
            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {
                String responseText = response.body().string();
                LogUtil.d("searchStr:",responseText);
                if(orderMainList!=null)
                    orderMainList.clear();
                orderMainList = ResponseUtil.handleOrderMainList(responseText);
                if(orderMainList!=null)
                    LogUtil.d("searchResult:",orderMainList.toString());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (orderMainList != null) {
                            OrderListAdapter orderListAdapter = new OrderListAdapter(MyOrderActivity.this,R.layout.item_order_unit,orderMainList);
                            final ListView orderListView = (ListView) findViewById(R.id.lv_order_list);
                            orderListView.setAdapter(orderListAdapter);
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
}
