package com.example.shoppingsystem.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.shoppingsystem.Entity.Recipient;
import com.example.shoppingsystem.Entity.User;
import com.example.shoppingsystem.R;
import com.example.shoppingsystem.adapter.AddressAdapter;
import com.example.shoppingsystem.layout.RoundCornerDialog;
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
    @InjectView(R.id.btn_refresh_address)
    Button refreshButton;

    private List<Recipient> recipientList = new ArrayList<>();
    private User user;
    private String Web = "http://10.0.2.2:8080";
    AddressAdapter recipientAdapter;
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
        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("User");
        initRecipientList(Web+"/recipient?user_id="+user.getUser_id());

    }

    @OnClick({R.id.btn_add_address,R.id.btn_refresh_address})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_add_address:
                Intent intent = new Intent(MyAddressActivity.this, EditRecipientActivity.class);
                intent.putExtra("User",user);
                startActivity(intent);
                break;
            case R.id.btn_refresh_address:
                initRecipientList(Web+"/recipient?user_id="+user.getUser_id());
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

    private void initRecipientList(final String websiteAddress){
        //收件人id，收件人名，地址，电话号码，用户id，状态
        HttpUtil.sendOkHttpRequest(websiteAddress,new okhttp3.Callback(){
            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException{
                String responseText =response.body().string();
                LogUtil.d("标记0",responseText);
                if (recipientList!=null)
                    recipientList.clear();
                recipientList = ResponseUtil.handleRecipientListResponse(responseText);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(recipientList !=null ){
                            initRecyclerView();
                        }
                        else {
                            ToastUtil.makeText(MyAddressActivity.this,"没有地址信息");
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
                        ToastUtil.makeText(MyAddressActivity.this,"网络出错");
                    }
                });
            }
        });
    }

    private void initRecyclerView() {
        GridLayoutManager layoutManager = new GridLayoutManager(MyAddressActivity.this, 1);
        recipientRecyclerView.setLayoutManager(layoutManager);
        recipientAdapter = new AddressAdapter(recipientList,user);
        recipientRecyclerView.setAdapter(recipientAdapter);

        //删除的回调
        recipientAdapter.setLongClickListener(new AddressAdapter.OnLongClickListener() {
            @Override
            public void onLongClick(int address_id) {
                String webAddress=Web+"/delAddress?address_id="+address_id+"&user_id="+user.getUser_id();
                showDeleteDialog(webAddress);
            }
        });
    }

    /**
     * 展示删除的dialog
     *
     * @param webAddress
     */
    private void showDeleteDialog(final String webAddress) {
        View view = View.inflate(MyAddressActivity.this, R.layout.dialog_two_btn, null);
        final RoundCornerDialog roundCornerDialog = new RoundCornerDialog(MyAddressActivity.this, 0, 0, view, R.style.RoundCornerDialog);
        roundCornerDialog.show();
        roundCornerDialog.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
        roundCornerDialog.setOnKeyListener(keyListener);//设置点击返回键Dialog不消失

        TextView tv_message = (TextView) view.findViewById(R.id.tv_message);
        TextView tv_logout_confirm = (TextView) view.findViewById(R.id.tv_logout_confirm);
        TextView tv_logout_cancel = (TextView) view.findViewById(R.id.tv_logout_cancel);
        tv_message.setText("确定要删除商品吗？");

        //确定
        tv_logout_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                roundCornerDialog.dismiss();
                initRecipientList(webAddress);
            }
        });
        //取消
        tv_logout_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                roundCornerDialog.dismiss();
            }
        });
    }

    DialogInterface.OnKeyListener keyListener = new DialogInterface.OnKeyListener() {
        public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
                return true;
            } else {
                return false;
            }
        }
    };
}
