package com.example.store.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.store.Application.BaseApplication;
import com.example.store.Entity.Store;
import com.example.store.R;
import com.example.store.Utils.HttpUtil;
import com.example.store.Utils.LogUtil;
import com.example.store.Utils.ResponseUtil;
import com.example.store.Utils.ToastUtil;

import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import okhttp3.Response;

public class MainActivity extends BaseActivity {

    @InjectView(R.id.tv_edit_mine)
    TextView storeTV;
    @InjectView(R.id.tv_product_setting)
    TextView productTV;
    @InjectView(R.id.tv_order_setting)
    TextView orderTV;
    @InjectView(R.id.iv_head)
    ImageView headIV;
    @InjectView(R.id.btn_quit)
    Button quitButton;

    private Store store;
    private String Web = ResponseUtil.Web;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        Intent getIntent = getIntent();
        store = (Store) getIntent.getSerializableExtra("Store");
    }

    @OnClick({R.id.tv_edit_mine, R.id.tv_product_setting, R.id.tv_order_setting, R.id.btn_quit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_edit_mine:
                gotoActivity("store");
                break;
            case R.id.tv_product_setting:
                gotoActivity("product");
                break;
            case R.id.tv_order_setting:
                gotoActivity("order");
                break;
            case R.id.btn_quit:
                Intent intent = new Intent(BaseApplication.getContext(), LoginActivity.class);
                intent.putExtra("isOn", true);
                startActivity(intent);
                finish();
                break;
            default:
                break;
        }
    }

    private void gotoActivity(String type) {
        Intent intent;
        if (type.equals("store")) {
            intent = new Intent(BaseApplication.getContext(), StoreDetailActivity.class);
            intent.putExtra("Store", store);
            startActivity(intent);
        } else if (type.equals("product")) {
            intent = new Intent(BaseApplication.getContext(), ProductListActivity.class);
            intent.putExtra("Store", store);
            startActivity(intent);
        } else if (type.equals("order")) {
            intent = new Intent(BaseApplication.getContext(), OrderListActivity.class);
            intent.putExtra("Store", store);
            startActivity(intent);
        }
    }

}
