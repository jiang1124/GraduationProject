package com.example.shoppingsystem.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.shoppingsystem.Application.BaseApplication;
import com.example.shoppingsystem.Entity.User;
import com.example.shoppingsystem.R;
import com.example.shoppingsystem.Entity.Recipient;
import com.example.shoppingsystem.util.HttpUtil;
import com.example.shoppingsystem.util.LogUtil;
import com.example.shoppingsystem.util.ToastUtil;
import com.lljjcoder.Interface.OnCityItemClickListener;
import com.lljjcoder.bean.CityBean;
import com.lljjcoder.bean.DistrictBean;
import com.lljjcoder.bean.ProvinceBean;
import com.lljjcoder.style.cityjd.JDCityPicker;

import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import okhttp3.Response;

public class EditRecipientActivity extends BaseActivity {
    @InjectView(R.id.tb_address)
    Toolbar tbAddress;
    @InjectView(R.id.et_recipient_name)
    EditText recipientNameET;
    @InjectView(R.id.et_recipient_phone)
    EditText phoneET;
    @InjectView(R.id.tv_select_city)
    TextView cityTV;
    @InjectView(R.id.et_recipient_address)
    EditText detailAddressET;
    @InjectView(R.id.btn_cancel_edit_recipient)
    Button cancelButton;
    @InjectView(R.id.s_default)
    Spinner spinner;

    private JDCityPicker cityPicker = new JDCityPicker();
    private String Web = "http://10.0.2.2:8080";
    private ActionBar actionBar;
    private Recipient recipient;
    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_recipient);
        cityPicker.init(this);
        ButterKnife.inject(this);
        setSupportActionBar(tbAddress);
        init();
    }

    @OnClick({R.id.tv_select_city,R.id.btn_cancel_edit_recipient,R.id.btn_yes_edit_recipient})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.tv_select_city:
                selectAddress();
                break;
            case R.id.btn_cancel_edit_recipient:
                finish();
                break;
            case R.id.btn_yes_edit_recipient:
                if(recipientNameET.getText().toString().equals("")||phoneET.getText().toString().equals("")
                        ||cityTV.getText().toString().equals("")||detailAddressET.getText().toString().equals("")) {
                    ToastUtil.makeText(this,"请填写完整！");
                }else {
                    String state = (String) spinner.getSelectedItem();
                    LogUtil.d("下拉框：",state);
                    if (recipient != null) {
                        submit(Web + "/updateAddress?address_id=" + recipient.getAddress_id() + "&user_id=" + recipient.getUser_id()
                                + "&recipient_name=" + recipientNameET.getText().toString() + "&phone=" + phoneET.getText().toString()
                                + "&city=" + cityTV.getText().toString() + "&address=" + detailAddressET.getText().toString() + "&state=" + state);
                    } else {
                        submit(Web+ "/addAddress?user_id=" + user.getUser_id() + "&recipient_name=" + recipientNameET.getText().toString()
                                + "&phone=" + phoneET.getText().toString() + "&city=" + cityTV.getText().toString() + "&address="
                                + detailAddressET.getText().toString() + "&state=" + state);
                    }
                    Intent intent = new Intent(EditRecipientActivity.this,MyAddressActivity.class);
                    if(user!=null)
                        intent.putExtra("User",user);
                    startActivity(intent);
                }
                break;
            default:
                break;
        }
    }

    public void selectAddress(){
        //监听选择点击事件及返回结果
        cityPicker.setOnCityItemClickListener(new OnCityItemClickListener() {
            @Override
            public void onSelected(ProvinceBean province, CityBean city, DistrictBean district) {
                cityTV.setText(province.toString().trim() + city.toString().trim() + district.toString().trim());
            }
            @Override
            public void onCancel() {
               //
            }
        });
        cityPicker.showCityPicker();
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

    private void init(){
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        Intent intent = getIntent();
        recipient =(Recipient) intent.getSerializableExtra("Recipient");
        user = (User) intent.getSerializableExtra("User");
        if(recipient!=null) {
            recipientNameET.setText(recipient.getRecipient_name());
            phoneET.setText(recipient.getPhone());
            cityTV.setText(recipient.getCity());
            detailAddressET.setText(recipient.getAddress());
        }
        String[] arr = {"设为默认","不设为默认"};
        ArrayAdapter<String> sprinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,arr);
        spinner.setAdapter(sprinnerAdapter);
        if(recipient!=null&&recipient.getState().equals("default")){
            spinner.setSelection(0);
        }else {
            spinner.setSelection(1);
        }
    }

    private void submit(String webAddress){
        LogUtil.d("edit:",webAddress);
        HttpUtil.sendOkHttpRequest(webAddress, new okhttp3.Callback() {
            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {
                String responseText = response.body().string();
                LogUtil.d("回应结果：",responseText);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                            ToastUtil.makeText(BaseApplication.getContext(), "提交成功");
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