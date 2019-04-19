package com.example.shoppingsystem.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.shoppingsystem.Application.BaseApplication;
import com.example.shoppingsystem.R;
import com.example.shoppingsystem.adapter.ProductListAdapter;
import com.example.shoppingsystem.adapter.ShoppingCarAdapter;
import com.example.shoppingsystem.emtity.Product;
import com.example.shoppingsystem.emtity.ShoppingCar;
import com.example.shoppingsystem.emtity.User;
import com.example.shoppingsystem.layout.RoundCornerDialog;
import com.example.shoppingsystem.util.*;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
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
    ExpandableListView elvShoppingCar;
    @InjectView(R.id.iv_select_all)
    ImageView ivSelectAll;
    @InjectView(R.id.ll_select_all)
    LinearLayout llSelectAll;
    @InjectView(R.id.btn_order)
    Button btnOrder;
    @InjectView(R.id.btn_delete)
    Button btnDelete;
    @InjectView(R.id.tv_total_price)
    TextView tvTotalPrice;
    @InjectView(R.id.rl_total_price)
    RelativeLayout rlTotalPrice;
    @InjectView(R.id.rl)
    RelativeLayout rl;
    @InjectView(R.id.iv_no_contant)
    ImageView ivNoContant;
    @InjectView(R.id.rl_no_contant)
    RelativeLayout rlNoContant;
    @InjectView(R.id.tv_refresh_cart)
    TextView RefreshTV;
    private List<ShoppingCar.Store> stores;
    private ShoppingCarAdapter shoppingCarAdapter;

    private boolean isLogin;
    private User user;
    private List<Product> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        Intent intent =getIntent();
        isLogin = intent.getBooleanExtra("isLogin",false);
        user = (User) intent.getSerializableExtra("User");
        LogUtil.d("Main isLogin:","is "+isLogin);
        initHome("http://10.0.2.2:8080/home");
    }

    /*
     * 点击事件
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
                startActivity(intent);
                break;
            case R.id.home_button:
                initHome("http://10.0.2.2:8080/home");
                PageSwitch(sortView, shoppingCartView, personView, homeView);
                break;
            case R.id.sort_button:
                PageSwitch(homeView, shoppingCartView, personView, sortView);
                break;
            case R.id.shopping_cart_button:
                initExpandableListView();
                initShoppingCart("http://10.0.2.2:8080/shoppingCart");
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
                headIntent.putExtra("isOff",false);
                startActivity(headIntent);
                break;
            default:
                break;
        }
    }

    /*
     * 页面切换
     */
    public final void PageSwitch(View goneView1,View goneView2,View goneView3,View visibleView){
        goneView1.setVisibility(View.GONE);
        goneView2.setVisibility(View.GONE);
        goneView3.setVisibility(View.GONE);
        visibleView.setVisibility(View.VISIBLE);
    }


    /*
     * 初始化
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

    @OnClick({R.id.tv_refresh_cart, R.id.tv_edit_cart})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_refresh_cart://刷新数据
                initShoppingCart("http://10.0.2.2:8080/shoppingCart");
                break;
            case R.id.tv_edit_cart://编辑
                String edit = EditCartTV.getText().toString().trim();
                if (edit.equals("编辑")) {
                    EditCartTV.setText("完成");
                    rlTotalPrice.setVisibility(View.GONE);
                    btnOrder.setVisibility(View.GONE);
                    btnDelete.setVisibility(View.VISIBLE);
                } else {
                    EditCartTV.setText("编辑");
                    rlTotalPrice.setVisibility(View.VISIBLE);
                    btnOrder.setVisibility(View.VISIBLE);
                    btnDelete.setVisibility(View.GONE);
                }
                break;
            default:
                break;
        }
    }
    /**
     * 初始化数据
     */
    private void initShoppingCart(String websiteAddress) {

//        HttpUtil.sendOkHttpRequest(websiteAddress, new okhttp3.Callback() {
//            @Override
//            public void onResponse(okhttp3.Call call, Response response) throws IOException {
//                String responseText = response.body().string();
//                LogUtil.d("Car标记0",responseText);
//                ShoppingCar shoppingCar = ResponseUtil.handleShoppingCart(responseText);
//                if(stores!=null)
//                    stores.clear();
//                stores = shoppingCar.getStores();
//                LogUtil.d("Car标记1",stores.get(0).getStore_name());
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        if (stores != null) {
//                            initExpandableListViewData(stores);
//                        } else {
//                            ToastUtil.makeText(BaseApplication.getContext(), "获取数据失败");
//                        }
//                    }
//                });
//            }
//            @Override
//            public void onFailure(okhttp3.Call call, IOException e) {
//                e.printStackTrace();
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        ToastUtil.makeText(BaseApplication.getContext(), "获取数据失败");
//                    }
//                });
//            }
//        });
        ShoppingCar shoppingCar=initShoppingCartStr();
        stores = shoppingCar.getStores();
        initExpandableListViewData(stores);
    }



    /**
     * 初始化ExpandableListView
     * 创建数据适配器adapter，并进行初始化操作
     */
    private void initExpandableListView() {
        shoppingCarAdapter = new ShoppingCarAdapter(MainActivity.this, llSelectAll, ivSelectAll, btnOrder, btnDelete, rlTotalPrice, tvTotalPrice);
        elvShoppingCar.setAdapter(shoppingCarAdapter);

        //删除的回调
        shoppingCarAdapter.setOnDeleteListener(new ShoppingCarAdapter.OnDeleteListener() {
            @Override
            public void onDelete() {
                initDelete();
                /**
                 * 实际开发中，在此请求删除接口，删除成功后，
                 * 通过initExpandableListViewData（）方法刷新购物车数据。
                 * 注：通过bean类中的DatasBean的isSelect_shop属性，判断店铺是否被选中；
                 *                  GoodsBean的isSelect属性，判断商品是否被选中，
                 *                  （true为选中，false为未选中）
                 */
            }
        });

        //修改商品数量的回调
        shoppingCarAdapter.setOnChangeCountListener(new ShoppingCarAdapter.OnChangeCountListener() {
            @Override
            public void onChangeCount(String goods_id) {
                /**
                 * 实际开发中，在此请求修改商品数量的接口，商品数量修改成功后，
                 * 通过initExpandableListViewData（）方法刷新购物车数据。
                 */
            }
        });
    }

    /**
     * 初始化ExpandableListView的数据
     * 并在数据刷新时，页面保持当前位置
     *
     * @param storeList 购物车的数据
     */
    private void initExpandableListViewData(List<ShoppingCar.Store> storeList) {
        if (storeList != null && storeList.size() > 0) {
            //刷新数据时，保持当前位置
            shoppingCarAdapter.setData(storeList);

            //使所有组展开
            for (int i = 0; i < shoppingCarAdapter.getGroupCount(); i++) {
                elvShoppingCar.expandGroup(i);
            }

            //使组点击无效果
            elvShoppingCar.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

                @Override
                public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                    return true;
                }
            });

            EditCartTV.setVisibility(View.VISIBLE);
            EditCartTV.setText("编辑");
            rlNoContant.setVisibility(View.GONE);
            elvShoppingCar.setVisibility(View.VISIBLE);
            rl.setVisibility(View.VISIBLE);
            rlTotalPrice.setVisibility(View.VISIBLE);
            btnOrder.setVisibility(View.VISIBLE);
            btnDelete.setVisibility(View.GONE);
        } else {
            EditCartTV.setVisibility(View.GONE);
            rlNoContant.setVisibility(View.VISIBLE);
            elvShoppingCar.setVisibility(View.GONE);
            rl.setVisibility(View.GONE);
        }
    }

    /**
     * 判断是否要弹出删除的dialog
     * 通过bean类中的DatasBean的isSelect_shop属性，判断店铺是否被选中；
     * GoodsBean的isSelect属性，判断商品是否被选中，
     */
    private void initDelete() {
        //判断是否有店铺或商品被选中
        //true为有，则需要刷新数据；反之，则不需要；
        boolean hasSelect = false;
        //创建临时的List，用于存储没有被选中的购物车数据
        List<ShoppingCar.Store> storesTemp = new ArrayList<>();

        for (int i = 0; i < stores.size(); i++) {
            List<ShoppingCar.Store.Goods> goods = stores.get(i).getGoods();
            boolean isSelect_shop = stores.get(i).getIsSelect_shop();

            if (isSelect_shop) {
                hasSelect = true;
                //跳出本次循环，继续下次循环。
                continue;
            } else {
                storesTemp.add(stores.get(i));
                storesTemp.get(storesTemp.size() - 1).setGoods(new ArrayList<ShoppingCar.Store.Goods>());
            }

            for (int y = 0; y < goods.size(); y++) {
                ShoppingCar.Store.Goods good = goods.get(y);
                boolean isSelect = good.getIsSelect_product();

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
    }

    /**
     * 展示删除的dialog
     *
     * @param storesTemp
     */
    private void showDeleteDialog(final List<ShoppingCar.Store> storesTemp) {
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
                initExpandableListViewData(stores);
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

    public ShoppingCar initShoppingCartStr() {
        ShoppingCar shoppingCar =new ShoppingCar();
        Product product = new Product();
        product.setProduct_id(1);
        product.setPro_name("Apple");
        product.setPro_favl(12);
        product.setExtra_money(10);
        product.setPro_detail("苹果");
        product.setPro_image("http://10.0.2.2:8080/image/product/apple_pic.png");
        product.setType("水果");
        product.setStore_id(1);
        product.setPro_sale(10);
        product.setPro_num(10);
        ShoppingCar.Store.Goods good = new ShoppingCar.Store.Goods();
        good.setProduct(product);
        List<ShoppingCar.Store.Goods>gs = new ArrayList<>();
        gs.add(good);
        ShoppingCar.Store store =new ShoppingCar.Store();
        store.setGoods(gs);
        store.setStore_id(1);
        store.setStore_name("一号店");
        List<ShoppingCar.Store> ss=new ArrayList<>();
        ss.add(store);
        shoppingCar.setStores(ss);
        shoppingCar.setUser_id(1);
        return shoppingCar;
    }
}
