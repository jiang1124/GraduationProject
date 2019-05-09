package com.example.store.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.store.Application.BaseApplication;
import com.example.store.Entity.Store;
import com.example.store.R;
import com.example.store.Utils.HttpUtil;
import com.example.store.Utils.LogUtil;
import com.example.store.Utils.ResponseUtil;
import com.example.store.Utils.RoundCornerDialog;
import com.example.store.Utils.ToastUtil;

import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import okhttp3.Response;

public class StoreDetailActivity extends BaseActivity {

    @InjectView(R.id.tv_store_name_show)
    TextView storeNameShow;
    @InjectView(R.id.tv_password_show)
    TextView passwordShow;
    @InjectView(R.id.tv_detail_show)
    TextView detailShow;
    @InjectView(R.id.tv_address_show)
    TextView addressShow;
    @InjectView(R.id.et_store_name)
    EditText storeNameET;
    @InjectView(R.id.et_password)
    EditText passwordET;
    @InjectView(R.id.et_detail)
    EditText detailET;
    @InjectView(R.id.et_address)
    EditText addressET;
    @InjectView(R.id.btn_edit)
    Button editButton;
    @InjectView(R.id.btn_sure)
    Button sureButton;

    private Store store;
    private String Web = ResponseUtil.Web;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_detial);
        Intent getIntent = getIntent();
        store = (Store) getIntent.getSerializableExtra("Store");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        ButterKnife.inject(this);
        init(Web + "/findStoreName?store_id=" + store.getStore_id());
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


    private void init(String webAddress) {
        HttpUtil.sendOkHttpRequest(webAddress, new okhttp3.Callback() {
            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {
                String responseText = response.body().string();
                LogUtil.d("ProductListActivity 回应结果：", responseText);
                store = ResponseUtil.handleStore(responseText);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        storeNameShow.setText(store.getStore_name());
                        passwordShow.setText("******");
                        storeNameET.setText(store.getStore_name());
                        passwordET.setText(store.getStore_password());
                        if(store.getStore_detail()!=null) {
                            detailShow.setText(store.getStore_detail());
                            addressShow.setText(store.getAddress());
                            detailET.setText(store.getStore_detail());
                            addressET.setText(store.getAddress());
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

    @OnClick({R.id.btn_edit, R.id.btn_sure})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_edit:
                storeNameShow.setVisibility(View.GONE);
                passwordShow.setVisibility(View.GONE);
                detailShow.setVisibility(View.GONE);
                addressShow.setVisibility(View.GONE);
                storeNameET.setVisibility(View.VISIBLE);
                passwordET.setVisibility(View.VISIBLE);
                detailET.setVisibility(View.VISIBLE);
                addressET.setVisibility(View.VISIBLE);
                break;
            case R.id.btn_sure:
                String storeId = "store_id="+store.getStore_id();
                String storeName = "store_name=" + storeNameET.getText().toString().trim();
                String password = "store_password=" + passwordET.getText().toString().trim();
                String detail = "store_detail=" + detailET.getText().toString().trim();
                String address = "store_address=" + addressET.getText().toString().trim();
                String webAddress = Web + "/updateStore?" + storeId + "&"+ storeName + "&" + password + "&" + detail + "&" + address;
                submit(webAddress);
                break;
            default:
                break;
        }
    }

    private void submit(String webAddress) {
        HttpUtil.sendOkHttpRequest(webAddress, new okhttp3.Callback() {
            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {
                String responseText = response.body().string();
                LogUtil.d("ProductListActivity 回应结果：", responseText);
                store = ResponseUtil.handleStore(responseText);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (store!=null) {
                            ToastUtil.makeText(BaseApplication.getContext(), "提交成功");
                            finish();
                        } else {
                            ToastUtil.makeText(BaseApplication.getContext(), "提交失败");
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
