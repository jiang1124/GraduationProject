package com.example.shoppingsystem.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shoppingsystem.Entity.OrderMain;
import com.example.shoppingsystem.R;

import java.util.List;

/**
 * Created by 79124 on 2019/4/22.
 */

public class OrderListAdapter extends ArrayAdapter<OrderMain> {

    private int resourceId;
    public OrderListAdapter(Context context, int textViewResourceId, List<OrderMain> objects) {
        super(context,textViewResourceId,objects);
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
            viewHolder.orderNumberTV = (TextView) view.findViewById(R.id.tv_order_number);
            viewHolder.stateTV =(TextView) view.findViewById(R.id.tv_state_order);
            viewHolder.nameTV = (TextView) view.findViewById(R.id.tv_name_order);
            viewHolder.phoneTV =(TextView) view.findViewById(R.id.tv_phone_order);
            viewHolder.timeTV = (TextView) view.findViewById(R.id.tv_time_order);
            viewHolder.addressTV =(TextView) view.findViewById(R.id.tv_address_order);
            viewHolder.priceTV =(TextView) view.findViewById(R.id.tv_price_order);
            view.setTag(viewHolder);
        }else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.orderNumberTV.setText(orderMain.getOrder_id());
        if(orderMain.getExtra()!=null)
            viewHolder.stateTV.setText("已到达:"+orderMain.getExtra());
        else
            viewHolder.stateTV.setText(orderMain.getPay_info());
        viewHolder.nameTV.setText(orderMain.getRecipient_name());
        viewHolder.phoneTV.setText(orderMain.getPhone());
        viewHolder.timeTV.setText(orderMain.getDate());
        viewHolder.addressTV.setText(orderMain.getAddress());
        viewHolder.priceTV.setText("￥："+orderMain.getAll_money());
        return view;
    }

    class ViewHolder{
        TextView orderNumberTV;
        TextView stateTV;
        TextView nameTV;
        TextView phoneTV;
        TextView timeTV;
        TextView addressTV;
        TextView priceTV;

    }
}
