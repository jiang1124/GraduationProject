package com.example.shoppingsystem.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.shoppingsystem.R;
import com.example.shoppingsystem.adapter.ProductListAdapter;
import com.example.shoppingsystem.Application.BaseApplication;
import com.example.shoppingsystem.emtity.Product;
import com.example.shoppingsystem.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class SearchActivity extends AppCompatActivity {
    @InjectView(R.id.rv_product_list)
    RecyclerView recyclerView;
    @InjectView(R.id.et_search_in_search)
    EditText searchEdit;
    @InjectView(R.id.btn_search_in_search)
    Button searchButton;
    @InjectView(R.id.tv_default_queue)
    TextView defaultQueueText;
    @InjectView(R.id.tv_price_queue)
    TextView priceQueueText;
    @InjectView(R.id.tv_sales_volume_queue)
    TextView salesVolumeQueueText;

    private List<Product> productList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Intent intent = getIntent();
        String key = intent.getStringExtra("key");
        String sort = intent.getStringExtra("sort");
        ButterKnife.inject(this);

        initSearchProductList();
        GridLayoutManager layoutManager = new GridLayoutManager(SearchActivity.this, 2);
        recyclerView.setLayoutManager(layoutManager);
        ProductListAdapter productListAdapter = new ProductListAdapter(productList);
        recyclerView.setAdapter(productListAdapter);

        if(key!=null)
            searchEdit.setText(key);
        else
            searchEdit.setText(sort);
    }

    @OnClick({R.id.btn_search_in_search,R.id.tv_default_queue,R.id.tv_price_queue,R.id.tv_sales_volume_queue})
    public void onClick(View v){
        switch (v.getId()) {
            case R.id.btn_search_in_search:
                ToastUtil.makeText(BaseApplication.getContext(),"You click search");
                break;
            case R.id.tv_default_queue:
                shiftQueueText(priceQueueText,salesVolumeQueueText,defaultQueueText);
                break;
            case R.id.tv_price_queue:
                shiftQueueText(defaultQueueText,salesVolumeQueueText,priceQueueText);
                break;
            case R.id.tv_sales_volume_queue:
                shiftQueueText(defaultQueueText,priceQueueText,salesVolumeQueueText);
                break;
            default:
                break;
        }
    }

    /*
     * 初始化
     */
    private void initSearchProductList() {
        Product apple = new Product("1", "Apple", R.drawable.apple_pic,15,9,"苹果",99,"水果",10,"红色");
        productList.add(apple);
        Product banana = new Product("2", "Banana", R.drawable.banana_pic,25,19,"香蕉",34,"水果",10,"黄色");
        productList.add(banana);
        Product orange = new Product("3", "Orange", R.drawable.orange_pic,35,29,"橘子",659,"水果",10,"橙色");
        productList.add(orange);
        Product watermelon = new Product("4", "Watermelon", R.drawable.watermelon_pic,45,39,"西瓜",95,"水果",10,"绿色");
        productList.add(watermelon);
        Product pear = new Product("5", "Pear", R.drawable.pear_pic,55,49,"梨子",94,"水果",10,"黄色");
        productList.add(pear);
        Product grape = new Product("6", "Grape", R.drawable.grape_pic,65,59,"葡萄",96,"水果",10,"紫色");
        productList.add(grape);
        Product pineapple = new Product("7", "Pineapple", R.drawable.pineapple_pic,75,69,"菠萝",39,"水果",10,"黄色");
        productList.add(pineapple);
        Product strawberry = new Product("8", "Strawberry", R.drawable.strawberry_pic,85,79,"草莓",89,"水果",10,"红色");
        productList.add(strawberry);
        Product cherry = new Product("9", "Cherry", R.drawable.cherry_pic,95,89,"樱桃",19,"水果",10,"红色");
        productList.add(cherry);
        Product mango = new Product("10", "Mango", R.drawable.mango_pic,105,99,"芒果",84,"水果",10,"黄色");
        productList.add(mango);
    }

    private final void shiftQueueText(TextView textView1,TextView textView2,TextView textView3){
        textView1.setTextSize(15);
        textView1.setTextColor(Color.parseColor("#4c4c4c"));
        textView2.setTextSize(15);
        textView2.setTextColor(Color.parseColor("#4c4c4c"));
        textView3.setTextSize(18);
        textView3.setTextColor(Color.parseColor("#1a1a1a"));
    }
}
