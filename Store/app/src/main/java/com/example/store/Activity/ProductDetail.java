package com.example.store.Activity;

import android.content.Intent;
import android.os.Environment;
import android.provider.SyncStateContract;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.bumptech.glide.Glide;
import com.example.store.Application.BaseApplication;
import com.example.store.Entity.Product;
import com.example.store.Entity.Store;
import com.example.store.R;
import com.example.store.Utils.Constants;
import com.example.store.Utils.HttpUtil;
import com.example.store.Utils.LogUtil;
import com.example.store.Utils.ResponseUtil;
import com.example.store.Utils.ToastUtil;
import com.example.store.Utils.UploadUtil;
import com.wildma.pictureselector.ImageUtils;
import com.wildma.pictureselector.PictureSelector;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import okhttp3.Response;

public class ProductDetail extends BaseActivity {
    @InjectView(R.id.et_product_name)
    EditText productNameET;
    @InjectView(R.id.et_product_price)
    EditText  productPriceET;
    @InjectView(R.id.et_product_favl)
    EditText productFavlET;
    @InjectView(R.id.et_extra_money)
    EditText extraMoneyET;
    @InjectView(R.id.iv_upload)
    ImageView upload;
    @InjectView(R.id.btn_sure)
    Button sureButton;
    @InjectView(R.id.sp_product_type)
    Spinner productTypeSp;

    private Product product;
    private Store store;
    private String Web = ResponseUtil.Web;
    String[] type = {"休闲零食","茶水饮料","粮油调味","生活日用","水具酒具","厨房用具","清洁用具"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        ButterKnife.inject(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        Intent getIntent = getIntent();
        product = (Product) getIntent.getSerializableExtra("Product");
        store = (Store) getIntent.getSerializableExtra("Store");
        ArrayAdapter<String> sprinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,type);
        productTypeSp.setAdapter(sprinnerAdapter);
        if(product!=null){
            initProduct();
        }
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

    @OnClick({R.id.btn_sure,R.id.iv_upload})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_sure:
                submit();
                break;
            case R.id.iv_upload:
                /**
                 * create()方法参数一是上下文，在activity中传activity.this，在fragment中传fragment.this。参数二为请求码，用于结果回调onActivityResult中判断
                 * selectPicture()方法参数分别为 是否裁剪、裁剪后图片的宽(单位px)、裁剪后图片的高、宽比例、高比例。都不传则默认为裁剪，宽200，高200，宽高比例为1：1。
                 */
                PictureSelector
                        .create(ProductDetail.this, PictureSelector.SELECT_REQUEST_CODE)
                        .selectPicture(true, 200, 200, 1, 1);

                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /*结果回调*/
        if (requestCode == PictureSelector.SELECT_REQUEST_CODE) {
            if (data != null) {
                String picturePath = data.getStringExtra(PictureSelector.PICTURE_PATH);

//                Glide.with(ProductDetail.this)
//                        .load(PictureSelector.PICTURE_PATH)
//                        .error(R.drawable.upload)
//                        .into(upload);
            }
        }
    }

    private void initProduct(){
        productNameET.setText(product.getPro_name());
        productPriceET.setText(product.getPro_price()+"");
        productFavlET.setText(product.getPro_favl()+"");
        extraMoneyET.setText(product.getExtra_money()+"");
        int i;
        for(i=0;i<type.length;++i){
            if(type[i].equals(product.getType()))
                break;
        }
        productTypeSp.setSelection(i);
        Glide.with(ProductDetail.this)
                .load(product.getPro_image())
                .error(R.drawable.upload)
                .into(upload);
    }

    private void submit(){
        String webAddress = "";
        String productName = "product_name="+productNameET.getText().toString().trim();
        String productPrice = "product_price="+productPriceET.getText().toString().trim();
        String productFavl = "product_favl="+productFavlET.getText().toString().trim();
        String extraMoney = "extra_money="+extraMoneyET.getText().toString().trim();
        String type = "type="+productTypeSp.getSelectedItem();
        if(product!=null){
            String productId = "product_id="+product.getProduct_id();
            webAddress = Web+"/updateProduct?"+productId+"&"+productName+"&"+productPrice+"&"+productFavl+"&"+extraMoney+"&"+type;
        }else {
            String storeId = "store_id="+store.getStore_id();
            webAddress = Web+"/addProductDetail?"+storeId+"&"+productName+"&"+productPrice+"&"+productFavl+"&"+extraMoney+"&"+type;
        }
        toServer(webAddress);
    }

    private void toServer(String webAddress){
        HttpUtil.sendOkHttpRequest(webAddress,new okhttp3.Callback(){
            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {
                String responseText = response.body().string();
                LogUtil.d("ProductListActivity 回应结果：",responseText);
                final String res = responseText;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(res.equals("add")){
                            ToastUtil.makeText(BaseApplication.getContext(),"添加成功");
                        }else if (res.equals("update")){
                            ToastUtil.makeText(BaseApplication.getContext(),"修改成功");
                        }
                        Intent intent = new Intent(BaseApplication.getContext(),ProductListActivity.class);
                        intent.putExtra("Store",store);
                        startActivity(intent);
                        finish();
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
