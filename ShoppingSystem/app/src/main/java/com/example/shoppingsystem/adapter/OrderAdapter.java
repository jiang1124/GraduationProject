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
import com.example.shoppingsystem.Entity.Shop;
import com.example.shoppingsystem.R;

import java.util.List;

/**
 * Created by 79124 on 2019/4/21.
 */

public class OrderAdapter extends ArrayAdapter<Shop> {

    private int resourceId;
    public OrderAdapter(Context context, int textViewResourceId, List<Shop> objects) {
        super(context,textViewResourceId,objects);
        resourceId =textViewResourceId;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Shop shop = getItem(position);
        View view;
        ViewHolder viewHolder;
        if(convertView == null){
            view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.shopImage = (ImageView) view.findViewById(R.id.iv_photo);
            viewHolder.shopName = (TextView) view.findViewById(R.id.tv_name);
            viewHolder.price =(TextView) view.findViewById(R.id.tv_price_value);
            view.setTag(viewHolder);
        }else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        double priceTemp = 0;
        for(int i = 0;i < shop.getGoods().size();i++){
            priceTemp+=shop.getGoods().get(i).getPro_favl()*shop.getGoods().get(i).getProduct_num();
        }
        Glide.with(BaseApplication.getContext())
                .load(shop.getGoods().get(0).getPro_image())
                .placeholder(R.mipmap.ic_launcher)
                .into(viewHolder.shopImage);
        viewHolder.shopName.setText(shop.getStore_name());
        viewHolder.price.setText(priceTemp+"");
        return view;
    }

    class ViewHolder{
        ImageView shopImage;
        TextView shopName;
        TextView price;
    }
}
