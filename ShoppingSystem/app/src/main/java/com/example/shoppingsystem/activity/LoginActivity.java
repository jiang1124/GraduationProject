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
import com.example.shoppingsystem.util.ToastUtil;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isRemember = pref.getBoolean("remember_password",false);

        setSupportActionBar(tbLogin);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        if(isRemember){
            //将帐号和密码都设置到文本框中
            String account = pref.getString("account","");
            String password = pref.getString("password","");
            accountInputEdit.setText(account);
            passwordInputEdit.setText(password);
            rememberPass.setChecked(true);
        }
    }

    @OnClick({R.id.btn_login,R.id.btn_login_to_registered,R.id.btn_registered_to_login,R.id.btn_registered})
    public void onClick(View v){
        switch (v.getId()) {
            case R.id.btn_login:
            getLoginAnswer();
            String account = accountInputEdit.getText().toString();
            String password = passwordInputEdit.getText().toString();
            if (getLoginAnswer()) {
                editor = pref.edit();
                if (rememberPass.isChecked()) {
                    editor.putBoolean("remember_password", true);
                    editor.putString("account", account);
                    editor.putString("password", password);
                } else {
                    editor.clear();
                }
                editor.apply();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                ToastUtil.makeText(LoginActivity.this, "帐号或密码错误");
            }
                break;
            case R.id.btn_registered:
                String registeredAccount = accountInputEdit.getText().toString();
                String registeredPassword = passwordInputEdit.getText().toString();
                if (getRegisteredAnswer()) {
                    editor = pref.edit();
                    if (registeredRememberPass.isChecked()) {
                        editor.putBoolean("remember_password", true);
                        editor.putString("account", registeredAccount);
                        editor.putString("password", registeredPassword);
                    } else {
                        editor.clear();
                    }
                    editor.apply();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    ToastUtil.makeText(LoginActivity.this, "注册失败");
                }
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
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean getLoginAnswer(){
        return  true;
    }

    public boolean getRegisteredAnswer(){
        return true;
    }
}
