package com.example.shoppingsystem.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.shoppingsystem.R;
import com.example.shoppingsystem.activity.ProductActivity;
import com.example.shoppingsystem.emtity.Product;

import java.util.List;

/**
 * Created by 79124 on 2019/4/1.
 */

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ViewHolder> {
    private List<Product> mProductList;
    private Context mContext;

    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView productImage;
        TextView productName;

        public ViewHolder(View view) {
            super(view);
            cardView = (CardView) view;
            productImage = (ImageView) view.findViewById(R.id.iv_product_image);
            productName = (TextView) view.findViewById(R.id.tv_product_name);
        }
    }

    public ProductListAdapter(List<Product> productList){
        mProductList = productList;
        notifyDataSetChanged();
    }

    public static void actionStart(Context context,Product product){
        Intent intent = new Intent(context,ProductActivity.class);
        intent.putExtra("Product",product);
        context.startActivity(intent);
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType){
        if(mContext == null)
        {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_card,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        holder.cardView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                int position = holder.getAdapterPosition();
                Product product = mProductList.get(position);
                ProductListAdapter.actionStart(mContext,product);
            }
        });
        holder.productImage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                int position = holder.getAdapterPosition();
                Product product = mProductList.get(position);
                ProductListAdapter.actionStart(mContext,product);
            }
        });
        return holder;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder,int position){
        Product product = mProductList.get(position);
        Glide.with(mContext)
                .load(product.getProductImageId())
                .placeholder(R.mipmap.ic_launcher)
                .into(holder.productImage);
        holder.productName.setText(product.getProductName());
    }
    @Override
    public int getItemCount(){
        return mProductList.size();
    }
}
