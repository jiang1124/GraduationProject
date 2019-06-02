package com.example.shoppingsystem.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.shoppingsystem.Application.BaseApplication;
import com.example.shoppingsystem.Entity.User;
import com.example.shoppingsystem.R;
import com.example.shoppingsystem.util.HttpUtil;
import com.example.shoppingsystem.util.LogUtil;
import com.example.shoppingsystem.util.ResponseUtil;
import com.example.shoppingsystem.util.ToastUtil;

import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import okhttp3.Response;

public class LoginActivity extends BaseActivity {
    @InjectView(R.id.btn_login)
    Button loginButton;
    @InjectView(R.id.et_user_name)
    EditText accountEdit;
    @InjectView(R.id.et_password)
    EditText passwordEdit;
    @InjectView(R.id.cb_rm_pass)
    CheckBox rememberCheckBox;
    @InjectView(R.id.cb_auto_login)
    CheckBox autoLoginCheckButton;

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private String account = "";
    private String password = "";
    private User user;
    private String Web = ResponseUtil.Web;
    private boolean isOn = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isRemember = pref.getBoolean("rememberPassword",false);
        boolean isAutoLogin = pref.getBoolean("autoLogin",false);
        Intent getIntent = getIntent();
        isOn = getIntent.getBooleanExtra("isOn",true);
        user = (User) getIntent.getSerializableExtra("User");
        if(user!=null){
            accountEdit.setText(user.getUser_name());
            passwordEdit.setText(user.getUser_password());
        }else if(isRemember){
            account = pref.getString("account","");
            password = pref.getString("password","");
            accountEdit.setText(account);
            passwordEdit.setText(password);
            rememberCheckBox.setChecked(true);
            if(isAutoLogin&&!account.equals("")&&!password.equals("")) {
                autoLoginCheckButton.setChecked(true);
            }
        }
        if(isAutoLogin&&isOn) {
            initUser(Web + "/login/verification?account=" + accountEdit.getText().toString().trim() + "&password=" + passwordEdit.getText().toString().trim());
        }else if(!isAutoLogin&&isOn){
            Intent intentMain = new Intent(BaseApplication.getContext(), MainActivity.class);
            startActivity(intentMain);
            finish();
        }
    }

    @OnClick({R.id.btn_login,R.id.tv_re})
    public void onClick(View view){
        switch (view.getId()) {
            case R.id.btn_login:
                account = accountEdit.getText().toString().trim();
                password = passwordEdit.getText().toString().trim();
                initUser(Web + "/login/verification?account=" + account+"&password="+password);
                break;
            case R.id.tv_re:
                Intent intentRegister = new Intent(BaseApplication.getContext(),RegisterActivity.class);
                startActivity(intentRegister);
                break;
            default:
                break;
        }
    }

    private void Login() {
        editor = pref.edit();
        if (rememberCheckBox.isChecked()) {
            if (autoLoginCheckButton.isChecked()) {
                editor.putBoolean("autoLogin", true);
            }
            editor.putBoolean("rememberPassword", true);
            editor.putString("account", account);
            editor.putString("password", password);
        } else {
            editor.clear();
        }
        editor.apply();
        Intent intentMain = new Intent(BaseApplication.getContext(), MainActivity.class);
        intentMain.putExtra("User",user);
        isOn = true;
        intentMain.putExtra("isOn",isOn);
        startActivity(intentMain);
        finish();
    }

    public void initUser(String webAddress){
        LogUtil.d("url：",webAddress);
        HttpUtil.sendOkHttpRequest(webAddress,new okhttp3.Callback(){
            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {
                String responseText = response.body().string();
                LogUtil.d("LoginActivity 回应结果：",responseText);
                user = ResponseUtil.handleUser(responseText);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(user != null){
                            Login();
                        }else {
                            ToastUtil.makeText(BaseApplication.getContext(),"账号或密码出错");
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
