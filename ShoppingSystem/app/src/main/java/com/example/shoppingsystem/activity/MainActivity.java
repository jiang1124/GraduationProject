package com.example.shoppingsystem.activity;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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
import com.example.shoppingsystem.Entity.Goods;
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
    @InjectView(R.id.tv_my_grade)
    TextView myGrade;
    @InjectView(R.id.tv_my_score)
    TextView myScore;
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

    private List<Shop> stores;
    private ShoppingCarAdapter shoppingCarAdapter;
    private boolean isLogin;
    private User user;
    private List<Product> productList;
    private List<String> sortData = new ArrayList<>();
    private String productIdStr;
    private String Web = "http://10.0.2.2:8080";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        Intent intent =getIntent();
        isLogin = intent.getBooleanExtra("isLogin",false);
        user = (User) intent.getSerializableExtra("User");
        LogUtil.d("Main isLogin:","is "+isLogin);
        initHome(Web+"/home");
    }

    //-----------------------------------------------------------------------------------------------

    /**
     * 点击事件:切换（首页，搜索，分类，个人），跳转（地址，收藏，足迹，订单，登陆）
     */
    @OnClick({R.id.home_button,R.id.search_button,R.id.sort_button,R.id.shopping_cart_button,
            R.id.person_button,R.id.tv_my_address,R.id.tv_my_collection,R.id.tv_my_history,
            R.id.tv_my_order,R.id.iv_my_head})
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
                PageSwitch(sortView, shoppingCartView, personView, homeView);
                break;
            case R.id.sort_button:
                initSort();
                ArrayAdapter<String> sortAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,sortData);
                sortListView.setAdapter(sortAdapter);
                PageSwitch(homeView, shoppingCartView, personView, sortView);
                break;
            case R.id.shopping_cart_button:
                initExpandableListView();
                initShoppingCart(Web+"/shoppingCart?id="+user.getUser_id());
                PageSwitch(homeView, sortView, personView, shoppingCartView);
                break;
            case R.id.person_button:
                initPerson();
                PageSwitch(homeView, sortView, shoppingCartView, personView);
                break;
            case R.id.tv_my_address:
                if(isLogin) {
                    Intent addressIntent = new Intent(MainActivity.this, MyAddressActivity.class);
                    addressIntent.putExtra("User", user);
                    startActivity(addressIntent);
                }else {
                    ToastUtil.makeText(this,"请先登陆");
                }
                break;
            case R.id.tv_my_collection:
                if(isLogin) {
                    Intent collectionIntent = new Intent(MainActivity.this, MyCollectionActivity.class);
                    collectionIntent.putExtra("User", user);
                    startActivity(collectionIntent);
                }else {
                    ToastUtil.makeText(this,"请先登陆");
                }
                break;
            case R.id.tv_my_history:
                if(isLogin) {
                    Intent historyIntent = new Intent(MainActivity.this, MyHistoryActivity.class);
                    historyIntent.putExtra("User", user);
                    startActivity(historyIntent);
                }else {
                    ToastUtil.makeText(this,"请先登陆");
                }
                break;
            case R.id.tv_my_order:
                if(isLogin) {
                    Intent orderIntent = new Intent(MainActivity.this, MyOrderActivity.class);
                    orderIntent.putExtra("User", user);
                    startActivity(orderIntent);
                }else {
                    ToastUtil.makeText(this,"请先登陆");
                }
                break;
            case R.id.iv_my_head:
                Intent headIntent = new Intent(MainActivity.this, LoginActivity.class);
                headIntent.putExtra("User",user);
                headIntent.putExtra("isOn",false);
                startActivity(headIntent);
                break;
            default:
                break;
        }
    }

    //-----------------------------------------------------------------------------------------------

    /**
     * 页面切换
     */
    public final void PageSwitch(View goneView1,View goneView2,View goneView3,View visibleView){
        goneView1.setVisibility(View.GONE);
        goneView2.setVisibility(View.GONE);
        goneView3.setVisibility(View.GONE);
        visibleView.setVisibility(View.VISIBLE);
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
//                LogUtil.d("Main标记1",productList.get(0).getPro_name());
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
        if (isLogin){
            Glide.with(MainActivity.this)
                    .load(user.getHead_image())
                    .placeholder(R.mipmap.ic_launcher)
                    .into(myHead);
            myNameTV.setText(user.getUser_name());
            myGrade.setText("等级"+user.getUser_grade());
            myGrade.setVisibility(View.VISIBLE);
            myScore.setText("积分"+user.getUser_score()+"");
            myScore.setVisibility(View.VISIBLE);
        }else{
            myNameTV.setText("未登录");
            myHead.setImageResource(R.mipmap.ic_launcher);
            myGrade.setVisibility(View.GONE);
            myScore.setVisibility(View.GONE);
        }
    }

//-----------------------------------------------------------------------------------------------

   /**
    * 初始化分类项
    */
    private void initSort(){
        sortData.add("水果"); //在链接好数据库的实战中，这个地方水果可以改为re.title
        sortData.add("休闲零食");
        sortData.add("茶酒冲饮");
        sortData.add("粮油干货");
        sortData.add("居家日用");
        sortData.add("餐饮用具");
        sortData.add("厨房烹饪");
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
//        Car shoppingCar=initShoppingCartStr();
//        stores = shoppingCar.getStores();
//        initExpandableListViewData(stores);
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
                settlementCartButton, DeleteCartButton, totalPriceRL, totalPriceTV);
        shoppingCarELV.setAdapter(shoppingCarAdapter);

        //删除的回调
        shoppingCarAdapter.setOnDeleteListener(new ShoppingCarAdapter.OnDeleteListener() {
            @Override
            public void onDelete(String productIds) {
                initDelete();
                productIdStr = productIds;
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
     * 判断是否要弹出删除的dialog
     * 通过bean类中的DatasBean的isSelect_shop属性，判断店铺是否被选中；
     * GoodsBean的isSelect属性，判断商品是否被选中，
     */
    private List<Goods> initDelete() {
        //判断是否有店铺或商品被选中
        //true为有，则需要刷新数据；反之，则不需要；
        boolean hasSelect = false;
        //创建临时的List，用于存储没有被选中的购物车数据
        List<Shop> storesTemp = new ArrayList<>();
        //创建临时的List，用于存储被选中的商品数据
        List<Goods> goodsList = new ArrayList<>();

        for (int i = 0; i < stores.size(); i++) {
            List<Goods> goods = stores.get(i).getGoods();
            boolean isSelect_shop = stores.get(i).isSelect_shop();

            if (isSelect_shop) {
                hasSelect = true;
                //跳出本次循环，继续下次循环。
                goodsList.add(goods.get(i));
                continue;
            } else {
                storesTemp.add(stores.get(i));
                storesTemp.get(storesTemp.size() - 1).setGoods(new ArrayList<Goods>());
            }

            for (int y = 0; y < goods.size(); y++) {
                Goods good = goods.get(y);
                boolean isSelect = good.isSelect_product();

                if (isSelect) {
                    hasSelect = true;
                } else {
                    storesTemp.get(storesTemp.size() - 1).getGoods().add(good);
                }
            }
        }

        if (hasSelect) {
            showDeleteDialog(storesTemp);
        } else {
            ToastUtil.makeText(MainActivity.this, "请选择要删除的商品");
        }
        return goodsList;
    }

    /**
     * 展示删除的dialog
     *
     * @param storesTemp
     */
    private void showDeleteDialog(final List<Shop> storesTemp) {
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
                stores = storesTemp;
//                initExpandableListViewData(stores);
                if(user!=null) {
                    String webAddress = Web+"/delProduct?user_id="
                            +user.getUser_id()+"&productIds="+productIdStr;
                    initShoppingCart(webAddress);
                }
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

//    public Car initShoppingCartStr() {
//        Car shoppingCar =new Car();
//        Goods good = new Goods();
//        good.setProduct_id(1);
//        good.setPro_name("Apple");
//        good.setPro_favl(12);
//        good.setExtra_money(10);
//        good.setPro_detail("苹果");
//        good.setPro_image(Web+"/image/product/apple_pic.png");
//        good.setType("水果");
//        good.setStore_id(1);
//        good.setPro_sale(10);
//        good.setPro_num(10);
//        List<Goods>gs = new ArrayList<>();
//        gs.add(good);
//        Shop store =new Shop();
//        store.setGoods(gs);
//        store.setStore_id(1);
//        store.setStore_name("一号店");
//        List<Shop> ss=new ArrayList<>();
//        ss.add(store);
//        shoppingCar.setStores(ss);
//        shoppingCar.setUser_id(1);
//        return shoppingCar;
//    }

}
