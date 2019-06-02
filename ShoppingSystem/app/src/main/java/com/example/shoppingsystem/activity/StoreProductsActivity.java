package com.example.shoppingsystem.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.example.shoppingsystem.Application.BaseApplication;
import com.example.shoppingsystem.Entity.Product;
import com.example.shoppingsystem.R;
import com.example.shoppingsystem.View.LoadMoreListView;
import com.example.shoppingsystem.adapter.ProductsAdapter;
import com.example.shoppingsystem.layout.RoundCornerDialog;
import com.example.shoppingsystem.util.HttpUtil;
import com.example.shoppingsystem.util.LogUtil;
import com.example.shoppingsystem.util.ResponseUtil;
import com.example.shoppingsystem.util.ToastUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;

public class StoreProductsActivity extends BaseActivity {
    private List<Product> productList = new ArrayList<>();
    private String Web = ResponseUtil.Web;

    private ProductsAdapter productsAdapter;
    private LoadMoreListView listView;
    private int page=0;

    private int store_id;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_products);
        Intent getIntent = getIntent();
        store_id = getIntent.getIntExtra("store_id",-1);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    protected void onStart(){
        super.onStart();
        page = 0;
        productList.clear();
        String webAddress = Web + "/storeProducts?store_id=" + store_id;
        initProducts(webAddress+"&page="+page);
        initListView(webAddress);
        initRefresh(webAddress);
    }

    private void initListView(final String webAddress) {
        productsAdapter = new ProductsAdapter(StoreProductsActivity.this,R.layout.item_product,productList);
        listView = (LoadMoreListView) findViewById(R.id.lv_store_product_list);
        listView.setAdapter(productsAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Product product = productList.get(position);
                Intent intent = new Intent(StoreProductsActivity.this,ProductActivity.class);
                intent.putExtra("Product",product);
                startActivity(intent);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Product product = productList.get(position);
                showDeleteDialog(Web + "/deleteProduct?product_id=" + product.getProduct_id());
                return true;
            }
        });
        listView.setOnLoadMoreListener(new LoadMoreListView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                initProducts(webAddress + "&page=" + page);
                                productsAdapter.notifyDataSetChanged();
                                listView.setLoadCompleted();
                            }
                        });
                    }
                }.start();
            }
        });
    }

    private void initRefresh(final String webAddress){
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setColorSchemeResources(
                R.color.colorPrimary,
                R.color.green,
                R.color.red
        );
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){
            @Override
            public void onRefresh(){
                refreshOrderList(webAddress);
            }
        });
    }

    private void refreshOrderList(final String webAddress){
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        page=0;
                        productList.clear();
                        initProducts(webAddress + "&page=" + page);
                        productsAdapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        }.start();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void initProducts(String webAddress){
        HttpUtil.sendOkHttpRequest(webAddress,new okhttp3.Callback(){
            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {
                String responseText = response.body().string();
                LogUtil.d("ProductListActivity 回应结果：",responseText);
                final List<Product> products = ResponseUtil.handleProductList(responseText);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        page+=1;
                        if(products != null){
                            productList.addAll(products);
                            productsAdapter.notifyDataSetChanged();
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

    private void delProducts(String webAddress){
        HttpUtil.sendOkHttpRequest(webAddress,new okhttp3.Callback(){
            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {
                String responseText = response.body().string();
                LogUtil.d("回应结果：",responseText);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initRefresh(Web + "/storeProducts?store_id=" + store_id);
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

    /**
     * 展示删除的dialog
     *
     * @param webAddress
     */
    private void showDeleteDialog(final String webAddress) {
        View view = View.inflate(BaseApplication.getContext(), R.layout.dialog_two_btn, null);
        final RoundCornerDialog roundCornerDialog = new RoundCornerDialog(StoreProductsActivity.this, 0, 0, view, R.style.RoundCornerDialog);
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
                delProducts(webAddress);
                roundCornerDialog.dismiss();
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
