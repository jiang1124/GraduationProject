package com.example.store.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.store.Application.BaseApplication;
import com.example.store.Entity.OrderMain;
import com.example.store.Entity.Product;
import com.example.store.R;

import java.util.List;

/**
 * Created by 79124 on 2019/4/23.
 */

public class OrderListAdapter extends ArrayAdapter<OrderMain> {

    private int resourceId;
    public OrderListAdapter(Context context, int textViewResourceId, List<OrderMain> objects) {
        super(context, textViewResourceId, objects);
        resourceId =textViewResourceId;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        OrderMain orderMain = getItem(position);
        View view;
        ViewHolder viewHolder;
        if(convertView == null){
            view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.orderNo = (TextView) view.findViewById(R.id.tv_order_number);
            viewHolder.orderState =(TextView) view.findViewById(R.id.tv_state_order);
            viewHolder.userName = (TextView) view.findViewById(R.id.tv_name_order);
            viewHolder.phone = (TextView) view.findViewById(R.id.tv_phone_order);
            viewHolder.date = (TextView) view.findViewById(R.id.tv_time_order);
            viewHolder.address =(TextView) view.findViewById(R.id.tv_address_order);
            viewHolder.price = (TextView) view.findViewById(R.id.tv_price_order);
            view.setTag(viewHolder);
        }else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.orderNo.setText(orderMain.getOrder_id());
        viewHolder.orderState.setText(orderMain.getPay_info());
        viewHolder.userName.setText(orderMain.getRecipient_name());
        viewHolder.phone.setText(orderMain.getPhone());
        viewHolder.date.setText(orderMain.getDate());
        viewHolder.address.setText(orderMain.getAddress());
        viewHolder.price.setText(orderMain.getAll_money()+"");
        return view;
    }

    class ViewHolder{
        TextView orderNo;
        TextView orderState;
        TextView userName;
        TextView phone;
        TextView date;
        TextView address;
        TextView price;
    }
}
