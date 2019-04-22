package com.example.shoppingsystem.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.shoppingsystem.Entity.User;
import com.example.shoppingsystem.R;
import com.example.shoppingsystem.activity.EditRecipientActivity;
import com.example.shoppingsystem.Entity.Recipient;

import java.util.List;

/**
 * Created by 79124 on 2019/4/15.
 */

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.ViewHolder> {
    private List<Recipient> mRecipient;
    private Context mContext;
    private User user;

    //收件人名，地址，电话号码
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView recipientNameTV;
        TextView addressTV;
        TextView phoneNumberTV;
        TextView editTV;
        TextView stateTV;

        public ViewHolder(View view) {
            super(view);
            recipientNameTV = (TextView) view.findViewById(R.id.tv_recipient_name);
            addressTV = (TextView) view.findViewById(R.id.tv_recipient_address);
            phoneNumberTV =(TextView) view.findViewById(R.id.tv_recipient_phone);
            editTV = (TextView) view.findViewById(R.id.tv_recipient_edit);
            stateTV = (TextView) view.findViewById(R.id.tv_address_state);
        }
    }

    public AddressAdapter(List<Recipient> Recipient,User user){
        mRecipient = Recipient;
        this.user = user;
        notifyDataSetChanged();
    }

    public void actionStart(Context context,Recipient recipient){
        Intent intent = new Intent(context, EditRecipientActivity.class);
        intent.putExtra("Recipient",recipient);
        intent.putExtra("User",user);
        context.startActivity(intent);
    }

    @Override
    public AddressAdapter.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType){
        if(mContext == null)
        {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_address_child,parent,false);
        final AddressAdapter.ViewHolder holder = new AddressAdapter.ViewHolder(view);
        holder.editTV.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                int position = holder.getAdapterPosition();
                Recipient recipient = mRecipient.get(position);
                actionStart(mContext,recipient);
            }
        });
        return holder;
    }
    @Override
    public void onBindViewHolder(AddressAdapter.ViewHolder holder, final int position){
        final Recipient recipient = mRecipient.get(position);
        holder.recipientNameTV.setText(recipient.getRecipient_name());
        holder.addressTV.setText(recipient.getCity()+" "+recipient.getAddress());
        holder.phoneNumberTV.setText(recipient.getPhone());
        if(recipient.getState().equals("default"))
            holder.stateTV.setVisibility(View.VISIBLE);
        else
            holder.stateTV.setVisibility(View.GONE);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLongClickListener.onLongClick(recipient.getAddress_id());
            }
        });
    }

    @Override
    public int getItemCount(){
        return mRecipient.size();
    }

    public interface OnLongClickListener{
        void onLongClick(int address_id);
    }

    private OnLongClickListener mLongClickListener;

    public void setLongClickListener(OnLongClickListener longClickListener) {
        mLongClickListener = longClickListener;
    }
}
