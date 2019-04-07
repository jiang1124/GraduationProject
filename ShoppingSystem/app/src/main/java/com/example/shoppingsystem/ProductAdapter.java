package com.example.shoppingsystem;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by 79124 on 2019/4/1.
 */

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    private Product product;
    static class ViewHolder extends RecyclerView.ViewHolder {
        View productView;
        ImageView productImage;
        TextView productName;
        Button backButton;

        public ViewHolder(View view) {
            super(view);
            productView = view;
            backButton = (Button) view.findViewById(R.id.back_product_button);
            productImage = (ImageView) view.findViewById(R.id.product_image);
            productName = (TextView) view.findViewById(R.id.product_name);
        }
    }

    public ProductAdapter(Product product){
        this.product = product;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_layout,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        holder.backButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                ((Activity) v.getContext()).finish();
            }
        });
        return holder;
    }
    @Override
    public void onBindViewHolder(ProductAdapter.ViewHolder holder, int position){
        Product product = this.product;
        holder.productImage.setImageResource(product.getProductImageId());
        holder.productName.setText(product.getProductName());
    }
    @Override
    public int getItemCount(){
        return 1;
    }
}
