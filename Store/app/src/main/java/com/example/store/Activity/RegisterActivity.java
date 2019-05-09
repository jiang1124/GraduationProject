package com.example.store.Activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.store.Application.BaseApplication;
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

public class RegisterActivity extends BaseActivity {

    @InjectView(R.id.btn_get_code)
    Button getCodeButton;
    @InjectView(R.id.btn_sure)
    Button sureButton;
    @InjectView(R.id.et_store_name)
    EditText storeNameET;
    @InjectView(R.id.et_phone)
    EditText phoneET;
    @InjectView(R.id.et_verification_code)
    EditText verificationCodeET;
    @InjectView(R.id.et_password)
    EditText passwordET;
    @InjectView(R.id.et_confirm_password)
    EditText confirmPasswordET;

    private String Web = ResponseUtil.Web;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.inject(this);
    }

    @OnClick({R.id.btn_sure,R.id.btn_get_code})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_sure:
                String storeName = storeNameET.getText().toString().trim();
                String phone = phoneET.getText().toString().trim();
                String password = passwordET.getText().toString().trim();
                String confirmPassword = confirmPasswordET.getText().toString().trim();
                if(!password.equals(confirmPassword)){
                    ToastUtil.makeText(BaseApplication.getContext(),"两次输入密码不同");
                }else {
                    register(Web + "/storeRegister?account=" + storeName + "&password=" + password);
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
                            intent.putExtra("isOn",true);
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
