package com.example.shoppingsystem.Activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.shoppingsystem.Application.BaseApplication;
import com.example.shoppingsystem.R;
import com.example.shoppingsystem.util.*;

import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import okhttp3.Response;

public class RegisterActivity extends BaseActivity {

    @InjectView(R.id.btn_get_code)
    Button getCodeButton;
    @InjectView(R.id.btn_sure)
    Button sureButton;
    @InjectView(R.id.et_user_name)
    EditText userNameET;
    @InjectView(R.id.et_phone)
    EditText phoneET;
    @InjectView(R.id.et_verification_code)
    EditText verificationCodeET;
    @InjectView(R.id.et_password)
    EditText passwordET;
    @InjectView(R.id.et_confirm_password)
    EditText confirmPasswordET;@InjectView(R.id.tb_register)
    Toolbar toolbar;

    private String Web = ResponseUtil.Web;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.inject(this);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
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

    @OnClick({R.id.btn_sure,R.id.btn_get_code})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_sure:
                String userName = userNameET.getText().toString().trim();
                String phone = phoneET.getText().toString().trim();
                String password = passwordET.getText().toString().trim();
                String confirmPassword = confirmPasswordET.getText().toString().trim();
                if(!password.equals(confirmPassword)){
                    ToastUtil.makeText(BaseApplication.getContext(),"两次输入密码不同");
                }else {
                    register(Web + "/login/registered?account=" + userName + "&password=" + password);
                }
                break;
            case R.id.btn_get_code:
                verificationCodeET.setText("1654");
                break;
            default:
                break;
        }
    }

    public void register(String webAddress){
        HttpUtil.sendOkHttpRequest(webAddress,new okhttp3.Callback(){
            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {
                String responseText = response.body().string();
                LogUtil.d("RegisterActivity 回应结果：",responseText);
                final String result = responseText;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(result.equals("成功")){
                            ToastUtil.makeText(BaseApplication.getContext(), "注册成功");
                            Intent intent = new Intent(BaseApplication.getContext(),LoginActivity.class);
                            intent.putExtra("isOn",false);
                            startActivity(intent);
                        }else {
                            ToastUtil.makeText(BaseApplication.getContext(), "注册失败");
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
