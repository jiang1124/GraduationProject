package com.example.store.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.TextView;

import com.example.store.Application.BaseApplication;
import com.example.store.Entity.Product;
import com.example.store.Entity.Store;
import com.example.store.Layout.LoadMoreListView;
import com.example.store.R;
import com.example.store.Utils.HttpUtil;
import com.example.store.Utils.LogUtil;
import com.example.store.Utils.ResponseUtil;
import com.example.store.Utils.RoundCornerDialog;
import com.example.store.Utils.ToastUtil;
import com.example.store.adapter.ProductListAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;

public class ProductListActivity extends BaseActivity {

    private List<Product> productList = new ArrayList<>();
    private Store store;
    private String Web = ResponseUtil.Web;

    ProductListAdapter productListAdapter;
    LoadMoreListView listView;
    private int page=0;

    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        Intent getIntent = getIntent();
        store = (Store) getIntent.getSerializableExtra("Store");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        //悬浮按钮
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProductListActivity.this,ProductDetail.class);
                intent.putExtra("Store",store);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart(){
        super.onStart();
        page = 0;
        String webAddress = Web + "/storeProducts?store_id=" + store.getStore_id();
        initProducts(webAddress+"&page="+page);
        initListView(webAddress);
        initRefresh(webAddress);
    }

    private void initListView(final String webAddress) {
        productListAdapter = new ProductListAdapter(ProductListActivity.this,R.layout.item_product,productList);
        listView = (LoadMoreListView) findViewById(R.id.lv_product_list);
        listView.setAdapter(productListAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Product product = productList.get(position);
                Intent intent = new Intent(ProductListActivity.this,ProductDetail.class);
                intent.putExtra("Product",product);
                intent.putExtra("Store",store);
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
                                productListAdapter.notifyDataSetChanged();
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
                        productListAdapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        }.start();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        //找到SearchView并配置相关参数
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView mSearchView = (SearchView) searchItem.getActionView();
        //搜索图标是否显示在搜索框内
        mSearchView.setIconifiedByDefault(true);
        //设置搜索框展开时是否显示提交按钮，可不显示
        mSearchView.setSubmitButtonEnabled(true);
        //让键盘的回车键设置成搜索
        mSearchView.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        //搜索框是否展开，false表示展开
        mSearchView.setIconified(false);
        //获取焦点
        mSearchView.setFocusable(true);
        mSearchView.requestFocusFromTouch();
        //设置提示词
        mSearchView.setQueryHint("请输入关键字");
        // 设置搜索文本监听
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
            @Override
            public boolean onQueryTextChange(String newText){
                //
                return true;
            }
            @Override
            public boolean onQueryTextSubmit(String query){
                ToastUtil.makeText(BaseApplication.getContext(),"You want to search "+query);
                return false;
            }
        });
        return true;
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
                            productListAdapter.notifyDataSetChanged();
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
                        initRefresh(Web + "/storeProducts?store_id=" + store.getStore_id());
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
        final RoundCornerDialog roundCornerDialog = new RoundCornerDialog(BaseApplication.getContext(), 0, 0, view, R.style.RoundCornerDialog);
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
                delProducts(webAddress);
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
