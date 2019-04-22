package com.example.shoppingsystem.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.shoppingsystem.Entity.Shop;
import com.example.shoppingsystem.R;
import com.example.shoppingsystem.View.PayWayDialog;
import com.example.shoppingsystem.adapter.OrderAdapter;
import com.example.shoppingsystem.adapter.OrderDetailAdapter;
import com.example.shoppingsystem.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class PayActivity extends BaseActivity implements View.OnClickListener {

    @InjectView(R.id.tb_pay)
    Toolbar tbPay;
    @InjectView(R.id.tv_total_price_pay)
    TextView totalTV;

    private String Web = "http://10.0.2.2:8080";
    private ImageView mIvWeichatSelect;
    private ImageView mIvAliSelect;
    private TextView totalPriceTV;
    private static final int PAY_TYPE_WECHAT = 0;  //微信支付,默认支付方式
    private static final int PAY_TYPE_ALIBABA = 1;  //支付宝支付
    private int payType = 0;
    private List<Shop> shops;
    private double totalPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);

        ButterKnife.inject(this);
        setSupportActionBar(tbPay);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        Button nowButton = (Button) findViewById(R.id.btn_now_pay);
        Button afterButton = (Button) findViewById(R.id.btn_after_pay);
        nowButton.setOnClickListener(this);
        afterButton.setOnClickListener(this);
        Intent intent = getIntent();
        shops = (List<Shop>) intent.getSerializableExtra("ShopList");
        totalPrice = intent.getDoubleExtra("totalPrice",0.0);
        OrderAdapter orderAdapter = new OrderAdapter(PayActivity.this,R.layout.item_order_child,shops);
        final ListView orderListView = (ListView) findViewById(R.id.lv_order_list);
        orderListView.setAdapter(orderAdapter);
        final LinearLayout linearLayout = (LinearLayout) findViewById(R.id.ll_product_list);
        orderListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Shop shop = shops.get(position);
                OrderDetailAdapter orderDetailAdapter = new OrderDetailAdapter(PayActivity.this,R.layout.item_product_order_child,shop.getGoods());
                ListView orderDetailListView = (ListView) findViewById(R.id.lv_product_list);
                orderDetailListView.setAdapter(orderDetailAdapter);
                orderListView.setVisibility(View.GONE);
                linearLayout.setVisibility(View.VISIBLE);
                TextView backTV = (TextView) findViewById(R.id.return_back);
                backTV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        orderListView.setVisibility(View.VISIBLE);
                        linearLayout.setVisibility(View.GONE);
                    }
                });
            }
        });
        totalTV.setText(totalPrice+"");
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
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_now_pay:
                pay();
                break;
            case R.id.btn_after_pay:

                break;
            default:
                break;
        }
    }

    private void pay() {
        View dialogView = getLayoutInflater().inflate(R.layout.pay_dialog_layout, null);
        //微信支付的选择
        mIvWeichatSelect = dialogView.findViewById(R.id.iv_buy_weichat_select);
        //支付宝的选择
        mIvAliSelect = dialogView.findViewById(R.id.iv_buy_alipay_select);
        //总价
        totalPriceTV = dialogView.findViewById(R.id.tv_price_dialog);
        totalPriceTV.setText(totalPrice+"");

        PayWayDialog dialog = new PayWayDialog(PayActivity.this, dialogView, new int[]{R.id.ll_pay_weichat, R.id.ll_pay_ali, R.id.tv_confirm, R.id.tv_cancel});
        dialog.bottmShow();
        dialog.setOnBottomItemClickListener(new PayWayDialog.OnBottomItemClickListener() {
            @Override
            public void onBottomItemClick(PayWayDialog dialog, View view) {
                switch (view.getId()) {
                    case R.id.ll_pay_weichat:   //微信支付
                        showToast("微信支付");
                        if (PAY_TYPE_WECHAT != payType) {
                            mIvWeichatSelect.setImageDrawable(ContextCompat.getDrawable(PayActivity.this,R.drawable.select));
                            mIvAliSelect.setImageDrawable(ContextCompat.getDrawable(PayActivity.this,R.drawable.unselect));
                            payType = PAY_TYPE_WECHAT;
                        }

                        break;
                    case R.id.ll_pay_ali:  //支付宝支付
                        showToast("支付宝支付");
                        if (PAY_TYPE_ALIBABA != payType) {
                            mIvWeichatSelect.setImageDrawable(ContextCompat.getDrawable(PayActivity.this,R.drawable.unselect));
                            mIvAliSelect.setImageDrawable(ContextCompat.getDrawable(PayActivity.this,R.drawable.select));
                            payType = PAY_TYPE_ALIBABA;
                        }
                        break;
                    case R.id.tv_confirm:  //确认支付
                        //TODO 支付
                        showToast("确认支付");
                        //重置
                        payType = PAY_TYPE_WECHAT;
                        dialog.cancel();
                        break;
                    case R.id.tv_cancel:  //取消支付
                        showToast("取消支付");
                        //重置
                        payType = PAY_TYPE_WECHAT;
                        dialog.cancel();
                        break;
                }
            }
        });
    }

    private void showToast(String s) {
        ToastUtil.makeText(this, s);
    }
}

