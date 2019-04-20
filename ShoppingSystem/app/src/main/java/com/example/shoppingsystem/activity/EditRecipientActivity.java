package com.example.shoppingsystem.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.shoppingsystem.R;
import com.example.shoppingsystem.Entity.Recipient;
import com.lljjcoder.Interface.OnCityItemClickListener;
import com.lljjcoder.bean.CityBean;
import com.lljjcoder.bean.DistrictBean;
import com.lljjcoder.bean.ProvinceBean;
import com.lljjcoder.style.cityjd.JDCityPicker;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class EditRecipientActivity extends BaseActivity {
    @InjectView(R.id.tv_select_city)
    TextView cityTV;
    @InjectView(R.id.btn_cancel_edit_recipient)
    Button cancelButton;

    private JDCityPicker cityPicker = new JDCityPicker();
    private String Web = "http://10.0.2.2:8080";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_recipient);
        cityPicker.init(this);
        ButterKnife.inject(this);
        Intent intent = getIntent();
        Recipient recipient =(Recipient) intent.getSerializableExtra("Recipient");

    }

    @OnClick({R.id.tv_select_city,R.id.btn_cancel_edit_recipient})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.tv_select_city:
                selectAddress();
                break;
            case R.id.btn_cancel_edit_recipient:
                finish();
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

}