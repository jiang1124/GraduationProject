package com.example.shoppingsystem.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shoppingsystem.R;
import com.example.shoppingsystem.classfile.BaseActivity;

public class LoginActivity extends BaseActivity implements View.OnClickListener{
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private EditText accountInputEdit;
    private EditText passwordInputEdit;
    private Button loginButton;
    private CheckBox rememberPass;
    private String accountTrue;
    private String passwordTrue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        accountInputEdit = (EditText) findViewById(R.id.account_login_text);
        passwordInputEdit = (EditText) findViewById(R.id.password_login_text);
        rememberPass = (CheckBox) findViewById(R.id.remember_pass);
        loginButton = (Button) findViewById(R.id.login_button);
        boolean isRemember = pref.getBoolean("remember_password",false);
        if(isRemember){
            //将帐号和密码都设置到文本框中
            String account = pref.getString("account","");
            String password = pref.getString("password","");
            accountInputEdit.setText(account);
            passwordInputEdit.setText(password);
            rememberPass.setChecked(true);
        }
        loginButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        switch (v.getId()) {
            case R.id.login_button:
            setAccountAndPassword();
            String account = accountInputEdit.getText().toString();
            String password = passwordInputEdit.getText().toString();
            if (account.equals(accountTrue) && password.equals(passwordTrue)) {
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
                Toast.makeText(LoginActivity.this, "帐号或密码错误", Toast.LENGTH_SHORT).show();
            }
                break;
            default:
                break;
        }
    }

    public void setAccountAndPassword(){
        accountTrue = "admin";
        passwordTrue = "123456";
    }
}
