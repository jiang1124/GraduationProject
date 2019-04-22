package com.example.shoppingsystem.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.shoppingsystem.Application.BaseApplication;
import com.example.shoppingsystem.Entity.Goods;
import com.example.shoppingsystem.R;

import java.util.List;

/**
 * Created by 79124 on 2019/4/21.
 */

public class OrderDetailAdapter extends ArrayAdapter<Goods> {

    private int resourceId;
    public OrderDetailAdapter(Context context, int resource, List<Goods> objects) {
        super(context, resource, objects);
        resourceId =resource;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Goods goods = getItem(position);
        View view;
        OrderDetailAdapter.ViewHolder viewHolder;
        if(convertView == null){
            view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
            viewHolder = new OrderDetailAdapter.ViewHolder();
            viewHolder.GoodsImage = (ImageView) view.findViewById(R.id.iv_photo);
            viewHolder.GoodsName = (TextView) view.findViewById(R.id.tv_name);
            viewHolder.price =(TextView) view.findViewById(R.id.tv_price_value);
            viewHolder.GoodsNum = (TextView) view.findViewById(R.id.tv_edit_buy_number);
            view.setTag(viewHolder);
        }else {
            view = convertView;
            viewHolder = (OrderDetailAdapter.ViewHolder) view.getTag();
        }
        double priceTemp =  goods.getPro_favl();
        Glide.with(BaseApplication.getContext())
                .load(goods.getPro_image())
                .placeholder(R.mipmap.ic_launcher)
                .into(viewHolder.GoodsImage);
        viewHolder.GoodsName.setText(goods.getPro_name());
        viewHolder.price.setText(priceTemp+"");
        viewHolder.GoodsNum.setText(goods.getProduct_num()+"");
        return view;
    }

    class ViewHolder{
        ImageView GoodsImage;
        TextView GoodsName;
        TextView price;
        TextView GoodsNum;
    }
}
