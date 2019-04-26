package com.example.store.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;

import com.example.store.Application.BaseApplication;
import com.example.store.Entity.Product;
import com.example.store.Entity.Store;
import com.example.store.R;
import com.example.store.Utils.HttpUtil;
import com.example.store.Utils.LogUtil;
import com.example.store.Utils.ResponseUtil;
import com.example.store.Utils.ToastUtil;
import com.example.store.adapter.ProductListAdapter;

import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import okhttp3.Response;

public class LoginActivity extends BaseActivity {
    @InjectView(R.id.btn_login)
    Button loginButton;
    @InjectView(R.id.et_store_name)
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
    private Store store;
    private String Web = "http://10.0.2.2:8080";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isRemember = pref.getBoolean("rememberPassword",false);
        boolean isAutoLogin = pref.getBoolean("autoLogin",false);
        if(isRemember){
            account = pref.getString("account","");
            password = pref.getString("password","");
            accountEdit.setText(account);
            passwordEdit.setText(password);
            rememberCheckBox.setChecked(true);
            if(isAutoLogin&&!account.equals("")&&!password.equals("")) {
                autoLoginCheckButton.setChecked(true);
                initStore(Web + "?account=" + accountEdit.getText().toString().trim()+"&password="+passwordEdit.getText().toString().trim());
            }
        }
    }

    @OnClick({R.id.btn_login,R.id.tv_re})
    public void onClick(View view){
        switch (view.getId()) {
            case R.id.btn_login:
                account = accountEdit.getText().toString().trim();
                password = passwordEdit.getText().toString().trim();
                Login();
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
        startActivity(intentMain);
    }

    public void initStore(String webAddress){
        HttpUtil.sendOkHttpRequest(webAddress,new okhttp3.Callback(){
            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {
                String responseText = response.body().string();
                LogUtil.d("ProductListActivity 回应结果：",responseText);
                store = ResponseUtil.handleStore(responseText);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(store != null){
                            Login();
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
