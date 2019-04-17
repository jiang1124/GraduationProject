package com.example.shoppingsystem.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.shoppingsystem.R;
import com.example.shoppingsystem.activity.EditRecipientActivity;
import com.example.shoppingsystem.activity.ProductActivity;
import com.example.shoppingsystem.emtity.Product;
import com.example.shoppingsystem.emtity.Recipient;
import com.example.shoppingsystem.util.LogUtil;
import com.example.shoppingsystem.util.ToastUtil;

import java.util.List;

import butterknife.InjectView;

/**
 * Created by 79124 on 2019/4/15.
 */

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.ViewHolder> {
    private List<Recipient> mRecipient;
    private Context mContext;

    //收件人名，地址，电话号码
    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView recipientName;
        TextView address;
        TextView phoneNumber;

        public ViewHolder(View view) {
            super(view);
            cardView = (CardView) view;
            recipientName = (TextView) view.findViewById(R.id.tv_recipient_name);
            address = (TextView) view.findViewById(R.id.tv_recipient_address);
            phoneNumber =(TextView) view.findViewById(R.id.tv_recipient_phone);
        }
    }

    public AddressAdapter(List<Recipient> Recipient){
        mRecipient = Recipient;
        notifyDataSetChanged();
    }

    public static void actionStart(Context context,Recipient recipient){
        Intent intent = new Intent(context, EditRecipientActivity.class);
        intent.putExtra("Recipient",recipient);
        context.startActivity(intent);
    }

    @Override
    public AddressAdapter.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType){
        if(mContext == null)
        {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_address_card,parent,false);
        final AddressAdapter.ViewHolder holder = new AddressAdapter.ViewHolder(view);
        holder.cardView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                int position = holder.getAdapterPosition();
                Recipient recipient = mRecipient.get(position);
                AddressAdapter.actionStart(mContext,recipient);
            }
        });
        return holder;
    }
    @Override
    public void onBindViewHolder(AddressAdapter.ViewHolder holder, int position){
        Recipient recipient = mRecipient.get(position);
        holder.recipientName.setText(recipient.getRecipientName());
        holder.address.setText(recipient.getRecipientAddress());
        holder.phoneNumber.setText(recipient.getRecipientPhone());
    }
    @Override
    public int getItemCount(){
        return mRecipient.size();
    }
}
