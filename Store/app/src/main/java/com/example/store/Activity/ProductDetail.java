package com.example.store.Activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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
import com.example.store.Utils.HttpUtil;
import com.example.store.Utils.LogUtil;
import com.example.store.Utils.ResponseUtil;
import com.example.store.Utils.ToastUtil;
import com.example.store.Utils.Upload;

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
    @InjectView(R.id.et_product_detail)
    EditText productDetail;

    private ProgressDialog progressDialog;

    private Product product;
    private Store store;
    private String Web = ResponseUtil.Web;
    String[] type = {"休闲零食","茶水饮料","粮油调味","生活日用","水具酒具","厨房用具","清洁用具"};

    public static final int TAKE_PHOTO = 1;

    public static final int CHOOSE_PHOTO = 2;

    private Uri imageUri;

    private String imagePath;

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
                if (ContextCompat.checkSelfPermission(ProductDetail.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(ProductDetail.this, new String[]{ Manifest.permission. WRITE_EXTERNAL_STORAGE }, 1);
                } else {
                    openAlbum();
                }
                break;
            default:
                break;
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
        String detail = "detail="+productDetail.getText().toString();
        if(product!=null){
            String productId = "product_id="+product.getProduct_id();
            webAddress = Web+"/updateProduct?"+productId+"&"+productName+"&"+productPrice+"&"+productFavl+"&"+extraMoney+"&"+type+"&"+detail;
        }else {
            if(imagePath!=null) {
                String storeId = "store_id=" + store.getStore_id();
                webAddress = Web + "/addProductDetail?" + storeId + "&" + productName + "&" + productPrice + "&" + productFavl + "&" + extraMoney + "&" + type+"&"+detail;
            }else {
                webAddress="fail";
            }
        }
        if(webAddress.equals("fail")){
            ToastUtil.makeText(BaseApplication.getContext(),"请选择图片");
        }else {
            progressDialog = new ProgressDialog(ProductDetail.this);
            progressDialog.setTitle("正在上传信息，请勿关闭");
            progressDialog.setMessage("上传中");
            progressDialog.setCancelable(true);
            progressDialog.show();
            toServer(webAddress);
        }
    }

    private void toServer(String webAddress){
        HttpUtil.sendOkHttpRequest(webAddress,new okhttp3.Callback(){
            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {
                String responseText = response.body().string();
                LogUtil.d("ProductDetail 回应结果：",responseText);
                final String res = responseText;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(res!=null){
                            String WebAddr = Web+"/filesUpload";
                            if(imagePath!=null) {
                                Upload.uploadFile(imagePath, WebAddr,res);
                            }
                            ToastUtil.makeText(BaseApplication.getContext(),"成功");
                            progressDialog.dismiss();
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

    private void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, CHOOSE_PHOTO); // 打开相册
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openAlbum();
                } else {
                    ToastUtil.makeText(this, "You denied the permission");
                }
                break;
            default:
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    try {
                        // 将拍摄的照片显示出来
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        upload.setImageBitmap(bitmap);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            case CHOOSE_PHOTO:
                if (resultCode == RESULT_OK) {
                    // 判断手机系统版本号
                    if (Build.VERSION.SDK_INT >= 19) {
                        // 4.4及以上系统使用这个方法处理图片
                        imagePath = handleImageOnKitKat(data);
                    } else {
                        // 4.4以下系统使用这个方法处理图片
                        imagePath = handleImageBeforeKitKat(data);
                    }
                }
                break;
            default:
                break;
        }
    }

    @TargetApi(19)
    private String handleImageOnKitKat(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        LogUtil.d("TAG", "handleImageOnKitKat: uri is " + uri);
        if (DocumentsContract.isDocumentUri(this, uri)) {
            // 如果是document类型的Uri，则通过document id处理
            String docId = DocumentsContract.getDocumentId(uri);
            if("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1]; // 解析出数字格式的id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // 如果是content类型的Uri，则使用普通方式处理
            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            // 如果是file类型的Uri，直接获取图片路径即可
            imagePath = uri.getPath();
        }
        displayImage(imagePath);
        return imagePath; // 根据图片路径显示图片
    }

    private String handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri, null);
        displayImage(imagePath);
        return imagePath;
    }

    private String getImagePath(Uri uri, String selection) {
        String path = null;
        // 通过Uri和selection来获取真实的图片路径
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    private void displayImage(String imagePath) {
        if (imagePath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            upload.setImageBitmap(bitmap);
        } else {
            ToastUtil.makeText(this, "failed to get image");
        }
    }
}
