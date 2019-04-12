package com.example.shoppingsystem.activity;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shoppingsystem.classfile.BaseActivity;
import com.example.shoppingsystem.classfile.Product;
import com.example.shoppingsystem.R;

public class ProductActivity extends BaseActivity implements View.OnClickListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        /*
         * 接收上一活动传输的数据
         */
        Intent intent = getIntent();
        Product product = (Product) intent.getSerializableExtra("Product");
        /*
         * 设置图片及文本内容
         */
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        ImageView productImageView = (ImageView) findViewById(R.id.product_image_product);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        collapsingToolbar.setTitle(product.getProductName());
        productImageView.setImageResource(product.getProductImageId());
        setTextViewContent(R.id.product_price_text,"价格："+product.getProductPrice());
        setTextViewContent(R.id.product_sale_price,"促销价："+product.getProductSalePrice());
        setTextViewContent(R.id.additional_charges,"运费:"+product.getAdditionalCharges());
        setTextViewContent(R.id.product_name_text,product.getProductName());
        setTextViewContent(R.id.product_detail_text,product.getProductDetail());

        Button collectButton = (Button) findViewById(R.id.collect_button);
        collectButton.setOnClickListener(this);
        Button inCartButton = (Button) findViewById(R.id.in_shopping_cart_button);
        inCartButton.setOnClickListener(this);
        Button buyButton = (Button) findViewById(R.id.buy_button);
        buyButton.setOnClickListener(this);
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

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.collect_button:
                Toast.makeText(v.getContext(),"已收藏",Toast.LENGTH_SHORT).show();
                break;
            case R.id.in_shopping_cart_button:
                Toast.makeText(v.getContext(),"已入购物车",Toast.LENGTH_SHORT).show();
                break;
            case R.id.buy_button:
                Toast.makeText(v.getContext(),"Buy",Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }
    final private void setTextViewContent(int textViewId,String textContent){
        TextView textView = (TextView) findViewById(textViewId);
        textView.setText(textContent);
    }
}
