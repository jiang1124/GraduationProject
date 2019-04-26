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
import com.example.shoppingsystem.Entity.OrderMain;
import com.example.shoppingsystem.Entity.Product;
import com.example.shoppingsystem.R;

import java.util.List;

/**
 * Created by 79124 on 2019/4/23.
 */

public class LineProductAdapter extends ArrayAdapter<Product> {

    private int resourceId;
    public LineProductAdapter(Context context, int textViewResourceId, List<Product> objects) {
        super(context, textViewResourceId, objects);
        resourceId =textViewResourceId;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Product product = getItem(position);
        View view;
        LineProductAdapter.ViewHolder viewHolder;
        if(convertView == null){
            view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
            viewHolder = new LineProductAdapter.ViewHolder();
            viewHolder.productImage = (ImageView) view.findViewById(R.id.iv_photo);
            viewHolder.productName =(TextView) view.findViewById(R.id.tv_name);
            viewHolder.priceValue = (TextView) view.findViewById(R.id.tv_price_value);
            viewHolder.timeTitle =(ImageView) view.findViewById(R.id.iv_edit_subtract);
            viewHolder.timeTV = (TextView) view.findViewById(R.id.tv_edit_buy_number);
            view.setTag(viewHolder);
        }else {
            view = convertView;
            viewHolder = (LineProductAdapter.ViewHolder) view.getTag();
        }

        Glide.with(BaseApplication.getContext())
                .load(product.getPro_image())
                .placeholder(R.mipmap.ic_launcher)
                .into(viewHolder.productImage);
        viewHolder.productName.setText(product.getPro_name());
        viewHolder.priceValue.setText(product.getPro_favl()+"");
        viewHolder.timeTitle.setVisibility(View.GONE);
        viewHolder.timeTV.setText("销量："+product.getPro_sale());
        return view;
    }

    class ViewHolder{
        ImageView productImage;
        TextView productName;
        TextView priceValue;
        ImageView timeTitle;
        TextView timeTV;

    }
}
