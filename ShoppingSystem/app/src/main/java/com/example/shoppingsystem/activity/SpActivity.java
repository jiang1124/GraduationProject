package com.example.shoppingsystem.activity;

import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.shoppingsystem.Application.BaseApplication;
import com.example.shoppingsystem.Entity.Shop;
import com.example.shoppingsystem.Entity.User;
import com.example.shoppingsystem.R;
import com.example.shoppingsystem.util.HttpUtil;
import com.example.shoppingsystem.util.LogUtil;
import com.example.shoppingsystem.util.ToastUtil;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import okhttp3.Response;

public class SpActivity extends BaseActivity {

    private TextView countdownTV;
    private MyCountDownTimer myCountDownTimer;
    private Handler handler = new Handler();
    private String Web = "http://10.0.2.2:8080";
    private List<Shop> shops;
    private String state;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sp);

        Intent getIntent = getIntent();
        shops = (List<Shop>) getIntent.getSerializableExtra("ShopList");
        user = (User) getIntent.getSerializableExtra("User");
        state = getIntent.getStringExtra("state");
        for(int i =0;i<shops.size();i++){
            submitOrder(shops.get(i));
        }


        countdownTV = (TextView) findViewById(R.id.tv_countdown);
        myCountDownTimer = new MyCountDownTimer(5000,1000);
        myCountDownTimer.start();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SpActivity.this,MyOrderActivity.class);
                intent.putExtra("User",user);
                startActivity(intent);
                finish();
            }
        },5000);
    }

    class MyCountDownTimer extends CountDownTimer{

        /**
         *
         * @param millisInFuture 表示毫秒为单位，倒计时总数
         * @param countDownInterval 表示间隔多少毫秒调用一次onTick方法
         * 注：1000毫秒=1秒
         */
        public  MyCountDownTimer(long millisInFuture,long countDownInterval){
            super(millisInFuture,countDownInterval);
        }
        @Override
        public void onFinish(){
            countdownTV.setText("交易完成，正在跳转");
        }
        @Override
        public void onTick(long millisUntilFinished){
            countdownTV.setText("倒计时("+millisUntilFinished/1000+")");
        }
    }

    private void submitOrder(Shop shop){
        double priceTemp = 0;
        String goodsStr ="";
        for(int i = 0;i < shop.getGoods().size();i++){
            priceTemp+=shop.getGoods().get(i).getPro_favl()*shop.getGoods().get(i).getProduct_num();
            goodsStr = goodsStr+shop.getGoods().get(i).getProduct_id()+",";
        }
        String webAddress = Web +"/addOrder?store_id="+shop.getStore_id()+"&goodsStr="+goodsStr+"&user_id="+user.getUser_id()+"&state="+state+"&all_money="+priceTemp;
        LogUtil.d("gsonStr:",webAddress);
        HttpUtil.sendOkHttpRequest(webAddress, new okhttp3.Callback() {
            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {
                String responseText = response.body().string();
                LogUtil.d("SpActivity requset:",responseText);
//                LogUtil.d("Main标记1",productList.get(0).getPro_name());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtil.makeText(BaseApplication.getContext(),"提交中，请勿关闭应用");
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
