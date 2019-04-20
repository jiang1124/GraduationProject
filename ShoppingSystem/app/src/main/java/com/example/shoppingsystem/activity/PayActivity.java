package com.example.shoppingsystem.activity;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.shoppingsystem.R;
import com.example.shoppingsystem.View.PayWayDialog;
import com.example.shoppingsystem.util.ToastUtil;

public class PayActivity extends BaseActivity implements View.OnClickListener {

    private String Web = "http://10.0.2.2:8080";

    private ImageView mIvWeichatSelect;
    private ImageView mIvAliSelect;

    private static final int PAY_TYPE_WECHAT = 0;  //微信支付,默认支付方式
    private static final int PAY_TYPE_ALIBABA = 1;  //支付宝支付
    private int payType = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);

        Button nowButton = (Button) findViewById(R.id.btn_now_pay);
        Button afterButton = (Button) findViewById(R.id.btn_after_pay);
        nowButton.setOnClickListener(this);
        afterButton.setOnClickListener(this);


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

