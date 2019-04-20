package com.example.shoppingsystem.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.shoppingsystem.Application.BaseApplication;
import com.example.shoppingsystem.Entity.Goods;
import com.example.shoppingsystem.Entity.Shop;
import com.example.shoppingsystem.R;
import com.example.shoppingsystem.activity.PayActivity;
import com.example.shoppingsystem.util.ToastUtil;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 购物车的adapter
 * 因为使用的是ExpandableListView，所以继承BaseExpandableListAdapter
 */
public class ShoppingCarAdapter extends BaseExpandableListAdapter {

    private final Context context;
    private final LinearLayout SelectAllCartLL;
    private final ImageView selectAllCartIV;
    private final Button settlementCartButton;
    private final Button DeleteCartButton;
    private final RelativeLayout totalPriceRL;
    private final TextView totalPriceTV;
    private List<Shop> storeList;
    private boolean isSelectAll = false;
    private double total_price;

    public ShoppingCarAdapter(Context context, LinearLayout SelectAllCartLL,
                              ImageView selectAllCartIV, Button settlementCartButton, Button DeleteCartButton,
                              RelativeLayout totalPriceRL, TextView totalPriceTV) {
        this.context = context;
        this.SelectAllCartLL = SelectAllCartLL;
        this.selectAllCartIV = selectAllCartIV;
        this.settlementCartButton = settlementCartButton;
        this.DeleteCartButton = DeleteCartButton;
        this.totalPriceRL = totalPriceRL;
        this.totalPriceTV = totalPriceTV;
    }

    /**
     * 自定义设置数据方法;
     * 通过 notifyDataSetChanged() 刷新数据，可保持当前位置
     *
     * @param storeList 需要刷新的数据
     */
    public void setData(List<Shop> storeList) {
        this.storeList = storeList;
        notifyDataSetChanged();
    }

    @Override
    public int getGroupCount() {
        if (storeList != null && storeList.size() > 0) {
            return storeList.size();
        } else {
            return 0;
        }
    }

    @Override
    public Object getGroup(int groupPosition) {
        return storeList.get(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(final int groupPosition, final boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHolder groupViewHolder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_shopping_car_group, null);

            groupViewHolder = new GroupViewHolder(convertView);
            convertView.setTag(groupViewHolder);
        } else {
            groupViewHolder = (GroupViewHolder) convertView.getTag();
        }
        final Shop store = storeList.get(groupPosition);
        //店铺ID
        String store_id = store.getStore_id()+"";
        //店铺名称
        String store_name = store.getStore_name();

        if (store_name != null) {
            groupViewHolder.tvStoreName.setText(store_name);
        } else {
            groupViewHolder.tvStoreName.setText("");
        }

        //店铺内的商品都选中的时候，店铺的也要选中
        for (int i = 0; i < store.getGoods().size(); i++) {
            Goods goods = store.getGoods().get(i);
            boolean isSelect = goods.isSelect_product();
            if (isSelect) {
                store.setSelect_shop(true);
            } else {
                store.setSelect_shop(false);
                break;
            }
        }

        //因为set之后要重新get，所以这一块代码要放到一起执行
        //店铺是否在购物车中被选中
        final boolean isSelect_shop = store.isSelect_shop();
        if (isSelect_shop) {
            groupViewHolder.ivSelect.setImageResource(R.drawable.select);
        } else {
            groupViewHolder.ivSelect.setImageResource(R.drawable.unselect);
        }

        //店铺选择框的点击事件
        groupViewHolder.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                store.setSelect_shop(!isSelect_shop);

                List<Goods> goods = store.getGoods();
                for (int i = 0; i < goods.size(); i++) {
                    Goods goodsClass = goods.get(i);
                    goodsClass.setSelect_product(!isSelect_shop);
                }
                notifyDataSetChanged();
            }
        });

        //当所有的选择框都是选中的时候，全选也要选中
        w:
        for (int i = 0; i < storeList.size(); i++) {
            List<Goods> goods = storeList.get(i).getGoods();
            for (int y = 0; y < goods.size(); y++) {
                Goods good = goods.get(y);
                boolean isSelect = good.isSelect_product();
                if (isSelect) {
                    isSelectAll = true;
                } else {
                    isSelectAll = false;
                    break w;//根据标记，跳出嵌套循环
                }
            }
        }
        if (isSelectAll) {
            selectAllCartIV.setBackgroundResource(R.drawable.select);
        } else {
            selectAllCartIV.setBackgroundResource(R.drawable.unselect);
        }

        //全选的点击事件
        SelectAllCartLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isSelectAll = !isSelectAll;

                if (isSelectAll) {
                    for (int i = 0; i < storeList.size(); i++) {
                        List<Goods> goods = storeList.get(i).getGoods();
                        for (int y = 0; y < goods.size(); y++) {
                            Goods good = goods.get(y);
                            good.setSelect_product(true);
                        }
                    }
                } else {
                    for (int i = 0; i < storeList.size(); i++) {
                        List<Goods> goods = storeList.get(i).getGoods();
                        for (int y = 0; y < goods.size(); y++) {
                            Goods good = goods.get(y);
                            good.setSelect_product(false);
                        }
                    }
                }
                notifyDataSetChanged();
            }
        });

        //合计的计算
        total_price = 0.0;
        totalPriceTV.setText("¥0.00");
        for (int i = 0; i < storeList.size(); i++) {
            List<Goods> goods = storeList.get(i).getGoods();
            for (int y = 0; y < goods.size(); y++) {
                Goods good= goods.get(y);
                boolean isSelect = good.isSelect_product();
                if (isSelect) {
                    double num = good.getProduct_num();
                    double price = good.getPro_favl();

                    total_price = total_price + num * price;
                    //让Double类型完整显示，不用科学计数法显示大写字母E
                    DecimalFormat decimalFormat = new DecimalFormat("0.00");
                    totalPriceTV.setText("¥" + decimalFormat.format(total_price));
                }
            }
        }

        //去结算的点击事件
        settlementCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //创建临时的List，用于存储被选中的商品
                List<Goods> temp = new ArrayList<>();
                for (int i = 0; i < storeList.size(); i++) {
                    List<Goods> goods = storeList.get(i).getGoods();
                    for (int y = 0; y < goods.size(); y++) {
                        Goods good = goods.get(y);
                        boolean isSelect = good.isSelect_product();
                        if (isSelect) {
                            temp.add(good);
                        }
                    }
                }

                if (temp != null && temp.size() > 0) {//如果有被选中的
                    Intent payIntent = new Intent(BaseApplication.getContext(), PayActivity.class);
                    BaseApplication.getContext().startActivity(payIntent);
                    ToastUtil.makeText(context, "跳转到确认订单页面，完成后续订单流程");
                } else {
                    ToastUtil.makeText(context, "请选择要购买的商品");
                }
            }
        });

        //删除的点击事件
        DeleteCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //创建临时的List，用于存储被选中的商品Id
                String temp = new String();
                for (int i = 0; i < storeList.size(); i++) {
                    List<Goods> goods = storeList.get(i).getGoods();
                    for (int y = 0; y < goods.size(); y++) {
                        Goods good = goods.get(y);
                        boolean isSelect = good.isSelect_product();
                        if (isSelect) {
                            temp=good.getProduct_id()+",";
                        }
                    }
                }
                /**
                 * 实际开发中，通过回调请求后台接口实现删除操作
                 */
                if (mDeleteListener != null) {
                    mDeleteListener.onDelete(temp.trim());
                } else {
                    ToastUtil.makeText(context, "请选择要购买的商品");
                }

            }
        });

        return convertView;
    }

    static class GroupViewHolder {
        @InjectView(R.id.iv_select)
        ImageView ivSelect;
        @InjectView(R.id.tv_store_name)
        TextView tvStoreName;
        @InjectView(R.id.ll)
        LinearLayout ll;

        GroupViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }

    //------------------------------------------------------------------------------------------------
    @Override
    public int getChildrenCount(int groupPosition) {
        if (storeList.get(groupPosition).getGoods() != null && storeList.get(groupPosition).getGoods().size() > 0) {
            return storeList.get(groupPosition).getGoods().size();
        } else {
            return 0;
        }
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return storeList.get(groupPosition).getGoods().get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder childViewHolder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_shopping_car_child, null);

            childViewHolder = new ChildViewHolder(convertView);
            convertView.setTag(childViewHolder);
        } else {
            childViewHolder = (ChildViewHolder) convertView.getTag();
        }
        final Shop store = storeList.get(groupPosition);
        //店铺ID
        String store_id = store.getStore_id()+"";
        //店铺名称
        String store_name = store.getStore_name();
        //店铺是否在购物车中被选中
        final boolean isSelect_shop = store.isSelect_shop();
        final Goods  good = store.getGoods().get(childPosition);
        //商品图片
        String goods_image = good.getPro_image();
        //商品ID
        final int goods_id = good.getProduct_id();
        //商品名称
        String goods_name = good.getPro_name();
        //商品价格
        String goods_price = good.getPro_favl()+"";
        //商品数量
        String goods_num = good.getProduct_num()+"";
        //商品是否被选中
        final boolean isSelect = good.isSelect_product();


        String goods_image_id = goods_image;
        Glide.with(context)
                .load(goods_image_id)
                .placeholder(R.mipmap.ic_launcher)
                .into(childViewHolder.ivPhoto);

        if (goods_name != null) {
            childViewHolder.tvName.setText(goods_name);
        } else {
            childViewHolder.tvName.setText("");
        }
        if (goods_price != "") {
            childViewHolder.tvPriceValue.setText(goods_price);
        } else {
            childViewHolder.tvPriceValue.setText("");
        }
        if (goods_num != "") {
            childViewHolder.tvEditBuyNumber.setText(goods_num);
        } else {
            childViewHolder.tvEditBuyNumber.setText("");
        }

        //商品是否被选中
        if (isSelect) {
            childViewHolder.ivSelect.setImageResource(R.drawable.select);
        } else {
            childViewHolder.ivSelect.setImageResource(R.drawable.unselect);
        }

        //商品选择框的点击事件
        childViewHolder.ivSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                good.setSelect_product(!isSelect);
                if (!isSelect == false) {
                    store.setSelect_shop(false);
                }
                notifyDataSetChanged();
            }
        });

        //加号的点击事件
        childViewHolder.ivEditAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //模拟加号操作
                int num = good.getProduct_num();
                num++;
                good.setProduct_num(num);
                notifyDataSetChanged();
                if (mChangeCountListener != null) {
                    mChangeCountListener.onChangeCount(goods_id,num);
                }
            }
        });
        //减号的点击事件
        childViewHolder.ivEditSubtract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //模拟减号操作
                int num = good.getProduct_num();
                if (num > 1) {
                    num--;
                    good.setProduct_num(num);
                    if (mChangeCountListener != null) {
                        mChangeCountListener.onChangeCount(goods_id,num);
                    }
                } else {
                    ToastUtil.makeText(context, "商品不能再减少了");
                }
                notifyDataSetChanged();
            }
        });

        if (childPosition == storeList.get(groupPosition).getGoods().size() - 1) {
            childViewHolder.view.setVisibility(View.GONE);
            childViewHolder.viewLast.setVisibility(View.VISIBLE);
        } else {
            childViewHolder.view.setVisibility(View.VISIBLE);
            childViewHolder.viewLast.setVisibility(View.GONE);
        }

        return convertView;
    }

    static class ChildViewHolder {
        @InjectView(R.id.iv_select)
        ImageView ivSelect;
        @InjectView(R.id.iv_photo)
        ImageView ivPhoto;
        @InjectView(R.id.tv_name)
        TextView tvName;
        @InjectView(R.id.tv_price_key)
        TextView tvPriceKey;
        @InjectView(R.id.tv_price_value)
        TextView tvPriceValue;
        @InjectView(R.id.iv_edit_subtract)
        ImageView ivEditSubtract;
        @InjectView(R.id.tv_edit_buy_number)
        TextView tvEditBuyNumber;
        @InjectView(R.id.iv_edit_add)
        ImageView ivEditAdd;
        @InjectView(R.id.view)
        View view;
        @InjectView(R.id.view_last)
        View viewLast;

        ChildViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }

    //-----------------------------------------------------------------------------------------------

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    //删除的回调
    public interface OnDeleteListener {
        void onDelete(String productIds);
    }

    public void setOnDeleteListener(OnDeleteListener listener) {
        mDeleteListener = listener;
    }

    private OnDeleteListener mDeleteListener;

    //修改商品数量的回调
    public interface OnChangeCountListener {
        void onChangeCount(int goods_id,int pro_num);
    }

    public void setOnChangeCountListener(OnChangeCountListener listener) {
        mChangeCountListener = listener;
    }

    private OnChangeCountListener mChangeCountListener;
}
