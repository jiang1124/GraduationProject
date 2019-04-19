package com.example.shoppingsystem.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.example.shoppingsystem.R;

/**
 * Created by 79124 on 2019/4/9.
 */


/**
 * 购物车的最佳实现（分店铺）
 * 主要功能：1.刷新数据；
 *           2.单选、全选；
 *           3.合计；
 *           4.删除；
 *           5.商品数量加减；
 */
public class ShoppingCartLayout extends LinearLayout {

//    @InjectView(R.id.tv_titlebar_center)
//    TextView tvTitlebarCenter;
//    @InjectView(R.id.tv_titlebar_right)
//    TextView tvTitlebarRight;
//    @InjectView(R.id.elv_shopping_car)
//    ExpandableListView elvShoppingCar;
//    @InjectView(R.id.iv_select_all)
//    ImageView ivSelectAll;
//    @InjectView(R.id.ll_select_all)
//    LinearLayout llSelectAll;
//    @InjectView(R.id.btn_order)
//    Button btnOrder;
//    @InjectView(R.id.btn_delete)
//    Button btnDelete;
//    @InjectView(R.id.tv_total_price)
//    TextView tvTotalPrice;
//    @InjectView(R.id.rl_total_price)
//    RelativeLayout rlTotalPrice;
//    @InjectView(R.id.rl)
//    RelativeLayout rl;
//    @InjectView(R.id.iv_no_contant)
//    ImageView ivNoContant;
//    @InjectView(R.id.rl_no_contant)
//    RelativeLayout rlNoContant;
//    @InjectView(R.id.tv_titlebar_left)
//    TextView tvTitlebarLeft;


//    private List<ShoppingCarDataBean.DatasBean> datas;
//    private Context context;
//    private ShoppingCarAdapter shoppingCarAdapter;

    public ShoppingCartLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.shopping_cart_layout, this);
//        ButterKnife.inject(this);
//        this.context = context;
//
//        initExpandableListView();
//        initData();
    }

//    @OnClick({R.id.tv_titlebar_left, R.id.tv_titlebar_right})
//    public void onViewClicked(View view) {
//        switch (view.getId()) {
//            case R.id.tv_titlebar_left://刷新数据
//                initData();
//                break;
//            case R.id.tv_titlebar_right://编辑
//                String edit = tvTitlebarRight.getText().toString().trim();
//                if (edit.equals("编辑")) {
//                    tvTitlebarRight.setText("完成");
//                    rlTotalPrice.setVisibility(View.GONE);
//                    btnOrder.setVisibility(View.GONE);
//                    btnDelete.setVisibility(View.VISIBLE);
//                } else {
//                    tvTitlebarRight.setText("编辑");
//                    rlTotalPrice.setVisibility(View.VISIBLE);
//                    btnOrder.setVisibility(View.VISIBLE);
//                    btnDelete.setVisibility(View.GONE);
//                }
//                break;
//            default:
//                break;
//        }
//    }
//    /**
//     * 初始化数据
//     */
//    private void initData() {
//        //使用Gson解析购物车数据，
//        //ShoppingCarDataBean为bean类，Gson按照bean类的格式解析数据
//        /**
//         * 实际开发中，通过请求后台接口获取购物车数据并解析
//         */
//        //模拟的购物车数据（实际开发中使用后台返回的数据）
//        String shoppingCarData = "{\n" +
//                "    \"code\": 200,\n" +
//                "    \"datas\": [\n" +
//                "        {\n" +
//                "            \"goods\": [\n" +
//                "                {\n" +
//                "                    \"goods_id\": \"1\",\n" +
//                "                    \"goods_image\": \""+R.drawable.apple_pic+"\",\n" +
//                "                    \"goods_name\": \"Apple\",\n" +
//                "                    \"goods_num\": \"1\",\n" +
//                "                    \"goods_price\": \"9.00\"\n" +
//                "                }\n" +
//                "            ],\n" +
//                "            \"store_id\": \"1\",\n" +
//                "            \"store_name\": \"一号店\"\n" +
//                "        },\n" +
//                "        {\n" +
//                "            \"goods\": [\n" +
//                "                {\n" +
//                "                    \"goods_id\": \"2\",\n" +
//                "                    \"goods_image\": \""+R.drawable.banana_pic+"\",\n" +
//                "                    \"goods_name\": \"Banana\",\n" +
//                "                    \"goods_num\": \"2\",\n" +
//                "                    \"goods_price\": \"19.00\"\n" +
//                "                },\n" +
//                "                {\n" +
//                "                    \"goods_id\": \"222222\",\n" +
//                "                    \"goods_image\": \""+ R.drawable.orange_pic+"\",\n" +
//                "                    \"goods_name\": \"Orange\",\n" +
//                "                    \"goods_num\": \"2\",\n" +
//                "                    \"goods_price\": \"29.00\"\n" +
//                "                }\n" +
//                "            ],\n" +
//                "            \"store_id\": \"2\",\n" +
//                "            \"store_name\": \"二号店\"\n" +
//                "        },\n" +
//                "        {\n" +
//                "            \"goods\": [\n" +
//                "                {\n" +
//                "                    \"goods_id\": \"333331\",\n" +
//                "                    \"goods_image\": \""+R.drawable.watermelon_pic+"\",\n" +
//                "                    \"goods_name\": \"Watermelon\",\n" +
//                "                    \"goods_num\": \"3\",\n" +
//                "                    \"goods_price\": \"39.00\"\n" +
//                "                },\n" +
//                "                {\n" +
//                "                    \"goods_id\": \"333332\",\n" +
//                "                    \"goods_image\": \""+R.drawable.pear_pic+"\",\n" +
//                "                    \"goods_name\": \"Pear\",\n" +
//                "                    \"goods_num\": \"3\",\n" +
//                "                    \"goods_price\": \"49.00\"\n" +
//                "                },\n" +
//                "                {\n" +
//                "                    \"goods_id\": \"333333\",\n" +
//                "                    \"goods_image\": \""+R.drawable.grape_pic+"\",\n" +
//                "                    \"goods_name\": \"Grape\",\n" +
//                "                    \"goods_num\": \"3\",\n" +
//                "                    \"goods_price\": \"59.00\"\n" +
//                "                }\n" +
//                "            ],\n" +
//                "            \"store_id\": \"3\",\n" +
//                "            \"store_name\": \"三号店\"\n" +
//                "        }\n" +
//                "    ]\n" +
//                "}";
//        Gson gson = new Gson();
//        ShoppingCarDataBean shoppingCarDataBean = gson.fromJson(shoppingCarData, ShoppingCarDataBean.class);
//        datas = shoppingCarDataBean.getDatas();
//
//        initExpandableListViewData(datas);
//    }
//
//    /**
//     * 初始化ExpandableListView
//     * 创建数据适配器adapter，并进行初始化操作
//     */
//    private void initExpandableListView() {
//        shoppingCarAdapter = new ShoppingCarAdapter(context, llSelectAll, ivSelectAll, btnOrder, btnDelete, rlTotalPrice, tvTotalPrice);
//        elvShoppingCar.setAdapter(shoppingCarAdapter);
//
//        //删除的回调
//        shoppingCarAdapter.setOnDeleteListener(new ShoppingCarAdapter.OnDeleteListener() {
//            @Override
//            public void onDelete() {
//                initDelete();
//                /**
//                 * 实际开发中，在此请求删除接口，删除成功后，
//                 * 通过initExpandableListViewData（）方法刷新购物车数据。
//                 * 注：通过bean类中的DatasBean的isSelect_shop属性，判断店铺是否被选中；
//                 *                  GoodsBean的isSelect属性，判断商品是否被选中，
//                 *                  （true为选中，false为未选中）
//                 */
//            }
//        });
//
//        //修改商品数量的回调
//        shoppingCarAdapter.setOnChangeCountListener(new ShoppingCarAdapter.OnChangeCountListener() {
//            @Override
//            public void onChangeCount(String goods_id) {
//                /**
//                 * 实际开发中，在此请求修改商品数量的接口，商品数量修改成功后，
//                 * 通过initExpandableListViewData（）方法刷新购物车数据。
//                 */
//            }
//        });
//    }
//
//    /**
//     * 初始化ExpandableListView的数据
//     * 并在数据刷新时，页面保持当前位置
//     *
//     * @param datas 购物车的数据
//     */
//    private void initExpandableListViewData(List<ShoppingCarDataBean.DatasBean> datas) {
//        if (datas != null && datas.size() > 0) {
//            //刷新数据时，保持当前位置
//            shoppingCarAdapter.setData(datas);
//
//            //使所有组展开
//            for (int i = 0; i < shoppingCarAdapter.getGroupCount(); i++) {
//                elvShoppingCar.expandGroup(i);
//            }
//
//            //使组点击无效果
//            elvShoppingCar.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
//
//                @Override
//                public boolean onGroupClick(ExpandableListView parent, View v,
//                                            int groupPosition, long id) {
//                    return true;
//                }
//            });
//
//            tvTitlebarRight.setVisibility(View.VISIBLE);
//            tvTitlebarRight.setText("编辑");
//            rlNoContant.setVisibility(View.GONE);
//            elvShoppingCar.setVisibility(View.VISIBLE);
//            rl.setVisibility(View.VISIBLE);
//            rlTotalPrice.setVisibility(View.VISIBLE);
//            btnOrder.setVisibility(View.VISIBLE);
//            btnDelete.setVisibility(View.GONE);
//        } else {
//            tvTitlebarRight.setVisibility(View.GONE);
//            rlNoContant.setVisibility(View.VISIBLE);
//            elvShoppingCar.setVisibility(View.GONE);
//            rl.setVisibility(View.GONE);
//        }
//    }
//
//    /**
//     * 判断是否要弹出删除的dialog
//     * 通过bean类中的DatasBean的isSelect_shop属性，判断店铺是否被选中；
//     * GoodsBean的isSelect属性，判断商品是否被选中，
//     */
//    private void initDelete() {
//        //判断是否有店铺或商品被选中
//        //true为有，则需要刷新数据；反之，则不需要；
//        boolean hasSelect = false;
//        //创建临时的List，用于存储没有被选中的购物车数据
//        List<ShoppingCarDataBean.DatasBean> datasTemp = new ArrayList<>();
//
//        for (int i = 0; i < datas.size(); i++) {
//            List<ShoppingCarDataBean.DatasBean.GoodsBean> goods = datas.get(i).getGoods();
//            boolean isSelect_shop = datas.get(i).getIsSelect_shop();
//
//            if (isSelect_shop) {
//                hasSelect = true;
//                //跳出本次循环，继续下次循环。
//                continue;
//            } else {
//                datasTemp.add(datas.get(i));
//                datasTemp.get(datasTemp.size() - 1).setGoods(new ArrayList<ShoppingCarDataBean.DatasBean.GoodsBean>());
//            }
//
//            for (int y = 0; y < goods.size(); y++) {
//                ShoppingCarDataBean.DatasBean.GoodsBean goodsBean = goods.get(y);
//                boolean isSelect = goodsBean.getIsSelect();
//
//                if (isSelect) {
//                    hasSelect = true;
//                } else {
//                    datasTemp.get(datasTemp.size() - 1).getGoods().add(goodsBean);
//                }
//            }
//        }
//
//        if (hasSelect) {
//            showDeleteDialog(datasTemp);
//        } else {
//            ToastUtil.makeText(context, "请选择要删除的商品");
//        }
//    }
//
//    /**
//     * 展示删除的dialog
//     *
//     * @param datasTemp
//     */
//    private void showDeleteDialog(final List<ShoppingCarDataBean.DatasBean> datasTemp) {
//        View view = View.inflate(context, R.layout.dialog_two_btn, null);
//        final RoundCornerDialog roundCornerDialog = new RoundCornerDialog(context, 0, 0, view, R.style.RoundCornerDialog);
//        roundCornerDialog.show();
//        roundCornerDialog.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
//        roundCornerDialog.setOnKeyListener(keylistener);//设置点击返回键Dialog不消失
//
//        TextView tv_message = (TextView) view.findViewById(R.id.tv_message);
//        TextView tv_logout_confirm = (TextView) view.findViewById(R.id.tv_logout_confirm);
//        TextView tv_logout_cancel = (TextView) view.findViewById(R.id.tv_logout_cancel);
//        tv_message.setText("确定要删除商品吗？");
//
//        //确定
//        tv_logout_confirm.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                roundCornerDialog.dismiss();
//                datas = datasTemp;
//                initExpandableListViewData(datas);
//            }
//        });
//        //取消
//        tv_logout_cancel.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                roundCornerDialog.dismiss();
//            }
//        });
//    }
//
//    DialogInterface.OnKeyListener keylistener = new DialogInterface.OnKeyListener() {
//        public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
//            if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
//                return true;
//            } else {
//                return false;
//            }
//        }
//    };
}

