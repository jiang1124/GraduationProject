package com.example.shoppingsystem.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.shoppingsystem.R;
import com.example.shoppingsystem.emtity.User;
import com.example.shoppingsystem.util.HttpUtil;
import com.example.shoppingsystem.util.LogUtil;
import com.example.shoppingsystem.util.ResponseUtil;
import com.example.shoppingsystem.util.ToastUtil;

import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

public class LoginActivity extends BaseActivity implements View.OnClickListener{

    @InjectView(R.id.et_login_account)
    EditText accountInputEdit;
    @InjectView(R.id.et_login_password)
    EditText passwordInputEdit;
    @InjectView(R.id.cb_remember_pass)
    CheckBox rememberPass;
    @InjectView(R.id.btn_login)
    Button loginButton;
    @InjectView(R.id.btn_login_to_registered)
    Button loginToRegisteredButton;
    @InjectView(R.id.et_registered_account)
    EditText registeredAccountInputEdit;
    @InjectView(R.id.et_registered_password)
    EditText registeredPasswordInputEdit;
    @InjectView(R.id.cb_registered_remember_pass)
    CheckBox registeredRememberPass;
    @InjectView(R.id.btn_registered)
    Button registeredButton;
    @InjectView(R.id.btn_registered_to_login)
    Button RegisteredToLoginButton;
    @InjectView(R.id.tb_login)
    Toolbar tbLogin;
    @InjectView(R.id.ll_login)
    LinearLayout loginLinearLayout;
    @InjectView(R.id.ll_registered)
    LinearLayout registeredLinearLayout;

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private User user;
    private boolean isLogin = false;
    private boolean isOff = true;
    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);
        setSupportActionBar(tbLogin);

        Intent intentGet =getIntent();
        isOff = intentGet.getBooleanExtra("isOff",true);
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isRemember = pref.getBoolean("remember_password",false);
        if(isRemember&&isOff) {
            //将帐号和密码都设置到文本框中
            String account = pref.getString("account", "");
            String password = pref.getString("password", "");
            accountInputEdit.setText(account);
            passwordInputEdit.setText(password);
            rememberPass.setChecked(true);
            String webAddress = "http://10.0.2.2:8080/login/verification?name=" + account + "&password=" + password;
            getLoginAnswer(webAddress);
        }else if(isOff){
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.putExtra("isLogin",isLogin);
            intent.putExtra("User",user);
            startActivity(intent);
            finish();
        }
    }

    @OnClick({R.id.btn_login,R.id.btn_login_to_registered,R.id.btn_registered_to_login,R.id.btn_registered})
    public void onClick(View v){
        switch (v.getId()) {
            case R.id.btn_login:
                String account = accountInputEdit.getText().toString();
                String password = passwordInputEdit.getText().toString();
                String webAddress ="http://10.0.2.2:8080/login/verification?name="+account+"&password="+password;
                type="login";
                getLoginAnswer(webAddress);
                break;
            case R.id.btn_registered:
                String registeredAccount = accountInputEdit.getText().toString();
                String registeredPassword = passwordInputEdit.getText().toString();
                String netAddress = "http://10.0.2.2:8080/login/registered?name="+registeredAccount+"&password="+registeredPassword;
                type="registered";
                getLoginAnswer(netAddress);
                break;
            case R.id.btn_login_to_registered:
                loginLinearLayout.setVisibility(View.GONE);
                registeredLinearLayout.setVisibility(View.VISIBLE);
                break;
            case R.id.btn_registered_to_login:
                loginLinearLayout.setVisibility(View.VISIBLE);
                registeredLinearLayout.setVisibility(View.GONE);
                break;
            default:
                break;
        }
    }

    public void getLoginAnswer(String webAddress){
        HttpUtil.sendOkHttpRequest(webAddress,new okhttp3.Callback(){
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                LogUtil.d("loginHttp:",responseText);
                user = ResponseUtil.handleUser(responseText);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(user!=null){
                            isLogin = true;
                            editor = pref.edit();
                            if (rememberPass.isChecked()) {
                                editor.putBoolean("remember_password", true);
                                editor.putString("account", user.getUser_name());
                                editor.putString("password", user.getUser_password());
                            } else {
                                editor.clear();
                            }
                            editor.apply();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.putExtra("isLogin",isLogin);
                            intent.putExtra("User",user);
                            startActivity(intent);
                            finish();
                        }
                        else {
                            if(type == "login") {
                                ToastUtil.makeText(LoginActivity.this, "帐号或密码错误");
                            }else {
                                ToastUtil.makeText(LoginActivity.this, "错误");
                            }
                        }
                    }
                });
            }

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtil.makeText(LoginActivity.this, "网络出错");
                    }
                });
            }
        });
    }
}
