package com.example.shoppingsystem.Activity;


import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.shoppingsystem.Application.BaseApplication;
import com.example.shoppingsystem.Entity.Car;
import com.example.shoppingsystem.Entity.Product;
import com.example.shoppingsystem.Entity.Shop;
import com.example.shoppingsystem.Entity.User;
import com.example.shoppingsystem.R;
import com.example.shoppingsystem.adapter.ProductListAdapter;
import com.example.shoppingsystem.adapter.ShoppingCarAdapter;
import com.example.shoppingsystem.layout.RoundCornerDialog;
import com.example.shoppingsystem.util.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.OnItemClick;
import okhttp3.Response;

public class MainActivity extends BaseActivity {
    @InjectView(R.id.home_button)
    Button homeButton;
    @InjectView(R.id.search_button)
    Button searchButton;
    @InjectView(R.id.sort_button)
    Button sortButton;
    @InjectView(R.id.shopping_cart_button)
    Button shoppingCartButton;
    @InjectView(R.id.person_button)
    Button personButton;
    @InjectView(R.id.home_layout_main)
    LinearLayout homeView;
    @InjectView(R.id.sort_layout_main)
    LinearLayout sortView;
    @InjectView(R.id.shopping_cart_layout_main)
    LinearLayout shoppingCartView;
    @InjectView(R.id.person_layout_main)
    LinearLayout personView;
    @InjectView(R.id.rv_home_list)
    RecyclerView homeRecyclerView;
    @InjectView(R.id.tv_my_address)
    TextView myAddressTV;
    @InjectView(R.id.tv_my_collection)
    TextView myCollectionTV;
    @InjectView(R.id.tv_my_history)
    TextView myHistoryTV;
    @InjectView(R.id.tv_my_name)
    TextView myNameTV;
    @InjectView(R.id.tv_my_order)
    TextView myOrderTV;
    @InjectView(R.id.iv_my_head)
    ImageView myHead;
    @InjectView(R.id.tv_edit_cart)
    TextView EditCartTV;
    @InjectView(R.id.elv_shopping_car)
    ExpandableListView shoppingCarELV;
    @InjectView(R.id.iv_select_all_cart)
    ImageView selectAllCartIV;
    @InjectView(R.id.ll_select_all_cart)
    LinearLayout SelectAllCartLL;
    @InjectView(R.id.btn_settlement_cart)
    Button settlementCartButton;
    @InjectView(R.id.btn_delete_cart)
    Button DeleteCartButton;
    @InjectView(R.id.tv_total_price_cart)
    TextView totalPriceTV;
    @InjectView(R.id.rl_total_price)
    RelativeLayout totalPriceRL;
    @InjectView(R.id.rl_bottom_cart)
    RelativeLayout bottomCartRL;
    @InjectView(R.id.iv_no_content)
    ImageView noContentIV;
    @InjectView(R.id.rl_no_content)
    RelativeLayout noContentRL;
    @InjectView(R.id.tv_refresh_cart)
    TextView RefreshTV;
    @InjectView(R.id.sort_list_view)
    ListView sortListView;
    @InjectView(R.id.btn_login)
    Button loginButton;
    @InjectView(R.id.btn_offline)
    Button outButton;

    private List<Shop> stores;
    private ShoppingCarAdapter shoppingCarAdapter;
    private boolean isOn;
    private User user;
    private List<Product> productList;
    private List<String> sortData = new ArrayList<>();
    private String Web = ResponseUtil.Web;


    public static final int TAKE_PHOTO = 1;
    public static final int CHOOSE_PHOTO = 2;
    private String imagePath;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        Intent intent =getIntent();
        isOn = intent.getBooleanExtra("isOn",false);
        user = (User) intent.getSerializableExtra("User");
        LogUtil.d("Main isOn:","is "+isOn);
        initHome(Web+"/home");
    }

    //-----------------------------------------------------------------------------------------------

    /**
     * 点击事件:切换（首页，搜索，分类，个人），跳转（地址，收藏，足迹，订单，登陆）
     */
    @OnClick({R.id.home_button,R.id.search_button,R.id.sort_button,R.id.shopping_cart_button,
            R.id.person_button,R.id.tv_my_address,R.id.tv_my_collection,R.id.tv_my_history,
            R.id.tv_my_order,R.id.iv_my_head,R.id.btn_offline,R.id.btn_login})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.search_button:
                EditText searchEditText = (EditText) findViewById(R.id.search_edit);
                String searchContent = searchEditText.getText().toString();
                Intent intent = new Intent(MainActivity.this,SearchActivity.class);
                intent.putExtra("key",searchContent);
                intent.putExtra("User",user);
                startActivity(intent);
                break;
            case R.id.home_button:
                initHome(Web+"/home");
                PageSwitch(sortView, shoppingCartView, personView, homeView,"home");
                break;
            case R.id.sort_button:
                initSort();
                ArrayAdapter<String> sortAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,sortData);
                sortListView.setAdapter(sortAdapter);
                PageSwitch(homeView, shoppingCartView, personView, sortView,"sort");
                break;
            case R.id.shopping_cart_button:
                if(isOn) {
                    initExpandableListView();
                    initShoppingCart(Web + "/shoppingCart?id=" + user.getUser_id());
                    PageSwitch(homeView, sortView, personView, shoppingCartView,"car");
                }else {
                    ToastUtil.makeText(this,"请先登陆");
                }
                break;
            case R.id.person_button:
                initPerson();
                PageSwitch(homeView, sortView, shoppingCartView, personView,"user");
                break;
            case R.id.tv_my_address:
                if(isOn) {
                    Intent addressIntent = new Intent(MainActivity.this, MyAddressActivity.class);
                    addressIntent.putExtra("User", user);
                    startActivity(addressIntent);
                }else {
                    ToastUtil.makeText(this,"请先登陆");
                }
                break;
            case R.id.tv_my_collection:
                if(isOn) {
                    Intent collectionIntent = new Intent(MainActivity.this, MyCollectionActivity.class);
                    collectionIntent.putExtra("User", user);
                    startActivity(collectionIntent);
                }else {
                    ToastUtil.makeText(this,"请先登陆");
                }
                break;
            case R.id.tv_my_history:
                if(isOn) {
                    Intent historyIntent = new Intent(MainActivity.this, MyHistoryActivity.class);
                    historyIntent.putExtra("User", user);
                    startActivity(historyIntent);
                }else {
                    ToastUtil.makeText(this,"请先登陆");
                }
                break;
            case R.id.tv_my_order:
                if(isOn) {
                    Intent orderIntent = new Intent(MainActivity.this, MyOrderActivity.class);
                    orderIntent.putExtra("User", user);
                    startActivity(orderIntent);
                }else {
                    ToastUtil.makeText(this,"请先登陆");
                }
                break;
            case R.id.iv_my_head:
                if(user!=null) {
                    if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                    } else {
                        openAlbum();
                    }
                }
                break;
            case R.id.btn_login:
                if(user==null) {
                    Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
                    loginIntent.putExtra("isOn", false);
                    startActivity(loginIntent);
                    finish();
                }
                break;
            case R.id.btn_offline:
                if(user!=null)
                {
                    user=null;
                    isOn=false;
                    initPerson();
                }
                break;
            default:
                break;
        }
    }

    //-----------------------------------------------------------------------------------------------

    /**
     * 页面切换
     */
    public void PageSwitch(View goneView1,View goneView2,View goneView3,View visibleView,String type){
        goneView1.setVisibility(View.GONE);
        goneView2.setVisibility(View.GONE);
        goneView3.setVisibility(View.GONE);
        visibleView.setVisibility(View.VISIBLE);
        homeButton.setBackgroundResource(R.drawable.home);
        sortButton.setBackgroundResource(R.drawable.classify);
        shoppingCartButton.setBackgroundResource(R.drawable.shopping);
        personButton.setBackgroundResource(R.drawable.user);
        if(type.equals("home")){
            homeButton.setBackgroundResource(R.drawable.home_selected);
        }else if(type.equals("sort")){
            sortButton.setBackgroundResource(R.drawable.classify_selected);
        }else if(type.equals("car")){
            shoppingCartButton.setBackgroundResource(R.drawable.shopping_selected);
        }else if(type.equals("user")){
            personButton.setBackgroundResource(R.drawable.user_selected);
        };
    }

//-----------------------------------------------------------------------------------------------
    /**
     * 首页页面初始化
     * @param websiteAddress url信息
     */
    private void initHome(String websiteAddress) {
        HttpUtil.sendOkHttpRequest(websiteAddress, new okhttp3.Callback() {
            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {
                String responseText = response.body().string();
//                LogUtil.d("Main标记0",responseText);
                if(productList!=null)
                    productList.clear();
                productList = ResponseUtil.handleProductList(responseText);
                LogUtil.d("Main标记1",productList.get(0).getPro_name());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (productList != null) {
                            GridLayoutManager layoutManager = new GridLayoutManager(MainActivity.this, 2);
                            homeRecyclerView.setLayoutManager(layoutManager);
                            ProductListAdapter productListAdapter = new ProductListAdapter(productList);
                            if(user!=null)
                                productListAdapter.setUserId(user.getUser_id());
                            homeRecyclerView.setAdapter(productListAdapter);
                            productListAdapter.notifyDataSetChanged();
                        } else {
                            ToastUtil.makeText(BaseApplication.getContext(), "获取数据失败");
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

//-----------------------------------------------------------------------------------------------

    /**
     * 个人页面初始化
     */
    private void initPerson(){
        if(user!=null) {
            outButton.setVisibility(View.VISIBLE);
            loginButton.setVisibility(View.GONE);
        }
        else{
            outButton.setVisibility(View.GONE);
            loginButton.setVisibility(View.VISIBLE);
        }
        if (isOn){
            Glide.with(MainActivity.this)
                    .load(user.getHead_image())
                    .placeholder(R.drawable.head)
                    .into(myHead);
            myNameTV.setText(user.getUser_name());
        }else{
            myNameTV.setText("未登录");
            Glide.with(MainActivity.this)
                    .load(R.drawable.head)
                    .into(myHead);
        }
    }

//-----------------------------------------------------------------------------------------------

   /**
    * 初始化分类项
    */
    private void initSort(){
        sortData.add("休闲零食");
        sortData.add("茶水饮料");
        sortData.add("粮油调味");
        sortData.add("生活日用");
        sortData.add("水具酒具");
        sortData.add("厨房用具");
        sortData.add("清洁用具");
    }

    /**
     * 分类点击事件
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @OnItemClick(R.id.sort_list_view)
    public void onItemClick(AdapterView<?> parent, View view, int position, long id){
        Intent intent = new Intent(this,SearchActivity.class);
        String sort = sortData.get(position);
        intent.putExtra("User",user);
        intent.putExtra("sort",sort);
        startActivity(intent);
    }

//-----------------------------------------------------------------------------------------------
    /**
     * 初始化购物车信息
     * @param websiteAddress url信息
     */
    private void initShoppingCart(String websiteAddress) {

        HttpUtil.sendOkHttpRequest(websiteAddress, new okhttp3.Callback() {
            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {
                String responseText = response.body().string();
                LogUtil.d("Car标记0",responseText);
                Car shoppingCar = ResponseUtil.handleShoppingCart(responseText);
                if(stores!=null)
                    stores.clear();
                if(shoppingCar!=null)
                    stores = shoppingCar.getStores();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (stores != null) {
                            initExpandableListViewData(stores);
                        } else {
                            ToastUtil.makeText(BaseApplication.getContext(), "获取数据失败");
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

    /**
     * 购物车刷新和编辑点击事件
     * @param view
     */
    @OnClick({R.id.tv_refresh_cart, R.id.tv_edit_cart})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_refresh_cart://刷新数据
                if(user!=null) {
                    initShoppingCart(Web+"/shoppingCart?id=" + user.getUser_id());
                } else{
                    ToastUtil.makeText(this,"请先登陆");
                }
                break;
            case R.id.tv_edit_cart://编辑
                String edit = EditCartTV.getText().toString().trim();
                if (edit.equals("编辑")) {
                    EditCartTV.setText("完成");
                    totalPriceRL.setVisibility(View.GONE);
                    settlementCartButton.setVisibility(View.GONE);
                    DeleteCartButton.setVisibility(View.VISIBLE);
                } else {
                    EditCartTV.setText("编辑");
                    totalPriceRL.setVisibility(View.VISIBLE);
                    settlementCartButton.setVisibility(View.VISIBLE);
                    DeleteCartButton.setVisibility(View.GONE);
                }
                break;
            default:
                break;
        }
    }

    /**
     * 初始化ExpandableListView
     * 创建数据适配器adapter，并进行初始化操作
     */
    private void initExpandableListView() {
        shoppingCarAdapter = new ShoppingCarAdapter(MainActivity.this, SelectAllCartLL, selectAllCartIV,
                settlementCartButton, DeleteCartButton, totalPriceRL, totalPriceTV,user);
        shoppingCarELV.setAdapter(shoppingCarAdapter);

        //删除的回调
        shoppingCarAdapter.setOnDeleteListener(new ShoppingCarAdapter.OnDeleteListener() {
            @Override
            public void onDelete(String productIds) {
                if(user!=null) {
                    String webAddress = Web+"/delProduct?user_id="
                            +user.getUser_id()+"&productIds="+productIds;
                    showDeleteDialog(webAddress);
                }
                //initDelete();
            }
        });

        //修改商品数量的回调
        shoppingCarAdapter.setOnChangeCountListener(new ShoppingCarAdapter.OnChangeCountListener() {
            @Override
            public void onChangeCount(int goods_id,int pro_num) {
                if(user!=null){
                    String webAddress = Web+"/updateProductNum?user_id=" + user.getUser_id() + "&product_id="
                            + goods_id + "&product_num=" + pro_num;
                    initShoppingCart(webAddress);
                }
            }
        });
    }

    /**
     * 初始化ExpandableListView的数据
     * 并在数据刷新时，页面保持当前位置
     *
     * @param storeList 购物车的数据
     */
    private void initExpandableListViewData(List<Shop> storeList) {
        if (storeList != null && storeList.size() > 0) {
            //刷新数据时，保持当前位置
            shoppingCarAdapter.setData(storeList);

            //使所有组展开
            for (int i = 0; i < shoppingCarAdapter.getGroupCount(); i++) {
                shoppingCarELV.expandGroup(i);
            }

            //使组点击无效果
            shoppingCarELV.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

                @Override
                public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                    return true;
                }
            });

            EditCartTV.setVisibility(View.VISIBLE);
            EditCartTV.setText("编辑");
            noContentRL.setVisibility(View.GONE);
            shoppingCarELV.setVisibility(View.VISIBLE);
            bottomCartRL.setVisibility(View.VISIBLE);
            totalPriceRL.setVisibility(View.VISIBLE);
            settlementCartButton.setVisibility(View.VISIBLE);
            DeleteCartButton.setVisibility(View.GONE);
        } else {
            EditCartTV.setVisibility(View.GONE);
            noContentRL.setVisibility(View.VISIBLE);
            shoppingCarELV.setVisibility(View.GONE);
            bottomCartRL.setVisibility(View.GONE);
        }
    }

    /**
     * 展示删除的dialog
     *
     * @param webAddress
     */
    private void showDeleteDialog(final String webAddress) {
        View view = View.inflate(MainActivity.this, R.layout.dialog_two_btn, null);
        final RoundCornerDialog roundCornerDialog = new RoundCornerDialog(MainActivity.this, 0, 0, view, R.style.RoundCornerDialog);
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
                initShoppingCart(webAddress);
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
                        myHead.setImageBitmap(bitmap);
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
                    String WebAddr = Web + "/filesUpload";
                    String name = user.getUser_id() + "";
                    if (imagePath != null) {
                        Upload.uploadFile(imagePath, WebAddr, name);
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
            myHead.setImageBitmap(bitmap);
        } else {
            ToastUtil.makeText(this, "failed to get image");
        }
    }

}
