package com.example.store.Activity;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.store.Application.BaseApplication;
import com.example.store.Entity.OrderExpand;
import com.example.store.Entity.OrderMain;
import com.example.store.Entity.Store;
import com.example.store.R;
import com.example.store.Utils.HttpUtil;
import com.example.store.Utils.LogUtil;
import com.example.store.Utils.ResponseUtil;
import com.example.store.Utils.ToastUtil;
import com.example.store.adapter.OrderDetailAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import okhttp3.Response;

public class OrderDetailActivity extends BaseActivity {
    @InjectView(R.id.tv_order_number)
    TextView orderNo;
    @InjectView(R.id.tv_state_order)
    TextView orderState;
    @InjectView(R.id.tv_name_order)
    TextView userName;
    @InjectView(R.id.tv_phone_order)
    TextView phone;
    @InjectView(R.id.tv_time_order)
    TextView date;
    @InjectView(R.id.tv_address_order)
    TextView address;
    @InjectView(R.id.tv_price_order)
    TextView price;
    @InjectView(R.id.btn_sure)
    Button sureButton;
    private Store store;
    private String Web = ResponseUtil.Web;
    private OrderMain orderMain;
    private List<OrderExpand> orderExpandList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        Intent getIntent = getIntent();
        store = (Store) getIntent.getSerializableExtra("Store");
        orderMain = (OrderMain) getIntent.getSerializableExtra("OrderMain");
        ButterKnife.inject(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        init();
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

    @OnClick(R.id.btn_sure)
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_sure:
                if(orderMain.getExtra()==null) {
                    String orderId = "order_id=" + orderMain.getOrder_id();
                    String extra = "extra=" + store.getAddress();
                    String webAddress = Web + "/updateOrderState?" + orderId + "&" + extra;
                    LogUtil.d("传输：",webAddress);
                    submit(webAddress);
                }else {
                    finish();
                }
                break;
            default:
                break;
        }
    }

    private void init() {
        orderNo.setText(orderMain.getOrder_id());
        if(orderMain.getPay_info().equals("已处理"))
            orderState.setText(orderMain.getExtra());
        else
            orderState.setText(orderMain.getPay_info());
        userName.setText(orderMain.getRecipient_name());
        phone.setText(orderMain.getPhone());
        date.setText(orderMain.getDate());
        address.setText(orderMain.getAddress());
        price.setText(orderMain.getAll_money() + "");
        String orderId = "order_id="+orderMain.getOrder_id();
        String webAddress = Web+"/findOrderProduct?"+orderId;
        getOrderExpand(webAddress);
    }

    private void getOrderExpand(String webAddress) {
        HttpUtil.sendOkHttpRequest(webAddress, new okhttp3.Callback() {
            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {
                String responseText = response.body().string();
                LogUtil.d("ProductListActivity 回应结果：", responseText);
                orderExpandList = ResponseUtil.handleOrderExpandList(responseText);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (orderExpandList!=null) {
                            OrderDetailAdapter orderDetailAdapter = new OrderDetailAdapter(OrderDetailActivity.this,R.layout.item_product_order_child,orderExpandList);
                            ListView listView = (ListView) findViewById(R.id.lv_order_expand);
                            listView.setAdapter(orderDetailAdapter);
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

    private void submit(String webAddress) {
        HttpUtil.sendOkHttpRequest(webAddress, new okhttp3.Callback() {
            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {
                String responseText = response.body().string();
                LogUtil.d("回应结果：", responseText);
                final String result = responseText;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (result.equals("成功")) {
                            ToastUtil.makeText(BaseApplication.getContext(), "提交成功");
                            Intent intent = new Intent(BaseApplication.getContext(),OrderListActivity.class);
                            intent.putExtra("Store",store);
                            startActivity(intent);
                            finish();
                        }else {
                            ToastUtil.makeText(BaseApplication.getContext(), "提交出错");
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
