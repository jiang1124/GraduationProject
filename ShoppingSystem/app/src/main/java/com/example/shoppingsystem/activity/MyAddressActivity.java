package com.example.shoppingsystem.activity;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.shoppingsystem.Entity.Recipient;
import com.example.shoppingsystem.R;
import com.example.shoppingsystem.adapter.AddressAdapter;
import com.example.shoppingsystem.util.HttpUtil;
import com.example.shoppingsystem.util.LogUtil;
import com.example.shoppingsystem.util.ResponseUtil;
import com.example.shoppingsystem.util.ToastUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import okhttp3.Response;

public class MyAddressActivity extends BaseActivity {
    @InjectView(R.id.tb_address)
    Toolbar tbAddress;
    @InjectView(R.id.rv_recipient_list)
    RecyclerView recipientRecyclerView;
    @InjectView(R.id.btn_add_address)
    Button addAddressButton;

    private List<Recipient> recipientList = new ArrayList<>();
    private String Web = "http://10.0.2.2:8080";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_address);
        ButterKnife.inject(this);
        setSupportActionBar(tbAddress);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        requestRecipientList(Web+"/recipientTran/recipientList");

    }

    @OnClick(R.id.btn_add_address)
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_add_address:
                Intent intent = new Intent(MyAddressActivity.this, EditRecipientActivity.class);
                startActivity(intent);
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
    private void requestRecipientList(final String websiteAddress){
        //收件人id，收件人名，地址，电话号码，用户id，状态
        HttpUtil.sendOkHttpRequest(websiteAddress,new okhttp3.Callback(){
            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException{
                String responseText =response.body().string();
                LogUtil.d("标记0",responseText);
                if (recipientList!=null)
                    recipientList.clear();
                recipientList = ResponseUtil.handleRecipientListResponse(responseText);
                if (recipientList!=null)
                    LogUtil.d("标记1",recipientList.get(0).getRecipient_name());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(recipientList !=null ){
                            GridLayoutManager layoutManager = new GridLayoutManager(MyAddressActivity.this, 1);
                            recipientRecyclerView.setLayoutManager(layoutManager);
                            AddressAdapter recipientAdapter = new AddressAdapter(recipientList);
                            recipientRecyclerView.setAdapter(recipientAdapter);
                        }
                        else {
                            ToastUtil.makeText(MyAddressActivity.this,"获取数据失败");
                        }
                    }
                });
            }
            @Override
            public void onFailure(okhttp3.Call call, IOException e){
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtil.makeText(MyAddressActivity.this,"获取数据失败");
                    }
                });
            }
        });
    }
}
