package com.example.store.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.store.Application.BaseApplication;
import com.example.store.R;
import com.example.store.Utils.ToastUtil;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

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
                }

                break;
            case R.id.btn_get_code:
                verificationCodeET.setText("1654");
                break;
            default:
                break;
        }
    }
}
