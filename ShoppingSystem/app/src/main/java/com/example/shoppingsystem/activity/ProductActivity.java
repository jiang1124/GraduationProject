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

import com.bumptech.glide.Glide;
import com.example.shoppingsystem.emtity.Product;
import com.example.shoppingsystem.R;
import com.example.shoppingsystem.util.ToastUtil;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class ProductActivity extends BaseActivity{
    @InjectView(R.id.collect_button)
    Button collectButton;
    @InjectView(R.id.in_shopping_cart_button)
    Button inCartButton;
    @InjectView(R.id.buy_button)
    Button buyButton;
    @InjectView(R.id.product_price_text)
    TextView productPriceText;
    @InjectView(R.id.product_sale_price)
    TextView productSalePriceText;
    @InjectView(R.id.additional_charges)
    TextView additionalCharges;
    @InjectView(R.id.product_name_text)
    TextView productNameText;
    @InjectView(R.id.product_detail_text)
    TextView productDetailText;
    @InjectView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.product_image_product)
    ImageView productImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        ButterKnife.inject(this);
        /*
         * 接收上一活动传输的数据
         */
        Intent intent = getIntent();
        Product product = (Product) intent.getSerializableExtra("Product");
        /*
         * 设置图片及文本内容
         */
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        String priceStr = "价格："+product.getProductPrice();
        String salePriceStr = "促销价："+product.getProductSalePrice();
        String addStr = "运费:"+product.getAdditionalCharges();
        collapsingToolbar.setTitle(product.getProductName());
        Glide.with(ProductActivity.this)
                .load(product.getProductImageId())
                .placeholder(R.mipmap.ic_launcher)
                .into(productImageView);
        productPriceText.setText(priceStr);
        productSalePriceText.setText(salePriceStr);
        additionalCharges.setText(addStr);
        productNameText.setText(product.getProductName());
        productDetailText.setText(product.getProductDetail());
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

    @OnClick({R.id.collect_button,R.id.in_shopping_cart_button,R.id.buy_button})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.collect_button:
                ToastUtil.makeText(v.getContext(),"已收藏");
                break;
            case R.id.in_shopping_cart_button:
                ToastUtil.makeText(v.getContext(),"已入购物车");
                break;
            case R.id.buy_button:
                ToastUtil.makeText(v.getContext(),"Buy");
                break;
            default:
                break;
        }
    }
}
