package com.example.shoppingsystem.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.shoppingsystem.Application.BaseApplication;
import com.example.shoppingsystem.Entity.Product;
import com.example.shoppingsystem.Entity.User;
import com.example.shoppingsystem.R;
import com.example.shoppingsystem.adapter.LineProductAdapter;
import com.example.shoppingsystem.adapter.ProductListAdapter;
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
import okhttp3.Response;

public class MyHistoryActivity extends BaseActivity {
    @InjectView(R.id.tb_history)
    Toolbar tbHistory;

    private String Web = "http://10.0.2.2:8080";
    private List<Product> productList = new ArrayList<>();
    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_history);
        ButterKnife.inject(this);
        setSupportActionBar(tbHistory);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        Intent getIntent = getIntent();
        user = (User) getIntent.getSerializableExtra("User");
        initProduct(Web+"/history?user_id="+user.getUser_id());
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

    public  void initProduct(String websiteAddress){
        LogUtil.d("搜索内容：",websiteAddress);
        HttpUtil.sendOkHttpRequest(websiteAddress, new okhttp3.Callback() {
            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {
                String responseText = response.body().string();
                LogUtil.d("searchStr:",responseText);
                if(productList!=null)
                    productList.clear();
                productList = ResponseUtil.handleProductList(responseText);
                if(productList!=null)
                    LogUtil.d("searchResult:",productList.toString());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (productList != null) {
                            LineProductAdapter lineProductAdapter = new LineProductAdapter(BaseApplication.getContext(),R.layout.item_product_order_child,productList);
                            final ListView orderListView = (ListView) findViewById(R.id.lv_history_product_list);
                            orderListView.setAdapter(lineProductAdapter);
                            orderListView.setOnLongClickListener(new View.OnLongClickListener() {
                                @Override
                                public boolean onLongClick(View v) {
                                    return false;
                                }
                            });
                        } else {
                            ToastUtil.makeText(BaseApplication.getContext(), "没有商品");
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
                        ToastUtil.makeText(BaseApplication.getContext(), "获取数据失败");
                    }
                });
            }
        });
    }
    /**
     * 展示删除的dialog
     *
     * @param webAddress
     */
    private void showDeleteDialog(final String webAddress) {
        View view = View.inflate(MyHistoryActivity.this, R.layout.dialog_two_btn, null);
        final RoundCornerDialog roundCornerDialog = new RoundCornerDialog(MyHistoryActivity.this, 0, 0, view, R.style.RoundCornerDialog);
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
                initProduct(Web+"/history?user_id="+user.getUser_id());
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
