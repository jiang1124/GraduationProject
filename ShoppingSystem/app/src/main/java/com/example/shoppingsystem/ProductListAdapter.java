package com.example.shoppingsystem;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by 79124 on 2019/4/1.
 */

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ViewHolder> {
    private List<Product> mProductList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        View productView;
        ImageView productImage;
        TextView productName;

        public ViewHolder(View view) {
            super(view);
            productView = view;
            productImage = (ImageView) view.findViewById(R.id.product_image);
            productName = (TextView) view.findViewById(R.id.product_name);
        }
    }
    public ProductListAdapter(List<Product> productList){
        mProductList = productList;
    }
    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_list_item,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        holder.productView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                int position = holder.getAdapterPosition();
                Product product = mProductList.get(position);
                Toast.makeText(v.getContext(),"you clicked view "+product.getProductName(),Toast.LENGTH_SHORT).show();
            }
        });
        holder.productImage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                int position = holder.getAdapterPosition();
                Product product = mProductList.get(position);
                Toast.makeText(v.getContext(),"you clicked image "+product.getProductName(),Toast.LENGTH_SHORT).show();
            }
        });
        return holder;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder,int position){
        Product product = mProductList.get(position);
        holder.productImage.setImageResource(product.getProductImageId());
        holder.productName.setText(product.getProductName());
    }
    @Override
    public int getItemCount(){
        return mProductList.size();
    }
}
